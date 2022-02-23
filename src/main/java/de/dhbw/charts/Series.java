package de.dhbw.charts;

import java.awt.Color;

public class Series {
    private String name;
    private Color color;

    public Series(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }
}
