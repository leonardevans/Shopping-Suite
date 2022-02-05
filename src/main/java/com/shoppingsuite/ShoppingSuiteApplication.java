package com.shoppingsuite;

import com.shoppingsuite.config.AppProperties;
import com.shoppingsuite.config.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ShoppingSuiteApplication {

    @Bean
    public static ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(ShoppingSuiteApplication.class, args);
    }

}
