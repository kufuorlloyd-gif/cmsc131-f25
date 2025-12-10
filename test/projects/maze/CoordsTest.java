package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CoordsTest {
    @Test
    void testEquals() {
        Coords c1 = new Coords(6, 7);
        Coords c2 = c1;
        Coords c3 = new Coords(6, 7);
        Coords c4 = new Coords(6, 8);

        assertTrue(c1.equals(c1)); // reflexive
        assertTrue(c1.equals(c2)); // alias
        assertTrue(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c2.equals(c4));
        assertFalse(c3.equals(c4));
    }
}