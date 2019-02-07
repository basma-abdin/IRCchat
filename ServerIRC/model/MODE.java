package model;

public enum MODE {
	pa,ma,pi,mi,pr,mr,po,mo;

public static MODE mode_of_string(String x) {
	if(x.equals("-i")) return mi;
	if(x.equals("+i")) return pi;
	if(x.equals("-a")) return ma;
	if(x.equals("-o")) return mo;
	if(x.equals("-r")) return mr;
	if(x.equals("+a")) return pa;
	if(x.equals("+o")) return po;
	return pr;
	
}
public static boolean equalsMode(String x , MODE p) {
	return mode_of_string(x)== p;
}
public static String toString(MODE s) {
	switch (s) {
	case pi:
		return "Invisible Mode";
	case pa:
		return "away mode";
	case po : 
		return "IRC Operator";
	case pr :
		return "normal mode";
		default:
		return ""; 
	}
	
}

}


