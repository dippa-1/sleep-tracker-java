import java.time.LocalDate;
import java.util.ArrayList;

public class TimeSeries<T extends Comparable<T>> {
  private String name;
  private ArrayList<LocalDate> dates;
  private ArrayList<T> values;

  public TimeSeries(String name, ArrayList<LocalDate> dates, ArrayList<T> values) throws IllegalArgumentException {
    this.name = name;

    if (dates.size() != values.size()) {
      throw new IllegalArgumentException("dates and values should have the same size");
    }

    // each date belong to one value. Both should be sorted by date in ascending order
    for (int i = 0; i < dates.size(); ++i) {
      for (int j = i + 1; j < dates.size(); ++j) {
        if (dates.get(i).isAfter(dates.get(j))) {
          LocalDate tmpDate = dates.get(i);
          dates.set(i, dates.get(j));
          dates.set(j, tmpDate);

          T tmpValue = values.get(i);
          values.set(i, values.get(j));
          values.set(j, tmpValue);
        }
      }
    }

    this.dates = dates;
    this.values = values;
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

}
