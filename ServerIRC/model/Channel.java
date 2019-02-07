package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Channel {
	 final private Map<String, ChatModelEvents> chUserlist= new TreeMap<>();
	
	String chName,key="",topic="";
	ArrayList <String> bannedUser,invetedUser,masters,allowed,replies;
	int limitNumber=0 , error=0,errorCh=0,rp=0,errorTopic=0;
	boolean isSecret=false,isModerated=false;
	public Channel(String cn, String u) {
		chName=cn;
		bannedUser = new ArrayList<>();
		invetedUser =new ArrayList<>();
		allowed =new ArrayList<>();
		masters= new ArrayList<>();
		masters.add(u);
	}
	
	public void registerUser(String user, ChatModelEvents handler) {
		chUserlist.put(user, handler);	
		notifyUserListChanged();
	}
	
	public boolean canEnter(String ...params) {
		if (hasUser(params[0])) {error = 25;return false;}
		else if (!hasBannedUser() && !hasInvitedUser() &&!hasLimitNumber() && !hasKey()) return true;
		else if (hasBannedUser() && bannedUser.contains(params[0])) {error=12; return false;}
		else if (hasInvitedUser() && !invetedUser.contains(params[0])) {error=8;return false;}
		else if (hasKey() && !params[1].equals(key)) {error =13; return false;}
		else if (chUserlist.size()==limitNumber) {error =9; return false;}
		else return true;
	}
	
	private void notifyUserListChanged() {
		chUserlist.values().forEach(c->c.sendRequestNotifyUsers(chName));
	}
	
	public int getError() {
		return error;
	}
	public int getchError() {
		return errorCh;
	}
	public int getReply() {
		return rp;
	}
	
	public ArrayList<String> getReplies(){
		return replies;
	}
	
	
	public void setKey(String k) {
		key=k;
	}
	public void sendChatMessage(String name ,String message,String channel) {
		for (ChatModelEvents c:chUserlist.values()) {
			if (!c.getName().equals(name)) c.messageSent(name,message,channel);
		}
	}
	public boolean hasTopic() {
		return !topic.equals("");
	}
	public boolean hasUser(String u) {
		return chUserlist.containsKey(u);
	}
	public void unRegisterUser(String name) {
		chUserlist.remove(name);
	}
	//secret + moderat limit
	public boolean modelManeger(String name,ArrayList<String> l) {
		String n;
		replies=new ArrayList<>();
		if (!isMaster(name)) {
		 if ((ModeChanel.getMode(l.get(1)) == ModeChanel.O)) {rp=9;
			replies.add(masters.get(0));return true;}
			errorCh = 27;return false;}
		else {
			replies.add(ModeChanel.toString(ModeChanel.getMode(l.get(1))));
			rp=10;
		switch (ModeChanel.getMode(l.get(1))) {
		case po:
			n = l.get(2);
			if(!chUserlist.containsKey(n)) {errorCh=26; return false;}
			else {masters.add(n); return true;}
		case mo:
			n = l.get(2);
			if(!chUserlist.containsKey(n)) {errorCh=26;return false;}
			else {masters.remove(n);return true;}
		case pv:
			n = l.get(2);
			if(!chUserlist.containsKey(n)) {errorCh=26;return false;}
			else {allowed.add(n);return true;}
		case mv:
			n = l.get(2);
			if(!chUserlist.containsKey(n)) {errorCh=26;return false;}
			else {allowed.remove(n);return true;}
		case ps:
			isSecret = true;
			return true;
		case ms:
			isSecret=false;
			return true;
		case pm:
			isModerated = true;
			return true;
		case mm:
			isModerated=false;
			return true;
		case pk:
			if(hasKey()) {errorCh = 28;return false;}
			else {key= l.get(2); return true;}
		case mk:
			key= "";  return true;
		case pb:
			if(l.size()<=2) {
			if(!bannedUser.isEmpty()) {
				rp=6;
				for (String string : bannedUser) {
					replies.add(string);
				}
				return true;
			}else {errorCh=29;return false;}
			}
			else {
				n=l.get(2);
				if(!chUserlist.containsKey(n)) {errorCh=26;return false;}
				else {bannedUser.add(n);return true;}
			}
		case mb:
			if(l.size()<=2) {
				if(!bannedUser.isEmpty()) {
					bannedUser.clear();
					return true;
				}else {rp=7;return true;}
				}
				else {
					n=l.get(2);
					if(!chUserlist.containsKey(n)) {errorCh=26;return false;}
					else {bannedUser.remove(n);return true;}
				}
		case pl:
			n=l.get(2);
			limitNumber=Integer.parseInt(n);
			return true;
		case ml:
			limitNumber=0;
			return true;
		case pi:
			if (l.size()<2 ) { {errorCh=0;return false;}}
			n = l.get(2);
			if(chUserlist.containsKey(n)) {errorCh=25;return false;}
			else {invetedUser.add(n); return true;}
		case mi:
			n = l.get(2);
			invetedUser.remove(n);
			return true;
		case I:
			if (invetedUser.isEmpty()) {rp=8;return true;}
			else {
				for (String string : invetedUser) {
					replies.add(string);
				}
				rp=8;
				return true;
			}
		
		default:
			errorCh = 30;
			return false;
		}
		}
	}
	private boolean isMaster(String name) {
		return masters.contains(name);
	}
	private boolean hasBannedUser() {return !bannedUser.isEmpty();}
	private boolean hasInvitedUser() {return !invetedUser.isEmpty();}
	//private boolean hasMasters() {return masters.size()>1;}
	public boolean hasAllowdUser() {return !allowed.isEmpty();}
	private boolean hasLimitNumber() {return limitNumber!=0;}
	private boolean hasKey() {return !key.equals("");}
	
	
	
	
	
	public boolean canSend(String name) {
		if(hasBannedUser()) { if (bannedUser.contains(name)); error=34; return false;}
		else if (isModerated && hasAllowdUser()) {if (!allowed.contains(name)) error=34; return false;}
		else if (isModerated) {if (!masters.contains(name)) error=34; return false;}
		return true;
	}
	
	public boolean showToUser(String name) {
		if (isSecret && hasInvitedUser()) {
			if (!invetedUser.contains(name)) return false;
		}
		else if(isSecret && !hasInvitedUser()) return false;
		 return true;
	}
	public ArrayList<String> getUserList() {
		ArrayList<String> tmp = new ArrayList<>();
		for (String user : chUserlist.keySet()) {
			tmp.add(user);
		}
		return tmp;
	}
	public Collection<String> getNamesList(){
		return chUserlist.keySet();
	}
	
	public boolean setTopic(String topic2,String name) {
		if (!chUserlist.containsKey(name)) {errorTopic =32; return false ; }
		if (!masters.contains(name)) {errorTopic=27; return false;}
		if(masters.contains(name)) {
			if (topic2.equals(":")) {topic="";return true;}
			else {topic=topic2;return true;}
		}
		return false;
	}
	public String getTopic() {
		return topic;
	}
	public int getErrTopic() {
		return errorTopic;
	}
	public boolean isSecret() {return isSecret;}

	public void removeUser(String name) {
		chUserlist.remove(name);
		notifyUserListChanged();
	}
	
}
