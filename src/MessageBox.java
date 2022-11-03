import view.MainView;

public class MessageBox {
    public static void main(String[] args) throws Exception {


        FileParser parser = new FileParser();

        parser.readMessagesFile();
        parser.readUsersFile();

        Crypo crypo = new Crypo();


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainView();
            }
        });

       /* byte[] encrypted = crypo.encrypt("Hello World!".getBytes(StandardCharsets.UTF_8));

        System.out.println(new String(encrypted));

        byte[] decrypted = crypo.decrypt(encrypted);

        System.out.println(new String(decrypted));


        Message a = new Message("123", "selamlar 123 abcd", "helllooo", "admin2");
        User b = new User("hello", "12390*");

        FileIO.writeFile("messages.txt", a.convertToByteArray());
        FileIO.writeFile("users.txt", b.convertToByteArray());
        */
        // byte[] textToHash = "Hello bro".getBytes(StandardCharsets.UTF_8);

    }
}
