package com.encore.logeat.junstin;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@SpringBootTest
@Transactional
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("레디스 연동 테스트")
    public void 레디스연동테스트() {
        //given
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String key1 = "kim@naver.com";
        String key2 = "son@gmail.com";
        Duration duration = Duration.ofMinutes(1);

        //when
        vop.set(key1, "332211", duration);
        vop.set(key2, "5IAK98", duration);

        //then
        Assertions.assertThat(vop.get(key1)).isEqualTo("332211");
        Assertions.assertThat(vop.get(key2)).isEqualTo("5IAK98");


    }

}
