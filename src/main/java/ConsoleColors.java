public class ConsoleColors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    public static final String ansiRedMessage(Exception e) {
        return ANSI_RED_BACKGROUND + e.getMessage() + ANSI_RESET;
    }

    public static final String ansiGreenMessage(String string) {
        return ANSI_GREEN_BACKGROUND + string + ANSI_RESET;
    }
}
