package com.yunding.answer.service.impl;

import com.yunding.answer.dto.RankingDto;
import com.yunding.answer.mapper.RankingMapper;
import com.yunding.answer.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    RankingMapper rankingMapper;

    /**
     * 排行榜
     * @return
     */
    @Override
    public List<RankingDto> getRanking() {
        return rankingMapper.selectRanking();
    }

}
