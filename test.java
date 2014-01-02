import java.util.ArrayList;

public class test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ArrayList<String> tempTags = new ArrayList<String>();
		tempTags.add(null);
		tempTags.add(null);
		tempTags.add(null);
		tempTags.add(null);
		int x = (int) (Math.random() * tempTags.size());
		System.out.print(tempTags.size());
	}
}
