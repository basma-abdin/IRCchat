package view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Controler.ChatProtocol;
import Controler.HandleClient;
public class ChatInput {
	InputStream in;
	ChatProtocol handler;
	String message="";
	Parser ps = new Parser();
	
	public ChatInput(InputStream in , HandleClient h) throws IOException {
		this.in = in;
		handler =h;
	}
	
	public void doRun() throws IOException {
		try (BufferedReader is = new BufferedReader(new InputStreamReader(in))) {
			boolean stop = false;
			String line ,command, nickName;
			ArrayList<String> params;
			while (!stop) {
				System.out.println("test connectrion");
				line = is.readLine();
				System.out.println(line);
				/*if(line.charAt(0)!='/') message=line;
				else */if(line.charAt(0)=='/') line = line.substring(1, line.length());
				System.out.println(line);
				
				ps.parser(line);
				command = ps.getCommand();
				nickName = ps.getUserName();
				params = ps.getParams();	
				switch (command) {
				case "PASS" :
					handler.sendPass(params.get(0));
					 break;
				case "NICK":
					handler.sendName(params.get(0));
					break;
				case "USER":
					handler.sendUser(params);
					break;				
				case "OPER":
					 handler.sendOper(params);
					 break;	
				case "MODE":
					handler.sendMode(params);
					break;
				case "UMODEUNKNOWNFLAG"	:
					handler.sendError(21);
					break;
				 case "JOIN":	
					handler.sendJoin(params);
					break;
				 case "PARAMS":
					 handler.sendError(24);
					 break;
				 case "NOSUCHCHANNEL":
					 handler.sendError(10);
					 break;
				 case "INFO":
					 handler.sendInfo();
					 break;
				 case "LIST":
					 handler.sendList();
					 break;
				 case "PART":
					 handler.sendPart(params);
					 break;
				 case "PRIVMSG":
					 handler.sendMessage(params);
					 break;
				 case "NAMES":
					 handler.sendNames(params.get(0));
					 break;
				 case "NOTIFYCHANNELS":
					 handler.sendNotifyChannels();
					 break;
				 case "NOTIFYUSERS":
					 handler.sendNotifyUsers(params.get(0));
					 break;
				 case "TOPIC":
					 handler.sendTopic(params);
					 break;
				 case "QUIT":
					 handler.sendQuit(params);
					 break;
				 case "Message":
					String  msg=line;
					 handler.sendNotJoinMessage(msg);
					 break;
				default:
					
					break;
				}
				ps.reInitialize();
		
			}
		}
	}
}