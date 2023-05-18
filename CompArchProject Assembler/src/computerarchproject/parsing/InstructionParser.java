package computerarchproject.parsing;

import computerarchproject.database.InstructionsData;

import java.util.Arrays;

public class InstructionParser {

    public static Instruction parseInstruction(String ins, int instructionAddress) {
        return configInstruction(tokenize(ins), instructionAddress);
    }

    private static String[] tokenize(String instruction) {
        return instruction.split(",");
    }

    private static Instruction configInstruction(String[] tokens, int insAddress) {
        String mnemonic = tokens[0];
        Instruction instruction = null;
        char type = InstructionsData.selectType(mnemonic);
        switch (type) {
            case 'R' ->
                    instruction = new RTypeInstruction(mnemonic, Arrays.asList(tokens).subList(1, tokens.length), InstructionsData.selectFunCode(mnemonic));
            case 'I', 'J' ->
                    instruction = new IAndJTypeInstruction(mnemonic, Arrays.asList(tokens).subList(1, tokens.length), tokens[tokens.length - 1], type);
        }
        if (instruction != null) {
            instruction.setOpCode(InstructionsData.selectOpcode(mnemonic));
            instruction.setAddress(insAddress);
        }

        return instruction;
    }

}
