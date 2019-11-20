import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.SamoaToWekaInstanceConverter;
import moa.classifiers.Classifier;
import moa.classifiers.bayes.NaiveBayes;
import moa.classifiers.trees.HoeffdingTree;
import moa.classifiers.trees.DecisionStump;
import moa.core.TimingUtils;
import weka.core.Attribute;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MoaClassification {

    // If not set then J48
    Classifier learner;
    Data data;
    Map<String, Double> result = null;
    Boolean isTesting = false;
    int numberSamplesCorrect = 0;
    int numberSamples = 0;

    MoaClassification(Data data) {
        this.data = data;
    }

    public void menu() {
        learnerMenu();
        testingMenu();
    }

    private void learnerMenu() {
        System.out.println("[1] To run with Bayes Classifier");
        System.out.println("[2] To run with Hoeffding tree Classifier");
        System.out.println("[3] To run with Decision stump Classifier");
        System.out.println("[4] To run with J48 (from weka) Classifier");
        System.out.println("[5] Main menu");

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
                break;
            case "5":
                Main.menu();
                break;
            default:
                ConsoleColors.ansiRedMessage("Invalid input");
                menu();
        }
    }

    private void testingMenu() {
        System.out.println("[1] To run testing");
        System.out.println("[2] To run not testing");
        System.out.println("[3] Main menu");
        switch (IOHelper.readInput()) {
            case "1":
                this.isTesting = true;
                result = run();
                Main.menu();
                break;
            case "2":
                this.isTesting = false;
                run();
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

    public Map<String, Double> run() {
        try {
            System.out.println("Starting processing, it may take a while.");

            numberSamples = 0;
            numberSamplesCorrect = 0;
            data.prepareForUse();

            if (this.learner != null) {
                learner.setModelContext(data.getHeader());
                learner.prepareForUse();
            }

            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();

            if (learner != null) {
                runMOAClassificator();
            } else {
                runJ48();
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
            Main.menu();
        }
        return null;
    }

    private void runMOAClassificator() {
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
    }


    private void runJ48() throws Exception {
        int windowSize = 100;
        Instances window;
        ArrayList<String> classVals;
        ArrayList<Attribute> attributes;
        J48 learner = new J48();
        SamoaToWekaInstanceConverter converter = new SamoaToWekaInstanceConverter();

        while (data.hasMoreInstances() && (!data.getUseGenerator() || numberSamples < data.getNumberSamples())) {
            classVals = new ArrayList<String>();
            attributes = new ArrayList<Attribute>();
            weka.core.Instance instance = converter.wekaInstance(data.nextInstance().getData());

            for (int i = 1; i < instance.numAttributes(); i++) {
                attributes.add(new Attribute("Attribute" + (i)));
            }

            Attribute classVal = new Attribute("class", data.getHeader().classAttribute().getAttributeValues());
            attributes.add(classVal);

            window = new weka.core.Instances("window", attributes, 0);

            for (int i = 0; i < windowSize - 1; i++) {
                if (!data.hasMoreInstances()) {
                    windowSize = i;
                    break;
                }
                window.add(instance);
                instance = converter.wekaInstance(data.nextInstance().getData());
            }

            window.add(instance);
            window.setClassIndex(window.numAttributes() - 1);
            learner.buildClassifier(window);

            Evaluation eval = new Evaluation(window);
            if (this.isTesting) {
                eval.crossValidateModel(learner, window, 5, new Random(1));
                numberSamplesCorrect += eval.correct();
            }
            numberSamples += windowSize;
        }
    }
}
