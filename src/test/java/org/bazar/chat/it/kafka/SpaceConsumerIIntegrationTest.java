package org.bazar.chat.it.kafka;

import builder.SpaceDeletingDtoBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.bazar.chat.domain.chat.Chat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;
import java.util.UUID;

import static org.awaitility.Awaitility.await;
import static org.bazar.chat.it.kafka.AbstractKafkaIntegrationTest.SPACE_DELETE_TOPIC;

@EmbeddedKafka(partitions = 1, topics = {SPACE_DELETE_TOPIC})
public class SpaceConsumerIIntegrationTest extends AbstractKafkaIntegrationTest {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Test
    void deleteChat_success() throws JsonProcessingException {
        Consumer<String, String> kafkaConsumer = getKafkaConsumer(embeddedKafka, SPACE_DELETE_TOPIC);
        Producer<String, String> kafkaProducer = getKafkaProducer(embeddedKafka);
        Chat chat = testDataHelper.createChatWith(SpaceDeletingDtoBuilder.SPACE_ID);
        testDataHelper.createMessageWith(chat, "content", UUID.randomUUID());

        sendMessage(kafkaConsumer, kafkaProducer, SPACE_DELETE_TOPIC, "", SpaceDeletingDtoBuilder.buildDefault());

        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(200))
                .until(() -> chatJpaRepository.findBySpaceId(SpaceDeletingDtoBuilder.SPACE_ID).isEmpty()
                        && messageJpaRepository.findAll().isEmpty());
    }
}
