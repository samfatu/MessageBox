package view;

import javax.swing.*;
import java.awt.*;

public class LeaveMessageView extends JFrame {
    public LeaveMessageView() throws HeadlessException {
        JFrame mainFrame = new JFrame();
        JLabel title = new JLabel();
        JButton accessButton = new JButton("Access");
        JButton messageButton = new JButton("Leave a Message");

        title.setText("LEAVE MESSAGE VIEW");
        title.setBounds(175, 50, 300, 25);
        accessButton.setBounds(200, 100, 100, 50);
        messageButton.setBounds(150, 200, 200, 50);

        mainFrame.add(title);
        mainFrame.add(accessButton);
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
}
