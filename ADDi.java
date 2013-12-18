public class ADDi extends FunctionalUnit {
	 int result;
	public ADDi(Register d,Register s1,int immediate){
		rs=s1;
		i=immediate;
		rd=d;
		result=rs.getValue()+i;
		rd.setValue(result);
	}

}
