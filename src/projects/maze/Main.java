package projects.maze;

public class Main {

    static Maze phase1() {
        Maze maze = MazeReader.load( "data/sample_maze.txt" );
        System.out.println("Maze successfully loaded!");
        maze.serialize("data/sample_maze_phase1.txt");
        return maze;
    }

    public static void main(String[] args) {
        Maze maze = phase1();
    }

}
