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
    try {
      ArgumentParser argParser = new ArgumentParser(args);

      switch (argParser.getCommand()) {
        case ADD:
          LocalDate date;
          if (argParser.getDate() == null) {
            ArrayList<SleepEntry> entries = db.getEntries();
            int entriesSize = entries.size();
            String inferredDateString;
            if (entriesSize == 0) {
              // Entry is probably for yesterday
              inferredDateString = LocalDate.now().minusDays(1).toString();
            } else {
              // infer date from last entry
              LocalDate lastDate = entries.get(entriesSize - 1).getDate();
              inferredDateString = lastDate.plusDays(1).toString();
            }
            date = LocalDate.parse(inferredDateString);
          } else {
            date = argParser.getDate();
          }
          SleepEntry entry = new SleepEntry(date, argParser.getBedTime(), argParser.getWakeupTime(), argParser.getRestRating());
          db.add(entry);
          break;
        case LIST:
          System.out.println(db);
          break;
        case HELP:
          printHelp();
          break;
        case STATS:
          db.printStats();
          break;
      }
    } catch (Exception e) {
      System.out.println("App.java: Could not parse arguments.\n" + e.getMessage());
      printHelp();
      System.exit(1);
    }

  }

  private static void printHelp() {
    System.out.println(
        """
        
            SLEEP OPTIMIZER USAGE:
            Add entry: java SleepOptimizer add [-d --date YYYY:MM:dd] [-b --bed HH:MM] [-w --wakeup HH:MM] [-r --rating 0 to 5]
              Example: java SleepOptimizer add -d 2021-01-30 -b 22:55 -w 9:05 -r 3
            """);
  }
}
