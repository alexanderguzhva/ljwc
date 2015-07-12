package com.gschw.ljwc.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class DBStorageElementsCollectionTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final DBStorageElement element1 = new DBStorageElement("meow");
    private static final DBStorageElement element2 = new DBStorageElement("woof");

    private static DBStorageElementsCollection createComplexCollection() {
        DBStorageElementsCollection collection = new DBStorageElementsCollection();
        collection.addElement(element1);
        collection.addElement(element2);
        return collection;
    }

    @Test
    public void testCollection() throws Exception {
        final DBStorageElementsCollection collection = createComplexCollection();

        Assert.assertTrue(collection.getElements().size() == 2);
        Assert.assertTrue(collection.getElements().contains(element1));
        Assert.assertTrue(collection.getElements().contains(element2));
    }


    @Test
    public void testSerializeToJSON() throws Exception {
        final DBStorageElementsCollection collection = createComplexCollection();

        ////
        final String expectedString = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/dbstorageelementscollection.json"), DBStorageElementsCollection.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(collection),
                expectedString);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final DBStorageElementsCollection collection = createComplexCollection();

        final DBStorageElementsCollection expectedCollection = MAPPER.readValue(fixture("fixtures/dbstorageelementscollection.json"), DBStorageElementsCollection.class);

        Assert.assertTrue(expectedCollection.getElements().size() == 2);
        DBStorageElement elementA = expectedCollection.getElements().get(0);
        DBStorageElement elementB = expectedCollection.getElements().get(1);

        Assert.assertEquals(element1.getKey(), elementA.getKey());
        Assert.assertEquals(element1.getTimestamp(), elementA.getTimestamp());
        Assert.assertEquals(element1.getData(), elementA.getData());
        Assert.assertEquals(element1.getMeta(), elementA.getMeta());

        Assert.assertEquals(element2.getKey(), elementB.getKey());
        Assert.assertEquals(element2.getTimestamp(), elementB.getTimestamp());
        Assert.assertEquals(element2.getData(), elementB.getData());
        Assert.assertEquals(element2.getMeta(), elementB.getMeta());
    }
}