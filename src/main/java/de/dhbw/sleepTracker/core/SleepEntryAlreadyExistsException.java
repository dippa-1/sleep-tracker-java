package de.dhbw.sleepTracker.core;
public class SleepEntryAlreadyExistsException extends Exception {
    public SleepEntryAlreadyExistsException(String message) {
        super(message);
    }
}
