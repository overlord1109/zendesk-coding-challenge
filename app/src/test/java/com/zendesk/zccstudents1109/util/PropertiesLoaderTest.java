package com.zendesk.zccstudents1109.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class PropertiesLoaderTest {

    @Test
    public void loadsProperties() {
        Properties props = new PropertiesLoader().loadProperties("application-test.properties");
        Assert.assertNotNull(props);
        Assert.assertEquals("https://zccstudents1109.zendesk.com", props.get("zendesk_base_url"));
    }

    @Test
    public void doesNotLoadPropertiesFromMissingFile() {
        Assert.assertNull(new PropertiesLoader().loadProperties("application-prod.properties"));
    }
}
