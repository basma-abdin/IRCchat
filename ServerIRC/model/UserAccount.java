package model;
import java.util.ArrayList;

public class UserAccount {
	private String nickName;
	private  String password;
	private  String realName;
	private  MODE mode;
	private boolean operator=false, invisible= false , away=false;
	private  String user;
	private ArrayList<String> channels;
	public UserAccount(String n,String u ,String r , MODE m) {
		nickName = n;
		user = u;
		realName = r;
		mode = m;
		channels = new ArrayList<>();
	}
	public  String getNickName() {
		return nickName;
	}
	public void setNickName(String nic) {
		nickName = nic;
	}
	public  String getPassword() {
		return password;
	}
	public  void setPassword(String pass) {
		password = pass;
	}
	public String getRealName() {
		return realName;
	}
	public  void setRealName(String real) {
		realName = real;
	}
	public MODE getMode() {
		return mode;
	}
	public  void setMode(MODE m) {
		mode = m;
		modeManeger();
	}
	public String getUser() {
		return user;
	}
	public void setUser(String u) {
		user = u;
	}
	@Override
	public String toString() {
		
		return "nickname = "+nickName+"/user = "+user+"/rN = "+realName+"/pass = "+password+"/mode = "+mode.toString();
	}
	public void addChannel(String ch) {
		channels.add(ch);
	}
	public void partChannel(String ch) {
		channels.remove(ch);
		channels.clear();
	}
	public boolean inChannel(String ch) {
		return channels.contains(ch);
	}
	public ArrayList<String> getChannels() {
		return channels;
	}
	public void partAllChannel() {
		channels.clear();		
	}
	public void modeManeger() {
		switch (mode) {
		case po:
			operator=true;
			break;
		case mo:
			operator = false;
			break;
		case pi:
			invisible = true;
			break;
		case mi:
			invisible = false;
			break;
		case pa:
			away = true;
			break;
		case ma:
			away = false;
			break;
		default:
			break;
		}
	}
	public boolean isInvisible() {return invisible;}
	public boolean isAway() {return away;}
	public boolean isOperator() {return operator;}
	
}
