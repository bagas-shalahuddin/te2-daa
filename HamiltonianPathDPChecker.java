// This code is contributed by Kingash taken from geeksforgeeks.org

public class HamiltonianPathDPChecker {

  // Method to verify the presence of a Hamiltonian Path in a graph
  static boolean hasHamiltonianPath(int adjacencyMatrix[][], int vertices) {
    boolean subsetDp[][] = new boolean[vertices][(1 << vertices)];

    // Initialize the dp array for single vertex subsets
    for (int v = 0; v < vertices; v++)
      subsetDp[v][(1 << v)] = true;

    // Iterate through all subsets of vertices
    for (int subset = 0; subset < (1 << vertices); subset++) {
      for (int current = 0; current < vertices; current++) {

        // Check if the current vertex is part of the subset
        if ((subset & (1 << current)) != 0) {

          // Check adjacent vertices
          for (int adjacent = 0; adjacent < vertices; adjacent++) {

            // Conditions: adjacent in subset, edge exists, and dp is true for subset
            // excluding current
            if ((subset & (1 << adjacent)) != 0 && adjacencyMatrix[adjacent][current] == 1
                && current != adjacent && subsetDp[adjacent][subset ^ (1 << current)]) {

              // Update the dp array for the current vertex
              subsetDp[current][subset] = true;
              break;
            }
          }
        }
      }
    }

    // Check final conditions for Hamiltonian Path
    for (int v = 0; v < vertices; v++) {
      if (subsetDp[v][(1 << vertices) - 1])
        return true;
    }

    return false;
  }
}