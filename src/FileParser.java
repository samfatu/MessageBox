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
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String[] getAllUsernames() {
        String[] usernames = new String[this.users.size()];

        for (int i = 0; i < this.users.size(); i++) {
            usernames[i] = this.users.get(i).getUserName();
        }

        return usernames;
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

    public boolean isMessageCodeUnique(String code) {
        for (Message message : this.getMessages()) {
            if (message.getId().equals(code)) {
                return false;
            }
        }

        return true;
    }

    public boolean isUserNameUnique(String userName) {
        for (User user : this.getUsers()) {
            if (user.getUserName().equals(userName)) {
                return false;
            }
        }

        return true;
    }

    public void addUser(User newUser) {
        this.users.add(newUser);
    }

    public void addMessage(Message newMessage) {
        this.messages.add(newMessage);
    }
}
