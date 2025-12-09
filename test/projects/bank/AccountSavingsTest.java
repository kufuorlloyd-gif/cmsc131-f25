package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AccountSavingsTest {
@Test
    void testConstructorValidData() {
        SavingsAccount acc = new SavingsAccount("sav001", "John Doe", 1000.0);
        
        assertNotNull(acc);
        assertEquals("sav001", acc.getID());
        assertEquals("John Doe", acc.getOwnerName());
        assertEquals(1000.0, acc.getBalance(), 0.001);
    }

    @Test
    void testConstructorNullId() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> new SavingsAccount(null, "John Doe", 1000.0)
        );
        assertEquals("id cannot be null", e.getMessage());
    }

    @Test
    void testConstructorNullOwnerName() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> new SavingsAccount("sav001", null, 1000.0)
        );
        assertEquals("ownerName cannot be null", e.getMessage());
    }

    @Test
    void testConstructorZeroBalance() {
        SavingsAccount acc = new SavingsAccount("sav001", "John Doe", 0.0);
        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testConstructorNegativeBalance() {
        SavingsAccount acc = new SavingsAccount("sav001", "John Doe", -100.0);
        assertEquals(-100.0, acc.getBalance(), 0.001);
    }

    // Static factory method tests

    @Test
    void testMakeValidInput() {
        String csvLine = "savings,sav123,Jane Smith,2500.75";
        SavingsAccount acc = SavingsAccount.make(csvLine);
        
        assertNotNull(acc);
        assertEquals("sav123", acc.getID());
        assertEquals("Jane Smith", acc.getOwnerName());
        assertEquals(2500.75, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeNullInput() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> SavingsAccount.make(null)
        );
        assertEquals("inputLine cannot be null", e.getMessage());
    }

    @Test
    void testMakeZeroBalance() {
        String csvLine = "savings,sav000,Zero Balance,0.0";
        SavingsAccount acc = SavingsAccount.make(csvLine);
        
        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeLargeBalance() {
        String csvLine = "savings,sav999,Rich Person,999999.99";
        SavingsAccount acc = SavingsAccount.make(csvLine);
        
        assertEquals(999999.99, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeWithSpacesInName() {
        String csvLine = "savings,sav456,Mary Jane Watson,1500.0";
        SavingsAccount acc = SavingsAccount.make(csvLine);
        
        assertEquals("Mary Jane Watson", acc.getOwnerName());
    }

    // toString tests

    @Test
    void testToString() {
        SavingsAccount acc = new SavingsAccount("sav111", "Bob Johnson", 1234.56);
        String result = acc.toString();
        
        assertEquals("savings,sav111,Bob Johnson,1234.56", result);
    }

    @Test
    void testToStringFormatsDecimalCorrectly() {
        SavingsAccount acc = new SavingsAccount("sav222", "Alice Brown", 100.5);
        String result = acc.toString();
        
        assertEquals("savings,sav222,Alice Brown,100.50", result);
    }

    @Test
    void testToStringWithZeroBalance() {
        SavingsAccount acc = new SavingsAccount("sav000", "Zero User", 0.0);
        String result = acc.toString();
        
        assertEquals("savings,sav000,Zero User,0.00", result);
    }

    @Test
    void testToStringRoundTrip() {
        String original = "savings,sav789,Test User,5555.55";
        SavingsAccount acc = SavingsAccount.make(original);
        String recreated = acc.toString();
        
        assertEquals(original, recreated);
    }

    @Test
    void testToCSVCallsToString() {
        SavingsAccount acc = new SavingsAccount("sav333", "CSV Test", 750.25);
        
        assertEquals(acc.toString(), acc.toCSV());
    }

    // Integration tests with Account superclass methods

    @Test
    void testCredit() {
        SavingsAccount acc = new SavingsAccount("sav444", "Credit Test", 100.0);
        acc.credit(50.0);
        
        assertEquals(150.0, acc.getBalance(), 0.001);
    }

    @Test
    void testDebit() {
        SavingsAccount acc = new SavingsAccount("sav555", "Debit Test", 100.0);
        acc.debit(30.0);
        
        assertEquals(70.0, acc.getBalance(), 0.001);
    }

    @Test
    void testHasFunds() {
        SavingsAccount acc = new SavingsAccount("sav666", "Funds Test", 100.0);
        
        assertTrue(acc.hasFunds(50.0));
        assertTrue(acc.hasFunds(100.0));
        assertFalse(acc.hasFunds(150.0));
    }

}
