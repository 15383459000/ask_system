package com.yunding.answer.service;

import com.yunding.answer.form.ServiceChatForm;

/**
 * @author HaoJun
 * @create 2020-03
 */
public interface ServiceService {

    /**
     * 申述
     * @param userId
     * @param serviceChatForm
     */
    boolean chatService(String userId, ServiceChatForm serviceChatForm);

}
