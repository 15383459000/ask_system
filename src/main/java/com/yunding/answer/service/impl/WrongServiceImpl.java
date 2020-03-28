package com.yunding.answer.service.impl;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.dto.*;
import com.yunding.answer.entity.QuestionInfo;
import com.yunding.answer.entity.WrongQuestion;
import com.yunding.answer.entity.UserInfo;
import com.yunding.answer.form.DeleteWrongQuestionForm;
import com.yunding.answer.form.LoginSuccessForm;
import com.yunding.answer.mapper.UserMapper;
import com.yunding.answer.mapper.WrongMapper;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.WrongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@Service
@Slf4j
public class WrongServiceImpl implements WrongService {

    @Autowired
    private WrongMapper wrongMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 查看错题，只展示类型
     * @param loginSuccessForm
     * @return
     */
    @Override
    public WrongQuestionTypeDto showQuestionType(LoginSuccessForm loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        //判断传入的这个手机号是否存在
        if(null == user){
            log.info("该手机未注册！");
            throw new SysException("违规操作！");
        //判断这个手机号和token是否对应
        }else if((!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId())
        ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken())))){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else{
            List<String> types = new ArrayList<String>();
            List<WrongQuestion> questions = wrongMapper.showQuestionInfo(user.getUserId());
            for(WrongQuestion q : questions){
                types.add(q.getCourseType());
            }
            WrongQuestionTypeDto wrongQuestionTypeDto = new WrongQuestionTypeDto();
            wrongQuestionTypeDto.setQuestionType(types);
            return wrongQuestionTypeDto;
        }
    }

    /**
     * 根据错题类型显示题型
     * @param loginSuccessForm
     * @return
     */
    @Override
    public QuestionListLoginDto showQuestionInfoByType(LoginSuccessForm<String> loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("该手机未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId())
                ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken())))){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else {
            QuestionListLoginDto<WrongQuestionInfo> questionListLoginDto = new QuestionListLoginDto();
            List<WrongQuestionInfo> wrongQuestionInfoList = new ArrayList<WrongQuestionInfo>();
            Integer last = (loginSuccessForm.getPage()-1)*loginSuccessForm.getLimit();
            Integer next = loginSuccessForm.getPage()*loginSuccessForm.getLimit();
            //查询问题表得到问题的基本的内容
            List<QuestionInfo> list = wrongMapper.showQuestionByType(user.getUserId(),loginSuccessForm.getType(),last,next);
            System.out.println(list.size());
            log.info("第一个对了");
            //查询错题表得到问题的错误时间和错误次数
            List<WrongQuestion> wrongQuestionList = wrongMapper.showWrongByType(user.getUserId(),loginSuccessForm.getType(),last,next);
            //合并
            System.out.println(wrongQuestionList.size());
            for(QuestionInfo q:list) {
                WrongQuestionInfo w = new WrongQuestionInfo();
                w.setQuestionContent(q.getQuestionContent());
                w.setAnalysis(q.getAnalysis());
                w.setAnswer(q.getAnswer());
                w.setCheckA(q.getCheckA());
                w.setCheckB(q.getCheckB());
                w.setCheckC(q.getCheckC());
                w.setCheckD(q.getCheckD());
                w.setDifficulty(q.getDifficulty());
                w.setType(q.getCourseType());
                for(WrongQuestion l : wrongQuestionList) {
                    if(q.getQuestionId()==l.getQuestionId()) {
                        w.setCreateAt(l.getCreateAt());
                        w.setWrongTime(l.getWrongTime());
                        break;
                    }
                }
                wrongQuestionInfoList.add(w);
            }
            questionListLoginDto.setObjects(wrongQuestionInfoList);
            return questionListLoginDto;
        }
    }

    /**
     * 删除错题
     * @param deleteWrongQuestionForm
     * @return
     */
    public MessageSuccessDto deleteWrongQuestion(DeleteWrongQuestionForm deleteWrongQuestionForm){
        UserInfo user = userMapper.selectByPhone(deleteWrongQuestionForm.getLoginSuccessForm().getPhone());
        if(null == user){
            log.info("用户未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(deleteWrongQuestionForm.getLoginSuccessForm().getAccessToken()))
                ||(!redisRepository.selectAccessToken(deleteWrongQuestionForm.getLoginSuccessForm().getAccessToken()).equals(user.getUserId()))){
            log.info("用户id和token不对应");
            throw new SysException("违规操作！");
        }else {
            wrongMapper.deleteWrongQuestion(user.getUserId(),deleteWrongQuestionForm.getQuestionId());
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("成功删除！");
            return messageSuccessDto;
        }
    }
}
