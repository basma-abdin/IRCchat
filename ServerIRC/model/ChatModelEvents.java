package model;

public interface ChatModelEvents {
	public void renameSent(String s);

	public String getName();

	public void messageSent(String name,String message,String ch);
	public void sendNotifyChannels();
	public void sendRequestNotifyUsers(String ch);

	public void messageSentPrivat(String from,String to ,String msg);
}
