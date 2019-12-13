package converters;

import java.io.*;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import helpers.*;


public class DataTextToArff {
    private String input = "";
    private String output = "";
    private Integer numberOfAttributes = 0;
    private Integer numberOfClassVals = 0;

    public void menu() {
        setInput();
        setOutput();
        setAttributes();
        setClassVals();

        if (this.input.length() > 0 && this.output.length() > 0) {
            run();
        } else {
            System.out.println("Pleas insert input and output path");
        }
    }

    private void setInput() {
        this.input = IOHelper.readInput("Please insert data input path:");
    }

    private void setOutput() {
        this.output = IOHelper.readInput("Please insert data output arrf path:");
    }

    private void setAttributes() {
        this.numberOfAttributes = IOHelper.tryParse(IOHelper.readInput("Please insert number of attributes:"));
    }

    private void setClassVals() {
        this.numberOfClassVals = IOHelper.tryParse(IOHelper.readInput("Please insert number of class vals:"));
    }

    private void run() {
        try {
            textToArff(this.input, this.output);
        } catch (Exception e) {
            ConsoleColors.ansiRedErrorMessage(e);
        }
    }

    private void textToArff(String input, String output) throws Exception {
        ArrayList<Attribute> attributes;
        ArrayList<String> classVals;
        Instances data;
        double[] values;

        attributes = new ArrayList<Attribute>();

        for (int i = 0; i < numberOfAttributes; i++) {
            attributes.add(new Attribute("Attribute" + (i)));
        }

        classVals = new ArrayList<String>();
        for (int i = 0; i < numberOfClassVals; i++) {
            classVals.add("class" + (i));
        }
        Attribute classVal = new Attribute("class", classVals);
        attributes.add(classVal);


        data = new Instances("MyRelation", attributes, 0);

        File file = new File(input);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        System.out.println("Starting transfering file, it may take a while...");

        while ((st = br.readLine()) != null) {
            values = new double[data.numAttributes()];
            String[] parts = st.split(",");
            for (int i = 0; i < parts.length - 1; i++) {
                values[i] = Double.parseDouble(parts[i]);
            }
            values[numberOfAttributes] = classVals.indexOf("class" + parts[parts.length - 1]);
            data.add(new DenseInstance(1.0, values));
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write(data.toString());
        writer.close();

        ConsoleColors.ansiGreenMessage("TRANSFER SUCCESS");
    }
}
