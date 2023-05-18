package computerarchproject.database;

import java.util.HashMap;
import java.util.Map;

public class ProgramLabels {
    private static final Map<String, Integer> labels = new HashMap<>();

    public static boolean addLabel(String label, int address) {
        return labels.putIfAbsent(label, address) == null;
    }

    public static int getAddress(String label) {
        if (!labels.containsKey(label))
            return -1;
        return labels.get(label);
    }
}
