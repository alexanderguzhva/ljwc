package com.gschw.ljwc.lj.ljscheduler.api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gschw.ljwc.auth.Identity;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.constraints.AssertTrue;

/**
 * Created by nop on 7/15/15.
 */
public class LJDownloadElementTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String url = "http://localhost:666/meow";
    private static final Identity identity = new Identity("woof");

    private static LJDownloadElement createComplex() {
        return new LJDownloadElement(identity, url);
    }

    @Test
    public void doTestPeoperties() throws Exception
    {
        final LJDownloadElement element = createComplex();

        Assert.assertEquals(url, element.getUrl());
        Assert.assertEquals(identity, element.getIdentity());
    }


    @Test
    public void testSerializeToJSON() throws Exception {
        final LJDownloadElement element = createComplex();

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/ljdownloadelement.json"), LJDownloadElement.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(element),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final LJDownloadElement element = createComplex();

        final LJDownloadElement expected =
                MAPPER.readValue(fixture("fixtures/ljdownloadelement.json"), LJDownloadElement.class);

        Assert.assertEquals(expected.getUrl(), element.getUrl());
        Assert.assertEquals(expected.getIdentity(), element.getIdentity());
    }



}