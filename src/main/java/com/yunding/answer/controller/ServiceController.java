package com.yunding.answer.controller;

import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.ServiceChatForm;
import com.yunding.answer.service.ServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Api(value = "ServiceController", tags = {"服务类API"})
@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController<UserInfoDto> {

    @Autowired
    ServiceService serviceService;

    /**
     * 申述
     * @param serviceChatForm
     * @return
     */
    @ApiOperation(value = "用户申述")
    @PostMapping("/chat")
    public ResultWrapper chatService(ServiceChatForm serviceChatForm) {
        if (serviceService.chatService(getCurrentUserInfo().getUserId(), serviceChatForm)) {
            return ResultWrapper.success();
        } else {
            return ResultWrapper.failure();
        }
    }

}
