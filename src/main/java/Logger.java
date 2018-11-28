import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Logger {
    File file;
    FileOutputStream fileOutput;
    FilePrintStream tee;
    public Logger() throws FileNotFoundException {
        file = new File("output.txt");
        fileOutput = new FileOutputStream("output.txt");
        tee = new FilePrintStream(fileOutput, System.out);
        System.setOut(tee);
    }
    public void close(){
        tee.close();
    }
    public File getLog(){
        return file;
    }
}
