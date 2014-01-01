
public class Units {
	public Units() {
	}

	public int AddSub(String type,Register d,int s1,int s2){
		if(type.equals("add")){
			int sum=s1+s2;
			d.setValue(sum);
			return sum;
		}
		else{
			int result=s1-s2;
			d.setValue(result);
			return result;
		}
	}

	public int Addi(Register d,int s1,int value){
		int sum=s1+value;
		d.setValue(sum);
		return sum;
	}

	public static int NAND(Register d,int s1,int s2){
		int result=(s1&s2);
		String binaryValue = Integer.toBinaryString(result);
		String tempBinaryValue = "";
		for (int temp=0; temp<binaryValue.length(); temp++) {
			if (binaryValue.charAt(temp) == '0') {
				tempBinaryValue = tempBinaryValue + '1';
			} else {
				tempBinaryValue += tempBinaryValue + '0';
			}
		}
		result = Integer.parseInt(tempBinaryValue, 2);
		d.setValue(result);
		return result;
	} 

	public int Multiply(Register d,int s1,int s2){
		int result=s1*s2;
		d.setValue(result);
		return result;
	}


	public int Load(Memory m,Register a,int b,int immediate){
		int val=immediate+b;
		String address=Integer.toBinaryString(val);
		String val1=m.readData(address);
		int value=Integer.parseInt(val1, 2);
		a.setValue(value);
		return value;
	}
	public int Store(Memory m,int a,Register b,int immediate){
		int address=immediate+b.getValue();
		String add=Integer.toBinaryString(address);
		String value=Integer.toBinaryString(a);
		m.writeData(add, value);
		return a;
	}

	public void BEQ(Register pc,Register a,Register b,int immediate){
		if(a.getValue()==b.getValue()){
			int address = pc.getValue()+1+immediate;
			pc.setValue(address);
		}
	}

	public void jump(Register pc,Register a,int immediate){
		int address=pc.getValue()+1+a.getValue()+immediate;
		pc.setValue(address);
	}

	public void Jalr(Register pc,Register a,Register b){
		a.setValue(pc.getValue()+1);
		pc.setValue(b.getValue());
	}

	public void Return(Register a,Register pc){
		pc.setValue(a.getValue());
	}

	public void CommitOP(int value,Register d){
		d.setValue(value);
	}
	public void CommitStore(Memory m,int value,Register address,int offset){
		int write = address.getValue()+offset;
		String add=Integer.toBinaryString(write);
		String val=Integer.toBinaryString(value);
		m.writeData(add,val);
	}
}
