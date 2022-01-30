import java.time.LocalDateTime;

public class SleepEntry {
    private LocalDateTime bedDateTime;
    private LocalDateTime wakeupDateTime;
    private int restRating;

    SleepEntry(String[] args) {
        boolean nextArgIsType = true;
        ArgumentType argType = null;
        for (String arg : args) {
            if (nextArgIsType) {
                argType = ArgumentType.getTypeFromFlag(arg);
                nextArgIsType = false;
                continue;
            }
            if (argType == null) {
                System.out.println("ERROR: Cannot read argument " + arg);
                System.exit(1);
            }
            System.out.println("Argument: " + arg);
            switch (argType) {
                case BEDTIME:
                    break;
                case WAKEUPTIME:
                    break;
                case DATE:
                    break;
                case RATING:
                    break;
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
