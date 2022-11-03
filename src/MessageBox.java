public class MessageBox {
    public static void main(String[] args) {
        FileParser parser = new FileParser();

        parser.readMessagesFile();
        parser.readUsersFile();

        Message a = new Message("123", "selamlar 123 abcd", "helllooo", "admin2");
        User b = new User("hello", "12390*");

        FileIO.writeFile("messages.txt", a.convertToByteArray());
        FileIO.writeFile("users.txt", b.convertToByteArray());
    }
}
