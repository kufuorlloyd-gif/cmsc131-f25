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

    public Audit() {
        this("Info.log", "Warning.log", "Error.log");
    }

    public Audit(String infoName, String warnName, String errorName){
        try {
            // Ensuring Audit folder exist so the FileWriters won't throw
            File rootPath = new File("data/Audit/");
            rootPath.mkdir();

            info = new FileWriter("data/Audit/" + infoName);
            warning = new FileWriter("data/Audit/" + warnName);
            error = new FileWriter("data/Audit/" + errorName);
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
