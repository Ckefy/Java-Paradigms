import java.util.ArrayList;
public class Reverse {
	public static void main (String[] args) {
		ArrayList <ArrayList <Integer>> massive = new ArrayList <>();
		for (int i=0; i<3; i++){
			ArrayList <Integer> massive1 = new ArrayList <>();
			for (int j=0; j<3; j++)	{
				massive1.add (i+j);
			}
			massive.add (massive1);
		}
		//massive.add (new Integer (2));
		System.out.println (massive.get (2).get (1));
	}
}