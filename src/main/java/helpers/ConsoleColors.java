package helpers;

public class ConsoleColors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    public static final void ansiRedErrorMessage(Exception e) {
        System.out.println(ANSI_RED_BACKGROUND + e.getMessage() + ANSI_RESET);
    }

    public static final void ansiRedMessage(String string) {
        System.out.println(ANSI_RED_BACKGROUND + string + ANSI_RESET);
    }

    public static final void ansiGreenMessage(String string) {
        System.out.println(ANSI_GREEN_BACKGROUND + string + ANSI_RESET);
    }
}
