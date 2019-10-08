import java.util.ArrayList;
import java.math.BigInteger;
import org.apache.commons.codec.digest.DigestUtils;

class Miner 
{

	private Block latestBlock;
	//private int counter = 0;
	private int hitCounter = 0; // Num. of mining results that match the target.
	private int maxMiningValues = 100; // Num. of values return to each client
	private int difficultyBits = 1;	 // The difficulty bit
	private long baseTarget = 2;
	private long nonce = 0;
	private BigInteger target;


	public Miner (Block block, int diffbits) {
		this.latestBlock = block;
		this.difficultyBits = diffbits;
		this.target = BigInteger.valueOf(baseTarget);
		this.target = this.target.pow(256-this.difficultyBits);
		System.out.println(target.toString(16)+" diff: "+this.difficultyBits);
	}
	
	public synchronized ArrayList<Result> getHashValues() {
		return mining();
	}

	public String createDigest() {
		return createDigest(this.latestBlock);
	}

	private String createDigest(Block block) {
		String digest = Integer.toString(block.getBlockNum()) 
		+ Long.toString(block.getNonce())
		+ block.getPrevHash().toString()		
		+ block.getOwnHash().toString();

		return digest;
	}

	public void setBlock(Block block) {
		if(this.latestBlock == null)
			this.latestBlock = block;

		if(this.latestBlock.getBlockNum() < block.getBlockNum()) 
			this.latestBlock = block;
		
	}

	public Block getBlock(){
		return latestBlock;
	}

	private void resetNonce() {
		this.nonce = 0;
	}

	public long getNonce() {
		return this.nonce;
	}

	public void setDifficultyBits(int bits) {
		this.difficultyBits = bits;
	}

	private void incDifficultyBits() {
		this.difficultyBits++;
	}

	public BigInteger getTarget() {
		return this.target;
	}

	public ArrayList<Result> mining() {
		ArrayList<Result> results = new ArrayList<Result>();
		String digest = createDigest();
		String result_string = null;
		BigInteger result;
		//long l_target = Math.pow(2, 256-this.difficultyBits);

		for(int count = 0; count < this.maxMiningValues; count++) {
			result_string = DigestUtils.sha3_256Hex(digest+Long.toString(nonce));
			result = new BigInteger(result_string, 16);
			results.add(new Result(result,nonce));
			if(target.compareTo(result) == 1){
				hitCounter++;
				//if(hitCounter%10 == 0) {
					// increment difficltyBits every 10 hits.
					incDifficultyBits();
				//}
				
			}

			nonce++;

		}

		return results;
	}

	public static boolean isHit(BigInteger target, BigInteger hashValue) {
		if(target.compareTo(hashValue) == 1) {
			return true;
		}
		else {
			return false;
		}

	}

}