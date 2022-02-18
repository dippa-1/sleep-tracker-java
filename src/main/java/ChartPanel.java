import java.awt.*;
import java.time.LocalDate;

import javax.swing.JPanel;

class ChartPanel extends JPanel {
    private Database db;

    public ChartPanel(Database db) {
        this.db = db;
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