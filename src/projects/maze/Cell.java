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

    private CellStatus cellStatus;
    private boolean explored;

    private final Coords coords;
    private Cell[]neighbors;

    public Cell(Coords c, CellStatus s) {
        coords = c;
        cellStatus = s;
        neighbors = new Cell[4];
        explored = false;
    }
    public Cell[] getNeighbors(){
        return neighbors;
    }


    public Coords getCoords() {
        return coords;
    }

    public CellStatus getStatus(){
        return cellStatus;
    }

    public boolean isExplored(){
        return explored;
    }

    public void markExplored(){
        explored = true;
    }

    public void markPath(){
        if (cellStatus == CellStatus.O){
            cellStatus = CellStatus.P;
        }


    }
    public boolean addNeighbor(Cell c){
        for (int i = 0; i < neighbors.length; i++){
            if(neighbors[i] == null){
                neighbors[i] = c;
                return true;
            }
        }
        return false;

    }

}


