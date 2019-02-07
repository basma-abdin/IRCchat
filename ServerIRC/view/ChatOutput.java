package view;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import Controler.ChatProtocol;

public class ChatOutput implements ChatProtocol {
	PrintWriter os;
	
	public ChatOutput(OutputStream out) throws IOException {
		this.os = new PrintWriter(out, true);
		
	}
	
	public void sendError(String Error) {
		os.println(Error);
	}

	public void sendPrefixeReplies(String s) {
		os.println(s);
	}
	public void sendReplies(String Error) {
		os.println(Error);
	}
	public void sendCanReplies(String Error,String ch) {
		os.println(Error);
		os.println(ch);
	}

	public void sendUserList(Set<String> userList) {
		System.out.println("send ui");
		for (String string : userList) {
			os.println(string);
		}
		
	}
	
	@Override
	public void sendMessage(String name,String message,String ch) {
		os.println("PRIVMSGCH");
		os.println(ch);
		os.println(name+":"+message);
	}
	public synchronized void sendchUserList(String reply,Collection<String> ulist,String r2){
		os.print(reply);
		ulist.forEach(os::println);
		System.err.println("hjjhk:"+r2);
		os.println(r2);
		}

	public void sendNotifyChannels(String sendReplies, ArrayList<String> listChannel) {
		os.print(sendReplies);
		for (String ch : listChannel) {
			os.println(ch);
		}
		os.println(".");
	}

	public void sendNotifyUsers(String sendReplies, ArrayList<String> tmp,String chName) {
		os.print(sendReplies);
		os.println(chName);
		for (String ch : tmp) {
			os.println(ch);
		}
		os.println(".");
	}

	public void sendChannelList(String r1,ArrayList<String> chanelList,String r2) {
		os.print(r1);
		for (String ch : chanelList) {
			os.println(ch);
		}
		os.println(r2);
	}

	public void sendPrivateMessage(String from, String to,String msg) {
		os.println("PRIVMSGU");
		os.println(to);
		os.println(from+":"+msg);
	}
}
