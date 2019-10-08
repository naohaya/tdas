import java.math.BigInteger;

public class Block
{

	private int blockNum = -1;
	private long nonce = -1;
	private Data data;
	private BigInteger prevHash;
	private BigInteger ownHash;

	public Block(int blockNum, long nonce, Data data, BigInteger prevHash, BigInteger ownHash) {
		this.blockNum = blockNum;
		this.nonce = nonce;
		this.data = data;
		this.prevHash = prevHash;
		this.ownHash = ownHash;
	}

	public int getBlockNum() {
		return this.blockNum;
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

	public long getNonce() {
		return this.nonce;
	}
	
}
