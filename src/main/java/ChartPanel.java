import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JPanel;

class ChartPanel extends JPanel {
    private Database db;
    private TimeSeries[] timeSeries;

    public ChartPanel(Database db) {
        this.db = db;

        this.timeSeries = new TimeSeries[3];
        this.timeSeries[0] = new TimeSeries<LocalTime>("Bedtime", db.g, values)
    }

    // This paints a legend, an x-axis (dates) and a y-axis with three  (rest rating, sleep duration and )
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



        // draw rectangle
        g.setColor(new Color(0xaa, 0xaa, 0xaa));
        g.fillRect(1, 1, getWidth() - 2, getHeight() - 1);
    }
}