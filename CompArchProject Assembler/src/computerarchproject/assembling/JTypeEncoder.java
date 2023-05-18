package computerarchproject.assembling;

import computerarchproject.parsing.Instruction;

public class JTypeEncoder extends Encoder {
    public JTypeEncoder(Instruction instruction) {
        super(instruction);
    }

    @Override
    protected void appendImmediate() {
        String address = encodeImmediate();
        machineCode += address;
    }

    @Override
    protected void adjustOperands() {
        RS = RT = RD = "";
    }
}
