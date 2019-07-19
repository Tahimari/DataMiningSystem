import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static Data data = new Data();

    public static void main(String[] args) {
        System.out.println("KMDataMiningSystem version 1.0");
        menu();
    }

    public static void menu() {
        String menuNumber = "";

        System.out.println("\nWhat you want to do?");
        System.out.println("[1] Transform plain text data to arff format");
        System.out.println("[2] Load Data");
        System.out.println("[3] Simple Classification");
        System.out.println("[4] Weka Classification");
        System.out.println("[5] Moa Classification");
        System.out.println("[6] Charts");
        System.out.println("[7] About program");
        System.out.println("[8] Exit");
        System.out.println("Insert menu number and press enter...");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        try {
            menuNumber = reader.readLine();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            menu();
        }

        switch(menuNumber) {
            case "1":
                DataTextToArff dataTextToArff = new DataTextToArff();
                dataTextToArff.menu();
                break;
            case "2":
                data.setInputFromConsole();
                menu();
                break;
            case "3":
                SimpleClassification simpleClassification = new SimpleClassification();
                simpleClassification.menu();
                break;
            case "4":
                WekaClassification wekaClassification = new WekaClassification();
                wekaClassification.menu();
                break;
            case "5":
                if (data.getInput().length() > 0) {
                    MoaClassification moaClassification = new MoaClassification(data);
                    moaClassification.menu();
                } else {
                    System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Please load data first" + ConsoleColors.ANSI_RESET);
                    menu();
                }
                break;
            case "6":
                Chart chart = new Chart();
                chart.menu();
                break;
            case "7":
                try {
                    about();
                } catch (Exception e) {
                    System.out.println(ConsoleColors.ansiRedMessage(e));
                    menu();
                }
                break;
            case "8":
                System.exit(0);
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND  + "Invalid input" + ConsoleColors.ANSI_RESET);
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
