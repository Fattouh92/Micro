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
	QueueOfArray multRs;
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
		loadRs = new QueueOfArray(loadRsSize,5);
		storeRs = new QueueOfArray(storeRsSize,5);
		addRs = new QueueOfArray(addRsSize,7);
		subRs = new QueueOfArray(subRsSize,7);
		nandRs = new QueueOfArray(nandRsSize,7);
		multRs = new QueueOfArray(mulRsSize,7);
		addiRs = new QueueOfArray(addiRsSize,5);
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
					String op = checkOp(ib.get(i,0));
					String[] regsVal = getRegs(op, ib.get(i,0));
					boolean valid = freeDest(regs, regTable, regsVal[0]);
					if (!rob.isFull() && valid){
						switch (op) { 
							
							case "load":
						        if (!loadRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid2){
						        		entry = new String[]{"Y", regsVal[1], "", Integer.toString(i), Integer.toString(loadCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", regsVal[1], Integer.toString(i), Integer.toString(loadCyc)};
						        	}
						        	loadRs.enqueue(entry);
						        }
								break;
							
							case "store":
								if (!storeRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid2){
						        		entry = new String[]{"Y", regsVal[1], "", Integer.toString(i), Integer.toString(storeCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", regsVal[1], Integer.toString(i), Integer.toString(storeCyc)};
						        	}
						        	storeRs.enqueue(entry);
						        }
								break;
							case "add":
								if (!addRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid3 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid2 && valid3){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(i), Integer.toString(addCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", regsVal[1], "", "", regsVal[2], Integer.toString(i), Integer.toString(addCyc)};
						        	}
						        	else if(valid3){
						        		entry = new String[]{"Y", "", regsVal[2], regsVal[1], "", Integer.toString(i), Integer.toString(addCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", regsVal[1], regsVal[2], Integer.toString(i), Integer.toString(addCyc)};						        	
						        	}
						        	addRs.enqueue(entry);
						        }
							  break;
							case "sub":
								if (!subRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid3 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid2 && valid3){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(i), Integer.toString(subCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", regsVal[1], "", "", regsVal[2], Integer.toString(i), Integer.toString(subCyc)};
						        	}
						        	else if(valid3){
						        		entry = new String[]{"Y", "", regsVal[2], regsVal[1], "", Integer.toString(i), Integer.toString(subCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", regsVal[1], regsVal[2], Integer.toString(i), Integer.toString(subCyc)};						        	
						        	}
						        	subRs.enqueue(entry);
						        }
							  break;
							case "nand":
								if (!nandRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid3 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid2 && valid3){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(i), Integer.toString(nandCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", regsVal[1], "", "", regsVal[2], Integer.toString(i), Integer.toString(nandCyc)};
						        	}
						        	else if(valid3){
						        		entry = new String[]{"Y", "", regsVal[2], regsVal[1], "", Integer.toString(i), Integer.toString(nandCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", regsVal[1], regsVal[2], Integer.toString(i), Integer.toString(nandCyc)};						        	
						        	}
						        	nandRs.enqueue(entry);
						        }
							  break;
							case "mult":
								if (!multRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid3 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid2 && valid3){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(i), Integer.toString(multCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", regsVal[1], "", "", regsVal[2], Integer.toString(i), Integer.toString(multCyc)};
						        	}
						        	else if(valid3){
						        		entry = new String[]{"Y", "", regsVal[2], regsVal[1], "", Integer.toString(i), Integer.toString(multCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", regsVal[1], regsVal[2], Integer.toString(i), Integer.toString(multCyc)};						        	
						        	}
						        	multRs.enqueue(entry);
						        }
							  break;
							case "addi":
								if (!addiRs.isFull()){
						        	ib.modify(i, 2, "issued");
						        	String[] robRec = {op, regsVal[0], "N"};
						        	rob.enqueue(robRec);
						        	tail++;
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = i;
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid2){
						        		entry = new String[]{"Y", regsVal[1], "", Integer.toString(i), Integer.toString(addiCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", regsVal[1], Integer.toString(i), Integer.toString(addiCyc)};
						        	}
						        	addiRs.enqueue(entry);
						        }
							  break;
							default:
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
	public boolean freeDest(ArrayList<Register> regs, int[] regTable, String destReg){
		return false;
	}
	public int getRegIndex(ArrayList<Register> regs, String destReg){
		return -1;
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
