package dsa.assignment;

import java.util.ArrayList;
import java.util.List;

import dsa.assignment.graph.Graph;
import dsa.assignment.service.GraphGenerator;

public class Benchmark {
    public static void main(String[] args) {
        firstBenchmark();
    }

    private static void firstBenchmark() {
        // Declare graphs
        GraphGenerator generator = new GraphGenerator();
        Graph sparse = generator.generateSparseGraph(1000),
        dense = generator.generateDenseGraph(1000),
        complete = generator.generateCompleteGraph(1000);

        // MST Benchmarks
        List<Long> primSparseResults = new ArrayList<>();
        List<Long> kruskalSparseResults = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Long time;

            time = benchmark(()->sparse.primMST());
            primSparseResults.add(time);

            time = benchmark(()->sparse.kruskalMST());
            kruskalSparseResults.add(time);
        }


        List<Long> primDenseResults = new ArrayList<>();
        List<Long> kruskalDenseResults = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Long time;

            time = benchmark(()->dense.primMST());
            primDenseResults.add(time);

            time = benchmark(()->dense.kruskalMST());
            kruskalDenseResults.add(time);
        }

        List<Long> primCompleteResults = new ArrayList<>();
        List<Long> kruskalCompleteResults = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Long time;

            time = benchmark(()->complete.primMST());
            primCompleteResults.add(time);

            time = benchmark(()->complete.kruskalMST());
            kruskalCompleteResults.add(time);
        }


    }

    private static long benchmark(Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        return end - start;
    }
}
