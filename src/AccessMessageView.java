import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class AccessMessageView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JLabel messageCodeNameLabel, messagePasswordLabel, userNameLabel, userPasswordLabel, errorLabel;
    private JTextField messageCodeNameField, userNameField;
    private JPasswordField messagePasswordField, userPasswordField;
    private JButton viewButton, mainPageButton, resetButton, messagePasswordVisibleButton, userPasswordVisibleButton;
    private ImageIcon showPassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));
    private ImageIcon hidePassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye_off.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));

    public AccessMessageView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;

        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Access Message");
        mainFrame.setResizable(false);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        mainFrame.setContentPane(contentPanel);

        // Message information form
        messageCodeNameLabel = new JLabel(" Message Code");
        messageCodeNameLabel.setBounds(80, 30, 200, 30);
        messageCodeNameField = new JTextField();
        messageCodeNameField.setBounds(80, 60, 200, 30);

        messagePasswordLabel = new JLabel(" Message Password");
        messagePasswordLabel.setBounds(300, 30, 200, 30);
        messagePasswordField = new JPasswordField();
        messagePasswordField.setBounds(300, 60, 200, 30);

        messagePasswordVisibleButton = new JButton(showPassword);
        messagePasswordVisibleButton.setBounds(510, 63, 24, 24);
        messagePasswordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        messagePasswordVisibleButton.setContentAreaFilled(false);

        contentPanel.add(messageCodeNameLabel);
        contentPanel.add(messageCodeNameField);
        contentPanel.add(messagePasswordLabel);
        contentPanel.add(messagePasswordField);
        contentPanel.add(messagePasswordVisibleButton);

        // User information form
        userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(80, 100, 200, 30);
        userNameField = new JTextField();
        userNameField.setBounds(80, 130, 200, 30);

        userPasswordLabel = new JLabel("User Password");
        userPasswordLabel.setBounds(300, 100, 200, 30);
        userPasswordField = new JPasswordField();
        userPasswordField.setBounds(300, 130, 200, 30);

        userPasswordVisibleButton = new JButton(showPassword);
        userPasswordVisibleButton.setBounds(510, 133, 24, 24);
        userPasswordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        userPasswordVisibleButton.setContentAreaFilled(false);

        contentPanel.add(userNameLabel);
        contentPanel.add(userNameField);
        contentPanel.add(userPasswordLabel);
        contentPanel.add(userPasswordField);
        contentPanel.add(userPasswordVisibleButton);

        // Navigation buttons
        viewButton = new JButton("View Message");
        viewButton.setBounds(80, 180, 140, 40);

        mainPageButton = new JButton("Main Page");
        mainPageButton.setBounds(235, 180, 140, 40);

        resetButton = new JButton("Reset Fields");
        resetButton.setBounds(390, 180, 140, 40);

        contentPanel.add(viewButton);
        contentPanel.add(mainPageButton);
        contentPanel.add(resetButton);

        // Error label
        errorLabel = new JLabel();
        errorLabel.setBounds(80, 210, 440, 60);
        errorLabel.setForeground(Color.RED);

        contentPanel.add(errorLabel);

        mainFrame.pack();
        mainFrame.setSize(600, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        messagePasswordVisibleButton.addActionListener(e -> this.messagePasswordButtonPressed());
        userPasswordVisibleButton.addActionListener(e -> this.userPasswordButtonPressed());
        mainPageButton.addActionListener(e -> this.openMainView());
        resetButton.addActionListener(e -> this.resetFields());
        viewButton.addActionListener(e -> {
            try {
                this.openMessage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void messagePasswordButtonPressed() {
        if (messagePasswordVisibleButton.getIcon().equals(this.showPassword)) {
            messagePasswordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> messagePasswordVisibleButton.setIcon(hidePassword));
        } else {
            messagePasswordField.setEchoChar('●');
            SwingUtilities.invokeLater(() -> messagePasswordVisibleButton.setIcon(showPassword));
        }
    }

    private void userPasswordButtonPressed() {
        if (userPasswordVisibleButton.getIcon().equals(this.showPassword)) {
            userPasswordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> userPasswordVisibleButton.setIcon(hidePassword));
        } else {
            userPasswordField.setEchoChar('●');
            SwingUtilities.invokeLater(() -> userPasswordVisibleButton.setIcon(showPassword));
        }
    }

    private void openMainView() {
        new MainView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void openMessage() throws Exception {
        String messageCode = this.messageCodeNameField.getText();
        String userName = this.userNameField.getText();

        if (this.parser.isMessageExistsWithCodeAndUserName(messageCode, userName) && this.parser.isUserExistsWithUserName(userName)) {
            if (this.isValidPasswords(messageCode, userName)) {
                new MessageView(this.parser,
                        this.crypto,
                        new String(crypto.decrypt(Crypto.decode(this.parser.getMessageWithCode(messageCode).getContent()))));
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            } else {
                this.errorLabel.setText("Passwords are not correct.");
            }
        } else {
            this.errorLabel.setText("Username or message code is not correct.");
        }
    }

    private void resetFields() {
        messageCodeNameField.setText("");
        messagePasswordField.setText("");
        userNameField.setText("");
        userPasswordField.setText("");
    }

    private boolean isValidPasswords(String messageCode, String userName) throws Exception {
        Message message = parser.getMessageWithCode(messageCode);
        User user = parser.getUserWithUserName(userName);

        byte[] givenMessagePassword = crypto.hash(new String(messagePasswordField.getPassword()).getBytes(StandardCharsets.UTF_8));
        byte[] givenUserPassword = crypto.hash(new String(userPasswordField.getPassword()).getBytes(StandardCharsets.UTF_8));

        return Arrays.equals(givenMessagePassword, Crypto.decode(message.getPassword())) &&
                Arrays.equals(givenUserPassword, Crypto.decode(user.getPassword()));
    }
}
