package com.yunding.answer.controller;

import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.AskingQuestionDto;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.*;
import com.yunding.answer.service.AskingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/5 15:25
 */

@Api(value = "AskingController", tags = {"答题API"})
@RestController
@RequestMapping("/asking")
public class AskingController extends BaseController<UserInfoDto> {

    @Autowired
    private AskingService askingService;

    /**
     * 获取题库信息
     * @return
     */
    @ApiOperation(value="获取所有题集列表", notes="获取所有题集列表",produces = "application/json")
    @GetMapping("/questionLib")
    public ResultWrapper getQuestionLib(){
        return ResultWrapper.successWithData(askingService.getQuestionLib());
    }

    /**
     * 开始答题
     * @param libIdForm
     * @return
     */
    @ApiOperation(value="分类答题开始答题", notes="分类答题开始答题",produces = "application/json")
    @PostMapping("/")
    public ResultWrapper starAsking(@RequestBody LibIdForm libIdForm){

        try {
            List<AskingQuestionDto> askingQuestionDtos = askingService.starAsking(libIdForm);
            return ResultWrapper.successWithData(askingQuestionDtos);
        } catch (Exception e) {
            return ResultWrapper.failure(e.toString());
        }

    }

    /**
     * 分类答题判卷
     * @param answerForm
     * @return
     */
    @ApiOperation(value="分类答题判卷", notes="分类答题判卷",produces = "application/json")
    @PostMapping("/answer")
    public ResultWrapper judgePaper(@RequestBody AnswerForm answerForm){

        try {
            return ResultWrapper.successWithData(askingService.judgePaper(answerForm,getCurrentUserInfo().getUserId()));
        }catch (Exception e){
            return ResultWrapper.failure(e.toString());
        }
    }

    /**
     * 开始闯关
     * @param libIdForm
     * @return
     */
    @ApiOperation(value="开始闯关", notes="开始闯关",produces = "application/json")
    @PostMapping("/stage")
    public ResultWrapper starStage(@RequestBody LibIdForm libIdForm){
        try {
            AskingQuestionDto askingQuestionDto = askingService.starStage(libIdForm);
            return ResultWrapper.successWithData(askingQuestionDto);
        } catch (Exception e) {
            return ResultWrapper.failure(e.toString());
        }
    }

    /**
     * 闯关判卷
     * @param stageAnswerForm
     * @return
     */
    @ApiOperation(value="闯关判卷", notes="闯关判卷",produces = "application/json")
    @PostMapping("/judgeStage")
    public ResultWrapper judgeStage(@RequestBody StageAnswerForm stageAnswerForm){
        return ResultWrapper.successWithData(askingService.judgeStage(stageAnswerForm,getCurrentUserInfo().getUserId()));
    }

    /**
     * 获取答案和解析
     * @param questionId
     * @return
     */
    @ApiOperation(value="获取答案和解析", notes="获取答案和解析",produces = "application/json")
    @GetMapping("/answerAndAnalysis")
    public ResultWrapper getAnswerAndAnalysis(String questionId){
        return ResultWrapper.successWithData(askingService.getAnswAndAna(questionId));
    }

    /**
     * 获取答题记录
     * @param recordByTimeForm
     * @return
     */
    @ApiOperation(value="获取答题记录", notes="获取答题记录",produces = "application/json")
    @PostMapping("/answerRecordByTime")
    public ResultWrapper getAnswerRecord(@RequestBody RecordByTimeForm recordByTimeForm){
        return ResultWrapper.successWithData(askingService.getAnswerRecord(recordByTimeForm,getCurrentUserInfo().getUserId()));
    }

    /**
     * 获取答题记录详情
     * @param answerId
     * @return
     */
    @ApiOperation(value="获取答题记录详情", notes="获取答题记录详情",produces = "application/json")
    @GetMapping("/answerRecordInfo")
    public ResultWrapper getAnswRecoInfo(String answerId){
        return ResultWrapper.successWithData(askingService.getAnswRecorInfo(answerId));
    }

    /**
     * 获取题目 - 快速刷题（选择填空）
     * @param practiceForm
     * @return
     */
    @ApiOperation(value = "获取题目 - 快速刷题（选择填空）")
    @PostMapping("/practice/quick")
    public ResultWrapper postQuickPractice(@RequestBody PracticeForm practiceForm) {
        return ResultWrapper.successWithData(askingService.getQuickPracticeList(practiceForm));
    }

    /**
     * 获取题目 - 快速刷题（问答）
     * @param practiceForm
     * @return
     */
    @ApiOperation(value = "获取题目 - 快速刷题（问答）")
    @PostMapping("/practice/ask")
    public ResultWrapper postAskPractice(@RequestBody PracticeForm practiceForm) {
        return ResultWrapper.successWithData(askingService.getAskPracticeList(practiceForm));
    }

    /**
     * 练习题 - 判卷
     * @param checkAnswersForm
     * @return
     */
    @ApiOperation(value = "练习题 - 判卷")
    @PostMapping("/check_answers")
    public ResultWrapper checkAnswers(@RequestBody CheckAnswersForm checkAnswersForm) {
        try {
            return ResultWrapper.successWithData(askingService.checkAnswers(checkAnswersForm,getCurrentUserInfo().getUserId()));
        }catch (Exception e){
            e.printStackTrace();
            return ResultWrapper.failure(e.toString());
        }
    }
}
