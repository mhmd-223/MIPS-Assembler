package computerarchproject.inputhandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestCodeRefactoring {
    public static void main(String[] args) {
        List<String> before = new ArrayList<>(List.of("add r1,r2,r3       ", "   addi             r1,  r0,    5      ", "lw r1, r2, 6"));
        List<String> after = new ArrayList<>(List.of("ADD,R1,R2,R3", "ADDI,R1,R0,5", "LW,R1,R2,6"));
        testCleaningAndAddresses(before, after);
        before = new ArrayList<>(List.of("addi r1,  r0,   3    ", "  add r5,r1    ,r2", "label:      ", "  j  0 "));
        testAddressesWithLabels(before);
        testInlineLabels();
        System.out.println("All tests passed.");
    }

    private static void testInlineLabels() {
        List<String> after;
        List<String> before;
        before = new ArrayList<>(List.of("addi r1,  r0,   3    ", "  add r5,r1    ,r2", "label:      lui 4", "  j  0 "));
        after = new ArrayList<>(List.of("ADDI,R1,R0,3", "ADD,R5,R1,R2", "LABEL:", "LUI,4", "J,0"));
        Map<Integer, String> organized = new HashMap<>();
        organized.put(0, "ADDI,R1,R0,3");
        organized.put(1, "ADD,R5,R1,R2");
        organized.put(2, "LUI,4");
        organized.put(3, "J,0");
        assert InputHandler.clean(before).equals(after) : "Failed in inline labels";
        assert InputHandler.organizeLines(after).equals(organized) : "Failed in inline labels";
    }


    private static void testAddressesWithLabels(List<String> before) {
        Map<Integer, String> organized;
        organized = new HashMap<>();
        organized.put(0, "ADDI,R1,R0,3");
        organized.put(1, "ADD,R5,R1,R2");
        organized.put(2, "J,0");
        assert InputHandler.organizeLines(InputHandler.clean(before)).equals(organized) : "Not handled labels";
    }

    private static void testCleaningAndAddresses(List<String> before, List<String> after) {
        assert InputHandler.clean(before).equals(after) : "Not cleaned";
        Map<Integer, String> organized = new HashMap<>();
        organized.put(0, "ADD,R1,R2,R3");
        organized.put(1, "ADDI,R1,R0,5");
        organized.put(2, "LW,R1,R2,6");
        assert InputHandler.organizeLines(after).equals(organized) : "Not organized";
    }
}
