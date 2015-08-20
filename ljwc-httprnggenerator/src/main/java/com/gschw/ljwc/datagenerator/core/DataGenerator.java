package com.gschw.ljwc.datagenerator.core;

/**
 * Generates an array of bytes of random length that contains random data.
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
