package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.QuestionListLoginDto;
import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.LoginSuccessForm;
import com.yunding.answer.form.RankingAreaForm;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.RankingService;
import com.yunding.answer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Cui
 * @Date: 2020/3/11
 * @Description:
 */
@RequestMapping("/ranking")
@Api(value = "CollectionController", tags = {"用户API"})
@RestController
@Slf4j
public class RankingController extends BaseController<UserInfoDto> {

    @Autowired
    private UserService userService;

    @Autowired
    RankingService rankingService;

    /**
     * 排行榜
     * @return
     */
    @ApiOperation(value = "排行榜")
    @GetMapping("/ranking")
    public ResultWrapper getRanking() {
        return ResultWrapper.successWithData(rankingService.getRanking());
    }


    /**
     * 根据地区展示排行榜
     * @param rankingAreaForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "根据地区展示排行榜")
    @GetMapping("/showRankingByArea")
    public ResultWrapper showRankingByArea(@Valid @RequestBody RankingAreaForm rankingAreaForm, BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto questionListLoginDto = userService.findRankingByArea(rankingAreaForm);
            return ResultWrapper.successWithData(questionListLoginDto);
        }catch (SysException e){
            log.info("RankingController.showRankingByArea");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 根据年级展示排行榜
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "根据年级展示排行榜")
    @GetMapping("/showRankingByGrade")
    public ResultWrapper showRankingByGrade(@Valid @RequestBody LoginSuccessForm loginSuccessForm, BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto questionListLoginDto = userService.findRankingByGrade(loginSuccessForm);
            return ResultWrapper.successWithData(questionListLoginDto);
        }catch (SysException e){
            log.info("RankingController.showRankingByArea");
            return ResultWrapper.failure(e.getMessage());
        }
    }
    /**
     * 根据学校展示排行榜
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "根据学校展示排行榜")
    @GetMapping("/showRankingBySchool")
    public ResultWrapper showRankingBySchool(@Valid @RequestBody LoginSuccessForm loginSuccessForm, BindingResult bindingResult){
        try {
            //参数检验
            validateParams(bindingResult);
            QuestionListLoginDto questionListLoginDto = userService.findRankingBySchool(loginSuccessForm);
            return ResultWrapper.successWithData(questionListLoginDto);
        }catch (SysException e){
            log.info("RankingController.showRankingByArea");
            return ResultWrapper.failure(e.getMessage());
        }
    }
}
