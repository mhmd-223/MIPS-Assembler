package computerarchproject.assembling;

import computerarchproject.database.Registers;
import computerarchproject.errors.AddressingError;
import computerarchproject.errors.ProgramExceptions;
import computerarchproject.parsing.IAndJTypeInstruction;
import computerarchproject.parsing.Instruction;

import java.util.List;

abstract class Encoder {
    protected final String r0 = "R0";
    protected String machineCode;
    protected String OPCODE, RS, RT, RD;
    protected List<String> operands;
    protected Instruction instruction;

    public Encoder(Instruction instruction) {
        this.instruction = instruction;
        OPCODE = instruction.getOpCode();
        operands = instruction.getOperands();
        machineCode = "";
        adjustOperands();
        encode();
    }

    abstract protected void appendImmediate();

    abstract protected void adjustOperands();

    protected void encode() {
        machineCode += OPCODE + Registers.selectCode(RS) + Registers.selectCode(RT) + Registers.selectCode(RD);
        appendImmediate();
    }

    public String getMachineCode() {
        return machineCode;
    }

    protected String getOffsetOrAddress(String immediate, ImmediateFormat format) {
        String offset, address;
        offset = ImmediateValueCalculator.calculateForIType(immediate, format);
        address = ImmediateValueCalculator.calculateForJType(immediate, format);
        String addressOrOffset = instruction.getType() == 'I' ? offset : address;
        AddressingError error;

        if (format == ImmediateFormat.LABEL && addressOrOffset == null)
            error = AddressingError.NO_SUCH_LABEL;
        else
            error = (instruction.getType() == 'I') ? AddressingError.OUT_OF_BOUNDARIES : AddressingError.TOO_LARGE_ADDRESS;
        if (addressOrOffset == null) throw new ProgramExceptions(error, immediate, instruction.getAddress() + 1);
        return addressOrOffset;
    }

    protected String encodeImmediate() {
        String immediate = ((IAndJTypeInstruction) instruction).getImmediate();
        String address;

        if (immediate.startsWith("0B")) address = getOffsetOrAddress(immediate.substring(2), ImmediateFormat.BINARY);

        else if (immediate.startsWith("0X"))
            address = getOffsetOrAddress(immediate.substring(2), ImmediateFormat.HEXADECIMAL);

        else {
            try {
                Integer.parseInt(immediate); // check it is numeric
                address = getOffsetOrAddress(immediate, ImmediateFormat.DECIMAL);
            } catch (NumberFormatException e) {
                address = getOffsetOrAddress(immediate + ":", ImmediateFormat.LABEL);
                address = instruction.getType() == 'J' ? address : calculateRelativeOffset(address);
            }
        }
        return address;
    }

    String calculateRelativeOffset(String labelAddress) {
        int relativeOffset = Integer.parseInt(labelAddress, 2) - instruction.getAddress() - 1; // the one because the offset starts from the next instruction
        String offset = Integer.toBinaryString(relativeOffset);
        offset = relativeOffset >= 0 ? offset : offset.substring(26); // 32[returned length]  - 6[max size for immediate]
        return ImmediateValueCalculator.calculateForIType(offset, ImmediateFormat.BINARY);
    }

}
