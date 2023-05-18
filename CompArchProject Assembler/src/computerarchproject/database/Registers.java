package computerarchproject.database;

public class Registers {
    private static final CSVHandler READER = new CSVHandler("src/computerarchproject/database/registers.csv");

    public static String selectCode(String register) {
        if (register.isEmpty())
            return "";
        return READER.filterBy("Register", register).get(0).get("code");
    }
}
