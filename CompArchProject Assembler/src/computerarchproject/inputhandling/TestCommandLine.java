package computerarchproject.inputhandling;

class TestCommandLine {
    public static void main(String[] args) {
        String programFile = "src/main/java/computerarchproject/inputhandling/testCommandLine.txt";
        CommandLineInput.getProgram(programFile).
                forEach((instruction) -> System.out.println(instruction.toString()));
    }
}
