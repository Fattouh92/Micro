
public class Register {
	String name;
	int value;

	public Register(int value) {
		this.value = value;
	}

	public Register(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (name.equals("zero"))
			return;
		this.value = value;
	}
	

}


