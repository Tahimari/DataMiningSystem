import weka.core.*;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

public class WekaClassification {

    Data data;

    WekaClassification(Data data) {
        this.data = data;
    }

    public void menu() {
        try {
            run();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
        }
        Main.menu();
    }

    public void run() throws Exception {
        System.out.println("Starting processing, it may take a while.");
        DataSource source = new DataSource(data.getInput());
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }


        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(data);
        System.out.println("\n\nClassifier model:\n\n" + nb);

        Evaluation eval = null;
        eval = new Evaluation(data);
        eval.crossValidateModel(nb, data, 5, new Random(1));
        System.out.println(eval.toSummaryString());
    }
}
