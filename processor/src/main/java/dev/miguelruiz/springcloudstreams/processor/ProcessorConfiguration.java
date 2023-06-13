package dev.miguelruiz.springcloudstreams.processor;

import dev.miguelruiz.springcloudstreams.processor.models.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.function.Function;

@Configuration
public class ProcessorConfiguration {

    @Bean
    public Function<String, Person> processName() {
        return name -> new Person(name, new Date().getTime());
    }

    @Bean
    public Function<String,String> toUpperCase() {
//        return name -> name.toUpperCase(); // lambda option
        return String::toUpperCase; // method reference option
    }





}
