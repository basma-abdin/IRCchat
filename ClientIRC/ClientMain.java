import java.awt.EventQueue;

public class ClientMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

}