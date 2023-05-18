package computerarchproject.assembling;

import computerarchproject.parsing.IAndJTypeInstruction;
import computerarchproject.parsing.Instruction;
import computerarchproject.parsing.InstructionParser;
import computerarchproject.parsing.RTypeInstruction;

import java.util.List;

public class ITypeEncoder extends Encoder {

    public ITypeEncoder(Instruction instruction) {
        super(instruction);
    }

    @Override
    protected void appendImmediate() {
        String offset = encodeImmediate();
        machineCode += offset;
    }

    @Override
    protected void adjustOperands() {
        String mnemonic = instruction.getMnemonic();    //LW,R1,R0,5 BLTZ R1,LABEL
        RD = "";
        switch (mnemonic) {
            case "BLTZ", "BLEZ", "BGTZ", "BGEZ" -> {
                RT = r0;
                RS = operands.get(0);
            }
            case "BLT", "BGT" -> {
                RS = operands.get(0);
                RT = operands.get(1);
            }
            default -> {
                RT = operands.get(0);
                RS = operands.get(1);
            }
        }

        switch (mnemonic) {
            case "BLTZ", "BLT" -> breakdownComparison(RS, RT, false);
            case "BLEZ" -> breakdownComparison(RS, RT, true);
            case "BGTZ", "BGT" -> breakdownComparison(RT, RS, false);
            case "BGEZ" -> breakdownComparison(RT, RS, true);
        }
    }

    private void breakdownComparison(String rs, String rt, boolean compareEquality) {
        String setLessOrEqual = compareEquality ? "SLTE" : "SLT";
        RTypeInstruction setLessIns = (RTypeInstruction) InstructionParser.parseInstruction(setLessOrEqual + ",R7," + rs + "," + rt, instruction.getAddress());
        machineCode += new RTypeEncoder(setLessIns).getMachineCode() + "\n";
        IAndJTypeInstruction branchNotEqualIns = (IAndJTypeInstruction) InstructionParser.parseInstruction("BNE,R7,R0," + ((IAndJTypeInstruction) instruction).getImmediate(), instruction.getAddress() + 1);
        machineCode += new ITypeEncoder(branchNotEqualIns).getMachineCode() + "\n";
    }

    @Override
    protected void encode() {
        String mnemonic = instruction.getMnemonic();
        if (List.of("BLTZ", "BLT", "BLEZ", "BGTZ", "BGT", "BGEZ").contains(mnemonic)) {
            return;
        }
        super.encode();
    }

}
