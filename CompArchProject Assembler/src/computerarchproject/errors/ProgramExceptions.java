package computerarchproject.errors;

import computerarchproject.database.InstructionsData;
import computerarchproject.database.ProgramLabels;

public class ProgramExceptions extends RuntimeException {

    public ProgramExceptions(SyntaxError syntaxError, int lineNumber, String inst) {
        super(getSyntaxMessage(syntaxError, lineNumber, inst.split(",")));
    }

    public ProgramExceptions(AddressingError addressingError, String labelOrImm, int lineNumber) {
        super(getAddressingMessage(addressingError, labelOrImm, lineNumber));
    }

    private static String getSyntaxMessage(SyntaxError syntaxError, int lineNumber, String[] ins) {
        String message;

        message = switch (syntaxError) {
            case INVALID_NUM_OF_OPERANDS -> numOfOperandsMessage(lineNumber, ins.length - 1, ins[0]);
            case INVALID_INSTRUCTION -> mnemonicMessage(lineNumber, ins[0]);
            case INVALID_OPERAND -> operandMessage(lineNumber, ins[0]);
            case VALID_SYNTAX -> "";
        };
        return message;
    }

    private static String getAddressingMessage(AddressingError addressingError, String labelOrImm, int line) {
        return switch (addressingError) {
            case NO_SUCH_LABEL ->
                    "Invalid label provided. Label '%s' at line %d is not defined in the program. Please check the label name and ensure that it has been defined before use.".formatted(labelOrImm.replace(":", ""), line);
            case LABEL_ALREADY_EXISTS ->
                    "Label redefinition error. Label '%s' is already defined at line %d. It is redefined at line %d. Please remove the duplicate label or rename it to a unique name.".formatted(labelOrImm.replace(":", ""), ProgramLabels.getAddress(labelOrImm) + 1, line);
            case OUT_OF_BOUNDARIES ->
                    "Invalid immediate value provided. Immediate value %s at line %d is out of the valid range (-32 to 31). Please check the immediate value and provide a valid value within the range.".formatted(labelOrImm, line);
            case TOO_LARGE_ADDRESS ->
                    "Invalid memory address provided. Memory address %s at line %d is outside the valid range of 0 to 4095 (12-bit memory address). Please check the memory address and provide a valid address within the range.".formatted(labelOrImm, line);
        };
    }

    private static String numOfOperandsMessage(int lineNumber, int provided, String mnemonic) {
        return ("Invalid number of operands provided for instruction '%s'" +
                " at line %d. Expected %d operands, but %d were provided. " +
                "Please check the instruction syntax and provide the correct number of operands.").
                formatted(mnemonic, lineNumber, InstructionsData.selectNumOfOperands(mnemonic), provided);
    }

    private static String mnemonicMessage(int lineNumber, String mnemonic) {
        return ("Invalid instruction mnemonic provided at line %d. Mnemonic '%s' is not a valid instruction mnemonic." +
                " Please check the instruction syntax and provide a valid instruction mnemonic.").
                formatted(lineNumber, mnemonic);
    }

    private static String operandMessage(int lineNumber, String mnemonic) {
        String operandType = "";
        switch (InstructionsData.selectType(mnemonic)) {
            case 'R' -> operandType = "register";
            case 'J' -> {
                if (mnemonic.equals("LUI"))
                    operandType = "immediate value";
                else
                    operandType = "label or immediate value";
            }
            case 'I' -> {
                switch (mnemonic) {
                    case "LW", "SW", "ORI", "ANDI", "ADDI" -> operandType = "register or immediate value";
                    default -> operandType = "register or label or immediate offset";
                }
            }
        }
        return ("Invalid operand provided for instruction '%s' at line %d. One of operands is not a valid %s." +
                " Please check the instruction syntax and provide a valid %s for the operand.").
                formatted(mnemonic, lineNumber, operandType, operandType);
    }

}

