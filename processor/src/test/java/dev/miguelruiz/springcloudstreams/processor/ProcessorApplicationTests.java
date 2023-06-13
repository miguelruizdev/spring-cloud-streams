package dev.miguelruiz.springcloudstreams.processor;

import dev.miguelruiz.springcloudstreams.processor.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProcessorApplicationTests {

    // InputDestination & OutputDestination are part of spring-cloud-stream-test-support module

    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Test
    void contextLoads() {
    }

    @Test
    public void testUsageCostProcessor() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        ProcessorApplication.class)).web(WebApplicationType.NONE)
                .run()) {

            String inputName = "Miguel";

            MessageConverter converter = context.getBean(CompositeMessageConverter.class);
            Map<String, Object> headers = new HashMap<>();
            headers.put("contentType", "application/json");
            MessageHeaders messageHeaders = new MessageHeaders(headers);
            Message<?> message = converter.toMessage(inputName, messageHeaders);

            inputDestination.send(message);

            // coincides with spring.cloud.stream.function.bindings.processName-out-0 value --> sinkinput
            Message<byte[]> sourceMessage = outputDestination.receive(10000, "sinkinput");

            Person person = (Person) converter
                    .fromMessage(sourceMessage, Person.class);

            assertThat(person.name()).isEqualTo("MIGUEL");
            assertThat(person.processedTimestamp()).isNotNull();
        }
    }
}
