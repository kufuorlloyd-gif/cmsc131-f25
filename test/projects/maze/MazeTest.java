package projects.maze;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MazeTest {

    @Test
    public void testSolve1() {
        Maze maze = MazeReader.load( "data/sample_maze1.txt" );
        //System.out.println("Maze successfully loaded!");
        assertNotNull(maze);
        maze.solve();

        maze.serialize("data/sample_maze1_out.txt");
    }

    @Test
    public void testSolve2() {
        Maze maze = MazeReader.load( "data/sample_maze2.txt" );
        //System.out.println("Maze successfully loaded!");
        assertNotNull(maze);
        maze.solve();

        maze.serialize("data/sample_maze2_out.txt");
    }

    @Test
    public void testSolve3() {
        Maze maze = MazeReader.load( "data/sample_maze3.txt" );
        //System.out.println("Maze successfully loaded!");
        assertNotNull(maze);
        maze.solve();

        maze.serialize("data/sample_maze3_out.txt");
    }


    @Test
    public void testSerializeError() {
        Maze maze = MazeReader.load( "" );
        //System.out.println("Maze successfully loaded!");
        //maze.serialize("data/sample_maze_out.txt");
        assertNull(maze);
    }
}