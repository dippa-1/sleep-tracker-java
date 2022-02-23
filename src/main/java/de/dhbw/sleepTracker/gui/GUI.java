package de.dhbw.sleepTracker.gui;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dhbw.charts.ChartPanel;
import de.dhbw.sleepTracker.core.Database;

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

        ChartPanel chartPanel = new ChartPanel(db.getEntries());
        NewEntryPanel newEntryPanel = new NewEntryPanel(db);
        newEntryPanel.addNewEntryListener(chartPanel);
        newEntryPanel.setMaximumSize(new Dimension(gui.getWidth(), 60));

        container.add(newEntryPanel);
        container.add(chartPanel);

        gui.setVisible(true);
    }

}
