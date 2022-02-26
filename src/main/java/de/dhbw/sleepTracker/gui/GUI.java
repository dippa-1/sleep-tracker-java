package de.dhbw.sleepTracker.gui;

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dhbw.charts.LineChartPanel;
import de.dhbw.charts.TimeSeries;
import de.dhbw.sleepTracker.core.Database;

public class GUI extends JFrame {
    public static void main(String[] args) {
        GUI gui = new GUI();
        Database db = new Database("sleep-data");

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Sleep Optimizer");
        final int width = 1200;
        final int height = 700;
        gui.setBounds(50, 50, width, height);

        // add parent container for horizontal layout
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        gui.add(container);

        LineChartPanel chartPanel = new LineChartPanel("Sleep History", new TimeSeries[] {});
        ChartDataManager chartDataManager = new ChartDataManager(db.getEntries(), chartPanel);
        NewEntryPanel newEntryPanel = new NewEntryPanel(db);
        newEntryPanel.addNewEntryListener(chartDataManager);
        newEntryPanel.setMaximumSize(new Dimension(gui.getWidth(), 60));

        container.add(newEntryPanel);
        container.add(chartPanel);

        gui.setVisible(true);
    }

}
