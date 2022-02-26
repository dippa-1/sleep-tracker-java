package de.dhbw.charts;

import javax.swing.JPanel;

import java.awt.*;

public class ChartPanel extends JPanel {
    private String title;

    public ChartPanel(String title) {
        this.title = title;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(this.title, 10, 20);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
    }

}
