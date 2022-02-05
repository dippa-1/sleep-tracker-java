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
    } catch (BedInputException e) {
      // print example of correct bedtime input, then ask user to try again
      System.out.println("Input format for bedtime: HH:MM");
      System.out.println("Example: 23:00 or 08:00 or 8:0");
      System.out.println("Please try again.");
      System.exit(1);
    } catch (WakeInputException e) {
      // print example of correct wakeup input, then ask user to try again
      System.out.println("Input format for wakeup: HH:MM");
      System.out.println("Example: 23:00 or 08:00 or 8:0");
      System.out.println("Please try again.");
      System.exit(1);
    } catch (DateInputException e) {
      // print example of correct date input, then ask user to try again
      System.out.println("Input format for date: YYYY-MM-DD");
      System.out.println("Example: 2020-01-01 or 2020-1-1 or 2020-01-1");
      System.out.println("Please try again.");
      System.exit(1);
    } catch (RatingInputException e) {
      // print example of correct rest rating input, then ask user to try again
      System.out.println("Input format for rest rating: 0-5");
      System.out.println("Example: 3 or 4");
      System.out.println("Please try again.");
      System.exit(1);
    // } catch (Exception e) {
    //   // print example of correct input, then ask user to try again
    //   System.out.println("Input format: add -d <date> -b <bedtime> -w <wakeup> -r <rest rating>");
    //   System.out.println("Example: add -d 2020-01-01 -b 23:00 -w 08:00 -r 4");
    //   // arguments can be in a different order
    //   System.out.println("Example: add -b 23:00 -w 08:00 -r 4 -d 2020-01-01");
    //   System.out.println("Please try again.");
    //   System.exit(1);
    } catch (Exception e) {
      // System.out.println("App.java: Could not parse arguments.\n" + e.getMessage());
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
