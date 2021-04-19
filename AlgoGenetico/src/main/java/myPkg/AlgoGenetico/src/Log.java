package myPkg.AlgoGenetico.src;

public class Log {
    private static String whole_log = "";
    public static int priority = -1;

    public static void addToWhole_log(String add_log, int expected_priority) {
        if (expected_priority >= priority)
            Log.whole_log += add_log;
    }

    public static String getWhole_log() {
        return Log.whole_log;
    }
    public static void clearLog(){
        Log.whole_log = "";
    }

}
