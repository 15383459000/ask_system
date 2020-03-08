package com.yunding.ask_system;


import com.yunding.answer.AskSystemApplication;
import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.redis.RedisRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskSystemApplication.class)
public class AskSystemApplicationTests {

    @Autowired
    RedisRepository redisRepository;

    @Test
    public void contextLoads() {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccessToken("111");
        tokenInfo.setUserId("1");
        redisRepository.saveAccessToken(tokenInfo);
        redisRepository.saveLoginAccessToken(tokenInfo);
    }

}
