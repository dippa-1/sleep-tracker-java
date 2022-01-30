public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
          printHelp();
          System.exit(0);
        }
        SleepEntry entry = new SleepEntry(args);
    }

    private static void printHelp() {
      System.out.println("""
      Sleep Optimizer: No parameters given
      USAGE:
      Add entry: java SleepOptimizer add [-d --date YYYY:MM:dd] [-b --bed HH:MM] [-w --wakeup HH:MM] [-r --rating 0 to 5]
        Example: java SleepOptimizer add -d 2021-01-30 -b 22:55 -w 9:05 -r 3
      """
      );
    }
}
