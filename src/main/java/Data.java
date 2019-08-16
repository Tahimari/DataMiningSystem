import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Data {

    private String input = "";

    public void setInputFromConsole() {
        String inputPath = "";
        System.out.println("Please insert poker data input path:");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            inputPath = reader.readLine();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
        }

        if (inputPath.length() > 0) {
            this.input = inputPath;
            System.out.println(ConsoleColors.ansiGreenMessage("LOAD SUCCESS"));
        } else {
            setInputFromConsole();
        }
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return this.input;
    }
}
