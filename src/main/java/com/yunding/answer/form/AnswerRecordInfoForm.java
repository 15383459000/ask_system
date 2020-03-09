package com.yunding.answer.form;

import lombok.Data;
import org.apache.catalina.User;

/**
 * @Author ycSong
 * @create 2020/3/8 20:24
 */
@Data
public class AnswerRecordInfoForm {


    private String questionId;

    private boolean isTrue;

    private String userAnswer;

    public AnswerRecordInfoForm(String questionId, boolean isTrue, String userAnswer) {
        this.questionId = questionId;
        this.isTrue = isTrue;
        this.userAnswer = userAnswer;
    }
}
