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

        // constants
        final int paddingX = 20;
        final int paddingY = 20;
        final int axisWidth = 2;
        final Color primaryColor = Color.BLACK;
        final Color bedtimeColor = Color.BLUE;
        final Color sleepDurationColor = Color.GREEN;
        final Color restRatingColor = Color.RED;

        // draw legend
        final int estimatedLegendTextWidth = 80;
        g2.setColor(bedtimeColor);
        g2.drawString("Bedtime", paddingX, paddingY);
        g2.setColor(sleepDurationColor);
        g2.drawString("Sleep duration", getWidth()/2 - estimatedLegendTextWidth, paddingY);
        g2.setColor(restRatingColor);
        g2.drawString("Rest rating", getWidth() - estimatedLegendTextWidth, paddingY);

        // draw x-axis
        g2.setColor(primaryColor);
        g2.fillRect(paddingX, getHeight() - paddingY, getWidth() - paddingX, getHeight() - paddingY - axisWidth);

        // draw y-axis
        g2.fillRect(paddingX, paddingY, paddingX + axisWidth, getHeight() - paddingY);

        // draw series




        // draw rectangle
        // g.setColor(new Color(0xaa, 0xaa, 0xaa));
        // g.fillRect(1, 1, getWidth() - 2, getHeight() - 1);
    }
}