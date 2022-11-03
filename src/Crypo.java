import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Crypo {
    private final byte[] secretKey = "9mng6517".getBytes(StandardCharsets.UTF_8);
    private final byte[] iv = "a76nb5h9".getBytes(StandardCharsets.UTF_8);
    private SecretKeySpec secretKeySpec;
    private IvParameterSpec ivSpec;
    private Cipher cipher;

    public Crypo() {
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

    public String hash() {
        return null;
    }
}
