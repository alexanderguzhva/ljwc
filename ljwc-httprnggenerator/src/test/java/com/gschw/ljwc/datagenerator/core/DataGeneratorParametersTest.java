package com.gschw.ljwc.datagenerator.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class DataGeneratorParametersTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void testSizes() throws Exception {
        final int min = 5;
        final int max = 10;
        final DataGeneratorParameters generatorParameters = new DataGeneratorParameters(111, min, max);

        Assert.assertEquals(generatorParameters.getSizeMin(), min);
        Assert.assertEquals(generatorParameters.getSizeMax(), max);
    }

    @Test
    public void testInverseSizes() throws Exception {
        final int min = 10;
        final int max = 5;
        final DataGeneratorParameters generatorParameters = new DataGeneratorParameters(111, min, max);

        Assert.assertEquals(generatorParameters.getSizeMin(), max);
        Assert.assertEquals(generatorParameters.getSizeMax(), min);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final DataGeneratorParameters generatorParameters = new DataGeneratorParameters(555, 5, 10);

        ////
        final String expectedString = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/datageneratorparameters.json"), DataGeneratorParameters.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(generatorParameters),
                expectedString);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final DataGeneratorParameters generatorParameters = new DataGeneratorParameters(555, 5, 10);

        final DataGeneratorParameters expectedGeneratorParameters =
                MAPPER.readValue(fixture("fixtures/datageneratorparameters.json"), DataGeneratorParameters.class);

        Assert.assertEquals(expectedGeneratorParameters.getRandomSeed(), generatorParameters.getRandomSeed());
        Assert.assertEquals(expectedGeneratorParameters.getSizeMin(), generatorParameters.getSizeMin());
        Assert.assertEquals(expectedGeneratorParameters.getSizeMax(), generatorParameters.getSizeMax());
    }
}