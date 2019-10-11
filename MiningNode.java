import java.util.ArrayList;
import java.math.BigInteger;

public class MiningNode extends Process 
{
	int id;
	boolean flag = true;
	Block initialBlock = null;
	ArrayList<Block> blockChain = new ArrayList<Block>();

	public MiningNode(int id, MessageQueue mq) {
		/*
		* call the constructor of superclass.
		*/ 
		super(id, mq);

		this.id = id;
	}

	public void run() {
		super.run();

		if (id == 0) {
			Data data = new Data("This is the first block in the chain.");
			initialBlock = new Block(1, 1, 0, data, new BigInteger("0", 16), new BigInteger("0", 16));
			Miner miner = new Miner(initialBlock, 20); // create a miner.

			initialBlock = miner.createInitialBlock(); // create an initial block of the chain.

			blockChain.add(initialBlock); // add the initial block into the chain.

			broadcastBlock(id, initialBlock); // send the first block to others.


		}
		else{
			Block b;
			while(true){
				if ((b = receiveBlock()) != null) { // receive the first block.
					blockChain.add(b); // add the first block in the own chain.
					break;
				}
				try{
					Thread.sleep(1000);
				}
				catch(InterruptedException e){}
			}

		}


	}

	/**
	* broadcast a block to every other node.
	*/
	private void broadcastBlock(int id, Block block) {
		for(int count = 0; count < super.getMessageQueue().getTotalNum(); count++) {
			if (count != id) {
				send(count, new DefaultMessage(id, block));
				// System.out.println("send a block to "+count);
			}

		}
	}

	/*
	* receive a block
	*/
	private Block receiveBlock() {
		Object c;
		if ((c = receive()) != null) {
			Block b = (Block) ((Message)c).getContent();
			System.out.println(id+": receive a block of "+b.getOwnHash().toString(16));

			return b;
		}
		else {
			System.out.println("no message at "+id);

			return null;
		}
	}


	/**
	* check obtained hash values are valid or not.
	*/
    // results = miner.getHashValues(); // obtain hash values.
	private Result checkHashValues(ArrayList<Result> results) {

			for(Result r : results) {
			//	System.out.println(r.getHashValue().toString(16));
				if(Miner.isHit(Miner.generatingTarget(r.getDifficultyBits()), r.getHashValue())){
					/*		
					System.out.println("Result: "+r.getHashValue().toString(16));
					System.out.println("Nonce: "+r.getNonce());
					System.out.println("Target: "+m.getTarget().toString(16));
					System.exit(0);
					*/

					return r; 
				}
			}

			return null; // does not hit
	}

	/**
	* output the own chain as a csv file.
	*/
	private void outputChain() {

	}

}