import java.util.ArrayList;


public class Register {
    ArrayList<Integer> registers= new ArrayList<Integer>();

	public int getValue(int index) {
		return registers.get(index);
	}

	public void setValue(int value, int index) {
		registers.add(index, value);
	}

}


