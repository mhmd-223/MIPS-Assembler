package computerarchproject.database;

import java.util.NoSuchElementException;

class TestCSVHandler {
    public static void main(String[] args) {
        String file = "src/main/java/computerarchproject/database/instructions data.csv";
        CSVHandler handler = new CSVHandler(file);

        assert handler.getRows().size() == 32; // current count of rows in csv file
        assert handler.filterBy("type", "r").equals(handler.filterBy("TYpE", "R"));
        assert handler.filterBy("opCode", "1010").get(0).get("opcode").equals("1010");
        assert handler.filterBy("type", "j").size() == 3;
        try {
            handler.filterBy("dummy", "dummy");
            System.out.println("Wrong key is not found.");
        } catch (NoSuchElementException ignored) {
        }

        System.out.println("All tests pass");
    }
}
