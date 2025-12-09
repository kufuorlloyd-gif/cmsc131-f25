package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    void testDataValidation() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> {
                // must instantiate subclass because Account is now abstract
                new CheckingAccount(null, "name", 0.0);
            }
        );
        assertEquals("id cannot be null", e.getMessage());

        e = assertThrows(
            IllegalArgumentException.class,
            () -> {
                new CheckingAccount("id", null, 0.0);}
        );
        assertEquals("ownerName cannot be null", e.getMessage());

    }
    
    @Test
    void testGetOwnerName(){
        Account acc = new CheckingAccount("id", "Robert", 0.0);
        assertEquals("Robert", acc.getOwnerName());
    }
    
    @Test
    void testGetBalance(){
        Account acc = new CheckingAccount("id", "Robert", 110.5);
        assertEquals(110.5, acc.getBalance());
    }
    
    @Test
    void testGetCSV(){
        Account acc = new CheckingAccount("tryh", "Robert", 0.0);
        assertEquals("checking,tryh,Robert,0.0", acc.toCSV());
    }

    // Static factory method tests
    
    @Test
    void testStaticFactoryThrowsOnNullInput() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> Account.make(null)
        );
        assertEquals("inputLine cannot be null", e.getMessage());
    }
    
    @Test
    void testStaticFactoryPreservesDataForChecking() {
        String csvLine = "checking,acc123,John Doe,500.75";
        Account acc = Account.make(csvLine);
        
        assertNotNull(acc);
        assertTrue(acc instanceof CheckingAccount, "Should create CheckingAccount");
        assertEquals("acc123", acc.getID());
        assertEquals("John Doe", acc.getOwnerName());
        assertEquals(500.75, acc.getBalance(), 0.001);
    }
    
    @Test
    void testStaticFactoryPreservesDataForSavings() {
        String csvLine = "savings,sav456,Jane Smith,1250.50";
        Account acc = Account.make(csvLine);
        
        assertNotNull(acc);
        assertTrue(acc instanceof SavingsAccount, "Should create SavingsAccount");
        assertEquals("sav456", acc.getID());
        assertEquals("Jane Smith", acc.getOwnerName());
        assertEquals(1250.50, acc.getBalance(), 0.001);
    }
    
    @Test
    void testStaticFactoryHandlesZeroBalance() {
        String csvLine = "checking,zero123,Zero Balance,0.0";
        Account acc = Account.make(csvLine);
        
        assertEquals(0.0, acc.getBalance(), 0.001);
    }
    
    @Test
    void testStaticFactoryHandlesLargeBalance() {
        String csvLine = "savings,rich999,Wealthy Person,999999.99";
        Account acc = Account.make(csvLine);
        
        assertEquals(999999.99, acc.getBalance(), 0.001);
    }
    
    @Test
    void testStaticFactoryCaseInsensitiveType() {
        String csvLine = "CHECKING,upper123,Upper Case,100.0";
        Account acc = Account.make(csvLine);
        
        assertTrue(acc instanceof CheckingAccount);
        
        csvLine = "SaViNgS,mixed456,Mixed Case,200.0";
        acc = Account.make(csvLine);
        assertTrue(acc instanceof SavingsAccount);
    }
    
    @Test
    void testStaticFactoryThrowsOnInvalidType() {
        assertThrows(
            IllegalArgumentException.class,
            () -> Account.make("invalid,id123,Name,100.0")
        );
    }
    
    // toCSV tests
    
    @Test
    void testToCSVFormatChecking() {
        Account acc = new CheckingAccount("chk001", "Alice Brown", 1500.25);
        String csv = acc.toCSV();
        
        assertTrue(csv.contains("checking"), "CSV should contain account type");
        assertTrue(csv.contains("chk001"), "CSV should contain ID");
        assertTrue(csv.contains("Alice Brown"), "CSV should contain owner name");
        assertTrue(csv.contains("1500.25"), "CSV should contain balance");
    }
    
    @Test
    void testToCSVFormatSavings() {
        Account acc = new SavingsAccount("sav002", "Bob Green", 2750.50);
        String csv = acc.toCSV();
        
        assertTrue(csv.contains("savings"), "CSV should contain account type");
        assertTrue(csv.contains("sav002"), "CSV should contain ID");
        assertTrue(csv.contains("Bob Green"), "CSV should contain owner name");
        assertTrue(csv.contains("2750.5"), "CSV should contain balance");
    }
    
    @Test
    void testToCSVWithZeroBalance() {
        Account acc = new CheckingAccount("zero001", "Empty Account", 0.0);
        String csv = acc.toCSV();
        
        assertTrue(csv.contains("0.0"), "CSV should show zero balance");
    }
    
    @Test
    void testToCSVRoundTrip() {
        // Test that we can create an account, convert to CSV, and recreate
        Account original = new CheckingAccount("rt123", "Round Trip", 750.25);
        String csv = original.toCSV();
        Account recreated = Account.make(csv);
        
        assertEquals(original.getID(), recreated.getID());
        assertEquals(original.getOwnerName(), recreated.getOwnerName());
        assertEquals(original.getBalance(), recreated.getBalance(), 0.001);
        assertEquals(original.getClass(), recreated.getClass());
    }
    
    // Additional helper method tests
    
    @Test
    void testCredit() {
        Account acc = new CheckingAccount("cred123", "Test User", 100.0);
        acc.credit(50.0);
        assertEquals(150.0, acc.getBalance(), 0.001);
    }
    
    @Test
    void testDebit() {
        Account acc = new CheckingAccount("deb123", "Test User", 100.0);
        acc.debit(30.0);
        assertEquals(70.0, acc.getBalance(), 0.001);
    }
    
    @Test
    void testHasFunds() {
        Account acc = new CheckingAccount("funds123", "Test User", 100.0);
        
        assertTrue(acc.hasFunds(50.0), "Should have sufficient funds");
        assertTrue(acc.hasFunds(100.0), "Should have exact funds");
        assertFalse(acc.hasFunds(150.0), "Should not have sufficient funds");
    }
    
    @Test
    void testGetID() {
        Account acc = new CheckingAccount("id999", "Test User", 0.0);
        assertEquals("id999", acc.getID());
    }
}