package com.yunding.answer.service;

import com.yunding.answer.dto.RankingDto;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
public interface RankingService {

    /**
     * 排行榜
     * @return
     */
    List<RankingDto> getRanking();

}
