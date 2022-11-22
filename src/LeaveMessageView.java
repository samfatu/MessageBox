import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaveMessageView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JLabel selectUsernameLabel, messagePasswordLabel, messagePasswordConfirmationLabel, messageCodeNameLabel, enterMessageLabel, errorLabel;
    private JPasswordField messagePasswordField, confirmMessagePasswordField;
    private JComboBox usersBox;
    private JTextField messageCodeNameField;
    private JTextArea messageTextArea;
    private JScrollPane scrollPane;
    private JButton leaveMessageButton, mainPageButton, messagePasswordVisibleButton, confirmMessagePasswordVisibleButton;
    private ImageIcon showPassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));
    private ImageIcon hidePassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye_off.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public LeaveMessageView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;

        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Leave a Message");
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        mainFrame.setContentPane(contentPanel);

        // Username selection area
        selectUsernameLabel = new JLabel("*Authenticated Users");
        selectUsernameLabel.setBounds(80, 30, 200, 30);
        usersBox = new JComboBox(this.parser.getAllUsernames());
        usersBox.setBounds(80, 60, 200, 30);

        contentPanel.add(selectUsernameLabel);
        contentPanel.add(usersBox);

        // Message code area
        messageCodeNameLabel = new JLabel("*Message Code");
        messageCodeNameLabel.setBounds(80, 100, 200, 30);
        messageCodeNameField = new JTextField();
        messageCodeNameField.setBounds(80, 130, 200, 30);

        contentPanel.add(messageCodeNameLabel);
        contentPanel.add(messageCodeNameField);

        // Message passwords area
        messagePasswordLabel = new JLabel("*Message Password");
        messagePasswordLabel.setBounds(300, 30, 200, 30);
        messagePasswordField = new JPasswordField();
        messagePasswordField.setBounds(300, 60, 200, 30);

        messagePasswordVisibleButton = new JButton(showPassword);
        messagePasswordVisibleButton.setBounds(510, 63, 24, 24);
        messagePasswordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        messagePasswordVisibleButton.setContentAreaFilled(false);

        messagePasswordConfirmationLabel = new JLabel( "*Confirm Password");
        messagePasswordConfirmationLabel.setBounds(300, 100, 200, 30);
        confirmMessagePasswordField = new JPasswordField();
        confirmMessagePasswordField.setBounds(300, 130, 200, 30);

        confirmMessagePasswordVisibleButton = new JButton(showPassword);
        confirmMessagePasswordVisibleButton.setBounds(510, 133, 24, 24);
        confirmMessagePasswordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        confirmMessagePasswordVisibleButton.setContentAreaFilled(false);

        contentPanel.add(messagePasswordLabel);
        contentPanel.add(messagePasswordField);
        contentPanel.add(messagePasswordVisibleButton);
        contentPanel.add(messagePasswordConfirmationLabel);
        contentPanel.add(confirmMessagePasswordField);
        contentPanel.add(confirmMessagePasswordVisibleButton);

        // Content area
        enterMessageLabel = new JLabel("*Enter your message");
        enterMessageLabel.setBounds(80, 160, 200, 30);
        messageTextArea = new JTextArea();
        scrollPane = new JScrollPane (messageTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(80, 190, 440, 250);

        contentPanel.add(enterMessageLabel);
        contentPanel.add(scrollPane);

        // Bottom buttons
        leaveMessageButton = new JButton("Leave Message");
        leaveMessageButton.setBounds(150, 460, 140, 40);
        mainPageButton = new JButton("Back to Main Page");
        mainPageButton.setBounds(310, 460, 140, 40);

        contentPanel.add(leaveMessageButton);
        contentPanel.add(mainPageButton);

        // Error Label
        errorLabel = new JLabel();
        errorLabel.setBounds(80, 485, 440, 60);
        errorLabel.setForeground(Color.RED);

        contentPanel.add(errorLabel);

        mainFrame.pack();
        mainFrame.setSize(600, 580);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        leaveMessageButton.addActionListener(e -> {
            try {
                this.leaveMessage();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        mainPageButton.addActionListener(e -> this.openMainPage());
        messagePasswordVisibleButton.addActionListener(e -> this.messagePasswordButtonPressed());
        confirmMessagePasswordVisibleButton.addActionListener(e -> this.messagePasswordConfirmationButtonPressed());
    }

    private void messagePasswordButtonPressed() {
        if (messagePasswordVisibleButton.getIcon().equals(this.showPassword)) {
            messagePasswordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> messagePasswordVisibleButton.setIcon(hidePassword));
        } else {
            messagePasswordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
            SwingUtilities.invokeLater(() -> messagePasswordVisibleButton.setIcon(showPassword));
        }
    }

    private void messagePasswordConfirmationButtonPressed() {
        if (confirmMessagePasswordVisibleButton.getIcon().equals(this.showPassword)) {
            confirmMessagePasswordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> confirmMessagePasswordVisibleButton.setIcon(hidePassword));
        } else {
            confirmMessagePasswordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
            SwingUtilities.invokeLater(() -> confirmMessagePasswordVisibleButton.setIcon(showPassword));
        }
    }

    private void openMainPage() {
        mainFrame.dispose();
        new MainView(this.parser, this.crypto);
        //mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void leaveMessage() throws Exception {
        if (isRequiredEmpty()) {
            this.errorLabel.setText("Required fields (*) cannot be empty!");
            return;
        } else if (!isPasswordsComplex()) {
            this.errorLabel.setText("Password is not strong!");
            return;
        } else if (!isPasswordsMatch()) {
            this.errorLabel.setText("Passwords are not match!");
            return;
        }

        String messageCode = this.messageCodeNameField.getText();

        if (!this.parser.isMessageCodeUnique(messageCode)) {
            this.errorLabel.setText("Given message code must be unique!");
        } else if (!messageCode.matches("[0-9]+")) {
            this.errorLabel.setText("Given message code must be consist of only numbers!");
        } else {
            Message message = new Message(
                    messageCode,
                    Crypto.encode(crypto.encrypt(messageTextArea.getText().getBytes(StandardCharsets.UTF_8))),
                    Crypto.encode(crypto.hash((new String(messagePasswordField.getPassword())).getBytes(StandardCharsets.UTF_8))),
                    this.usersBox.getSelectedItem().toString());
            this.parser.addMessage(message);
            FileIO.writeFile("messages.txt", message.convertToByteArray());
            int action = JOptionPane.showOptionDialog(
                            this.mainFrame,
                            "Leaving message operation is successful. Do you want to return main page?",
                            "Success",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            new String[]{"Yes Return Main Page!", "No, I will leave another message"},
                            "Yes Return Main Page!");

            if (action == 0) {
                mainFrame.dispose();
                new MainView(this.parser, this.crypto);
                //mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            } else {
                this.usersBox.setSelectedIndex(0);
                this.messagePasswordField.setText("");
                this.confirmMessagePasswordField.setText("");
                this.messageCodeNameField.setText("");
                this.messageTextArea.setText("");
                this.errorLabel.setText("");
            }
        }
    }

    private boolean isRequiredEmpty() {
        return messageCodeNameField.getText().equals("") || messageTextArea.getText().equals("");
    }

    private boolean isPasswordsMatch() {
        return Arrays.equals(messagePasswordField.getPassword(), confirmMessagePasswordField.getPassword());
    }

    private boolean isPasswordsComplex() {
        Matcher matcher = pattern.matcher(new String(messagePasswordField.getPassword()));
        return matcher.matches();
    }
}
