package com.yunding.answer.dto;

import lombok.Data;

/**
 * 向前端反馈用户 学习计划
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class LearningPlanDto {


    /**
     * 昵称
     */
    private String userName;

    /**
     * 积分
     */
    private String integral;

    /**
     * 等级
     */
    private int grade;

    /**
     * 每日学习时长
     */
    private String dailyStudyTime;

    /**
     * 每日答题量
     */
    private String dailyExercisesQuantity;

    /**
     * 预定答题量
     */
    private String planningExercisesQuantity;

    /**
     * 预计完成日期
     */
    private int planningFinishedTime;

    /**
     * 累计学习天数
     */
    private String totalLearningTime;

    /**
     * 累计答题
     */
    private String totalExercisesQuantity;

}
