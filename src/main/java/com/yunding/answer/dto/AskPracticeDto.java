package com.yunding.answer.dto;

import lombok.Data;

/**
 * 获取题目 - 快速刷题（问答）
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class AskPracticeDto {

    private String questionId;
    private String questionTypeId;
    private String questionContent;
    private String questionTagId;

}
