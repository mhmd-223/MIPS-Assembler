package computerarchproject.parsing;

import java.util.List;

public class RTypeInstruction extends Instruction {

    private final String funcOp;


    protected RTypeInstruction(String mnemonic, List<String> operands, String funcOp) {
        super(mnemonic, 'R', operands);
        this.funcOp = funcOp;
    }

    public String getFuncOp() {
        return funcOp;
    }


    @Override
    public String toString() {
        return "RTypeInstruction{" +
                "funcOp='" + funcOp + '\'' +
                "} " + super.toString();
    }
}
