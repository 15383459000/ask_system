package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.entity.User;
import com.yunding.answer.form.*;
import com.yunding.answer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "UserController", tags = {"用户API"})
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserInfoDto> {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户验证码登录")
    @PutMapping("/login/password")
    public ResultWrapper loginPassword(@Valid @RequestBody UserLoginPhoneForm userLoginForm, BindingResult bindingResult) {

        /**
         * 参数验证
         */
        validateParams(bindingResult);
        User user = new User ();
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = userService.login(userLoginForm.getPhone(), userLoginForm.getCode());
        } catch (SysException e) {
            System.out.println(e.toString());
            return ResultWrapper.failure(e.getMessage());
        }
        return ResultWrapper.successWithData(tokenInfo+"userRole:"+user.getUserRole ());
    }

}
