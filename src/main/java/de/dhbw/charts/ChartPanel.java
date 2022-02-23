package de.dhbw.charts;

import javax.swing.JPanel;

import de.dhbw.sleepTracker.gui.NewEntryListener;

import java.awt.*;
import java.util.ArrayList;

public class ChartPanel extends JPanel {
    private String title;

    public ChartPanel(String title) {
        this.title = title;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawString(title, 10, 20);
    }

}
