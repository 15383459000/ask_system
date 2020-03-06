package com.yunding.answer.controller;

import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.service.AskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
