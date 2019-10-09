import java.math.BigInteger;

public class Block
{

	private int blockNum = -1;
	private int difficultyBits = 0;
	private long nonce = -1;
	private Data data;
	private BigInteger prevHash;
	private BigInteger ownHash;

	public Block(int blockNum, int diffbits, long nonce, Data data, BigInteger prevHash, BigInteger ownHash) {
		this.blockNum = blockNum;
		this.difficultyBits = diffbits;
		this.nonce = nonce;
		this.data = data;
		this.prevHash = prevHash;
		this.ownHash = ownHash;
	}

	public int getBlockNum() {
		return this.blockNum;
	}

	public int getDifficultyBits() {
		return this.difficultyBits;
	}
	
	public Data getData() {
		return this.data;
	}

	public BigInteger getPrevHash() {
		return this.prevHash;
	}

	public BigInteger getOwnHash() {
		return this.ownHash;
	}

	public void setOwnHash(BigInteger ownhash) {
		this.ownHash = ownhash;
	}

	public void setNonce(long nonce) {
		this.nonce = nonce;
	}

	public long getNonce() {
		return this.nonce;
	}
	
}
