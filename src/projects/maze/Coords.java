package projects.maze;

public class Coords {

    private final int row;
    private final int col;

    public Coords(int r, int c) {
        row = r;
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Coords other) {
        return (
            getRow() == other.getRow()
            && getCol() == other.getCol()
        );
    }


    public boolean isPositive(){
        return row >= 0 && col >= 0;
    }
    /**
     * "getUp" means to get the coordinate that's above the current coordinate
     *
     */
    public Coords getUp(){
        return new Coords(row,col-1);
    }
/**
     * "getDown" means to get the coordinate that's below the current coordinate
     *
     */
    public Coords getDown(){
        return new Coords(row,col+1);
    }
    /**
     * "getLeft" means to get the coordinate that's to the left the current coordinate
     *
     */
    public Coords getLeft(){
        return new Coords(row-1,col);
    }
    /**
     * "getRight" means to get the coordinate that's to the right the current coordinate
     *
     */
    public Coords getRight(){
        return new Coords(row+1,col);
    }
    
    
    public String toString() {
        return String.format("%d, %d", col, row);
    }
}


