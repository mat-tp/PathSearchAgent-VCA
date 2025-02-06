import java.util.List;

/**
 * Main class for testing the PathSearchAgent project.
 * <p>
 * It tests different maze configurations and runs several search algorithms
 * (A*, Greedy Best-First, Dijkstra's, BFS, and DFS) on each maze.
 * The visualization is output to the console using ANSI color codes.
 * </p>
 * 
 * To compile:
 * <pre>
 *   javac Main.java Environment.java Node.java SearchAlgo.java
 * </pre>
 * 
 * To run:
 * <pre>
 *   java Main
 * </pre>
 * 
 * To generate Javadoc documentation:
 * <pre>
 *   javadoc -d doc *.java
 * </pre>
 * and then open {@code doc/index.html} in your browser.
 * 
 * @author mat-tp
 */
public class Main {
    private static final int DELAY_MS = 100; // Delay between steps for visualization

    /**
     * Default constructor for Main.
     */
    public Main() {
        
    }
    
    /**
     * Main entry point.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Test different maze configurations
        testMaze("Simple Maze", createSimpleMaze());
        sleep(2000); // Pause between mazes
        testMaze("Complex Maze", createComplexMaze());
        sleep(2000);
        testMaze("Spiral Maze", createSpiralMaze());
    }

    /**
     * Tests the given maze configuration by running all search algorithms.
     *
     * @param mazeName Name of the maze configuration
     * @param grid     2D integer array representing the maze (0: path, 1: wall)
     */
    private static void testMaze(String mazeName, int[][] grid) {
        System.out.println("\n=== Testing " + mazeName + " ===");
        Environment env = new Environment(grid);
        SearchAlgo searchAlgo = new SearchAlgo();

        // Define start and target nodes
        Node start = new Node(0, 0);
        Node target = new Node(grid[0].length - 1, grid.length - 1);

        // Test all algorithms; reset the nodes between runs
        testAlgorithm("A* Search", env, searchAlgo, start, target,
            (e, s, t) -> searchAlgo.aStarSearch(e, s, t));

        env.resetNodes();
        testAlgorithm("Greedy Best-First Search", env, searchAlgo, start, target,
            (e, s, t) -> searchAlgo.greedyBestFirstSearch(e, s, t));

        env.resetNodes();
        testAlgorithm("Dijkstra's Algorithm", env, searchAlgo, start, target,
            (e, s, t) -> searchAlgo.dijkstra(e, s, t));

        env.resetNodes();
        testAlgorithm("Breadth-First Search", env, searchAlgo, start, target,
            (e, s, t) -> searchAlgo.bfs(e, s, t));

        env.resetNodes();
        testAlgorithm("Depth-First Search", env, searchAlgo, start, target,
            (e, s, t) -> searchAlgo.dfs(e, s, t));
    }

    /**
     * Tests a specific search algorithm on the maze.
     *
     * @param name       The name of the algorithm
     * @param env        The environment (maze)
     * @param searchAlgo The search algorithm implementation
     * @param start      The start node
     * @param target     The target node
     * @param pathFinder A functional interface to run the chosen algorithm
     */
    private static void testAlgorithm(String name, Environment env, SearchAlgo searchAlgo,
                                      Node start, Node target, PathFinder pathFinder) {
        System.out.println("\nTesting " + name);
        long startTime = System.currentTimeMillis();
        java.util.List<Node> path = pathFinder.findPath(env, start, target);
        long endTime = System.currentTimeMillis();

        if (path != null) {
            System.out.println(name + " Results:");
            System.out.println("Path found: " + path);
            System.out.println("Path length: " + (path.size() - 1) + " steps");
            System.out.println("Time taken: " + (endTime - startTime) + "ms");
        } else {
            System.out.println(name + ": No path found");
        }
        sleep(1000); // Pause before next algorithm
    }

    /**
     * Functional interface for search algorithm testing.
     */
    @FunctionalInterface
    interface PathFinder {
        /**
         * Finds a path from the start node to the target node.
         *
         * @param env    The environment (maze)
         * @param start  The start node
         * @param target The target node
         * @return A list of nodes representing the path, or null if no path is found
         */
        java.util.List<Node> findPath(Environment env, Node start, Node target);
    }

    /**
     * Creates a simple maze configuration.
     *
     * @return A 2D array representing the maze
     */
    private static int[][] createSimpleMaze() {
        return new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
    }

    /**
     * Creates a complex maze configuration.
     *
     * @return A 2D array representing the maze
     */
    private static int[][] createComplexMaze() {
        return new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };
    }

    /**
     * Creates a spiral maze configuration.
     *
     * @return A 2D array representing the maze
     */
    private static int[][] createSpiralMaze() {
        return new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };
    }

    /**
     * Causes the program to pause for the specified number of milliseconds.
     *
     * @param milliseconds The time to sleep in milliseconds
     */
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
