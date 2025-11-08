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

    // TODO add tests for 
    // static factory throws on null input
    // static factory preserves data
    // toCSV output is correct

}