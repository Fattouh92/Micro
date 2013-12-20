import java.util.ArrayList;

public class ICache {
	int S;
	int L;
	int m;
	int S2;
	int L2;
	int m2;
	int S3;
	int L3;
	int m3;
	int memory_accesses;
	int cycles_access_data;
	int cycles_access_data2;
	int cycles_access_data3;
	int cycles_access_memory;
	int write_hit_policy;   //1=write_throught 2=write_back
	int write_miss_policy;  //1=write allocate 2=write around
	int write_hit_policy2;  //1=write_throught 2=write_back
	int write_miss_policy2; //1=write allocate 2=write around
	int write_hit_policy3;  //1=write_throught 2=write_back
	int write_miss_policy3; //1=write allocate 2=write around
	int hit=0;
	int miss=0;
	int hit2=0;
	int miss2=0;
	int hit3=0;
	int miss3=0;
	int cycles = 0;
	int levels;
	int index;
	int tag;
	int offset;
	int index2;
	int tag2;
	int offset2;
	int index3;
	int tag3;
	int offset3;
	int pc;
	ArrayList<ActualCache> LevelOne = new ArrayList<ActualCache>();
	ArrayList<ActualCache> LevelTwo = new ArrayList<ActualCache>();
	ArrayList<ActualCache> LevelThree = new ArrayList<ActualCache>();
	Memory memory;

	public ICache(int s, int l, int m, int s2, int l2, int m2, int s3, int l3,
			int m3, int cycles_access_data, int cycles_access_data2,
			int cycles_access_data3, int cycles_access_memory,
			int write_hit_policy, int write_miss_policy, 
			int write_hit_policy2, int write_miss_policy2,int write_hit_policy3, 
			int write_miss_policy3, int levels, Memory memory) {
		S = s;
		L = l;
		this.m = m;
		S2 = s2;
		L2 = l2;
		this.m2 = m2;
		S3 = s3;
		L3 = l3;
		this.m3 = m3;
		this.cycles_access_data = cycles_access_data;
		this.cycles_access_data2 = cycles_access_data2;
		this.cycles_access_data3 = cycles_access_data3;
		this.cycles_access_memory = cycles_access_memory;
		this.write_hit_policy = write_hit_policy;
		this.write_miss_policy = write_miss_policy;
		this.write_hit_policy2 = write_hit_policy2;
		this.write_miss_policy2 = write_miss_policy2;
		this.write_hit_policy3 = write_hit_policy3;
		this.write_miss_policy3 = write_miss_policy3;
		this.levels = levels;
		this.offset = (int)(Math.log(L)/Math.log(2));
		this.index =  (int)(Math.log(S/L)/Math.log(2));
		this.tag = 16 - offset - index;
		int i;
		for (i = 0; i<m; i++) {
			LevelOne.add(new ActualCache(new String[S/L][4]));
		}
		if (2 <= levels) {
			this.offset2 = (int)(Math.log(L2)/Math.log(2));
			this.index2 =  (int)(Math.log(S2/L2)/Math.log(2));
			this.tag2 = 16 - offset - index;
			for (i = 0; i<m; i++) {
				LevelTwo.add(new ActualCache(new String[S2/L2][4]));
			}
			if (3 <= levels) {
				this.offset3 = (int)(Math.log(L3)/Math.log(2));
				this.index3 =  (int)(Math.log(S3/L3)/Math.log(2));
				this.tag3 = 16 - offset3 - index3;
				for (i = 0; i<m; i++) {
					LevelThree.add(new ActualCache(new String[S3/L3][4]));
				}
			}
		}
		this.memory = memory;
	}

	public void read (String address) {  //type=1 read or 2 = write
		cycles += this.cycles_access_data;

		while(address.length() < 16) {
			address = "0" + address;
		}

		String tempTag = address.substring(0, tag);
		String tempIndex = address.substring(tag, tag+index);
		String tempOffset = address.substring(tag+index, 16);

		int indexInt = Integer.parseInt(tempIndex, 2);
		ArrayList<String> tempTags = new ArrayList<String>();
		for(int j = 0; j < LevelOne.size(); j++) {
			String TempTag1 = LevelOne.get(j).getArray()[indexInt][0];
			tempTags.add(TempTag1);
		}
		if (tempTags.contains(tempTag)) {
			hit2++;
		} else {
			miss2++;
			if (levels >= 2) {
				this.readLevelThree(address);
				if (tempTags.contains(null)) {
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][0] = tempTag;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][1] = tempIndex;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][2] = tempOffset;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][3] = "N";
				} else {
					int random = (int) (Math.random() * tempTags.size()); 
					LevelTwo.get(random).getArray()[indexInt][0] = tempTag;
					LevelTwo.get(random).getArray()[indexInt][1] = tempIndex;
					LevelTwo.get(random).getArray()[indexInt][2] = tempOffset;
					LevelTwo.get(random).getArray()[indexInt][3] = "N";
				}
			} else {
				this.memory_accesses++;
				if (tempTags.contains(null)) {
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][0] = tempTag;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][1] = tempIndex;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][2] = tempOffset;
					LevelTwo.get(tempTags.indexOf(null)).getArray()[indexInt][3] = "N";
				} else {
					int random = (int) (Math.random() * tempTags.size()); 
					LevelTwo.get(random).getArray()[indexInt][0] = tempTag;
					LevelTwo.get(random).getArray()[indexInt][1] = tempIndex;
					LevelTwo.get(random).getArray()[indexInt][2] = tempOffset;
					LevelTwo.get(random).getArray()[indexInt][3] = "N";
				}
			}

		}
	}

	public void readLevelTwo (String address) {

		cycles += this.cycles_access_data2;
		while(address.length() < 16) {
			address = "0" + address;
		}

		String tempTag = address.substring(0, tag2);
		String tempIndex = address.substring(tag2, tag2+index2);
		String tempOffset = address.substring(tag2+index2, 16);

		int indexInt = Integer.parseInt(tempIndex, 2);
		ArrayList<String> tempTags = new ArrayList<String>();
		for(int j = 0; j < LevelTwo.size(); j++) {
			String TempTag1 = LevelTwo.get(j).getArray()[indexInt][0];
			tempTags.add(TempTag1);
		}
		if (tempTags.contains(tempTag)) {
			hit++;
		} else {
			miss++;
			if (levels >= 2) {
				this.readLevelTwo(address);
				if (tempTags.contains(null)) {
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][0] = tempTag;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][1] = tempIndex;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][2] = tempOffset;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][3] = "N";
				} else {
					int random = (int) (Math.random() * tempTags.size()); 
					LevelOne.get(random).getArray()[indexInt][0] = tempTag;
					LevelOne.get(random).getArray()[indexInt][1] = tempIndex;
					LevelOne.get(random).getArray()[indexInt][2] = tempOffset;
					LevelOne.get(random).getArray()[indexInt][3] = "N";
				}
			} else {
				this.memory_accesses++;
				if (tempTags.contains(null)) {
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][0] = tempTag;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][1] = tempIndex;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][2] = tempOffset;
					LevelOne.get(tempTags.indexOf(null)).getArray()[indexInt][3] = "N";
				} else {
					int random = (int) (Math.random() * tempTags.size()); 
					LevelOne.get(random).getArray()[indexInt][0] = tempTag;
					LevelOne.get(random).getArray()[indexInt][1] = tempIndex;
					LevelOne.get(random).getArray()[indexInt][2] = tempOffset;
					LevelOne.get(random).getArray()[indexInt][3] = "N";
				}
			}

		}
	}

	public void readLevelThree(String address) {
		cycles += this.cycles_access_data3;
		while(address.length() < 16) {
			address = "0" + address;
		}

		String tempTag = address.substring(0, tag3);
		String tempIndex = address.substring(tag3, tag3+index3);
		String tempOffset = address.substring(tag3+index3, 16);

		int indexInt = Integer.parseInt(tempIndex, 2);
		ArrayList<String> tempTags = new ArrayList<String>();
		for(int j = 0; j < LevelThree.size(); j++) {
			String TempTag1 = LevelThree.get(j).getArray()[indexInt][0];
			tempTags.add(TempTag1);
		}
		if (tempTags.contains(tempTag)) {
			hit3++;
		} else {
			miss3++;
			this.memory_accesses++;
			if (tempTags.contains(null)) {
				LevelThree.get(tempTags.indexOf(null)).getArray()[indexInt][0] = tempTag;
				LevelThree.get(tempTags.indexOf(null)).getArray()[indexInt][1] = tempIndex;
				LevelThree.get(tempTags.indexOf(null)).getArray()[indexInt][2] = tempOffset;
				LevelThree.get(tempTags.indexOf(null)).getArray()[indexInt][3] = "N";
			} else {
				int random = (int) (Math.random() * tempTags.size()); 
				LevelThree.get(random).getArray()[indexInt][0] = tempTag;
				LevelThree.get(random).getArray()[indexInt][1] = tempIndex;
				LevelThree.get(random).getArray()[indexInt][2] = tempOffset;
				if (LevelThree.get(random).getArray()[indexInt][3].equals("Y")) {
					this.memory_accesses++;
				}
				LevelThree.get(random).getArray()[indexInt][3] = "N";
			}
		}
	}
}