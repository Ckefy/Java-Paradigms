import java.util.ArrayList;
import java.util.*;	
public class Reverse {
	public static void main (String[] args) {
		ArrayList <ArrayList <String>> massive = new ArrayList <>();
		Scanner	std = new Scanner(System.in);
		String s = new String ();
		int c=0;
		while (std.hasNextLine()){	
			ArrayList <String> massive1 = new ArrayList <>();
			s = std.nextLine();
			if (s=="") {
				massive1.add (s);
				massive.add (massive1);
				//continue;
			}
			String [] splited = s.split (" ");	
			for (int i=0; i<splited.length; i++) {
				massive1.add (splited[i]);
			}
			massive.add (massive1);	
		 }
		for (int i=massive.size()-1; i>=0; i--){
			for (int j=massive.get(i).size()-1; j>=0; j--){
				System.out.print (massive.get(i).get(j));
				System.out.print (' ');
			}
		System.out.println ();
		}
	}
}
//String[] s=s1.split(" ");