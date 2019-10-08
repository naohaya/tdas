import java.math.BigInteger;

class Result {
	BigInteger result;
	long nonce;

	Result(BigInteger r, long n) {
		this.result = r;
		this.nonce = n;
	}

	public BigInteger getHashValue() {
		return this.result;
	}

	public long getNonce() {
		return this.nonce;
	}
}