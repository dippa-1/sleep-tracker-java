import java.time.LocalDateTime;

public class SleepEntry {
    private LocalDateTime bedDateTime;
    private LocalDateTime wakeupDateTime;
    private int restRating;

    SleepEntry(String[] args) {
        boolean nextArgIsType = false;
        ArgumentType argType;
        for (String arg : args) {
            if (nextArgIsType) {
                argType = ArgumentType.getTypeFromFlag(arg);
                nextArgIsType = false;
                if (argType == null) {
                    System.out.println("ERROR: Cannot read flag " + arg);
                    System.exit(1);
                }
                continue;
            }
            switch (argType) {

            }
        }
    }

    public LocalDateTime getBedDateTime() {
        return bedDateTime;
    }

    public LocalDateTime getWakeupDateTime() {
        return wakeupDateTime;
    }

    public int getRestRating() {
        return restRating;
    }
}
