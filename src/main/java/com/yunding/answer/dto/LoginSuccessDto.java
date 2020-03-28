package com.yunding.answer.dto;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/3/9
 * @Description:
 */
@Data
public class LoginSuccessDto {
    /**
     * 登陆成功返回的token信息
     */
    private String accessToken;

    private String phoneNumber;
}
