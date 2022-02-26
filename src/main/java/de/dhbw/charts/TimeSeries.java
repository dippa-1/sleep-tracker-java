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

  private double calculateXProgress(LocalDateTime startDate, Duration totalDuration, LocalDateTime currentDate) {
    return Duration.between(startDate, currentDate).toMillis() / (double)totalDuration.toMillis();
  }

  @Override
  public void paintLine(Graphics g, Rectangle bounds) {
    super.paintLine(g, bounds);
    if (getValues().size() < 2 || getLabels().size() < 2) {
      return;
    }

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(getColor());
    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    final LocalDateTime startDate = getFirstLabel();
    final LocalDateTime endDate = getLastLabel();
    final Duration totalDuration = Duration.between(startDate, endDate);

    // Draw graph lines based on the class of the values.
    // This helps to provide a good solution for the different types of values.
    if (getValues().get(0).getClass() == Integer.class) {
      int maximum = (int) getMaximum();
      int minimum = (int) getMinimum();
      int previousX = bounds.x;
      int previousY = bounds.y + bounds.height - ((int) getValues().get(0) - minimum) * bounds.height / (maximum - minimum);
      for (int i = 1; i < getValues().size(); ++i) {
        final double xProgress = calculateXProgress(startDate, totalDuration, getLabels().get(i));
        int x = (int) (bounds.x + xProgress * bounds.width);
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
        final double xProgress = calculateXProgress(startDate, totalDuration, getLabels().get(i));
        int x = (int) (bounds.x + xProgress * bounds.width);
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
        final double xProgress = calculateXProgress(startDate, totalDuration, getLabels().get(i));
        int x = (int) (bounds.x + xProgress * bounds.width);
        int y = bounds.y + bounds.height - (int)((elementMinutes - minimumMinutes) * bounds.height / minutesRange);
        g2d.drawLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
      }

    }

  }


  @Override
  public void paintXAxisTicks(Graphics g, Rectangle bounds, int tickCount, int tickSize) {
      super.paintXAxisTicks(g, bounds, tickCount, tickSize);

      if (getLabels().size() < 2) {
          return;
      }

      final int estimatedWidthPerMonthLabel = 40; // to kind of center date labels

      Graphics2D g2d = (Graphics2D) g;
        LocalDateTime startDate = (LocalDateTime) getFirstLabel();
        LocalDateTime endDate = (LocalDateTime) getLastLabel();
        Duration timeDiff = Duration.between(startDate, endDate);
        for (int i = 0; i < tickCount; ++i) {
            LocalDateTime date = ((LocalDateTime)getFirstLabel())
                    .plusDays(i * timeDiff.toDays() / (tickCount - 1));
            int x = bounds.x + bounds.width * i / (tickCount - 1);
            g2d.fillRect(x, bounds.y + bounds.height, tickSize, tickSize * 4);

            final int displayYear = date.getYear() % 2000;
            final String displayMonth = date.getMonth().toString().substring(0, 3);
            g2d.drawString(displayMonth + " " + displayYear, x - estimatedWidthPerMonthLabel / 2, bounds.height + bounds.y + 20);
        }
  }

  @Override
  public void paintYAxisTicks(Graphics g, Rectangle bounds, int tickSize, int xLabelOffset) {
      super.paintYAxisTicks(g, bounds, tickSize, xLabelOffset);

      if (getLabels().size() < 2) {
          return;
      }

      Graphics2D g2d = (Graphics2D) g;
      final int yLabelOffset = 5;

      // draw different axis ticks and value labels for different types of values
      if (getValues().get(0).getClass() == Integer.class) {
        final int minimum = (int) getMinimum();
        final int maximum = (int) getMaximum();

        // one tick for every number in reversed order
        g2d.setColor(getColor());
        for (int i = maximum; i >= minimum; --i) {
            int y = bounds.y + bounds.height * (maximum - i) / (maximum - minimum);

            g2d.fillRect(bounds.x - tickSize * 4, y, tickSize * 4, tickSize);
            g2d.drawString(Integer.toString(i), bounds.x - xLabelOffset, y + yLabelOffset);
        }
      } else if (getValues().get(0).getClass() == Float.class) {

        final float minimum = (float) getMinimum();
        final int minimumInt = (int) Math.floor(minimum);
        final float maximum = (float) getMaximum();
        final int maximumInt = (int) Math.floor(maximum + 1f);
        // one tick for every hour with the maximum being at the top
        g2d.setColor(getColor());
        for (int i = maximumInt; i >= minimumInt; --i) {
            int y = bounds.y + bounds.height * (maximumInt - i) / (maximumInt - minimumInt);

            g2d.fillRect(bounds.x - tickSize * 4, y, tickSize * 4, tickSize);
            g2d.drawString(Integer.toString(i % 24) + " h", bounds.x - xLabelOffset, y + yLabelOffset);
        }
      } else if (getValues().get(0).getClass() == LocalTime.class) {
        final LocalTime minTime = (LocalTime) getMinimum();
        final int minHour = minTime.getHour(); // min time might actually be max time. TODO: fix this
        final LocalTime maxTime = (LocalTime) getMaximum();
        final int maxHour = maxTime.getHour() + 1;

        // one tick for every hour with the maximum being at the top
        g2d.setColor(getColor());
        for (int i = maxHour; i >= minHour; --i) {
            int y = bounds.y + bounds.height * (maxHour - i) / (maxHour - minHour);

            g2d.fillRect(bounds.x - tickSize * 4, y, tickSize * 4, tickSize);
            g2d.drawString((i % 24) + ":00", bounds.x - xLabelOffset, y + yLabelOffset);
        }
      }

  }


}
