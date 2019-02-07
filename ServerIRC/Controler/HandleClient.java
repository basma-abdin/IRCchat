package Controler;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import model.ChannelModel;
import model.ChatModel;
import model.ChatModelEvents;
import model.RepliesErrorsModel;
import model.UserModel;
import server.IChatLogger;
import view.ChatInput;
import view.ChatOutput;

public class HandleClient implements Runnable, ChatProtocol, ChatModelEvents {
	private final Socket s;
	private ChatOutput cho;
	private ChatInput chi;
	private String name = "";
	private String mdp = "",actualChannel="";
	private String user ="";
	//private boolean inChannel=false;
	String host ;
	private IChatLogger logger = null;
	private ClientState state = ClientState.ST_INIT;
	public static boolean stop = false;
	
	private enum ClientState {
		ST_INIT, ST_NORMAL
	};

	public HandleClient(Socket s, IChatLogger logger) throws IOException {
		this.s = s;
		this.logger = logger;
		host = s.getInetAddress().getHostName();
	}

	@Override
	public void run() {
		try (Socket s1 = s) {
			cho = new ChatOutput(s1.getOutputStream());
			chi = new ChatInput(s1.getInputStream(), this);
			chi.doRun();
		} catch (IOException ex) {
			if (!stop) {
				finish();
			}
		}

	}

	private void finish() {
		if (!stop) {
			stop = true;
			try {
				s.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (name != null) {
		//	unRegister();
			
			}
		}
	}
	
	private void unRegister() {
		ChatModel.unregisterUser(name);
		UserModel.unRegisterAccount(name);
		ChannelModel.unRegister(name);
		logger.systemMessage("Client disconnected"+name);
		
	}

	@Override
	public void sendPass(String password) {
		if (state == ClientState.ST_INIT) return;
		if (password.equals("")) {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"PASS"));
			
		}
		else if (password.equals(mdp)) {cho.sendError(RepliesErrorsModel.sendError(1,null));
		}
		else {
			mdp = password; 
			UserModel.changePass(name, mdp);
			cho.sendReplies("Your password is"+mdp);
		}
	}
	
	@Override
	public void sendName(String newname) {
		if (newname.equals("")) {
			cho.sendError(RepliesErrorsModel.sendError(2, null));}
		else if (!ChatModel.existeNick(newname) && state == ClientState.ST_NORMAL) {
			if (!name.equals(newname)) {
				UserModel.renameNickname(name,newname);
				ChatModel.renameNickname(name,newname,this);
				cho.sendReplies("you changed nick to "+newname);
				logger.systemMessage("USER changed his name "+name);
			}
			else {
				return;
			}
		}
		else if (ChatModel.existeNick(newname)) {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(3, newname));// err_inuse;
			}
		else {
			registerNickname(newname);
			logger.systemMessage("New user is registred");
		}
	}

	private void registerNickname(String newname) {
		
		ChatModel.registerNickname(newname,this);
		
		UserModel.registerUserNickname(newname);
		name = newname;
		state= ClientState.ST_NORMAL;
		ArrayList<String> paramsreplies = new ArrayList<>(); 
		String usr = (user.equals(""))?"":user;
		paramsreplies.add("Nick");
		paramsreplies.add(newname);
		cho.sendPrefixeReplies(RepliesErrorsModel.sendPrefixeReplies(name,usr,host,paramsreplies));
		cho.sendReplies(RepliesErrorsModel.sendReplies(0, ""));
		cho.sendReplies("you are known as "+newname);
	}
	
	@Override
	public void renameSent(String s) {
		ArrayList<String> paramsreplies = new ArrayList<>(); 
		String usr = (user.equals(""))?"":user;
		paramsreplies.add(s);
		cho.sendPrefixeReplies(RepliesErrorsModel.sendPrefixeReplies(name,usr,host,paramsreplies));
		name = s;
		
	}
	
	@Override
	public void sendUser(ArrayList<String> params) {
		if (params.get(0).equals("") || params.size() < 3)  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"USER"));
		}
		else if( state == ClientState.ST_INIT) {
			if (!ChatModel.existeNick(params.get(0))) {
				user= params.get(0);
				name = params.get(0);
			UserModel.registerUserAcount(name,params);
			ChatModel.registerNickname(name, this);
			state = ClientState.ST_NORMAL;
			cho.sendReplies(RepliesErrorsModel.sendReplies(0, name,user,host));
			}
			else cho.sendError(RepliesErrorsModel.sendErrorCommand(3, params.get(0)));
		}
		else if (state == ClientState.ST_NORMAL) {

			user =params.get(0);
			if (UserModel.alreadyExist(name,user,params.get(1),params.get(2))) cho.sendError(RepliesErrorsModel.sendError(1, null));
			else {
				UserModel.registerUserAcount(name, params);

				logger.systemMessage("New user registred");
				cho.sendReplies(RepliesErrorsModel.sendReplies(0, name,user,host));
			}
		}
	}
	
	@Override
	public void sendOper(ArrayList<String> params) {
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		if (params.get(0).equals("") || params.size() < 2)  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"OPER"));
		}
		else if (!ChatModel.existeNick(params.get(0))) cho.sendError(RepliesErrorsModel.sendError(22, null));
		else if (mdp=="") cho.sendError("ERR_UNREGISTREDPASS");
		else if(!params.get(1).equals(mdp)) cho.sendError(RepliesErrorsModel.sendError(20, null));
		else {
			UserModel.changeMode(name,"o");
			cho.sendReplies(RepliesErrorsModel.sendReplies(1,""));
		}
	}

	@Override
	public void sendMode(ArrayList<String> params) {
		String []x ;
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		if(params.get(0).equals("") || params.size()<2) cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"MODE"));
		else {
			switch (params.get(0).charAt(0)) {
			case '#': //channel mode
				if (!ChannelModel.existeChannel(params.get(0))) 
					cho.sendError(RepliesErrorsModel.sendErrorCommand(19, params.get(0)));
				else {
					if(ChannelModel.sendMode(name, params)) {
						if (ChannelModel.getchRp(params.get(0)) != 0 ) {
						ArrayList<String> tmp=ChannelModel.getReplies(params.get(0));
						x = new String [ChannelModel.getReplies(params.get(0)).size()];
						for (int i=0;i<x.length ;i++) {
							x[i]=tmp.get(i);
						}
						cho.sendReplies(RepliesErrorsModel.sendReplies(ChannelModel.getchRp(params.get(0)),x));
					}}
					else 
						cho.sendError(RepliesErrorsModel.sendErrorCommand(ChannelModel.getchError(params.get(0)), params.get(0)));
				
				}
				break;
			default:
				if (!ChatModel.existeNick(params.get(0))) cho.sendError(RepliesErrorsModel.sendError(22, null));
				else {
					UserModel.changeMode(name, params.get(1));
					cho.sendReplies(RepliesErrorsModel.sendReplies(2,params.get(1)));
				}
				break;
			}
			}	
	}
	

	@Override
	public void sendError(int i) {
		cho.sendError(RepliesErrorsModel.sendError(i, null));
	}

	@Override
	public void sendInfo() {
		cho.sendError(UserModel.getInformation(name));
	}

	@Override
	public void sendList() {
		cho.sendChannelList(RepliesErrorsModel.sendReplies(16, ""),ChannelModel.getChanelList(),RepliesErrorsModel.sendReplies(17, ""));
	}
	@Override
	public void sendJoin(ArrayList<String> params) {
		ArrayList<String> keys = new ArrayList<>();
		ArrayList<String> channels = new ArrayList<>();
		String key="";
		boolean result= false;
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		else if (params.get(0).equals("") || params.size() < 1)  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"JOIN"));
		}
		else if (params.get(0).equals("0")) {channels.add("all");sendPart(channels);}
		//test  si il y a plusieurs chaines 
		else {
			for (String string : params) {
				if (string.charAt(0) == '+' || string.charAt(0)=='#' || string.charAt(0)=='&') channels.add(string);
				else keys.add(string);
			}
		for (int i = 0 ; i<channels.size();i++) {
			key = (keys.size()>=i+1)?key = keys.get(i):"";
			 	 if(ChannelModel.existeChannel(channels.get(i))) 
			 	 {	
					result=ChannelModel.enterChannel(channels.get(i),name,this,key);
					}
				else
					{
					result=ChannelModel.createChannel(channels.get(i),name,this,key);
					}
						 
				if (!result) cho.sendError(RepliesErrorsModel.sendError(ChannelModel.getError(channels.get(i)),null));
				else {
					UserModel.joinNewChannel(name, channels.get(i));
					actualChannel= channels.get(i);
					ArrayList<String> a = new ArrayList<>();
					a.add("JOIN");
					a.add(channels.get(i));
					cho.sendPrefixeReplies(RepliesErrorsModel.sendPrefixeReplies(name,user,host,a));

					logger.systemMessage("User "+name+" has joined "+channels.get(i));
					cho.sendReplies(RepliesErrorsModel.sendReplies(5,channels.get(i),ChannelModel.getTopic(channels.get(i))));
					sendNotifyUsers(channels.get(i));
					cho.sendReplies(RepliesErrorsModel.sendReplies(3, channels.get(i)));
					
				}
			}
		}
		

	}
	public String getName() {
		return name;
	}
	@Override
	public void sendMessage(ArrayList<String> params) {
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		else if (params.size()<1) cho.sendError(RepliesErrorsModel.sendErrorCommand(33, "PRIVMSG"));
		else if (params.size()<2 || params.get(1)=="") cho.sendError(RepliesErrorsModel.sendErrorCommand(36, ""));
		else if (params.get(0).charAt(0)=='#'|| params.get(0).charAt(0)=='+' ||params.get(0).charAt(0)=='&') {
			if(ChannelModel.existeChannel(params.get(0))) {
				if(ChannelModel.hasChUser(params.get(0), name)) {
					if(ChannelModel.canSend(name, params.get(0))) {miniSend(params.get(0), params);}
					else cho.sendError(RepliesErrorsModel.sendErrorCommand(ChannelModel.getError(params.get(0)), params.get(0)));
				}else cho.sendError(RepliesErrorsModel.sendErrorCommand(26, params.get(0)));
			}else cho.sendError(RepliesErrorsModel.sendErrorCommand(10, params.get(0)));
		}
		else {
			if(!ChatModel.existeNick(params.get(0))) cho.sendError(RepliesErrorsModel.sendErrorCommand(35, params.get(0)));
			else if (UserModel.isAway(params.get(0))) cho.sendReplies(RepliesErrorsModel.sendReplies(18, ""));
			else sendPrivateMessage(params.get(0),params);
		}
	}
	private void miniSend(String ch,ArrayList<String> msg) {
		cho.sendCanReplies("PRIVMSG",ch);
		StringBuilder m = new StringBuilder();
		for (int i = 0; i < msg.size(); i++) {
			if (i!= 0) { m.append(msg.get(i)); m.append(" ");}
		}
		ArrayList<String> a = new ArrayList<>();
		a.add("PRIVMSG");
		a.add(ch);
		a.add(m.toString());
		cho.sendPrefixeReplies(RepliesErrorsModel.sendPrefixeReplies(name,user,host,a));
		ChannelModel.sendMessage(name, ch, m.toString());
	}
	
	private void sendPrivateMessage( String to, ArrayList<String> p) {
		cho.sendCanReplies("PRIVMSGUC",to);
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < p.size(); i++) {
			if (i!= 0) {msg.append(p.get(i)); msg.append(" ");}
		}
		ChatModel.sendMessage(name,to,msg.toString());
	}

	@Override
	public void messageSent(String name,String message,String ch) {
		cho.sendMessage(name,message,ch);
	}
	@Override
	public void messageSentPrivat(String from,String to, String msg) {
		cho.sendPrivateMessage(from,to,msg);
	}
	
	public void sendPart(ArrayList<String> params) {
		ArrayList<String> channels = new ArrayList<>();
		StringBuilder msg = new StringBuilder();
		msg.append("");
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		else if (params.get(0).equals("") || params.size() < 1)  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"PART"));
		}
		else if (params.get(0).equals("all")) {
			for (String ch : UserModel.userChannels(name)) {
				ChannelModel.sendPart(ch, name, "");	
			}
			UserModel.PartChannel(0, name, "");
		}
		else {
			for (String string : params) {
				if (string.charAt(0) == '+' || string.charAt(0)=='#' || string.charAt(0)=='&') channels.add(string);
				else msg.append(string+"");
			}
			for (String ch : channels) {
				if (!ChannelModel.existeChannel(ch)) cho.sendError(RepliesErrorsModel.sendErrorCommand(17, ch));
				else if (!ChannelModel.hasChUser(ch,name)) cho.sendError(RepliesErrorsModel.sendErrorCommand(18, ch));
				else {
					ArrayList<String> a = new ArrayList<>();
					a.add("PART");
					a.add(ch);
					cho.sendPrefixeReplies(RepliesErrorsModel.sendPrefixeReplies(name,user,host,a));
					
					ChannelModel.sendPart(ch,name,msg.toString());
					logger.systemMessage("User "+name+" has left "+ch);
					UserModel.PartChannel(1,name, ch);
					sendNotifyUsers(ch);
			 		//inChannel= UserModel.userHasChannel(name);
				}
			}
		}
	}
	
	@Override
	public void sendNames(String chString) {
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		else {
		String ch = chString.equals("")?actualChannel:chString;
		Collection<String> userlist = ChannelModel.userRequest(ch);
		if (userlist.isEmpty()) {
			cho.sendReplies(RepliesErrorsModel.sendReplies(12, ch));
		}
		else {
			
			cho.sendchUserList(RepliesErrorsModel.sendReplies(11,""),userlist,RepliesErrorsModel.sendReplies(12,chString));
	
		}}
	}
	
	@Override
	public void sendTopic(ArrayList<String> params) {
		if (state ==ClientState.ST_INIT) {
			cho.sendError(RepliesErrorsModel.sendError(23, null)); 
			return ;
		}
		else if (params.get(0).equals("") || params.size() < 1)  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(0,"TOPIC"));
		}else if (!ChannelModel.existeChannel(params.get(0)))  {
			cho.sendError(RepliesErrorsModel.sendErrorCommand(17,params.get(0)));
		}else if (params.size()<=1)  {
			String tmp= ChannelModel.getTopic(params.get(0));
			if (tmp.equals("")) cho.sendReplies(RepliesErrorsModel.sendReplies(15,params.get(0)));
			else cho.sendReplies(RepliesErrorsModel.sendReplies(5, params.get(0),":",tmp));
		}else {
			if(ChannelModel.sendTopic(params.get(0),params.get(1),name)){
				cho.sendReplies(RepliesErrorsModel.sendReplies(5, params.get(0),params.get(1)));
			}else {
				
				cho.sendError(RepliesErrorsModel.sendError(ChannelModel.getErrTopic(params.get(0)),null));
			}
		}
		
	}
	
	@Override
	public void sendNotifyChannels() {
		ArrayList<String> listChannel =  ChannelModel.notifyChannels(name);
		if (listChannel != null) {
			cho.sendNotifyChannels(RepliesErrorsModel.sendReplies(13, ""), listChannel);
		}
	}
	
	public void sendNotifyUsers(String ch) {
		ArrayList<String> userlistChannel =ChannelModel.notifyusersChannels(ch);
		ArrayList<String> tmp = new ArrayList<>();
		if (userlistChannel != null) {
			for (String user : userlistChannel) {
				if(UserModel.canBeShown(user)) tmp.add(user);
			}
			cho.sendNotifyUsers(RepliesErrorsModel.sendReplies(14, ""),tmp,ch);
		}
	}

	@Override
	public void sendRequestNotifyUsers(String ch) {
		sendNotifyUsers(ch);
	}
	
	@Override
	public void sendNotJoinMessage(String msg) {
		cho.sendError(RepliesErrorsModel.sendReplies(4,msg));
	}
	
	@Override
	public void sendQuit(ArrayList<String> params) {
		stop=true;
		unRegister();
		logger.systemMessage("User "+name +" left");
	}

}
