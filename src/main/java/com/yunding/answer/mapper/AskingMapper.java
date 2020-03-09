package com.yunding.answer.mapper;

import com.yunding.answer.dto.*;
import com.yunding.answer.form.AnswerRecordForm;
import com.yunding.answer.form.AnswerRecordInfoForm;
import com.yunding.answer.form.LibIdForm;
import com.yunding.answer.form.WrongQuestionForm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/5 14:29
 */
@Repository
public interface AskingMapper {

    /**
     * 获取题库信息
     * @return
     */
    List<QuestionLibDto> getQuestionLib();

    /**
     * 拿出题库中的题
     * @return
     */
    List<String> getQuestionsFromLib(LibIdForm libIdForm);

    /**
     * 通过题目id集合获取题目详细信息
     * @param qids
     * @return
     */
    List<AskingQuestionDto> getQuestionsInfo(List<String> qids);

    /**
     * 通过id
     * @param qids 题目id集合
     * @return 答案集合
     */
    String getAnswerById(String qids);

    /**
     * 插入答题记录
     * @param answerRecordForm 答题记录表单
     */
    void insertAskingRecord(AnswerRecordForm answerRecordForm);

    /**
     * 获取该用户最新的答题记录id
     * @return
     */
    String getNewRecordId(String userId);

    /**
     * 插入答题记录详情
     * @param answerRecordForms
     *
     */
    void insertAskingRecordInfo(List<AnswerRecordInfoForm> answerRecordForms,String newRecord);

    /**
     * 答题记录插入错题集
     * @param wrongQuestionForm
     * @param userId
     */
    void insertWrongQuestionsRecord(WrongQuestionForm wrongQuestionForm, String userId, String libraryId);

    /**
     * 从错题集中获取
     * @param userId
     * @return
     */
    List<QidAndWrongTimeDto> getQidAndWrongTime(String userId);

    /**
     * 查询每日做题时间以及总做题量
     * @param userId
     * @return
     */
    DailyTimeAndTotalAskNumDto getDailyTimeAndTotalAskNum(String userId);

    /**
     * 更新每日做题时间
     * @param
     */
    void updateDailyTime(String userId,String dailyTime);

    /**
     * 更新总做题时间
     * @param userId
     * @param totalAskNum
     */
    void updateTotalAskNum(String userId,String totalAskNum);

    /**
     * 更行错误次数
     * @param wrongTimes
     * @param qid
     * @param userId
     */
    void updateWrongTime(String wrongTimes,String qid,String userId);


}
