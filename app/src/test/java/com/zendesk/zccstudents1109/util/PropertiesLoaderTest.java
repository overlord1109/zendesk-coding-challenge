package com.zendesk.zccstudents1109.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesLoaderTest {

    @Test
    public void loadsProperties() {
        Properties props = new PropertiesLoader().loadProperties("application-test.properties");
        assertNotNull(props);
        assertEquals("https://zccstudents1109.zendesk.com", props.get("zendesk_base_url"));
    }

    @Test
    public void doesNotLoadPropertiesFromMissingFile() {
        assertNull(new PropertiesLoader().loadProperties("application-prod.properties"));
    }
}
