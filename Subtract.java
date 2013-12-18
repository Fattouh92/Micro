public class Subtract extends FunctionalUnit {
	 int result;
	public Subtract(Register d,Register s1,Register s2){
		rs=s1;
		rt=s2;
		rd=d;
		result=rs.getValue()+rt.getValue();
		rd.setValue(result);
	}

}