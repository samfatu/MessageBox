import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
        /*int length = this.id.length() + this.content.length + this.password.length + this.userName.length() + 5;
        return ByteBuffer.allocate(length)
                    .put(this.id.getBytes(StandardCharsets.UTF_8))
                    .put((byte) 31)
                    .put(this.content)
                    .put((byte) 31)
                    .put(this.password)
                    .put((byte) 31)
                    .put(this.userName.getBytes(StandardCharsets.UTF_8))
                    .put("/n".getBytes(StandardCharsets.UTF_8))
                    .array();
         */
        String object = this.id + (char) 31 + this.content + (char) 31 + this.password + (char) 31 + this.userName + "\n";
        return object.getBytes(StandardCharsets.UTF_8);
    }
}
