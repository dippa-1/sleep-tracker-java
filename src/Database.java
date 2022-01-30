import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Database {
  private String filepath;
  private ArrayList<SleepEntry> entries;

  Database(String filepath) {
    this.filepath = filepath;
    this.load();
    this.save();
  }

  public void add(SleepEntry entry) {
    // this.entries

  }

  public ArrayList<SleepEntry> getEntries() {
    return this.entries;
  }

  private void sort() {
    if (this.filepath == null)
      return;

  }

  private void save() {
    if (this.filepath == null)
      return;

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

      sb.append("1");
      sb.append(',');
      sb.append("Prashant Ghimire");
      sb.append('\n');

      writer.write(sb.toString());

      System.out.println("done!");

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

  }

  private void load() {
    if (this.filepath == null)
      return;
    this.entries = new ArrayList<SleepEntry>();

  }
}
