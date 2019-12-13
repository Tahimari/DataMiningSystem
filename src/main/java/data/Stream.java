package data;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import helpers.IOHelper;
import moa.core.Example;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.streams.ArffFileStream;
import moa.streams.InstanceStream;
import moa.streams.generators.RandomRBFGenerator;
import moa.tasks.TaskMonitor;

public class Stream extends AbstractOptionHandler implements InstanceStream {
    protected RandomRBFGenerator generator = new RandomRBFGenerator();
    protected ArffFileStream arff = new ArffFileStream();
    private boolean useGenerator = false;
    private int numberSamples = 0;

    public void setUseGeneratorFromConsole() {
        System.out.println("Use generator?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        System.out.println("Insert number: ");
        switch (IOHelper.readInput()) {
            case "1":
                this.useGenerator = true;
                break;
            case "2":
                this.useGenerator = false;
                break;
            default:
                this.setUseGeneratorFromConsole();
                break;
        }
    }

    public void setNumberSamplesFromConsole() {
        this.numberSamples = IOHelper.tryParse(IOHelper.readInput("Please insert number of generated samples:"));
    }

    public int getNumberSamples() {
        return this.numberSamples;
    }

    public boolean getUseGenerator() {
        return this.useGenerator;
    }

    @Override
    protected void prepareForUseImpl(TaskMonitor taskMonitor, ObjectRepository objectRepository) {
        if (this.useGenerator) {
            generator.prepareForUseImpl(taskMonitor, objectRepository);
        } else {
            arff.prepareForUseImpl(taskMonitor, objectRepository);
        }
    }

    @Override
    public InstancesHeader getHeader() {
        if (this.useGenerator) {
            return generator.getHeader();
        } else {
            return arff.getHeader();
        }
    }

    @Override
    public long estimatedRemainingInstances() {
        if (this.useGenerator) {
            return generator.estimatedRemainingInstances();
        } else {
            return arff.estimatedRemainingInstances();
        }
    }

    @Override
    public boolean hasMoreInstances() {
        if (this.useGenerator) {
            return generator.hasMoreInstances();
        } else {
            return arff.hasMoreInstances();
        }
    }

    @Override
    public Example<Instance> nextInstance() {
        if (this.useGenerator) {
            return generator.nextInstance();
        } else {
            return arff.nextInstance();
        }
    }

    @Override
    public boolean isRestartable() {
        if (this.useGenerator) {
            return generator.isRestartable();
        } else {
            return arff.isRestartable();
        }
    }

    @Override
    public void restart() {
        if (this.useGenerator) {
            generator.restart();
        } else {
            arff.restart();
        }
    }

    @Override
    public void getDescription(StringBuilder stringBuilder, int i) {
        if (this.useGenerator) {
            generator.getDescription(stringBuilder, i);
        } else {
            arff.getDescription(stringBuilder, i);
        }
    }
}
