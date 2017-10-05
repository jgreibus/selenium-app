package com.softneta;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

    private String url;
    private String username;
    private String password;
    private String listPath;

    public TestProperties() {
        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            this.url = properties.getProperty("viewer.url");
            this.username = properties.getProperty("viewer.username");
            this.password = properties.getProperty("viewer.password");
            this.listPath = properties.getProperty("studies.list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getListPath() {
        return listPath;
    }
}
