package dsa.assignment.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.util.CombinatoricsUtils;

import dsa.assignment.graph.Graph;

public class GraphGenerator {


    public Graph generateSparseGraph(int vertices) {

        Graph graph = new Graph();
        Random random = new Random(7293847);
        Set<String> used = new HashSet<>();

        for (int i = 1; i < vertices; i++) {

            int parent = random.nextInt(i);

            int weight = random.nextInt(1000) + 1;

            graph.addEdge(parent, i, weight);


            used.add(parent+"-"+i);
        }


        int currentEdges = vertices - 1;
        int target = vertices * 5;

        while (currentEdges < target) {
            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);

            if (u == v) continue;

            int min = Math.min(u, v);
            int max = Math.max(u, v);

            String edge = min + "-" + max;
            if (used.contains(edge)) continue;

            used.add(edge);

            int weight = random.nextInt(1000) + 1;

            graph.addEdge(u, v, weight);

            currentEdges++;
        }

        return graph;
    }

    public Graph generateDenseGraph(int vertices) {

        Random rand = new Random(3712948);
        Graph graph = new Graph();
        Set<String> used = new HashSet<>();

        for (int i = 1; i < vertices; i++) {
            int parent = rand.nextInt(i);

            int weight = rand.nextInt(1000) + 1;

            graph.addEdge(parent, i, weight);

            used.add(parent+"-"+i);
        }

        int currentEdges = vertices - 1;
        long target = CombinatoricsUtils.binomialCoefficient(vertices, 2) / 4;

        while (currentEdges < target) {

            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);

            if (u == v) continue;

            int min = Math.min(u, v);
            int max = Math.max(u, v);

            String edge = min + "-" + max;
            if (used.contains(edge)) continue;

            used.add(edge);

            int weight = rand.nextInt(1000) + 1;
            graph.addEdge(u, v, weight);

            currentEdges++;
        }

        return graph;
    }

    public Graph generateCompleteGraph(int vertices) {

        Random rand = new Random(734829);
        Graph graph = new Graph();

        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices ; j++) {
                int weight = rand.nextInt(1000) + 1;
                graph.addEdge(i, j, weight);
            }
        }

        return graph;
    }

    public Graph generateDAG(int vertices) {

        Random rand = new Random(217849);
        Graph graph = new Graph();
        Set<String> used = new HashSet<>();

        for (int i = 1; i < vertices; i++) {
            int parent = rand.nextInt(i);
            int weight = rand.nextInt(1000) + 1;

            graph.addDirectedEdge(parent, i, weight);
            String edge = parent + "-" + i;
            used.add(edge);
        }

        int currentEdges = vertices - 1;
        int target = vertices * 5;

        while (currentEdges < target) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices - u) + u;

            if (u == v) continue;


            String edge = u + "-" + v;
            if (used.contains(edge)) continue;

            int weight = rand.nextInt(1000) + 1;
            graph.addDirectedEdge(u, v, weight);

            currentEdges++;
        }

        return graph;
    }
}
