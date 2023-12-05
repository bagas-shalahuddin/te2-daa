import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class HamiltonianPathGraphGenerator {

    public static int[][] generateGraph(int numVertices, GraphType graphType) {
        int[][] graph = new int[numVertices][numVertices];

        switch (graphType) {
            case COMPLETE:
                generateCompleteGraph(graph);
                break;
            case DISCONNECTED:
                generateDisconnectedGraph(graph);
                break;
            case RANDOM:
                generateRandomGraph(graph);
                break;
            default:
                break;
        }

        return graph;
    }

    private static void generateCompleteGraph(int[][] graph) {
        for (int i = 0; i < graph.length - 1; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                addEdge(graph, i, j);
            }
        }
    }

    private static void generateDisconnectedGraph(int[][] graph) {
        int mid = graph.length / 2;
        for (int i = 0; i < mid; i++) {
            for (int j = i + 1; j < mid; j++) {
                addEdge(graph, i, j);
            }
        }
        for (int i = mid; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                addEdge(graph, i, j);
            }
        }
    }

    private static void generateRandomGraph(int[][] graph) {
        Random random = new Random(42);
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (random.nextBoolean()) {
                    addEdge(graph, i, j);
                }
            }
        }
    }

    private static void addEdge(int[][] graph, int vertex1, int vertex2) {
        graph[vertex1][vertex2] = 1;
        graph[vertex2][vertex1] = 1;
    }

    public static void writeGraphToFile(int[][] graph, String filePath) {
        try (PrintWriter pw = new PrintWriter(filePath)) {
            for (int[] row : graph) {
                for (int cell : row) {
                    pw.print(cell + " ");
                }
                pw.println();
            }
            System.out.println("Graph written to file: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
