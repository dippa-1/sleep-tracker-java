import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      printHelp();
      System.exit(0);
    }

    Database db = new Database("sleep-data");
    switch (args[0]) {
      case "add":
        boolean dateArgumentExists = false;
        for (String arg : args) {
          if (arg.equals("-d") || arg.equals("--date")) {
            dateArgumentExists = true;
            break;
          }
        }
        if (!dateArgumentExists) {
          // infer date
          ArrayList<SleepEntry> entries = db.getEntries();
          int entriesSize = entries.size();
          String inferredDateString;
          if (entriesSize == 0) {
            inferredDateString = LocalDate.now().toString();
          } else {
            LocalDate lastDate = entries.get(entriesSize - 1).getDate();
            inferredDateString = lastDate.plusDays(1).toString();
          }
          String[] oldArgs = args;
          args = new String[args.length + 2];
          for (int i = 0; i < oldArgs.length; ++i) {
            args[i] = oldArgs[i];
          }
          args[args.length - 2] = "-d";
          args[args.length - 1] = inferredDateString;
        }
        SleepEntry entry = new SleepEntry(Arrays.copyOfRange(args, 1, args.length));
        db.add(entry);
        break;
      case "list":
        System.out.println(db);
        break;
      case "help":
        printHelp();
        break;
      case "stats":
        db.printStats();
        break;
      default:
        System.out.println("Command " + args[0] + " not found.");
        break;
    }
  }

  private static void printHelp() {
    System.out.println(
        """
            Sleep Optimizer: No parameters given
            USAGE:
            Add entry: java SleepOptimizer add [-d --date YYYY:MM:dd] [-b --bed HH:MM] [-w --wakeup HH:MM] [-r --rating 0 to 5]
              Example: java SleepOptimizer add -d 2021-01-30 -b 22:55 -w 9:05 -r 3
            """);
  }
}
