import java.nio.charset.StandardCharsets;

public class Message {
    private String id;
    private String content;
    private String password;
    private String userName;

    public Message(String id, String content, String password, String userName) {
        this.id = id;
        this.content = content;
        this.password = password;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] convertToByteArray() {
        String object = this.id + (char) 31 + this.content + (char) 31 + this.password + (char) 31 + this.userName + "\n";
        return object.getBytes(StandardCharsets.UTF_8);
    }
}
