import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;


public class DataTextToArff {
    public void textToArff(String input, String output) throws Exception  {
        ArrayList<Attribute> attributes;
        ArrayList<String> classVals;
        Instances data;
        double[] values;

        attributes = new ArrayList<Attribute>();

        attributes.add(new Attribute("S1"));
        attributes.add(new Attribute("C1"));
        attributes.add(new Attribute("S2"));
        attributes.add(new Attribute("C2"));
        attributes.add(new Attribute("S3"));
        attributes.add(new Attribute("C3"));
        attributes.add(new Attribute("S4"));
        attributes.add(new Attribute("C4"));
        attributes.add(new Attribute("S5"));
        attributes.add(new Attribute("C5"));
        classVals = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            classVals.add("class" + (i));
        }
        Attribute classVal = new Attribute("class", classVals);
        attributes.add(classVal);


        data = new Instances("MyRelation", attributes, 0);

        File file = new File(input);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null)  {
            values = new double[data.numAttributes()];
            String[] parts = st.split(",");
            for(int i = 0; i < parts.length - 1; i++) {
                values[i] = Double.parseDouble(parts[i]);
                System.out.println(parts[i]);
            }
            values[10] = classVals.indexOf("class" + parts[10]);
            data.add(new DenseInstance(1.0, values));
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        writer.write(data.toString());
        writer.close();

        System.out.println(data);
    }
}
