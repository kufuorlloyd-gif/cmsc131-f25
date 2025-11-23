package projects.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Audit {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // dusel: ok but one FileWriter would be enough
    private FileWriter info;
    private FileWriter warning;
    private FileWriter error;

    public Audit(){
        try {
            /** dusel
             * better to have filenames for info, warning, error 
             * as inputs to this function
             */
            // Ensuring Audit folder exist so the FileWriters won't throw
            File rootPath = new File("/Audit/");
            rootPath.mkdir();

            info = new FileWriter("Audit/Info.log");
            warning = new FileWriter("Audit/Warning.log");
            error = new FileWriter("Audit/Error.log");
        } catch(IOException e) {
            e.printStackTrace();
            /** dusel
             * trying to use a null filewriter in downsteam code
             * like bank.processTransactions will make your program crash. 
             * worse, you'll see a NullPointerException, which is not so 
             * helpful for debugging.
             * 
             * instead of this, just have your constructor throw, which will 
             * (1) prevent your program from running in the first place
             * (2) give you helpful info about what is wrong
             */
            info = null;
            warning = null;
            error = null;
        }
    }
    
    
    public void writeInfo(String msg){
        try {
            info.write(getCurrentTime() + msg);
            info.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void writeWarning(String msg){
        try {
            warning.write(getCurrentTime() + msg);
            warning.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    public void writeError(String msg){
        try {
            error.write(getCurrentTime() + msg);
            error.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static String getCurrentTime(){
        return "[" + LocalDateTime.now().format(TIME_FORMATTER) + "] ";
    }
}
