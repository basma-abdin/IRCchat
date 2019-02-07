package model;
import java.util.ArrayList;
import java.util.TreeMap;


public class UserModel {
	private static final TreeMap<String, UserAccount> listUser = new TreeMap<>();
	
	public static void registerUserNickname(String l ) {
		UserAccount uA = new UserAccount(l, "", "", MODE.pr);
		listUser.put(l, uA);
		
	}

	public static void renameNickname(String name, String tmpN) {
		UserAccount u = listUser.get(name);
		u.setNickName(tmpN);
		//UserAccount o = new UserAccount(tmpN, u.getUser(), u.getRealName(), u.getMode());
		listUser.remove(name);
		listUser.put(tmpN, u);
	}

	public static void registerUserAcount(String name, ArrayList<String> params) {
		if (!listUser.containsKey(name)) {
			registerUserNickname(name);
			
		}
		UserAccount u = listUser.get(name);
		u.setUser(params.get(0));
		u.setMode(MODE.mode_of_string(params.get(1)));
		StringBuilder realname = new StringBuilder();
		for (int i = 2; i < params.size(); i++) {
			realname.append(params.get(i));
		}
		u.setRealName(realname.toString());
		listUser.replace(name, u);
	}
	public static void unRegisterAccount(String name) {
		listUser.remove(name);
	}

	public static boolean alreadyExist(String ... params) {
		UserAccount u = listUser.get(params[0]);
		return u.getUser().equals(params[1]) && MODE.equalsMode(params[2],u.getMode()) ;
	}

	public static void changeMode(String name,String mode) {
		UserAccount u = listUser.get(name);
		u.setMode(MODE.mode_of_string(mode));
		listUser.replace(name, u);
	}

	public static String getInformation(String name) {
		return listUser.get(name).toString();
	}

	public static void changePass(String name, String mdp) {
		UserAccount u = listUser.get(name);
		u.setPassword(mdp);
		listUser.replace(name, u);
		
	}
	public static void joinNewChannel(String name,String ch) {
		listUser.get(name).addChannel(ch);
	}
	public static void PartChannel(int x,String name,String ch) {
		if (x==0)listUser.get(name).partAllChannel();
		else listUser.get(name).partChannel(ch);
		
	}
	public static boolean userHasChannel(String name,String ch) {
		return listUser.get(name).inChannel(ch);
	}
	public static ArrayList<String> userChannels(String name) {
		 return listUser.get(name).getChannels();
	}
	
	public static boolean canBeShown(String user) {
		return !listUser.get(user).isInvisible();
	}

	public static boolean isAway(String user) {
			return listUser.get(user).isAway();
	}
}
