import java.math.BigInteger;
public class SumBigInteger {
	public static void main (String[] args) {
		BigInteger answer = new BigInteger ("0");
		StringBuilder sans = new StringBuilder ();
		for (int i = 0; i < args.length; i++) {
			int j = 0;
			while (j < args[i].length()) {
				char c = args[i].charAt(j);
				if (c == '+' || c == '-' || c == '.' || (c >= '0' && c <= '9'))  {
					sans.append (args[i].charAt(j));
					j++;
				} else {
					if (sans.length() != 0) {
						String s = new String (sans);
						answer = answer.add(new BigInteger(s));
						sans.setLength (0);
					}
					j++;
				}	
				if (j >= args[i].length()) {
					if (sans.length() != 0){
						String s = new String (sans);
						answer = answer.add(new BigInteger(s));
						sans.setLength (0);

					}
				}
			}
		 if (sans.length() != 0) {
			String s = new String (sans);
			answer = answer.add(new BigInteger(s));
			sans.setLength (0);

		 }
		}
		System.out.println (answer);
	}
}