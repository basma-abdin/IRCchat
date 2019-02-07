package server;

public interface IChatLogger {
	public void systemMessage(String string) ;

	public void clientConnected(String string);

	public void clientDisconnected(String string, String name);

	
}
