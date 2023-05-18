package computerarchproject.assembling;

import computerarchproject.parsing.Instruction;
import computerarchproject.parsing.RTypeInstruction;

public class RTypeEncoder extends Encoder {


    public RTypeEncoder(Instruction instruction) {
        super(instruction);
    }

    @Override
    protected void adjustOperands() {
        String mnemonic = instruction.getMnemonic();
        RD = operands.get(0);
        switch (mnemonic) {
            case "JR" -> {
                RD = r0;
                RS = operands.get(0);
                RT = r0;
            }
            case "JALR" -> {

                RS = operands.get(1);
                RT = r0;
            }
            default -> {
                RS = operands.get(1);
                RT = operands.get(2);
            }
        }
    }

    @Override
    protected void appendImmediate() {
        machineCode += ((RTypeInstruction) instruction).getFuncOp();
    }

}
