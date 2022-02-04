import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class ArgumentParser {
  private Command command;
  // private String[] rawArgs; // e.g. -d 2020-01-01, -b 23:12, -w 8:30, -r 3
  private LocalDate date;
  private LocalTime bedTime;
  private LocalTime wakeupTime;
  private int restRating;

  public ArgumentParser(String[] args) throws Exception {
    try {
      this.command = Command.getCommandFromString(args[0]);
      // System.out.println(this.command.command);
    } catch (Exception e) {
      throw new Exception("ArgumentParser.java: Could not get command from string. \n" + e.getMessage());
    }

    if (this.command == Command.ADD) {
      boolean restRatingProvided = false;
      boolean nextArgIsType = true;
      ArgumentType argType = null;
      for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
        if (nextArgIsType) {
          argType = ArgumentType.getTypeFromFlag(arg);
          nextArgIsType = false;
          continue;
        }
        if (argType == null) {
          throw new Exception("ArgumentParser.java: Could not get argument type from flag '" + arg + "'\n");
        }
        switch (argType) {
          case BEDTIME:
            try {
              String[] timeParts = arg.split(":");
              int hour = Integer.parseInt(timeParts[0]);
              int minute = Integer.parseInt(timeParts[1]);
              this.bedTime = LocalTime.of(hour, minute);
            } catch (Exception e) {
              // TODO : throw bedtime exception
              System.out.println("ERROR: Wrong bedtime format!");
              System.exit(1);
            }
            break;
          case WAKEUPTIME:
            try {
              String[] timeParts = arg.split(":");
              int hour = Integer.parseInt(timeParts[0]);
              int minute = Integer.parseInt(timeParts[1]);
              this.wakeupTime = LocalTime.of(hour, minute);
            } catch (Exception e) {
              // TODO : throw wakeup time exception
              System.out.println("ERROR: Wrong wake-up time format!");
              System.exit(1);
            }
            break;
          case DATE:
            try {
              String[] dateParts = arg.split("-");
              int year = Integer.parseInt(dateParts[0]);
              int month = Integer.parseInt(dateParts[1]);
              int day = Integer.parseInt(dateParts[2]);
              this.date = LocalDate.of(year, month, day);
            } catch (Exception e) {
              // TODO : throw date exception
              System.out.println("ERROR: Wrong date format!");
              System.exit(1);
            }
            break;
          case RATING:
            try {
              this.restRating = Integer.parseInt(arg);
              restRatingProvided = true;
            } catch (Exception e) {
              // TODO : throw rating exception
              System.out.println("ERROR: Rating must be a number!");
              System.exit(1);
            }
            break;
        }
        nextArgIsType = true;
      }

      // Check if all data is present
      if (this.bedTime == null) {
        throw new Exception("No bedtime provided!");
      }
      if (this.wakeupTime == null) {
        throw new Exception("No wakeup time provided!");
      }
      if (this.date == null) {
        // It's ok to have no date, because we can infer it from the database
        // throw new Exception("No date provided!");
      }
      if (!restRatingProvided) {
        throw new Exception("No rating provided!");
      }
    }

  }

  public Command getCommand() {
    return command;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getBedTime() {
    return bedTime;
  }

  public LocalTime getWakeupTime() {
    return wakeupTime;
  }

  public int getRestRating() {
    return restRating;
  }

}
