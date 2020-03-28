package com.yunding.answer.entity;

import lombok.Data;


/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class QuestionInfo {
    //题的id
    private Integer questionId;
    //题型id
    private Integer questionTypeId;
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
    //标签
    private Integer questionTagId;
    //类型
    private String courseType;
}
