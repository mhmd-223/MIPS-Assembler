package computerarchproject.parsing;

import java.util.List;

public abstract class Instruction {
    private final String mnemonic;
    private final char type;
    private final List<String> operands;
    private int address;
    private String opCode;

    public Instruction(String mnemonic, char type, List<String> operands) {
        this.mnemonic = mnemonic;
        this.type = type;
        this.operands = operands;

    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public char getType() {
        return type;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public List<String> getOperands() {
        return operands;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "mnemonic='" + mnemonic + '\'' +
                ", opCode='" + opCode + '\'' +
                ", type=" + type +
                ", operands=" + operands +
                '}';
    }
}
