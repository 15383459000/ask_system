package com.yunding.answer.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class WrongQuestion {

    private Integer questionId;

    private String userId;

    private Integer wrongTime;

    private Date createAt;

    private String libraryId;

    private String userAnswer;

    private String courseType;
}
