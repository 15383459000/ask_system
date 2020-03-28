package com.yunding.answer.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class UpdatePhoneNumberForm {
    //token信息
    @NotBlank(message = "token信息不能为空")
    private String accessToken;
    //手机号
    @NotBlank(message = "手机号不能为空")
    @Pattern(message = "请输入正确的手机号", regexp = "^((13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(16[6])|(17[0|4|6|7|8])|(18[0-9])|(19[8-9]))\\d{8}$")
    private String oldPhone;
    @NotBlank(message = "手机号不能为空")
    @Pattern(message = "请输入正确的手机号", regexp = "^((13[0-9])|(14[5-8])|(15([0-3]|[5-9]))|(16[6])|(17[0|4|6|7|8])|(18[0-9])|(19[8-9]))\\d{8}$")
    private String newPhone;
    //验证码
    private String code;
}
