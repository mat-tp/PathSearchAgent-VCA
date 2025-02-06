import java.util.*;

/**
 * Implements various search algorithms for pathfinding.
 * <p>
 * This class includes implementations for:
 * <ul>
 *   <li>A* Search</li>
 *   <li>Breadth-First Search (BFS)</li>
 *   <li>Depth-First Search (DFS)</li>
 *   <li>Dijkstra's Algorithm (Uniform Cost Search)</li>
 *   <li>Greedy Best-First Search</li>
 * </ul>
 * Each method returns a list of nodes representing the path from the start to the target.
 * 
 * @see Environment
 * @see Node
 * @author mat-tp
 */
public class SearchAlgo {
	
	/**
	 * Default constructor for SearchAlgo.
	 */
	public SearchAlgo() {
	  
	}

    /**
     * Executes the A* search algorithm.
     *
     * @param env    The environment (maze)
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path is found
     */
    public List<Node> aStarSearch(Environment env, Node start, Node target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        Set<Node> closedSet = new HashSet<>();

        start.g = 0;
        start.h = start.calculateHeuristic(target);
        start.f = start.g + start.h;
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            System.out.println("\nExploring node " + current + " with f=" + current.f + ", g=" + current.g + ", h=" + current.h);
            env.printGrid(current); // Visualization

            if (current.equals(target)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : env.getNeighbors(current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + 1;

                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.parent = current;
                    neighbor.g = tentativeG;
                    neighbor.h = neighbor.calculateHeuristic(target);
                    neighbor.f = neighbor.g + neighbor.h;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    } else {
                        openSet.remove(neighbor);
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Executes the Breadth-First Search algorithm.
     *
     * @param env    The environment (maze)
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path is found
     */
    public List<Node> bfs(Environment env, Node start, Node target) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            System.out.println("\nExploring node " + current);
            env.printGrid(current);

            if (current.equals(target)) {
                return reconstructPath(current);
            }

            for (Node neighbor : env.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    neighbor.parent = current;
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Executes the Depth-First Search algorithm.
     *
     * @param env    The environment (maze)
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path is found
     */
    public List<Node> dfs(Environment env, Node start, Node target) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            System.out.println("\nExploring node " + current);
            env.printGrid(current);

            if (current.equals(target)) {
                return reconstructPath(current);
            }

            for (Node neighbor : env.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    neighbor.parent = current;
                    stack.push(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Executes Dijkstra's algorithm (uniform cost search).
     *
     * @param env    The environment (maze)
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path is found
     */
    public List<Node> dijkstra(Environment env, Node start, Node target) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.g));
        Set<Node> visited = new HashSet<>();

        start.g = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            System.out.println("\nExploring node " + current + " with cost=" + current.g);
            env.printGrid(current);

            if (current.equals(target)) {
                return reconstructPath(current);
            }

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            for (Node neighbor : env.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    int newCost = current.g + 1;
                    if (newCost < neighbor.g) {
                        neighbor.g = newCost;
                        neighbor.parent = current;
                        queue.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Executes the Greedy Best-First Search algorithm.
     *
     * @param env    The environment (maze)
     * @param start  The starting node
     * @param target The target node
     * @return A list of nodes representing the path, or null if no path is found
     */
    public List<Node> greedyBestFirstSearch(Environment env, Node start, Node target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.h));
        Set<Node> openSetTracker = new HashSet<>();
        Set<Node> closedSet = new HashSet<>();

        start.h = start.calculateHeuristic(target);
        openSet.add(start);
        openSetTracker.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            openSetTracker.remove(current);

            // Visualize current state
            env.printGrid(current, openSetTracker, closedSet, null);
            System.out.println("\nExploring node " + current + " with h=" + current.h);

            // Delay for visualization
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (current.equals(target)) {
                java.util.List<Node> path = reconstructPath(current);
                env.printGrid(current, openSetTracker, closedSet, path);
                return path;
            }

            closedSet.add(current);

            for (Node neighbor : env.getNeighbors(current)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                if (!openSetTracker.contains(neighbor)) {
                    neighbor.parent = current;
                    neighbor.h = neighbor.calculateHeuristic(target);
                    openSet.add(neighbor);
                    openSetTracker.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Reconstructs the path from the target node back to the start node.
     *
     * @param current The target node
     * @return A list of nodes representing the path from start to target
     */
    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
