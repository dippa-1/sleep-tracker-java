import java.util.HashMap;
import java.util.Map;

public enum ArgumentType {
    BEDTIME("-b", "--bed"), WAKEUPTIME("-w", "--wakeup"), RATING("-r", "--rating"), DATE("-d", "--date");

    public final String flag;
    public final String longFlag;

    private static final Map<String, ArgumentType> BY_FLAG = new HashMap<>();

    static {
        for (ArgumentType e: values()) {
            BY_FLAG.put(e.flag, e);
            BY_FLAG.put(e.longFlag, e);
        }
    }

    private ArgumentType(String flag, String longFlag) {
        this.flag = flag;
        this.longFlag = longFlag;
    }

    public static ArgumentType getTypeFromFlag(String flag) {
        // for (ArgumentType e : values()) {
        //     if (e.flag.equals(flag) || e.longFlag.equals(flag)) {
        //         return e;
        //     }
        // }

        // return null;
        return BY_FLAG.get(flag);

    }
}