import java.util.ArrayList;

public class TomasuloAlg {
	Cache cache;
	boolean flushFetch = false;
	QueueOfArray ib;
	QueueOfArray rob;
	ArrayList<String[]> loadRs;
	int loadRsSize;
	ArrayList<String[]> storeRs;
	int storeRsSize;
	ArrayList<String[]> addSubRs;
	int addSubRsSize;
	ArrayList<String[]> nandRs;
	int nandRsSize;
	ArrayList<String[]> multRs;
	int multRsSize;
	ArrayList<String[]> addiRs;
	int addiRsSize;
	ArrayList<String[]> jmpJalrRetRs;
	int jmpJalrRetRsSize;
	ArrayList<String[]> beqRs;
	int timeForFetch;
	int beqRsSize;
	int storeCyc;
	int addSubCyc;
	int nandCyc;
	int multCyc;
	int addiCyc;
	int jmpJalrRetCyc;
	int beqCyc;
	int cycles;
	boolean canWrite;
	boolean iBreak;
	public TomasuloAlg(int ibSize, int loadRsSize, int storeRsSize, int addSubRsSize, int nandRsSize, int multRsSize, int addiRsSize, int jmpJalrRetRsSize, int beqRsSize, int robSize, int storeCyc, int addSubCyc, int nandCyc, int multCyc, int addiCyc, int jmpJalrRetCyc, int beqCyc){
		ib = new QueueOfArray(ibSize,3);
		rob = new QueueOfArray(robSize,4);
		loadRs = new ArrayList<String[]>();
		this.loadRsSize = loadRsSize;
		storeRs = new ArrayList<String[]>();
		this.storeRsSize = storeRsSize;
		addSubRs = new ArrayList<String[]>();
		this.addSubRsSize = addSubRsSize;
		nandRs = new ArrayList<String[]>();
		this.nandRsSize = nandRsSize;
		multRs = new ArrayList<String[]>();
		this.multRsSize = multRsSize;
		addiRs = new ArrayList<String[]>();
		this.addiRsSize = addiRsSize;
		jmpJalrRetRs = new ArrayList<String[]>();
		this.jmpJalrRetRsSize = jmpJalrRetRsSize;
		beqRs = new ArrayList<String[]>();
		this.beqRsSize = beqRsSize;
		this.storeCyc = storeCyc;
		this.addSubCyc = addSubCyc;
		this.nandCyc = nandCyc;
		this.multCyc = multCyc;
		this.addiCyc = addiCyc;
		this.jmpJalrRetCyc = jmpJalrRetCyc;
		this.beqCyc = beqCyc;
		cycles = -1;
		canWrite = true;
		iBreak = false;
	}
	public void start(ArrayList<Register> regs, ICache iCache, Cache cache, Memory memory, Register pc){
	//public void start(ArrayList<Register> regs, String[] mem, Register pc){
		//Units fu = new Units(cache);
		Units fu = new Units();
		this.cache = cache;
		//Memory memory = new Memory();
		int[] regTable = new int[regs.size()];
		for (int tempCounter=0; tempCounter < regTable.length; tempCounter++) {
			regTable[tempCounter] = -1;
		}
		//String ins = memory.readData(Integer.toBinaryString(pc.getValue()));
		//iCache.read(Integer.toBinaryString(pc.getValue()));
		String ins = "";
		boolean addIns = true;
		int counter = 100;
		//while (!(ins.equals("null") && rob.isEmpty())){
		while (counter > 0){
			cycles++;
			System.err.println("PCCCCCCCCC" + pc.value);
			

			if (!rob.isEmpty()){
				if (rob.peek()[1].equals("Y")){
					if (rob.peek()[3].equals("O")){
						fu.CommitOP(Integer.parseInt(rob.peek()[0]), getRegOrg(regs, rob.peek()[2]));
					}
					else if (rob.peek()[3].equals("S")){
						String[] split =  rob.peek()[2].split(" ");
						String address = split[0];
						String offset = split[1];
						fu.CommitStore(memory, Integer.parseInt(rob.peek()[0]), getRegOrg(regs, address), Integer.parseInt(offset));
					}
					ib.modify(0, 1, "commited");
				}
			}
			
			if (!ib.isEmpty()){
				if(ib.peek()[1].equals("commited")){
					rob.dequeue();
					ib.dequeue();
					if (!ib.isEmpty()){
						for(int i=0;i < ib.size(); i++){
							if (!ib.get(i, 1).equals("")){
								int temp = Integer.parseInt(ib.get(i, 2));
								temp--;
								ib.modify(i, 2, Integer.toString(temp));
							}
						}
					}
					if (!loadRs.isEmpty()){
						for(int i=0;i<loadRs.size();i++){
							String[] temp = loadRs.get(i);
							if (!temp[2].equals("")){
								int rem = Integer.parseInt(temp[2]);
								rem--;
								temp[2] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[3]);
							rem--;
							temp[3] = Integer.toString(rem);
							loadRs.set(i, temp);
						}
					}
					if (!storeRs.isEmpty()){
						for(int i=0;i<storeRs.size();i++){
							String[] temp = storeRs.get(i);
							if (!temp[3].equals("")){
								int rem = Integer.parseInt(temp[3]);
								rem--;
								temp[3] = Integer.toString(rem);
							}
							if (!temp[4].equals("")){
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							storeRs.set(i, temp);
						}
					}
					if (!addSubRs.isEmpty()){
						for(int i=0;i<addSubRs.size();i++){
							String[] temp = addSubRs.get(i);
							if (!temp[3].equals("")){
								int rem = Integer.parseInt(temp[3]);
								rem--;
								temp[3] = Integer.toString(rem);
							}
							if (!temp[4].equals("")){
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							addSubRs.set(i, temp);
						}
					}
					if (!nandRs.isEmpty()){
						for(int i=0;i<nandRs.size();i++){
							String[] temp = nandRs.get(i);
							if (!temp[3].equals("")){
								int rem = Integer.parseInt(temp[3]);
								rem--;
								temp[3] = Integer.toString(rem);
							}
							if (!temp[4].equals("")){
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							nandRs.set(i, temp);
						}
					}
					if (!multRs.isEmpty()){
						for(int i=0;i<multRs.size();i++){
							String[] temp = multRs.get(i);
							if (!temp[3].equals("")){
								int rem = Integer.parseInt(temp[3]);
								rem--;
								temp[3] = Integer.toString(rem);
							}
							if (!temp[4].equals("")){
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							multRs.set(i, temp);
						}
					}
					if (!addiRs.isEmpty()){
						for(int i=0;i<addiRs.size();i++){
							String[] temp = addiRs.get(i);
							if (!temp[2].equals("")){
								int rem = Integer.parseInt(temp[2]);
								rem--;
								temp[2] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[3]);
							rem--;
							temp[3] = Integer.toString(rem);
							addiRs.set(i, temp);
						}
					}
					if (!jmpJalrRetRs.isEmpty()){
						for(int i=0;i<jmpJalrRetRs.size();i++){
							String[] temp = jmpJalrRetRs.get(i);
							if (!temp[3].equals("")){
								int rem = Integer.parseInt(temp[3]);
								rem--;
								temp[3] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[4]);
							rem--;
							temp[4] = Integer.toString(rem);
							jmpJalrRetRs.set(i, temp);
						}
					}
					if (!beqRs.isEmpty()){
						for(int i=0;i<beqRs.size();i++){
							String[] temp = beqRs.get(i);
							if (!temp[4].equals("")){
								int rem = Integer.parseInt(temp[4]);
								rem--;
								temp[4] = Integer.toString(rem);
							}
							if (!temp[5].equals("")){
								int rem = Integer.parseInt(temp[5]);
								rem--;
								temp[5] = Integer.toString(rem);
							}
							int rem = Integer.parseInt(temp[6]);
							rem--;
							temp[6] = Integer.toString(rem);
							beqRs.set(i, temp);
						}
					}
				}
			}
			
			for (int i = 0; i < ib.size(); i++){
				iBreak = false;
				if (ib.get(i,1).equals("")){
					//System.out.println("hi2");
					String op = checkOp(ib.get(i,0));
					//System.out.println(op);
					String[] regsVal = getRegs(op, ib.get(i,0));
					if (!rob.isFull()){
						switch (op) { 
						case "load":
							if (loadRs.size()<loadRsSize){
								ib.modify(i, 1, "issued");
								String[] robRec = {"", "N", regsVal[0], "O"};
								rob.enqueue(robRec);
								ib.modify(i, 2, Integer.toString(rob.nItems - 1));
								int regIndex = getRegIndex(regs, regsVal[0]);
								regTable[regIndex] = rob.nItems - 1;
								boolean valid = this.freeDest(regs, regTable, regsVal[1]);
								if (regsVal[1].equals(regsVal[0])){
									valid = true;
								}
								String[] entry;
								if (valid){

									entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", Integer.toString(rob.nItems - 1), Integer.toString(cache.read(Integer.toBinaryString(getRegOrg(regs, regsVal[1]).getValue() + Integer.parseInt(regsVal[2])), 1,this.cycles)), Integer.toString(getRegOrg(regs, regsVal[1]).getValue() + Integer.parseInt(regsVal[2]))};
								}
								else{
									entry = new String[]{"Y", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), "", regsVal[2]};
								}
								loadRs.add(entry);
							}
							break;

						case "store":
							if (storeRs.size()<storeRsSize){
								ib.modify(i, 1, "issued");
								String[] robRec = {"", "N", regsVal[1] + " " + regsVal[2], "S"};
								rob.enqueue(robRec);
								ib.modify(i, 2, Integer.toString(rob.nItems - 1));
								boolean valid = this.freeDest(regs, regTable, regsVal[0]);
								boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
								String[] entry;
								if (valid && valid2){
									entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), "", "", Integer.toString(rob.nItems - 1), Integer.toString(storeCyc)};
								}
								else if (valid){
									entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), Integer.toString(storeCyc)};
								}
								else if(valid2){
									entry = new String[]{"Y", "", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), "", Integer.toString(rob.nItems - 1), Integer.toString(storeCyc)};
								}
								else{
									entry = new String[]{"Y", "", "", Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), Integer.toString(storeCyc)};
								}
								storeRs.add(entry);
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
						        	if (regsVal[1].equals(regsVal[0])){
										valid = true;
									}
						        	if (regsVal[2].equals(regsVal[0])){
										valid2 = true;
									}
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
						        	if (regsVal[1].equals(regsVal[0])){
										valid = true;
									}
						        	if (regsVal[2].equals(regsVal[0])){
										valid2 = true;
									}
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
						        	if (regsVal[1].equals(regsVal[0])){
										valid = true;
									}
						        	if (regsVal[2].equals(regsVal[0])){
										valid2 = true;
									}
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
						        	if (regsVal[1].equals(regsVal[0])){
										valid = true;
									}
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
							case "jmp":
								//System.out.println("hi");
								if (jmpJalrRetRs.size()<jmpJalrRetRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", "pc", "J"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	boolean valid = this.freeDest(regs, regTable, regsVal[0]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), Integer.toString(pc.getValue()),  "", Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(pc.getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	jmpJalrRetRs.add(entry);
						        	addIns = false;
						        }
							  break;
							case "beq":
								if (beqRs.size()<beqRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", "pc", "B"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	boolean valid = this.freeDest(regs, regTable, regsVal[0]);
						        	boolean valid2 = this.freeDest(regs, regTable, regsVal[1]);
						        	String[] entry;
						        	if (valid && valid2){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(pc.getValue()), "", "", Integer.toString(rob.nItems - 1), Integer.toString(beqCyc)};
						        	}
						        	else if(valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), "", Integer.toString(pc.getValue()), "", Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1) , Integer.toString(beqCyc)};
						        	}
						        	else if(valid2){
						        		entry = new String[]{"Y", "", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(pc.getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), "", Integer.toString(rob.nItems - 1) , Integer.toString(beqCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", "", Integer.toString(pc.getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1) , Integer.toString(beqCyc)};
						        	}
						        	beqRs.add(entry);
						        	canWrite = false;
						        }
							  break;
							case "jalr":
								if (jmpJalrRetRs.size()<jmpJalrRetRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", regsVal[0], "O"};
						        	rob.enqueue(robRec);
									ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	int regIndex = getRegIndex(regs, regsVal[0]);
						        	regTable[regIndex] = rob.nItems - 1;
						        	boolean valid = this.freeDest(regs, regTable, regsVal[1]);
						        	if (regsVal[1].equals(regsVal[0])){
										valid = true;
									}
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[1]).getValue()), Integer.toString(pc.getValue()),  "", Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(pc.getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[1])]), Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	jmpJalrRetRs.add(entry);
						        	addIns = false;
						        }
							  break;
							case "ret":
								if (jmpJalrRetRs.size()<jmpJalrRetRsSize){
									ib.modify(i, 1, "issued");
						        	String[] robRec = {"", "N", "pc", "J"};
						        	rob.enqueue(robRec);
						        	ib.modify(i, 2, Integer.toString(rob.nItems - 1));
						        	boolean valid = this.freeDest(regs, regTable, regsVal[0]);
						        	String[] entry;
						        	if (valid){
						        		entry = new String[]{"Y", Integer.toString(getRegOrg(regs, regsVal[0]).getValue()), Integer.toString(pc.getValue()),  "", Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	else{
						        		entry = new String[]{"Y", "", Integer.toString(pc.getValue()), Integer.toString(regTable[getRegIndex(regs, regsVal[0])]), Integer.toString(rob.nItems - 1), Integer.toString(jmpJalrRetCyc)};
						        	}
						        	jmpJalrRetRs.add(entry);
						        	addIns = false;
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
					String[] regsVal = getRegs(op, ib.get(i,0));
					switch (op) {					
					case "load":
						for (int k = 0; k < loadRs.size(); k++){
							if (loadRs.get(k)[3].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (loadRs.get(index)[4].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
						}
						else if (loadRs.get(index)[2].equals("")){
								boolean sameAdd = false;
								for (int k = 0; k < storeRs.size(); k++){
									if (storeRs.get(k)[4].equals("")){
										String offset = "0";
										for(int t = 0; t<ib.size(); t++){
											if (ib.get(t, 2).equals(storeRs.get(k)[5])){
												offset = getRegs("store", ib.get(t, 0))[2];
											}
										}
										
										String address = Integer.toString(Integer.parseInt(storeRs.get(k)[2]) + Integer.parseInt(offset));
										if (address.equals(loadRs.get(index)[5])){
											sameAdd = true;
										}
									}
								}
								if (!sameAdd){
									String[] temp = loadRs.get(index);
									int rem = Integer.parseInt(temp[4]);
									rem--;
									temp[4] = Integer.toString(rem);
									loadRs.set(index, temp);
								}
						}
						else{
							if (!rob.get(Integer.parseInt(loadRs.get(index)[2]), 0).equals("")){
								String[] temp = loadRs.get(index);
								temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(loadRs.get(index)[2]), 2)).getValue());
								temp[5] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(loadRs.get(index)[2]), 2)).getValue() + Integer.parseInt(temp[5]));
								temp[4] = Integer.toString(cache.read(temp[5], 1,this.cycles));
								temp[2] = "";
								loadRs.set(index, temp);
							}
						}
						break;
					case "store":
						for (int k = 0; k < storeRs.size(); k++){
							if (storeRs.get(k)[5].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (storeRs.get(index)[6].equals(Integer.toString(0))){
							int timeToStore = cache.read(Integer.toBinaryString(Integer.parseInt(storeRs.get(index)[2]) + Integer.parseInt(regsVal[2])), 2, this.cycles);
							String[] temp = storeRs.get(index);
							temp[6] = Integer.toString(timeToStore);
							storeRs.set(index, temp);
							ib.modify(i, 1, "executed");
						}
						else if (storeRs.get(index)[3].equals("") && storeRs.get(index)[4].equals("")){
							String[] temp = storeRs.get(index);
							int rem = Integer.parseInt(temp[6]);
							rem--;
							temp[6] = Integer.toString(rem);
							storeRs.set(index, temp);
						}
						else{
							if (storeRs.get(index)[1].equals("")){
								if (!rob.get(Integer.parseInt(storeRs.get(index)[3]), 0).equals("")){
									String[] temp = storeRs.get(index);
									temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(storeRs.get(index)[3]), 2)).getValue());
									temp[3] = "";
									storeRs.set(index, temp);
								}
							}
							if (storeRs.get(index)[2].equals("")){
								if (!rob.get(Integer.parseInt(storeRs.get(index)[4]), 0).equals("")){
									String[] temp = storeRs.get(index);
									temp[2] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(storeRs.get(index)[4]), 2)).getValue());
									temp[4] = "";
									storeRs.set(index, temp);
								}
							}
						}
						break;
					case "add":
					case "sub":
						for (int k = 0; k < addSubRs.size(); k++){
							if (addSubRs.get(k)[5].equals(ib.get(i, 2))){
								index = k;
								//System.out.println("index: " + index);
							}
						}
						if (addSubRs.get(index)[6].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
							//System.out.println("i: " + i + addSubRs.get(index)[6]);
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
					case "jmp":
						for (int k = 0; k < jmpJalrRetRs.size(); k++){
							if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (jmpJalrRetRs.get(index)[5].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
						}
						else if (jmpJalrRetRs.get(index)[3].equals("")){
							String[] temp = jmpJalrRetRs.get(index);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							jmpJalrRetRs.set(index, temp);
						}
						else{
							if (!rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 0).equals("")){
								String[] temp = jmpJalrRetRs.get(index);
								temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 2)).getValue());
								temp[3] = "";
								jmpJalrRetRs.set(index, temp);
							}
						}
						break;
					case "beq":
						for (int k = 0; k < beqRs.size(); k++){
							if (beqRs.get(k)[6].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (beqRs.get(index)[7].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
						}
						else if (beqRs.get(index)[4].equals("") && beqRs.get(index)[5].equals("")){
							String[] temp = beqRs.get(index);
							int rem = Integer.parseInt(temp[7]);
							rem--;
							temp[7] = Integer.toString(rem);
							beqRs.set(index, temp);
						}
						else{
							if (beqRs.get(index)[1].equals("")){
								if (!rob.get(Integer.parseInt(beqRs.get(index)[4]), 0).equals("")){
									String[] temp = beqRs.get(index);
									temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(beqRs.get(index)[4]), 2)).getValue());
									temp[4] = "";
									beqRs.set(index, temp);
								}
							}
							if (beqRs.get(index)[2].equals("")){
								if (!rob.get(Integer.parseInt(beqRs.get(index)[5]), 0).equals("")){
									String[] temp = beqRs.get(index);
									temp[2] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(beqRs.get(index)[5]), 2)).getValue());
									temp[5] = "";
									beqRs.set(index, temp);
								}
							}
						}
						break;
					case "jalr":
						for (int k = 0; k < jmpJalrRetRs.size(); k++){
							if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (jmpJalrRetRs.get(index)[5].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
						}
						else if (jmpJalrRetRs.get(index)[3].equals("")){
							String[] temp = jmpJalrRetRs.get(index);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							jmpJalrRetRs.set(index, temp);
						}
						else{
							if (!rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 0).equals("")){
								String[] temp = jmpJalrRetRs.get(index);
								temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 2)).getValue());
								temp[3] = "";
								jmpJalrRetRs.set(index, temp);
							}
						}
						break;
					case "ret":
						for (int k = 0; k < jmpJalrRetRs.size(); k++){
							if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
								index = k;
							}
						}
						if (jmpJalrRetRs.get(index)[5].equals(Integer.toString(0))){
							ib.modify(i, 1, "executed");
						}
						else if (jmpJalrRetRs.get(index)[3].equals("")){
							String[] temp = jmpJalrRetRs.get(index);
							int rem = Integer.parseInt(temp[5]);
							rem--;
							temp[5] = Integer.toString(rem);
							jmpJalrRetRs.set(index, temp);
						}
						else{
							if (!rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 0).equals("")){
								String[] temp = jmpJalrRetRs.get(index);
								temp[1] = Integer.toString(getRegOrg(regs, rob.get(Integer.parseInt(jmpJalrRetRs.get(index)[3]), 2)).getValue());
								temp[3] = "";
								jmpJalrRetRs.set(index, temp);
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
					int bindex = rob.size();
					for (int j = 0; j < rob.size(); j++){
						if (rob.get(j, 3).equals("B")){
							bindex = j;
						}
					}
					if (canWrite || Integer.parseInt(ib.get(i, 2)) <= bindex){
						switch (op) {					
						case "load":
							for (int k = 0; k < loadRs.size(); k++){
								if (loadRs.get(k)[3].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Load(memory, getRegOrg(regs, regsVal[0]), Integer.parseInt(loadRs.get(index)[1]), Integer.parseInt(regsVal[2]));
							rob.modify(Integer.parseInt(loadRs.get(index)[3]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(loadRs.get(index)[3]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							loadRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "store":
							for (int k = 0; k < storeRs.size(); k++){
								if (storeRs.get(k)[5].equals(ib.get(i, 2))){
									index = k;
								}
							}
							if (storeRs.get(index)[6].equals("0")){
								result = fu.Store(memory, Integer.parseInt(storeRs.get(index)[1]), Integer.parseInt(storeRs.get(index)[2]), Integer.parseInt(regsVal[2]));
								rob.modify(Integer.parseInt(storeRs.get(index)[5]), 0, Integer.toString(result));
								rob.modify(Integer.parseInt(storeRs.get(index)[5]), 1, "Y");
								storeRs.remove(index);
								ib.modify(i, 1, "written");
							}
							else{
								String[] temp = storeRs.get(index);
								int rem = Integer.parseInt(temp[6]);
								rem--;
								temp[6] = Integer.toString(rem);
							}
							break;
						case "add":
						case "sub":
								for (int k = 0; k < addSubRs.size(); k++){
									if (addSubRs.get(k)[5].equals(ib.get(i, 2))){
										index = k;
										//System.out.println(index);
									}
								}
							result = fu.AddSub(op, getRegOrg(regs, regsVal[0]), Integer.parseInt(addSubRs.get(index)[1]), Integer.parseInt(addSubRs.get(index)[2]));
							rob.modify(Integer.parseInt(addSubRs.get(index)[5]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(addSubRs.get(index)[5]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
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
							rob.modify(Integer.parseInt(nandRs.get(index)[5]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(nandRs.get(index)[5]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
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
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
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
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							addiRs.remove(index);
							ib.modify(i, 1, "written");
							break;
						case "jmp":
							for (int k = 0; k < jmpJalrRetRs.size(); k++){
								if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.jump(pc, Integer.parseInt(jmpJalrRetRs.get(index)[2]), Integer.parseInt(jmpJalrRetRs.get(index)[1]), Integer.parseInt(regsVal[1]));
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							jmpJalrRetRs.remove(index);
							ib.modify(i, 1, "written");
							addIns = true;
							break;
						case "beq":
							for (int k = 0; k < beqRs.size(); k++){
								if (beqRs.get(k)[6].equals(ib.get(i, 2))){
									index = k;
								}
							}
							int pc3 = pc.getValue();
							result = fu.BEQ(pc, Integer.parseInt(beqRs.get(index)[3]), Integer.parseInt(beqRs.get(index)[1]), Integer.parseInt(beqRs.get(index)[2]), Integer.parseInt(regsVal[2]));
							if (result != pc3){
								flushFetch = true;
								for(int a = 0; a < loadRs.size(); a++){
									if(Integer.parseInt(loadRs.get(a)[3]) > bindex){
										loadRs.remove(a);
									}
								}
								for(int a = 0; a < storeRs.size(); a++){
									if(Integer.parseInt(storeRs.get(a)[3]) > bindex){
										storeRs.remove(a);
									}
								}
								for(int a = 0; a < addSubRs.size(); a++){
									//System.out.println("addSub: " + addSubRs.get(a)[5]);
									if(Integer.parseInt(addSubRs.get(a)[5]) > bindex){
										addSubRs.remove(a);
										//System.out.println("addSub: " + addSubRs.get(a)[5]);
									}
								}
								for(int a = 0; a < nandRs.size(); a++){
									if(Integer.parseInt(nandRs.get(a)[5]) > bindex){
										nandRs.remove(a);
									}
								}
								for(int a = 0; a < multRs.size(); a++){
									if(Integer.parseInt(multRs.get(a)[5]) > bindex){
										multRs.remove(a);
									}
								}
								for(int a = 0; a < addiRs.size(); a++){
									if(Integer.parseInt(addiRs.get(a)[3]) > bindex){
										addiRs.remove(a);
									}
								}
								for(int a = 0; a < jmpJalrRetRs.size(); a++){
									if(Integer.parseInt(jmpJalrRetRs.get(a)[4]) > bindex){
										jmpJalrRetRs.remove(a);
									}
								}
								for(int a = 0; a < beqRs.size(); a++){
									if(Integer.parseInt(beqRs.get(a)[6]) > bindex){
										beqRs.remove(a);
									}
								}
								for(int b = 0; b <= ib.size(); b++){
									if (Integer.parseInt(ib.get(b, 2)) > bindex){
										ib.nItems--;
									}
								}
								for(int c = 0; c < regs.size(); c++){
									if(regTable[c] > bindex){
										regTable[c] = -1;										}
								}
								for (int d = bindex + 1; d <= rob.size(); d++){
									rob.nItems--;
								}

								iBreak = true;
							}
							rob.modify(Integer.parseInt(beqRs.get(index)[6]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(beqRs.get(index)[6]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							beqRs.remove(index);
							ib.modify(i, 1, "written");
							canWrite = true;
							break;
						case "jalr":
							for (int k = 0; k < jmpJalrRetRs.size(); k++){
								if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Jalr(pc, Integer.parseInt(jmpJalrRetRs.get(index)[2]), getRegOrg(regs, regsVal[0]), Integer.parseInt(jmpJalrRetRs.get(index)[1]));
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							jmpJalrRetRs.remove(index);
							ib.modify(i, 1, "written");
							addIns = true;
							break;
						case "ret":
							for (int k = 0; k < jmpJalrRetRs.size(); k++){
								if (jmpJalrRetRs.get(k)[4].equals(ib.get(i, 2))){
									index = k;
								}
							}
							result = fu.Return(Integer.parseInt(jmpJalrRetRs.get(index)[1]), pc);
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 0, Integer.toString(result));
							rob.modify(Integer.parseInt(jmpJalrRetRs.get(index)[4]), 1, "Y");
							if (regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] == Integer.parseInt(ib.get(i, 2))){
								regTable[getRegIndex(regs, rob.get(Integer.parseInt(ib.get(i, 2)), 2))] = -1;
							}
							jmpJalrRetRs.remove(index);
							ib.modify(i, 1, "written");
							addIns = true;
							break;
						default:
							break;
						}
						
					}
				}
				if (iBreak)
					break;
			}



			if (!ib.isFull() && addIns){
				//ins = memory.readData(Integer.toBinaryString(pc.getValue()));
				//iCache.read(Integer.toBinaryString(pc.getValue()));
				try {
					if (flushFetch == true) {
						timeForFetch = 0;
					}
					if (timeForFetch == 0) {
						if (!ins.equals("null")){
							if (ins.length() != 0) {
								if (!flushFetch) {
									String[] insa = {ins, "", ""};
									ib.enqueue(insa);
								}
								
							}
						}
						else {
							addIns = false;
						}
						flushFetch = false;
						pc.setValue((pc.getValue() + 2));
						ins = memory.readData(pc.getValue()+"");
						//System.out.println(pc.getValue()+"");
						timeForFetch = iCache.read(Integer.toBinaryString(pc.getValue()), 1);
					}
					//ins = mem[pc.getValue()];
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("Wrong pc calculated after jump");
					System.exit(0);
				}
				
				
				System.err.println(this.timeForFetch+ins);
				this.timeForFetch--;
				//pc.setValue((pc.getValue() + 1));
			}
			counter--;
			System.out.println("cycles: " + cycles);
			System.out.println("ib: ");
			printing(ib, 3);
			System.out.println("rob: ");
			printing(rob, 4);
			System.out.println("load rs: ");
			printingA(loadRs, 5);
			System.out.println("store rs: ");
			printingA(storeRs, 7);
			System.out.println("mult rs: ");
			printingA(multRs, 7);
			System.out.println("add/sub rs: ");
			printingA(addSubRs, 7);
			System.out.println("nand rs: ");
			printingA(nandRs, 7);
			System.out.println("jmpJalrRet rs: ");
			printingA(jmpJalrRetRs, 6);
			System.out.println("beq rs: ");
			printingA(beqRs, 8);
			System.out.println();
		}
		int time = 0;
		for (int i = 0; i<cache.times.size(); i++) {
			if (this.cache.times.get(i) < time) {
				time+= (this.cache.times.get(i)+this.cache.cycles_access_memory);
			} else {
				time = (this.cache.times.get(i)+this.cache.cycles_access_memory);
			}
		}
		if (time > cycles) {
			cycles= time;
		}
	}

	public String checkOp(String ins){
		return ins.split(" ")[0];
	}
	public String[] getRegs(String op, String ins){
		String[] s = new String[3];
		String[] split = ins.split(" ");
		s[0] = split[1];
		if (!op.equals("ret")){
			s[1] = split[2];
			if (!op.equals("jmp") && !op.equals("jalr")){
				s[2] = split[3];
			}
		}
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
		TomasuloAlg t = new TomasuloAlg(8, 1, 2, 2, 2, 2, 1, 2, 8, 8, 2, 2, 1, 2, 2, 2, 5);
		Register F6 = new Register("F6", 1);
		Register R2 = new Register("R2", 5);
		Register F2 = new Register("F2", 1);
		Register R3 = new Register("R3", 1);
		Register F0 = new Register("F0", 1);
		Register F4 = new Register("F4", 1);
		Register F8 = new Register("F8", 1);
		Register F10 = new Register("F10", 1);
		Register F16 = new Register("F16", 0);
		Register pc = new Register("pc", -2);
		ArrayList<Register> regs = new ArrayList<Register>();
		regs.add(F6);
		regs.add(R2);
		regs.add(F2);
		regs.add(R3);
		regs.add(F0);
		regs.add(F4);
		regs.add(F8);
		regs.add(F10);
		regs.add(F16);
		regs.add(pc);
		Memory memo = new Memory();
		String[] mem = {"sub R2 R2 R3","beq R2 F0 2", "ret F16", "nand R2 R2 R3"};
		memo.writeData("0", mem[0]);
		memo.writeData("2", mem[1]);
		memo.writeData("4", mem[2]);
		memo.writeData("6", mem[3]);
		t.start(regs,new ICache(128, 16, 1, 64, 4, 2, 0, 0,
				0,  3,  0,
				 0,  1,
				 1,  1, 
				 0, 0,0,0, 2, memo),new Cache(128, 16, 1, 64, 4, 2, 0, 0,
				0,  3,  0,
				 0,  10,
				 1,  1, 
				 0, 0,0,0, 2, memo), memo, pc);
	}
}
