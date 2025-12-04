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
    public boolean isValid(){
        return row >= 0 && col >= 0;

    }
    public Coords getUp(){
        return new Coords(row,col-1);
    }
    public Coords getDown(){
        return new Coords(row,col+1);
    }
    public Coords getLeft(){
        return new Coords(row-1,col);
    }
    public Coords getRight(){
        return new Coords(row+1,col);
    }
    
    

}


