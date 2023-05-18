package computerarchproject.inputhandling;

import computerarchproject.database.ProgramLabels;
import computerarchproject.errors.AddressingError;
import computerarchproject.errors.ProgramExceptions;
import computerarchproject.errors.SyntaxError;
import computerarchproject.parsing.Instruction;
import computerarchproject.parsing.InstructionParser;
import computerarchproject.validation.SyntaxValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract class Input {
    protected static LinkedList<Instruction> program = new LinkedList<>();

    private static void checkExistedLabels(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.matches("\\w+:") && !ProgramLabels.addLabel(line, i))
                throw new ProgramExceptions(AddressingError.LABEL_ALREADY_EXISTS, line, i + 1);
        }
    }

    private static void writeProgram(LinkedList<Instruction> program, Map<Integer, String> addresses) {
        addresses.forEach((address, line) -> {
            SyntaxError syntaxValidationResult = SyntaxValidator.isValidSyntax(line);
            if (syntaxValidationResult == SyntaxError.VALID_SYNTAX)
                program.add(InstructionParser.parseInstruction(line, address));
            else
                throw new ProgramExceptions(syntaxValidationResult, address + 1, line);
        });
    }

    protected static void cleanAndWriteProgram(List<String> lines) {
        lines = InputHandler.clean(lines);
        checkExistedLabels(lines);
        Map<Integer, String> addresses = InputHandler.organizeLines(lines);
        writeProgram(program, addresses);
    }

}
