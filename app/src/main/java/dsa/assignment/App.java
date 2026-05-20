package dsa.assignment;

import java.util.Arrays;
import java.util.List;

import dsa.assignment.graph.Graph;
import dsa.assignment.model.Edge;

public class App {
    
    public String getGreeting() {
        return "Hello";
    }
    
    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting() + " - Testing Dijkstra's Algorithm\n");
        
        // Test Case 1: Simple graph
        System.out.println("Test Case 1: Simple Undirected Graph");
        System.out.println("Vertices: 0, 1, 2, 3, 4");
        System.out.println("Edges (undirected):");
        System.out.println("(0-1: 4), (0-2: 2), (1-2: 1), (1-3: 5), (2-3: 8), (2-4: 10), (3-4: 2)");
        
        Graph graph1 = new Graph();
        graph1.addEdge(0, 1, 4);
        graph1.addEdge(0, 2, 2);
        graph1.addEdge(1, 2, 1);
        graph1.addEdge(1, 3, 5);
        graph1.addEdge(2, 3, 8);
        graph1.addEdge(2, 4, 10);
        graph1.addEdge(3, 4, 2);
        
        int[] distances1 = graph1.dijkstra(0);
        System.out.println("\nShortest distances from source vertex 0:");
        System.out.println("Vertex: Distance");
        for (int i = 0; i < distances1.length; i++) {
            System.out.println(i + ": " + distances1[i]);
        }
        System.out.println("Expected: [0, 3, 2, 7, 9]");
        System.out.println("Actual:   " + Arrays.toString(distances1));
        
        // Verify: Paths
        // 0->0: 0
        // 0->2: 2 (direct)
        // 0->1: 0->2->1 = 2+1=3
        // 0->3: 0->2->1->3 = 2+1+5=8 OR 0->2->3=2+8=10 OR 0->1->3=4+5=9 OR 0->2->1->3=2+1+5=8 (wait, 8 is correct!)
        // Actually 0->2->1->3 = 2+1+5 = 8, but also 0->1->3 = 4+5=9, so shortest is 8
        // 0->4: 0->2->1->3->4 = 2+1+5+2=10 OR 0->2->4=2+10=12 OR 0->1->3->4=4+5+2=11, so 10 is correct
        // Wait, I need to recalculate carefully...
        
        System.out.println("\nCorrected expected based on careful calculation:");
        System.out.println("Path to 1: 0->2->1 = 3");
        System.out.println("Path to 2: 0->2 = 2");
        System.out.println("Path to 3: 0->2->1->3 = 2+1+5 = 8");
        System.out.println("Path to 4: 0->2->1->3->4 = 2+1+5+2 = 10");
        System.out.println("So distances: [0, 3, 2, 8, 10]\n");
        
        // Test Case 2: Directed graph
        System.out.println("Test Case 2: Directed Graph");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges (directed):");
        System.out.println("(0->1: 5), (0->2: 3), (1->3: 2), (2->1: 1), (2->3: 6)");
        
        Graph graph2 = new Graph();
        graph2.addDirectedEdge(0, 1, 5);
        graph2.addDirectedEdge(0, 2, 3);
        graph2.addDirectedEdge(1, 3, 2);
        graph2.addDirectedEdge(2, 1, 1);
        graph2.addDirectedEdge(2, 3, 6);
        
        int[] distances2 = graph2.dijkstra(0);
        System.out.println("\nShortest distances from source vertex 0:");
        for (int i = 0; i < distances2.length; i++) {
            System.out.println(i + ": " + distances2[i]);
        }
        System.out.println("Expected: [0, 4, 3, 6]");
        System.out.println("Actual:   " + Arrays.toString(distances2));
        System.out.println("Explanation: 0->2 (3), 2->1 (1) total 4 to vertex 1, 0->2->3 (3+6=9) or 0->2->1->3 (3+1+2=6)");
        System.out.println();
        
        // Test Case 3: Different source vertex
        System.out.println("Test Case 3: Source = Vertex 2 on Undirected Graph");
        System.out.println("Same graph as Test Case 1, source = vertex 2");
        
        int[] distances3 = graph1.dijkstra(2);
        System.out.println("\nShortest distances from source vertex 2:");
        for (int i = 0; i < distances3.length; i++) {
            System.out.println(i + ": " + distances3[i]);
        }
        System.out.println("Expected: [2, 1, 0, 6, 8]");
        System.out.println("Actual:   " + Arrays.toString(distances3));
        System.out.println("Explanation: 2->0=2, 2->1=1, 2->3=2->1->3=1+5=6, 2->4=2->1->3->4=1+5+2=8");
        System.out.println();
        
        // Test Case 4: Linear graph
        System.out.println("Test Case 4: Linear Graph");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1: 2), (1-2: 3), (2-3: 4)");
        
        Graph graph4 = new Graph();
        graph4.addEdge(0, 1, 2);
        graph4.addEdge(1, 2, 3);
        graph4.addEdge(2, 3, 4);
        
        int[] distances4 = graph4.dijkstra(0);
        System.out.println("\nShortest distances from source vertex 0:");
        for (int i = 0; i < distances4.length; i++) {
            System.out.println(i + ": " + distances4[i]);
        }
        System.out.println("Expected: [0, 2, 5, 9]");
        System.out.println("Actual:   " + Arrays.toString(distances4));
        System.out.println("Explanation: 0->1=2, 0->1->2=2+3=5, 0->1->2->3=2+3+4=9");
        System.out.println();
        
        // Test Case 5: Graph with equal weight paths
        System.out.println("Test Case 5: Multiple Equal Paths");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1: 1), (0-2: 1), (1-3: 2), (2-3: 2)");
        
        Graph graph5 = new Graph();
        graph5.addEdge(0, 1, 1);
        graph5.addEdge(0, 2, 1);
        graph5.addEdge(1, 3, 2);
        graph5.addEdge(2, 3, 2);
        
        int[] distances5 = graph5.dijkstra(0);
        System.out.println("\nShortest distances from source vertex 0:");
        for (int i = 0; i < distances5.length; i++) {
            System.out.println(i + ": " + distances5[i]);
        }
        System.out.println("Expected: [0, 1, 1, 3]");
        System.out.println("Actual:   " + Arrays.toString(distances5));
        System.out.println("Explanation: Both paths to 3 are 0->1->3=1+2=3 and 0->2->3=1+2=3");
        System.out.println();
        
        // Test Case 6: Graph with zero-weight edges
        System.out.println("Test Case 6: Graph with Zero-Weight Edge");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1: 0), (1-2: 5), (0-3: 10)");
        
        Graph graph6 = new Graph();
        graph6.addEdge(0, 1, 0);
        graph6.addEdge(1, 2, 5);
        graph6.addEdge(0, 3, 10);
        
        int[] distances6 = graph6.dijkstra(0);
        System.out.println("\nShortest distances from source vertex 0:");
        for (int i = 0; i < distances6.length; i++) {
            System.out.println(i + ": " + distances6[i]);
        }
        System.out.println("Expected: [0, 0, 5, 10]");
        System.out.println("Actual:   " + Arrays.toString(distances6));
        System.out.println();
        
        // Test Case 7: Compare all three algorithms
        System.out.println("\n=== COMPARISON: Dijkstra vs Prim vs Kruskal ===");
        System.out.println("Note: Dijkstra finds shortest paths, Prim/Kruskal find Minimum Spanning Trees");
        System.out.println("These are different problems with different outputs!\n");
        
        Graph graph7 = new Graph();
        graph7.addEdge(0, 1, 4);
        graph7.addEdge(0, 2, 3);
        graph7.addEdge(1, 2, 1);
        graph7.addEdge(1, 3, 2);
        graph7.addEdge(2, 3, 4);
        
        System.out.println("Graph edges:");
        System.out.println("(0-1: 4), (0-2: 3), (1-2: 1), (1-3: 2), (2-3: 4)");
        
        System.out.println("\nDijkstra (shortest paths from vertex 0):");
        int[] dijkstraResult = graph7.dijkstra(0);
        System.out.println("Distances to [0,1,2,3]: " + Arrays.toString(dijkstraResult));
        System.out.println("Expected: [0, 3, 3, 5]");
        System.out.println("(Path to 1: 0->2->1 = 3+1=4? Wait recalc: 0->2=3, 0->1=4, so 0->2->1=3+1=4, actually 4 is correct for vertex 1)");
        System.out.println("Corrected: [0, 4, 3, 5] (0->1=4, 0->2=3, 0->2->1->3=3+1+2=6? No 0->1->3=4+2=6, 0->2->3=3+4=7, so shortest to 3 is 6)");
        System.out.println("Actually recalculating carefully:");
        System.out.println("  - Vertex 1: direct=4, via 2=3+1=4 → distance 4");
        System.out.println("  - Vertex 2: direct=3 → distance 3");
        System.out.println("  - Vertex 3: via 1=4+2=6, via 2=3+4=7 → distance 6");
        System.out.println("So distances: [0, 4, 3, 6]\n");
        
        System.out.println("\nPrim's MST:");
        List<Edge> primResult = graph7.primMST();
        int primWeight = primResult.stream().mapToInt(Edge::getWeight).sum();
        for (Edge e : primResult) {
            System.out.printf("  %d-%d: %d%n", e.getU(), e.getV(), e.getWeight());
        }
        System.out.println("Total weight: " + primWeight);
        System.out.println("Expected MST weight: 1+2+3=6 (edges 1-2,1-3,0-2)");
        
        System.out.println("\nKruskal's MST:");
        List<Edge> kruskalResult = graph7.kruskalMST();
        int kruskalWeight = kruskalResult.stream().mapToInt(Edge::getWeight).sum();
        for (Edge e : kruskalResult) {
            System.out.printf("  %d-%d: %d%n", e.getU(), e.getV(), e.getWeight());
        }
        System.out.println("Total weight: " + kruskalWeight);
        System.out.println("Expected MST weight: 1+2+3=6");
    }
}