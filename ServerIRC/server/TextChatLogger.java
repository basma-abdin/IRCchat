package server;
public class TextChatLogger implements IChatLogger {
	 private ServerFrame frame = null;
	 public TextChatLogger(ServerFrame f) {
		 this.frame = f;
	}
	@Override
	public void systemMessage(String string) {
		frame.writeToLog(string);
	}

	@Override
	public void clientConnected(String string) {
		// TODO Auto-generated method stub
		frame.writeToLog(string);

	}

	@Override
	public void clientDisconnected(String string, String name) {
		// TODO Auto-generated method stub

	}

}
