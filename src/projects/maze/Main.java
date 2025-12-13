package projects.maze;

public class Main {

    static Maze phase1() {
        Maze maze = MazeReader.load( "data/sample_maze.txt" );
        System.out.println("Maze successfully loaded!");
        maze.serialize("data/sample_maze_phase1.txt");
        return maze;
    }

    static void phase2() {
        Maze maze = MazeReader.load("data/hard_maze.txt");
        maze.solve();
        maze.serialize("data/hard_maze.out");

        maze = MazeReader.load("data/hard_maze_nosol.txt");
        maze.solve();
        maze.serialize("data/hard_maze_nosol.out");
    }

    public static void main(String[] args) {
        Maze maze = phase1();
        phase2();
    }

}
