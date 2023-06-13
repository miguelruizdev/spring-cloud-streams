package dev.miguelruiz.springcloudstreams.source;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class SourceConfiguration {

    @Bean
    public Supplier<String> supplyName() {
        return () -> "Miguel Ruiz";
    }

}
