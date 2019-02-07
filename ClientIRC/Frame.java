

import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.GroupLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import java.awt.Component;
import javax.swing.text.StyleConstants;

import java.awt.Color;
import java.util.Random;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.text.SimpleAttributeSet;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Frame extends JFrame
{
    private static final long serialVersionUID = -5529544174206949096L;
    private String nickname;
    private String ipAddress;
    private Socket socket;
    private ClientHandleConnection handler;
    public boolean connected;
    private SimpleAttributeSet privateMessageFont;
    private SimpleAttributeSet error;
    private SimpleAttributeSet messageFont;
    private SimpleAttributeSet serverMessageFont;
    private SimpleAttributeSet replyFont;
    private JButton bJoinChannel;
    private JButton bSendMessage;
    private JList<String> channels;
    private JMenu config;
    private JMenu connnect;
    private JMenu disconnect;
    private JMenu quit;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPaneChannel;
    public  JTabbedPane jTabbedPane1;
    private JTextPane jTextArea1;
    private JTextField jTextField1;
    private JTextField txtChannels;
    private JList<String> userList;
    private ArrayList<String> privatePanel= new ArrayList<>();

    
    public Frame() {
    	this.setTitle("IRC Client");
        ipAddress = "127.0.0.1";
        socket = null;
        handler = null;
        connected = false;
        privateMessageFont = new SimpleAttributeSet();
        error = new SimpleAttributeSet();
        replyFont = new SimpleAttributeSet();
        messageFont = new SimpleAttributeSet();
        serverMessageFont = new SimpleAttributeSet();
        final Random r = new Random();
        nickname = "Guest" + r.nextInt(999);
        initComponents();
        setResizable(false);
    }
    
 /////////////////////////////////////DEBUT DE LA METHODE INICOMPONENTS///////////////////////////////////////////////////////////////////////
    private void initComponents() {
        jPanel1 = new JPanel();
        jTabbedPane1 = new JTabbedPane();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextPane();
        jTextField1 = new JTextField();
        jScrollPaneChannel = new JScrollPane();
        bJoinChannel = new JButton();
        bSendMessage = new JButton();
        jMenuBar1 = new JMenuBar();
        config = new JMenu();
        jMenuItem2 = new JMenuItem();
        jMenuItem1 = new JMenuItem();
        connnect = new JMenu();
        disconnect = new JMenu();
        quit = new JMenu();
        StyleConstants.setForeground(error, Color.RED);
        StyleConstants.setForeground(privateMessageFont, Color.blue);
        StyleConstants.setBackground(privateMessageFont, Color.white);
        StyleConstants.setBold(privateMessageFont, true);
        StyleConstants.setBold(error, true);
        StyleConstants.setForeground(messageFont, Color.white);
        StyleConstants.setBackground(messageFont, Color.black);
        StyleConstants.setBold(messageFont, true);
        StyleConstants.setForeground(serverMessageFont, Color.red);
        StyleConstants.setBackground(serverMessageFont, Color.black);
        StyleConstants.setBold(serverMessageFont, true);
        replyFont= new SimpleAttributeSet();
        StyleConstants.setItalic(replyFont, true);
        StyleConstants.setForeground(replyFont, Color.black);
        StyleConstants.setBackground(replyFont, Color.WHITE);
        setDefaultCloseOperation(3);
        jTextArea1.setEditable(false);
        
        
//////////////////////////////////////////PANEL CHANNEL DANS INICOMPONENTS////////////////////////////////////////////////////////////////////////////////////////////
        jScrollPane1.setViewportView(jTextArea1);
        jScrollPaneChannel.setBackground(new Color(255, 255, 255));
        
        bJoinChannel.setText("Join");
        bJoinChannel.setEnabled(false);
        bJoinChannel.addMouseListener(new MouseAdapter(){
       	 	@Override
       	 	public void mouseClicked(MouseEvent evt) {
       		joinListener(evt);
       	 	}
 	    });
        
        channels = new JList<String>();
        jScrollPaneChannel.setViewportView(channels);
        channels.setModel(new DefaultListModel<String>());
        channels.addListSelectionListener(new ListSelectionListener(){ 
        		@Override
        	    public void valueChanged(ListSelectionEvent evt) {
        			setBjoiEnables(evt);
        		}
        });
        
        
/////////////////////////////////////////PANEL SEND DANS INICOMPONENTS//////////////////////////////////////////////////////////////////////////////////////////        
        bSendMessage.setText("Send");
        bSendMessage.addMouseListener(new MouseAdapter(){
       	 	@Override
       	 	public void mouseClicked(MouseEvent evt) {
       		sendListener(evt);
       	 	}
 	    });
        
        txtChannels = new JTextField();
        txtChannels.setHorizontalAlignment(SwingConstants.CENTER);
        txtChannels.setText("Channels");
        txtChannels.setColumns(10);
        
        
///////////////////////////////////////PARTIE GRAPHIQUE DANS INICOMPONENTS/////////////////////////////////////////////////////////////////////////////////
        final GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(bSendMessage, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        				.addComponent(jScrollPane1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE))
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(txtChannels, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
        					.addGap(51))
        				.addGroup(Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jScrollPaneChannel, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addGap(18)
        							.addComponent(bJoinChannel, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)))
        					.addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, jPanel2Layout.createSequentialGroup()
        					.addComponent(jScrollPaneChannel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(txtChannels, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)))
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(bJoinChannel)
        				.addComponent(bSendMessage))
        			.addContainerGap())
        );
        jPanel2.setLayout(jPanel2Layout);
        jTabbedPane1.addTab("IRC Chat", jPanel2);
        final GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTabbedPane1));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jTabbedPane1, GroupLayout.Alignment.TRAILING));
        
////////////////////////////////CONFIGURATION DES MENUS DANS INICOMPONENTS////////////////////////////////////////////////////////////////////////////////////////////////
        config.setText("Config");
        jMenuItem2.setText("NickName");
        jMenuItem2.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        config.add(jMenuItem2);
        jMenuItem1.setText("IP address");
        jMenuItem1.setActionCommand("jMenu3");
        jMenuItem1.addActionListener(new ActionListener() {@Override
            public void actionPerformed(ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
        });
        config.add(jMenuItem1);
        jMenuBar1.add(config);
        connnect.setText("Connect");
        connnect.addMouseListener(new MouseAdapter(){
        	@Override
            public void mouseClicked(MouseEvent evt) {
                connectListener(evt);
        	}
        });
        jMenuBar1.add(connnect);
        disconnect.setText("Disconnect");
        disconnect.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent evt) {
            jMenu3MouseClicked(evt);
        }
        });
        jMenuBar1.add(disconnect);
        quit.setText("Quit");
        quit.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(quit);
        setJMenuBar(jMenuBar1);
        final GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 802, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);
        pack();
    }
///////////////////////////////////////////FIN DE LA METHODE INITCOMPONENTS///////////////////////////////////////////////////////////////////    
    
    

///////////////////////////////////////////METHODE POUR LE MENU CONFIG/////////////////////////////////////////////////////////////////////////   
    private void jMenuItem2ActionPerformed(final ActionEvent evt) {
        nickname = JOptionPane.showInputDialog("Enter your new nickname", nickname);
        if (connected) {
            handler.sendNick(nickname);
        }
    }
    
    public void nameBadSent() {
        JOptionPane.showMessageDialog(this, "Server : Nickname already exist or is not authorized.");
        disconnect();
    }   
    
    
    private void jMenuItem1ActionPerformed(final ActionEvent evt) {
        ipAddress = JOptionPane.showInputDialog("Enter new ip address", ipAddress);
    }
    
    
///////////////////////////////////////////METHODE POUR LES MENU CONNECT ET DISCONNECT ET QUIT////////////////////////////////////////////////////////////    
    private void connectListener(final MouseEvent evt) {
        connect();
    }
    private void jMenu3MouseClicked(final MouseEvent evt) {
        disconnect();
    }
    private void jMenu4MouseClicked(final MouseEvent evt) {
        disconnect();
        dispatchEvent(new WindowEvent(this, 201));
    }
    
    private void connect() {
        if (!connected) {
            try {
                socket = new Socket(ipAddress, 6667);
                (handler = new ClientHandleConnection(socket, this)).start();
                connected = true;
                handler.sendNick(nickname);
                handler.askNotifyChannels();
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Connection failed !");
            }
        }
    }
    
    private void disconnect() {
        if (connected) {
            Component[] components;
            for (int length = (components = jTabbedPane1.getComponents()).length, i = 0; i < length; ++i) {
                final Component c = components[i];
                if (c.getName() != null && c.getName().compareTo("World Chat") != 0) {
                    leaveChannel((JPanel)c);
                }
            }
            handler.sendQuit();
            handler.finish();
            connected = false;
            jTextArea1.setText("");
            ((DefaultListModel<String>)channels.getModel()).clear();
            ((DefaultListModel<String>)userList.getModel()).clear();
        }
        handler.sendQuit();
    }
    
  public void leaveChannel(final JPanel j) {
        handler.LeaveChannel(j.getName());
        jTabbedPane1.remove(j);
    }

    private void joinListener(final MouseEvent evt) {
        final String selec = channels.getSelectedValue();
        handler.sendJoinRoom(selec);
    }
    
    public void sendListener(final MouseEvent evt) {
    	if (connected) {
    		final String msg = jTextField1.getText();
    		jTextField1.setText("");
    		if (!msg.equals(""))handler.sendMessage(msg);
    	}
    }

    private void setBjoiEnables(final ListSelectionEvent evt) {
    	 bJoinChannel.setEnabled(true);
    }
    
    
    public void notifyChannels(final ArrayList<String> roomsList) {
        ((DefaultListModel<String>)channels.getModel()).clear();
        for (final String s : roomsList) {
            ((DefaultListModel<String>)channels.getModel()).addElement(s);
        }
        Component[] components;
        for (int length = (components = jTabbedPane1.getComponents()).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            if (c.getName() != null && !roomsList.contains(c.getName()) && c.getName().compareTo("IRC") != 0) {
                try {
                    jTabbedPane1.remove(c);
                }
                catch (ArrayIndexOutOfBoundsException ex) {}
            }
        }
        if (channels.getModel().getSize() < 1) {
           bJoinChannel.setEnabled(false);
        }
    }
      
    private JPanel makeNewJPanel(final String room) {
        return (JPanel)new MyJPanel(this, room, nickname, messageFont);
    }   
    private JPanel makeNewPrivateJPanel(final String user) {
        return (JPanel)new PrivatePanel(this,user);
    }   

	public void printServerResponse(String respons,int i) {
		try {
			 int panel= jTabbedPane1.getSelectedIndex();
			 Component c =jTabbedPane1.getComponent(panel);
			if (c.getName() != null && privatePanel.contains(c.getName())) {
				 final PrivatePanel p = (PrivatePanel)c;
	            	p.printServerResponse(respons, i);
			 }
			else if (c.getName() != null &&!c.getName().equals("IRC Chat")) {
				 final MyJPanel p = (MyJPanel)c;
	            	p.printServerResponse(respons, i);
			 }
			 else {
			 final int len = jTextArea1.getText().length();
			 if (i==1)
			jTextArea1.getStyledDocument().insertString(len,respons+"\n",error);
			 else jTextArea1.getStyledDocument().insertString(len,respons+"\n",replyFont);
			 }
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
    public void errorSent(final String strMsg) {
        JOptionPane.showMessageDialog(this, "Server : " + strMsg);
    }
  
	public void notifyUsers(String chName, ArrayList<String> userList2) {
		 Component[] components =jTabbedPane1.getComponents();
		 for (Component component : components) {
			if (component.getName()!= null && component.getName().compareTo(chName)==0) {
				final MyJPanel p = (MyJPanel)component;
            	p.notifyUsers(userList2);
            	return;
			}
		}
	}

	public void joinFromServer(String ch, String topic) {
		Component[] components;
        for (int length = (components = jTabbedPane1.getComponents()).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            if (c.getName() != null && c.getName().compareTo(ch) == 0) {
            	if (!topic.equals("")) {
    				final MyJPanel p = (MyJPanel)c;
                	p.setTopic(topic);
    			}
                return;
            }}
		 jTabbedPane1.addTab(ch,makeNewJPanel(ch));
		 jTabbedPane1.setSelectedIndex(getIndex(ch));
			int index = getIndex(ch);
			if (index!=-1 && !topic.equals("")) {
				Component c =jTabbedPane1.getComponent(index);
				final MyJPanel p = (MyJPanel)c;
            	p.setTopic(topic);
			}
		// handler.askRoomUsersList(ch);
		 
	}
	public int getIndex(String tofind) {
		int count = jTabbedPane1.getTabCount();
		for (int i = 0; i < count; i++) {
			if (jTabbedPane1.getTitleAt(i).equals(tofind)) return i;
		}
		return -1;
	}

	public void sendMessageFromPanelToServer(String name, String msg) {
		handler.sendMessageFromChannelToServer(name,msg);
	}

	public void messageChannelRecievied(String ch, String from, String msg) {
		int index= getIndex(ch);
		if (index!= -1) {
			Component c =jTabbedPane1.getComponent(index);
			final MyJPanel p = (MyJPanel)c;
        	p.messageRecieved(from,msg);

		}
		
	}

	public void canSendToChannel(String ch) {
		int index= getIndex(ch);
		if (index!= -1) {
			Component c =jTabbedPane1.getComponent(index);
			final MyJPanel p = (MyJPanel)c;
        	p.printMyMsg();

		}
	}

	public void canSendPrivate(String to) {
		int index = getIndex(to);
		if (index == -1) {
			jTabbedPane1.addTab(to, makeNewPrivateJPanel(to));
			privatePanel.add(to); 
			jTabbedPane1.setSelectedIndex(getIndex(to));

			Component c =jTabbedPane1.getComponent(getIndex(to));
			final PrivatePanel p = (PrivatePanel)c;
        	p.printMyMsg();
		}else {
			Component c =jTabbedPane1.getComponent(index);
			final PrivatePanel p = (PrivatePanel)c;
        	p.printMyMsg();
		}
		
	}

	public void sendPrivateRequest(String user) {
		handler.sendPrivateMessageToServer(user,"Welcom");
	}

	public void sendMessagePrivateToServer(String to, String message) {
		handler.sendPrivateMessageToServer(to, message);
		
	}
	public void messagePrivateRecievied(String to, String from, String msg) {
		int index= getIndex(from);
		if (index!= -1) {
			Component c =jTabbedPane1.getComponent(index);
			final PrivatePanel p = (PrivatePanel)c;
        	p.messageRecieved(from,msg);

		}else {
			jTabbedPane1.addTab(from, makeNewPrivateJPanel(from));
			privatePanel.add(from);
			jTabbedPane1.setSelectedIndex(getIndex(from));

			Component c =jTabbedPane1.getComponent(getIndex(from));
			final PrivatePanel p = (PrivatePanel)c;
        	p.messageRecieved(from,msg);
		}
		
	}
	
	public void leavePribatePanel(JPanel p) {
		 jTabbedPane1.remove(p);
	}

}
