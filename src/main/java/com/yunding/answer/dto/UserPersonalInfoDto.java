package com.yunding.answer.dto;

import com.yunding.answer.form.LoginSuccessForm;
import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class UserPersonalInfoDto {
    private String username;

    private String school;

    private String province;

    private String city;

    private String county;

    private String portrait;

    private Integer integral;

    private Integer grade;

    private String realName;

    private String gender;
}
