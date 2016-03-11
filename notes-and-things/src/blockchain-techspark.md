Blockchain and graph, greater than the sum of their hype?

Ask any hipster and they will tell you - Blockchain is the next big thing.  Hype
is infectious. The finance world is certainly not immune, with many high profile
firms making public statements around trialling the technology for various
applications, but most notably around trading and settlement.

Blockchain is effectively an encrypted temporal linked list of transactions.
A linked list is not an appropriate data structure for random access.  So we
decided to integrate one fully-hyped technology - with one enjoying somewhat of
a renaissance - graph databases.

For this investigation we used the same blockchain platform used by many blockchain-based
startups - Ethereum and integrated it with Neo4j.

Setting up a local testnet using the Ethereum Go client, Geth

    1. First create the customgenesis.json file:
    {
        "nonce": "0x0000000000000042",
        "mixhash": "0x0000000000000000000000000000000000000000000000000000000000000000",
        "difficulty": "0x4000",
        "alloc": {},
        "coinbase": "0x0000000000000000000000000000000000000000",
        "timestamp": "0x00",
        "parentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
        "extraData": "Custom Excelian Ethereum Genesis Block",
        "gasLimit": "0xffffffff"
    }
    2. Start the server:
        ethereum@Exc-lab ~/testnet $  geth --genesis customgenesis.json --networkid 1100 -nodiscover -maxpeers 0  console
    3. Create some users:
        ethereum@Exc-lab ~/testnet $  geth account new
    4. Mine some ether, Ethereum's platform currency (which can be traded on
       the public Ethereum network and has an fx rate into Bitcoin)
        ethereum@Exc-lab ~/testnet $  geth --mine -rpccorsdomain "*" --ipcapi "admin,eth,miner" --rpcapi "eth,web3" --networkid 1100 -maxpeers 5 --minerthreads 1
    5. From a separate window check the balance of the miner's account:
        ethereum@Exc-lab ~/testnet $  geth attach
        > web3.fromWei(eth.getBalance(eth.coinbase),"ether")
        610

Deploying a test contract



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


===NOTES:

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

