package m;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MapParser {
    private PrintWriter writer;

    public MapParser(String fileName) throws FileNotFoundException {
        this.writer = new PrintWriter(fileName);
    }
}
