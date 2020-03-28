package com.yunding.answer.service;

import com.yunding.answer.dto.MessageSuccessDto;
import com.yunding.answer.dto.MessageSuccessLoginDto;
import com.yunding.answer.dto.QuestionListLoginDto;
import com.yunding.answer.dto.WrongQuestionTypeDto;
import com.yunding.answer.form.DeleteWrongQuestionForm;
import com.yunding.answer.form.LoginSuccessForm;
import org.springframework.stereotype.Component;

@Component("wrongService")
public interface WrongService {

    /**
     * 查看错题，只展示类型
     * @param loginSuccessForm
     * @return
     */
    WrongQuestionTypeDto showQuestionType(LoginSuccessForm loginSuccessForm);

    /**
     * 根据类型查看题目详情
     * @param loginSuccessForm
     * @return
     */
    QuestionListLoginDto showQuestionInfoByType(LoginSuccessForm<String> loginSuccessForm);

    /**
     * 删除错题
     * @param deleteWrongQuestionForm
     * @return
     */
    MessageSuccessDto deleteWrongQuestion(DeleteWrongQuestionForm deleteWrongQuestionForm);
}
