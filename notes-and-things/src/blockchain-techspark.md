# Blockchain and graph, greater than the sum of their hype?

Ask any hipster and they will tell you - Blockchain is the next big thing.  Hype
is infectious. The finance world is certainly not immune, with many high profile
firms making public statements around trialling the technology for various
applications, most notably around trading and settlement.

Blockchain is effectively an encrypted temporal linked list of transactions.
A linked list is not an appropriate data structure for random access.  So we
decided to integrate one fully-hyped technology - with one enjoying somewhat of
a renaissance - graph databases.

For this investigation we used the same blockchain platform used by many blockchain-based
startups - Ethereum - and integrated it with Neo4j.  Over the course of writing 
this article, Ethereum Homestead was released, and this has made the deployment 
of smart contracts altogether easier by providing a GUI to connect to the testnet.

Ethereum provides a distributed blockchain platform (the Ethereum network) and a 
command line client written, geth.  Geth is a javascript repl shell (we found it 
limited in nature when compared to the ever-growing Javascript ecosystem) which 
also acts as a gateway to the Ethereum blockchain.  Geth exposes 
a number of endpoints and apis that can be embedded in our own application.  
We will use their javascript api, web3.js, from within a node application, 
to hook into geth to listen for blockchain events from our newly-minted smart contract, 
and on receiving an event, insert the event into a running Neo4j graph.

Smart contracts in Ethereum can be thought of as a piece of Solidity code that 
encapsulates some logic to be run on the Ethereum network.  For the smart contract 
we will deploy in this article, it can be thought of as a new share issue.  Therefore 
the contract will contain:
 1. the total number of shares in the issue - i.e. the issuer issues 10,000 new shares
 2. reference data about the contract such as the ISIN 
 3. the register of how many shares each account owns

# Getting started:

 1. Install the Ethereum-Wallet gui from ethereum.org
 2. Start the Ethereum-Wallet client and connect to the public testnet
    << Insert Screengrab here  >>
 3. Select the menu option to start mining Ethereum in the client 
 4. Create new accounts for Alice, Bob and Carol
 5. Install the Go Ethereum command line client (we used `brew install geth` on 
    a Mac for convenience)
 6. Install the solidity compiler `brew install cpp-ethereum`
 7. From the command line, you should now be able to do the following
    `ethereum@Exc-lab ~/testnet $  geth attach`
 8. This will start the geth interactive Javascript shell to interact with the 
    testnet 
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
<< Insert screen grab of deployment options  >>

Fill out the details as shown in the above screenshot 
 
 
## Interacting with the contract on the blockchain
 
The contract is now deployed and one instance has been instantiated on the 
Ethereum testnet blockchain.  Another way to think of this is the shares 
have now been issued but are still all owned by the issuer.  This contract 
instance can now be remotely located by other clients of the same blockchain.
  
Also, the ethereum-wallet gui can now be used to make transactions in our new 
share issue.  This is done by selecting the send button at the top of the screen, 
and then selecting abc_A_ord in the dropdown box on the right of the screen. 

## Listening to events on the contract

1. Install neo4j (this can be done on a mac with `brew install neo4j`)
2. Install node.js (`brew install node4-lts`)
3. Install the ethereum node library :
    `npm install web3`
4. Install the neo4j node library :
    `npm install neo4j-js`
5. start the geth shell :
    `geth attach`
6. From within the geth shell, start the rpc server:
    `admin.startRPC("127.0.0.1", 8545, "*", "web3,db,net,eth")`
6.a may also need to define the solidity compiler in geth:
    `admin.setSolc('/usr/local/bin/solc')` 
    
5. start the node repl shell
    
    `> var Web3 = require('web3')`
    `> web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));`
    `> var contractAddress = "0x807bF45B0245d8FA96F68E319116E18a15b07A10"`
    `> var contractCode = "<paste in the code above for the contract>"`
    `> var compiledContract = web3.eth.compile.solidity(contractCode)`
    `> var contractDefinition = compiledContract.ShareClass.info.abiDefinition`
    `> var reference = web3.eth.contract(contractDefinition).at(contractAddress)`
    `> reference.Transfer().watch(
         function(error, result){
           insertIntoNeo(result.args.from, result.args.to, result.args.value);
         });`
         
    define the method here
  
  
So from the geth shell:
1. create a variable containing the code for the contract: 
`> var contractCode = <paste in the code above for the contract>`
2. compile the contract locally:
`> var compiledContract = web3.eth.compile.solidity(contractCode)`
3. create a variable for the contract byte code:
`> var contractDefinition = compiledContract.ShareClass.info.abiDefinition`
4. create a variable for the remote reference:
 `> var reference = eth.contract(contractDefinition).at('0x12345678....')`
5. create a listener on the remote reference:
 `> reference.Transfer().watch(function(error, result){
        insertIntoNeo(result.args.from, result.args.to, result.args.value);
   })`
 
  
 
define the insertIntoNeo function in this section
 
todo:
 
 need the way of connecting a javascript/node console to testnet
 OR
 need a way of making a rest call in the event handler 

 can't be done from within geth - need to run geth as the gateway to the 
 ethereum network, then run up a web3.js console in node (?) and then locate the 
 contract instance & listen to the events on it, and make the rest call into the 
 neo4j server
 
 need to just make a web3 variable object available, and then I should be able 
 to do all the above code too
 
 to make the rest call to add into neo4j
 
 then do some txns
 
 visualise using the graph and bung in a screenshot
 
 
 
 
 
 







This could be further extended and integrated with an identity or KYC service, 
to provide lookup of who each account holder is, to enable the sending of annual 
shareholder reports for instance.  Thereby alleviating XYZ, inc. from having to 
maintain its own shareholder register as its shareholders could manage their own 
identity and details for all their holdings in one place. 
 
Setting up neo4j


visualisation


It is undeniable that blockchain and its system of trust is enabling
existing applications to work faster and more securely.  It is also
facilitating new distributed applications (such as secure public land registries,
particularly in regions such as "Gambia?" where there is no current reliable
standard).  However, we see its value as a complementary technology to fulfil
your business cases rather than being an end in itself.

We are continuing to
invest in our blockchain practice as we see a multitude of applications in the
pipeline.  We believe

Something about once legislation catches up with technical innovation in this area,
we want our customers to be well-placed and ready to surf on the wave, instead
of being drowned by it.


# NOTES:

Getting other people to interact with your code

In order to other people to run your contract they need two things: the address 
where the contract is located and the ABI (Application Binary Interface) which 
is a sort of user manual, describing the name of its functions and how to call 
them. In order to get each of them run these commands:

greeterCompiled.greeter.info.abiDefinition;
greeter.address;
Then you can instantiate a javascript object which can be used to call the 
contract on any machine connected to the network. Replace 'ABI' and 'address' to 
create a contract object in javascript:

var greeter = eth.contract(ABI).at(Address);
This particular example can be instantiated by anyone by simply calling:

