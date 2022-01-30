import java.time.LocalDate;
import java.time.LocalTime;

public class SleepEntry {
  private LocalTime bedTime;
  private LocalTime wakeupTime;
  private LocalDate bedDate;
  private int restRating;

  SleepEntry(String[] args) {
    boolean restRatingProvided = false;

    boolean nextArgIsType = true;
    ArgumentType argType = null;
    for (String arg : args) {
      if (nextArgIsType) {
        argType = ArgumentType.getTypeFromFlag(arg);
        nextArgIsType = false;
        continue;
      }
      if (argType == null) {
        System.out.println("ERROR: Cannot read argument " + arg);
        System.exit(1);
      }
      switch (argType) {
        case BEDTIME:
          try {
            String[] timeParts = arg.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            this.bedTime = LocalTime.of(hour, minute);
            System.out.println("Bedtime: " + this.bedTime.toString());
          } catch (Exception e) {
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
            System.out.println("Wake-up time: " + this.wakeupTime.toString());
          } catch (Exception e) {
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
            this.bedDate = LocalDate.of(year, month, day);
            System.out.println("Date: " + this.bedDate.toString());
          } catch (Exception e) {
            System.out.println("ERROR: Wrong date format!");
            System.exit(1);
          }
          break;
        case RATING:
          try {
            this.restRating = Integer.parseInt(arg);
            System.out.println("Rating: " + arg);
            restRatingProvided = true;
          } catch (Exception e) {
            System.out.println("ERROR: Rating must be a number!");
            System.exit(1);
          }
          break;
      }
      nextArgIsType = true;
    }

    // Check if all data is present
    if (this.bedTime == null) {
      System.out.println("ERROR: No bedtime provided!");
      System.exit(1);
    }
    if (this.wakeupTime == null) {
      System.out.println("ERROR: No wake-up time provided!");
      System.exit(1);
    }
    if (this.bedDate == null) {
      System.out.println("ERROR: No date provided!");
      System.exit(1);
    }
    if (!restRatingProvided) {
      System.out.println("ERROR: No rest-rating provided!");
      System.exit(1);
    }

  }

  public LocalTime getBedTime() {
    return bedTime;
  }

  public LocalTime getWakeupTime() {
    return wakeupTime;
  }

  public LocalDate getBedDate() {
    return bedDate;
  }

  public int getRestRating() {
    return restRating;
  }

}
