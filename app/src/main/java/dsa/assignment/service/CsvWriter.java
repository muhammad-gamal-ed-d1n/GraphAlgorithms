package dsa.assignment.service;

import java.io.PrintWriter;

public class CsvWriter {

    public static void writeHeader(PrintWriter writer) {
        writer.println("Algorithm,Distribution,Run,Time");
    }

    public static void WriteRow(
        PrintWriter writer,
        String algorithm,
        String distribution,
        Integer run,
        Long time
    ) {
        writer.printf("%s,%s,%d,%d%n", algorithm, distribution, run, time);
    }
}
