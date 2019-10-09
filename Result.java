import java.math.BigInteger;

class Result {
	BigInteger result;
	long nonce;
	int difficultyBits;

	Result(BigInteger r, long n, int d) {
		this.result = r;
		this.nonce = n;
		this.difficultyBits = d;
	}

	public BigInteger getHashValue() {
		return this.result;
	}

	public long getNonce() {
		return this.nonce;
	}

	public int getDifficultyBits() {
		return this.difficultyBits;
	}
}