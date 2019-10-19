import moa.core.TimingUtils;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.HoeffdingTree;
import weka.core.*;
import weka.classifiers.Evaluation;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WekaClassification {

    Data data;
    Map<String, Double> result = null;
    Classifier learner;

    WekaClassification(Data data) {
        this.data = data;
    }

    public void menu() {
        learnerMenu();
        testingMenu();
    }

    private void learnerMenu() {
        System.out.println("[1] To run with Bayes Classifier");
        System.out.println("[2] To run with Hoeffding tree Classifier");
        System.out.println("[3] Main menu");

        switch (IOHelper.readInput()) {
            case "1":
                this.learner = new NaiveBayes();
                break;
            case "2":
                this.learner = new HoeffdingTree();
                break;
            case "3":
                Main.menu();
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }
    }

    private void testingMenu() {
        System.out.println("[1] To run testing");
        System.out.println("[2] To run not testing");
        System.out.println("[3] Main menu");

        switch (IOHelper.readInput()) {
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

            DataSource source = new DataSource(this.data.getInput());
            Instances data = source.getDataSet();
            if (data.classIndex() == -1) {
                data.setClassIndex(this.data.getClassValIndex());
            }

            double numberSamplesCorrect = 0;
            double numberSamples = 0;
            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();

            this.learner.buildClassifier(data);

            Evaluation eval = new Evaluation(data);

            if (isTesting) {
                eval.crossValidateModel(this.learner, data, 5, new Random(1));
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
            ConsoleColors.ansiRedErrorMessage(e);
            Main.menu();
        }
        return null;
    }
}
