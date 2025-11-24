package projects.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MazeTest {

    @Test
    public void testSerialize() {
        Maze maze = MazeReader.load( "data/sample_maze.txt" );
        //System.out.println("Maze successfully loaded!");
        assertNotNull(maze);


        maze.serialize("data/sample_maze_out.txt");
    }


    @Test
    public void testSerializeError() {
        Maze maze = MazeReader.load( "" );
        //System.out.println("Maze successfully loaded!");
        //maze.serialize("data/sample_maze_out.txt");
        assertNull(maze);
    }
}