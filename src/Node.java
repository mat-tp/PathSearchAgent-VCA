import java.util.Objects;

/**
 * Represents a node (cell) in the maze grid.
 * <p>
 * A Node stores its coordinates, cost from the start (g), heuristic estimate (h),
 * total cost (f = g + h), a reference to its parent for path reconstruction,
 * and its visualization state.
 * </p>
 * 
 * @see Environment
 * @see SearchAlgo
 * @author mat-tp
 */
public class Node {
    /** X coordinate in the grid */
    int x, y;
    /** Cost from start node (g) */
    int g = Integer.MAX_VALUE;
    /** Heuristic estimate to target (h) */
    int h = 0;
    /** Total cost (f = g + h) */
    int f = 0;
    /** Parent node for path reconstruction */
    Node parent;
    /** Node state for visualization */
    NodeState state = NodeState.UNVISITED;

    /**
     * Enumeration for node visualization states.
     */
    public enum NodeState {
        /** Node hasn't been explored */
        UNVISITED,
        /** Node is in open set (frontier) */
        OPEN,
        /** Node has been fully explored */
        CLOSED,
        /** Node is part of the final path */
        PATH
    }

    /**
     * Constructs a node with the specified coordinates.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the Manhattan distance heuristic from this node to the target node.
     *
     * @param target The target node
     * @return The Manhattan distance
     */
    public int calculateHeuristic(Node target) {
        return Math.abs(this.x - target.x) + Math.abs(this.y - target.y);
    }

    /**
     * Calculates the Euclidean distance heuristic from this node to the target node.
     *
     * @param target The target node
     * @return The Euclidean distance (rounded to an integer)
     */
    public int calculateEuclideanHeuristic(Node target) {
        double dx = this.x - target.x;
        double dy = this.y - target.y;
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Checks equality based on the node's coordinates.
     *
     * @param obj The object to compare
     * @return true if the coordinates are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    /**
     * Computes the hash code based on the node's coordinates.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the node in the format (x,y).
     *
     * @return A string representing the node
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
