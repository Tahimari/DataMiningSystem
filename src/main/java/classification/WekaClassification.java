package classification;

import moa.core.TimingUtils;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.HoeffdingTree;
import weka.core.Instances;
import weka.classifiers.Evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import data.Data;
import helpers.*;

public class WekaClassification {

    private Data data;
    private Classifier learner;
    public Map<String, Double> result = null;

    public WekaClassification(Data data) throws Exception {
        if (data.getInput().length() > 0 || data.getUseGenerator()) {
            this.data = data;
        } else {
            throw new Exception("Please load data first");
        }
    }

    public void menu() {
        learnerMenu();
    }

    private void learnerMenu() {
        System.out.println("[1] To run with Bayes Classifier");
        System.out.println("[2] To run with Hoeffding tree Classifier");
        System.out.println("[3] To run with Decision stump Classifier");
        System.out.println("[4] Main menu");

        switch (IOHelper.readInput()) {
            case "1":
                this.learner = new NaiveBayes();
                break;
            case "2":
                this.learner = new HoeffdingTree();
                break;
            case "3":
                this.learner = new DecisionStump();
                break;
            case "4":
                return;
            default:
                ConsoleColors.ansiRedMessage("Invalid input");
                this.learnerMenu();
                return;
        }
        testingMenu();
    }

    private void testingMenu() {
        System.out.println("[1] To run testing");
        System.out.println("[2] To run not testing");
        System.out.println("[3] Main menu");

        switch (IOHelper.readInput()) {
            case "1":
                result = run(true);
                break;
            case "2":
                run(false);
                break;
            case "3":
                return;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                this.testingMenu();
                return;
        }

    }

    private Map<String, Double> run(boolean isTesting) {
        try {
            System.out.println("Starting processing, it may take a while.");

            double numberSamplesCorrect = 0;
            double numberSamples = 0;
            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();

            Instances data = this.data.getDataSet();
            if (data.classIndex() == -1) {
                data.setClassIndex(this.data.getClassValIndex());
            }

            this.learner.buildClassifier(data);

            Evaluation eval = new Evaluation(data);

            if (isTesting) {
                eval.crossValidateModel(this.learner, data, 5, new Random(System.currentTimeMillis()));
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
        }
        return null;
    }
}
