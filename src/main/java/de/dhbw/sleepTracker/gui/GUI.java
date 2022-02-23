package de.dhbw.sleepTracker.gui;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dhbw.charts.ChartPanel;
import de.dhbw.charts.LineChartPanel;
import de.dhbw.charts.TimeSeries;
import de.dhbw.charts._ChartPanel;
import de.dhbw.sleepTracker.core.Database;
import de.dhbw.sleepTracker.core.SleepEntry;

public class GUI extends JFrame {
    public static void main(String[] args) {
        GUI gui = new GUI();
        Database db = new Database("sleep-data");

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Sleep Optimizer");
        gui.setBounds(280, 120, 800, 600);

        // add parent container for horizontal layout
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        gui.add(container);

        TimeSeries<LocalTime> bedtimes = new TimeSeries<LocalTime>("Bedtime", new Color(0xef, 0x44, 0x44));
        TimeSeries<Float> durations = new TimeSeries<Float>("Duration", new Color(0x22, 0xc5, 0x5e));
        TimeSeries<Integer> ratings = new TimeSeries<Integer>("Rating", new Color(0x06, 0xb6, 0xd4));

        // insert entries into time series
        for (SleepEntry entry : db.getEntries()) {
            LocalDateTime date = LocalDateTime.of(entry.getDate(), LocalTime.MIN);
            bedtimes.insert(date, entry.getBedTime());
            durations.insert(date, (float) entry.getSleepDuration().toSecondOfDay() / 3600f);
            ratings.insert(date, entry.getRestRating());
        }
        TimeSeries[] series = {bedtimes, durations, ratings};

        LineChartPanel chartPanel = new LineChartPanel("Sleep History", series);
        NewEntryPanel newEntryPanel = new NewEntryPanel(db);
        // newEntryPanel.addNewEntryListener(chartPanel);
        newEntryPanel.setMaximumSize(new Dimension(gui.getWidth(), 60));

        container.add(newEntryPanel);
        container.add(chartPanel);

        gui.setVisible(true);
    }

}
