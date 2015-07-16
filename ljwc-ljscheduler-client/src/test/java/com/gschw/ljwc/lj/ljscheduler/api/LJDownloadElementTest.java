package com.gschw.ljwc.lj.ljscheduler.api;

import static org.testng.Assert.*;

import com.gschw.ljwc.auth.Identity;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.validation.constraints.AssertTrue;

/**
 * Created by nop on 7/15/15.
 */
public class LJDownloadElementTest {

    @Test
    public void doTestPeoperties() throws Exception
    {
        final String url = "http://localhost:666/meow";
        final Identity identity = new Identity("woof");

        final LJDownloadElement element = new LJDownloadElement(identity, url);

        Assert.assertEquals(url, element.getUrl());
        Assert.assertEquals(identity, element.getIdentity());
    }


}