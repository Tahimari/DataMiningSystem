package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IOHelper {

    public static String readInput() {
        return runBufferedReader();
    }

    public static String readInput(String label) {
        System.out.println(label);
        return runBufferedReader();
    }

    public static void waitForReadLine () {
        IOHelper.doInputReading();
    }

    private static String runBufferedReader() {
        String input = IOHelper.doInputReading();
        if (input.length() > 0) {
            return input;
        } else {
            ConsoleColors.ansiRedMessage("Invalid Input");
            return "";
        }
    }

    private static String doInputReading() {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (Exception e) {
            ConsoleColors.ansiRedErrorMessage(e);
            return "";
        }
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
