
public class Units {
	//Cache cache;
	
	//public Units(Cache c) {
		//cache = c;
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
	
	public int NAND(Register d,int s1,int s2){
		int result=~(s1&s2);
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
		//cache.read(address, 1);
		String val1=m.readData(address);
		int value=Integer.parseInt(val1, 2);
		a.setValue(value);
		return value;
	}
	public int Store(Memory m,int a,Register b,int immediate){
		int address=immediate+b.getValue();
		String add=Integer.toBinaryString(address);
		//cache.read(add, 2);
		String value=Integer.toBinaryString(a);
		m.writeData(add, value);
		return a;
	}
	
   public int BEQ(Register pc,int pc2, int a,int b,int immediate){
	   if(a==b){
		   int address = pc2+1+immediate;
		   pc.setValue(address);
	   }
	   return pc.getValue();
   }
   
   public int jump(Register pc,int pc2, int a,int immediate){
	   int address=pc2+1+a+immediate;
	   pc.setValue(address);
	   return address;
   }
   
   public int Jalr(Register pc,int pc2, Register a,int b){
	   a.setValue(pc2+1);
	   pc.setValue(b);
	   return a.getValue();
   }
   
   public int Return(int a,Register pc){
	   pc.setValue(a);
	   return a;
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
