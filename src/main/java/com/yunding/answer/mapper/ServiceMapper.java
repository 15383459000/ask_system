package com.yunding.answer.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Repository
public interface ServiceMapper {

    /**
     * 插入用户申诉内容
     * @param userId
     * @param contents
     */
    @Insert("INSERT INTO representations (user_id, contents) VALUES (#{userId}, #{contents})")
    void insertUserChat(@Param("userId") String userId, @Param("contents") String contents);

}
