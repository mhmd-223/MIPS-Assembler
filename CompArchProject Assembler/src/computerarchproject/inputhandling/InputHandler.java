package computerarchproject.inputhandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class InputHandler {
    private static final String COMMENT_SYMBOL = "%";

    // I want each line like "ADD,R1,R2,R3"
    public static List<String> clean(List<String> instructions) {
        LinkedList<String> cleanedLines = new LinkedList<>();

        for (String instruction : instructions) {

            if (instruction.isEmpty())
                continue;

            instruction = instruction.trim();
            // a comment, ignore it
            if (instruction.startsWith(COMMENT_SYMBOL))
                continue;

            instruction = instruction.toUpperCase();

            // an instruction followed by a comment
            if (instruction.contains(COMMENT_SYMBOL))
                instruction = instruction.replaceAll(COMMENT_SYMBOL + ".*", "");

            // case of labels
            if (instruction.matches("\\w+:.*")) {
                String[] inlineInstruction = instruction.split(":"); // Ex => label: instruction
                // Case of [ label: ] in a single line
                if (inlineInstruction.length == 1) {
                    cleanedLines.add(instruction);
                    continue;
                } else {
                    cleanedLines.add(inlineInstruction[0] + ":");
                    instruction = inlineInstruction[1].trim();
                }
            }

            // make all separated by commas
            instruction = instruction.replace(',', ' '); // EX => lw r1, r2, r3 --> lw r1 r2 r3
            instruction = instruction.replaceAll(" +", ","); // => lw,r1,r2,r3
            cleanedLines.add(instruction);
        }

        return cleanedLines;
    }


    // attach each line of code with its corresponding address in the instructions
    public static Map<Integer, String> organizeLines(List<String> instructions) {
        Map<Integer, String> addresses = new HashMap<>();
        int address = 0;
        for (String instruction : instructions) {
            if (!instruction.matches(".+:")) {
                addresses.put(address++, instruction);
            }
        }
        return addresses;
    }

}
