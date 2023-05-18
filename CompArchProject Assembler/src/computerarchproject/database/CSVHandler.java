package computerarchproject.database;

import computerarchproject.filehandling.FileHandler;

import java.util.*;

class CSVHandler {
    //  this class will handle csv files
    //  in the same way as csv module of python
    private final List<String> fileLines;
    private LinkedList<Map<String, String>> rows;

    public CSVHandler(String fileName) {
        fileLines = FileHandler.read(fileName);
        readFile();
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    private void readFile() {
        rows = new LinkedList<>();
        // The header
        String[] keys = fileLines.get(0).split(",");
        fileLines.remove(0);

        fileLines.forEach(line -> {
            String[] values = line.split(",");
            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < keys.length; i++) {
                row.put(keys[i].toLowerCase(), values[i]);
            }
            rows.add(row);
        });
    }

    public List<Map<String, String>> filterBy(String header, String value) {
        if (!rows.getFirst().containsKey(header.toLowerCase()))
            throw new NoSuchElementException("The presented header is not found.");

        LinkedList<Map<String, String>> filteredRows = new LinkedList<>();
        rows.forEach(row -> {
            if (row.get(header.toLowerCase()).equalsIgnoreCase(value))
                filteredRows.add(row);
        });
        return filteredRows;
    }
}
