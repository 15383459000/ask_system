package com.yunding.answer.form;

import lombok.Data;

/**
 * @Author ycSong
 * @create 2020/3/9 19:12
 */
@Data
public class WrongQuestionForm {

    private String questionId;

    private String userAnswer;

    public WrongQuestionForm(String questionId, String userAnswer) {
        this.questionId = questionId;
        this.userAnswer = userAnswer;
    }
}
