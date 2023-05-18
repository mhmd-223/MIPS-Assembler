package computerarchproject.inputhandling;

import computerarchproject.filehandling.FileHandler;
import computerarchproject.parsing.Instruction;

import java.util.LinkedList;
import java.util.List;

public class CommandLineInput extends Input {
    public static List<Instruction> getProgram(String fileName) {
        program = new LinkedList<>();
        List<String> lines = FileHandler.read(fileName);
        cleanAndWriteProgram(lines);
        return program;
    }

}