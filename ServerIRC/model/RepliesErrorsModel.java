package model;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;



public class RepliesErrorsModel {
	private static final List<String> errorsCode =createErrorsCode();
	private static final List<String> repliesCode =createRepliesCode();
	private static final TreeMap<String, String> errors = createErrors();
	private static final TreeMap<String, String> replies =creatReplies() ;
	
	
	private static List<String> createErrorsCode() {
		List<String> localy = new ArrayList<>();
		//Erreur de PASS et USER
		localy.add(0,"ERR_NEEDMOREPARAMS" );
		localy.add(1,"ERR_ALREADYREGISTRED" );
		//Erreur de NICK
		localy.add(2,"ERR_NONICKNAMEGIVEN" );
		localy.add(3,"ERR_NICKNAMEINUSE" );
		localy.add(4,"ERR_UNAVAILRESOURCE" );
		localy.add(5,"ERR_ERRONEUSNICKNAME" );
		localy.add(6,"ERR_NICKCOLLISION" );
		localy.add(7,"ERR_RESTRICTED" );
		//Erreur de JOIN
		localy.add(8,"ERR_INVITEONLYCHAN" );
		localy.add(9,"ERR_CHANNELISFULL" );
		localy.add(10,"ERR_NOSUCHCHANNEL" );
		localy.add(11,"ERR_TOOMANYTARGETS" );
		localy.add(12,"ERR_BANNEDFROMCHAN" );
		localy.add(13,"ERR_BADCHANNELKEY" );
		localy.add(14,"ERR_BADCHANMASK" );
		localy.add(15,"ERR_TOOMANYCHANNELS" );
		localy.add(16,"ERR_UNAVAILRESOURCE" );
		//Erreur de PART
		localy.add(17,"ERR_NOSUCHCHANNEL" );
		localy.add(18,"ERR_NOTONCHANNEL" );
		localy.add(19,"ERR_NOSUCHCHANNEL" );
		
		//OPEr
		localy.add(20,"ERR_PASSWDMISMATCH");
		//MODE
		localy.add(21,"ERR_UMODEUNKNOWNFLAG");
		localy.add(22,"ERR_USERSDONTMATCH");
		localy.add(23,"ERR_NOTREGISTERED");
		localy.add(24,"ERR_PARAMETERS");
		localy.add(25,"ERR_INCHANNEL");
		//MODE channel
		localy.add(26,"ERR_USERNOTINCHANNEL");
		localy.add(27,"ERR_CHANOPRIVSNEEDED");
		localy.add(28,"ERR_KEYSET");
		localy.add(29,"ERR_NOBANLIST");
		localy.add(30,"ERR_UNKNOWNMODE");
		localy.add(31,"ERR_NOTALLOW");
		localy.add(32,"ERR_NOTONCHANNEL");
		
		//PRIMSG
		localy.add(33,"ERR_NORECIPIENT");
		localy.add(34,"ERR_CANNOTSENDTOCHAN");
		localy.add(35,"ERR_NOSUCHNICK");
		localy.add(36,"ERR_NOTEXTTOSEND");
		
		return localy;
	}
	private static TreeMap<String, String> createErrors() {
		TreeMap<String, String> locallist = new TreeMap<>();
		//PASS et USER
		locallist.put("ERR_NEEDMOREPARAMS", ":Not enough parameters");
		locallist.put("ERR_ALREADYREGISTRED", ":Unauthorized command (already registered)");
		locallist.put("ERR_UMODEUNKNOWNFLAG", ":Unknown MODE flag");
		//OPER
		locallist.put("ERR_PASSWDMISMATCH" ,":Password incorrect");
		locallist.put("ERR_UMODEUNKNOWNFLAG", ":Unknown MODE flag");
		
		//NICK
		locallist.put("ERR_NONICKNAMEGIVEN", ":No nickname given");		
		locallist.put("ERR_NICKNAMEINUSE", ":Nickname is already in use");		
		locallist.put("ERR_UNAVAILRESOURCE", ":Nick/channel is temporarily unavailable");		
		locallist.put("ERR_ERRONEUSNICKNAME", ":Erroneous nickname");		
		locallist.put("ERR_NICKCOLLISION", ":Nickname collision KILL from");		
		locallist.put("ERR_RESTRICTED", ":Your connection is restricted!");		
		//JOIN
		locallist.put("ERR_INVITEONLYCHAN", ":Cannot join channel (+i)");
		locallist.put("ERR_CHANNELISFULL", ":Cannot join channel (+l)");
		locallist.put("ERR_NOSUCHCHANNEL", ":No such channel");
		locallist.put("ERR_TOOMANYTARGETS", ":<error code> recipients. <abort message>");
		locallist.put("ERR_BANNEDFROMCHAN", ":Cannot join channel (+b)");
		locallist.put("ERR_BADCHANNELKEY", ":Cannot join channel (+k)");
		locallist.put("ERR_BADCHANMASK", ":Bad Channel Mask");
		locallist.put("ERR_TOOMANYCHANNELS", ":You have joined too many channels");
		locallist.put("ERR_UNAVAILRESOURCE", ":Nick/channel is temporarily unavailable");
		//PART
		locallist.put("ERR_NOSUCHCHANNEL", ":No such channel");
		locallist.put("ERR_NOTONCHANNEL", ":You're not on that channel");
		locallist.put("ERR_NOSUCHCHANNEL", ":No such channel");
		locallist.put("ERR_USERSDONTMATCH", ":Cannot change mode for other user");
		locallist.put("ERR_NOTREGISTERED",":You have not registered");
		
		locallist.put("ERR_PARAMETERS", "Error in parameters form");
		locallist.put("ERR_INCHANNEL","already in this channel");
		locallist.put("ERR_CHANOPRIVSNEEDED",":You're not channel operator");
		locallist.put("ERR_USERNOTINCHANNEL", ":They aren't on that channel");
		locallist.put("ERR_KEYSET", ":Channel key already set");
		locallist.put("ERR_NOBANLIST", "");
		locallist.put("ERR_UNKNOWNMODE", "");
		locallist.put("ERR_NOTALLOW", "you are not allowed on this channel");
		locallist.put("ERR_NOTONCHANNEL", "You're not on that channel");
		//PRIV MSG
		locallist.put("ERR_NORECIPIENT", ":No text to sen");
		locallist.put("ERR_CANNOTSENDTOCHAN", "Cannot send to channel");
		locallist.put("ERR_NOSUCHNICK", ":No such nick");
		locallist.put("ERR_NOTEXTTOSEND",":No text to send");
		
		
		
		
		
		
		return locallist;
	}
	
	private static List<String> createRepliesCode() {
		List<String> localy = new ArrayList<>();
		localy.add(0,"RPL_WELCOME");
		localy.add(1,"RPL_YOUREOPER");
		localy.add(2,"RPL_UMODEIS");
		localy.add(3,"RPL_JOINCHANNEL");
		localy.add(4,"RPL_NOTJOINED");
		localy.add(5,"RPL_TOPIC");
		localy.add(6,"RPL_BANLIST");
		localy.add(7,"RPL_ENDOFBANLIST");
		localy.add(8,"RPL_ENDOFINVITELIST");
		localy.add(9,"RPL_UNIQOPIS");
		localy.add(10,"RPL_CHANNELMODEISS");
		localy.add(11,"RPL_NAMREPLY");
		localy.add(12,"RPL_ENDOFNAMES");
		localy.add(13,"RPL_NOTIFYCHANNEL");
		localy.add(14,"RPL_NOTIFYUSERS");
		localy.add(15,"RPL_NOTOPIC");
		localy.add(16,"RPL_LIST");
		localy.add(17,"RPL_LISTEND");
		localy.add(18,"RPL_AWAY");
		
		return localy;
	}
	private static TreeMap<String, String> creatReplies() {
		TreeMap<String, String> locallist = new TreeMap<>();
		locallist.put("RPL_YOUREOPER",":You are now an IRC operator");
		locallist.put("RPL_WELCOME"	,"Welcome to the Internet Relay Network ");
		locallist.put("RPL_UMODEIS","your mode is");
		locallist.put("RPL_NOTJOINED", "To JOIN a channel type /JOIN #");
		locallist.put("RPL_JOINCHANNEL", "you are now talking on ");
		locallist.put("RPL_TOPIC", "");
		locallist.put("RPL_BANLIST", "");
		locallist.put("RPL_ENDOFBANLIST",":End of channel ban list");
		locallist.put("RPL_ENDOFINVITELIST"," :End of channel invite list");
		locallist.put("RPL_UNIQOPIS","");
		locallist.put("RPL_CHANNELMODEIS","");
		locallist.put("RPL_NAMREPLY","");
		locallist.put("RPL_ENDOFNAMES",":End of NAMES list");
		locallist.put("RPL_NOTIFYCHANNEL","");
		locallist.put("RPL_NOTIFYUSERS","");
		locallist.put("RPL_NOTOPIC",":No topic is set");
		locallist.put("RPL_LIST","");
		locallist.put("RPL_AWAY","This user is away");
		return locallist;
	}
	
	public static String sendError (int key , ArrayList<String> params) {
		StringBuilder sb =  new StringBuilder();
		String s = errorsCode.get(key);
		sb.append(s);
		sb.append("\n");
		if (params != null) {
			for (String string : params) {
				sb.append(string);
			}
		}
		sb.append(errors.get(s));	
		return sb.toString();
	}
	public static String sendErrorCommand (int key , String params) {
		StringBuilder sb =  new StringBuilder();
		String s = errorsCode.get(key);
		sb.append(s);
		sb.append("\n");
		sb.append(params);
		sb.append(errors.get(s));	
		return sb.toString();
	}
	public static String sendPrefixeReplies(String name, String user,String host, ArrayList<String> paramsreplies) {
		StringBuilder sb =  new StringBuilder();
		sb.append(":");
		sb.append(name);
		if (user != "") {
			sb.append("!"+user);
		}
		sb.append("@");
		sb.append(host+" ");
		for (String string : paramsreplies) {
			sb.append(string+" ");
		}
		return sb.toString();
	}
	
	public static String sendReplies(int key , String ... params) {
		String reply = repliesCode.get(key);
		StringBuilder sb = new StringBuilder();
		if (key == 13 || key==14 || key == 16 || key == 17) sb.append(reply+"\n");
		else if (key == 5) {sb.append(reply+"\n"); sb.append(params[0]+":"+params[1]);}
		else if (key == 12) {sb.append(reply+"\n");sb.append(params[0]+replies.get(reply));}
		else if (key == 11) {sb.append(reply+"\n");}
		else if (key == 3) {sb.append(reply+"\n");sb.append(replies.get(reply)+params[0]);}
		else {
			sb.append(replies.get(reply)+" ");
		if (params[0] !="") {
		for (int i = 0 ; i< params.length ; i++) {
			if (i == 0 && key==0)sb.append(params[i]+"!");
			if (i==1 && key==0) sb.append(params[i]+"@");
			else sb.append(params[i]+" ");
		}}
		}
		return sb.toString();
	}
	
}
