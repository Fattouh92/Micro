public class Pointer {
	int value;
	int limit;
	public Pointer(int limit){
		value = 0;
		this.limit = limit;
	}
	public void inc(){
		value++;
		if (value == limit){
			value = 0;
		}
	}
	public int val(){
		return value;
	}
}
