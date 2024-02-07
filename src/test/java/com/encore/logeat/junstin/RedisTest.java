package com.encore.logeat.junstin;


import com.encore.logeat.common.redis.RedisService;
import com.encore.logeat.common.security.SecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;



@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("레디스 연동 테스트")
    public void 레디스연동테스트() {
        //given
        String key1 = "kim@naver.com";
        String key2 = "son@gmail.com";
        Duration duration = Duration.ofMinutes(1);

        //when
        redisService.setValues(key1, "332211", duration);
        redisService.setValues(key2, "5IAK98", duration);

        //then
        Assertions.assertThat(redisService.getValues(key1)).isEqualTo("332211");
        Assertions.assertThat(redisService.getValues(key2)).isEqualTo("5IAK98");
    }

    @Test
    @DisplayName("키가 없으면 값이 어떻게 나오는지 확인")
    public void 레디스값확인() {
        String key1 = "kim@naver.com";
        String key2 = "kim@gmail.com";

        String values1 = redisService.getValues(key1);
        String values2 = redisService.getValues(key2);

        boolean b1 = redisService.checkExistsValue(values1);
        boolean b2 = redisService.checkExistsValue(values2);

        Assertions.assertThat(b1).isEqualTo(true);
        Assertions.assertThat(b2).isEqualTo(false);

    }

}
