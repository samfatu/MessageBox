package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class MainView extends JFrame {

    public MainView() throws HeadlessException {
        JFrame mainFrame = new JFrame();
        JLabel title = new JLabel();
        JButton accessButton = new JButton("Access");
        JButton messageButton = new JButton("Leave a Message");

        title.setText("Welcome To Message Box!");
        title.setBounds(175, 50, 300, 25);
        accessButton.setBounds(200, 100, 100, 50);
        messageButton.setBounds(150, 200, 200, 50);

        mainFrame.add(title);
        mainFrame.add(accessButton);
        mainFrame.add(messageButton);
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

        accessButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccessMessageView();
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        messageButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LeaveMessageView();
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

}
