package com.yunding.answer.form;

import lombok.Data;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class CheckAnswersForm {

    /**
     * 题目id
     */
    private List<String> questionIdList;

    /**
     * 用户答案集合
     */
    private List<String> userAnswerList;

}
