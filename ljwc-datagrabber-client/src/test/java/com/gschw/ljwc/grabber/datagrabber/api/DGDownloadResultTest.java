package com.gschw.ljwc.grabber.datagrabber.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gschw.ljwc.auth.Identity;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class DGDownloadResultTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void doTestProperties() throws Exception {
        final Identity identity = new Identity("meow");
        final DGDownloadResult resultFalse = new DGDownloadResult(identity, false);
        final DGDownloadResult resultTrue = new DGDownloadResult(identity, true);

        Assert.assertFalse(resultFalse.isSuccess());
        Assert.assertEquals(resultFalse.getTaskIdentity(), identity);

        Assert.assertTrue(resultTrue.isSuccess());
        Assert.assertEquals(resultTrue.getTaskIdentity(), identity);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final Identity identity = new Identity("meow");
        final DGDownloadResult dgDownloadResult = new DGDownloadResult(identity, true);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/dgdownloadresult.json"), DGDownloadResult.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(dgDownloadResult),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final Identity identity = new Identity("meow");
        final DGDownloadResult dgDownloadResult = new DGDownloadResult(identity, true);

        Assert.assertEquals(
                MAPPER.readValue(fixture("fixtures/dgdownloadresult.json"), DGDownloadResult.class),
                dgDownloadResult);
    }

}