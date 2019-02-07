package view;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Listtokens {
	private  TreeMap<String, Pattern> tokens ;
	private List<String> commands;
	public Listtokens() {
		tokens = createList();
		commands = createCommandsList();
	}
	
	private TreeMap<String, Pattern> createList() {
		TreeMap<String, Pattern> tokenLocal = new TreeMap<>();
		tokenLocal.put("letter", Pattern.compile("\\w"));
		tokenLocal.put("digit", Pattern.compile("\\d"));
		tokenLocal.put("special", Pattern.compile("[\\x5B-\\x60\\x7B-\\x7D]"));

		tokenLocal.put("user", Pattern.compile("[\\x01-\\x09\\x0B-\\x0C\\x0E-\\x1F\\x21-\\x3F\\x41-\\xFF]*"));
		tokenLocal.put("SPACE", Pattern.compile("\\x20"));
		tokenLocal.put("nospcrlfcl", Pattern.compile("[\\x01-\\x09\\x0B-\\x0C\\x0E-\\x1F\\x21-\\x39\\x3B-\\xFF]"));
		tokenLocal.put("middle", Pattern.compile(tokenLocal.get("nospcrlfcl")+"[:"+tokenLocal.get("nospcrlfcl")+"]*"));
		tokenLocal.put("trailing", Pattern.compile("[: "+tokenLocal.get("nospcrlfcl")+"]*"));
		tokenLocal.put("crlf", Pattern.compile("\\x0D\\x0A"));		

		tokenLocal.put("nickname", Pattern.compile("["+tokenLocal.get("letter")+tokenLocal.get("special")+"]["+tokenLocal.get("letter")+tokenLocal.get("digit")+tokenLocal.get("special")+"-]{0,8}"));
		tokenLocal.put("shortname", Pattern.compile("["+tokenLocal.get("letter")+tokenLocal.get("digit")+"]["+tokenLocal.get("letter")+tokenLocal.get("digit")+"-]*"));
		
		tokenLocal.put("hostname", Pattern.compile(tokenLocal.get("shortname")+"(."+tokenLocal.get("shortname")+")*"));
		tokenLocal.put("ip4addr", Pattern.compile(tokenLocal.get("digit")+"{1,3}."+tokenLocal.get("digit")+"{1,3}."+tokenLocal.get("digit")+"{1,3}."+tokenLocal.get("digit")+"{1,3}"));
		tokenLocal.put("hexdigit", Pattern.compile("(["+tokenLocal.get("digit")+"ABCDEF])"));
		tokenLocal.put("ip6addr", Pattern.compile(tokenLocal.get("hexdigit")+"+(:"+tokenLocal.get("hexdigit")+"+){7}"));
		tokenLocal.put("hostadrr", Pattern.compile("(["+tokenLocal.get("ip4addr")+tokenLocal.get("ip6addr")+"])"));
		tokenLocal.put("host", Pattern.compile("["+tokenLocal.get("hostname")+tokenLocal.get("hostadrr")+"]"));
		
		tokenLocal.put("servername", Pattern.compile("("+tokenLocal.get("hostname")+")"));
		
	
		tokenLocal.put("prefixe",Pattern.compile("("+tokenLocal.get("servername")+"|("+tokenLocal.get("nickname")+"((!"+tokenLocal.get("user")+")?@"+tokenLocal.get("host")+")?))"));
		tokenLocal.put("command", Pattern.compile("("+tokenLocal.get("letter")+")+|("+tokenLocal.get("digit")+"){3}"));

		tokenLocal.put("params", Pattern.compile("("+tokenLocal.get("SPACE")+tokenLocal.get("middle")+"){0,14}("+tokenLocal.get("SPACE")+":"+tokenLocal.get("trailing")+")?"));
		
		
		tokenLocal.put("message", Pattern.compile("(:"+tokenLocal.get("prefixe")+tokenLocal.get("SPACE")+")?("+tokenLocal.get("command")+")("+tokenLocal.get("params")+")?"));
		//MODE
		tokenLocal.put("mode", Pattern.compile("([+-]?[aiwoOrkplbsIv]*)*"));		
		//JOIN
		tokenLocal.put("channelid", Pattern.compile("[\\x41-\\x5A"+tokenLocal.get("digit")+"]{5}"));
		tokenLocal.put("chanstring", Pattern.compile("[\\x01-\\x07\\x08-\\x09\\x0B-\\x0C\\x0E-\\x1F\\x21-\\x2B\\x2D-\\x39\\x3B-\\xFF]"));
		tokenLocal.put("channel", Pattern.compile("[#+(!"+tokenLocal.get("channelid")+")&]"+tokenLocal.get("chanstring")+"*(:"+tokenLocal.get("chanstring")+")?"));
		tokenLocal.put("key", Pattern.compile("[\\x01-\\x05\\x07-\\x08\\x0C\\x0E-\\x1F\\x21-\\x7F]{0,23}"));
		
		tokenLocal.put("join", Pattern.compile(" (("+tokenLocal.get("channel")+"(,"+tokenLocal.get("channel")+")*( "+tokenLocal.get("key")+"(,"+tokenLocal.get("key")+")*)?)|0)"));
		
		
		
		
		return tokenLocal;
	}

	public TreeMap<String, Pattern> getTokens() {
		return tokens;
	}
	
	private List <String>createCommandsList()
	{
		List<String> commandLocal = new ArrayList <>();
		
		commandLocal.add("PASS");
		commandLocal.add("NICK");
		commandLocal.add("USER");
		commandLocal.add("OPER");
		commandLocal.add("QUIT");
		
		commandLocal.add("CONNECT");
		commandLocal.add("MODE");

		commandLocal.add("JOIN");
		commandLocal.add("PART");
		commandLocal.add("NAMES");
		commandLocal.add("LIST");
		commandLocal.add("INVITE");
		commandLocal.add("KICK");
		commandLocal.add("TIME");
		//commandLocal.add("TRACE");
		commandLocal.add("ADMIN");
		commandLocal.add("INFO");
		//commandLocal.add("PING");
		//commandLocal.add("PONG");
		commandLocal.add("TOPIC");
		commandLocal.add("ERROR");
		commandLocal.add("DIE");
		commandLocal.add("USERS");
		commandLocal.add("NOTIFYCHANNELS");
		commandLocal.add("NOTIFYUSERS");
		commandLocal.add("PRIVMSG");
		
		
		return  commandLocal;	
	}
	public List<String> getCommands() {
		return commands;
	}
	
	
}