
public class MessageBox {

    public static void main(String[] args) {
        FileParser parser = new FileParser();
        Crypto crypto = new Crypto();

        javax.swing.SwingUtilities.invokeLater(() -> new MainView(parser, crypto));
    }
}
