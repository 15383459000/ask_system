package com.yunding.answer.service;

import com.yunding.answer.dto.*;
import com.yunding.answer.form.AnswerForm;
import com.yunding.answer.form.LibIdForm;
import com.yunding.answer.form.RecordByTimeForm;
import com.yunding.answer.form.StageAnswerForm;

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

    /**
     * 开始闯关
     * @param libIdForm
     * @return
     */
    AskingQuestionDto starStage(LibIdForm libIdForm);

    /**
     * 闯关判卷
     * @param stageAnswerForm
     * @return
     */
    boolean judgeStage(StageAnswerForm stageAnswerForm,String userId);

    /**
     * 获取答案和解析
     * @param questionId
     * @return
     */
    AnswAndAnalyDto getAnswAndAna(String questionId);

    /**
     * 获取答题记录
     * @param recordByTimeForm 时间段
     * @return
     */
    List<AnswerRecordDto> getAnswerRecord(RecordByTimeForm recordByTimeForm, String userId);

    /**
     * 获取答题记录详情
     * @param answerId
     * @return
     */
    List<AnswerRecordInfoDto> getAnswRecorInfo(String answerId);
}
