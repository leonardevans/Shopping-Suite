package com.shoppingsuite;

import com.shoppingsuite.config.AppProperties;
import com.shoppingsuite.config.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ShoppingSuiteApplication extends SpringBootServletInitializer {

    @Bean
    public static ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(ShoppingSuiteApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShoppingSuiteApplication.class);
    }

}
