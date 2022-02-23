package de.dhbw.sleepTracker.gui;
import java.util.ArrayList;

import de.dhbw.sleepTracker.core.SleepEntry;

public interface NewEntryListener {
    void entryAdded(ArrayList<SleepEntry> entries);
}
