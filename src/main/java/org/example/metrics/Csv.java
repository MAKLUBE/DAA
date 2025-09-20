package org.example.metrics;

import java.io.FileWriter;
import java.io.IOException;

public final class Csv {
    private CsvWriter() {}
    public static void append(String file, String line) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(line);
            if (!line.endsWith("\n")) fw.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
