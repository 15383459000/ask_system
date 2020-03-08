package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.*;
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


    public void login(){

    }

}
