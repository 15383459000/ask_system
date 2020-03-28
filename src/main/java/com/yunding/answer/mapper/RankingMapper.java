package com.yunding.answer.mapper;

import com.yunding.answer.dto.RankingDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Repository
public interface RankingMapper {

    /**
     * 排行榜 做题总数排序
     * @return
     */
    @Select("SELECT user_name, portrait, total_exercises_quantity " +
            "FROM user_info " +
            "ORDER BY total_exercises_quantity;")
    List<RankingDto> selectRanking();

}
