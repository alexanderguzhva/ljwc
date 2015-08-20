package com.gschw.ljwc.datagenerator.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

/**
 *  Parameters for a @see com.gschw.ljwc.datagenerator.DataGenerator class.
 */
public class DataGeneratorParameters {
    @JsonIgnore
    private Random random;

    @JsonIgnore
    public Random getRandom() {
        return random;
    }

    //
    private long randomSeed;

    @JsonProperty
    public long getRandomSeed() {
        return randomSeed;
    }

    //
    private int sizeMin;

    private int sizeMax;

    @JsonProperty("sizeMin")
    public int getSizeMin() {
        return sizeMin;
    }

    @JsonProperty("sizeMax")
    public int getSizeMax() {
        return sizeMax;
    }

    //
    public void setSizes(int sizeMin, int sizeMax) {
        this.sizeMin = Math.min(sizeMin, sizeMax);
        this.sizeMax = Math.max(sizeMin, sizeMax);
    }

    //
    public DataGeneratorParameters() {
        this.randomSeed = 111;
        this.random = new Random(this.randomSeed);
        this.sizeMin = 16;
        this.sizeMax = 256;
    }

    @JsonCreator
    public DataGeneratorParameters(
            @JsonProperty("randomSeed") long seed,
            @JsonProperty("sizeMin") int sizeMin,
            @JsonProperty("sizeMax") int sizeMax) {
        this.randomSeed = seed;
        this.random = new Random(this.randomSeed);
        this.sizeMin = Math.min(sizeMin, sizeMax);
        this.sizeMax = Math.max(sizeMin, sizeMax);
    }
}
