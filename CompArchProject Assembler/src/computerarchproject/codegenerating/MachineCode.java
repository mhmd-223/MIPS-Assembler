package computerarchproject.codegenerating;

import computerarchproject.assembling.ITypeEncoder;
import computerarchproject.assembling.JTypeEncoder;
import computerarchproject.assembling.RTypeEncoder;
import computerarchproject.filehandling.FileHandler;
import computerarchproject.inputhandling.CommandLineInput;
import computerarchproject.parsing.Instruction;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MachineCode {
    public static void main(String[] args) {
        List<Instruction> program;
        LinkedList<String> machineCode = new LinkedList<>();
        LinkedList<String> machineCodeHex = new LinkedList<>();

        /* The instructions are passed in file as command line arguments. */
        if (args.length > 0) {
            program = CommandLineInput.getProgram(args[0]);
            assemble(program, machineCode);
            toHex(machineCode, machineCodeHex);

            /* Write the result machine code to a file. */
            if (args.length == 3) {
                String machineCodePath = args[1];
                String format = args[2].toLowerCase();
                if (format.equals("hex"))
                    FileHandler.write(machineCodePath, machineCodeHex);
                else if (format.equals("binary"))
                    FileHandler.write(machineCodePath, machineCode);
                else
                    throw new IllegalArgumentException("Unexpected format: " + format + "\nValid formats: binary or hex");
            } else if (args.length == 1) { /* Print the result machine code in terminal. */
                machineCodeHex.forEach(System.out::println);
            } else if (args.length == 2) {
                FileHandler.write(args[1], machineCodeHex);
            }
        } else {
            terminalInput(machineCode, machineCodeHex);
        }

    }

    private static void terminalInput(LinkedList<String> machineCode, LinkedList<String> machineCodeHex) {
        List<Instruction> program;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Instructions file path (source): ");
            String codePath = scanner.next();
            System.out.print("Machine code file path (target): ");
            String resultPath = scanner.next();
            program = CommandLineInput.getProgram(codePath);
            String format;
            System.out.print("Format (binary/hex): ");
            while (!"binary,hex".contains(format = scanner.next().trim().toLowerCase())) {
                System.out.println("Unexpected format: " + format);
            }
            assemble(program, machineCode);
            toHex(machineCode, machineCodeHex);
            if (format.equals("binary"))
                FileHandler.write(resultPath, machineCode);
            else
                FileHandler.write(resultPath, machineCodeHex);

        }
    }

    private static void toHex(LinkedList<String> machineCode, LinkedList<String> machineCodeHex) {
        machineCode.forEach(line -> {
            /* in case if an instruction is split into two like in comparison */
            String[] code = line.split("\n");
            for (String binaryCode : code)
                machineCodeHex.add(Integer.toHexString(Integer.parseUnsignedInt(binaryCode, 2)));
        });
    }

    private static void assemble(List<Instruction> program, LinkedList<String> machineCode) {
        for (Instruction instruction : program) {
            switch (instruction.getType()) {
                case 'R' -> machineCode.add(new RTypeEncoder(instruction).getMachineCode());
                case 'I' -> machineCode.add(new ITypeEncoder(instruction).getMachineCode());
                case 'J' -> machineCode.add(new JTypeEncoder(instruction).getMachineCode());
            }
        }
    }

}
