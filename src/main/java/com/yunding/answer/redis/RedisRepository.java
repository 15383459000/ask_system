package com.yunding.answer.redis;

import com.yunding.answer.dto.TokenInfo;

/**
 * @author 宋万顷
 */
public interface RedisRepository {

    /**
     * 保存手机号和验证码的键值对<phone, messageCode>
     * @param phone
     * @param messageCode 验证码
     */
    void saveMessageCode(String phone, String messageCode);

    /**
     * 根据key：手机号 查询验证码
     * @param phone
     * @return
     */
    String selectMessageCodeByPhone(String phone);

    /**
     * 删除手机号和验证码的键值对
     * @param phone
     */
    void deleteMessageCode(String phone);

    /**
     * 认证
     * @param tokenInfo
     */
    void saveAccessToken(TokenInfo tokenInfo);

    /**
     * 登录认证
     * @param tokenInfo
     */
    void saveLoginAccessToken(TokenInfo tokenInfo);

    /**
     * 查询认证
     * @param accessToken
     * @return
     */
    String selectAccessToken(String accessToken);

    /**
     * 查询登录认证
     * @param userId
     * @return
     */
    String selectLoginAccessToken(String userId);

    /**
     * 删除认证
     * @param accessToken
     */
    void deleteAccessToken(String accessToken);

    /**
     * 删除登录认证
     * @param userId
     */
    void deleteLoginAccessToken(String userId);
}
