import java.util.ArrayList;

public class TomasuloAlg {
	QueueOfArray ib;
	QueueOfArray rob;
	QueueOfArray loadStoreRs;
	QueueOfArray addSubRs;
	QueueOfArray nandRs;
	QueueOfArray multRs;
	QueueOfArray addiRs;
	int loadStoreCyc;
	int addSubCyc;
	int nandCyc;
	int multCyc;
	int addiCyc;
	int cycles;
	public TomasuloAlg(int ibSize, int loadStoreRsSize, int addSubRsSize, int nandRsSize, int multRsSize, int addiRsSize, int robSize, int loadStoreCyc, int addSubCyc, int nandCyc, int multCyc, int addiCyc){
		ib = new QueueOfArray(ibSize,3);
		rob = new QueueOfArray(robSize,4);
		loadStoreRs = new QueueOfArray(loadStoreRsSize,5);
		addSubRs = new QueueOfArray(addSubRsSize,7);
		nandRs = new QueueOfArray(nandRsSize,7);
		multRs = new QueueOfArray(multRsSize,7);
		addiRs = new QueueOfArray(addiRsSize,5);
		this.loadStoreCyc = loadStoreCyc;
		this.addSubCyc = addSubCyc;
		this.nandCyc = nandCyc;
		this.multCyc = multCyc;
		this.addiCyc = addiCyc;
		cycles = 0;
	}
	
	public void start(ArrayList<Register> regs, ICache iCache, Cache cache, Memory memory, Register pc){
	//public void start(ArrayList<Register> regs, String[] mem, Register pc){
		Units fu = new Units(cache);
		int[] regTable = new int[regs.size()];
		for (int tempCounter=0; tempCounter < regTable.length; tempCounter++) {
			regTable[tempCounter] = -1;
		}
		String ins = memory.readData(Integer.toBinaryString(pc.getValue()));
		iCache.read(Integer.toBinaryString(pc.getValue()));
		//String ins = mem[pc.getValue()];
		boolean addIns = true;
		while (!ins.equals("")){
			if (addIns){
				cycles++;
				String[] insa = {ins, "", ""};
				ib.enqueue(insa);
				/*
				System.out.println("cycles: " + cycles);
				System.out.println("ib: ");
				printing(ib, 3);
				*/
			}
			cycles++;
			for (int i = 0; i < ib.size(); i++){
				
				if (ib.get(i,1).equals("")){
					String op = checkOp(ib.get(i,0));
					String[] regsVal = getRegs(op, ib.get(i,0));
					if (!rob.isFull()){
						switch (op) { 
							
							case "load":
						        if (!loadStoreRs.isFull()){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", regsVal[1], "", Integer.toString(rob.rear), Integer.toString(loadStoreCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.rear), Integer.toString(loadStoreCyc)};
						        	}
						        	loadStoreRs.enqueue(entry);
						        }
								break;
							
							case "store":
								if (!loadStoreRs.isFull()){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[1] + " " + regsVal[2], "S"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[1]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[0]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", regsVal[0], "", Integer.toString(rob.rear), Integer.toString(loadStoreCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(rob.rear), Integer.toString(loadStoreCyc)};
						        	}
						        	loadStoreRs.enqueue(entry);
						        }
								break;
							case "add":
							case "sub":
								if (!addSubRs.isFull()){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(rob.rear), Integer.toString(addSubCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", regsVal[1], "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(addSubCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", regsVal[2], Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.rear) , Integer.toString(addSubCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(addSubCyc)};
						        	}
						        	addSubRs.enqueue(entry);
						        }
							  break;
							case "nand":
								if (!nandRs.isFull()){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(rob.rear), Integer.toString(nandCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", regsVal[1], "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(nandCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", regsVal[2], Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.rear) , Integer.toString(nandCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(nandCyc)};
						        	}
						        	nandRs.enqueue(entry);
						        }
							  break;
							case "mult":
								if (!multRs.isFull()){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", regsVal[1], regsVal[2], "", "", Integer.toString(rob.rear), Integer.toString(multCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", regsVal[1], "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(multCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", regsVal[2], Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.rear) , Integer.toString(multCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.rear) , Integer.toString(multCyc)};
						        	}
						        	multRs.enqueue(entry);
						        }
							  break;
							case "addi":
								if (!addiRs.isFull()){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.rear));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.rear;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", regsVal[1], "", Integer.toString(rob.rear), Integer.toString(addiCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.rear), Integer.toString(addiCyc)};
						        	}
						        	addiRs.enqueue(entry);
						        }
							  break;
							default:
							  break;
						}
					}
				}
				
				else if(ib.get(i,1).equals("issued")){
					String op = checkOp(ib.get(i,0));
					int index = 0;
					switch (op) {					
						case "load":
						case "store":
							for (int k = 0; k < loadStoreRs.size(); k++){
								if (loadStoreRs.get(k, 3).equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (loadStoreRs.get(index, 4).equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (loadStoreRs.get(index, 2).equals("")){
								int rem = Integer.parseInt(loadStoreRs.get(index, 4));
								rem--;
								loadStoreRs.modify(index, 4, Integer.toString(rem));
							}
							else{
								if (!rob.get(Integer.parseInt(loadStoreRs.get(index, 2)), 0).equals("")){
									loadStoreRs.modify(index, 1, loadStoreRs.get(index, 2));
									loadStoreRs.modify(index, 2, "");
								}
							}
							break;
						case "add":
						case "sub":
							for (int k = 0; k < addSubRs.size(); k++){
								if (addSubRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (addSubRs.get(index, 6).equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (addSubRs.get(index, 3).equals("") && addSubRs.get(index, 4).equals("")){
								int rem = Integer.parseInt(addSubRs.get(index, 6));
								rem--;
								addSubRs.modify(index, 6, Integer.toString(rem));
							}
							else{
								if (addSubRs.get(index, 1).equals("")){
									if (!rob.get(Integer.parseInt(addSubRs.get(index, 3)), 0).equals("")){
										addSubRs.modify(index, 1, addSubRs.get(index, 3));
										addSubRs.modify(index, 3, "");
									}
								}
								if (addSubRs.get(index, 2).equals("")){
									if (!rob.get(Integer.parseInt(addSubRs.get(index, 4)), 0).equals("")){
										addSubRs.modify(index, 2, addSubRs.get(index, 4));
										addSubRs.modify(index, 4, "");
									}
								}
							}
							break;
						case "nand":
							for (int k = 0; k < nandRs.size(); k++){
								if (nandRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (nandRs.get(index, 6).equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (nandRs.get(index, 3).equals("") && nandRs.get(index, 4).equals("")){
								int rem = Integer.parseInt(nandRs.get(index, 6));
								rem--;
								nandRs.modify(index, 6, Integer.toString(rem));
							}
							else{
								if (nandRs.get(index, 1).equals("")){
									if (!rob.get(Integer.parseInt(nandRs.get(index, 3)), 0).equals("")){
										nandRs.modify(index, 1, nandRs.get(index, 3));
										nandRs.modify(index, 3, "");
									}
								}
								if (nandRs.get(index, 2).equals("")){
									if (!rob.get(Integer.parseInt(nandRs.get(index, 4)), 0).equals("")){
										nandRs.modify(index, 2, nandRs.get(index, 4));
										nandRs.modify(index, 4, "");
									}
								}
							}
							break;
						case "mult":
							for (int k = 0; k < multRs.size(); k++){
								if (multRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (multRs.get(index, 6).equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (multRs.get(index, 3).equals("") && multRs.get(index, 4).equals("")){
								int rem = Integer.parseInt(multRs.get(index, 6));
								rem--;
								multRs.modify(index, 6, Integer.toString(rem));
							}
							else{
								if (multRs.get(index, 1).equals("")){
									if (!rob.get(Integer.parseInt(multRs.get(index, 3)), 0).equals("")){
										multRs.modify(index, 1, multRs.get(index, 3));
										multRs.modify(index, 3, "");
									}
								}
								if (multRs.get(index, 2).equals("")){
									if (!rob.get(Integer.parseInt(multRs.get(index, 4)), 0).equals("")){
										multRs.modify(index, 2, multRs.get(index, 4));
										multRs.modify(index, 4, "");
									}
								}
							}
							break;
						case "addi":
							for (int k = 0; k < addiRs.size(); k++){
								if (addiRs.get(k, 3).equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (addiRs.get(index, 4).equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (addiRs.get(index, 2).equals("")){
								int rem = Integer.parseInt(addiRs.get(index, 4));
								rem--;
								addiRs.modify(index, 4, Integer.toString(rem));
							}
							else{
								if (!rob.get(Integer.parseInt(addiRs.get(index, 2)), 0).equals("")){
									addiRs.modify(index, 1, addiRs.get(index, 2));
									addiRs.modify(index, 2, "");
								}
							}
							break;
						default:
							break;
					}
				}
				
				else if (ib.get(i,1).equals("executed")){
					String op = checkOp(ib.get(i,0));
					String[] regsVal = getRegs(op, ib.get(i,0));
					int index = 0;
					int result;
					switch (op) {					
						case "load":
						case "store":
							for (int k = 0; k < loadStoreRs.size(); k++){
								if (loadStoreRs.get(k, 3).equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.LoadStore(op, memory, getRegOrg(regs, regsVal[0]), getRegOrg(regs, regsVal[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(loadStoreRs.get(index, 3)), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(loadStoreRs.get(index, 3)), 1, "Y");
							loadStoreRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "add":
						case "sub":
							for (int k = 0; k < addSubRs.size(); k++){
								if (addSubRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.AddSub(op, getRegOrg(regs, regsVal[0]), getRegOrg(regs, regsVal[1]), getRegOrg(regs, regsVal[2]));
							rob.modify(Integer.parseInt(addSubRs.get(index, 5)), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(addSubRs.get(index, 5)), 1, "Y");
							addSubRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "nand":
							for (int k = 0; k < nandRs.size(); k++){
								if (nandRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.NAND(getRegOrg(regs, regsVal[0]), getRegOrg(regs, regsVal[1]), getRegOrg(regs, regsVal[2]));
							rob.modify(Integer.parseInt(nandRs.get(index, 5)), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(nandRs.get(index, 5)), 1, "Y");
							nandRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "mult":
							for (int k = 0; k < multRs.size(); k++){
								if (multRs.get(k, 5).equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Multiply(getRegOrg(regs, regsVal[0]), getRegOrg(regs, regsVal[1]), getRegOrg(regs, regsVal[2]));
							rob.modify(Integer.parseInt(multRs.get(index, 5)), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(multRs.get(index, 5)), 1, "Y");
							multRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "addi":
							for (int k = 0; k < addiRs.size(); k++){
								if (addiRs.get(k, 3).equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Addi(getRegOrg(regs, regsVal[0]), getRegOrg(regs, regsVal[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(addiRs.get(index, 3)), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(addiRs.get(index, 3)), 1, "Y");
							addiRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						default:
							break;
					}
				}
				
			}
			
			if (rob.peek()[1].equals("Y")){
				if (rob.peek()[3].equals("O")){
					fu.CommitOP(Integer.parseInt(rob.peek()[0]), getRegOrg(regs, rob.peek()[2]));
				}
				else{
					String address = rob.peek()[2].split(" ")[0];
					String offset = rob.peek()[2].split(" ")[1];
					fu.CommitStore(memory, Integer.parseInt(rob.peek()[0]), getRegOrg(regs, address), Integer.parseInt(offset));
				}
				rob.dequeue();
				ib.dequeue();
			}
			
/*
			System.out.println("cycles: " + cycles);
			System.out.println("ib: ");
			printing(ib, 3);
			System.out.println("rob: ");
			printing(rob, 2);
			System.out.println("load rs: ");
			printing(loadRs, 5);
			System.out.println("mult rs: ");
			printing(multRs, 7);
			System.out.println("sub rs: ");
			printing(subRs, 7);
			System.out.println("nand rs: ");
			printing(nandRs, 7);
			System.out.println("add rs: ");
			printing(addRs, 7);
			System.out.println("reg table: " + regTable);
*/			
			
			if (!ib.isFull()){
				pc.setValue((pc.getValue() + 1));
				ins = memory.readData(Integer.toBinaryString(pc.getValue()));
				iCache.read(Integer.toBinaryString(pc.getValue()));
				//ins = mem[pc.getValue()];
				addIns = true;
			}
			else{
				addIns = false;
			}
			
		}
	}
	
	public String checkOp(String ins){
		return ins.split(" ")[0];
	}
	public String[] getRegs(String op, String ins){
		String[] s = new String[3];
		String[] split = ins.split(" ");
		s[0] = split[1];
		s[1] = split[2];
		s[2] = split[3];
		return s;
	}
	public boolean freeDest(ArrayList<Register> regs, int[] regTable, String destReg){
		int access = this.getRegIndex(regs, destReg);
		if (access == -1){
			return false;
		}
		if (regTable[access] == -1) {
			return true;
		}
		return false;
	}
	
	public int getRegIndex(ArrayList<Register> regs, String destReg){
		for(int tempI =0; tempI<regs.size(); tempI++) {
			String tempR = regs.get(tempI).name;
			if (tempR.equals(destReg)) {
				return tempI;
			}
		}
		return -1;
	}
	public Register getRegOrg(ArrayList<Register> regs, String reg){
		for(int tempI =0; tempI<regs.size(); tempI++) {
			String tempR = regs.get(tempI).name;
			if (tempR.equals(reg)) {
				return regs.get(tempI);
			}
		}
		return null;
	}
	/*
	public void printing(QueueOfArray x, int width){
		for (int i = 0; i< x.size(); i++){
			for (int j =0; j < width; j++){
				System.out.print(x.get(i, j) + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String [] args){
		TomasuloAlg t = new TomasuloAlg(6, 2, 0, 2, 2, 2, 2, 0, 6, 1, 1, 2, 2, 12, 6, 2);
		Register F6 = new Register("F6", 1);
		Register R2 = new Register("R2", 1);
		Register F2 = new Register("F2", 1);
		Register R3 = new Register("R3", 1);
		Register F0 = new Register("F0", 1);
		Register F4 = new Register("F4", 1);
		Register F8 = new Register("F8", 1);
		Register F10 = new Register("F10", 1);
		Register pc = new Register("pc", 0);
		ArrayList<Register> regs = new ArrayList<Register>();
		regs.add(F6);
		regs.add(R2);
		regs.add(F2);
		regs.add(R3);
		regs.add(F0);
		regs.add(F4);
		regs.add(F8);
		regs.add(F10);
		String[] mem = {"load F6 R2 32", "load F2 R3 44", "mult F0 F2 F4", "sub F8 F2 F6", "nand F10 F0 F6", "add F6 F8 F2", ""};
		t.start(regs, mem, pc);
	}
	*/
}
