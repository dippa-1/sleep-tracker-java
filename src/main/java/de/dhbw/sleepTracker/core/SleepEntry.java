package de.dhbw.sleepTracker.core;
import java.time.LocalDate;
import java.time.LocalTime;

public class SleepEntry {
  private LocalDate date; // the date is not the actual date, when the user went to bed after 24:00. It then is the date of the day before for consistency
  private LocalTime bedTime;
  private LocalTime wakeupTime;
  private int restRating;

  public SleepEntry(LocalDate date, LocalTime bedTime, LocalTime wakeupTime, int restRating) {
    this.date = date;
    this.bedTime = bedTime;
    this.wakeupTime = wakeupTime;
    this.restRating = restRating;
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
