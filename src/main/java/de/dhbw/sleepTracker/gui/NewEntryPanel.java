package de.dhbw.sleepTracker.gui;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.*;

import de.dhbw.sleepTracker.core.Database;
import de.dhbw.sleepTracker.core.SleepEntry;
import de.dhbw.sleepTracker.core.SleepEntryAlreadyExistsException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEntryPanel extends JPanel {
    private Database db;
    private ArrayList<NewEntryListener> newEntryListeners;

    public NewEntryPanel(Database db) {
        this.db = db;
        this.newEntryListeners = new ArrayList<NewEntryListener>();

        // add textfields for date, bedtime, wakeup time and rest rating
        JLabel dateLabel = new JLabel("Date: ");
        JTextField dateField = new JTextField("" + LocalDate.now(), 10);
        this.add(dateLabel);
        this.add(dateField);

        JLabel bedtimeLabel = new JLabel("Bedtime: ");
        JTextField bedtimeField = new JTextField(10);
        this.add(bedtimeLabel);
        this.add(bedtimeField);

        JLabel wakeupLabel = new JLabel("Wakeup: ");
        JTextField wakeupField = new JTextField(10);
        this.add(wakeupLabel);
        this.add(wakeupField);

        JLabel restLabel = new JLabel("Rest Rating: ");
        JTextField restField = new JTextField(10);
        this.add(restLabel);
        this.add(restField);

        // add "add" button
        JButton addButton = new JButton("Add");
        // listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get text from textfields and parse
                String dateString = dateField.getText();
                String bedtimeString = bedtimeField.getText();
                String wakeupString = wakeupField.getText();
                String restString = restField.getText();

                try {
                    LocalDate date = LocalDate.parse(dateString);
                    try {
                        LocalTime bedtime = LocalTime.parse(bedtimeString);
                        try {
                            LocalTime wakeup = LocalTime.parse(wakeupString);
                            try {
                                int rest = Integer.parseInt(restString);
                                SleepEntry entry = new SleepEntry(date, bedtime, wakeup, rest);
                                try {
                                    db.add(entry, false);
                                    db.sort();
                                    db.save();
                                    date = date.plusDays(1);
                                    dateField.setText(date.toString());
                                    bedtimeField.setText("");
                                    wakeupField.setText("");
                                    restField.setText("");
                                } catch (SleepEntryAlreadyExistsException e1) {
                                    // show dialog whether to overwrite
                                    int result = JOptionPane.showConfirmDialog(null,
                                            "Sleep entry already exists. Overwrite?", "Overwrite?",
                                            JOptionPane.YES_NO_OPTION);
                                    if (result == JOptionPane.YES_OPTION) {
                                        db.add(entry, true);
                                        db.sort();
                                        db.save();
                                        date = date.plusDays(1);
                                        dateField.setText(date.toString());
                                        bedtimeField.setText("");
                                        wakeupField.setText("");
                                        restField.setText("");
                                    }
                                } finally {
                                    // notify listeners
                                    for (NewEntryListener listener : newEntryListeners) {
                                        listener.entryAdded(db.getEntries());
                                    }
                                }

                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Rest rating must be a number");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Wakeup time must be in the format HH:MM");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Bedtime time must be in the format HH:MM");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Date must be in the format YYYY-MM-DD");
                }

            }
        });
        this.add(addButton);

    }

    public void addNewEntryListener(NewEntryListener listener) {
        newEntryListeners.add(listener);
    }

}
