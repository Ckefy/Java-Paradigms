public class Sum {
	public static void main (String[] args) {
		int answer=0;
		String sans="";
		for (int i=0; i<args.length; i++) {
			int j=0;
			while (j<args[i].length()) {
				if (args[i].charAt(j)=='\\') {
					if (sans.length()!=0) {
						answer+=Integer.parseInt (sans);
						sans="";
					}
					j++;
					if (args[i].charAt(j)=='u')
						j+=5;
					else
						j++;
				}
				else {
					char c=args[i].charAt(j);
					if (c=='+' || c=='-' || c=='.' || c==',' || c=='e' || c=='E' || (c>='0' && c<='9'))  {
						sans+=args[i].charAt(j);
						j++;
					}
					else {
						if (sans.length()!=0) {
							answer+=Integer.parseInt (sans);
							sans="";
						}
						j++;
					}	
				}
				if (j>=args[i].length()) {
					if (sans.length()!=0){
						answer+=Integer.parseInt (sans);
						sans="";
					}
				}
			}
		}
		if (sans.length()!=0) {
			answer+=Integer.parseInt (sans);
			sans="";
		}
		System.out.println (answer);
	}
}