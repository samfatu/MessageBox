import javax.swing.*;
import java.awt.*;

public class LeaveMessageView extends JFrame {
    private FileParser parser;
    private Crypto crypto;

    public LeaveMessageView(FileParser parser, Crypto crypto) throws HeadlessException {
        this.parser = parser;
        this.crypto = crypto;


    }
}
