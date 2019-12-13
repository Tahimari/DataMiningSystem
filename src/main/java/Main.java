import classification.MoaClassification;
import classification.WekaClassification;
import converters.DataTextToArff;
import data.Data;
import helpers.ConsoleColors;
import helpers.IOHelper;
import view.Chart;

public class Main {

    static Data data = new Data();
    static WekaClassification wekaClassification;
    static MoaClassification moaClassification;

    public static void main(String[] args) {
        System.out.println("KMDataMiningSystem version 1.0");
        menu();
    }

    public static void menu() {

        System.out.println("\nWhat you want to do?");
        System.out.println("[1] Transform plain text data to arff format");
        System.out.println("[2] Load data");
        System.out.println("[3] Weka Classification");
        System.out.println("[4] Moa Classification");
        System.out.println("[5] Charts");
        System.out.println("[6] About program");
        System.out.println("[7] Exit");
        System.out.println("Insert menu number and press enter...");

        switch (IOHelper.readInput()) {
            case "1":
                DataTextToArff dataTextToArff = new DataTextToArff();
                dataTextToArff.menu();
                break;
            case "2":
                data.setData();
                menu();
                break;
            case "3":
                if (data.getInput().length() > 0 || data.getUseGenerator()) {
                    wekaClassification = new WekaClassification(data);
                    wekaClassification.menu();
                } else {
                    ConsoleColors.ansiRedMessage("Please load data first");
                    menu();
                }
                break;
            case "4":
                if (data.getInput().length() > 0 || data.getUseGenerator()) {
                    moaClassification = new MoaClassification(data);
                    moaClassification.menu();
                } else {
                    ConsoleColors.ansiRedMessage("Please load data first");
                    menu();
                }
                break;
            case "5":
                Chart chart = new Chart(wekaClassification, moaClassification);
                chart.menu();
                break;
            case "6":
                about();
                break;
            case "7":
                System.exit(0);
                break;
            default:
                ConsoleColors.ansiRedMessage("Invalid input");
                menu();
        }
        menu();
    }

    public static void about() {
        System.out.println("data.Data Mining System version 1.0.0");
        System.out.println("This program is part of thesis, created by Kamil Misiak");
        System.out.println("Press enter to go to menu...");
        IOHelper.readInput();
        menu();
    }
}
