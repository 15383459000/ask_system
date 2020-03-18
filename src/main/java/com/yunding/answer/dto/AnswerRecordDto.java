package com.yunding.answer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Author ycSong
 * @create 2020/3/8 19:40
 */
@Data
public class AnswerRecordDto {

    private String answerId;

    private String usedTime;

    private String accuracy;

    private String libraryName;

    private String libraryRank;

    private String image;

    private String introduction;

    private String createAt;


}
