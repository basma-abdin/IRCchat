

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandleConnection extends Thread 
{
  private ChatOutput cho = null;
  private ChatInput chi = null;
  private Socket sock = null;
  private Frame frame = null;
  
  public ClientHandleConnection(Socket s, Frame f) throws IOException {
    sock = s;
    chi = new ChatInput(sock.getInputStream(), this);
    cho = new ChatOutput(sock.getOutputStream());
    frame = f;
  }
  //// From server /////
  public void joinSentFromServ(String ch, String topic) {
  	frame.joinFromServer(ch,topic);
  }
  
  public void messageChanelSent(String ch, String from, String msg) {
		frame.messageChannelRecievied(ch,from,msg);
  }
  
  public void notifyUsers(final String chName, final ArrayList<String> userList)
  {
    frame.notifyUsers(chName, userList);
  }

  public void errorSent(String strMsg) {
    frame.errorSent(strMsg);
  }
  
  public void sendResponseReceived(String respons,int i) {
		frame.printServerResponse(respons,i);
  }
  
  public void canSendtoChannel(String ch) {
		frame.canSendToChannel(ch);
	}
	public void canSendPrivate(String to) {
		frame.canSendPrivate(to);
	}
	public void messagePrivateSent(String to, String from, String msg) {
		frame.messagePrivateRecievied(to, from, msg);
	}
	 
	public void notifyChannels(ArrayList<String> roomList) {
		    frame.notifyChannels(roomList);
	}
		  

  //// To server ///
  public void sendToServer(String requete) {
	  cho.sendRequet(requete);
  }
  
  public void sendNick(String name)
  {
    cho.sendName(name);
  }
  
  public void sendQuit()
  {
    cho.sendQuit();
    
  }
  public void sendAskUserList()
  {
    cho.sendAskUserList();
  }
  
  public void sendMessage(String msg) {
    cho.sendMessage(msg);
  }
 
  public void sendMessageFromChannelToServer(String ch, String msg) {
	  if (msg.charAt(0) == '/') cho.sendMessage(msg);
	  else cho.sendMessageFromChannel(ch,msg);	
  }
  
  public void sendJoinRoom(String selec) {
		 cho.sendJoinRoom(selec);
  }
  
  
  public void run() {
    try {
      chi.doRun();
    } catch (IOException e) {
      finish();
    }
  }
  
  public void finish()
  {
    chi.stop();
    try {
      sock.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  
  public void askNotifyChannels() {
	 cho.askNotifyChannels();
  }
  
 
  public void LeaveChannel(String selec) {
    cho.sendLeaveChannel(selec);
  }

  
  public void askRoomUsersList(String selec)
  {
    cho.askRoomUsersList(selec);
  }
  
	public void sendPrivateMessageToServer(String user,String msg) {
		cho.sendPrivateMessage(user, msg);
	}

 
}