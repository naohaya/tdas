import java.util.ArrayList;
import java.math.BigInteger;
import org.apache.commons.codec.digest.DigestUtils;

class Miner 
{

	private Block latestBlock = null; // latest block in the chain.
	private int hitCounter = 0; // Num. of mining results that match the target.
	private int maxMiningValues = 100; // Num. of values return to each client.
	private int difficultyBits = 1;	 // The difficulty bit.
	private long baseTarget = 2;
	private long nonce = 0;
	private BigInteger target;

	/**
	* Constructor
	*/
	Miner (Block block, int diffbits) {
		this.difficultyBits = diffbits;
		this.target = this.generatingTarget(this.difficultyBits);
		this.latestBlock = block;		

	}


	
	/**
	* A method for obtaining hash values from outside.
	* It requires mutual exclusive access.
	*/
	public synchronized ArrayList<Result> getHashValues() {
		return mining(this.latestBlock);
	}

	/**
	* obsolete
	public String createDigest() {
		return createDigest(this.latestBlock);
	}
	*/

	public static String createDigest(Block block) {
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
		if (latestBlock != null) {
			return latestBlock;
		}
		else {
			return null;
		}
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

	public int getDifficultyBits() {
		return this.difficultyBits;
	}

	private void incDifficultyBits() {
		this.difficultyBits++;
	}

	public Block createInitialBlock() {
		boolean flag = true;
		Block initBlock = new Block(1, 1, 0, 
									new Data("This is the first block in the chain."), 
									new BigInteger("0", 16), 
									new BigInteger("0", 16));
		while(flag) {
			ArrayList<Result> results = mining(initBlock);

			for(Result r : results) {
			//	System.out.println(r.getHashValue().toString(16));
				if(this.isHit(this.generatingTarget(r.getDifficultyBits()), r.getHashValue())){
			//	if(Miner.isHit(m.getTarget().toString(16), r.getHashValue().toString(16))){
					System.out.println("Result: "+r.getHashValue().toString(16));
					System.out.println("Nonce: "+r.getNonce());

					initBlock.setOwnHash(r.getHashValue());
					flag = false;
					break;
				}
			}
		}

		return initBlock;
	}

	public static String createHashedDigest(String digest, long nonce) {
		String result = DigestUtils.sha3_256Hex(digest+Long.toString(nonce));
		return result;
	}

	public static BigInteger generatingTarget(int diffbits) {
		BigInteger new_target = BigInteger.valueOf(2);
		new_target = new_target.pow(256-diffbits); // target should be 2^(256-diffbits)
//		System.out.println("New target: " + tgt.toString(16) + " diffbits: " + diffbits);
		return new_target;
	}

	public BigInteger getTarget() {
		return this.target;
	}

	private ArrayList<Result> mining(Block block) {
		ArrayList<Result> results = new ArrayList<Result>();
		String digest = this.createDigest(block);
		String result_string = null;
		BigInteger result;
		//long l_target = Math.pow(2, 256-this.difficultyBits);

		for(int count = 0; count < this.maxMiningValues; count++) {
			result_string = this.createHashedDigest(digest, nonce);
			result = new BigInteger(result_string, 16);
			results.add(new Result(result,nonce,getDifficultyBits()));
			if(target.compareTo(result) == 1){
				hitCounter++; // increment hitCounter whenever a result matches the requirement.
				if(hitCounter%10 == 0) {
					// increment difficltyBits every 10 hits.
					incDifficultyBits();
				}
				
			}

			nonce++;

		}

		return results;
	}

	// for judgeing the hash value is eligible or not according to the target.
	public static boolean isHit(BigInteger target, BigInteger hashValue) {
		if(target.compareTo(hashValue) == 1) {
			return true;
		}
		else {
			return false;
		}

	}

}