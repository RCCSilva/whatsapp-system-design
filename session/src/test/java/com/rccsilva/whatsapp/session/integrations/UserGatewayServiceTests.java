package com.rccsilva.whatsapp.session.integrations;

import com.rccsilva.whatsapp.session.domain.UserGateway;
import com.rccsilva.whatsapp.session.services.UserGatewayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserGatewayServiceTests {

    @Autowired
    private UserGatewayService userGatewayService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        deleteDatabase();
    }

    @AfterEach
    void teardown() {
        deleteDatabase();
    }

    private void deleteDatabase() {
        Set<String> keys = redisTemplate.keys("whatsapp:*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }
    @Test
    void test_saveAndGet_givenExistingGateway_returnGateway() {
        // Given
        UserGateway userGateway = new UserGateway("rafael", "gateway");

        // Test
        userGatewayService.handleIncome(userGateway);
        String gateway =  userGatewayService.get("rafael");

        // Assert
        assertThat(gateway).isEqualTo("gateway");
    }

    @Test
    void test_saveAndGet_givenNonExistingGateway_returnGateway() {
        // Test
        String gateway =  userGatewayService.get("asdasd");

        // Assert
        assertThat(gateway).isNull();
    }
}
