package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.*;
import com.yunding.answer.entity.UserInfo;
import com.yunding.answer.form.LoginSuccessForm;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Cui
 * @Date: 2020/3/10
 * @Description:
 */
@RequestMapping("/collection")
@Api(value = "CollectionController", tags = {"用户API"})
@RestController
@Slf4j
public class CollectionQuestionController extends BaseController<UserInfoDto> {

    @Autowired
    private CollectionService collectionService;
    /**
     * 展示所有收藏的题
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "展示收藏的题")
    @GetMapping("/showList")
    public ResultWrapper showCollections(@Valid @RequestBody LoginSuccessForm loginSuccessForm, BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto questionListLoginDto = collectionService.showCollections(loginSuccessForm);
            return ResultWrapper.successWithData(questionListLoginDto);
        }catch (SysException e){
            log.info("CollectionQuestionController.showCollections");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 取消收藏
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "取消收藏")
    @DeleteMapping("/delete")
    public ResultWrapper deleteCollection(LoginSuccessForm<Integer> loginSuccessForm, BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = collectionService.deleteCollection(loginSuccessForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("CollectionQuestionController.deleteCollection");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    @ApiOperation(value = "搜索收藏的题")
    @GetMapping("/searchCollection")
    public ResultWrapper searchCollection(LoginSuccessForm<String> loginSuccessForm,BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto questionListLoginDto = collectionService.searchCollection(loginSuccessForm);
            return ResultWrapper.successWithData(questionListLoginDto);
        }catch (SysException e){
            log.info("CollectionQuestionController.searchCollection");
            return ResultWrapper.failure(e.getMessage());
        }
    }
}
