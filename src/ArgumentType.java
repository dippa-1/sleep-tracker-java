import java.util.HashMap;
import java.util.Map;

public enum ArgumentType {
  BEDTIME("bed"), WAKEUPTIME("wakeup"), RATING("rating"), DATE("date");

  public final String flag;
  public final String longFlag;
  public final String name;

  private static final Map<String, ArgumentType> BY_FLAG = new HashMap<>();

  static {
    for (ArgumentType e : values()) {
      BY_FLAG.put(e.flag, e);
      BY_FLAG.put(e.longFlag, e);
    }
  }

  private ArgumentType(String name) {
    this.flag = "-" + name.charAt(0);
    this.longFlag = "--" + name;
    this.name = name;
  }

  public static ArgumentType getTypeFromFlag(String flag) {
    // for (ArgumentType e : values()) {
    // if (e.flag.equals(flag) || e.longFlag.equals(flag)) {
    // return e;
    // }
    // }

    // return null;
    return BY_FLAG.get(flag);

  }

  public static ArgumentType getTypeFromName(String name) {
    return getTypeFromFlag("-" + name.charAt(0));
  }
}
