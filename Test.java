import java.util.ArrayList;
import java.math.BigInteger;

class Test {
	public static void main(String [] args) {
		Data d = new Data("hogehogehoge");
		Block b = new Block(1, 1, d, new BigInteger("FFFFFFFF0", 16), new BigInteger("FFFFFFFF1", 16));
		Miner m = new Miner(b, 20);
		while(true) {
			ArrayList<Result> results = m.getHashValues();

			for(Result r : results) {
			//	System.out.println(r.getHashValue().toString(16));
				if(Miner.isHit(m.getTarget(), r.getHashValue())){
			//	if(Miner.isHit(m.getTarget().toString(16), r.getHashValue().toString(16))){
					System.out.println("Result: "+r.getHashValue().toString(16));
					System.out.println("Nonce: "+r.getNonce());
					System.out.println("Target: "+m.getTarget().toString(16));
					System.exit(0);
				}
			}

		}

	}
}