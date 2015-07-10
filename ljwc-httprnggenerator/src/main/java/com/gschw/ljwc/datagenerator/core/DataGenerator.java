package com.gschw.ljwc.datagenerator.core;

/**
 * Created by hadoop on 7/2/15.
 */
public class DataGenerator {
    private DataGeneratorParameters parameters;

    public DataGeneratorParameters getParameters() {
        return parameters;
    }

    public DataGenerator(DataGeneratorParameters parameters) {
        this.parameters = parameters;
    }

    public byte[] generate() {
        int max = parameters.getSizeMax();
        int min = parameters.getSizeMin();

        int length = parameters.getRandom().nextInt(max - min + 1) + min;
        byte[] data = new byte[length];
        parameters.getRandom().nextBytes(data);

        return data;
    }
}
