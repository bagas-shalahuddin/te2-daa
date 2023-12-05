import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
  public static void main(String[] args) {
    int[] numVerticesOptions = { 16, 18, 20 };
    String[] methods = { "DP", "Backtracking" };
    String resultsFileName = "experiment-result.txt";
    String result = "";

    for (int numVertices : numVerticesOptions) {
      for (GraphType graphType : GraphType.values()) {
        // Generate graph and write to file 
        int[][] graph = HamiltonianPathGraphGenerator.generateGraph(numVertices, graphType);
        HamiltonianPathGraphGenerator.writeGraphToFile(graph, "dataset/" + "hamiltonian-path-" + numVertices + "-"
            + graphType.toString().toLowerCase() + ".txt");

        for (int experimentRun = 0; experimentRun < 5; experimentRun++) {
          for (String method : methods) {
            result += "\nIteration " + (experimentRun + 1) + " : Testing " + method + " with " + numVertices + " vertices, " + graphType + " graph\n";

            boolean existsPath = false;

            // Start measuring memory usage
            Runtime runtime = Runtime.getRuntime();
            runtime.gc(); // Suggest garbage collection for more accurate memory measurement
            long startMemory = runtime.totalMemory() - runtime.freeMemory();

            // Measure start time
            long startTime = System.nanoTime();

            if (method.equals("DP")) {
              // Check for Hamiltonian Path using Dynamic Programming
              existsPath = HamiltonianPathDPChecker.hasHamiltonianPath(graph, numVertices);
            } else {
              // Check for Hamiltonian Path using Backtracking
              HamiltonianPathBacktrackingChecker backtrackingChecker = new HamiltonianPathBacktrackingChecker(graph);
              existsPath = backtrackingChecker.hasHamiltonianPath();
            }

            // Measure end time
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

            // Measure memory usage after the operation
            long endMemory = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = endMemory - startMemory;

            // Append results to the string
            result += "Hamiltonian Path (" + method + "): " + existsPath + "\n";
            result += "Elapsed time: " + (elapsedTime / 1000000.0) + " ms\n";
            result += "Used memory: " + (memoryUsed / 1024.0) + " kilobytes\n";
            result += "\n==============================================================\n";
          }
        }
      }
    }
    // Print to console and write to file
    System.out.println(result);
    writeResultsToFile(result, resultsFileName);
  }

  private static void writeResultsToFile(String result, String fileName) {
    try (FileWriter fw = new FileWriter(fileName, false); PrintWriter pw = new PrintWriter(fw)) {
      pw.print(result);
    } catch (IOException e) {
      System.err.println("An error occurred while writing to the results file: " + e.getMessage());
    }
  }
}
