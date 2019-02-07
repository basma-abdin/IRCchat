package model;



import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import Controler.HandleClient;


public class ChannelModel {
	private static final TreeMap<String, Channel> channelList = new TreeMap<>();

	public static boolean existeChannel(String chName) {
		return channelList.containsKey(chName);
	}

	public static boolean createChannel(String chN, String name, HandleClient handleClient , String k) {
		channelList.put(chN, new Channel(chN,name));
		channelList.get(chN).registerUser(name, handleClient);
		if (k!= "") channelList.get(chN).setKey(k);
		ChatModel.notifyChangedChannels();
		return true;
	} 
	public static boolean enterChannel(String chName, String name , ChatModelEvents handler , String k) {
		if(!canEnter(chName,name,k)) return false ;
		else {
			channelList.get(chName).registerUser(name,handler);
			return true;
		}
		}

	private static boolean canEnter(String chName, String name,String key) {
		return channelList.get(chName).canEnter(name,key);
	}
	
	public static int getError(String chName) {
		return channelList.get(chName).getError();
	}
	public static int getchError(String chName) {
		return channelList.get(chName).getchError();
	}
	public static int getchRp(String chName) {
		return channelList.get(chName).getReply();
	}
	public static ArrayList<String> getReplies(String chName) {
		return channelList.get(chName).getReplies();
	}

	public static void sendMessage(String name,String actualChannel, String message) {
		channelList.get(actualChannel).sendChatMessage(name,message,actualChannel);
	}
	
	public static boolean canSend(String name, String actualChannel) {
		return channelList.get(actualChannel).canSend(name);
	}

	public static boolean hasTopic(String chname) {
		return channelList.get(chname).hasTopic();
	}

	public static boolean hasChUser(String ch, String user) {
		return channelList.get(ch).hasUser(user);
	}

	public static void sendPart(String ch, String name, String string) {
		channelList.get(ch).unRegisterUser(name);
		if (string!="")sendMessage(name, ch, "LEAVING : "+string);
	}
	public static boolean sendMode(String name,ArrayList<String> l) {
		return channelList.get(l.get(0)).modelManeger(name,l);
	}

	public static Collection<String> userRequest(String ch) {
		return channelList.get(ch).getNamesList();
		
	}

	public static void clearAll() {
		channelList.clear();	
	}
	public static ArrayList<String> notifyChannels(String name){
		ArrayList< String> local =new ArrayList<>();
		if (!channelList.isEmpty()) {
		for (String ch : channelList.keySet()) {
			if (channelList.get(ch).showToUser(name)) local.add(ch);
		}
		return local;
		}
		else return null;
	}

	public static ArrayList<String> notifyusersChannels(String cch) {
		channelList.get(cch).hasAllowdUser();
		return channelList.get(cch).getUserList();
	}

	public static boolean sendTopic(String ch, String topic,String name) {
		
		return channelList.get(ch).setTopic(topic,name);
	}

	public static String getTopic(String toString) {
		return channelList.get(toString).getTopic();
	}
	public static int getErrTopic(String chName) {return channelList.get(chName).getErrTopic(); }
	
	public static ArrayList<String> getChanelList(){
		ArrayList<String> local=new ArrayList<>();
		if (!channelList.isEmpty()) {
			for (String ch : channelList.keySet()) {
				if (!channelList.get(ch).isSecret()) local.add(ch);
			}
			return local;
			}
			else return null;
		
	}

	public static void unRegister(String name) {
		for (String ch : channelList.keySet()) {
			if (hasChUser(ch, name)) {
				deleteUser(ch,name);
			}
		}
		
	}

	private static void deleteUser(String ch, String name) {
		channelList.get(ch).removeUser(name);
	}
	
	
	
	
}
