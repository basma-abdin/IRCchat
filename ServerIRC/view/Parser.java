package view;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parser {
	private List<String> commands ;
	private String command,userName,prefixe;
	private boolean hasPrefixe,Error;
	private Listtokens listtoken ;
	private Matcher result;
	private Pattern message;
	private ArrayList<String> params;
	private TreeMap<String, Pattern> tokens;
	String cmd;
	public Parser() {
		listtoken = new Listtokens();
		tokens = listtoken.getTokens();
		commands = listtoken.getCommands();
		hasPrefixe = false ;
	}


	public void parser (String s) {
		cmd = s;
		String [] analyse;
		message =  tokens.get("message");
		result =  message.matcher(cmd);
		System.out.println(s);
		if (result.matches()) {
			if (s.charAt(0)==':') {
				analyse = cmd.split(" ",3);
				System.out.println(analyse[0]);
				prefixe = analyse[0].substring(1, analyse[0].length());
				System.out.println(prefixe);
				if (isMatch(prefixe, "prefixe")) {
					hasPrefixe = true;
					System.out.println("prefixe analyse" );
					userName = isMatch(prefixe.split("!|@")[0], "nickname")?prefixe.split("!|@")[0]:"error";
					System.out.println("username :"+userName);
					if(commands.contains(analyse[1])){
						command = analyse[1];
						System.out.println("command:"+command);
					}else error("COMMAND DONT EXISTE");
				}else error("PREFIXE DONT MATCH");
				}else {
					analyse = cmd.split(" ",2);
					System.out.println(analyse[0]);
					if( commands.contains(analyse[0])){
						command = analyse[0];
						System.out.println("command here [0]"+command);
					}else error("Message");
				}
				parseParameters(cmd);
			}
			else 
			{
				error("REWRITE CODE");
			}
		}
		
		
		
	private void error(String s) {
			Error = true; command = s;//"UNKNOWNCOMMAND";
			System.out.println("REWRITE CODE");
			
	}			

	private void parseParameters(String cmd2) {	
		String cmdLocal;String [] s = new String [1];
		params = new ArrayList<>();
		if(!Error) {	
			cmdLocal = (hasPrefixe)? cmd2.substring(command.length()+prefixe.length()+2,cmd2.length()):cmd2.substring(command.length(),cmd2.length());
			System.out.println("cmd local:"+cmdLocal+":");
			
			if (cmdLocal.length() <= 1 ) {
				params.add(0,"");
			}
			else{
				if(isMatch(cmdLocal, "params")) {
					if (command.equals("JOIN") || command.equals("PART")) {
						if (isMatch(cmdLocal, "join")) s = cmdLocal.split(" |,");
						else  {command = "NOSUCHCHANNEL"; params.add(0,"");s[0]=""; }
					}
					else { s = cmdLocal.split(" "); }
				int j=0, i=0 ;
				while (j<s.length) {
					if (!s[j].equals("")) {
						params.add(i,s[j]);
						i++;
					}
					j++;
				}
				}
				
				if (command.equals("USER") && params.size()==3 ) {
					if (!isMatch(params.get(1), "mode")) {
						command="UMODEUNKNOWNFLAG";
					}
				}
				if (command.equals("MODE") && params.size()==2) {
					if(!isMatch(params.get(1), "mode")) {
						command="UMODEUNKNOWNFLAG";
					}
				}
			
			
			
			}
			
			
		}
		
		
	}

	public void reInitialize () {
		hasPrefixe = false;
		Error = false;
		command="";
		userName= "";
	}

	public String getCommand() {
		return command;
	}


	public String getUserName() {
		return userName;
	}


	public ArrayList<String> getParams() {
		return params;
	}

	public boolean isHasPrefixe() {
		return hasPrefixe;
	}

	public boolean isMatch (String toMatch  , String key) {
		 return toMatch.matches(tokens.get(key).toString()); 
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}