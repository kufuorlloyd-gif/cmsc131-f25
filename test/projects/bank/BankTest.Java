package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;


public class BankTest {
    private Bank bank;
    private Account acct;
    private static final String TEST_DIR = "data/tests/";

    @BeforeEach
    void setup() {
        bank = new Bank();
        acct = new SavingsAccount("id0", "Owner Name", 1.0);
        
        // Create test directory
        new File(TEST_DIR).mkdirs();
    }

    @AfterEach
    void cleanup() {
        // Clean up test files
        File testDir = new File(TEST_DIR);
        if (testDir.exists()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            testDir.delete();
        }
        
        // Clean up Audit directory
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

    // tests for add method

    @Test
    void testAddDataValidation(){
        Exception e = assertThrows(
            NullPointerException.class,
            () -> {bank.add(null);}
        );
    }

    @Test
    void testAddAccount() {
        // add account that's absent from bank's accounts
        boolean addAccountResult = bank.add(acct);
        assertEquals(
            true,
            addAccountResult,
            "bank.add should return true"
        );
        assertEquals(
            1,
            bank.getCount(),
            "bank.getCount should be 1"
        );

        // add account that's present in bank's accounts (no effect)
        addAccountResult = bank.add(acct);
        assertEquals(
            false,
            addAccountResult,
            "bank.add should return false"
        );
        assertEquals(
            1,
            bank.getCount(),
            "bank.getCount should still be 1"
        );
    }

    @Test
    void testAddAccountOverflow() {
        for (int idx = 0; idx <= 100; idx++) {
            Integer id = idx;
            bank.add(
                new CheckingAccount(
                    id.toString(),
                    "Owner Name",
                    1.0
                )
            );
        }

        // also serves as a test for getCount
        assertEquals(
            101,
            bank.getCount(),
            "bank should hold 101 accounts"
        );
    }

    // tests for find method

    @Test
    void testFindDataValidation() {
        Exception e = assertThrows(
            NullPointerException.class,
            () -> {bank.find(null);}
        );
        
    }

    @Test
    void testFind() {
        bank.add(acct);
        assertEquals(
            0,
            bank.find(acct.getID()),
            "acct should be at index 0"
        );
        assertEquals(
            -1,
            bank.find("id1"),
            "result should be -1 when finding absent account"
        );
    }

    // Tests for loadAccounts

    @Test
    void testLoadAccountsFailureMode() {
        String nonExistentFile = TEST_DIR + "nonexistent.csv";
        
        boolean result = bank.loadAccounts(nonExistentFile);
        
        assertFalse(result, "loadAccounts should return false for non-existent file");
        assertEquals(0, bank.getCount(), "No accounts should be loaded");
    }

    @Test
    void testLoadAccountsPreservesData() throws IOException {
        // Create test CSV file with sample accounts
        String testFile = TEST_DIR + "test_accounts.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.write("savings,sav001,Alice Johnson,1500.50\n");
        writer.write("checking,chk002,Bob Smith,2750.75\n");
        writer.write("savings,sav003,Carol White,500.00\n");
        writer.close();

        boolean result = bank.loadAccounts(testFile);

        assertTrue(result, "loadAccounts should return true");
        assertEquals(3, bank.getCount(), "Should load 3 accounts");

        // Verify first account
        Account acc1 = bank.findAccount("sav001");
        assertNotNull(acc1, "First account should exist");
        assertEquals("sav001", acc1.getID());
        assertEquals("Alice Johnson", acc1.getOwnerName());
        assertEquals(1500.50, acc1.getBalance(), 0.001);
        assertTrue(acc1 instanceof SavingsAccount);

        // Verify second account
        Account acc2 = bank.findAccount("chk002");
        assertNotNull(acc2, "Second account should exist");
        assertEquals("chk002", acc2.getID());
        assertEquals("Bob Smith", acc2.getOwnerName());
        assertEquals(2750.75, acc2.getBalance(), 0.001);
        assertTrue(acc2 instanceof CheckingAccount);

        // Verify third account
        Account acc3 = bank.findAccount("sav003");
        assertNotNull(acc3, "Third account should exist");
        assertEquals("sav003", acc3.getID());
        assertEquals("Carol White", acc3.getOwnerName());
        assertEquals(500.00, acc3.getBalance(), 0.001);
        assertTrue(acc3 instanceof SavingsAccount);
    }

    @Test
    void testLoadAccountsReturnsTrueOnSuccess() throws IOException {
        String testFile = TEST_DIR + "success_test.csv";
        FileWriter writer = new FileWriter(testFile);
        writer.write("checking,test123,Test User,100.00\n");
        writer.close();

        boolean result = bank.loadAccounts(testFile);

        assertTrue(result, "loadAccounts should return true on success");
    }
    // Tests for writeAccounts

    @Test
    void testWriteAccountsFailureMode() {
        // Try to write to an invalid path (directory that doesn't exist and can't be created)
        String invalidPath = "/invalid/path/that/does/not/exist/output.csv";
        
        boolean result = bank.writeAccounts(invalidPath);
        
        assertFalse(result, "writeAccounts should return false for invalid path");
    }

    @Test
    void testWriteAccountsPreservesData() throws IOException {
        // Add accounts to bank
        bank.add(new SavingsAccount("sav100", "David Green", 3000.25));
        bank.add(new CheckingAccount("chk200", "Emma Brown", 1500.50));
        bank.add(new SavingsAccount("sav300", "Frank Wilson", 750.00));

        String outputFile = TEST_DIR + "output_accounts.csv";
        boolean result = bank.writeAccounts(outputFile);

        assertTrue(result, "writeAccounts should succeed");

        // Read back the file and verify content
        String content = new String(Files.readAllBytes(Paths.get(outputFile)));
        String[] lines = content.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines");

        assertTrue(lines[0].contains("savings"));
        assertTrue(lines[0].contains("sav100"));
        assertTrue(lines[0].contains("David Green"));
        assertTrue(lines[0].contains("3000.25"));

        assertTrue(lines[1].contains("checking"));
        assertTrue(lines[1].contains("chk200"));
        assertTrue(lines[1].contains("Emma Brown"));
        assertTrue(lines[1].contains("1500.50"));

        assertTrue(lines[2].contains("savings"));
        assertTrue(lines[2].contains("sav300"));
        assertTrue(lines[2].contains("Frank Wilson"));
        assertTrue(lines[2].contains("750.00"));
    }

    @Test
    void testWriteAccountsReturnsTrueOnSuccess() {
        bank.add(new CheckingAccount("test999", "Test User", 100.0));
        
        String outputFile = TEST_DIR + "success_write.csv";
        boolean result = bank.writeAccounts(outputFile);

        assertTrue(result, "writeAccounts should return true on success");
        assertTrue(new File(outputFile).exists(), "Output file should exist");
    }
    
    @Test
    void testWriteAccountsOverwritesExistingFile() throws IOException {
        String outputFile = TEST_DIR + "overwrite.csv";
        
        // Write initial content
        bank.add(new SavingsAccount("old", "Old Account", 100.0));
        bank.writeAccounts(outputFile);

        // Create new bank with different account
        Bank bank2 = new Bank();
        bank2.add(new CheckingAccount("new", "New Account", 200.0));
        bank2.writeAccounts(outputFile);

        // Verify file was overwritten
        Bank bank3 = new Bank();
        bank3.loadAccounts(outputFile);
        
        assertEquals(1, bank3.getCount(), "Should only have new account");
        assertNull(bank3.findAccount("old"), "Old account should not exist");
        assertNotNull(bank3.findAccount("new"), "New account should exist");
    }
    
    @Test
    void testFindAccountInterface() {
        bank.add(acct);
        bank.add(new CheckingAccount("chk001", "Test User", 500.0));

        Account found = bank.findAccount("id0");
        assertNotNull(found, "Should find existing account");
        assertEquals("id0", found.getID());
        assertEquals("Owner Name", found.getOwnerName());

        Account notFound = bank.findAccount("nonexistent");
        assertNull(notFound, "Should return null for non-existent account");
    }
}