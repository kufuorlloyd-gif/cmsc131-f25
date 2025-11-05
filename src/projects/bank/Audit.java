package projects.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Audit {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private FileWriter Info;
    private FileWriter Warning;
    private FileWriter Error;

    public Audit(){
        try {
            // Ensuring Audit folder exist so the FileWriters won't throw
            File rootPath = new File("/Audit/");
            rootPath.mkdir();

            Info = new FileWriter("Audit/Info.log");
            Warning = new FileWriter("Audit/Warning.log");
            Error = new FileWriter("Audit/Error.log");
        } catch(IOException e) {
            e.printStackTrace();
            Info = null;
            Warning = null;
            Error = null;
        }
    }
    
    
    public void writeInfo(String msg){
        try {
            Info.write(getCurrentTime() + msg);
            Info.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void writeWarning(String msg){
        try {
            Warning.write(getCurrentTime() + msg);
            Warning.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    public void writeError(String msg){
        try {
            Error.write(getCurrentTime() + msg);
            Error.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static String getCurrentTime(){
        return "[" + LocalDateTime.now().format(TIME_FORMATTER) + "] ";
    }
}
