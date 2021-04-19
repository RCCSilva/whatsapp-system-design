package com.rccsilva.whatsapp.session.integrations;

import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import com.rccsilva.whatsapp.session.domain.UserGateway;
import com.rccsilva.whatsapp.session.domain.UserMessage;
import com.rccsilva.whatsapp.session.publisher.SendMessagePublisher;
import com.rccsilva.whatsapp.session.repositories.UserMessageRepository;
import com.rccsilva.whatsapp.session.services.MessageService;
import com.rccsilva.whatsapp.session.services.UserGatewayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @SpyBean
    private UserGatewayService userGatewayService;

    @SpyBean
    private SendMessagePublisher sendMessagePublisher;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMessageRepository userMessageRepository;

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
        userMessageRepository.deleteAll();
    }

    @Test
    void testSend_GivenToUserThatDoesNotHaveGateway_dontPublishAndSaveMessage() {
        // Given
        SendMessageRequest sendMessageRequest = new SendMessageRequest(
            "rafael",
            "unknown",
            "My message!",
            "22:11"
        );

        // Test
        messageService.send(sendMessageRequest);

        // Assert
        List<UserMessage> userMessageList= userMessageRepository.findAll();
        assertThat(userMessageList.size()).isEqualTo(1);
        assertThat(userMessageList.get(0).getToUserId()).isEqualTo(sendMessageRequest.getTo());
        assertThat(userMessageList.get(0).getMessages().size()).isEqualTo(1);
        assertThat(userMessageList.get(0).getMessages().get(0).getText()).isEqualTo(sendMessageRequest.getText());

        Mockito.verify(userGatewayService, Mockito.times(1))
                .get("unknown");
        Mockito.verify(sendMessagePublisher, Mockito.times(0))
                .publish(Mockito.any(), Mockito.any());
    }

    @Test
    void testSend_GivenToUserThatDoesHaveGateway_ifPublishNotReplies_saveMessage() {
        // Given
        SendMessageRequest sendMessageRequest = new SendMessageRequest(
                "rafael",
                "unknown",
                "My message!",
                "22:11"
        );

        userGatewayService.handleIncome(new UserGateway("unknown", "gateway"));

        // Test
        messageService.send(sendMessageRequest);

        // Assert
        List<UserMessage> userMessageList= userMessageRepository.findAll();
        assertThat(userMessageList.size()).isEqualTo(0);

        Mockito.verify(userGatewayService, Mockito.times(1))
                .get("unknown");
        Mockito.verify(sendMessagePublisher, Mockito.times(1))
                .publish(Mockito.any(), Mockito.any());

        // TODO Need to handle the response and test if it fails
    }
}
