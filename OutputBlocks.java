import java.io.*;
import java.util.ArrayList;

public class OutputBlocks {
	ArrayList<Block> blockChain;
	int id;

	public OutputBlocks(int id, ArrayList<Block> bc) {
		this.blockChain = bc;
		this.id = id;
	}

	public void output() {
		String filename = "process" + this.id + ".log";
		try{
			File file = new File(filename);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Block block : blockChain) {
				bw.write(block.getBlockNum() + "," + block.getPrevHash().toString(16) + "," + block.getOwnHash().toString(16));
				bw.newLine();
			}
			bw.close();
		}
		catch(IOException ioe) {
			System.out.println(ioe);
		}

	}

}