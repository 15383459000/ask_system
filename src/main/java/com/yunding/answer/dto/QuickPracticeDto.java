package com.yunding.answer.dto;

import lombok.Data;

/**
 * 向前端返回快速刷题题目（选择填空）
 *
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class QuickPracticeDto {

    /**
     * 题目 id
     */
    private String questionId;

    /**
     * 题目类型 id
     */
    private String questionTypeId;

    /**
     * 题目内容
     */
    private String questionContent;

    /**
     * 选项 A
     */
    private String checkA;

    /**
     * 选项 B
     */
    private String checkB;

    /**
     * 选项 C
     */
    private String checkC;

    /**
     * 选项 D
     */
    private String checkD;

    /**
     * 标签 id
     */
    private String questionTagId;



}
