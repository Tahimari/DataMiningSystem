import java.io.*;
import java.util.ArrayList;

import scala.Int;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;


public class DataTextToArff {
    private String input = "";
    private String output = "";
    private Integer numberOfAttributes = 0;
    private Integer numberOfClassVals = 0;

    public void menu() {
        try {
            setInput();
            setOutput();
            setAttributes();
            setClassVals();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            Main.menu();
        }

        if (input.length() > 0 && output.length() > 0) {
            run();
        } else {
            System.out.println("Pleas insert input and output path");
        }

        Main.menu();
    }

    private void run() {
        try {
            textToArff(this.input, this.output);
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            Main.menu();
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
        int line = 0;

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

        System.out.println(ConsoleColors.ANSI_GREEN_BACKGROUND + "TRANSFER SUCCESS" + ConsoleColors.ANSI_RESET);
    }

    private void setInput() throws IOException {
        System.out.println("Please insert poker data input path:");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String inputPath = reader.readLine();
        if (inputPath.length() > 0) {
            this.input = inputPath;
        } else {
            setInput();
        }
    }

    private void setOutput() throws IOException {
        System.out.println("Please insert poker data output arrf path:");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String outputPath = reader.readLine();
        if (outputPath.length() > 0) {
            this.output = outputPath;
        } else {
            setOutput();
        }
    }

    private void setAttributes() throws IOException {
        System.out.println("Please insert number of attributes:");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String numberOfAttributes = reader.readLine();
        if (Integer.parseInt(numberOfAttributes) > 0) {
            this.numberOfAttributes = Integer.parseInt(numberOfAttributes);
        } else {
            setAttributes();
        }
    }

    private void setClassVals() throws IOException {
        System.out.println("Please insert number of class vals:");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String numberOfClassVals = reader.readLine();
        if (Integer.parseInt(numberOfClassVals) > 0) {
            this.numberOfClassVals = Integer.parseInt(numberOfClassVals);
        } else {
            setClassVals();
        }
    }
}
