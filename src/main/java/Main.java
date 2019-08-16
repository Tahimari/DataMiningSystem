import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static Data data = new Data();
    static WekaClassification wekaClassification;
    static MoaClassification moaClassification;

    public static void main(String[] args) {
        System.out.println("KMDataMiningSystem version 1.0");
        menu();
    }

    public static void menu() {
        String menuNumber = "";

        System.out.println("\nWhat you want to do?");
        System.out.println("[1] Transform plain text data to arff format");
        System.out.println("[2] Load Data");
        System.out.println("[3] Weka Classification");
        System.out.println("[4] Moa Classification");
        System.out.println("[5] Charts");
        System.out.println("[6] About program");
        System.out.println("[7] Exit");
        System.out.println("Insert menu number and press enter...");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        try {
            menuNumber = reader.readLine();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            menu();
        }

        switch (menuNumber) {
            case "1":
                DataTextToArff dataTextToArff = new DataTextToArff();
                dataTextToArff.menu();
                break;
            case "2":
                data.setInputFromConsole();
                menu();
                break;
            case "3":
                if (data.getInput().length() > 0) {
                    wekaClassification = new WekaClassification(data);
                    wekaClassification.menu();
                } else {
                    System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Please load data first" + ConsoleColors.ANSI_RESET);
                    menu();
                }
                break;
            case "4":
                if (data.getInput().length() > 0) {
                    moaClassification = new MoaClassification(data);
                    moaClassification.menu();
                } else {
                    System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Please load data first" + ConsoleColors.ANSI_RESET);
                    menu();
                }
                break;
            case "5":
                Chart chart = new Chart(wekaClassification, moaClassification);
                chart.menu();
                break;
            case "6":
                try {
                    about();
                } catch (Exception e) {
                    System.out.println(ConsoleColors.ansiRedMessage(e));
                    menu();
                }
                break;
            case "7":
                System.exit(0);
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }
    }

    public static void about() throws IOException {
        System.out.println("Data Mining System version 1.0.0");
        System.out.println("This program is part of thesis, created by Kamil Misiak");
        System.out.println("Press enter to go to menu...");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String menuNumber = reader.readLine();
        menu();
    }
}
