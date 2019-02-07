

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MyJPanel extends JPanel {
    private static final long serialVersionUID = 6773845384714827626L;
    private Frame frame = null;
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextPane jTextArea1 = new JTextPane();
    private JTextField jTextField1 = new JTextField();
    private JScrollPane jScrollPaneListUsers = new JScrollPane();
    private JButton bSend = new JButton();
    private JButton bPrivateMess = new JButton();
    private JButton bLeaveChannel = new JButton();
    private JList<String> userList;
    private int nbBackSlashN = 0;
    private String nickname = null;
    private SimpleAttributeSet messageFont = null;
    private SimpleAttributeSet errorFont = null;
    private SimpleAttributeSet myMessageFont=null;
    private SimpleAttributeSet replyFont=null;
    private JTextField textField;
    private  String message= "";
    private final JTextField txtTopic = new JTextField();
    
    public MyJPanel(Frame frame, String room, String nickname, SimpleAttributeSet messageFont) {
    	txtTopic.setEditable(false);
    	txtTopic.setText("TOPIC:");
    	txtTopic.setColumns(10);
        this.init(room);
       // this.messageFont = messageFont;
        this.nickname = nickname;
        this.frame = frame;
    }
   
    
///////////////////////////DEBUT DE LA METHODE INIT/////////////////////////////////////////////////////////////////////////////////////////////    
    private void init(String room) {
        this.setName(room);
        this.jTextArea1.setEditable(false);
        this.jScrollPane1.setViewportView(this.jTextArea1);   
        
//////////////////////////button SEND DANS INIT//////////////////////////////////////////////////////////////////////////////////////////////////        
        this.bSend.setText("Send");
        this.bSend.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                panelSendMessage(evt);
            }
        });
      
//////////////////////////BUTTON PRIVATE DANS INIT//////////////////////////////////////////////////////////////////////////////////////////////////        
        this.bPrivateMess.setText("Private");
        bPrivateMess.setEnabled(false);
        this.bPrivateMess.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
            	bPrivateListener(evt);
            }
        });
        jScrollPaneListUsers.setBackground(new Color(255, 255, 255));
        
////////////////////////////PANEL LEAVE CHANNEL DANS INIT///////////////////////////////////////////////////////////////////////////////////////////        
        this.bLeaveChannel.setText("Part Channel");
        this.bLeaveChannel.setEnabled(true);
        this.bLeaveChannel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
                panelLeaveChannel();
            }
        });
        
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setText("Users");
        textField.setEditable(false);
        textField.setColumns(10);
        
 /////////////////////////////////////////
        userList = new JList<String>();
        jScrollPaneListUsers.setViewportView(userList);
         userList.setModel(new DefaultListModel<String>());
         userList.addListSelectionListener(new ListSelectionListener(){ 
         		@Override
         	    public void valueChanged(ListSelectionEvent evt) {
         			enablePrivateMsg(evt);
         		}
         });
  ///////// Custom Font //////////////////////////
         myMessageFont = new SimpleAttributeSet();
         StyleConstants.setItalic(myMessageFont, true);
         StyleConstants.setForeground(myMessageFont, Color.GREEN);
         StyleConstants.setBackground(myMessageFont, Color.WHITE);
        
         messageFont= new SimpleAttributeSet();
         StyleConstants.setItalic(messageFont, true);
         StyleConstants.setForeground(messageFont, Color.magenta);
         StyleConstants.setBackground(messageFont, Color.WHITE);
         
         errorFont= new SimpleAttributeSet();
         StyleConstants.setItalic(errorFont, true);
         StyleConstants.setForeground(errorFont, Color.red);
         StyleConstants.setBackground(errorFont, Color.WHITE);
         
         replyFont= new SimpleAttributeSet();
         StyleConstants.setItalic(replyFont, true);
         StyleConstants.setForeground(replyFont, Color.black);
         StyleConstants.setBackground(replyFont, Color.WHITE);
         
         
 /////////////////////////PARTIE GRAMPHIQUE DES LAYOUT DE INIT//////////////////////////////////////////////////////////////////////////////////
        GroupLayout jPanel2Layout = new GroupLayout(this);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGap(6)
        					.addComponent(jTextField1)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(bSend, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 461, GroupLayout.PREFERRED_SIZE))
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jScrollPaneListUsers, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        						.addComponent(bPrivateMess, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        						.addComponent(bLeaveChannel, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
        					.addGap(112))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGap(61)
        					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addGap(2)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jScrollPaneListUsers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(bPrivateMess)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(bLeaveChannel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
        				.addComponent(bSend, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(52, Short.MAX_VALUE))
        );
        
        jScrollPane1.setColumnHeaderView(txtTopic);
        this.setLayout(jPanel2Layout);
        
//////////////////////////////AJOUT DES USERS_LIST DANS JSCROLLPANELISTUSERS DE INICOMPONENTS/////////////////////////////////////////        
       
    }
    
//////////////////////////////FIN DE LA METHODE INIT///////////////////////////////////////////////////////////////////////////////////


    
     private void enablePrivateMsg(final ListSelectionEvent evt) {
        bPrivateMess.setEnabled(true);
    	}
      
    
////////////////////////////METHODE POUR LE JBUTTON �RT CHANNEL/////////////////////////////////////////////////////////////////////////   
    
     private void panelLeaveChannel() {
    	this.frame.leaveChannel(this);
    }    
         

////////////////////////////METHODE POUR LE JBUTTON SEND_MESSAGE/////////////////////////////////////////////////////////////////////////   
    
    public void notifyRoomMessageSent(String strName, String strMsg) {
    	try {
    		int len = this.jTextArea1.getText().length() - this.nbBackSlashN;
    		if (strName.compareTo(this.nickname) == 0) {
    			this.jTextArea1.getStyledDocument().insertString(len, String.valueOf(strName) + " : " + strMsg + '\n', this.messageFont);
    			} else {
    				this.jTextArea1.getStyledDocument().insertString(len, String.valueOf(strName) + " : " + strMsg + '\n', null);
    				}
    		++this.nbBackSlashN;
    		}
    	catch (BadLocationException e) {
    		JOptionPane.showMessageDialog(this, "Chat Internal Error, contact the dev");
    		}
    	}
    
    
    private void panelSendMessage(MouseEvent evt) {
    	message = jTextField1.getText();
     	jTextField1.setText("");
     	if (!message.equals(""))
  		frame.sendMessageFromPanelToServer(this.getName(),message);
     		//frame.sendListener(evt);
    }
    
    public void printMyMsg() {
    	try {
    		final int len = jTextArea1.getText().length();
    		jTextArea1.getStyledDocument().insertString(len, message + "\n", myMessageFont);
    		++nbBackSlashN;
    		}
    	catch (BadLocationException e) {
    		JOptionPane.showMessageDialog(this, "Chat Error");
    		}
    	message="";
	}

 
    private void bPrivateListener(final MouseEvent evt) {
    	final String user = this.userList.getSelectedValue();
    	if (!user.equals(nickname))frame.sendPrivateRequest(user);
    	
    }
    
   public void notifyUsers(final ArrayList<String> users) {
    	((DefaultListModel<String>)this.userList.getModel()).clear();
    	for (final String s : users) {
    		((DefaultListModel<String>)this.userList.getModel()).addElement(s);
    		}
    	}
    public void setTopic(String t) {
    	txtTopic.setEditable(true);
    	String s =txtTopic.getText();
    	txtTopic.setText(s+" "+t);
    	txtTopic.setEditable(false);
    	
    }


	public void messageRecieved(String from, String msg) {
		try {
    		final int len = jTextArea1.getText().length();
    		jTextArea1.getStyledDocument().insertString(len, from+" : "+msg + "\n", messageFont);
    		
    		++nbBackSlashN;
    		}
    	catch (BadLocationException e) {
    		JOptionPane.showMessageDialog(this, "Chat Error");
    		}
	}
	public void printServerResponse(String respons,int i) {
		try {
			
			 final int len = jTextArea1.getText().length();
			 if (i==1)
			jTextArea1.getStyledDocument().insertString(len,respons+"\n",errorFont);
			 else jTextArea1.getStyledDocument().insertString(len,respons+"\n",replyFont);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
}