import java.time.LocalDate;
import java.time.LocalTime;

public class SleepEntry {
  private LocalDate date; // the date is not the actual date, when the user went to bed after 24:00. It then is the date of the day before for consistency
  private LocalTime bedTime;
  private LocalTime wakeupTime;
  private int restRating;

  SleepEntry(LocalDate date, LocalTime bedTime, LocalTime wakeupTime, int restRating) {
    this.date = date;
    this.bedTime = bedTime;
    this.wakeupTime = wakeupTime;
    this.restRating = restRating;
  }

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
            this.date = LocalDate.of(year, month, day);
            System.out.println("Date: " + this.date.toString());
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
    if (this.date == null) {
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

  public LocalDate getDate() {
    return date;
  }

  public int getRestRating() {
    return restRating;
  }

  public LocalTime getSleepDuration() {
    return this.wakeupTime.minusHours(this.bedTime.getHour()).minusMinutes(this.bedTime.getMinute());
  }

  public String toString() {
    return "Date: " + this.date.toString() + " | Bedtime: " + this.bedTime.toString() + " | Wakeup: " + this.wakeupTime.toString() + " | Rating: " + this.restRating;
  }

}
