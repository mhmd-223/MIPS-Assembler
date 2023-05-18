package computerarchproject.validation;

import computerarchproject.errors.SyntaxError;

class TestValidator {
    public static void main(String[] args) {
        String ins1 = "ADD,R1,R2,R3"; // correct
        String ins2 = "ADD,R1,R2"; // wrong
        String ins3 = "DUMMY,R5,R0"; // wrong
        String ins4 = "LW,R9,RR,R-"; // wrong

        test1(ins1, ins2, ins3, ins4);

        ins1 = "LW,R1,R2,LABEL";  // wrong
        ins2 = "BGT,R1,R2,0XF72"; // correct
        ins3 = "J,0B1010105"; // wrong
        ins4 = "BGTZ,R1,LABLE#"; // wrong

        test2(ins1, ins2, ins3, ins4);

        ins1 = "LW,R1,R2,0B101555";  // wrong
        ins2 = "BGT,R1,R2,0B101011"; // correct
        ins3 = "JAL,0XFFF"; // correct
        ins4 = "LUI,LABEL"; // wrong

        test3(ins1, ins2, ins3, ins4);

        ins1 = "LW,R1,R2,-5";  // correct
        ins2 = "BGT,R1,R2,0X"; // wrong
        ins3 = "JAL,LABEL"; // correct
        ins4 = "LUI,0XAAA"; // correct

        test4(ins1, ins2, ins3, ins4);


        System.out.println("All tests passed");
    }

    private static void test4(String ins1, String ins2, String ins3, String ins4) {
        assert SyntaxValidator.isValidSyntax(ins1) == SyntaxError.VALID_SYNTAX : "correct failed";
        assert SyntaxValidator.isValidSyntax(ins2) == SyntaxError.INVALID_OPERAND : "operand validation failed";
        assert SyntaxValidator.isValidSyntax(ins3) == SyntaxError.VALID_SYNTAX : "correct failed";
        assert SyntaxValidator.isValidSyntax(ins4) == SyntaxError.VALID_SYNTAX : "correct failed";
    }

    private static void test3(String ins1, String ins2, String ins3, String ins4) {
        assert SyntaxValidator.isValidSyntax(ins1) == SyntaxError.INVALID_OPERAND : "operand validation failed";
        assert SyntaxValidator.isValidSyntax(ins2) == SyntaxError.VALID_SYNTAX : "correct failed";
        assert SyntaxValidator.isValidSyntax(ins3) == SyntaxError.VALID_SYNTAX : "correct failed";
        assert SyntaxValidator.isValidSyntax(ins4) == SyntaxError.INVALID_OPERAND : "operand validation failed";
    }

    private static void test2(String ins1, String ins2, String ins3, String ins4) {
        assert SyntaxValidator.isValidSyntax(ins1) == SyntaxError.INVALID_OPERAND : "operand validation failed.";
        assert SyntaxValidator.isValidSyntax(ins2) == SyntaxError.VALID_SYNTAX : "correct failed";
        assert SyntaxValidator.isValidSyntax(ins3) == SyntaxError.INVALID_OPERAND : "operand validation failed";
        assert SyntaxValidator.isValidSyntax(ins4) == SyntaxError.INVALID_OPERAND : "operand validation failed";
    }

    private static void test1(String ins1, String ins2, String ins3, String ins4) {
        assert SyntaxValidator.isValidSyntax(ins1) == SyntaxError.VALID_SYNTAX : "correct instruction considered wrong";
        assert SyntaxValidator.isValidSyntax(ins2) == SyntaxError.INVALID_NUM_OF_OPERANDS : "failed in numbers of operands";
        assert SyntaxValidator.isValidSyntax(ins3) == SyntaxError.INVALID_INSTRUCTION : "failed in instruction mnemonic";
        assert SyntaxValidator.isValidSyntax(ins4) == SyntaxError.INVALID_OPERAND : "failed in invalid registers";
    }
}
