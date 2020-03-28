package com.yunding.answer.service.impl;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.dto.LoginSuccessDto;
import com.yunding.answer.dto.MessageSuccessDto;
import com.yunding.answer.dto.MessageSuccessLoginDto;
import com.yunding.answer.dto.QuestionListLoginDto;
import com.yunding.answer.entity.QuestionInfo;
import com.yunding.answer.entity.UserInfo;
import com.yunding.answer.form.LoginSuccessForm;
import com.yunding.answer.mapper.CollectionMapper;
import com.yunding.answer.mapper.UserMapper;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/3/11
 * @Description:
 */
@Slf4j
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private CollectionMapper collectionMapper;

    /**
     * 展示收藏的题
     * @param loginSuccessForm
     * @return
     */
    @Override
    public QuestionListLoginDto showCollections(LoginSuccessForm loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))
        ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))){
            log.info("手机号与token信息不匹配");
            throw new SysException("违规操作！");
        }else {
            QuestionListLoginDto<QuestionInfo> questionListLoginDto = new QuestionListLoginDto();
            Integer last = (loginSuccessForm.getPage()-1)*loginSuccessForm.getLimit()+1;
            Integer next = loginSuccessForm.getPage()*loginSuccessForm.getLimit();
            List<QuestionInfo> list = collectionMapper.findCollectionById(user.getUserId(),last,next);
            questionListLoginDto.setObjects(list);
            return questionListLoginDto;
        }
    }

    /**
     * 取消收藏
     * @param loginSuccessForm
     * @return
     */
    @Override
    public MessageSuccessDto deleteCollection(LoginSuccessForm<Integer> loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("手机号未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))
        ||(!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))){
            log.info("手机号与token信息不匹配！");
            throw new SysException("违规操作！");
        }else {
            collectionMapper.deleteCollection(user.getUserId(),loginSuccessForm.getType());
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("取消收藏成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 搜索收藏里的题
     * @param loginSuccessForm
     * @return
     */
    @Override
    public QuestionListLoginDto searchCollection(LoginSuccessForm<String> loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("手机号未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))
        ||(!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))){
            log.info("手机号与token信息不匹配！");
            throw new SysException("违规操作！");
        }else {
            QuestionListLoginDto<QuestionInfo> questionListLoginDto = new QuestionListLoginDto();
            questionListLoginDto.setObjects(collectionMapper.searchCollection(user.getUserId(),loginSuccessForm.getType()));
            return questionListLoginDto;
        }
    }
}
