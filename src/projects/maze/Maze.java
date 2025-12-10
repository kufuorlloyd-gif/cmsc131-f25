

package projects.maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * A maze is a group of Cells("spaces") organized in a specific combination to form a 
 * a certain pathway that leads to the "End" cell.
 */
public class Maze {

    private final Grid grid;
    private Coords start;

    public Maze(int maxCells) {
        grid = new Grid(maxCells);
    }

    public Grid getGrid() {
        return grid;
    }

    public Coords[] discoverNeighbors(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException(
                "Parameter status cannot be null"
            );
        }

        Coords coords = cell.getCoords();
        Coords[] possibleNeighbors = {
            coords.getUp(),
            coords.getDown(),
            coords.getLeft(),
            coords.getRight()
        };

        int numNeighbors = 0;
        Coords[] neighbors = new Coords[possibleNeighbors.length];
        for (Coords offset : possibleNeighbors) {
            if (grid.getCell(offset) != null) {
                neighbors[numNeighbors++] = offset;
            }
        }

        Coords[] validNeighbors = new Coords[numNeighbors];
        for (int i = 0; i < numNeighbors; i++) {
            validNeighbors[i] = neighbors[i];
        }
        return validNeighbors;
    }

    public void discoverAndSetupNeighbors() {
        Cell[] cells = grid.getAllCells();
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            Coords[] neighbors = discoverNeighbors(cell);
            cell.setNeighbors(neighbors);

            if (cell.getStatus() == CellStatus.S) {
                start = cell.getCoords();
            }
        }
    }

    public void solve(){
        grid.walk(grid.getCell(start));

    } 

    public boolean insertCell(Cell c){
        return grid.insertCell(c);
    }

    /**
     * reads all cells in the maze and formats them back in the grid as a file.
     * @param filename - Output filename.
     */
    public void serialize(String filename) {
        Cell[] cells = grid.getAllCells();

        FileWriter writer;
        try {
            writer = new FileWriter(new File(filename));
            // discover dimension of maze's underlying grid
            int maxRow = 0, maxCol = 0;
            int idxCell;
            for (idxCell = 0; idxCell < cells.length; idxCell++) {
                int row = cells[idxCell].getCoords().getRow();
                int col = cells[idxCell].getCoords().getCol();
                if (row > maxRow) { maxRow = row; }
                if (col > maxCol) { maxCol = col; }
            }
    
            // write cell statuses, using 'X' for absent (inaccessible) cells
            idxCell = 0;
            for (int row = 0; row <= maxRow; row++) {
                for (int col = 0; col <= maxCol; col++) {
                    Cell maybeCell = grid.getCell(
                        new Coords(row, col)
                    );
                    if (maybeCell != null) {
                        writer.write(maybeCell.getStatus().name());
                        idxCell++;
                    } else {
                        writer.write('X');
                    }
                    writer.write(' ');
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
   
}