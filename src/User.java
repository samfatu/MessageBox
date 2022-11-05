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
        /*int length = this.userName.length() + this.password.length + 3;
        return ByteBuffer.allocate(length)
                .put(this.userName.getBytes(StandardCharsets.UTF_8))
                .put((byte) 31)
                .put(this.password)
                .put("/n".getBytes(StandardCharsets.UTF_8))
                .array();
         */
        String object = this.userName + (char) 31 + this.password + "\n";
        return object.getBytes(StandardCharsets.UTF_8);
    }
}
