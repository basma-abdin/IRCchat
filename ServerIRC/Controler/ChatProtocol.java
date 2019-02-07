package Controler;
import java.util.ArrayList;

public interface ChatProtocol {

	default void sendPass(String password) {}

	default void sendName(String string) {}

	default void sendUser(ArrayList<String> params) {}

	default void sendOper(ArrayList<String> params) {}

	default void sendError(int i) {}

	default void sendMode(ArrayList<String> params) {}

	default void sendJoin(ArrayList<String> params) {}

	default void sendInfo() {}

	default void sendList() {}

	default void sendMessage(ArrayList<String> params) {}
	default void sendMessage(String c,String d , String ch) {}

	default void sendPart(ArrayList<String> params) {}

	default void sendNames(String string) {}

	default void sendNotifyChannels(){}

	default void sendNotifyUsers(String string) {}
	default void sendTopic(ArrayList<String> params) {}

	default void sendNotJoinMessage(String msg) {}

	default void sendQuit(ArrayList<String> params) {}
	
	
	
	
	
	
	
}

