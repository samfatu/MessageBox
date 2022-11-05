import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileParser {
    private ArrayList<Message> messages;
    private ArrayList<User> users;

    public FileParser() {
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();

        readUsersFile();
        readMessagesFile();
    }

    public void readMessagesFile() {
        byte[] messagesFileContent = FileIO.readFileAsByteArray("messages.txt");
        String[] lines = (new String(messagesFileContent)).split("\n");

        for (String line : lines) {
            String[] messageProperties = line.split("[\\x1F]"); // x1F == 31 in decimal
            if (messageProperties.length == 4) {
                messages.add(new Message(messageProperties[0], messageProperties[1], messageProperties[2], messageProperties[3]));
            }
        }

        /*for (Message m : this.messages) {
            System.out.println(m.getContent());
        }*/
    }

    public void readUsersFile() {
        byte[] usersFileContent = FileIO.readFileAsByteArray("users.txt");
        String[] lines = (new String(usersFileContent)).split("\n");

        for (String line : lines) {
            String[] userProperties = line.split("[\\x1F]"); // x1F == 31 in decimal
            if (userProperties.length == 2) {
                this.users.add(new User(userProperties[0], userProperties[1]));
            }
        }

        /*for (User u : this.users) {
            System.out.println(u.getUserName());
        }*/
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isMessageExistsWithCodeAndUserName(String code, String userName) {
        for (Message message : this.getMessages()) {
            if (code.equals(message.getId()) && userName.equals(message.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public Message getMessageWithCode(String code) {
        for (Message message : this.getMessages()) {
            if (code.equals(message.getId())) {
                return message;
            }
        }
        return null;
    }

    public boolean isUserExistsWithUserName(String userName) {
        for (User user : this.getUsers()) {
            if (userName.equals(user.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public User getUserWithUserName(String userName) {
        for (User user : this.getUsers()) {
            if (userName.equals(user.getUserName())) {
                return user;
            }
        }
        return null;
    }
}
