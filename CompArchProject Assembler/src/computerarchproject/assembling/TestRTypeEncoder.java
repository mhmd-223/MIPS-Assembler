package computerarchproject.assembling;

import computerarchproject.parsing.InstructionParser;
import computerarchproject.parsing.RTypeInstruction;

class TestRTypeEncoder {
    public static void main(String[] args) {
        RTypeInstruction instruction = ((RTypeInstruction) InstructionParser.parseInstruction("AND,R1,R2,R3", 0));
        String machineCode = new RTypeEncoder(instruction).getMachineCode();
        System.out.printf("%#x\n", Integer.parseInt(machineCode, 2));
    }
}
