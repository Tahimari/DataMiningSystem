import moa.streams.ArffFileStream;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.datagenerators.classifiers.classification.RandomRBF;


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
            this.arff = new ArffFileStream(this.getInput(), this.getClassValIndex());
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

    public Instances getDataSource()  {
        try {
            if (this.getUseGenerator()) {
                String[] options = {"n10000", "a10", "c10"};
                RandomRBF generator = new RandomRBF();
                generator.setOptions(options);
                generator.defineDataFormat();
                return generator.generateExamples();
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
