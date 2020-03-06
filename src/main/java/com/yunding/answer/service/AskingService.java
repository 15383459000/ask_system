package com.yunding.answer.service;

import com.yunding.answer.dto.QuestionLibDto;

import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/5 15:21
 */
public interface AskingService {

    /**
     * 获取题库信息
     * @return
     */
    List<QuestionLibDto> getQuestionLib();
}
