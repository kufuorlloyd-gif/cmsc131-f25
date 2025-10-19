package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    void testDataValidation() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Account(null, "name", 0.0, AccountType.CHECKING);}
        );
        assertEquals("id cannot be null", e.getMessage());

        e = assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Account("id", null, 0.0, AccountType.CHECKING);}
        );
        assertEquals("ownerName cannot be null", e.getMessage());

        e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Account("id", "Owner Name", 0.0, null);}
        );
        assertEquals("type cannot be null", e.getMessage());
    }

    // TODO add tests for 
    // static factory throws on null input
    // static factory preserves data
    // toCSV output is correct

}
