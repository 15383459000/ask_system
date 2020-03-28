package com.yunding.answer.service.impl;

import com.yunding.answer.form.ServiceChatForm;
import com.yunding.answer.mapper.ServiceMapper;
import com.yunding.answer.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceMapper serviceMapper;

    /**
     * 申述
     * @param userId
     * @param serviceChatForm
     */
    @Override
    public boolean chatService(String userId, ServiceChatForm serviceChatForm) {
        String contents = serviceChatForm.getContents();
        try {
            serviceMapper.insertUserChat(userId, contents);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
