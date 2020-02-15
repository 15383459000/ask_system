package com.yunding.answer.redis;

import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.yunding.answer.constant.RedisKeyTemplate.*;
import static com.yunding.answer.util.RedisKeyUtil.buildKey;

/**
 * @author 宋万顷
 */
@Component
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveMessageCode(String phone, String messageCode) {
        RedisUtil.set(redisTemplate, buildKey(T_VERIFICATION_CODE, phone), messageCode);
    }

    @Override
    public String selectMessageCodeByPhone(String phone) {
        return RedisUtil.<String>get(redisTemplate, buildKey(T_VERIFICATION_CODE, phone), String.class);
    }

    @Override
    public void deleteMessageCode(String phone) {
        RedisUtil.del(redisTemplate, buildKey(T_VERIFICATION_CODE, phone));
    }

    @Override
    public void saveAccessToken(TokenInfo tokenInfo) {
        RedisUtil.set(redisTemplate, buildKey(T_ACCESS_TOKEN, tokenInfo.getAccessToken()), tokenInfo.getUserId());
    }

    @Override
    public void saveLoginAccessToken(TokenInfo tokenInfo) {
        RedisUtil.set(redisTemplate, buildKey(T_USER_CURRENT_TOKEN, tokenInfo.getUserId()), tokenInfo.getAccessToken());
    }

    @Override
    public String selectAccessToken(String accessToken) {
        return RedisUtil.<String>get(redisTemplate, buildKey(T_ACCESS_TOKEN, accessToken), String.class);
    }

    @Override
    public String selectLoginAccessToken(String userId) {
        return RedisUtil.<String>get(redisTemplate, buildKey(T_USER_CURRENT_TOKEN, userId), String.class);
    }

    @Override
    public void deleteAccessToken(String accessToken) {
        RedisUtil.del(redisTemplate, buildKey(T_ACCESS_TOKEN, accessToken));
    }

    @Override
    public void deleteLoginAccessToken(String userId) {
        RedisUtil.del(redisTemplate, buildKey(T_USER_CURRENT_TOKEN, userId));
    }
}
