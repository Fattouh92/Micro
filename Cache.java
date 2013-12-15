import java.util.ArrayList;

public class Cache {
	int S;
	int L;
	int m;
	int cycles_access_data;
	int cycles_access_memory;
	int write_hit_policy; //1=write_throught 2=write_back
	int write_miss_policy; //1=write allocate 2=write around
	ArrayList<ActualCache> myList = new ArrayList<ActualCache>();

	public Cache(int s, int l, int m, int cycles_access_data,
			int cycles_access_memory, int write_hit_policy,
			int write_miss_policy) {
		S = s;
		L = l;
		this.m = m;
		this.cycles_access_data = cycles_access_data;
		this.cycles_access_memory = cycles_access_memory;
		this.write_hit_policy = write_hit_policy;
		this.write_miss_policy = write_miss_policy;
		for (int i = 0; i<m; i++) {
			myList.add(new ActualCache(new Object[S/L][7]));
		}
	}
	
	
	
	
	
}
