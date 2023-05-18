package computerarchproject.validation;

import computerarchproject.database.InstructionsData;
import computerarchproject.errors.SyntaxError;

public class SyntaxValidator {
    /*
        steps of validation:
                           1- check if the instructions is in the database
                           2- check if the presented number of operands is matching
                           3- check if the presented operands are valid registers
     */


    private static char type;

    public static SyntaxError isValidSyntax(String line) {
        String[] ins = line.split(",");
        String mnemonic = ins[0];
        if (!isValidMnemonic(mnemonic))
            return SyntaxError.INVALID_INSTRUCTION;
        if (!isValidNumOfOperands(mnemonic, ins.length - 1))
            return SyntaxError.INVALID_NUM_OF_OPERANDS;
        if (!isValidOperands(ins))
            return SyntaxError.INVALID_OPERAND;

        return SyntaxError.VALID_SYNTAX;
    }

    private synchronized static boolean isValidMnemonic(String mnemonic) {
        try {
            type = InstructionsData.selectType(mnemonic);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private static boolean isValidNumOfOperands(String mnemonic, int provided) {
        return InstructionsData.selectNumOfOperands(mnemonic) == provided;
    }

    private static boolean isValidOperands(String[] operands) {
        return OperandsValidator.validateOperands(operands, type);
    }

    private static class OperandsValidator {
        /*
                1- if R-type all operands are registers
                2- if I-type first two operands are registers (or first operand in some instructions) and last one is a numeric value (hex, binary and decimal) or label.
                3- if J-TYPE the operand is a label or a value (hex, binary or decimal)
         */
        static boolean validateOperands(String[] ins, char type) {
            return switch (type) {
                case 'R' -> validateRType(ins);
                case 'I' -> validateIType(ins);
                case 'J' -> validateJType(ins);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
        }

        private static boolean validateJType(String[] operands) {
            String immediate = operands[operands.length - 1];
            String regex = "(0B[01]+)|(0X[0-9A-F]+)|(\\d+)";

            // just numerical values. No support for variables right now.
            if (operands[0].equals("LUI"))
                return immediate.matches(regex);

            return isValidImmediate(immediate, regex, true);
        }

        private static boolean validateIType(String[] ins) {
            String immediate = ins[ins.length - 1];

            boolean allowLabel = switch (ins[0]) {
                case "LW", "SW", "ANDI", "ADDI", "ORI" -> false;
                default -> true;
            };

            if (!isValidImmediate(immediate, "(0B[01]+)|(0X[0-9A-F]+)|([+-]?\\d+)", allowLabel))
                return false;

            for (int i = 1; i < ins.length - 1; i++) {
                if (!ins[i].matches("R[0-7]"))
                    return false;
            }
            return true;
        }

        private static boolean isValidImmediate(String immediate, String regex, boolean isLabelAllowed) {
            boolean validLabel, numberFormat;
            validLabel = immediate.matches("\\w+") && !immediate.contains("0B") && !immediate.contains("0X");
            numberFormat = immediate.matches(regex);
            return isLabelAllowed ? validLabel || numberFormat : numberFormat;
        }

        private static boolean validateRType(String[] operands) {
            for (int i = 1; i < operands.length; ++i) {
                if (!operands[i].matches("R[0-7]"))
                    return false;
            }
            return true;
        }
    }
}

