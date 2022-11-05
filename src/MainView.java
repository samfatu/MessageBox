import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private BoxLayout boxLayout;
    private JLabel title;
    private JButton accessButton;
    private JButton messageButton;

    public MainView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;
        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Message Box");
        mainFrame.setResizable(false);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();

        boxLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);

        contentPanel.setLayout(boxLayout);
        // 10px padding to each edge
        Border padding = BorderFactory.createEmptyBorder(100, 10, 10, 10);
        contentPanel.setBorder(padding);

        mainFrame.setContentPane(contentPanel);

        // View components
        title = new JLabel("Welcome To Message Box!", SwingConstants.CENTER);
        accessButton = new JButton("Access");
        messageButton = new JButton("Leave a Message");

        title.setMaximumSize(new Dimension(300, 50));
        accessButton.setMaximumSize(new Dimension(100, 50));
        messageButton.setMaximumSize(new Dimension(150, 50));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        accessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(title);
        contentPanel.add(accessButton);
        contentPanel.add(messageButton);

        mainFrame.pack();
        mainFrame.setSize(420, 420);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        accessButton.addActionListener(e -> this.openAccessMessageView());
        messageButton.addActionListener(e -> this.openLeaveMessageView());
    }

    private void openAccessMessageView() {
        new AccessMessageView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void openLeaveMessageView() {
        new LeaveMessageView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(this.mainFrame, WindowEvent.WINDOW_CLOSING));
    }

}
