package com.gschw.ljwc.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class DBStorageElementTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String key = "meow";


    private static DBStorageElement createComplexStorageElement() {
        DBStorageElement complexStorageElement = new DBStorageElement(key);
        DateTime dateTime = new DateTime(2000, 1, 1, 23, 12, 59, 1);
        complexStorageElement.setTimestamp(dateTime);

        complexStorageElement.getData().put("key1", "value1".getBytes());
        complexStorageElement.getMeta().put("keyA", "valuea".getBytes());

        return complexStorageElement;
    }

    @Test
    public void testGetKey() throws Exception {
        final DBStorageElement element = new DBStorageElement(key);
        Assert.assertEquals(element.getKey(), key);
    }

    @Test
    public void testTimestamp() throws Exception {
        final DBStorageElement element = new DBStorageElement(key);

        DateTime dateTime = DateTime.now();
        element.setTimestamp(dateTime);
        Assert.assertEquals(element.getTimestamp(), dateTime);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final DBStorageElement element = createComplexStorageElement();

        ////
        final String expectedString = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/dbstorageelement.json"), DBStorageElement.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(element),
                expectedString);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final DBStorageElement element = createComplexStorageElement();

        final DBStorageElement expectedElement = MAPPER.readValue(fixture("fixtures/dbstorageelement.json"), DBStorageElement.class);

        Assert.assertEquals(element.getKey(), expectedElement.getKey());
        Assert.assertEquals(element.getTimestamp(), expectedElement.getTimestamp());
        Assert.assertEquals(element.getData(), expectedElement.getData());
        Assert.assertEquals(element.getMeta(), expectedElement.getMeta());
    }

    @Test
    public void testDeserializeFromJSON_Empty() throws Exception {
        final DBStorageElement element = new DBStorageElement(null);

        final DBStorageElement expectedElement = MAPPER.readValue(fixture("fixtures/empty.json"), DBStorageElement.class);

        Assert.assertEquals(element.getKey(), expectedElement.getKey());
        Assert.assertEquals(element.getTimestamp(), expectedElement.getTimestamp());
        Assert.assertEquals(element.getData(), expectedElement.getData());
        Assert.assertEquals(element.getMeta(), expectedElement.getMeta());
    }
}