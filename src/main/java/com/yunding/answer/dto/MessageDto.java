package com.yunding.answer.dto;

import lombok.Data;

/**
 * @author ycSong
 * @version 1.0
 * @date 2019/7/31 18:22
 */
@Data
public class MessageDto {

    /**
     * 验证码
     */
    private int code;

    /**
     * 状态
     */
    private String state;

    public MessageDto(){}

    public MessageDto(int code, String state) {
        this.code = code;
        this.state = state;
    }
}
