package com.rccsilva.whatsapp.session.repositories;

import com.rccsilva.whatsapp.session.domain.UserGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    final private String baseKey = "whatsapp:gateway:";
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void save(UserGateway userGateway) {

        this.redisTemplate.opsForValue().set(
                baseKey + userGateway.getUserId(),
                userGateway.getGateway()
        );
    }

    public String find(String userId) {
        logger.info("Getting gateway of {}", userId);
        return this.redisTemplate.opsForValue().get(baseKey + userId);
    }
}
