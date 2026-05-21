package dsa.assignment.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import dsa.assignment.model.Edge;
import dsa.assignment.model.Node;

public class Graph {
    private Set<Integer> vertices;
    private Map<Integer, List<Edge>> adj;
    private Set<Edge> edges;

    public Graph() {
        this.vertices = new HashSet<>();
        this.adj = new HashMap<>();
        this.edges = new HashSet<>();
    }

    public void addEdge(int u, int v, int weight) {
        if(!vertices.contains(u)) vertices.add(u);
        if(!vertices.contains(v)) vertices.add(v);

        adj.computeIfAbsent(u, k -> new ArrayList<>());
        adj.computeIfAbsent(v, k -> new ArrayList<>());

        adj.get(u).add(new Edge(u, v, weight));
        adj.get(v).add(new Edge(v, u, weight));

        edges.add(new Edge(u, v, weight));
        edges.add(new Edge(v, u, weight));
    }

    public void addDirectedEdge(int u, int v, int weight) {
        if(!vertices.contains(u)) vertices.add(u);
        if(!vertices.contains(v)) vertices.add(v);

        adj.computeIfAbsent(u, k -> new ArrayList<>());
        adj.computeIfAbsent(v, k -> new ArrayList<>());

        adj.get(u).add(new Edge(u, v, weight));

        edges.add(new Edge(u, v, weight));
    }


    public List<Edge> primMST() {

        if (vertices.size() <= 1) return new ArrayList<>();

        // Initialize keys map, initialize Q heap
        Map<Integer, Integer> keys = initKeys();
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        pq.add(new Node(0, 0));
        Map<Integer, Integer> parent = getParentList();
        Set<Integer> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            visited.add(u.getVertex());

            // skip duplicates
            if (u.getWeight() > keys.get(u.getVertex())) continue;

            for (Edge e : adj.get(u.getVertex())) {
                Integer w = e.getWeight();
                if (!visited.contains(e.getV()) && keys.get(e.getV()) > w) {
                    // update pq
                    pq.add(new Node(e.getV(), w));
                    // update keys map
                    keys.put(e.getV(), w);
                    // update parent map
                    parent.put(e.getV(), u.getVertex());
                }
            }
        }

        List<Edge> result = new ArrayList<>();
        for (Integer v: vertices) {
            if (parent.get(v) != null) {
                result.add(getEdge(parent.get(v), v));
            }
        }

        return result;
    }

    public List<Edge> kruskalMST() {

        if (vertices.size() <= 1) return new ArrayList<>();

        PriorityQueue<Edge> pq = getKruskalEdges();
        // create disjoint sets
        List<Set<Integer>> sets = getDisjointSets();
        List<Edge> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Set<Integer> setU = findSet(edge.getU(), sets);
            Set<Integer> setV = findSet(edge.getV(), sets);
            if (!setU.equals(setV)) {
                // should do a union, (create a new larger set and delete the old ones)
                union(sets, edge.getU(), edge.getV());
                result.add(edge);
            }
        }

        return result;
    }

    public int[] dijkstra(int source) {

        if (vertices.size() <= 1) return new int[]{0};

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        pq.add(new Node(source, 0));
        Map<Integer, Integer> result = new HashMap<>();
        
        //populate result
        for (Integer v : vertices) {
            result.put(v, Integer.MAX_VALUE);
        }
        result.put(source, 0);

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            // skip duplicates
            if (u.getWeight() > result.get(u.getVertex())) continue;

            for (Edge e : adj.get(u.getVertex())) {
                Integer w = e.getWeight();
                Integer newDist = result.get(e.getU()) + w;

                if (newDist < result.get(e.getV())) {
                    // update priority queue
                    pq.add(new Node(e.getV(), newDist));
                    // update result
                    result.put(e.getV(), newDist);
                }
            }
        }

        return result.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] dagShortestPath(int source) {
        if (vertices.size() <= 1) return new int[]{0};

        Stack<Integer> stack = topologicalSort(source);
        int[] result = new int[vertices.size()];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[source] = 0;

        while(!stack.empty()) {
            int u = stack.pop();

            if (result[u] == Integer.MAX_VALUE) continue;

            for (Edge e : adj.get(u)) {
                Integer w = e.getWeight();

                if (result[e.getV()] > w + result[u]) {
                    result[e.getV()] = w + result[u];
                }
            }
        }


        return result;
    }

    private Stack<Integer> topologicalSort(int source) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> state = new ArrayList<>(Collections.nCopies(vertices.size(), 0));
    
        for (Integer v : vertices) {
            if (state.get(v) == 0) {
                dfs(v, state, stack);
            }
        }

        return stack;
    }

    private void dfs(int u, List<Integer> state, Stack<Integer> stack) {
        state.set(u, 1);
        for (Edge e : adj.get(u)) {
            if (state.get(e.getV()) == 1){System.out.println("ERRORRR");}; // TODO: throw error
            if (state.get(e.getV()) == 0) {
                dfs(e.getV(), state, stack);
            }
        }
        state.set(u, 2);
        stack.push(u);
    }

    public PriorityQueue<Node> getPriorityQueueDijkstra(int source) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));

        for (Integer v : vertices) {
            if (v == source) continue;
            pq.add(new Node(v, Integer.MAX_VALUE));
        }
        pq.add(new Node(source, 0));

        return pq;
    }

    private void union(List<Set<Integer>> sets, int u, int v) {
        Set<Integer> setU = findSet(u, sets);
        Set<Integer> setV = findSet(v, sets);
        sets.remove(setV);
        setU.addAll(setV);
    }

    private Set<Integer> findSet(int u, List<Set<Integer>> sets) {
        return sets.stream()
        .filter(s -> s.contains(u))
        .findFirst().get();
    }

    private List<Set<Integer>> getDisjointSets() {
        List<Set<Integer>> sets = new ArrayList<Set<Integer>>();

        for (int v : vertices) {
            sets.add(new HashSet<Integer>(Set.of(v)));
        }

        return sets;
    }

    private PriorityQueue<Edge> getKruskalEdges() {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        for (Edge edge : edges) {
            pq.add(edge);
        }
        return pq;
    }

    private Edge getEdge(int u, int v) {
        for (Edge edge : edges) {
            if (edge.getU() == u && edge.getV() == v) {
                return edge;
            }
        }
        return null;
    }

    private Map<Integer, Integer> getParentList() {
        Map<Integer, Integer> parent = new HashMap<>();
        for (Integer v : vertices) {
            parent.put(v, null);
        }
        return parent;
    }

    private Map<Integer, Integer> initKeys() {
        Map<Integer, Integer> keys = new HashMap<>();
        for (int v : vertices) {
            keys.put(v, Integer.MAX_VALUE);
        }
        keys.put(0, 0);
        return keys;
    }
}
