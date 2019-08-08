package com.kafkaqa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan
@EntityScan
@EnableJpaRepositories
public class KafkaQAapplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaQAapplication.class, args);
    }
}
