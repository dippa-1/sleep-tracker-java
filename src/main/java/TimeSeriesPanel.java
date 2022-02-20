import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class TimeSeriesPanel extends JPanel {
  private TimeSeries[] timeSeries;

  public TimeSeriesPanel(TimeSeries[] timeSeries) {
    this.timeSeries = timeSeries;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);


    Graphics2D g2 = (Graphics2D) g;

    // constants
    final int paddingX = 20;
    final int paddingY = 20;
    final int axisWidth = 2;


    final int estimatedLegendTextWidth = 80;
    g2.setColor(Constants.bedtimeColor);
    g2.drawString("Bedtime", paddingX, paddingY);
    g2.setColor(Constants.sleepDurationColor);
    g2.drawString("Sleep duration", getWidth()/2 - estimatedLegendTextWidth, paddingY);
    g2.setColor(Constants.restRatingColor);
    g2.drawString("Rest rating", getWidth() - estimatedLegendTextWidth, paddingY);

    // draw x-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(paddingX, getHeight() - paddingY, getWidth() - paddingX, getHeight() - paddingY - axisWidth);

    // draw y-axis
    g2.fillRect(paddingX, paddingY, paddingX + axisWidth, getHeight() - paddingY);

  }
}