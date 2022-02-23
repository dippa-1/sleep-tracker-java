package de.dhbw.charts;

import java.awt.*;
import java.util.ArrayList;

public class LineSeries<X extends Comparable<X>, Y extends Comparable<Y>> extends Series {
    private ArrayList<X> labels;
    private ArrayList<Y> values;

    public LineSeries(String name, Color color) {
        super(name, color);
        this.labels = new ArrayList<X>();
        this.values = new ArrayList<Y>();
    }

    public ArrayList<X> getLabels() {
        return this.labels;
    }

    public ArrayList<Y> getValues() {
        return this.values;
    }

    public X getFirstLabel() {
        return this.labels.get(0);
    }

    public X getLastLabel() {
        return this.labels.get(this.labels.size() - 1);
    }

    public Y getMaximum() {
        Y max = this.values.get(0);
        for (int i = 1; i < this.values.size(); ++i) {
            if (this.values.get(i).compareTo(max) > 0) {
                max = this.values.get(i);
            }
        }
        return max;
    }

    public Y getMinimum() {
        Y min = this.values.get(0);
        for (int i = 1; i < this.values.size(); ++i) {
            if (this.values.get(i).compareTo(min) < 0) {
                min = this.values.get(i);
            }
        }
        return min;
    }

    // insert sorted
    public void insert(X label, Y value) {
        int i = 0;
        while (i < this.labels.size() && this.labels.get(i).compareTo(label) < 0) {
            ++i;
        }
        this.labels.add(i, label);
        this.values.add(i, value);
    }

    public void setLabels(ArrayList<X> labels) {
        this.labels = labels;
    }

    public void setValues(ArrayList<Y> values) {
        this.values = values;
    }

    public void paintLine(Graphics g, Rectangle bounds) {}

}
