package server;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

class MyJFrame
extends JFrame {
    private static final long serialVersionUID = 2676336557217427054L;
    private ServerFrame frame = null;
    private JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea logArea = new JTextArea();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();

    MyJFrame(String name, ServerFrame f) {
        this.frame = f;
        this.setTitle(name);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        logArea.setForeground(Color.LIGHT_GRAY);
        this.logArea.setEditable(false);
        this.logArea.setColumns(20);
        this.logArea.setRows(5);
        this.jScrollPane1.setViewportView(this.logArea);
        this.jButton1.setText("Start");
        this.jButton1.addMouseListener(new MouseAdapter() {
        		@Override
        	    public void mouseClicked(MouseEvent evt) {
        	        MyJFrame.this.jButton1MouseClicked(evt);
        	    }
        });

        
        this.jButton2.setText("Stop");
        this.jButton2.addMouseListener(new MouseAdapter() {
    		@Override
    	    public void mouseClicked(MouseEvent evt) {
    			jButton2MouseClicked(evt);
    	    }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 300, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton1, -1, -1, 32767).addGroup(layout.createSequentialGroup().addGap(0, 0, 32767)).addComponent(this.jButton2, -1, -1, 32767)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 299, -2).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addComponent(this.jButton1).addGap(18, 18, 18).addComponent(this.jButton2).addGap(26, 26, 26))).addContainerGap()));
        this.pack();
    }

    private void jButton1MouseClicked(MouseEvent evt) {
        this.frame.startServer();
    }

    private void jButton2MouseClicked(MouseEvent evt) {
        frame.stopServer();
        this.logArea.setText("");
    }

    static void access$0(MyJFrame myJFrame, MouseEvent mouseEvent) {
        myJFrame.jButton1MouseClicked(mouseEvent);
    }

    static void access$1(MyJFrame myJFrame, MouseEvent mouseEvent) {
        myJFrame.jButton2MouseClicked(mouseEvent);
    }
}