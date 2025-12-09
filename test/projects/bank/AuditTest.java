package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AuditTest {

    Path tempDir;
    
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @BeforeEach
    void setUp() {
        // Clean up any existing Audit directory
        deleteAuditDirectory();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up after tests
        deleteAuditDirectory();
    }
    
    private void deleteAuditDirectory() {
        File auditDir = new File("Audit");
        if (auditDir.exists()) {
            File[] files = auditDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            auditDir.delete();
        }
    }
    
    @Test
    void testConstructorCreatesFilesAndDirectory() {
        Audit audit = new Audit();
        
        File auditDir = new File("data/Audit");
        assertTrue(auditDir.exists());
        assertTrue(auditDir.isDirectory());
    }
    
    @Test
    void testWriteInfo() throws IOException {
        Audit audit = new Audit();
        String testMessage = "Test info message\n";
        
        audit.writeInfo(testMessage);
        
        String content = readFile("data/Audit/Info.log");
        assertTrue(content.contains(testMessage), "Log should contain the message");
        assertTrue(content.contains("["), "Log should contain timestamp bracket");
    }
    
    @Test
    void testWriteWarning() throws IOException {
        Audit audit = new Audit();
        String testMessage = "Test warning message\n";
        
        audit.writeWarning(testMessage);
        
        String content = readFile("data/Audit/Warning.log");
        assertTrue(content.contains(testMessage), "Log should contain the message");
    }
    
    @Test
    void testWriteError() throws IOException {
        Audit audit = new Audit();
        String testMessage = "Test error message\n";
        
        audit.writeError(testMessage);
        
        String content = readFile("data/Audit/Error.log");
        assertTrue(content.contains(testMessage), "Log should contain the message");
    }
    
    @Test
    void testMultipleWrites() throws IOException {
        Audit audit = new Audit();
        
        audit.writeInfo("First message\n");
        audit.writeInfo("Second message\n");
        audit.writeInfo("Third message\n");
        
        String content = readFile("data/Audit/Info.log");
        assertTrue(content.contains("First message"), "Should contain first message");
        assertTrue(content.contains("Second message"), "Should contain second message");
        assertTrue(content.contains("Third message"), "Should contain third message");
    }
    
    @Test
    void testSeparateLogFiles() throws IOException {
        Audit audit = new Audit();
        
        audit.writeInfo("Info message\n");
        audit.writeWarning("Warning message\n");
        audit.writeError("Error message\n");
        
        String infoContent = readFile("data/Audit/Info.log");
        String warningContent = readFile("data/Audit/Warning.log");
        String errorContent = readFile("data/Audit/Error.log");
        
        assertTrue(infoContent.contains("Info message"));
        assertFalse(infoContent.contains("Warning message"));
        assertFalse(infoContent.contains("Error message"));
        
        assertTrue(warningContent.contains("Warning message"));
        assertFalse(warningContent.contains("Info message"));
        
        assertTrue(errorContent.contains("Error message"));
        assertFalse(errorContent.contains("Info message"));
    }
    
    @Test
    void testEmptyMessage() throws IOException {
        Audit audit = new Audit();
        
        audit.writeInfo("");
        
        String content = readFile("data/Audit/Info.log");
        assertTrue(content.matches(".*\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\].*"), 
                   "Log should still contain timestamp");
    }
    
    @Test
    void testSpecialCharacters() throws IOException {
        Audit audit = new Audit();
        String specialMessage = "Message with special chars: @#$%^&*()\n";
        
        audit.writeInfo(specialMessage);
        
        String content = readFile("data/Audit/Info.log");
        assertTrue(content.contains(specialMessage), 
                   "Special characters should be preserved");
    }
    
    // Helper method to read file content
    private String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
