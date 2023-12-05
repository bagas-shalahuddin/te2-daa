import java.util.Arrays;

public class HamiltonianPathBacktrackingChecker {

    // Number of vertices in the graph.
    private int numOfVertices;
    // An array to store the Hamiltonian path.
    private int[] path;
    private int[][] adjacencyMatrix;

    public HamiltonianPathBacktrackingChecker(int[][] adjacencyMatrix) {
        this.numOfVertices = adjacencyMatrix.length;
        this.adjacencyMatrix = adjacencyMatrix;
        this.path = new int[numOfVertices];
        Arrays.fill(this.path, -1); // Fill path with -1 indicating no vertices have been visited.
    }

    // Checks if a vertex can be added at the given position in the Hamiltonian
    // Path.
    private boolean isSafe(int v, int pos) {
        // There should be an edge from the last added vertex to the current vertex.
        if (adjacencyMatrix[path[pos - 1]][v] == 0) {
            return false;
        }

        // The current vertex should not already be included in the path.
        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }

        return true;
    }

    // Recursive function to find the Hamiltonian Path.
    private boolean hamiltonianPathUtil(int pos) {
        // If all vertices are included in the path, we have found a Hamiltonian Path.
        if (pos == numOfVertices) {
            return true;
        }

        // Try different vertices as the next candidate in the path.
        for (int v = 0; v < numOfVertices; v++) {
            // Check if the vertex can be added to the path.
            if (isSafe(v, pos)) {
                path[pos] = v; // Add the vertex to the path.

                // Continue building the path from the next position.
                if (hamiltonianPathUtil(pos + 1)) {
                    return true;
                }

                // If adding the vertex doesn't lead to a solution, remove it from the path.
                path[pos] = -1;
            }
        }

        // No vertex can be added to the path at this position, return false.
        return false;
    }

    public boolean hasHamiltonianPath() {
        // Try each vertex as a starting point for the Hamiltonian Path.
        for (int startingVertex = 0; startingVertex < numOfVertices; startingVertex++) {
            Arrays.fill(this.path, -1); // Reset the path before trying a new starting vertex.
            path[0] = startingVertex; // Start the path with the current starting vertex.

            // Use the utility function to build the rest of the path.
            if (hamiltonianPathUtil(1)) {
                return true; // Hamiltonian Path found.
            }
        }
        return false; // No Hamiltonian Path found for any starting vertex.
    }
}
