package com.yunding.answer.controller;

import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.MessageDto;
import com.yunding.answer.form.NumberForm;
import com.yunding.answer.util.ShortMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ycSong
 * @version 1.0
 * @date 2019/7/31 18:02
 */
@RequestMapping("/message")
@RestController
@Slf4j
public class ShortMessageController {



    @Autowired
    private ShortMessageUtil shortMessageUtil;

    @PutMapping("/")
    public ResultWrapper getSsm(@RequestBody NumberForm number) {
        //先设置为成功状态
        String successState = "OK";
        try {
            //发送信息
            MessageDto messageDto=shortMessageUtil.getSsm(number);
            //如果状态为ok为成功
            if (successState.equals(messageDto.getState())) {
                return ResultWrapper.success("发送成功");
            }else {
                return ResultWrapper.failure("发送失败");
            }
        } catch (Exception e) {
            return ResultWrapper.failure(e.toString ());
        }

    }

}



