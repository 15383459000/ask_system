package com.yunding.answer.mapper;

import com.yunding.answer.entity.QuestionInfo;
import com.yunding.answer.entity.WrongQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WrongMapper {

    /**
     * 通过用户id得到所有的错题类型
     * @param userId
     * @return
     */
//    @Select("select * " +
//            "from ask_system.wrong_questions_record " +
//            "where user_id=#{userId} ")
    List<WrongQuestion> showQuestionInfo(String userId);

    /**
     * 通过类型得到这个类型的错题
     * @param userId
     * @param type
     * @return
     */
    //查问题表
//    @Select("select * " +
//            "from ask_system.questions_info " +
//            "where user_id = #{userId} and course_type = #{type}")
    List<QuestionInfo> showQuestionByType(String userId, String type, Integer last, Integer next);
    //查错题表
//    @Select("select * " +
//            "from ask_system.wrong_questions_record " +
//            "where user_id = #{userId} and course_type = #{type}")
    List<WrongQuestion> showWrongByType(String userId, String type, Integer last, Integer next);

    /**
     * 通过用户id和题目id删除错题
     * @param userId
     * @param questionId
     */
//    @Delete("delete " +
//            "from ask_system.wrong_questions_record " +
//            "where user_id = #{userId} and question_Id =#{questionId} ")
    void deleteWrongQuestion(String userId, Integer questionId);
}
