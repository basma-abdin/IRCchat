package model;

public enum ModeChanel {
	
	po,mo,ps, ms, pp , mp, pk, mk, pb , mb, pi , mi ,I, pl, ml/*Limit*/ ,O/*Ocreator*/,pm,mm,pv,mv;
	
	public static ModeChanel getMode(String s) {
		switch (s) {
		case "+o":
			return po;
		case "-o":
			return mo;
		case "+s":
			return ps;
		case "-s":
			return ms;
		case "+p":
			return pp;
		case "-p":
			return mp;
		case "+k":
			return pk;
		case "-k":
			return mk;
		case "+b":
			return pb;
		case "-b":
			return mb;
		case "+i":
			return pi;
		case "-i":
			return mi;
		case "I":
			return I;
		case "+l":
			return pl;
		case "-l":
			return ml;
		case "O":
			return O;
		case "+m":
			return pm;
		case "-m":
			return mm;
		case "+v":
			return pv;
		case "-v":
			return mv;
		default:
			return pi;
		}
	}
	public static String toString(ModeChanel s) {
		switch (s) {
		case po:
			return "Channel operator";
		case ps:
			return "Secret channel";
		case pp:
			return "Private channel";
		case pm:
			return "Channel is moderated";
		case pi:
			return "Only users with invites may enter the channel.";
		case pl:
			return "Limits number of users able to be on channel ";
		case pb:
			return "Bans hostmasks from channel";
		case pk:
			return "Channels has key ";
		case pv:
			return "Gives a user voice status on channel ";
		default:
			return ""; 
		}
		
	}

}

