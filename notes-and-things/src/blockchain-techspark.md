# Blockchain and graph, greater than the sum of their hype?


Pick up any of the recent financial press and it's likely to contain at least
one article proclaiming Blockchain as the next big thing.  Hype
is infectious. The finance world is certainly not immune, with many high profile
institutions making public statements around trialling the technology for various
applications, most notably around trading and settlement.

Blockchain is effectively an encrypted temporal linked list of transactions.
A linked list is not an appropriate data structure for random access.  So we
decided to integrate one fully-hyped technology - with one enjoying somewhat of
a renaissance - graph databases.

For this investigation we used the blockchain platform favoured by many
startups - Ethereum - and integrated it with Neo4j.  Over the course of writing 
this article, Ethereum Homestead was released, and this has made the deployment 
of smart contracts altogether easier by providing a GUI to connect to the testnet.

Ethereum provides a distributed blockchain platform (the Ethereum network) and a 
command line client, geth.  In addition to being a gateway to the Ethereum network, 
Geth is a Javascript repl shell (we found it 
limited in nature when compared to the ever-growing Javascript ecosystem) which 
also acts as a gateway to the Ethereum blockchain.  Geth exposes 
a number of endpoints and apis that can be accessed from our own application to 
access the Ethereum.  
We will use their Javascript api, web3.js, from within a node application, 
to hook into geth to listen for blockchain events from our newly-minted smart contract, 
and on receiving an event, insert the event into a running Neo4j graph.

The interactions are as follows:

<< Insert interaction diagram here - because it's complex!  >>

Smart contracts in Ethereum can be thought of as a piece of Solidity code that 
encapsulates some logic to be run on the Ethereum network.  The smart contract 
we will deploy in this article is modelling a new share issue.  Therefore 
the contract will contain:
 1. the total number of shares in the issue - i.e. the issuer issues 
    10,000,000 new shares
 2. reference data about the contract such as the ISIN 
 3. the register of how many shares each account owns

# Getting started:

 1. Install the Ethereum-Wallet gui from ethereum.org
 2. Create accounts in your wallet, for Alice, Bob and Carol, who will be the 
    market participants for this share issue
 3. Start the Ethereum-Wallet client and connect to the public testnet
    << Insert Screengrab here  >>
 4. Select the menu option to start mining Ethereum in the client 
 5. Create new accounts for Alice, Bob and Carol
 6. Install the Go Ethereum command line client (we used `brew install geth` on 
    a Mac for convenience)
 7. Install the solidity compiler `brew install cpp-ethereum`
 8. From the command line, you should now be able to start the ethereum interactive 
    Javascript shell to connect to the public Ethereum testnet configured from 
    starting the wallet gui:
    `ethereum@Exc-lab ~/testnet $  geth attach`
 9. Using the Ethereum Wallet gui, deploy the following smart contract code:
 
` 
contract ShareClass {

    string public name;
    uint8 public decimals;
    string public symbol;
    string public isin;
    string public description;

    /* the register of how many shares are owned by which account */
    mapping (address => uint256) public balanceOf;

    /* Generates an event on the blockchain to notify clients */
    event Transfer(address indexed from, address indexed to, uint256 value);

    /* Initializes the shareclass for the instrument with initial supply of
       all equity assigned to the issuer */
    function ShareClass(uint256 initialSupply,
                        string tokenName,
                        string isinId,
                        string desc,
                        uint8 decimalUnits,
                        string tokenSymbol) {
        balanceOf[msg.sender] = initialSupply;  // Give the creator all equities
        name = tokenName;                       // Set the name for display purposes
        decimals = decimalUnits;                // Amount of decimals for display purposes
        symbol = tokenSymbol;                   // Set the symbol for display purposes
        isin = isinId;
        description = desc;
    }

    function transfer(address recipient, uint256 quantity) {
        ensureSenderHasEnough(quantity);
        balanceOf[msg.sender] -= quantity;
        balanceOf[recipient]  += quantity;
        // Notify of transfer event:
        Transfer(msg.sender, recipient, quantity);
    }

    function ensureSenderHasEnough(uint256 quantity) private {
        if (balanceOf[msg.sender] < quantity) throw;
    }
}
`

The above code is written in Solidity - Ethereum's smart contract language.  The 
language borrows much of its syntax from Go, but without the rich choice of apis 
(for good reasons). The contract will be deployed to the Ethereum network and 
run on every miner node within the Ethereum Virtual Machine (hence why the apis 
are restricted).  Thus creating the immutable record of ownership.

<< Insert screen grab of deployment options  >>


 
 
## Interacting with the contract on the blockchain
 
The contract is now deployed and one instance has been instantiated on the 
Ethereum testnet blockchain.  Another way to think of this is the shares 
have now been issued but are still all owned by the issuer.  This contract 
instance can now be remotely located by other clients of the same blockchain.
  
Also, the ethereum-wallet gui can now be used to make transactions in our new 
share issue.  This is done by selecting the send button at the top of the screen, 
and then selecting abc_A_ord in the dropdown box on the right of the screen. 

Now the share issue has been made on Ethereum, we can now listen to events and 
make trades.  This will be achieved, by creating a small Node app, that will 
connect to the running instance of geth.

## Setting up Neo4j

We will model our blockchain share trades in the graph database, Neo4j.  The nodes
in the graph will be modelled as the accounts of the market participants (Alice, 
Bob and Carol), with the trades modelled as relationships.

Neo4j ships out-of-the-box with a user-friendly gui web console.  This can be 
used as an IDE to administer the server, issue Cypher queries (Neo4j's version 
of SQL for graph), and also to visualise the data.

1. Install neo4j (`brew install neo4j`)
2. Connect to the neo4j console, open a browser to http://localhost:7474
3. Create the account nodes with the following Cypher code:
`
CREATE(issuer:Account {name:"Issuer", address:"0x75ab0f992cd2e91a01e63c3e459aa6072ae50adf", remaining_balance:10000000})
CREATE(alice:Account  {name:"Alice",  address:"0xb4dddb1511a89c2ad188869fda0b967b8cc1637c", remaining_balance:0})
CREATE(bob:Account    {name:"Bob",    address:"0x9f6a733474677e0b08ea7cd655b182b7424f196d", remaining_balance:0})
CREATE(carol:Account  {name:"Carol",  address:"0xa2e9af9e1b73bb2c12f8a7868158d940d22d3b31", remaining_balance:0})
`    

## Listening to events on the deployed contract from a node app

1. Install the node packages : ethereum, node-rest-client
    `npm install web3`
    `npm install node-rest-client`
3. start the geth shell :
    `geth attach`
4. From within the geth shell, start the rpc server on port 8545:
    `admin.startRPC("127.0.0.1", 8545, "*", "web3,db,net,eth")`
5. Start the node repl shell, then type the following, this will create a local 
    reference to the remote contract over the jsonrpc port open to geth on 8545.
    
var Web3 = require('web3')
var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));
var contractAddress = "0x807bF45B0245d8FA96F68E319116E18a15b07A10"
var contractCode = "contract ShareClass { string public name; uint8 public decimals; string public symbol; string public isin; string public description; mapping (address => uint256) public balanceOf; event Transfer(address indexed from, address indexed to, uint256 value);  function ShareClass(uint256 initialSupply, string tokenName, string isinId, string desc, uint8 decimalUnits, string tokenSymbol) { balanceOf[msg.sender] = initialSupply; name = tokenName; decimals = decimalUnits; symbol = tokenSymbol; isin = isinId; description = desc; } function transfer(address recipient, uint256 quantity) { ensureSenderHasEnough(quantity); balanceOf[msg.sender] -= quantity; balanceOf[recipient]  += quantity; Transfer(msg.sender, recipient, quantity); } function ensureSenderHasEnough(uint256 quantity) private { if (balanceOf[msg.sender] < quantity) throw; } }"
var compiledContract = web3.eth.compile.solidity(contractCode)
var contractDefinition = compiledContract.ShareClass.info.abiDefinition
var reference = web3.eth.contract(contractDefinition).at(contractAddress)

6. Next define the following function in node:

`function insertIntoNeo(ownerAddress, buyerAddress, amount){
    var stmts = {
        "statements" : [ 
            { "statement" : `MATCH (owner:Account),(buyer:Account) WHERE owner.address = '${ownerAddress}' AND buyer.address = '${buyerAddress}' CREATE (owner)-[ :SOLD_TO { amount:${amount}, tstamp:timestamp() } ]->(buyer)` }, 
            { "statement" : `MATCH (owner:Account) WHERE owner.address = '${ownerAddress}' set owner.remaining_balance = owner.remaining_balance - ${amount}`}, 
            { "statement" : `MATCH (buyer:Account) WHERE buyer.address = '${buyerAddress}' set buyer.remaining_balance = buyer.remaining_balance + ${amount}`} 
        ]
    }
    
    var args = {
        data : stmts,
        headers : 
        { 
            "Content-Type": "application/json",
            "Accept": "application/json; charset=UTF-8"
        }
    }
    
    client.post("http://localhost:7474/db/data/transaction/commit", args, function (data, response) {
    	console.log(data);
    	console.log(response);
    });
}`

This will use Neo4j's rest api for executing statements against the database.  
The above code executes 3 statements within the same transaction :
    1. find the appropriate account nodes, and then add a new sold_to relationship 
    between them.  Relationships in Neo are directional and can have properties,
    in this case, the amount and the timestamp of the trade are set
    2. find the appropriate seller account node and decrement their balance of
    stock
    3. find the appropriate buyer account node and increment their balance of stock
    
7. In the node repl, now add the code to listen to the remote reference and insert the details of the trade into Neo:

  `reference.Transfer().watch(
    function(error, result){
        insertIntoNeo(result.args.from, result.args.to, result.args.value);
  });`

8. From within the Ethereum client, it should now be possible to trade the 
   contract and then see this reflected in Neo.  Pointing a browser at 
   `http://localhost:7474` should yield a visualisation similar to the following:
  
  << Insert screenshot of the Graph gui  >>
    
## Further extensions/use cases

This could be further extended and integrated with an identity or KYC service, 
to provide lookup of who each account holder is, to enable the sending of annual 
shareholder reports for instance.  Thereby alleviating ABC, inc. from having to 
maintain its own shareholder register as its shareholders could manage their own 
identity and details for all their holdings in one place. 

Alternatively, what about fraud visualisation - all of this functionality is 
available out of the box, making Graph a compelling choice for data visualisation


Legislation still has a way to go to catch up with technical innovation in this 
area.  For instance, the laws around property rights still need to be addressed, 
as does the issue of how to reverse an irreversible, immutable transaction, if 
it were ever in dispute. 

The appropriate law changes will certainly come in time.  However, it is 
undeniable that blockchain and its system of trust is enabling
existing applications to work faster and more securely.  It is also
facilitating new distributed applications (such as secure public land registries,
particularly in regions such as "Gambia?" where there is no current reliable
standard).  Looking past the hype, we see the real value in blockchain as a 
complementary technology to some of the more established technologies prevalent 
in finance today.

