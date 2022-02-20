import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    final int estimatedWidthPerMonthLabel = 25; // to kind of center date labels

    // draw x-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(pl, height - pb, width - pl - pr, axisWidth);
    // ticks with date labels
    LocalDateTime startDate = LocalDateTime.of(this.timeSeries[0].getStartDate(), LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(this.timeSeries[0].getEndDate(), LocalTime.MIN);
    Duration timeDiff = Duration.between(startDate, endDate);
    for (int i = 0; i < numTicksDateAxis; ++i) {
      LocalDate date = this.timeSeries[0].getStartDate().plusDays(i * timeDiff.toDays() / (numTicksDateAxis - 1));
      int x = pl + usableWidth * i / (numTicksDateAxis - 1);
      g2.fillRect(x, height - pb, axisWidth, axisWidth*4);

      g2.drawString(date.getMonth().toString().substring(0, 3), x - estimatedWidthPerMonthLabel / 2, height - pb + 20);
    }

    // draw y-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(pl, pt, axisWidth, height - pt - pb);

  }
}