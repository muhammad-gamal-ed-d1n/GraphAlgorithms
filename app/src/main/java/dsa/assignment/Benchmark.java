package dsa.assignment;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

import dsa.assignment.graph.Graph;
import dsa.assignment.service.CsvWriter;
import dsa.assignment.service.GraphGenerator;

public class Benchmark {
    public static void main(String[] args) {
        thirdBenchmark();
    }

    private static void firstBenchmark() {
        try {
            // Initialize CsvWriter
            PrintWriter writer = new PrintWriter(new FileWriter("mst_construction.csv"));
            CsvWriter.writeHeader(writer);

            // Declare graphs
            GraphGenerator generator = new GraphGenerator();
            Graph sparse = generator.generateSparseGraph(1000),
            dense = generator.generateDenseGraph(1000),
            complete = generator.generateCompleteGraph(1000);

            // MST Benchmarks
            for (int i = 0; i < 5; i++) {
                Long time;

                time = benchmark(()->sparse.primMST());

                CsvWriter.WriteRow(writer, "Prim's", "Sparse", i + 1, time);
                writer.flush();

                time = benchmark(()->sparse.kruskalMST());

                CsvWriter.WriteRow(writer, "Kruskal", "Sparse", i + 1, time);
                writer.flush();
            }


            for (int i = 0; i < 5; i++) {
                Long time;

                time = benchmark(()->dense.primMST());
                CsvWriter.WriteRow(writer, "Prim's", "Dense", i + 1, time);
                writer.flush();

                time = benchmark(()->dense.kruskalMST());

                CsvWriter.WriteRow(writer, "Kruskal", "Dense", i + 1, time);
                writer.flush();
            }

            for (int i = 0; i < 5; i++) {
                Long time;

                time = benchmark(()->complete.primMST());

                CsvWriter.WriteRow(writer, "Prim's", "Complete", i + 1, time);
                writer.flush();

                time = benchmark(()->complete.kruskalMST());

                CsvWriter.WriteRow(writer, "Kruskal", "Complete", i + 1, time);
                writer.flush();
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void secondBenchmark() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("sssp_general.csv"));
            CsvWriter.writeHeader(writer);
            writer.flush();

            Random rand = new Random(5123423);

            // initialize graphs
            GraphGenerator generator = new GraphGenerator();
            Graph sparse = generator.generateSparseGraph(1000), 
            dense = generator.generateDenseGraph(1000),
            complete = generator.generateCompleteGraph(1000),
            dag = generator.generateDAG(1000);


            for (int i = 0; i < 5; i++) {
                Long time;
                int source = rand.nextInt(1000);

                time = benchmark(() -> sparse.dijkstra(source));
                CsvWriter.WriteRow(writer, "Dijkstra", "Sparse", i + 1, time);
                writer.flush();
            }

            for (int i = 0; i < 5; i++) {
                Long time;
                int source = rand.nextInt(1000);

                time = benchmark(() -> dense.dijkstra(source));
                CsvWriter.WriteRow(writer, "Dijkstra", "Dense", i + 1, time);
                writer.flush();
            }
            for (int i = 0; i < 5; i++) {
                Long time;
                int source = rand.nextInt(1000);

                time = benchmark(() -> complete.dijkstra(source));
                CsvWriter.WriteRow(writer, "Dijkstra", "Complete", i + 1, time);
                writer.flush();
            }

            for (int i = 0; i < 5; i++) {
                Long time;
                int source = rand.nextInt(1000);

                time = benchmark(() -> dag.dijkstra(source));
                CsvWriter.WriteRow(writer, "Dijkstra", "DAG", i + 1, time);
                writer.flush();
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void thirdBenchmark() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("sssp_dag.csv"));
            CsvWriter.writeHeader(writer);
            writer.flush();

            Random rand = new Random(73891275);

            // initialize graphs
            GraphGenerator generator = new GraphGenerator();
            Graph dag = generator.generateDAG(1000);

            for (int i = 0; i < 5; i++) {
                Long time;
                int source = rand.nextInt(1000);

                time = benchmark(() -> dag.dijkstra(source));
                CsvWriter.WriteRow(writer, "Dijkstra", "DAG", i + 1, time);
                writer.flush();

                time = benchmark(() -> dag.dagShortestPath(source));
                CsvWriter.WriteRow(writer, "DAGShortestTime", "DAG", i + 1, time);
                writer.flush();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long benchmark(Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        return end - start;
    }
}
