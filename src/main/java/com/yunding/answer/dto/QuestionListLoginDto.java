package com.yunding.answer.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Data
public class QuestionListLoginDto<T> {
    private List<T> objects;
}
