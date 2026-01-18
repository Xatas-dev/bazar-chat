package org.bazar.chat.it;

import org.bazar.chat.adapter.outbound.persistence.chat.ChatJpaRepository;
import org.bazar.chat.adapter.outbound.persistence.message.MessageJpaRepository;
import org.bazar.chat.fw.BazarChatApplication;
import org.bazar.chat.it.config.TestSecurityConfig;
import org.bazar.chat.it.testutil.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(
        classes = {
                BazarChatApplication.class
        }
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import(TestSecurityConfig.class)
public abstract class AbstractIntegrationTest {
    @Autowired
    protected TestDataHelper testDataHelper;
    @Autowired
    protected ChatJpaRepository chatRepository;
    @Autowired
    protected MessageJpaRepository messageRepository;

    @BeforeEach
    void cleanUp() {
        testDataHelper.clearTables();
    }

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
