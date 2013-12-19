import java.util.ArrayList;

public class TomasuloAlg {
	Queue ib;
	String[] rob;
	int head;
	int tail;
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
	int cycles;
	public TomasuloAlg(int ibSize, int loadRsSize, int storeRsSize, int addRsSize, int subRsSize, int nandRsSize, int mulRsSize, int addiRsSize, int robSize, int loadCyc, int storeCyc, int addCyc, int subCyc, int nandCyc, int multCyc, int addiCyc){
		ib = new Queue(ibSize);
		rob = new String[robSize];
		head = 0;
		tail = 0;
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
		cycles = 0;
	}
	
	public void start(ArrayList<Register> regs, Cache cache){
		int[] regTable = new int[regs.size()];
		String ins = cache.getInstruction();
		while (!ins.equals("")){
			cycles++;
			ib.enqueue(ins);
			for (int i = 0; i < ib.size(); i++){
				cycles++;
				if (state.get(i).equals("")){
					state.enqueue("issue");
					
				}
				
				
				String op = checkOp(ins);
				switch (op) { 
					case "load":
				        //statements;
						break;
					case "store":
				        //statements;
						break;
					case "add":
				        //statements;
					  break;
					case "sub":
				        //statements;
					  break;
					case "nand":
				        //statements;
					  break;
					case "mult":
				        //statements;
					  break;
					case "addi":
				        //statements;
					  break;
					default:
					  //statements;
						break;
				}
			}
			
			if (!ib.isFull()){
				ins = cache.getInstruction();
			}
		}
	}
	
	public String checkOp(String ins){
		return "";
	}
}
