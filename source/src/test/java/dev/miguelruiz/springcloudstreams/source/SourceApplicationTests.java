package dev.miguelruiz.springcloudstreams.source;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SourceApplicationTests {

	@Autowired
	private OutputDestination outputDestination;

	@Test
	void contextLoads() {
	}

	@Test
	public void testUsageDetailSender() {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
				TestChannelBinderConfiguration
						.getCompleteConfiguration(SourceApplication.class))
				.web(WebApplicationType.NONE)
				.run()) {

			Message<byte[]> sourceMessage = outputDestination.receive(10000, "processorinput");

			MessageConverter converter = context.getBean(CompositeMessageConverter.class);

			String name = (String) converter
					.fromMessage(sourceMessage, String.class);

			assertThat(name).isEqualTo("Miguel Ruiz");
		}
	}
}
