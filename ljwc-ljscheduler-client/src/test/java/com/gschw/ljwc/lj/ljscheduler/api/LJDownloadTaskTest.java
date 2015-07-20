package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gschw.ljwc.auth.Identity;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/19/15.
 */
public class LJDownloadTaskTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final LJDownloadElement element1 =
            new LJDownloadElement(
                    new Identity("meow"),
                    "http://localhost:666/meow");

    private static final LJDownloadElement element2 =
            new LJDownloadElement(
                    new Identity("woof"),
                    "http://localhost:666/woof");

    private static final Identity taskIdentity = new Identity("quack");
    private static final Identity taskAssignedTo = new Identity("roar");

    private static LJDownloadTask createComplex() {
        LJDownloadTask task = new LJDownloadTask(taskIdentity);
        task.setAssignedTo(taskAssignedTo);
        task.addElement(element1);
        task.addElement(element2);

        return task;
    }

    @Test
    public void doTestIdentity() {
        LJDownloadTask task = new LJDownloadTask(taskIdentity);
        Assert.assertEquals(task.getIdentity(), taskIdentity);
        Assert.assertNull(task.getAssignedTo());
        Assert.assertNotNull(task.getElements());
        Assert.assertEquals(task.getElements().size(), 0);
    }

    @Test
    public void doTestAssignedTo() {
        LJDownloadTask task = new LJDownloadTask(taskIdentity);
        task.setAssignedTo(taskAssignedTo);
        Assert.assertEquals(task.getAssignedTo(), taskAssignedTo);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final LJDownloadTask task = createComplex();

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/ljdownloadtask.json"), LJDownloadTask.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(task),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final LJDownloadTask task = createComplex();

        final LJDownloadTask expected =
                MAPPER.readValue(fixture("fixtures/ljdownloadtask.json"), LJDownloadTask.class);

        Assert.assertEquals(expected.getIdentity(), task.getIdentity());
        Assert.assertEquals(expected.getAssignedTo(), task.getAssignedTo());
        Assert.assertEquals(expected.getElements(), task.getElements());
    }

}