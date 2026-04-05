package io.hhplus.architecture.support;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUpDatabase() {
        databaseCleanUp.execute();
    }
}
