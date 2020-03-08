package com.yunding.answer.dto;

import lombok.Data;

/**
 * @Author ycSong
 * @create 2020/3/7 16:29
 */

@Data
public class AskingQuestionDto {

    private String questionId;

    private String questionContent;

    private String typeInfo;

    private String questionTag;

    private String checkA;

    private String checkB;

    private String checkC;

    private String checkD;
}
