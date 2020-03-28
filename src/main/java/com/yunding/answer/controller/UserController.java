package com.yunding.answer.controller;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.*;
import com.yunding.answer.form.*;
import com.yunding.answer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(value = "UserController", tags = {"用户API"})
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController<UserInfoDto> {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")

    public ResultWrapper register(@Valid @RequestBody UserRegisterForm userRegisterForm,BindingResult bindingResult){
        try{
            //参数校验
            validateParams(bindingResult);
            MessageSuccessDto registerSuccessDto = userService.register(userRegisterForm);
            return ResultWrapper.successWithData(registerSuccessDto);
        }catch (SysException e){
            log.info("UserController.register");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 用户验证码登录
     * @param userLoginForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "用户验证码登录")
    @PostMapping("/login/code")
    public ResultWrapper loginPassword(@Valid @RequestBody UserLoginCodeForm userLoginForm, BindingResult bindingResult) {
        try {
            //参数校验
            validateParams(bindingResult);
            MessageSuccessLoginDto messageSuccessLoginDto = userService.loginByCode(userLoginForm);
            return ResultWrapper.successWithData(messageSuccessLoginDto);
        } catch (SysException e) {
            log.info("UserController.loginPassword");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 用户密码登录
     * @param userLoginPhoneForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "账号密码登录")
    @PostMapping("/login/password")
    public ResultWrapper login(@Valid @RequestBody UserLoginPhoneForm userLoginPhoneForm,BindingResult bindingResult){
        try{
            //参数校验
            validateParams(bindingResult);
            MessageSuccessLoginDto messageSuccessLoginDto = null;
            messageSuccessLoginDto = userService.loginByPassword(userLoginPhoneForm);
            return ResultWrapper.successWithData(messageSuccessLoginDto);
        }catch (SysException e){
            log.info("UserController.login");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 忘记密码
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "忘记密码")
    @PostMapping("/updatePassword")
    public ResultWrapper updatePasswordByPhoneToLogin(@Valid @RequestBody UserRegisterForm userRegisterForm,BindingResult bindingResult){
        try{
            //参数验证
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = null;
            messageSuccessDto = userService.updatePasswordByPhone(userRegisterForm);
            messageSuccessDto.setMessage("重置密码成功,请登录！");
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("UserController.updatePasswordByPhoneToLogin");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 通过手机验证码修改密码
     * @param updatePasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "通过手机验证码修改密码")
    @PostMapping("/password/updateByCode")
    public ResultWrapper updatePasswordByPhone(@Valid @RequestBody UpdatePasswordForm updatePasswordForm,BindingResult bindingResult){
        try{
            //参数验证
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = userService.updatePasswordLoginByPhone(updatePasswordForm);
            messageSuccessDto.setMessage("重置密码成功，请重新登录！");
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("UserController.updatePasswordByPhone");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 通过旧密码修改密码
     * @param updatePasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "旧密码修改密码")
    @PostMapping("/updateByPassword")
    public ResultWrapper updatePasswordByPassword(@Valid @RequestBody UpdatePasswordForm updatePasswordForm,BindingResult bindingResult){
        try{
            //参数验证
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = userService.updatePasswordByPassword(updatePasswordForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 个人中心
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "个人中心")
    @GetMapping("/info")
    public ResultWrapper getUserInfo(@Valid @RequestBody LoginSuccessForm loginSuccessForm,BindingResult bindingResult){
        try{
            //参数检验
            validateParams(bindingResult);
            UserPersonalInfoDto userInfoDto = userService.getUserInfo(loginSuccessForm);
            return ResultWrapper.successWithData(userInfoDto);
        }catch (SysException e){
            log.info("UserController.getUserInfo");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 修改个人中心
     * @param userUpdateInfoForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改个人信息")
    @PutMapping("/updateInfo")
    public ResultWrapper updateUserInfo(@Valid @RequestBody UserUpdateInfoForm userUpdateInfoForm,BindingResult bindingResult){
        try{
            //检验参数
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = userService.updateUserInfo(userUpdateInfoForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("UserController.updateUserInfo");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 退出登录
     * @param loginSuccessForm
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PutMapping("/logout")
    public ResultWrapper updatePhoneNumber(@Valid @RequestBody LoginSuccessForm loginSuccessForm,BindingResult bindingResult) {
        try {
            //参数检验
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = userService.userLogout(loginSuccessForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 更改绑定的手机号
     * @param updatePhoneNumberForm
     * @return
     */
    @ApiOperation(value = "更改绑定的手机号")
    @PutMapping("/updatePhone")
    public ResultWrapper updatePhoneNumber(@Valid @RequestBody UpdatePhoneNumberForm updatePhoneNumberForm,BindingResult bindingResult){
        try{
            //参数检验
            validateParams(bindingResult);
            MessageSuccessDto messageSuccessDto = userService.updatePhone(updatePhoneNumberForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("UserController.updatePhoneNumber");
            return ResultWrapper.failure(e.getMessage());
        }
    }


    /**
     * 获取用户学习计划
     * @return
     */
    @ApiOperation(value = "获取用户学习计划")
    @GetMapping("/plan")
    public ResultWrapper getLearningPlan() {
        return ResultWrapper.successWithData(userService.getLearningPlan(getCurrentUserInfo().getUserId()));
    }


    /**
     * 用户签到
     * @return
     */
    @ApiOperation(value = "用户签到")
    @GetMapping("/learning_sign")
    public ResultWrapper userLearningSign() {
        try {
            if (userService.userLearningSign(getCurrentUserInfo().getUserId())) {
                return ResultWrapper.success();
            } else {
                return ResultWrapper.failure("已签到");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultWrapper.failure("签到失败");
        }
    }

    /**
     * 查询用户当前积分
     * @return
     */
    @ApiOperation(value = "查询用户当前积分")
    @GetMapping("/present_integral")
    public ResultWrapper getPresentIntegral() {
        return ResultWrapper.successWithData(userService.getPresentIntegral(getCurrentUserInfo().getUserId()));
    }

    /**
     * 学情分析 - 查看大数据面板
     * @return
     */
    @ApiOperation(value = "学情分析 - 查看大数据面板")
    @GetMapping("/analysis")
    public ResultWrapper getAnalysis() {
        return ResultWrapper.successWithData(userService.getAnalysis(getCurrentUserInfo().getUserId()));
    }
}
