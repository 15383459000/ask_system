package com.yunding.answer.dto;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/3/11
 * @Description:
 */
@Data
public class RankingListDto {
    private Integer ranking;
    private String username;
    private String portrait;
    private Integer totalExercisesQuantity;
}
