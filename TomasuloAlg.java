import java.util.ArrayList;

public class TomasuloAlg {
	QueueOfArray ib;
	QueueOfArray rob;
	ArrayList<String[]> loadStoreRs;
	int loadStoreRsSize;
	ArrayList<String[]> addSubRs;
	int addSubRsSize;
	ArrayList<String[]> nandRs;
	int nandRsSize;
	ArrayList<String[]> multRs;
	int multRsSize;
	ArrayList<String[]> addiRs;
	int addiRsSize;
	int loadStoreCyc;
	int addSubCyc;
	int nandCyc;
	int multCyc;
	int addiCyc;
	int cycles;
	public TomasuloAlg(int ibSize, int loadStoreRsSize, int addSubRsSize, int nandRsSize, int multRsSize, int addiRsSize, int robSize, int loadStoreCyc, int addSubCyc, int nandCyc, int multCyc, int addiCyc){
		ib = new QueueOfArray(ibSize,3);
		rob = new QueueOfArray(robSize,4);
		loadStoreRs = new ArrayList<String[]>();
		this.loadStoreRsSize = loadStoreRsSize;
		addSubRs = new ArrayList<String[]>();
		this.addSubRsSize = addSubRsSize;
		nandRs = new ArrayList<String[]>();
		this.nandRsSize = nandRsSize;
		multRs = new ArrayList<String[]>();
		this.multRsSize = multRsSize;
		addiRs = new ArrayList<String[]>();
		this.addiRsSize = addiRsSize;
		this.loadStoreCyc = loadStoreCyc;
		this.addSubCyc = addSubCyc;
		this.nandCyc = nandCyc;
		this.multCyc = multCyc;
		this.addiCyc = addiCyc;
		cycles = 0;
	}
	
	//public void start(ArrayList<Register> regs, ICache iCache, Cache cache, Memory memory, Register pc){
	public void start(ArrayList<Register> regs, String[] mem, Register pc){
		//Units fu = new Units(cache);
		Units fu = new Units();
		Memory memory = new Memory();
		int[] regTable = new int[regs.size()];
		for (int tempCounter=0; tempCounter < regTable.length; tempCounter++) {
			regTable[tempCounter] = -1;
		}
		//String ins = memory.readData(Integer.toBinaryString(pc.getValue()));
		//iCache.read(Integer.toBinaryString(pc.getValue()));
		String ins = mem[pc.getValue()];
		boolean addIns = true;
		int counter = 30;
		//while (!(ins.equals("") && rob.isEmpty())){
		while (counter > 0){
			cycles++;
			
			if (!ib.isEmpty()){
				if(ib.peek()[1].equals("commited")){
					rob.dequeue();
					ib.dequeue();
					if (!loadStoreRs.isEmpty()){
						for(int i=0;i<loadStoreRs.size();i++){
							String[] temp = loadStoreRs.get(i);
							int rem = Integer.parseInt(temp[3]);
							rem--;
							temp[3] = Integer.toString(rem);
							loadStoreRs.set(i, temp);
						}
					}
					if (!addSubRs.isEmpty()){
						for(int i=0;i<addSubRs.size();i++){
							String[] temp = addSubRs.get(i);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							addSubRs.set(i, temp);
						}
					}
					if (!nandRs.isEmpty()){
						for(int i=0;i<nandRs.size();i++){
							String[] temp = nandRs.get(i);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							nandRs.set(i, temp);
						}
					}
					if (!multRs.isEmpty()){
						for(int i=0;i<multRs.size();i++){
							String[] temp = multRs.get(i);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							multRs.set(i, temp);
						}
					}
					if (!addiRs.isEmpty()){
						for(int i=0;i<addiRs.size();i++){
							String[] temp = addiRs.get(i);
							int rem = Integer.parseInt(temp[3]);
							rem--;
							temp[3] = Integer.toString(rem);
							addiRs.set(i, temp);
						}
					}
				}
			}
			
			if (!rob.isEmpty()){
				if (rob.peek()[1].equals("Y")){
					if (rob.peek()[3].equals("O")){
						fu.CommitOP(Integer.parseInt(rob.peek()[0]), getRegOrg(regs, rob.peek()[2]));
					}
					else{
						String[] split =  rob.peek()[2].split(" ");
						String address = split[0];
						String offset = split[1];
						fu.CommitStore(memory, Integer.parseInt(rob.peek()[0]), getRegOrg(regs, address), Integer.parseInt(offset));
					}
					ib.modify(0, 1, "commited");
				}
			}
			for (int i = 0; i < ib.size(); i++){
				
				if (ib.get(i,1).equals("")){
					String op = checkOp(ib.get(i,0));
					String[] regsVal = getRegs(op, ib.get(i,0));
					if (!rob.isFull()){
						switch (op) { 
							
							case "load":
						        if (loadStoreRs.size()<loadStoreRsSize){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", Integer.toString(rob.nItems - 1), Integer.toString(loadStoreCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), Integer.toString(loadStoreCyc)};
						        	}
						        	loadStoreRs.add(entry);
						        }
								break;
							
							case "store":
								if (loadStoreRs.size()<loadStoreRsSize){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[1] + " " + regsVal[2], "S"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[1]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[0]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), "", Integer.toString(rob.nItems - 1), Integer.toString(loadStoreCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(rob.nItems - 1), Integer.toString(loadStoreCyc)};
						        	}
						        	loadStoreRs.add(entry);
						        }
								break;
							case "add":
							case "sub":
								if (addSubRs.size() < addSubRsSize){
						        	ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), "", "", Integer.toString(rob.nItems - 1), Integer.toString(addSubCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(addSubCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.nItems - 1) , Integer.toString(addSubCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(addSubCyc)};
						        	}
						        	addSubRs.add(entry);
						        }
							  break;
							case "nand":
								if (nandRs.size()<nandRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), "", "", Integer.toString(rob.nItems - 1), Integer.toString(nandCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(nandCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.nItems - 1) , Integer.toString(nandCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(nandCyc)};
						        	}
						        	nandRs.add(entry);
						        }
							  break;
							case "mult":
								if (multRs.size()<multRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[2]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), "", "", Integer.toString(rob.nItems - 1), Integer.toString(multCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(multCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", Integer.toString(getRegOrg(regs, regsVal[2]).getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), "", Integer.toString(rob.nItems - 1) , Integer.toString(multCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(regTable[getRegIndex(regs, regsVal[2])]), Integer.toString(rob.nItems - 1) , Integer.toString(multCyc)};
						        	}
						        	multRs.add(entry);
						        }
							  break;
							case "addi":
								if (addiRs.size()<addiRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", Integer.toString(rob.nItems - 1), Integer.toString(addiCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), Integer.toString(addiCyc)};
						        	}
						        	addiRs.add(entry);
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
								if (loadStoreRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (loadStoreRs.get(index)[4].equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (loadStoreRs.get(index)[2].equals("")){
								String[] temp = loadStoreRs.get(index);
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
								loadStoreRs.set(index, temp);
							}
							else{
								if (!rob.get(Integer.parseInt(loadStoreRs.get(index)[2]), 0).equals("")){
									String[] temp = loadStoreRs.get(index);
									temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(loadStoreRs.get(index)[2]), 2)).getValue());
									temp[2] = "";
									loadStoreRs.set(index, temp);
								}
							}
							break;
						case "add":
						case "sub":
							for (int k = 0; k < addSubRs.size(); k++){
								if (addSubRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (addSubRs.get(index)[6].equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (addSubRs.get(index)[3].equals("") && addSubRs.get(index)[4].equals("")){
								String[] temp = addSubRs.get(index);
								int rem = Integer.parseInt(temp[6]);
								rem--;
								temp[6] = Integer.toString(rem);
								addSubRs.set(index, temp);
							}
							else{
								if (addSubRs.get(index)[1].equals("")){
									if (!rob.get(Integer.parseInt(addSubRs.get(index)[3]), 0).equals("")){
										String[] temp = addSubRs.get(index);
										temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(addSubRs.get(index)[3]), 2)).getValue());
										temp[3] = "";
										addSubRs.set(index, temp);
									}
								}
								if (addSubRs.get(index)[2].equals("")){
									if (!rob.get(Integer.parseInt(addSubRs.get(index)[4]), 0).equals("")){
										String[] temp = addSubRs.get(index);
										temp[2] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(addSubRs.get(index)[4]), 2)).getValue());
										temp[4] = "";
										addSubRs.set(index, temp);
									}
								}
							}
							break;
						case "nand":
							for (int k = 0; k < nandRs.size(); k++){
								if (nandRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (nandRs.get(index)[6].equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (nandRs.get(index)[3].equals("") && nandRs.get(index)[4].equals("")){
								String[] temp = nandRs.get(index);
								int rem = Integer.parseInt(temp[6]);
								rem--;
								temp[6] = Integer.toString(rem);
								nandRs.set(index, temp);
							}
							else{
								if (nandRs.get(index)[1].equals("")){
									if (!rob.get(Integer.parseInt(nandRs.get(index)[3]), 0).equals("")){
										String[] temp = nandRs.get(index);
										temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(nandRs.get(index)[3]), 2)).getValue());
										temp[3] = "";
										nandRs.set(index, temp);
									}
								}
								if (nandRs.get(index)[2].equals("")){
									if (!rob.get(Integer.parseInt(nandRs.get(index)[4]), 0).equals("")){
										String[] temp = nandRs.get(index);
										temp[2] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(nandRs.get(index)[4]), 2)).getValue());
										temp[4] = "";
										nandRs.set(index, temp);
									}
								}
							}
							break;
						case "mult":
							for (int k = 0; k < multRs.size(); k++){
								if (multRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (multRs.get(index)[6].equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (multRs.get(index)[3].equals("") && multRs.get(index)[4].equals("")){
								String[] temp = multRs.get(index);
								int rem = Integer.parseInt(temp[6]);
								rem--;
								temp[6] = Integer.toString(rem);
								multRs.set(index, temp);
							}
							else{
								if (multRs.get(index)[1].equals("")){
									if (!rob.get(Integer.parseInt(multRs.get(index)[3]), 0).equals("")){
										String[] temp = multRs.get(index);
										temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(multRs.get(index)[3]), 2)).getValue());
										temp[3] = "";
										multRs.set(index, temp);
									}
								}
								if (multRs.get(index)[2].equals("")){
									if (!rob.get(Integer.parseInt(multRs.get(index)[4]), 0).equals("")){
										String[] temp = multRs.get(index);
										temp[2] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(multRs.get(index)[4]), 2)).getValue());
										temp[4] = "";
										multRs.set(index, temp);
									}
								}
							}
							break;
						case "addi":
							for (int k = 0; k < addiRs.size(); k++){
								if (addiRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (addiRs.get(index)[4].equals(Integer.toString(0))){
								ib.modify(i, 1, "executed");
							}
							else if (addiRs.get(index)[2].equals("")){
								String[] temp = addiRs.get(index);
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
								addiRs.set(index, temp);
							}
							else{
								if (!rob.get(Integer.parseInt(addiRs.get(index)[2]), 0).equals("")){
									String[] temp = addiRs.get(index);
									temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(addiRs.get(index)[2]), 2)).getValue());
									temp[2] = "";
									addiRs.set(index, temp);
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
							for (int k = 0; k < loadStoreRs.size(); k++){
								if (loadStoreRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Load(memory, getRegOrg(regs, regsVal[0]), Integer.parseInt(loadStoreRs.get(index)[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(loadStoreRs.get(index)[3]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(loadStoreRs.get(index)[3]), 1, "Y");
							loadStoreRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "store":
							for (int k = 0; k < loadStoreRs.size(); k++){
								if (loadStoreRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Store(memory, Integer.parseInt(loadStoreRs.get(index)[1]), getRegOrg(regs, regsVal[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(loadStoreRs.get(index)[3]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(loadStoreRs.get(index)[3]), 1, "Y");
							loadStoreRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "add":
						case "sub":
							for (int k = 0; k < addSubRs.size(); k++){
								if (addSubRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
									System.out.println(index);
								}
							}
							result = fu.AddSub(op, getRegOrg(regs, regsVal[0]), Integer.parseInt(addSubRs.get(index)[1]), Integer.parseInt(addSubRs.get(index)[2]));
							rob.modify(Integer.parseInt(addSubRs.get(index)[5]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(addSubRs.get(index)[5]), 1, "Y");
							addSubRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "nand":
							for (int k = 0; k < nandRs.size(); k++){
								if (nandRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.NAND(getRegOrg(regs, regsVal[0]), Integer.parseInt(nandRs.get(index)[1]), Integer.parseInt(nandRs.get(index)[2]));
							System.out.println("nand index: " + index + " " + result + " " + nandRs.get(index)[5]);
							rob.modify(Integer.parseInt(nandRs.get(index)[5]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(nandRs.get(index)[5]), 1, "Y");
							nandRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "mult":
							for (int k = 0; k < multRs.size(); k++){
								if (multRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Multiply(getRegOrg(regs, regsVal[0]), Integer.parseInt(multRs.get(index)[1]), Integer.parseInt(multRs.get(index)[2]));
							rob.modify(Integer.parseInt(multRs.get(index)[5]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(multRs.get(index)[5]), 1, "Y");
							multRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "addi":
							for (int k = 0; k < addiRs.size(); k++){
								if (addiRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Addi(getRegOrg(regs, regsVal[0]), Integer.parseInt(addiRs.get(index)[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(addiRs.get(index)[3]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(addiRs.get(index)[3]), 1, "Y");
							addiRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						default:
							break;
					}
				}
				
			}
			
			
			
			
			if (addIns){
				//cycles++;
				String[] insa = {ins, "", ""};
				ib.enqueue(insa);
				
				//System.out.println("cycles: " + cycles);
				//System.out.println("ib: ");
				//printing(ib, 3);
				
			}
			

			System.out.println("cycles: " + cycles);
			System.out.println("ib: ");
			printing(ib, 3);
			System.out.println("rob: ");
			printing(rob, 4);
			System.out.println("load/store rs: ");
			printingA(loadStoreRs, 5);
			System.out.println("mult rs: ");
			printingA(multRs, 7);
			System.out.println("add/sub rs: ");
			printingA(addSubRs, 7);
			System.out.println("nand rs: ");
			printingA(nandRs, 7);
			System.out.println();
			
			
			if (!ib.isFull() && addIns){
				pc.setValue((pc.getValue() + 1));
				//ins = memory.readData(Integer.toBinaryString(pc.getValue()));
				//iCache.read(Integer.toBinaryString(pc.getValue()));
				ins = mem[pc.getValue()];
				if (!ins.equals("")){
					addIns = true;
				}
				else{
					addIns = false;
				}
			}
			else{
				addIns = false;
			}
			counter--;
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
	
	public void printing(QueueOfArray x, int width){
		for (int i = 0; i< x.size(); i++){
			for (int j =0; j < width; j++){
				System.out.print(x.get(i, j) + " ");
			}
			System.out.println();
		}
	}
	public void printingA(ArrayList<String[]> x, int width){
		for (int i = 0; i< x.size(); i++){
			for (int j =0; j < width; j++){
				System.out.print(x.get(i)[j] + " ");
			}
			System.out.println();
		}
	}
	public void printingB(String[] x, int width){
		for (int i =0;i<width;i++){
			System.out.print(x[i] + " ");
		}
		System.out.println();
	}
	public static void main(String [] args){
		TomasuloAlg t = new TomasuloAlg(8, 5, 2, 2, 2, 0, 8, 1, 2, 12, 6, 2);
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
		String[] mem = {"mult F0 F2 F4", "sub F8 F2 F6", "nand F10 F0 F6", "add F6 F8 F2", ""};
		t.start(regs, mem, pc);
	}
	
}
