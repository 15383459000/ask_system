package com.yunding.answer.mapper;

import com.yunding.answer.dto.QuestionLibDto;
import org.apache.ibatis.annotations.Mapper;
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
}
