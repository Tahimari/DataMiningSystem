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

    SimpleClassification simpleClassification;
    WekaClassification wekaClassification;
    MoaClassification moaClassification;

    Chart(
            SimpleClassification simpleClassification,
            WekaClassification wekaClassification,
            MoaClassification moaClassification
    ) {
        super("Classification");
        this.simpleClassification = simpleClassification;
        this.wekaClassification = wekaClassification;
        this.moaClassification = moaClassification;
    }

    public void menu() {
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
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Accurancy",
                "Instances", "Proper clasified instances",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        run();
    }

    private void speedChart() {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Speed",
                "Time", "Clasified instances",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        run();
    }

    private void run() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");
        return dataset;
    }
}
