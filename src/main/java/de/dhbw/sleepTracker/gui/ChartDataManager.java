package de.dhbw.sleepTracker.gui;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.Color;

import de.dhbw.charts.LineChartPanel;
import de.dhbw.charts.TimeSeries;
import de.dhbw.sleepTracker.core.Database;
import de.dhbw.sleepTracker.core.SleepEntry;

public class ChartDataManager implements NewEntryListener {
    TimeSeries<LocalTime> bedtimes;
    TimeSeries<Float> durations;
    TimeSeries<Integer> ratings;
    LineChartPanel chartPanel;

    public ChartDataManager(ArrayList<SleepEntry> entries, LineChartPanel chartPanel) {
        this.chartPanel = chartPanel;
        bedtimes = new TimeSeries<LocalTime>("Bedtime", new Color(0xef, 0x44, 0x44));
        durations = new TimeSeries<Float>("Duration", new Color(0x22, 0xc5, 0x5e));
        ratings = new TimeSeries<Integer>("Rating", new Color(0x06, 0xb6, 0xd4));
        entryAdded(entries);
    }

    @Override
    public void entryAdded(ArrayList<SleepEntry> entries) {
        for (SleepEntry entry : entries) {
            LocalDateTime date = LocalDateTime.of(entry.getDate(), LocalTime.MIN);
            bedtimes.insert(date, entry.getBedTime());
            durations.insert(date, (float) entry.getSleepDuration().toSecondOfDay() / 3600f);
            ratings.insert(date, entry.getRestRating());
        }
        chartPanel.setSeries(new TimeSeries[] { bedtimes, durations, ratings });
    }
}
