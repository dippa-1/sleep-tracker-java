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
    for (int i = 0; i < entries.size(); ++i) {
      if (entry.getDate().equals(entries.get(i).getDate())) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entry already exists for " + entry.getDate() + ". Overwrite? (y/n) ");
        String input = scanner.nextLine();
        if (input.equals("y")) {
          entries.set(i, entry);
          this.save();
        }
        scanner.close();
        this.save();
        return;
      }
    }
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
}
