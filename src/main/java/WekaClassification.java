import moa.core.TimingUtils;
import weka.core.*;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WekaClassification {

    Data data;
    Map<String, Double> result = null;

    WekaClassification(Data data) {
        this.data = data;
    }

    public void menu() {

        String menuNumber = "";

        System.out.println("[1] To run testing");
        System.out.println("[2] To run not testing");
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
                result = run(true);
                Main.menu();
                break;
            case "2":
                run(false);
                Main.menu();
                break;
            case "3":
                Main.menu();
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }
    }

    private Map<String, Double> run(boolean isTesting) {
        try {
            System.out.println("Starting processing, it may take a while.");

            DataSource source = new DataSource(data.getInput());
            Instances data = source.getDataSet();
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }


            NaiveBayes nb = new NaiveBayes();

            double numberSamplesCorrect = 0;
            double numberSamples = 0;
            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();

            nb.buildClassifier(data);

            Evaluation eval = null;
            eval = new Evaluation(data);
            eval.crossValidateModel(nb, data, 5, new Random(1));

            if (isTesting) {
                numberSamplesCorrect = eval.correct();
            }

            numberSamples = eval.numInstances();
            double accuracy = 100.0 * numberSamplesCorrect / numberSamples;
            double time = TimingUtils.nanoTimeToSeconds(TimingUtils.getNanoCPUTimeOfCurrentThread() - evaluateStartTime);
            Map<String, Double> map = new HashMap<String, Double>();
            map.put("accurancy", accuracy);
            map.put("time", time);

            System.out.println(numberSamples + " instances processed with " + accuracy + "% accuracy in " + time + "seconds");

            return map;
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            Main.menu();
        }
        return null;
    }
}
