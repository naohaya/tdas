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
			Miner miner = new Miner(initialBlock, 20);
		
			while(flag) {
				ArrayList<Result> results = miner.getHashValues();

				for(Result r : results) {
			//	System.out.println(r.getHashValue().toString(16));
					if(Miner.isHit(Miner.generatingTarget(r.getDifficultyBits()), r.getHashValue())){
			//	if(Miner.isHit(m.getTarget().toString(16), r.getHashValue().toString(16))){
						System.out.println("Result: "+r.getHashValue().toString(16));
						System.out.println("Nonce: "+r.getNonce());
						System.out.println("Target: "+miner.getTarget().toString(16));
					
						initialBlock.setOwnHash(r.getHashValue());
						initialBlock.setNonce(r.getNonce());
						flag = false;
						break;
					}
				}
			}

			blockChain.add(initialBlock);

			for(int count = 1; count < super.getMessageQueue().getTotalNum(); count++) {
				send(count, new DefaultMessage(0, initialBlock));
				System.out.println("send a block to "+count);

			}

		}
		else{
			Object c;
			while(true){
				if ((c = receive()) != null) {
					Block b = (Block) ((Message)c).getContent();
					System.out.println(id+": receive a block of "+b.getOwnHash().toString(16));
					blockChain.add(b);
					break;
				}
				else{
					System.out.println("no message at "+id);
				}
				try{
					Thread.sleep(1000);
				}
				catch(InterruptedException e){}
			}

		}


	}

}