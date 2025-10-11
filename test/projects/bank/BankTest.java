package projects.bank;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;

public class BankTest {
private Bank bank;
    private Account acc1;
    private Account acc2;

    @BeforeEach
    public void setUp() {
        bank = new Bank(4); // bank can hold up to 4 accounts
        acc1 = new Account("100", "Alice", 500.00, 
        AccountType.CHECKING);
        acc2 = new Account("200", "Bob", 700.00, 
        AccountType.SAVINGS);
        bank.addAccount(acc1);
        bank.addAccount(acc2);
    }


    public void testAccountCount() {
        assertEquals(2, bank.getAccountCount());
    }

    
    public void testFindAccountExists() {
        Account found = bank.findAccount("100");
        assertNotNull(found);
        assertEquals("Alice", found.getaccountOwner());
    }

    
    public void testFindAccountNotFound() {
        Account found = bank.findAccount("999");
        assertNull(found);
    }

    public void testFindAccountsByOwner() {
        Account[] results = bank.findAccountsByOwner("Alice");
        assertEquals(1, results.length);
        assertEquals("100", results[0].getaccountId());
    }
}
