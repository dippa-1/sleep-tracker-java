import java.time.LocalDate;
import java.util.ArrayList;

public class TimeSeries<T extends Comparable<T>> {
  private String name;
  private ArrayList<LocalDate> dates;
  private ArrayList<T> values;

  public TimeSeries(String name) {
    this.name = name;
    this.dates = new ArrayList<LocalDate>();
    this.values = new ArrayList<T>();
  }

  public String getName() {
    return this.name;
  }

  public ArrayList<LocalDate> getDates() {
    return this.dates;
  }

  public ArrayList<T> getValues() {
    return this.values;
  }

  public LocalDate getStartDate() {
    return this.dates.get(0);
  }

  public LocalDate getEndDate() {
    return this.dates.get(this.dates.size() - 1);
  }

  public T getMaximum() {
    T max = this.values.get(0);
    for (int i = 1; i < this.values.size(); ++i) {
      if (this.values.get(i).compareTo(max) > 0) {
        max = this.values.get(i);
      }
    }
    return max;
  }

  public T getMinimum() {
    T min = this.values.get(0);
    for (int i = 1; i < this.values.size(); ++i) {
      if (this.values.get(i).compareTo(min) < 0) {
        min = this.values.get(i);
      }
    }
    return min;
  }

  // inserts the next datapoint sorted
  public void insert(LocalDate date, T value) {
    int i = 0;
    while (i < this.dates.size() && this.dates.get(i).compareTo(date) < 0) {
      ++i;
    }
    this.dates.add(i, date);
    this.values.add(i, value);
  }

}
