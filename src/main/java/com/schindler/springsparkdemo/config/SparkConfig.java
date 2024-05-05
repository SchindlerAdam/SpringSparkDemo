package com.schindler.springsparkdemo.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName("SpringSparkDemo")
                .master("local")
                .getOrCreate();
    }
}
