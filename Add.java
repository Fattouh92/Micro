
public class Add extends FunctionalUnit {
	 int sum;
	public Add(Register d,Register s1,Register s2){
		rs=s1;
		rt=s2;
		rd=d;
		sum=rs.getValue()+rt.getValue();
		rd.setValue(sum);
	}

}
