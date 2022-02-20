import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;

import javax.swing.JPanel;

class ChartPanel extends JPanel {
    // private Database db;
    private TimeSeries[] timeSeries;

    public ChartPanel(ArrayList<SleepEntry> entries) {
        // this.db = db;

        this.timeSeries = new TimeSeries[3];
        this.timeSeries[0] = new TimeSeries<LocalTime>("Bedtime");
        this.timeSeries[1] = new TimeSeries<LocalTime>("Sleep duration");
        this.timeSeries[2] = new TimeSeries<Integer>("Rest rating");

        // get entries
        // ArrayList<SleepEntry> entries = db.getEntries();

        // insert entries to time series
        for (SleepEntry entry : entries) {
            LocalDate date = entry.getDate();
            this.timeSeries[0].insert(date, entry.getBedTime());
            this.timeSeries[1].insert(date, entry.getSleepDuration());
            this.timeSeries[2].insert(date, entry.getRestRating());
        }

        // print series
        System.out.println("Series: " + this.timeSeries[0].getName());
        for (LocalDate date : (ArrayList<LocalDate>) this.timeSeries[0].getDates()) {
            System.out.println(date);
        }
        for (LocalTime bedtime : (ArrayList<LocalTime>) this.timeSeries[0].getValues()) {
            System.out.println(bedtime);
        }
        for (LocalTime sleepDuration : (ArrayList<LocalTime>) this.timeSeries[1].getValues()) {
            System.out.println(sleepDuration);
        }
        for (Integer restRating : (ArrayList<Integer>) this.timeSeries[2].getValues()) {
            System.out.println(restRating);
        }

    }

    // This paints a legend, an x-axis (dates) and a y-axis with three series (rest rating, sleep duration and )
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // draw legend
        g2.setColor(Color.BLACK);
        g2.drawString("Rest rating", 10, 20);
        g2.drawString("Sleep duration", 10, 40);

        // draw x-axis
        g2.setColor(Color.BLACK);
        g2.drawLine(10, getHeight() - 10, getWidth() - 10, getHeight() - 10);

        // draw y-axis
        g2.drawLine(10, 10, 10, getHeight() - 10);

        // draw series




        // draw rectangle
        // g.setColor(new Color(0xaa, 0xaa, 0xaa));
        // g.fillRect(1, 1, getWidth() - 2, getHeight() - 1);
    }
}