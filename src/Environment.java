import java.util.*;
import java.util.Arrays;

/**
 * Represents the environment or maze grid where the pathfinding takes place.
 * <p>
 * The Environment class handles grid management and visualization using ANSI color codes.
 * </p>
 * 
 * @author mat-tp
 */
public class Environment {
    private int width, height;
    private int[][] grid;
    private Node[][] nodeGrid;
    private boolean animationEnabled = true;
    private int animationDelay = 100; // milliseconds

    // ANSI color codes for visualization
    private static final String RESET = "\u001B[0m";
    private static final String BLACK_BACKGROUND = "\u001B[40m";
    private static final String RED_BACKGROUND = "\u001B[41m";
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private static final String YELLOW_BACKGROUND = "\u001B[43m";
    private static final String BLUE_BACKGROUND = "\u001B[44m";
    private static final String PURPLE_BACKGROUND = "\u001B[45m";
    private static final String WHITE_TEXT = "\u001B[37m";
    private static final String BOLD = "\u001B[1m";

    /**
     * Constructs an Environment with the given grid.
     *
     * @param grid 2D array representing the maze (0 for path, 1 for wall)
     * @throws IllegalArgumentException if the grid is null, empty, or not rectangular
     */
    public Environment(int[][] grid) {
        validateGrid(grid);
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
        initializeNodeGrid();
    }

    /**
     * Validates the input grid for consistency.
     *
     * @param grid The input grid to validate
     * @throws IllegalArgumentException if the grid is null, empty, or not rectangular,
     *                                  or if it contains values other than 0 or 1
     */
    private void validateGrid(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        int width = grid[0].length;
        for (int[] row : grid) {
            if (row.length != width) {
                throw new IllegalArgumentException("Grid must be rectangular");
            }
            for (int cell : row) {
                if (cell != 0 && cell != 1) {
                    throw new IllegalArgumentException("Grid cells must be 0 or 1");
                }
            }
        }
    }

    /**
     * Initializes the node grid used for pathfinding.
     */
    private void initializeNodeGrid() {
        nodeGrid = new Node[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodeGrid[y][x] = new Node(x, y);
            }
        }
    }

    /**
     * Checks if the specified position is within bounds and walkable.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if the position is within the grid and not a wall; false otherwise
     */
    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && grid[y][x] == 0;
    }

    /**
     * Retrieves valid neighbor nodes for a given node.
     *
     * @param node The node for which to get neighbors
     * @return List of valid neighboring nodes
     */
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        // Directions: right, down, left, up
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];

            if (isValid(nx, ny)) {
                Node neighbor = nodeGrid[ny][nx];
                neighbor.g = Integer.MAX_VALUE;
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Convenience method to print the grid using only the current node.
     *
     * @param current The current node being explored
     */
    public void printGrid(Node current) {
        printGrid(current, null, null, null);
    }

    /**
     * Prints the current state of the grid with visualization.
     *
     * @param current   Current node being explored
     * @param openSet   Set of nodes in the open set (optional)
     * @param closedSet Set of nodes in the closed set (optional)
     * @param path      The current path (optional)
     */
    public void printGrid(Node current, Set<Node> openSet, Set<Node> closedSet, java.util.List<Node> path) {
        if (!animationEnabled) return;

        clearScreen();
        printHeader();

        // Print column numbers
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            System.out.printf("%2d ", x);
        }
        System.out.println();

        // Print top border
        System.out.print("   ╔");
        System.out.print("═".repeat(width * 3));
        System.out.println("╗");

        // Print grid with row numbers
        for (int y = 0; y < height; y++) {
            System.out.printf("%2d ║", y);
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == 1) {
                    // Wall cell
                    System.out.print(BLACK_BACKGROUND + WHITE_TEXT + " ■ " + RESET);
                } else {
                    Node node = nodeGrid[y][x];
                    if (path != null && path.contains(node)) {
                        System.out.print(GREEN_BACKGROUND + WHITE_TEXT + " P " + RESET);
                    } else if (current != null && current.equals(node)) {
                        System.out.print(PURPLE_BACKGROUND + WHITE_TEXT + " C " + RESET);
                    } else if (openSet != null && openSet.contains(node)) {
                        System.out.print(YELLOW_BACKGROUND + WHITE_TEXT + " O " + RESET);
                    } else if (closedSet != null && closedSet.contains(node)) {
                        System.out.print(BLUE_BACKGROUND + WHITE_TEXT + " X " + RESET);
                    } else {
                        System.out.print(" · ");
                    }
                }
            }
            System.out.println("║");
        }

        // Print bottom border
        System.out.print("   ╚");
        System.out.print("═".repeat(width * 3));
        System.out.println("╝");

        printLegend();
        printStatistics(current, openSet, closedSet);

        // Animation delay
        sleep(animationDelay);
    }

    /**
     * Prints the header information.
     */
    private void printHeader() {
        System.out.println(BOLD + "\n=== Pathfinding Visualization ===" + RESET);
        System.out.println("Grid Size: " + width + "x" + height);
    }

    /**
     * Prints the legend for the visualization.
     */
    private void printLegend() {
        System.out.println("\nLegend:");
        System.out.println(BLACK_BACKGROUND + WHITE_TEXT + " ■ " + RESET + " Wall");
        System.out.println(PURPLE_BACKGROUND + WHITE_TEXT + " C " + RESET + " Current Node");
        System.out.println(YELLOW_BACKGROUND + WHITE_TEXT + " O " + RESET + " Open Set");
        System.out.println(BLUE_BACKGROUND + WHITE_TEXT + " X " + RESET + " Closed Set");
        System.out.println(GREEN_BACKGROUND + WHITE_TEXT + " P " + RESET + " Path");
        System.out.println(" · " + " Unvisited");
    }

    /**
     * Prints current statistics such as the current node, open set size, and closed set size.
     *
     * @param current   The current node being explored
     * @param openSet   The set of nodes in the open set (optional)
     * @param closedSet The set of nodes in the closed set (optional)
     */
    private void printStatistics(Node current, Set<Node> openSet, Set<Node> closedSet) {
        if (current != null) {
            System.out.println("\nCurrent Node: " + current);
            System.out.println("Open Set Size: " + (openSet != null ? openSet.size() : 0));
            System.out.println("Closed Set Size: " + (closedSet != null ? closedSet.size() : 0));
        }
    }

    /**
     * Clears the console screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Introduces a delay for animation.
     *
     * @param milliseconds The delay in milliseconds
     */
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Enables or disables animation.
     *
     * @param enabled true to enable animation, false to disable
     */
    public void setAnimationEnabled(boolean enabled) {
        this.animationEnabled = enabled;
    }

    /**
     * Sets the animation delay.
     *
     * @param milliseconds Delay in milliseconds
     */
    public void setAnimationDelay(int milliseconds) {
        this.animationDelay = milliseconds;
    }

    /**
     * Gets the width of the grid.
     *
     * @return The width of the grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the grid.
     *
     * @return The height of the grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns a copy of the grid.
     *
     * @return A 2D array copy of the grid
     */
    public int[][] getGrid() {
        return Arrays.stream(grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    /**
     * Resets all nodes in the grid.
     */
    public void resetNodes() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodeGrid[y][x] = new Node(x, y);
            }
        }
    }

    /**
     * Returns a string representation of the grid.
     *
     * @return A string representing the grid
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grid ").append(width).append("x").append(height).append(":\n");
        for (int[] row : grid) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
}
