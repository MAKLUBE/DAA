package org.example.metrics;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Objects;

public final class CsvWriter implements Closeable, Flushable {
    private final BufferedWriter out;

    public CsvWriter(Path path, boolean append, boolean writeHeader) throws IOException {
        Path dir = path.getParent();
        if (dir != null) Files.createDirectories(dir);
        out = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                append ? new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE}
                        : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE}
        );
        if (writeHeader) {
            writeRow("algo","input","n","time_ns","depth","comps","copies","allocs");
            flush();
        }
    }

    public void writeRow(Object... cells) throws IOException {
        for (int i = 0; i < cells.length; i++) {
            if (i > 0) out.write(',');
            out.write(escape(Objects.toString(cells[i], "")));
        }
        out.write('\n');
    }

    private static String escape(String s) {
        if (s.indexOf(',') >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0 || s.indexOf('\r') >= 0) {
            return '"' + s.replace("\"", "\"\"") + '"';
        }
        return s;
    }

    @Override public void flush() throws IOException {
        out.flush();
    }
    @Override public void close() throws IOException {
        out.close();
    }
}
