package com.wendy.cryptotradingsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String url1;
    private String url2;

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
    public String printProperties() {
        return "AppProperties{" +
                "url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                '}';
    }
}
