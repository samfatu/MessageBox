import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MessageView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton mainPageButton;

    public MessageView(FileParser parser, Crypto crypto, String content) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;

        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("View Message");
        mainFrame.setResizable(false);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        mainFrame.setContentPane(contentPanel);

        // Title
        titleLabel = new JLabel("Message left for you");
        titleLabel.setBounds(20, 20, 600, 30);

        contentPanel.add(titleLabel);

        // Content area
        textArea = new JTextArea(content);
        textArea.setEditable(false);
        scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 60, 560, 200);

        contentPanel.add(scrollPane);

        // Back button
        mainPageButton = new JButton("Back to Main Page");
        mainPageButton.setBounds(20, 270, 140, 40);

        contentPanel.add(mainPageButton);

        mainFrame.pack();
        mainFrame.setSize(600, 360);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainPageButton.addActionListener(e -> this.backToMainPagePressed());
    }

    private void backToMainPagePressed() {
        new MainView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }
}
