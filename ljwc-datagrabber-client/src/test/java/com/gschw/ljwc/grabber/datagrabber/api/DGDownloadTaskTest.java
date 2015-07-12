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
public class DGDownloadTaskTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String idString = "meow";
    private static final String urlString = "http://localhost:123";
    private static final String urlReplyString = "http://otherhost:80";


    @Test
    public void doTestProperties() throws Exception {
        final Identity identity = new Identity(idString);
        final DGDownloadTask dgDownloadTask = new DGDownloadTask(identity, urlString, urlReplyString);

        Assert.assertEquals(dgDownloadTask.getTaskIdentity(), identity);
        Assert.assertEquals(dgDownloadTask.getUrl(), urlString);
        Assert.assertEquals(dgDownloadTask.getUploadServiceURL(), urlReplyString);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final Identity identity = new Identity(idString);
        final DGDownloadTask dgDownloadTask = new DGDownloadTask(identity, urlString, urlReplyString);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/dgdownloadtask.json"), DGDownloadTask.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(dgDownloadTask),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final Identity identity = new Identity(idString);
        final DGDownloadTask dgDownloadTask = new DGDownloadTask(identity, urlString, urlReplyString);

        Assert.assertEquals(
                MAPPER.readValue(fixture("fixtures/dgdownloadtask.json"), DGDownloadTask.class),
                dgDownloadTask);
    }

}