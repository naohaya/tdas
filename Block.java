import java.math.BigInteger;

public class Block
{

	/*
	* Block header
	*/
	private int blockNum = -1;
	private int difficultyBits = 0;
	private long nonce = -1;
	private BigInteger prevHash = null;
	private BigInteger ownHash = null;
	private String timestamp = null; // TODO: to be Date instead of String

	/*
	* Block content
	*/
	private Data data = null;


	public Block(){

	}

	public Block(int blockNum, int diffbits, long nonce, Data data, BigInteger prevHash, BigInteger ownHash, String ts) {
		this.blockNum = blockNum;
		this.difficultyBits = diffbits;
		this.nonce = nonce;
		this.data = data;
		this.prevHash = prevHash;
		this.ownHash = ownHash;
		this.timestamp = ts;
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

	public void setTimestamp(String ts) {
		this.timestamp = ts;
	}

	public String getTimestamp() { // return Date instead of String
		return this.timestamp;
	}
	
}
