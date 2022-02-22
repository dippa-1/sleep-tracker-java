import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ChartPanel extends JPanel {
    // private Database db;
    private TimeSeries[] timeSeries;

    public ChartPanel(ArrayList<SleepEntry> entries) {
        // this.db = db;

        this.timeSeries = new TimeSeries[3];
        this.timeSeries[0] = new TimeSeries<LocalTime>("Bedtime", Color.BLUE);
        this.timeSeries[1] = new TimeSeries<LocalTime>("Sleep duration", Color.RED);
        this.timeSeries[2] = new TimeSeries<Integer>("Rest rating", Color.GREEN);

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


        // Vertical alignment: title&legend -> actual chart
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titleAndLegend = new JPanel();
        titleAndLegend.setLayout(new BoxLayout(titleAndLegend, BoxLayout.X_AXIS));
        JLabel historyLabel = new JLabel("History");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleAndLegend.add(new JLabel("History"));
        titleAndLegend.add(new JLabel("Bedtime"));
        titleAndLegend.add(new JLabel("Sleep duration"));
        titleAndLegend.add(new JLabel("Rest rating"));
        this.add(titleAndLegend);

        // add timeseries panel
        this.add(new TimeSeriesPanel(this.timeSeries));


    }

    // This paints a legend, an x-axis (dates) and a y-axis with three series (rest rating, sleep duration and )
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}