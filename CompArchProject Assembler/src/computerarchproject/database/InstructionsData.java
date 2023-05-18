package computerarchproject.database;

import java.util.Map;

public class InstructionsData {
    //  this class will serve for data, it will contain methods to
    //  fetch data with the name of instruction
    //  methods will be, getOpcode, getNumOfOps ....etc

    private static final CSVHandler READER = new CSVHandler("src/computerarchproject/database/instructions data.csv");

    public static char selectType(String ins) {
        return getInsData(ins).get("type").charAt(0);
    }

    private static Map<String, String> getInsData(String ins) {
        return READER.filterBy("Instruction", ins).get(0);
    }

    public static String selectOpcode(String ins) {
        return getInsData(ins).get("opcode");
    }

    public static int selectNumOfOperands(String ins) {
        return Integer.parseInt(getInsData(ins).get("numofoperands"));
    }

    public static String selectFunCode(String ins) {
        return getInsData(ins).get("funcode");
    }
}
