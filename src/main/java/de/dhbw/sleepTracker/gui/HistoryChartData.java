package de.dhbw.sleepTracker.gui;

import java.time.LocalTime;
import java.awt.Color;

import de.dhbw.charts.TimeSeries;

public class HistoryChartData {
    private TimeSeries<Integer> ratings;
    private TimeSeries<LocalTime> bedtimes;
    private TimeSeries<Float> durations;

    public HistoryChartData() {
        ratings = new TimeSeries<Integer>("Rating", new Color(0x06, 0xb6, 0xd4));
        bedtimes = new TimeSeries<LocalTime>("Bedtime", new Color(0xef, 0x44, 0x44));
        durations = new TimeSeries<Float>("Duration", new Color(0x22, 0xc5, 0x5e));
    }

    public TimeSeries<Integer> getRatings() {
        return ratings;
    }

    public TimeSeries<LocalTime> getBedtimes() {
        return bedtimes;
    }

    public TimeSeries<Float> getDurations() {
        return durations;
    }
}
