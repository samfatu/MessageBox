import java.nio.charset.StandardCharsets;

public class MessageBox {

    public static void main(String[] args) throws Exception {
        FileParser parser = new FileParser();
        Crypto crypto = new Crypto();

        for (Message m : parser.getMessages()) {
            System.out.println(m.getUserName() + new String(crypto.decrypt(Crypto.decoder(m.getContent()))));
        }

        for (User u : parser.getUsers()) {
            System.out.println(u.getUserName());
        }


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainView(parser, crypto);
            }
        });


       /* byte[] encrypted = crypo.encrypt("Hello World!".getBytes(StandardCharsets.UTF_8));

        System.out.println(new String(encrypted));

        byte[] decrypted = crypo.decrypt(encrypted);

        System.out.println(new String(decrypted));

        Message a = new Message("1",
                Crypto.encode(crypto.encrypt("Lorem ipsum dolor sir amet".getBytes(StandardCharsets.UTF_8))),
                Crypto.encode(crypto.hash("qwe".getBytes(StandardCharsets.UTF_8))),
                "mcg");
        User b = new User("abc", Crypto.encode(crypto.hash("1234".getBytes(StandardCharsets.UTF_8))));

        FileIO.writeFile("messages.txt", a.convertToByteArray());
        FileIO.writeFile("users.txt", b.convertToByteArray());

        Message a = new Message("123", "selamlar 123 abcd", "helllooo", "admin2");
        User b = new User("hello", "12390*");

        FileIO.writeFile("messages.txt", a.convertToByteArray());
        FileIO.writeFile("users.txt", b.convertToByteArray());
        */
        // byte[] textToHash = "Hello bro".getBytes(StandardCharsets.UTF_8);

    }
}
