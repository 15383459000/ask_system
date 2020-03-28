package com.yunding.answer.mapper;

import com.yunding.answer.dto.*;
import com.yunding.answer.form.AnswerRecordForm;
import com.yunding.answer.form.AnswerRecordInfoForm;
import com.yunding.answer.form.LibIdForm;
import com.yunding.answer.form.WrongQuestionForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
     * 拿出题库中的题id
     * @return
     */
    List<String> getQuestionsFromLib(LibIdForm libIdForm);

    /**
     * 拿出题库中的非问答题选择判断填空题id
     * @param libIdForm
     * @return
     */
    List<String> getNorQuesFromLib(LibIdForm libIdForm);

    /**
     * 通过题目id集合获取题目详细信息
     * @param qids
     * @return
     */
    List<AskingQuestionDto> getQuestionsInfo(List<String> qids);

    /**
     * 获取该题目详情
     * @param qid
     * @return
     */
    AskingQuestionDto getQuestionInfo(String qid);

    /**
     * 通过id
     * @param qid 题目id
     * @return 答案集合
     */
    String getAnswerById(String qid);

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
    void insertAskingRecordInfo(List<AnswerRecordInfoForm> answerRecordForms, String newRecord);

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
     * 获取每日做题时间
     * @param userId
     * @return
     */
    Integer getDailyTime(String userId);

    /**
     * 获取总做题量
     * @param userId
     * @return
     */
    Integer getTotalAskNum(String userId);

    /**
     * 更新每日做题时间
     * @param
     */
    void updateDailyTime(String userId, String dailyTime);

    /**
     * 更新总做题时间
     * @param userId
     * @param totalAskNum
     */
    void updateTotalAskNum(String userId, String totalAskNum);

    /**
     * 更行错误次数
     * @param wrongTimes
     * @param qid
     * @param userId
     */
    void updateWrongTime(String wrongTimes, String qid, String userId);

    /**
     * 获取闯关数
     * @param userId
     * @return
     */
    String getStageNum(String userId);

    /**
     * 更新闯关数
     * @param userId
     * @return
     */
    void updateStageNum(String userId, String stageNum);

    /**
     * 插入闯关数
     * @param userId
     * @return
     */
    void insertStageNum(String userId);

    /**
     * 获取题目解析
     * @param qid
     * @return
     */
    AnswAndAnalyDto getAnswerAndAnalysis(String qid);


    /**
     * 查找时间段内的答题记录结果
     * @param stopTime 结束时间
     * @param startTime 开始时间
     * @param userId 用户id
     * @return
     */
    List<AnswerRecordDto> getAnswerRecord(long startTime, long stopTime, String userId);

    /**
     * 获取答题记录详情
     * @param answerId
     * @return
     */
    List<AnswerRecordInfoDto> getAnswRecoInfo(String answerId);

    /**
     * @author HaoJun
     * @create 2020-03
     */

    /**
     * 获取快速答题题目（选择填空）
     * @param libraryId
     * @return
     */
    @Select("SELECT questions_info.question_id, question_type_id, question_content, " +
            "checkA, checkB, checkC, checkD, question_tag_id " +
            "FROM questions_info, question_form_library " +
            "WHERE question_form_library.library_id = #{libraryId} " +
            "AND question_form_library.question_id = questions_info.question_id " +
            "AND (question_type_id = 1 OR question_type_id = 2 OR question_type_id = 3) " +
            ";")
    List<QuickPracticeDto> selectQuickPracticeList(@Param("libraryId") int libraryId);

    /**
     * 获取题目 - 快速刷题（问答）
     * @param LibraryId
     * @return
     */
    @Select("SELECT questions_info.question_id, question_type_id, question_content, question_tag_id " +
            "FROM questions_info, question_form_library " +
            "WHERE question_form_library.library_id = #{libraryId} " +
            "AND question_form_library.question_id = questions_info.question_id " +
            "AND question_type_id = 4 " +
            ";")
    List<AskPracticeDto> selectAskPracticeList(@Param("libraryId") int LibraryId);

    /**
     * 根据问题id获取答案
     * @param questionId
     * @return
     */
    @Select("SELECT answer FROM questions_info WHERE question_id = #{questionId};")
    String selectAnswerById(@Param("questionId") String questionId);

    /**
     * 增加做题量
     * @param userId
     */
    @Update("UPDATE user_info SET total_exercises_quantity = total_exercises_quantity + 10 WHERE user_id = #{userId};")
    void updateExercisesQuantity(@Param("userId") String userId);

    /**
     * 查询时间戳更新时间
     * @param userId
     * @return
     */
    @Select("SELECT updateAt FROM learning_plan WHERE user_id = #{userId};")
    Date selectLearningDaysUpdateTime(@Param("userId") String userId);

    /**
     * 增加累计学习天数
     * @param userId
     */
    @Update("UPDATE learning_plan SET total_learning_time = total_learning_time + 1 WHERE user_id = #{userId};")
    void updateLearningDays(@Param("userId") String userId);



}
