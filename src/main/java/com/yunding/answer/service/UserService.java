package com.yunding.answer.service;

import com.yunding.answer.dto.*;
import com.yunding.answer.entity.UserInfo;
import com.yunding.answer.form.*;
import org.springframework.stereotype.Component;

@Component("userService")
public interface UserService {

    /**
     * 账号密码登录
     * @param userLoginPhoneForm
     * @return
     */
    MessageSuccessLoginDto loginByPassword(UserLoginPhoneForm userLoginPhoneForm);

    /**
     * 验证码登录
     * @param userLoginCodeForm
     * @return
     */
    MessageSuccessLoginDto loginByCode(UserLoginCodeForm userLoginCodeForm);

    /**
     * 用户注册
     * @param userRegisterForm
     * @return
     */
    MessageSuccessDto register(UserRegisterForm userRegisterForm);

    /**
     * 忘记密码
     * @param userRegisterForm
     * @return
     */
    MessageSuccessDto updatePasswordByPhone(UserRegisterForm userRegisterForm);

    /**
     * 修改密码通过手机号
     * @param updatePasswordForm
     * @return
     */
    MessageSuccessDto updatePasswordLoginByPhone(UpdatePasswordForm updatePasswordForm);
    /**
     * 通过旧密码修改新密码
     * @param updatePasswordForm
     * @return
     */
    MessageSuccessDto updatePasswordByPassword(UpdatePasswordForm updatePasswordForm);
    /**
     * 个人中心
     * @param loginSuccessForm
     * @return
     */
    UserPersonalInfoDto getUserInfo(LoginSuccessForm loginSuccessForm);

    /**
     * 修改个人中心
     * @param userUpdateInfoForm
     * @return
     */
    MessageSuccessDto updateUserInfo(UserUpdateInfoForm userUpdateInfoForm);

    /**
     * 退出登录
     * @param loginSuccessForm
     * @return
     */
    MessageSuccessDto userLogout(LoginSuccessForm loginSuccessForm);
    /**
     * 更改绑定的手机号
     * @param updatePhoneNumberForm
     * @return
     */
    MessageSuccessDto updatePhone(UpdatePhoneNumberForm updatePhoneNumberForm);


    /**
     * 通过地区得到排行榜
     * @param rankingAreaForm
     * @return
     */
    QuestionListLoginDto<RankingListDto> findRankingByArea(RankingAreaForm rankingAreaForm);

    /**
     * 根据年级得到排行榜
     * @param loginSuccessForm
     * @return
     */
    QuestionListLoginDto<RankingListDto> findRankingByGrade(LoginSuccessForm<String> loginSuccessForm);

    /**
     * 根据学校得到排行榜
     * @param loginSuccessForm
     * @return
     */
    QuestionListLoginDto<RankingListDto> findRankingBySchool(LoginSuccessForm<String> loginSuccessForm);



    /**
     * 获取用户学习计划
     * @param userId
     * @return
     */
    LearningPlanDto getLearningPlan(String userId);

    /**
     * 用户签到
     * @param userId
     */
    boolean userLearningSign(String userId);

    /**
     * 获取用户当前积分
     * @param userId
     * @return
     */
    PresentIntegralDto getPresentIntegral(String userId);

    /**
     * 学情分析 - 查看大数据面板
     * @param userId
     * @return
     */
    AnalysisDto getAnalysis(String userId);
}
