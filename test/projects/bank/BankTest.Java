package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BankTest {
    private Bank bank;
    private Account acct;

    @BeforeEach
    void setup() {
        bank = new Bank();
        acct = new SavingsAccount("id0", "Owner Name", 1.0);
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

    // TODO add test coverage for 
    // loadAccounts failure mode
    // loadAccounts preserves data
    // loadAccounts returns true on succeed
    // writeAccounts failure mode
    // writeAccounts preserves data
    // writeAccounts returns true on succeed

} // end: class BankTest
