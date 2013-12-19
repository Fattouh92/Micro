import java.util.ArrayList;

public class TomasuloAlg {
	QueueOfArray ib;
	QueueOfArray rob;
	int head;
	int tail;
	QueueOfArray loadRs;
	QueueOfArray storeRs;
	QueueOfArray addRs;
	QueueOfArray subRs;
	QueueOfArray nandRs;
	QueueOfArray mulRs;
	QueueOfArray addiRs;
	int loadCyc;
	int storeCyc;
	int addCyc;
	int subCyc;
	int nandCyc;
	int multCyc;
	int addiCyc;
	int cycles;
	public TomasuloAlg(int ibSize, int loadRsSize, int storeRsSize, int addRsSize, int subRsSize, int nandRsSize, int mulRsSize, int addiRsSize, int robSize, int loadCyc, int storeCyc, int addCyc, int subCyc, int nandCyc, int multCyc, int addiCyc){
		ib = new QueueOfArray(ibSize,2);
		rob = new QueueOfArray(robSize,3);
		head = 0;
		tail = 0;
		loadRs = new QueueOfArray(loadRsSize,8);
		addRs = new QueueOfArray(addRsSize,8);
		storeRs = new QueueOfArray(storeRsSize,8);
		subRs = new QueueOfArray(subRsSize,8);
		nandRs = new QueueOfArray(nandRsSize,8);
		mulRs = new QueueOfArray(mulRsSize,8);
		addiRs = new QueueOfArray(addiRsSize,8);
		this.loadCyc = loadCyc;
		this.storeCyc = storeCyc;
		this.addCyc = addCyc;
		this.subCyc = subCyc;
		this.nandCyc = nandCyc;
		this.multCyc = multCyc;
		this.addiCyc = addiCyc;
		cycles = 0;
	}
	
	public void start(ArrayList<Register> regs, Cache cache, int pc){
		int[] regTable = new int[regs.size()];
		String ins = cache.getInstruction();
		while (!ins.equals("")){
			cycles++;
			String[] insa = {ins, ""};
			ib.enqueue(insa);
			for (int i = 0; i < ib.size(); i++){
				cycles++;
				
				if (ib.get(i,1).equals("")){
					if (!rob.isFull()){
						String op = checkOp(ib.get(i,0));
						switch (op) { 
							case "load":
						        if (!loadRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] regsVal = getRegs(op, ib.get(i,0));
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	
						        }
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
	public String[] getRegs(String op, String ins){
		String[] s = new String[3];
		//[dest reg, reg1, reg2//offset]
		return s;
	}
	public boolean tdFull(String[][] x){
		for(int j = 0; j < x.length; j++){
			if (x[j][0].equals("")){
				return false;
			}
		}
		return true;
	}
}
