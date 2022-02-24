package de.dhbw.charts;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import de.dhbw.sleepTracker.core.Constants;

public class LineChartPanel extends ChartPanel {
    private LineSeries[] series;

    public LineChartPanel(String title, LineSeries[] series) {
        super(title);
        this.series = series;
    }

    private int progressToPixel(int start, int end, double progressPercent) {
        return (int) (start + (end - start) * progressPercent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // this.setBackground(Color.LIGHT_GRAY);

        Graphics2D g2 = (Graphics2D) g;
        // anti-aliasing
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // padding
        final int pl = 100; // left
        final int pr = 20; // right
        final int pt = 20; // top
        final int pb = 40; // bottom
        final int axisWidth = 2;
        final int height = this.getHeight();
        final int width = this.getWidth();
        final int usableWidth = width - pl - pr;
        final int usableHeight = height - pt - pb;
        final int numTicksDateAxis = 10;
        final int xRatingLabelOffset = 20;
        final int xBedtimeLabelOffset = 60;
        final int xDurationLabelOffset = 90;

        // draw x-axis
        g2.setColor(Constants.primaryColor);
        g2.fillRect(pl, height - pb, width - pl - pr, axisWidth);

        // draw y-axis
        g2.setColor(Constants.primaryColor);
        g2.fillRect(pl, pt, axisWidth, height - pt - pb);

        if (this.series.length == 0) {
            return;
        }

        // x-axis ticks with date labels
        // Warning: this assumes that the time series are sorted by date and that all series have the same dates
        this.series[0].paintXAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), numTicksDateAxis, axisWidth);

        // y-axis ticks for each time series
        // bedtime with full hours only
        this.series[0].paintYAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), axisWidth, xBedtimeLabelOffset);

        // duration with full hours only
        this.series[1].paintYAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), axisWidth, xDurationLabelOffset);

        // rest rating
        this.series[2].paintYAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), axisWidth, xRatingLabelOffset);

        // draw all series
        for (LineSeries s: series) {
            s.paintLine(g2, new Rectangle(pl, pt, usableWidth, usableHeight));
        }

    }

    public void setSeries(LineSeries[] series) {
        this.series = series;
        this.repaint();
    }

}


