package com.zendesk.zccstudents1109.util;

import com.zendesk.zccstudents1109.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesLoader {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Properties props;

    public PropertiesLoader() {
        this.props = new Properties();
    }

    public Properties loadProperties(String propsPath) {
        InputStream resourceStream = App.class.getClassLoader().getResourceAsStream(propsPath);
        try {
            this.props.load(resourceStream);
        } catch (IOException | NullPointerException e) {
            logger.log(Level.ALL, "Exception while loading properties from resource file " + propsPath);
            return null;
        }
        return props;
    }
}
