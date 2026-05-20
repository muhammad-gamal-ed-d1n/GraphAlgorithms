package dsa.assignment;

import java.util.List;

import dsa.assignment.graph.Graph;
import dsa.assignment.model.Edge;

public class App {
    public static void main(String[] args) {
        
        // Test Case 1: Simple triangle graph
        System.out.println("=== Test Case 1: Triangle Graph ===");
        System.out.println("Vertices: 0, 1, 2");
        System.out.println("Edges: (0-1: 4), (0-2: 3), (1-2: 2)");
        
        Graph graph1 = new Graph();
        graph1.addEdge(0, 1, 4);
        graph1.addEdge(0, 2, 3);
        graph1.addEdge(1, 2, 2);
        
        List<Edge> kruskal1 = graph1.kruskalMST();
        
        System.out.println("Kruskal's MST Result:");
        int totalWeight = 0;
        for (Edge edge : kruskal1) {
            System.out.printf("Edge: %d - %d, Weight: %d%n", 
                            edge.getU(), edge.getV(), edge.getWeight());
            totalWeight += edge.getWeight();
        }
        System.out.println("Total MST Weight: " + totalWeight);
        System.out.println("Expected MST: (1-2: 2) and (0-2: 3)");
        System.out.println("Expected total weight: 5");
        System.out.println();
        
        // Test Case 2: Complex graph
        System.out.println("=== Test Case 2: Complex Graph ===");
        System.out.println("Vertices: 0, 1, 2, 3, 4");
        System.out.println("Edges:");
        System.out.println("(0-1: 2), (0-3: 6), (1-2: 3), (1-3: 8), (1-4: 5), (2-4: 7), (3-4: 9)");
        
        Graph graph2 = new Graph();
        graph2.addEdge(0, 1, 2);
        graph2.addEdge(0, 3, 6);
        graph2.addEdge(1, 2, 3);
        graph2.addEdge(1, 3, 8);
        graph2.addEdge(1, 4, 5);
        graph2.addEdge(2, 4, 7);
        graph2.addEdge(3, 4, 9);
        
        List<Edge> kruskal2 = graph2.kruskalMST();
        
        System.out.println("Kruskal's MST Result:");
        totalWeight = 0;
        for (Edge edge : kruskal2) {
            System.out.printf("Edge: %d - %d, Weight: %d%n", 
                            edge.getU(), edge.getV(), edge.getWeight());
            totalWeight += edge.getWeight();
        }
        System.out.println("Total MST Weight: " + totalWeight);
        System.out.println("Expected MST: (0-1: 2), (1-2: 3), (1-4: 5), (0-3: 6)");
        System.out.println("Expected total weight: 16");
        System.out.println();
        
        // Test Case 3: Linear graph
        System.out.println("=== Test Case 3: Linear Graph ===");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1: 1), (1-2: 2), (2-3: 3)");
        
        Graph graph3 = new Graph();
        graph3.addEdge(0, 1, 1);
        graph3.addEdge(1, 2, 2);
        graph3.addEdge(2, 3, 3);
        
        List<Edge> kruskal3 = graph3.kruskalMST();
        
        System.out.println("Kruskal's MST Result:");
        totalWeight = 0;
        for (Edge edge : kruskal3) {
            System.out.printf("Edge: %d - %d, Weight: %d%n", 
                            edge.getU(), edge.getV(), edge.getWeight());
            totalWeight += edge.getWeight();
        }
        System.out.println("Total MST Weight: " + totalWeight);
        System.out.println("Expected MST: All edges (0-1: 1), (1-2: 2), (2-3: 3)");
        System.out.println("Expected total weight: 6");
        System.out.println();
        
        // Test Case 4: Graph with equal weights
        System.out.println("=== Test Case 4: Graph with Equal Weights ===");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1: 5), (0-2: 5), (1-3: 5), (2-3: 5)");
        
        Graph graph4 = new Graph();
        graph4.addEdge(0, 1, 5);
        graph4.addEdge(0, 2, 5);
        graph4.addEdge(1, 3, 5);
        graph4.addEdge(2, 3, 5);
        
        List<Edge> kruskal4 = graph4.kruskalMST();
        
        System.out.println("Kruskal's MST Result:");
        totalWeight = 0;
        for (Edge edge : kruskal4) {
            System.out.printf("Edge: %d - %d, Weight: %d%n", 
                            edge.getU(), edge.getV(), edge.getWeight());
            totalWeight += edge.getWeight();
        }
        System.out.println("Total MST Weight: " + totalWeight);
        System.out.println("Expected MST: Any 3 edges connecting all vertices");
        System.out.println("Expected total weight: 15");
        System.out.println();
        
        // Test Case 5: Compare Prim vs Kruskal on same graph
        System.out.println("=== Test Case 5: Prim vs Kruskal Comparison ===");
        Graph graph5 = new Graph();
        graph5.addEdge(0, 1, 4);
        graph5.addEdge(0, 7, 8);
        graph5.addEdge(1, 2, 8);
        graph5.addEdge(1, 7, 11);
        graph5.addEdge(2, 3, 7);
        graph5.addEdge(2, 8, 2);
        graph5.addEdge(2, 5, 4);
        graph5.addEdge(3, 4, 9);
        graph5.addEdge(3, 5, 14);
        graph5.addEdge(4, 5, 10);
        graph5.addEdge(5, 6, 2);
        graph5.addEdge(6, 7, 1);
        graph5.addEdge(6, 8, 6);
        graph5.addEdge(7, 8, 7);
        
        List<Edge> primResult = graph5.primMST();
        List<Edge> kruskalResult = graph5.kruskalMST();
        
        System.out.println("Prim's MST Total Weight: " + 
            primResult.stream().mapToInt(Edge::getWeight).sum());
        System.out.println("Kruskal's MST Total Weight: " + 
            kruskalResult.stream().mapToInt(Edge::getWeight).sum());
        System.out.println("Both should be equal (37 for this graph)");
        
        // Verify both produce same total weight
        int primTotal = primResult.stream().mapToInt(Edge::getWeight).sum();
        int kruskalTotal = kruskalResult.stream().mapToInt(Edge::getWeight).sum();
        if (primTotal == kruskalTotal) {
            System.out.println("✓ Both algorithms produce the same total weight!");
        } else {
            System.out.println("✗ Algorithms produced different total weights!");
        }
    }

    public String getGreeting() {
        return "Hello";
    }
}