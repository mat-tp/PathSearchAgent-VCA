# PathSearchAgent VCA

## 🌟 Overview

The **PathSearchAgent VCA** is a Java-based intelligent agent designed to navigate an environment and find optimal paths between points. By leveraging popular **Artificial Intelligence search algorithms**, this project demonstrates automated pathfinding in various environments. The agent can efficiently find paths while considering obstacles, terrain types, and various maze configurations.

## 🛠️ Features

- **Environment Simulation**: A 2D grid environment with different terrains and obstacles.
- **Pathfinding Agent**: An intelligent agent that uses AI to find optimal paths.
- **Implemented Search Algorithms**:
  - A* Search
  - Breadth-First Search (BFS)
  - Depth-First Search (DFS)
  - Dijkstra's Algorithm
  - Greedy Best-First Search
- **Visualization**: Visual representation of the search process and the resulting path.
- **Maze Configurations**: Supports simple, complex, and spiral mazes.
- **Extensibility**: Easily extendable for the addition of more AI techniques.

## 💻 Technologies Used

- **Java** (JDK 21 or later recommended)
- **Javadoc** for generating project documentation

## 📁 Project Structure

PathSearchAgent-VCA/ ├── src/ # Source code directory │ 
                       ├── Environment.java # Defines the environment and its visualization │ 
                       ├── Main.java # Entry point and maze setup │ 
                       ├── Node.java # Represents nodes in the search algorithms │ 
                       ├── SearchAlgo.java # Implements AI search algorithms 
                     ├── doc/ # Javadoc-generated documentation  
                     ├── README.md 


## ⚙️ Installation & Setup

1. **Ensure you have Java installed:**

   Check your Java version by running the following command:

   ```bash
   java -version

If Java is not installed, download and install Java 21 (or later) from either Oracle JDK or use OpenJDK.

    Clone the repository:

git clone https://github.com/your-username/PathSearchAgent-VCA.git  # Replace with your repository URL
cd PathSearchAgent-VCA/src

Compile the Project:

Navigate to the src/ directory and compile the Java files:

javac *.java

Run the Program:

Start the program to run the pathfinding algorithms:

    java Main

    The program will execute the selected algorithms on the maze configurations and display the results in the console. The visualization will also appear if enabled.


The Main.java file controls the setup of different maze configurations and runs the various search algorithms on them. You can modify Main.java to:

    Customize the Start/Goal Nodes: Change the starting and goal positions for pathfinding.
    Create Custom Mazes: Design your own maze layouts.
    Toggle Visualization: Enable or disable the visualization of the search process.
    Adjust Animation Speed: Fine-tune the speed of the visualized search.

🚧 Future Enhancements

    Additional Search Algorithms: Implement more advanced algorithms like Jump Point Search or Theta*.
    Improved Visualization: Upgrade the graphical user interface (GUI) using JavaFX for a better user experience.
    Performance Metrics: Introduce algorithm performance evaluation, such as path length, execution time, and memory usage.
    Map Representations: Expand the project to support different map types, such as graph-based representations.
    Path Smoothing: Apply path smoothing techniques to improve the quality of the final path.

 Author

Mat-Tp
