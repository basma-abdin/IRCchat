

import java.io.OutputStream;
import java.io.PrintWriter;

public class ChatOutput
{
  private PrintWriter os = null;
  
  public ChatOutput(OutputStream out) throws java.io.IOException {
    os = new PrintWriter(out, true);
  }
  
  public void askConnection() {}
  
  public void sendMessage(String msg) {
	    os.println(msg);
  }
  
  public void sendName(String name) {
    os.println("/NICK "+name);
  }
  
  public void sendJoinRoom(String selec) {
	    os.println("/JOIN "+selec);
  }
	    
 public void sendAskUserList()
  {
    os.println("/NAMES");
  }
  
  public void askRoomUsersList(String selec) {
	os.println("/NOTIFYUSERS "+selec);
  }
  
  public void sendQuit() {
    os.println("/QUIT");
  }
  
  public void sendPrivateMessage(String to, String msg) {
    os.println("/PRIVMSG "+to+" "+msg);
  }
  
  public void sendAskRoomList() {
    os.println("/LIST ");
  }
  
  public void sendLeaveChannel(String selec) {
    os.println("/PART "+selec+" "+"Bye");
  }

    public void sendRequet(String reauete) {
	os.println(reauete);
  }
  
  public void sendNotifyMyhannel(){
   os.println("/NotifyMyChannel");
  }

  public void askNotifyChannels() {
	os.println("/NOTIFYCHANNELS");
  }


  public void sendMessageFromChannel(String ch, String msg) {
	os.println("/PRIVMSG "+ch+" "+msg);
  }
  
 
}