package com.yunding.answer.service;

import com.yunding.answer.dto.AskingQuestionDto;
import com.yunding.answer.dto.QuestionLibDto;
import com.yunding.answer.form.AnswerForm;
import com.yunding.answer.form.LibIdForm;

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

    /**
     * 开始考试,通过题集id获取十道题目
     * @param libIdForm
     * @return
     */
    List<AskingQuestionDto> starAsking(LibIdForm libIdForm);

    /**
     * 判卷
     * @param answerForm
     * @return 答题记录id
     */
    String judgePaper(AnswerForm answerForm,String userId);
}
