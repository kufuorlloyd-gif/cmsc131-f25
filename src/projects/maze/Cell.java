package projects.maze;

/**
 * A cell is a "space" in the maze that could be either a Start,end, or open path.
 */
public class Cell {

    private CellStatus cellStatus;
    private boolean explored;

    private final Coords coords;
    private Coords[]neighbors;

    public Cell(Coords c, CellStatus s) {
        if (c == null) {
            throw new IllegalArgumentException("Coords (c) cannot be null");
        }
        if (s == null) {
            throw new IllegalArgumentException("Status (s) cannot be null");
        }
        coords = c;
        cellStatus = s;
        explored = false;
        neighbors = new Coords[0];
    }
    public Coords[] getNeighbors(){
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

    public void setNeighbors(Coords[] inNeighbors) {
        neighbors = inNeighbors;
    }

}


