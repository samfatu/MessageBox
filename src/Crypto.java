import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Crypto {
    private final byte[] secretKey = "9mng6517".getBytes(StandardCharsets.UTF_8);
    private final byte[] iv = "a76nb5h9".getBytes(StandardCharsets.UTF_8);
    private SecretKeySpec secretKeySpec;
    private IvParameterSpec ivSpec;
    private Cipher cipher;

    public Crypto() {
        this.secretKeySpec = new SecretKeySpec(this.secretKey, "DES");
        this.ivSpec = new IvParameterSpec(iv);
    }

    public byte[] encrypt(byte[] text) throws Exception {
        this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec, this.ivSpec);
        return this.cipher.doFinal(text);
    }

    public byte[] decrypt(byte[] text) throws Exception {
        this.cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec, this.ivSpec);
        return this.cipher.doFinal(text);
    }

    public byte[] hash(byte[] text) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(text);
    }

    public boolean isHashesEqual(byte[] first, byte[] second) throws Exception {
        return new String(hash(first)).equals(new String(hash(second)));
    }

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decoder(String data) {
        return Base64.getDecoder().decode(data);
    }
}
