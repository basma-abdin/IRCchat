package model;

import java.util.Set;
import java.util.TreeMap;

import Controler.HandleClient;

public class ChatModel {
	private static final TreeMap<String, ChatModelEvents> clientList = new TreeMap<>();

	public static boolean existeNick(String n) {
		return clientList.containsKey(n);
	}

	public static void renameNickname(String oldname, String newname, HandleClient h) {
		clientList.remove(oldname);
		clientList.put(newname, h);
		clientList.get(newname).renameSent(newname);
	}

	public static void registerNickname(String tmpN, HandleClient handleClient) {
		clientList.put(tmpN, handleClient);
	}

	public static synchronized Set<String> userList() {
		return clientList.keySet();
	}
	public static void clearAll() {
		clientList.clear();
	}

	public static void notifyChangedChannels() {
		for (ChatModelEvents user : clientList.values()) {
			user.sendNotifyChannels();
		}
		
	}

	public static void sendMessage(String from, String to, String msg) {
		clientList.get(to).messageSentPrivat(from,to, msg);
	}

	public static void unregisterUser(String name) {
		if (existeNick(name)) {
			clientList.remove(name);
		}
	}
	
}
