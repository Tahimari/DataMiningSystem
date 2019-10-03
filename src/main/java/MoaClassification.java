import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.Classifier;
import moa.classifiers.bayes.NaiveBayes;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.TimingUtils;
import moa.streams.ArffFileStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MoaClassification {

    Data data;
    Map<String, Double> result = null;
    Classifier learner;

    MoaClassification(Data data) {
        this.data = data;
    }

    public void menu() {
        learnerMenu();
        testingMenu();
    }

    private void testingMenu() {
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

    private void learnerMenu() {
        String menuNumber = "";

        System.out.println("[1] To run with Bayes Classifier");
        System.out.println("[2] To run with Hoeffding tree Classifier");
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

    private Map<String, Double> run(boolean isTesting) {
        try {
            System.out.println("Starting processing, it may take a while.");

            ArffFileStream stream = new ArffFileStream(data.getInput(), 10);
            stream.prepareForUse();

            learner.setModelContext(stream.getHeader());
            learner.prepareForUse();

            int numberSamplesCorrect = 0;
            int numberSamples = 0;
            long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
            while (stream.hasMoreInstances()) {
                Instance trainInst = stream.nextInstance().getData();
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
            System.out.println(ConsoleColors.ansiRedMessage(e));
            Main.menu();
        }
        return null;
    }
}
