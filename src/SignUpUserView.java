import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpUserView extends JFrame {
    private FileParser parser;
    private Crypto crypto;
    private JFrame mainFrame;
    private JPanel contentPanel;
    private JLabel title, userNameLabel, passwordLabel, confirmPasswordLabel, errorLabel;
    private JTextField userNameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, mainPageButton, passwordVisibleButton, confirmPasswordVisibleButton;
    private ImageIcon showPassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));
    private ImageIcon hidePassword = new ImageIcon((new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resources/eye_off.png")))).getImage().getScaledInstance(24,24, Image.SCALE_SMOOTH));
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public SignUpUserView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;
        // Initialize Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Sign Up");
        mainFrame.setResizable(false);

        // Initialize content panel placed in main frame
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        mainFrame.setContentPane(contentPanel);

        // View components
        title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font(null, Font.BOLD, 20));
        title.setBounds(60, 30, 300, 30);

        contentPanel.add(title);

        userNameLabel = new JLabel("*Username");
        userNameLabel.setBounds(60, 70, 300, 30);
        userNameField = new JTextField();
        userNameField.setBounds(60, 100, 300, 30);

        contentPanel.add(userNameLabel);
        contentPanel.add(userNameField);

        // Passwords area
        passwordLabel = new JLabel("*User Password");
        passwordLabel.setBounds(60, 130, 300, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(60, 160, 300, 30);

        passwordVisibleButton = new JButton(showPassword);
        passwordVisibleButton.setBounds(370, 163, 24, 24);
        passwordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        passwordVisibleButton.setContentAreaFilled(false);

        confirmPasswordLabel = new JLabel( "*Confirm Password");
        confirmPasswordLabel.setBounds(60, 190, 300, 30);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(60, 220, 300, 30);

        confirmPasswordVisibleButton = new JButton(showPassword);
        confirmPasswordVisibleButton.setBounds(370, 223, 24, 24);
        confirmPasswordVisibleButton.setBorder(BorderFactory.createEmptyBorder());
        confirmPasswordVisibleButton.setContentAreaFilled(false);

        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(passwordVisibleButton);
        contentPanel.add(confirmPasswordLabel);
        contentPanel.add(confirmPasswordField);
        contentPanel.add(confirmPasswordVisibleButton);

        // Error label
        errorLabel = new JLabel();
        errorLabel.setBounds(60, 260, 300, 30);
        errorLabel.setForeground(Color.RED);

        contentPanel.add(errorLabel);

        // Bottom buttons
        registerButton = new JButton("Sign Up");
        registerButton.setBounds(120, 300, 180, 40);
        mainPageButton = new JButton("Return to Main Page");
        mainPageButton.setBounds(120, 350, 180, 40);

        contentPanel.add(registerButton);
        contentPanel.add(mainPageButton);

        mainFrame.pack();
        mainFrame.setSize(420, 440);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        registerButton.addActionListener(e -> {
            try {
                this.signUp();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        mainPageButton.addActionListener(e -> this.openMainPageView());
        passwordVisibleButton.addActionListener(e -> this.passwordButtonPressed());
        confirmPasswordVisibleButton.addActionListener(e -> this.passwordConfirmationButtonPressed());
    }


    private void passwordButtonPressed() {
        if (passwordVisibleButton.getIcon().equals(this.showPassword)) {
            passwordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> passwordVisibleButton.setIcon(hidePassword));
        } else {
            passwordField.setEchoChar('●');
            SwingUtilities.invokeLater(() -> passwordVisibleButton.setIcon(showPassword));
        }
    }

    private void passwordConfirmationButtonPressed() {
        if (confirmPasswordVisibleButton.getIcon().equals(this.showPassword)) {
            confirmPasswordField.setEchoChar((char) 0);
            SwingUtilities.invokeLater(() -> confirmPasswordVisibleButton.setIcon(hidePassword));
        } else {
            confirmPasswordField.setEchoChar('●');
            SwingUtilities.invokeLater(() -> confirmPasswordVisibleButton.setIcon(showPassword));
        }
    }

    private void openMainPageView() {
        new MainView(this.parser, this.crypto);
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }

    private void signUp() throws Exception {
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

        String userName = this.userNameField.getText();

        if (!this.parser.isUserNameUnique(userName)) {
            this.errorLabel.setText("Given username must be unique!");
        } else if (userName.contains(" ")) {
            this.errorLabel.setText("Username can not contain space!");
        } else {
            User user = new User(
                    userName,
                    Crypto.encode(crypto.hash((new String(passwordField.getPassword())).getBytes(StandardCharsets.UTF_8))));
            this.parser.addUser(user);
            FileIO.writeFile("users.txt", user.convertToByteArray());
            int action = JOptionPane.showOptionDialog(
                    this.mainFrame,
                    "User creation is successful. Do you want to return main page?",
                    "Success",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Yes Return Main Page!", "No, I will create another user."},
                    "Yes Return Main Page!");

            if (action == 0) {
                new MainView(this.parser, this.crypto);
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            } else {
                this.userNameField.setText("");
                this.passwordField.setText("");
                this.confirmPasswordField.setText("");
            }
        }
    }

    private boolean isRequiredEmpty() {
        return userNameField.getText().equals("");
    }

    private boolean isPasswordsMatch() {
        return Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword());
    }

    private boolean isPasswordsComplex() {
        Matcher matcher = pattern.matcher(new String(passwordField.getPassword()));
        return matcher.matches();
    }
}
