package com.gschw.ljwc.uploader.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gschw.ljwc.auth.Identity;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.constraints.AssertTrue;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/13/15.
 */
public class DGUploadTaskTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String idString = "meow";
    private static final String urlString = "http://localhost:9999/meow";
    private static final String bytesString = "woof";

    private DGUploadTask createComplex() {
        final Identity identity = new Identity(idString);
        DGUploadTask task = new DGUploadTask(identity, urlString, bytesString.getBytes());
        return task;
    }

    @Test
    public void testProperties() throws Exception {
        final Identity identity = new Identity(idString);
        final DGUploadTask task = createComplex();
        Assert.assertEquals(task.getData(), bytesString.getBytes());
        Assert.assertEquals(task.getUrl(), urlString);
        Assert.assertEquals(task.getTaskIdentity(), identity);
    }


    @Test
    public void testSerializeToJSON() throws Exception {
        final DGUploadTask uploadTask = createComplex();

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/dguploadertask.json"), DGUploadTask.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(uploadTask),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final DGUploadTask uploadTask = createComplex();

        final DGUploadTask expected = MAPPER.readValue(fixture("fixtures/dguploadertask.json"), DGUploadTask.class);
        Assert.assertEquals(uploadTask.getTaskIdentity(), expected.getTaskIdentity());
        Assert.assertEquals(uploadTask.getUrl(), expected.getUrl());
        Assert.assertEquals(uploadTask.getData(), expected.getData());
    }


}