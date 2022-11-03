import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileIO {
    public static byte[] readFileAsByteArray(String filePath) {
        File file = new File(filePath);
        byte[] content = null;
        try {
            if (!file.exists()) {
                file.createNewFile(); // if file already exists will do nothing
            }
            content = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void writeFile(String outputFilePath, byte[] content) {
        File yourFile = new File(outputFilePath);
        try{
            if (!yourFile.exists()) {
                yourFile.createNewFile(); // if file already exists will do nothing
            }
            FileOutputStream oFile = new FileOutputStream(yourFile, true);
            oFile.write(content);
            oFile.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
