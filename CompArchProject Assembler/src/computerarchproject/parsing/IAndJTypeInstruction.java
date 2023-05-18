package computerarchproject.parsing;

import java.util.List;

public class IAndJTypeInstruction extends Instruction {


    private final String immediate;

    protected IAndJTypeInstruction(String mnemonic, List<String> operands, String immediate, char type) {
        super(mnemonic, type, operands);
        this.immediate = immediate;
    }

    public String getImmediate() {
        return immediate;
    }


    @Override
    public String toString() {
        return "ITypeInstruction{" +
                "immediate='" + immediate + '\'' +
                "} " + super.toString();
    }
}
