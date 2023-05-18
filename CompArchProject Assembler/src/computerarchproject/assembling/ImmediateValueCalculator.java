package computerarchproject.assembling;

import computerarchproject.database.ProgramLabels;

enum ImmediateFormat {
    BINARY,
    DECIMAL,
    HEXADECIMAL,
    LABEL
}

class ImmediateValueCalculator {

    private static final int I_TYPE_IMM_SIZE = 6;
    private static final int J_TYPE_IMM_SIZE = 12;

    public static String calculateForIType(String presentedImm, ImmediateFormat format) {
        return calculate(presentedImm, format, 'I');
    }

    public static String calculateForJType(String imm, ImmediateFormat format) {
        return calculate(imm, format, 'J');
    }

    private static String calculate(String imm, ImmediateFormat format, char type) {
        String addressOrOffset;
        boolean offset = type == 'I';

        addressOrOffset = switch (format) {
            case HEXADECIMAL -> calculateHex(imm, offset);
            case BINARY -> calculateBinary(imm, offset);
            case LABEL -> getLabelAddress(imm, offset);
            case DECIMAL -> offset ? calculateDecimalOffset(imm) : calculateDecimalAddress(imm);
        };

        return addressOrOffset;
    }

    private static String getLabelAddress(String presentedImm, boolean offset) {
        int address;
        if ((address = ProgramLabels.getAddress(presentedImm)) < 0)
            return null;
        return calculateBinary(Integer.toBinaryString(address), offset);
    }

    private static String calculateDecimalOffset(String presentedImm) {
        int offset = Integer.parseInt(presentedImm);
        if (offset < -32 || offset > 31)
            return null;
        if (offset < 0)
            return calculateBinary(Integer.toBinaryString(offset).substring(32 - I_TYPE_IMM_SIZE), true);

        return calculateBinary(Integer.toBinaryString(offset), true);
    }

    private static String calculateDecimalAddress(String imm) {
        int address = Integer.parseInt(imm);
        if (address < 0 || address > 4095)
            return null;
        return calculateBinary(Integer.toBinaryString(address), false);
    }

    private static String calculateHex(String presentedImm, boolean offset) {
        String binaryRepresentation = Integer.toBinaryString(Integer.parseInt(presentedImm, 16));
        return calculateBinary(binaryRepresentation, offset);
    }

    private static String calculateBinary(String presentedImm, boolean offset) {
        final int MAX_SIZE = offset ? I_TYPE_IMM_SIZE : J_TYPE_IMM_SIZE;

        if (presentedImm.length() > MAX_SIZE)
            return null;
        if (presentedImm.length() < MAX_SIZE) {
            return "0".repeat(MAX_SIZE - presentedImm.length()) + presentedImm;
        }
        return presentedImm;
    }
}
