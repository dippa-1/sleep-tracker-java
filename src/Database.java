import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Database {
  private String filepath;
  private ArrayList<SleepEntry> entries;

  Database(String filepath) {
    this.filepath = filepath;
    this.load();
  }

  public void add(SleepEntry entry) {
    System.out.println("Input: " + entry);

    for (int i = 0; i < entries.size(); ++i) {
      if (entry.getDate().equals(entries.get(i).getDate())) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entry already exists for " + entry.getDate() + ". Overwrite? [Y/n] ");
        String input = scanner.nextLine();
        if (input.toLowerCase().equals("y") || input.isEmpty()) {
          entries.set(i, entry);
          this.save();
          System.out.println("Overwritten.");
        } else {
          System.out.println("Aborted.");
        }
        scanner.close();
        return;
      }
    }
    // entry doesn't exist yet
    this.entries.add(entry);
    this.save();
  }

  public ArrayList<SleepEntry> getEntries() {
    return this.entries;
  }

  // not needed actually
  private void sort() {
    if (this.filepath == null) {
      return;
    }

  }

  private void save() {
    if (this.filepath == null) {
      return;
    }

    try (PrintWriter writer = new PrintWriter(this.filepath + ".csv")) {

      StringBuilder sb = new StringBuilder();
      sb.append("date");
      sb.append(',');
      sb.append("bed");
      sb.append(',');
      sb.append("wakeup");
      sb.append(',');
      sb.append("rating");
      sb.append('\n');

      for (SleepEntry e : entries) {
        sb.append(e.getDate().toString());
        sb.append(',');
        sb.append(e.getBedTime().toString());
        sb.append(',');
        sb.append(e.getWakeupTime().toString());
        sb.append(',');
        sb.append(e.getRestRating());
        sb.append('\n');
      }

      writer.write(sb.toString());

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  private void load() {
    if (this.filepath == null) {
      return;
    }
    this.entries = new ArrayList<SleepEntry>();

    ArrayList<ArrayList<String>> records = new ArrayList<>();
    Map<String, Integer> csvHeaderIndexes = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(this.filepath + ".csv"))) {
      String line;
      for (int lineNumber = 1; (line = br.readLine()) != null; ++lineNumber) {
        String[] values = line.split(",");
        if (lineNumber == 1) {
          // just in case a human created a new csv file and switched the order.
          for (int i = 0; i < values.length; ++i) {
            csvHeaderIndexes.put(values[i], i);
          }
          continue;
        }
        if (values.length != csvHeaderIndexes.size())
          continue;
        records.add(new ArrayList<>(Arrays.asList(values)));
        try {
          LocalDate date = LocalDate.parse(values[csvHeaderIndexes.get("date")]);
          LocalTime bedTime = LocalTime.parse(values[csvHeaderIndexes.get("bed")]);
          LocalTime wakeupTime = LocalTime.parse(values[csvHeaderIndexes.get("wakeup")]);
          int restRating = Integer.parseInt(values[csvHeaderIndexes.get("rating")]);
          SleepEntry entry = new SleepEntry(date, bedTime, wakeupTime, restRating);
          this.entries.add(entry);
        } catch (Exception e) {
          System.out.println("ERROR: Could not parse database.");
          System.out.println("Line buffer: " + line);
          System.out.println("Message: " + e.getMessage());
          System.exit(1);
        }
      }
    } catch (Exception e) {
      // Could not read database, maybe it's empty or doesn't exist, so continue.
    }

  }

  public String toString() {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < this.entries.size(); ++i) {
      string.append(this.entries.get(i).toString());
      if (i == this.entries.size() - 1) break;
      string.append('\n');
    }
    return string.toString();
  }

  public void printStats() {
    // print general entries stats. assumes that the entries are sorted by date
    int entriesSize = entries.size();
    if (entriesSize == 0) {
      System.out.println("No entries");
      return;
    }
    LocalDate firstDate = entries.get(0).getDate();
    LocalDate lastDate = entries.get(entriesSize - 1).getDate();
    System.out.format("%40s: %s\n", "First entry", firstDate);
    System.out.format("%40s: %s\n", "Last entry", lastDate);
    System.out.format("%40s: %d\n", "Total entries", entriesSize);
    System.out.format("%40s: %d\n", "Total days", lastDate.getDayOfYear() - firstDate.getDayOfYear() + 1);

    // calculate mean bedtime
    LocalTime meanBedTime;
    if (entriesSize == 0) {
      meanBedTime = LocalTime.of(0, 0);
    } else {
      int totalSeconds = 0;
      for (SleepEntry e : entries) {
        totalSeconds += e.getBedTime().toSecondOfDay();
      }
      totalSeconds /= entriesSize;
      int hours = totalSeconds / 3600;
      int minutes = (totalSeconds % 3600) / 60;
      int seconds = totalSeconds % 60;
      meanBedTime = LocalTime.of(hours, minutes, seconds);
    }
    // print
    System.out.format("%40s: %s\n", "Mean bedtime", meanBedTime);

    // calculate mean wakeup time
    LocalTime meanWakeupTime;
    if (entriesSize == 0) {
      meanWakeupTime = LocalTime.of(0, 0);
    } else {
      int totalSeconds = 0;
      for (SleepEntry e : entries) {
        totalSeconds += e.getWakeupTime().toSecondOfDay();
      }
      totalSeconds /= entriesSize;
      int hours = totalSeconds / 3600;
      int minutes = (totalSeconds % 3600) / 60;
      int seconds = totalSeconds % 60;
      meanWakeupTime = LocalTime.of(hours, minutes, seconds);
    }
    // print
    System.out.format("%40s: %s\n", "Mean wakeup time", meanWakeupTime);

    // calculate mean rest rating
    double meanRestRating;
    if (entriesSize == 0) {
      meanRestRating = 0;
    } else {
      double totalRestRating = 0;
      for (SleepEntry e : entries) {
        totalRestRating += e.getRestRating();
      }
      meanRestRating = totalRestRating / entriesSize;
    }
    // print
    System.out.format("%40s: %.2f\n", "Mean rest rating", meanRestRating);

    // calculate mean sleep duration
    LocalTime meanSleepDuration;
    if (entriesSize == 0) {
      meanSleepDuration = LocalTime.of(0, 0);
    } else {
      int totalSeconds = 0;
      for (SleepEntry e : entries) {
        totalSeconds += e.getSleepDuration().toSecondOfDay();
      }
      totalSeconds /= entriesSize;
      int hours = totalSeconds / 3600;
      int minutes = (totalSeconds % 3600) / 60;
      int seconds = totalSeconds % 60;
      meanSleepDuration = LocalTime.of(hours, minutes, seconds);
    }
    // print
    System.out.format("%40s: %s\n", "Mean sleep duration", meanSleepDuration);

    // count the longest consecutive entries with a rating of 4 and above
    int longestConsecutive = 0;
    int currentConsecutive = 0;
    for (SleepEntry e : entries) {
      if (e.getRestRating() >= 4) {
        ++currentConsecutive;
      } else {
        if (currentConsecutive > longestConsecutive) {
          longestConsecutive = currentConsecutive;
        }
        currentConsecutive = 0;
      }
    }
    if (currentConsecutive > longestConsecutive) {
      longestConsecutive = currentConsecutive;
    }
    // print
    System.out.format("%40s: %d\n", "Longest awake streak (4 and better)", longestConsecutive);

    // count the longest consecutive entries with a rating of 2 and below
    longestConsecutive = 0;
    currentConsecutive = 0;
    for (SleepEntry e : entries) {
      if (e.getRestRating() <= 2) {
        ++currentConsecutive;
      } else {
        if (currentConsecutive > longestConsecutive) {
          longestConsecutive = currentConsecutive;
        }
        currentConsecutive = 0;
      }
    }
    if (currentConsecutive > longestConsecutive) {
      longestConsecutive = currentConsecutive;
    }
    // print
    System.out.format("%40s: %d\n", "Longest slump (2 and below)", longestConsecutive);
  }

}
