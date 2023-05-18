package computerarchproject.filehandling;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileHandler {
    public static List<String> read(String filePath) {
        LinkedList<String> lines = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null)
                lines.add(line);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("File not found in " + filePath);
        }
        return lines;
    }

    public static void write(String filePath, List<String> lines) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            try {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Not valid lines to write.");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("File not found in " + filePath);
        }
    }
}
