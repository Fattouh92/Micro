
public class Units {
	Cache cache;
	
	public Units(Cache c) {
		cache = c;
	}
	
	public int AddSub(String type,Register d,Register s1,Register s2){
		if(type.equals("Add")){
			int sum=s1.getValue()+s2.getValue();
			d.setValue(sum);
			return sum;
		}
		else{
		int result=s1.getValue()-s2.getValue();
		d.setValue(result);
		return result;
		}
		}
	
	public int Addi(Register d,Register s1,int value){
		int sum=s1.getValue()+value;
		d.setValue(sum);
		return sum;
		}
	
	public int NAND(Register d,Register s1,Register s2){
		int result=~(s1.getValue()&s2.getValue());
		 d.setValue(result);
		 return result;
	} 
	
	public int Multiply(Register d,Register s1,Register s2){
		int result=s1.getValue()*s2.getValue();
		d.setValue(result);
		return result;
	}
	
	
	public int LoadStore(String type,Memory m,Register a,Register b,int immediate){
		if(type.equals("Load")){
		int val=immediate+b.getValue();
		String address=Integer.toBinaryString(val);
		cache.read(address, 1);
		String val1=m.readData(address);
		int value=Integer.parseInt(val1, 2);
		a.setValue(value);
		return value;
		}
		else{
			int address=immediate+b.getValue();
			String add=Integer.toBinaryString(address);
			cache.read(add, 2);
			String value=Integer.toBinaryString(a.getValue());
			m.writeData(add, value);
			return a.getValue();
		}
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
   public void CommitStore(Memory m,int value,int address,int offset){
	   int write = address+offset;
	   String add=Integer.toBinaryString(write);
	   String val=Integer.toBinaryString(value);
	   m.writeData(add,val);
   }
}
