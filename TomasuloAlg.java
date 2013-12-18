import java.util.ArrayList;

public class TomasuloAlg {
	Queue ib;
	Queue rob;
	int robPointer;
	String[][] loadRs;
	String[][] storeRs;
	String[][] addRs;
	String[][] subRs;
	String[][] nandRs;
	String[][] mulRs;
	String[][] addiRs;
	Queue state;
	int loadCyc;
	int storeCyc;
	int addCyc;
	int subCyc;
	int nandCyc;
	int multCyc;
	int addiCyc;
	public TomasuloAlg(int ibSize, int loadRsSize, int storeRsSize, int addRsSize, int subRsSize, int nandRsSize, int mulRsSize, int addiRsSize, int robSize, int loadCyc, int storeCyc, int addCyc, int subCyc, int nandCyc, int multCyc, int addiCyc){
		ib = new Queue(ibSize);
		rob = new Queue(robSize);
		loadRs = new String[loadRsSize][8];
		addRs = new String[addRsSize][8];
		storeRs = new String[storeRsSize][8];
		subRs = new String[subRsSize][8];
		nandRs = new String[nandRsSize][8];
		mulRs = new String[mulRsSize][8];
		addiRs = new String[addiRsSize][8];
		state = new Queue(ibSize);
		this.loadCyc = loadCyc;
		this.storeCyc = storeCyc;
		this.addCyc = addCyc;
		this.subCyc = subCyc;
		this.nandCyc = nandCyc;
		this.multCyc = multCyc;
		this.addiCyc = addiCyc;
	}
	
	public void start(ArrayList<Register> regs, String[] ins){
	
	}
}
