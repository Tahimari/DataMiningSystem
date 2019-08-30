import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Chart extends JFrame {

    WekaClassification wekaClassification;
    MoaClassification moaClassification;

    Chart(
            WekaClassification wekaClassification,
            MoaClassification moaClassification
    ) {
        super("Classification");
        this.wekaClassification = wekaClassification;
        this.moaClassification = moaClassification;
    }

    public void menu() {
        if (wekaClassification == null || wekaClassification.result == null) {
            System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Weka data is not set" + ConsoleColors.ANSI_RESET);
        }
        if (moaClassification == null || moaClassification.result == null) {
            System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Moa data is not set" + ConsoleColors.ANSI_RESET);
        }

        String menuNumber = "";

        System.out.println("[1] To see accurancy chart");
        System.out.println("[2] To see speed chart");
        System.out.println("[3] Main menu");

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
                accurancyChart();
                menu();
                break;
            case "2":
                speedChart();
                menu();
                break;
            case "3":
                Main.menu();
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }
    }

    private void accurancyChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Accurancy",
                "Category",
                "Proper clasified instances",
                createAccurancyDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        run();
    }

    private void speedChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Speed",
                "Category",
                "Clasified instances",
                createSpeedDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        run();
    }

    private void run() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private DefaultCategoryDataset createAccurancyDataset() {
        final String moa = "MOA";
        final String weka = "WEKA";
        final String accurancy = "Accurancy";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (moaClassification != null
                && moaClassification.result != null
                && moaClassification.result.get("accurancy") != null) {
            dataset.addValue(moaClassification.result.get("accurancy"), moa, accurancy);
        }

        if (wekaClassification != null
                && wekaClassification.result != null
                && wekaClassification.result.get("accurancy") != null) {
            dataset.addValue(wekaClassification.result.get("accurancy"), weka, accurancy);
        }

        return dataset;
    }

    private DefaultCategoryDataset createSpeedDataset() {
        final String moa = "MOA";
        final String weka = "WEKA";
        final String accurancy = "Speed";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (moaClassification != null
                && moaClassification.result != null
                && moaClassification.result.get("time") != null) {
            dataset.addValue(moaClassification.result.get("time"), moa, accurancy);
        }

        if (wekaClassification != null
                && wekaClassification.result != null
                && wekaClassification.result.get("time") != null) {
            dataset.addValue(wekaClassification.result.get("time"), weka, accurancy);
        }

        return dataset;
    }
}
