package com.yunding.answer.mapper;

import com.yunding.answer.entity.QuestionInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionMapper {

    /**
     * 通过id找到所有的收藏的题
     * @param userId
     * @return
     */
//    @Select("SELECT * " +
//            "FROM ask_system.questions_info " +
//            "WHERE question_id = " +
//            "any(SELECT question_id from ask_system.question_collection WHERE user_id = #{userId})")
    List<QuestionInfo> findCollectionById(String userId, Integer last, Integer next);

    /**
     * 删除收藏的题
     * @param userId
     * @param questionId
     */
//    @Delete("delete " +
//            "from ask_system.question_collection " +
//            "where user_id = #{userId} and question_Id = #{questionId} ")
    void deleteCollection(String userId, Integer questionId);

    /**
     * 搜索收藏的题
     * @param userId
     * @param key
     * @return
     */
//    @Select("SELECT * " +
//            "FROM ask_system.questions_info " +
//            "WHERE (question_id = " +
//            "ANY(SELECT question_id FROM ask_system.question_collection WHERE user_id = #{userId})) " +
//            "AND (question_content LIKE '%${key}%' OR checkA LIKE '%${key}%' OR checkB LIKE '%${key}%' " +
//            "OR checkC LIKE '%${key}%' OR checkD LIKE '%${key}%' OR course_type LIKE '%${key}%')")
    List<QuestionInfo> searchCollection(String userId, String key);
}
