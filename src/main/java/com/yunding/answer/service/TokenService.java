package com.yunding.answer.service;

import com.yunding.answer.dto.TokenInfo;

/**
 * @author 宋万顷
 */
public interface TokenService {

    /**
     * 从Redis获取token的信息
     * @param accessToken
     * @return
     */
    TokenInfo getTokenInfo(String accessToken);

}
