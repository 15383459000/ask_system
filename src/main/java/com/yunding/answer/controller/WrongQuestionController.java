package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.*;
import com.yunding.answer.form.DeleteWrongQuestionForm;
import com.yunding.answer.form.LoginSuccessForm;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.WrongService;
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
@RequestMapping("/wrong")
@Api(value = "WrongController", tags = {"用户API"})
@RestController
@Slf4j
public class WrongQuestionController extends BaseController<UserInfoDto> {

    @Autowired
    private WrongService wrongService;

    /**
     * 展示错题的类型
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "展示错题的类型")
    @GetMapping("/showType")
    public ResultWrapper showWrongType(@Valid @RequestBody LoginSuccessForm loginSuccessForm, BindingResult bindingResult){
        try{
            //参数检验
            validateParams(bindingResult);
            WrongQuestionTypeDto wrongQuestionTypeDto = wrongService.showQuestionType(loginSuccessForm);
            return ResultWrapper.successWithData(wrongQuestionTypeDto);
        }catch (SysException e){
            log.info("WrongQuestionController.showWrongType");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 通过题型查看错题
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "通过题型查看错题")
    @GetMapping("/showQuestionByType")
    public ResultWrapper showQuestionByType(@Valid @RequestBody LoginSuccessForm<String> loginSuccessForm,BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto wrongQuestionInfoDto = wrongService.showQuestionInfoByType(loginSuccessForm);
            return ResultWrapper.successWithData(wrongQuestionInfoDto);
        }catch (SysException e){
            log.info("WrongQuestionController.showQuestionByType");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 删除错题
     * @param deleteWrongQuestionForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "删除错题")
    @DeleteMapping("/deleteWrongQuestion")
    public ResultWrapper deleteWrongQuestion(@Valid @RequestBody DeleteWrongQuestionForm deleteWrongQuestionForm,BindingResult bindingResult){
        try{
            //参数检验
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = wrongService.deleteWrongQuestion(deleteWrongQuestionForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("WrongQuestionController.deleteWrongQuestion");
            return ResultWrapper.failure(e.getMessage());
        }
    }
}
