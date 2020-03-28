package com.yunding.answer.service;

import com.yunding.answer.dto.MessageSuccessDto;
import com.yunding.answer.dto.MessageSuccessLoginDto;
import com.yunding.answer.dto.QuestionListLoginDto;
import com.yunding.answer.form.LoginSuccessForm;

public interface CollectionService {

    /**
     * 展示所有收藏的题
     * @param loginSuccessForm
     * @return
     */
    QuestionListLoginDto showCollections(LoginSuccessForm loginSuccessForm);

    /**
     * 删除收藏的题
     * @param loginSuccessForm
     * @return
     */
    MessageSuccessDto deleteCollection(LoginSuccessForm<Integer> loginSuccessForm);

    /**
     * 搜索收藏里的题
     * @param loginSuccessForm
     * @return
     */
    QuestionListLoginDto searchCollection(LoginSuccessForm<String> loginSuccessForm);
}
