package com.yunding.answer.service;

import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.dto.UserInfoDto;

public interface UserService {

    /**
     * 登陆 用验证码登陆
     * @param phone
     * @param code
     * @return
     */
    TokenInfo login(String phone, String code);



}



