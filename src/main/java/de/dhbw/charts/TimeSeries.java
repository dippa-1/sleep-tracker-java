package de.dhbw.charts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.*;

public class TimeSeries<Y extends Comparable<Y>> extends LineSeries {

  public TimeSeries(String name, Color color) {
    super(name, color);
    setLabels(new ArrayList<LocalDateTime>());
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

  @Override
  public void paintLine(Graphics g, Rectangle bounds) {
    if (getValues().size() == 0) {
      return;
    }

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(getColor());
    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    // Draw graph lines based on the class of the values.
    // This helps to provide a good solution for the different types of values.
    if (getValues().get(0).getClass() == Integer.class) {
      int maximum = (int) getMaximum();
      int minimum = (int) getMinimum();
      int previousX = bounds.x;
      int previousY = bounds.y + bounds.height - ((int) getValues().get(0) - minimum) * bounds.height / (maximum - minimum);
      for (int i = 1; i < getValues().size(); ++i) {
        int x = bounds.x + i * bounds.width / getValues().size();
        int y = bounds.y + bounds.height - ((int) getValues().get(i) - minimum) * bounds.height / (maximum - minimum);
        g2d.drawLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
      }
    } else if (getValues().get(0).getClass() == Float.class) {
      float maximum = (float) getMaximum();
      float minimum = (float) getMinimum();
      int previousX = bounds.x;
      int previousY = bounds.y + bounds.height - (int)(((float) getValues().get(0) - minimum) * bounds.height / (maximum - minimum));
      for (int i = 1; i < getValues().size(); ++i) {
        int x = bounds.x + i * bounds.width / getValues().size();
        int y = bounds.y + bounds.height - (int)(((float) getValues().get(i) - minimum) * bounds.height / (maximum - minimum));
        g2d.drawLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
      }
    } 
    else if (getValues().get(0).getClass() == LocalTime.class) {
      final LocalTime maximum = (LocalTime) getMaximum();
      final long maximumMinutes = maximum.getMinute() + maximum.getHour() * 60;
      final LocalTime minimum = (LocalTime) getMinimum();
      final long minimumMinutes = minimum.getMinute() + minimum.getHour() * 60;
      long minutesRange = maximumMinutes - minimumMinutes;
      if (minutesRange < 0) {
        minutesRange += 24 * 60;
      }

      int previousX = bounds.x;
      LocalTime element = (LocalTime) getValues().get(0);
      long elementMinutes = element.getMinute() + element.getHour() * 60;
      int previousY = bounds.y + bounds.height - (int)((elementMinutes - minimumMinutes) * bounds.height / minutesRange);
      for (int i = 1; i < getValues().size(); ++i) {
        element = (LocalTime) getValues().get(i);
        elementMinutes = element.getMinute() + element.getHour() * 60;
        int x = bounds.x + i * bounds.width / getValues().size();
        int y = bounds.y + bounds.height - (int)((elementMinutes - minimumMinutes) * bounds.height / minutesRange);
        g2d.drawLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
      }

    }

  }


}
