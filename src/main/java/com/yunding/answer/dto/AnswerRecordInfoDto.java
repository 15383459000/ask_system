package com.yunding.answer.dto;

import lombok.Data;

/**
 * @Author ycSong
 * @create 2020/3/18 14:26
 */
@Data
public class AnswerRecordInfoDto {

    private String questionId;

    private String typeInfo;

    private String questionContent;

    private String checkA;

    private String checkB;

    private String checkC;

    private String checkD;

    private String tagInfo;

    private String userAnswer;

    private String isTrue;
}
