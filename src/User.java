import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] convertToByteArray() {
        String object = this.userName + (char) 31 + this.password + "\n";
        return object.getBytes(StandardCharsets.UTF_8);
    }
}
