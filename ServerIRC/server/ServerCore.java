package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controler.HandleClient;
import model.ChannelModel;
import model.ChatModel;

public class ServerCore extends Thread {
	private int port;
	ServerSocket ss;
	private boolean stop = false;
	private IChatLogger logger=null;
	private ServerFrame frame;
	
	public ServerCore(int port,ServerFrame f) throws IOException {
	this.port = port;
	this.frame=f;
	logger = new TextChatLogger(frame);
	logger.systemMessage("IRC Server is started.!");
	this.start();
	}
	
	public void run() {
		try (ServerSocket ss = new ServerSocket(port)) {
		ss.setSoTimeout(1000);
		while (!stop){
		try {
		Socket s = ss.accept();
		logger.clientConnected(s.toString());
		new Thread(new HandleClient(s, logger)).start();
		} catch (SocketTimeoutException ex) {
		}
		}
		} catch (IOException e) {
		System.out.println("Could not bind port " + port);
		Logger.getLogger(ServerCore.class.getName()).log(Level.SEVERE,null, e);
		}
	}
	public synchronized void finish() {
		ChatModel.clearAll();
		ChannelModel.clearAll();
		stop = true;
		
		}
}
