package com.yunding.answer.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class UserPersonalForm {

    private String username;

    private String school;

    private String province;

    private String city;

    private String county;
    @NotBlank(message = "头像不能为空")
    private String portrait;

    private String gender;

    private String realName;
}
