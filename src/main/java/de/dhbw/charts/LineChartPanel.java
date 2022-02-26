package de.dhbw.charts;

import java.awt.*;

import de.dhbw.sleepTracker.core.Constants;

public class LineChartPanel extends ChartPanel {
    private LineSeries[] series;

    public LineChartPanel(String title, LineSeries[] series) {
        super(title);
        this.series = series;
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
        final int pr = 30; // right
        final int pt = 40; // top
        final int pb = 30; // bottom
        final int axisWidth = 2;
        final int height = this.getHeight();
        final int width = this.getWidth();
        final int usableWidth = width - pl - pr;
        final int usableHeight = height - pt - pb;
        final int numTicksDateAxis = 10;
        final int xLabelOffset = 35;
        final int xLabelOffsetIncrease = 25;
        final int xLegendOffset = (getWidth() - (this.series.length - 1) * xLabelOffsetIncrease) / 2;
        final int xLegendOffsetIncrease = 80;
        final int yLegendOffset = 10;

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
        // Warning: this assumes that the time series are sorted by date and that all
        // series have the same dates
        this.series[0].paintXAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), numTicksDateAxis,
                axisWidth);

        // draw all axis, lines and legends
        for (int i = 0; i < this.series.length; ++i) {
            this.series[i].paintLegend(g, xLegendOffset + xLegendOffsetIncrease * i, yLegendOffset);
            this.series[i].paintYAxisTicks(g, new Rectangle(pl, pt, usableWidth, usableHeight), axisWidth,
                    xLabelOffset + i * xLabelOffsetIncrease);
            this.series[i].paintLine(g2, new Rectangle(pl + axisWidth, pt, usableWidth, usableHeight));
        }

    }

    public void setSeries(LineSeries[] series) {
        this.series = series;
        this.repaint();
    }

}
