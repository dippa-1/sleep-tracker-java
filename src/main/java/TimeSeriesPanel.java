import java.awt.Color;
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

    this.setBackground(Color.LIGHT_GRAY);


    Graphics2D g2 = (Graphics2D) g;

    // constants
    final int paddingX = 20;
    final int paddingY = 20;
    final int axisWidth = 2;
    final int height = this.getHeight();
    final int width = this.getWidth();

    // draw x-axis
    g2.setColor(Constants.primaryColor);
    g2.fillRect(paddingX, height - paddingY, width - paddingX, height - paddingY - 1);

    // draw y-axis
    // g2.fillRect(paddingX, paddingY, paddingX + axisWidth, getHeight() - paddingY);

  }
}