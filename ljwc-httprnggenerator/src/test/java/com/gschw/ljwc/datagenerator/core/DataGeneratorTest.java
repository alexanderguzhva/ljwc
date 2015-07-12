package com.gschw.ljwc.datagenerator.core;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class DataGeneratorTest {
    @Test
    public void testGetParameters() throws Exception {
        DataGeneratorParameters parameters = new DataGeneratorParameters(5555, 44, 333);
        DataGenerator dataGenerator = new DataGenerator(parameters);

        Assert.assertEquals(dataGenerator.getParameters().getRandomSeed(), parameters.getRandomSeed());
        Assert.assertEquals(dataGenerator.getParameters().getSizeMin(), parameters.getSizeMin());
        Assert.assertEquals(dataGenerator.getParameters().getSizeMax(), parameters.getSizeMax());
    }

    @Test
    public void testGenerate() throws Exception {
        DataGeneratorParameters parameters = new DataGeneratorParameters(5555, 44, 333);
        DataGenerator dataGenerator = new DataGenerator(parameters);

        for (int i = 0; i < 100; i++) {
            byte[] data = dataGenerator.generate();

            Assert.assertNotNull(data);
            Assert.assertTrue(data.length >= parameters.getSizeMin() && data.length <= parameters.getSizeMax());
        }
    }

}