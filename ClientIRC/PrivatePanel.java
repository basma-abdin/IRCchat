
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrivatePanel extends JPanel{
	private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextPane jTextArea1 = new JTextPane();
    private JTextField jTextField1 = new JTextField();
    private JButton bSend = new JButton();
    private JButton bLeaveChannel = new JButton();
    private SimpleAttributeSet messageFont = null;
    private SimpleAttributeSet MyMessage=null;
    private SimpleAttributeSet errorFont=null;
    private SimpleAttributeSet replyFont=null;
    private String message =""; 
	private static final long serialVersionUID = 1L;
	private Frame frame;
	public PrivatePanel(Frame f , String tabName) {
		frame=f;
		init(tabName);
	}
	private void init(String tabName) {
		 this.setName(tabName);
	        this.jTextArea1.setEditable(false);
	        this.jScrollPane1.setViewportView(this.jTextArea1);   
	        
	//////////////////////////button SEND DANS INIT//////////////////////////////////////////////////////////////////////////////////////////////////        
	        this.bSend.setText("Send");
	        bSend.addMouseListener(new MouseAdapter() {
	        	 @Override
	             public void mouseClicked(MouseEvent evt) {
	                 panelSendPrivateMessage(evt);
	                 
	             }
	        });
	////////////////////////////PANEL LEAVE CHANNEL DANS INIT///////////////////////////////////////////////////////////////////////////////////////////        
	        this.bLeaveChannel.setText("LEAVE");
	        this.bLeaveChannel.setEnabled(true);
	        bLeaveChannel.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		sendLeavePanel();
	        	}
			});
	      
	  ///////// Custom Font //////////////////////////
	         MyMessage = new SimpleAttributeSet();
	         StyleConstants.setBold(MyMessage, true);
	         StyleConstants.setForeground(MyMessage, Color.blue);
	         StyleConstants.setBackground(MyMessage, Color.WHITE);
	     
	         messageFont= new SimpleAttributeSet();
	         StyleConstants.setForeground(messageFont, Color.ORANGE);
	         StyleConstants.setBackground(messageFont, Color.WHITE);
	         StyleConstants.setBold(messageFont, true);
	         
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
	        			.addContainerGap(66, Short.MAX_VALUE)
	        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.TRAILING, false)
	        				.addGroup(jPanel2Layout.createSequentialGroup()
	        					.addComponent(jTextField1)
	        					.addPreferredGap(ComponentPlacement.RELATED)
	        					.addComponent(bSend, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
	        				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 461, GroupLayout.PREFERRED_SIZE))
	        			.addGap(18)
	        			.addComponent(bLeaveChannel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
	        			.addGap(29))
	        );
	        jPanel2Layout.setVerticalGroup(
	        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
	        		.addGroup(jPanel2Layout.createSequentialGroup()
	        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
	        				.addGroup(jPanel2Layout.createSequentialGroup()
	        					.addGap(2)
	        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
	        					.addPreferredGap(ComponentPlacement.UNRELATED)
	        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.BASELINE)
	        						.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
	        						.addComponent(bSend, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
	        				.addGroup(jPanel2Layout.createSequentialGroup()
	        					.addGap(58)
	        					.addComponent(bLeaveChannel)))
	        			.addContainerGap(38, Short.MAX_VALUE))
	        );
	        this.setLayout(jPanel2Layout);

	}
	
	
	 private void panelSendPrivateMessage(MouseEvent evt) {
	    	message = jTextField1.getText();
	     	jTextField1.setText("");
	     	if (!message.equals("")) {
	     		frame.sendMessagePrivateToServer(this.getName(),message);
	     		
	     	}
	    }
	    
	    public void printMyMsg() {
	    	try {
	    		final int len = jTextArea1.getText().length();
	    		jTextArea1.getStyledDocument().insertString(len, message + "\n", MyMessage);
	    		}
	    	catch (BadLocationException e) {
	    		JOptionPane.showMessageDialog(this, "Chat Error");
	    		}
	    	message="";
		}

	
	    public void messageRecieved(String from, String msg) {
			try {
	    		final int len = jTextArea1.getText().length();
	    		jTextArea1.getStyledDocument().insertString(len,msg + "\n", messageFont);
	    		
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
	
		private void sendLeavePanel() {
			//frame.sendMessagePrivateToServer(this.getName(),"I'm leaving,good bye");
			frame.leavePribatePanel(this);
		}
}
