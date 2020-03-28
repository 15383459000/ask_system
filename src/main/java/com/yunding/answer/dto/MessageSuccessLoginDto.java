package com.yunding.answer.dto;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class MessageSuccessLoginDto {
    private LoginSuccessDto loginSuccessDto;

    private String portrait;

    private String username;

    private Integer integral;

    private Integer grade;

    private String gender;
}
