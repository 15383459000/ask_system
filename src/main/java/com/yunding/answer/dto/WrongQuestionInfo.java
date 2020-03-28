package com.yunding.answer.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class WrongQuestionInfo {
    //题目内容
    private String questionContent;
    //A选项内容
    private String checkA;
    //B选项内容
    private String checkB;
    //C选项内容
    private String checkC;
    //D选项内容
    private String checkD;
    //答案
    private String answer;
    //解析
    private String analysis;
    //难度
    private Integer difficulty;
    //题型
    private String type;
    //错误时间
    private Date CreateAt;
    //错误次数
    private Integer wrongTime;
}
