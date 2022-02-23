package de.dhbw.sleepTracker.core;
import java.util.HashMap;
import java.util.Map;

public enum Command {
  ADD("add"), LIST("list"), HELP("help"), STATS("stats");

  public final String command;

  // Cache commands to access quickly by string
  private static final Map<String, Command> BY_STRING = new HashMap<>();
  static {
    for (Command c : values()) {
      BY_STRING.put(c.command, c);
    }
  }

  private Command(String command) {
    this.command = command;
  }

  public static Command getCommandFromString(String command) throws Exception {
    Command cmd = BY_STRING.get(command);
    if (cmd == null) {
      throw new Exception("Command.java: '" + command + "' not found");
    }
    return cmd;
  }

}
