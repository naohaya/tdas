import java.math.BigInteger;

public class Block
{

	private int blockNum = -1;
	private int difficultyBits = 0;
	private long nonce = -1;
	private Data data = null;
	private BigInteger prevHash = null;
	private BigInteger ownHash = null;

	public Block(){

	}

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

	public void setBlockNum(int bnum) {
		this.blockNum = bnum;
	}

	public int getDifficultyBits() {
		return this.difficultyBits;
	}

	public void setDifficultyBits(int diffbits) {
		this.difficultyBits = diffbits;
	}
	
	public Data getData() {
		return this.data;
	}

	public void setData(Data d) {
		this.data = d;
	}

	public BigInteger getPrevHash() {
		return this.prevHash;
	}

	public void setPrevHash(BigInteger prevhash) {
		this.prevHash = prevhash;
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
