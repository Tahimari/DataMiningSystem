package data;

import moa.streams.ArffFileStream;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.datagenerators.classifiers.classification.RandomRBF;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import helpers.*;


public class Data extends Stream {
    private String input = "";
    private int classValIndex = 0;

    public void setData() {
        this.setUseGeneratorFromConsole();
        if (this.getUseGenerator()) {
            this.setNumberSamplesFromConsole();
        } else {
            this.setInputFromConsole();
            this.setClassValIndexFromConsole();
            this.arff = new ArffFileStream(this.input, this.classValIndex + 1);
        }
    }

    public void setInputFromConsole() {
        this.input = IOHelper.readInput("Please insert data input path:");
    }

    public String getInput() {
        return this.input;
    }

    public void setClassValIndexFromConsole() {
        this.classValIndex = IOHelper.tryParse(IOHelper.readInput("Please insert class value index:"));
    }

    public Integer getClassValIndex() {
        return this.classValIndex;
    }

    public Instances getDataSource() {
        try {
            if (this.getUseGenerator()) {
                String[] options = new String[2];
                options[0] = "-n";
                options[1] = String.valueOf(this.getNumberSamples());

                RandomRBF generator = new RandomRBF();
                generator.setOptions(options);
                generator.defineDataFormat();

                Instances instances = generator.generateExamples();

                this.classValIndex = generator.getNumAttributes();

                options[0] = "-R";
                options[1] = "1-2";

                NumericToNominal numericToNominal = new NumericToNominal();
                numericToNominal.setOptions(options);
                numericToNominal.setInputFormat(instances);

                return Filter.useFilter(instances, numericToNominal);
            } else {
                DataSource source = new DataSource(this.input);
                return source.getDataSet();
            }
        } catch (Exception e) {
            ConsoleColors.ansiRedErrorMessage(e);
            return null;
        }

    }
}
