package projects.maze;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    void constructorDataValidation() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Cell(null, CellStatus.S);}
        );
        assertNotEquals(
            e.getMessage(),
            ""
        );
        e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Cell(new Coords(0, 0), null);}
        );
        assertNotEquals(
            e.getMessage(),
            ""
        );
    }

    @Test
    void testNeighbors() {
        Cell a = new Cell(new Coords(0,0), CellStatus.S);
        assertEquals(a.getNeighbors().length, 0);
        a.setNeighbors(new Coords[4]);
        assertEquals(a.getNeighbors().length, 4);
    }

    @Test
    void testCoords() {
        Cell a = new Cell(new Coords(5,5), CellStatus.O);
        assertTrue(a.getCoords().equals(new Coords(5,5)));
    }
    @Test
    void testStatus() {
        Cell a = new Cell(new Coords(0,0), CellStatus.O);
        assertEquals(a.getStatus(),CellStatus.O);
    }
    @Test
    void testExplored() {
        Cell a = new Cell(new Coords(0,0), CellStatus.S);
        assertFalse(a.isExplored());
        a.markExplored();
        assertTrue(a.isExplored());
    }
    @Test
    void testPath() {
        Cell a = new Cell(new Coords(0,0), CellStatus.O);
        assertEquals(a.getStatus(),CellStatus.O);
        a.markPath();
        assertEquals(a.getStatus(),CellStatus.P);

        // test unmarkable
        Cell b = new Cell(new Coords(0,0), CellStatus.S);
        assertEquals(b.getStatus(),CellStatus.S);
        b.markPath();
        assertEquals(b.getStatus(),CellStatus.S);

    }
}