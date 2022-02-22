import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JPanel;

public class TimeSeriesPanel extends JPanel {
  private TimeSeries[] timeSeries;

  public TimeSeriesPanel(TimeSeries[] timeSeries) {
    this.timeSeries = timeSeries;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.setBackground(Color.LIGHT_GRAY);


    Graphics2D g2 = (Graphics2D) g;
    // anti-aliasing
    g2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // padding
    final int pl = 100; // left
    final int pr = 20; // right
    final int pt = 20; // top
    final int pb = 40; // bottom
    final int axisWidth = 2;
    final int height = this.getHeight();
    final int width = this.getWidth();
    final int usableWidth = width - pl - pr;
    final int usableHeight = height - pt - pb;
    final int numTicksDateAxis = 10;
    final int estimatedWidthPerMonthLabel = 40; // to kind of center date labels
    final int yLabelOffset = 5;
    final int xRatingLabelOffset = 20;
    final int xBedtimeLabelOffset = 60;
    final int xDurationLabelOffset = 90;

    // draw x-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(pl, height - pb, width - pl - pr, axisWidth);

    // draw y-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(pl, pt, axisWidth, height - pt - pb);

    // sadly, at this point, we have to use a fixed amount of time series because we already know the amount
    if (this.timeSeries.length != 3) {
      return;
    }

    // x-axis ticks with date labels
    LocalDateTime startDate = LocalDateTime.of(this.timeSeries[0].getStartDate(), LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(this.timeSeries[0].getEndDate(), LocalTime.MIN);
    Duration timeDiff = Duration.between(startDate, endDate);
    for (int i = 0; i < numTicksDateAxis; ++i) {
      LocalDate date = this.timeSeries[0].getStartDate().plusDays(i * timeDiff.toDays() / (numTicksDateAxis - 1));
      int x = pl + usableWidth * i / (numTicksDateAxis - 1);
      g2.fillRect(x, height - pb, axisWidth, axisWidth*4);

      final int displayYear = date.getYear() % 2000;
      final String displayMonth = date.getMonth().toString().substring(0, 3);
      g2.drawString(displayMonth + " " + displayYear, x - estimatedWidthPerMonthLabel / 2, height - pb + 20);
    }

    // y-axis ticks for each time series
    // rest rating
    final int minRating = (int) this.timeSeries[2].getMinimum();
    final int maxRating = (int) this.timeSeries[2].getMaximum();
    // one tick for every number in reversed order
    g2.setColor(this.timeSeries[2].getColor());
    for (int i = maxRating; i >= minRating; --i) {
      int y = pt + usableHeight * (maxRating - i) / (maxRating - minRating);

      g2.fillRect(pl - axisWidth*4, y, axisWidth*4, axisWidth);
      g2.drawString(Integer.toString(i), pl - xRatingLabelOffset, y + yLabelOffset);
    }

    // bedtime with full hours only
    final LocalTime minBedtime = (LocalTime) this.timeSeries[0].getMinimum();
    final int minHour = minBedtime.getHour();
    final LocalTime maxBedtime = (LocalTime) this.timeSeries[0].getMaximum();
    final int maxHour = maxBedtime.getHour() + 1;
    System.out.println(minBedtime + " " + maxBedtime);
    System.out.println(minHour + " " + maxHour);
    // one tick for every hour with the maximum being at the top
    g2.setColor(this.timeSeries[0].getColor());
    for (int i = maxHour; i >= minHour; --i) {
      int y = pt + usableHeight * (maxHour - i) / (maxHour - minHour);

      g2.fillRect(pl - axisWidth*4, y, axisWidth*4, axisWidth);
      g2.drawString(Integer.toString(i % 24) + ":00", pl - xBedtimeLabelOffset, y + yLabelOffset);
    }

    // duration with full hours only
    final LocalTime minDuration = (LocalTime) this.timeSeries[1].getMinimum();
    final int minHourDuration = minDuration.getHour();
    final LocalTime maxDuration = (LocalTime) this.timeSeries[1].getMaximum();
    final int maxHourDuration = maxDuration.getHour() + 1;
    System.out.println(minDuration + " " + maxDuration);
    System.out.println(minHourDuration + " " + maxHourDuration);
    // one tick for every hour with the maximum being at the top
    g2.setColor(this.timeSeries[1].getColor());
    for (int i = maxHourDuration; i >= minHourDuration; --i) {
      int y = pt + usableHeight * (maxHourDuration - i) / (maxHourDuration - minHourDuration);

      g2.fillRect(pl - axisWidth*4, y, axisWidth*4, axisWidth);
      g2.drawString(Integer.toString(i % 24) + " h", pl - xDurationLabelOffset, y + yLabelOffset);
    }






  }
}