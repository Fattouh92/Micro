
public class Units {

	
	public void Add(Register d,Register s1,Register s2){
		int sum=s1.getValue()+s2.getValue();
		d.setValue(sum);
		}
	
	public void Addi(Register d,Register s1,int value){
		int sum=s1.getValue()+value;
		d.setValue(sum);
		}
	
	public void Subtract(Register d,Register s1,Register s2){
			int result=s1.getValue()-s2.getValue();
			d.setValue(result);
			}
	
	public void Multiply(Register d,Register s1,Register s2){
		int result=s1.getValue()*s2.getValue();
		d.setValue(result);
	}
	
	public void NAND(Register d,Register s1,Register s2){
		int result=~(s1.getValue()&s2.getValue());
		 d.setValue(result);
	} 
	
	public void Load(Memory m,Register a,Register b,int immediate){
		int val=immediate+b.getValue();
		String address=Integer.toBinaryString(val);
		String val1=m.readData(address);
		int value=Integer.parseInt(val1,2);
		a.setValue(value);
	}
	
	public void Store(Memory m,Register a,Register b,int immediate){
		int address=immediate+b.getValue();
		String add=Integer.toBinaryString(address);
		String value=Integer.toBinaryString(a.getValue());
		m.writeData(add, value);
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
}
