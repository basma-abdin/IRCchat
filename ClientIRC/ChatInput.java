

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;


public class ChatInput
{
    private ClientHandleConnection handler;
    private InputStream in;
    private boolean stop;
    
    public ChatInput(final InputStream in, final ClientHandleConnection handler) {
        this.handler = null;
        this.stop = false;
        this.in = in;
        this.handler = handler;
    }
    
    public void stop() {
        this.stop = true;
    }
    
    public void doRun() throws IOException {
        Throwable t = null;
        String line;
        Pattern error = Pattern.compile("ERR_\\w");
        Pattern reply = Pattern.compile("RPL_\\w");
       
        try {
            final BufferedReader is = new BufferedReader(new InputStreamReader(this.in));
            try {
                while (!this.stop) {
                	
                   line = is.readLine();
                  
                    if (error.matcher(line).find()) {
                    	line = is.readLine();
                    	this.handler.sendResponseReceived(line,1);
                    }
                    else if(line.equals("RPL_NOTIFYCHANNEL")) {
                    	final ArrayList<String> channelList = new ArrayList<String> ();
                    	String x3;
                        while (!(x3 = is.readLine()).equals(".")) {
                        	 channelList.add(x3);
                        }
                    	this.handler.notifyChannels(channelList);
                	}
                    else if (line.equals("RPL_NOTIFYUSERS")) {
                        final ArrayList<String> userList = new ArrayList<String>();
                        final String channelName = is.readLine();
                        String x;
                        while (!(x = is.readLine()).equals(".")) {
                            userList.add(x);
                        }
                    	this.handler.notifyUsers(channelName, userList);
                    }
                    else if (line.equals("RPL_TOPIC")) {
                    	String ch="" ,  topic="";
                    	String x= is.readLine();
                    	boolean f =false ;
                    	for (int i = 0; i < x.length(); i++) {
                    		if (x.charAt(i)== ':') f=true;
                    		else if(x.charAt(i)!= ':' && !f) ch=ch+x.charAt(i);
                    		else if (x.charAt(i)!= ':' && f) topic=topic+x.charAt(i);
                    	}
                    	handler.joinSentFromServ(ch,topic);
                    }else if (line.equals("PRIVMSGCH")) {
                    	String ch=is.readLine();
                    	String msg="" , from="";
                    	String x= is.readLine();
                    	boolean f =false ;
                    	for (int i = 0; i < x.length(); i++) {
                    		if (x.charAt(i)== ':') f=true;
                    		else if(x.charAt(i)!= ':' && !f  ) from=from+x.charAt(i);
							else if (x.charAt(i)!= ':' && f ) msg=msg+x.charAt(i);
						}
                    	handler.messageChanelSent(ch,from,msg);
                    	
                    }else if (line.equals("PRIVMSG")) {
                    	line=is.readLine();
                    	handler.canSendtoChannel(line);
                    }else if (line.equals("PRIVMSGUC")) {
                    	line=is.readLine();
                    	handler.canSendPrivate(line);
                    }else if (line.equals("PRIVMSGU")){
                    	String to=is.readLine();
                    	String msg="" , from="";
                    	String x= is.readLine();
                    	boolean f =false ;
                    	for (int i = 0; i < x.length(); i++) {
                    		if (x.charAt(i) == ':') f=true;
                    		else if(x.charAt(i)!= ':' && !f  ) from=from+x.charAt(i);
							else if (x.charAt(i)!= ':' && f ) msg=msg+x.charAt(i);
						}
                    	handler.messagePrivateSent(to,from,msg);
                    }
                    else if (reply.matcher(line).find())
                    {	
                    	line =is.readLine();
                    	this.handler.sendResponseReceived(line,0);
                    }
                    else if (line.charAt(0)==':')
                    {	
                    	continue;
                    }
                    else {
                    	this.handler.sendResponseReceived(line,0);
                    }
                }
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        finally {
            if (t == null) {
                final Throwable t2 = null;
                t = t2;
            }
            else {
                final Throwable t2 = null;
                if (t != t2) {
                    t.addSuppressed(t2);
                }
            }
        }
    }
}
