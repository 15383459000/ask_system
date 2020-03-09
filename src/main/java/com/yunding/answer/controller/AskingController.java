package com.yunding.answer.controller;

import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.AskingQuestionDto;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.AnswerForm;
import com.yunding.answer.form.LibIdForm;
import com.yunding.answer.service.AskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/5 15:25
 */

@RestController
@RequestMapping("/asking")
public class AskingController extends BaseController<UserInfoDto> {

    @Autowired
    private AskingService askingService;

    /**
     * 获取题库信息
     * @return
     */
    @GetMapping("/questionLib")
    public ResultWrapper getQuestionLib(){
        return ResultWrapper.successWithData(askingService.getQuestionLib());
    }

    /**
     * 开始考试
     * @param libIdForm
     * @return
     */
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
     * 判卷
     * @param answerForm
     * @return
     */
    @PostMapping("/answer")
    public ResultWrapper judgePaper(@RequestBody AnswerForm answerForm){

//        try {
            return ResultWrapper.successWithData(askingService.judgePaper(answerForm,getCurrentUserInfo().getUserId()));
//        }catch (Exception e){
//            return ResultWrapper.failure(e.toString());
//        }
    }
}
