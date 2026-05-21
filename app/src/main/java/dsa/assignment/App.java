package dsa.assignment;

import dsa.assignment.graph.Graph;
import dsa.assignment.service.GraphGenerator;

public class App {
    
    public String getGreeting() {
        return "Hello";
    }
    
    public static void main(String[] args) {

        GraphGenerator generator = new GraphGenerator();

        Graph graph = generator.generateCompleteGraph(1000);

        graph.primMST();
    }
}