package de.dhbw.charts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.awt.Color;

public class TimeSeries<Y extends Comparable<Y>> extends LineSeries {

  public TimeSeries(String name, Color color) {
    super(name, color);
    labels = new ArrayList<LocalDateTime>();
  }

  @Override
  public LocalDateTime getFirstLabel() {
    return (LocalDateTime) super.getFirstLabel();
  }

  @Override
  public LocalDateTime getLastLabel() {
    return (LocalDateTime) super.getLastLabel();
  }

  @Override
  public ArrayList<LocalDateTime> getLabels() {
    return (ArrayList<LocalDateTime>) super.getLabels();
  }


}
