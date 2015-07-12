package com.gschw.ljwc.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.testng.Assert.*;

/**
 * Created by nop on 7/12/15.
 */
public class IdentityTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String idString = "meow";


    @Test
    public void testGetId() {
        final Identity identity = new Identity(idString);
        Assert.assertEquals(identity.getId(), idString);
    }

    @Test
    public void testSerializeToJSON() throws Exception {
        final Identity identity = new Identity(idString);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/identity.json"), Identity.class));

        Assert.assertEquals(
                MAPPER.writeValueAsString(identity),
                expected);
    }

    @Test
    public void testDeserializeFromJSON() throws Exception {
        final Identity identity = new Identity(idString);

        Assert.assertEquals(
                MAPPER.readValue(fixture("fixtures/identity.json"), Identity.class),
                identity);
    }

    @Test
    public void testDeserializeFromJSON_Empty() throws Exception {
        final Identity identity = new Identity(null);

        Assert.assertEquals(
                MAPPER.readValue(fixture("fixtures/empty.json"), Identity.class),
                identity);
    }

}