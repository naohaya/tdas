import java.util.ArrayList;
import java.math.BigInteger;
import java.util.UUID;
import java.util.Calendar;

public class MiningNode extends Process 
{
	int id;
	int difficultyBits = 20;
	boolean flag = true;
	Block initialBlock = null;
	ArrayList<Block> blockChain = new ArrayList<Block>();
	boolean debug = false;

	public MiningNode(int id, MessageQueue mq) {
		/*
		* call the constructor of superclass.
		*/ 
		super(id, mq);

		this.id = id;
	}

	/**
	* The main procedure of the process.
	*/
	public void run() {
		super.run();

		// the procedure of Process 0.
		if (id == 0) {
			Data data = new Data("This is the first block in the chain.");
			initialBlock = new Block(1, 1, 0, data, new BigInteger("0", 16), new BigInteger("0", 16), Calendar.getInstance().getTime().toString());
			Miner miner = new Miner(initialBlock, difficultyBits); // create a miner.			

			initialBlock = miner.createInitialBlock(); // create an initial block of the chain.

			addBlockToChain(initialBlock, blockChain); // add the initial block into the chain.

			broadcastMiner(id, miner); 
			broadcastBlock(id, initialBlock); // send the first block to others.

			Block b;
			//while(true){
			while(blockChain.size() < 5){
				if ((b = receiveBlock()) != null) { // receive the first block.
					//blockChain.add(b); // add the first block in the own chain.
					addBlockToChain(b,blockChain);
					miner.updateBlock(b);
					//break;
				}

				miner.setBlock(blockChain.get(blockChain.size() - 1));
				Result r = checkHashValues(miner.getHashValues()); // check the obtained hash values.
				if(r != null) {
					b = createBlock(r, blockChain); // create a block if there is an eligible hash value.
					addBlockToChain(b,blockChain);  // add the created block into the chain.
					miner.updateBlock(b);  // update the block as the latest block in the miner.
					broadcastBlock(id, b); // broadcast the block to every other processes.

					System.out.print("Found at Process " + id + "=>>");
					System.out.print("Result: "+r.getHashValue().toString(16));
					System.out.println(", Nonce: "+r.getNonce());
				}

				yield();

				try{
					Thread.sleep(1000);
				}
				catch(InterruptedException e){}
			}

			printChain(id, blockChain);
			outputChain(id, blockChain); // output information of each block in the chain.
			debugPrint("Latest block is :"+miner.getBlock().getOwnHash().toString(16));

		}
		// the procedure of processes except Process 0.
		else{
			Object c;
			Block b;
			Miner miner = null; // create a miner.	
			//while(true){
			while(blockChain.size() < 5){
				if (miner == null) {
					if ((c = receive()) != null) {
						miner = (Miner) ((Message)c).getContent();
					}
				}
				if ((b = receiveBlock()) != null) { // receive the first block.
					//blockChain.add(b); // add the first block in the own chain.
					addBlockToChain(b,blockChain);
					//break;
				}
				if(blockChain.size() > 0){
					miner.setBlock(blockChain.get(blockChain.size() - 1));
					Result r = checkHashValues(miner.getHashValues()); // obtain hash values.
					if(r != null) {
						b = createBlock(r, blockChain); // create a block if there is an eligible hash value.
						addBlockToChain(b,blockChain);  // add the created block into the chain.
						miner.updateBlock(b);  // update the block as the latest block in the miner.
						broadcastBlock(id, b); // broadcast the block to every other processes.
						
					System.out.print("Found at Process " + id + "=>>");
					System.out.print("Result: "+r.getHashValue().toString(16));
					System.out.println(", Nonce: "+r.getNonce());
					}


				}
				try{
					Thread.sleep(1000);
				}
				catch(InterruptedException e){}
			}

			outputChain(id, blockChain); // output information of each block in the chain.


		}


	}

	/**
	* broadcast a miner to every other node.
	*/
	private void broadcastMiner(int id, Miner m) {
		for(int count = 0; count < super.getMessageQueue().getTotalNum(); count++) {
			if (count != id) {
				send(count, new DefaultMessage(id, m));
				// System.out.println("send a block to "+count);
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
			System.out.println("Proc. "+id+": received a block of "+b.getOwnHash().toString(16));

			return b;
		}
		else {
			debugPrint("no message at "+id);

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
	* create a block from a given result.
	*/
	private Block createBlock(Result r, ArrayList<Block> bchain){
		Block b = new Block();
		b.setData(new Data(UUID.randomUUID().toString())); // set a random UUID as a string.
		b.setOwnHash(r.getHashValue());
		b.setPrevHash(bchain.get(bchain.size() - 1).getOwnHash());
		b.setNonce(r.getNonce());
		b.setDifficultyBits(r.getDifficultyBits());
		b.setTimestamp(Calendar.getInstance().getTime().toString());

		return b;
	}

	/**
	* validate the hash value of a received block.
	*/
	private boolean validateBlock(Block b) {
		if(Miner.isHit(Miner.generatingTarget(b.getDifficultyBits()), b.getOwnHash())){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* add a received block into the own chain
	*/
	private boolean addBlockToChain(Block block, ArrayList<Block> bchain){
		if(bchain.size() > 0){
			for(Block b : bchain) {
				if(b.getOwnHash().compareTo(block.getPrevHash()) == 0) {
					block.setBlockNum(bchain.get(bchain.size() - 1).getBlockNum() + 1);
					bchain.add(block);
					return true;
				}
			}
		}
		else{
			bchain.add(block); // initial block
		}

		return false;
	}

	/**
	* print information of each block in a chain.
	*/
	private void printChain(int id, ArrayList<Block> chain) {
		
			System.out.println("==============" + "Process " + id);
			for (Block blk : chain) {
				System.out.println(blk.getBlockNum() + ": prev_hash = " + blk.getPrevHash().toString(16) +
									", own_hash = " + blk.getOwnHash().toString(16));
				// System.out.println(blk.getData().getContent());
			}
			System.out.println("==============");
		

	}

	/**
	* output the own chain as a file in the csv format.
	*/
	private void outputChain(int id, ArrayList<Block> chain) {
		OutputBlocks ob = new OutputBlocks(id, chain);
		ob.output();
	}

	/**
	* debug output on a screen.
	*/
	private void debugPrint(String st) {
		if(debug == true) {
			System.out.println(st);
		}
	}

}