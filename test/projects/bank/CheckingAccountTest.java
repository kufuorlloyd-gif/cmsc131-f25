package projects.bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

    @Test
    void testConstructorValidData() {
        CheckingAccount acc = new CheckingAccount("chk001", "John Doe", 1000.0);
        
        assertNotNull(acc);
        assertEquals("chk001", acc.getID());
        assertEquals("John Doe", acc.getOwnerName());
        assertEquals(1000.0, acc.getBalance(), 0.001);
    }

    @Test
    void testConstructorNullId() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> new CheckingAccount(null, "John Doe", 1000.0)
        );
        assertEquals("id cannot be null", e.getMessage());
    }

    @Test
    void testConstructorNullOwnerName() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> new CheckingAccount("chk001", null, 1000.0)
        );
        assertEquals("ownerName cannot be null", e.getMessage());
    }

    @Test
    void testConstructorZeroBalance() {
        CheckingAccount acc = new CheckingAccount("chk001", "John Doe", 0.0);
        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testConstructorNegativeBalance() {
        CheckingAccount acc = new CheckingAccount("chk001", "John Doe", -100.0);
        assertEquals(-100.0, acc.getBalance(), 0.001);
    }

    // Static factory method tests

    @Test
    void testMakeValidInput() {
        String csvLine = "checking,chk123,Jane Smith,2500.75";
        CheckingAccount acc = CheckingAccount.make(csvLine);
        
        assertNotNull(acc);
        assertEquals("chk123", acc.getID());
        assertEquals("Jane Smith", acc.getOwnerName());
        assertEquals(2500.75, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeNullInput() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> CheckingAccount.make(null)
        );
        assertEquals("inputLine cannot be null", e.getMessage());
    }

    @Test
    void testMakeZeroBalance() {
        String csvLine = "checking,chk000,Zero Balance,0.0";
        CheckingAccount acc = CheckingAccount.make(csvLine);
        
        assertEquals(0.0, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeLargeBalance() {
        String csvLine = "checking,chk999,Rich Person,999999.99";
        CheckingAccount acc = CheckingAccount.make(csvLine);
        
        assertEquals(999999.99, acc.getBalance(), 0.001);
    }

    @Test
    void testMakeWithSpacesInName() {
        String csvLine = "checking,chk456,Mary Watson,1500.0";
        CheckingAccount acc = CheckingAccount.make(csvLine);
        
        assertEquals("Mary Watson", acc.getOwnerName());
    }

    // toString tests

    @Test
    void testToString() {
        CheckingAccount acc = new CheckingAccount("chk111", "Bob Johnson", 1234.56);
        String result = acc.toString();
        
        assertEquals("checking,chk111,Bob Johnson,1234.56", result);
    }

    @Test
    void testToStringWithZeroBalance() {
        CheckingAccount acc = new CheckingAccount("chk000", "Zero User", 0.0);
        String result = acc.toString();
        
        assertEquals("checking,chk000,Zero User,0.00", result);
    }

    @Test
    void testToStringRoundTrip() {
        String original = "checking,chk789,Test User,5555.55";
        CheckingAccount acc = CheckingAccount.make(original);
        String recreated = acc.toString();
        
        assertEquals(original, recreated);
    }

    // Integration tests with Account superclass methods

    @Test
    void testCredit() {
        CheckingAccount acc = new CheckingAccount("chk444", "Credit Test", 100.0);
        acc.credit(50.0);
        
        assertEquals(150.0, acc.getBalance(), 0.001);
    }

    @Test
    void testDebit() {
        CheckingAccount acc = new CheckingAccount("chk555", "Debit Test", 100.0);
        acc.debit(30.0);
        
        assertEquals(70.0, acc.getBalance(), 0.001);
    }

    @Test
    void testHasFunds() {
        CheckingAccount acc = new CheckingAccount("chk666", "Funds Test", 100.0);
        
        assertTrue(acc.hasFunds(50.0));
        assertTrue(acc.hasFunds(100.0));
        assertFalse(acc.hasFunds(150.0));
    }
}