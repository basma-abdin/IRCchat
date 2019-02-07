package server;



import java.io.IOException;
import java.awt.Color;

public class ServerFrame {
    private MyJFrame frame;
    private ServerCore currentServ;

    public ServerFrame() {
        this.frame = new MyJFrame("IRC Server", this);
        frame.getContentPane().setForeground(new Color(51, 51, 51));
        this.currentServ = null;
        this.frame.setVisible(true);
    }

    void startServer() {
        int port = 6667;
        try {
            this.currentServ = new ServerCore(port, this);
        }
        catch (IOException e) {
            this.writeToLog("Error during initialisation:" + e.toString());
        }
    }

    void stopServer() {
        this.currentServ.finish();
    }

    public synchronized void writeToLog(String msg) {
        this.frame.logArea.append(String.valueOf(msg) + "\n");
    }

}