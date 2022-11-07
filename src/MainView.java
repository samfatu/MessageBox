import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JLabel title;
    private JButton accessButton, messageButton, signUpButton;

    public MainView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;
        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Message Box");
        mainFrame.setResizable(false);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        mainFrame.setContentPane(contentPanel);

        // View components
        title = new JLabel("Welcome To Message Box!", SwingConstants.CENTER);
        title.setFont(new Font(null, Font.BOLD, 20));
        title.setBounds(60, 100, 300, 30);
        accessButton = new JButton("Access a Message");
        accessButton.setBounds(120, 140, 180, 40);
        messageButton = new JButton("Leave a Message");
        messageButton.setBounds(120, 190, 180, 40);
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(120, 240, 180, 40);

        contentPanel.add(title);
        contentPanel.add(accessButton);
        contentPanel.add(messageButton);
        contentPanel.add(signUpButton);

        mainFrame.pack();
        mainFrame.setSize(420, 420);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        accessButton.addActionListener(e -> this.openAccessMessageView());
        messageButton.addActionListener(e -> this.openLeaveMessageView());
        signUpButton.addActionListener(e -> this.openSignUpUserView());
    }

    private void openAccessMessageView() {
        new AccessMessageView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void openLeaveMessageView() {
        new LeaveMessageView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void openSignUpUserView() {
        new SignUpUserView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
    }
}
