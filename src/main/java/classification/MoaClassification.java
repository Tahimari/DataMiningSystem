package classification;

import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.Classifier;
import moa.classifiers.bayes.NaiveBayes;
import moa.classifiers.trees.HoeffdingTree;
import moa.classifiers.trees.DecisionStump;
import moa.core.TimingUtils;

import java.util.*;

import data.Data;
import helpers.*;

public class MoaClassification {

    private Classifier learner;
    private Data data;
    private Boolean isTesting = false;
    private int numberSamplesCorrect = 0;
    private int numberSamples = 0;
    public Map<String, Double> result = null;

    public MoaClassification(Data data) {
        this.data = data;
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
                menu();
        }
        testingMenu();
    }

    private void testingMenu() {
        System.out.println("[1] To run testing");
        System.out.println("[2] To run not testing");
        System.out.println("[3] Main menu");
        switch (IOHelper.readInput()) {
            case "1":
                this.isTesting = true;
                result = run();
                break;
            case "2":
                this.isTesting = false;
                run();
                break;
            case "3":
                return;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }

    }

    private Map<String, Double> run() {
        try {
            System.out.println("Starting processing, it may take a while.");

            numberSamples = 0;
            numberSamplesCorrect = 0;
            data.prepareForUse();

            learner.setModelContext(data.getHeader());
            learner.prepareForUse();

            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();

            while (data.hasMoreInstances() && (!data.getUseGenerator() || numberSamples < data.getNumberSamples())) {
                Instance trainInst = data.nextInstance().getData();
                if (isTesting) {
                    if (learner.correctlyClassifies(trainInst)) {
                        numberSamplesCorrect++;
                    }
                }
                numberSamples++;
                learner.trainOnInstance(trainInst);
            }

            double accuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
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
