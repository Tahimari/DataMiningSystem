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

    private static String runBufferedReader() {
        String input = "";
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            input = reader.readLine();
        } catch (Exception e) {
            ConsoleColors.ansiRedErrorMessage(e);
        }

        if (input.length() > 0) {
            return input;
        } else {
            ConsoleColors.ansiRedMessage("Invalid Input");
            return IOHelper.runBufferedReader();
        }
    }
}
