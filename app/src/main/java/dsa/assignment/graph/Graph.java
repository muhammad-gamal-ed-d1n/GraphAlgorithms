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
    private Set<Edge> edges;
    private Set<Integer> vertices;

    public Graph() {
        this.edges = new HashSet<>();
        this.vertices = new HashSet<>();
    }

    public void addEdge(int u, int v, int weight) {
        Edge edge1 = new Edge(u, v, weight);
        Edge edge2 = new Edge(v, u, weight);
        edges.add(edge1);
        edges.add(edge2);
        vertices.add(u);
        vertices.add(v);
    }

    public void addDirectedEdge(int u, int v, int weight) {
        Edge edge = new Edge(u, v, weight);
        edges.add(edge);
        vertices.add(u);
        vertices.add(v);
    }

    public List<Edge> primMST() {
        // Initialize keys map, initialize Q heap
        Map<Integer, Integer> keys = initKeys();
        PriorityQueue<Node> pq = getHeap();
        Map<Integer, List<Integer>> adj = getAdjancencyList();
        Map<Integer, Integer> parent = getParentList();

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            for (Integer v : adj.get(u.getVertex())) {
                Integer w = getWeight(u.getVertex(), v);
                if (pq.contains(new Node(v, keys.get(v))) && keys.get(v) > w) {
                    // update pq
                    pq.remove(new Node(v, keys.get(v)));
                    pq.add(new Node(v, w));
                    // update keys map
                    keys.put(v, w);
                    // update parent map
                    parent.put(v, u.getVertex());
                }
            }
        }

        List<Edge> result = new ArrayList<>();
        List<Integer> vertices = getVertices();
        for (Integer v: vertices) {
            if (parent.get(v) != null) {
                result.add(getEdge(parent.get(v), v));
            }
        }


        return result;
    }

    public List<Edge> kruskalMST() {
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
        PriorityQueue<Node> pq = getPriorityQueueDijkstra(source);
        Map<Integer, List<Integer>> adj = getAdjancencyList();
        Map<Integer, Integer> result = new HashMap<>();
        
        //populate result
        for (Integer v : getVertices()) {
            result.put(v, Integer.MAX_VALUE);
        }
        result.put(source, 0);

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            for (Integer v : adj.get(u.getVertex())) {
                Integer w = getWeight(u.getVertex(), v);
                if (u.getWeight() + w < result.get(v)) {
                    // update priority queue
                    pq.remove(new Node(v, result.get(v)));
                    pq.add(new Node(v, u.getWeight() + w));
                    // update result
                    result.put(v, u.getWeight() + w);
                }
            }
        }

        return result.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] dagShortestPath(int source) {
        Stack<Integer> stack = topologicalSort(source);
        int[] result = new int[getVertices().size()];
        Arrays.fill(result, Integer.MAX_VALUE);
        Map<Integer, List<Integer>> adj = getAdjancencyList();
        result[source] = 0;

        while(!stack.empty()) {
            int u = stack.pop();

            if (result[u] == Integer.MAX_VALUE) continue;

            for (Integer v : adj.get(u)) {
                Integer w = getWeight(u, v);

                if (result[v] < w + result[u]) {
                    result[v] = w + result[u];
                }
            }
        }


        return result;
    }

    private Stack<Integer> topologicalSort(int source) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> vertices = getVertices();
        List<Integer> state = new ArrayList<>(Collections.nCopies(vertices.size(), 0));
    
        for (Integer v : vertices) {
            if (state.get(v) == 0) {
                dfs(v, state, stack);
            }
        }

        return stack;
    }

    private void dfs(int v, List<Integer> state, Stack<Integer> stack) {
        Map<Integer, List<Integer>> adj = getAdjancencyList();

        state.set(v, 1);
        for (Integer u : adj.get(v)) {
            if (state.get(u) == 1); // TODO: throw error
            if (state.get(u) == 0) {
                dfs(u, state, stack);
            }
        }
        state.set(v, 2);
        stack.push(v);
    }

    public PriorityQueue<Node> getPriorityQueueDijkstra(int source) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        List<Integer> vertices = getVertices();
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
        List<Integer> vertices = getVertices();

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

    private Integer getWeight(int u, int v) {
        for (Edge edge : edges) {
            if (edge.getU() == u && edge.getV() == v) return edge.getWeight();
        }
        return Integer.MAX_VALUE;
    }

    private Map<Integer, Integer> getParentList() {
        List<Integer> vertices = getVertices();
        Map<Integer, Integer> parent = new HashMap<>();
        for (Integer v : vertices) {
            parent.put(v, null);
        }
        return parent;
    }

    private Map<Integer, List<Integer>> getAdjancencyList() {
        List<Integer> vertices = getVertices();
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int v : vertices) {
            adj.put(v, new ArrayList<Integer>());
        }
        for (Edge edge : edges) {
            adj.get(edge.getU()).add(edge.getV());
        }
        return adj;
    }

    private PriorityQueue<Node> getHeap() {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        pq.addAll(getNodes());
        return pq;
    }

    private List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        List<Integer> vertices = getVertices();
        for (Integer v : vertices) {
            nodes.add(new Node(v, Integer.MAX_VALUE));
        }
        nodes.get(0).setWeight(0);
        return nodes;
    }

    private Map<Integer, Integer> initKeys() {
        List<Integer> vertices = getVertices();
        Map<Integer, Integer> keys = new HashMap<>();
        for (int v : vertices) {
            keys.put(v, Integer.MAX_VALUE);
        }
        keys.put(vertices.get(0), 0);
        return keys;
    }

    private List<Integer> getVertices() {
        Set<Integer> vertices = new HashSet<Integer>();
        for (Edge edge : this.edges) {
            vertices.add(edge.getU());
            vertices.add(edge.getV());
        }
        return new ArrayList<>(vertices);
    }
}
