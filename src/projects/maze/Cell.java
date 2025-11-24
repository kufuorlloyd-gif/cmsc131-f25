package projects.maze;

public class Cell {
    
    /** `Cell` class additions

Extra attributes

- `neighbors` (Coords[]) - coordinates of neighbors
    - needs accessor/mutator

- `explored` (boolean) - traversal flag (defaults to false)
    - needs accessor/mutator

- `status` (CellStatus enum) - cell's role/state
    - Enum values    
        - `S` maze entrance
        - `E` maze exit  
        - `O` open cell
        - `P` part of solution path
    - Needs accessor/mutator

A cell will be constructed with a coordinates and a status. Decide for yourself what are sensible default values (if any) for the other attributes.
 */

    private final CellStatus cellStatus;


    private final Coords coords;

    public Cell(Coords c, CellStatus s) {
        coords = c;
        cellStatus = s;
    }


    public Coords getCoords() {
        return coords;
    }

    public CellStatus getStatus(){
        return cellStatus;
    }

}


