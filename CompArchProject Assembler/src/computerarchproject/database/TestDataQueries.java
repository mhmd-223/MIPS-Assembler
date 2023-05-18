package computerarchproject.database;

class TestDataQueries {
    private static final String wrongType = " instruction wrong type";
    private static final String wrongCode = " instruction wrong opcode";
    private static final String wrongNum = " instruction wrong number of operands";


    public static void main(String[] args) {

        String or, add, lw, jal;
        or = "OR";
        add = "ADD";
        lw = "LW";
        jal = "JAL";
        testAND(or);
        testADD(add);
        testLW(lw);
        testJAL(jal);
    }

    private static void testJAL(String jal) {
        // test opcode
        assert InstructionsData.selectOpcode(jal).equals("1011") : jal + wrongCode;
        // test number of operands
        assert InstructionsData.selectNumOfOperands(jal) == 1 : jal + wrongNum;
        // test type
        assert InstructionsData.selectType("JAL") == 'J' : "JAL" + wrongType;

        printSuccessStatement(jal);
    }

    private static void printSuccessStatement(String ins) {
        System.out.println("All tests of " + ins + " instruction.");
    }

    private static void testLW(String lw) {
        assert InstructionsData.selectOpcode(lw).equals("0010") : lw + wrongCode;
        assert InstructionsData.selectNumOfOperands(lw) == 3 : lw + wrongNum;
        assert InstructionsData.selectType(lw) == 'I' : lw + wrongType;
        printSuccessStatement(lw);
    }

    private static void testADD(String add) {
        assert InstructionsData.selectOpcode(add).equals("0001") : add + wrongCode;
        assert InstructionsData.selectNumOfOperands(add) == 3 : add + wrongNum;
        assert InstructionsData.selectType(add) == 'R' : add + wrongType;
        assert InstructionsData.selectFunCode(add).equals("000") : "ADD instruction wrong func code.";
        printSuccessStatement(add);

    }

    private static void testAND(String or) {
        assert InstructionsData.selectOpcode(or).equals("0000") : or + wrongCode;
        assert InstructionsData.selectNumOfOperands(or) == 3 : or + wrongNum;
        assert InstructionsData.selectType(or) == 'R' : or + wrongType;
        assert InstructionsData.selectFunCode(or).equals("001") : "OR instruction wrong func code.";
        printSuccessStatement(or);
    }
}
