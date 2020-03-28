package com.yunding.answer.service.impl;

import com.yunding.answer.common.util.TokenUtil;
import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.dto.*;
import com.yunding.answer.entity.LearningSign;
import com.yunding.answer.entity.UserInfo;
import com.yunding.answer.form.*;
import com.yunding.answer.mapper.UserMapper;
import com.yunding.answer.redis.RedisRepository;
import com.yunding.answer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Cui
 * @Date: 2020/3/8
 * @Description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 账号密码登录
     * @param userLoginPhoneForm
     * @return
     */
    @Override
    public MessageSuccessLoginDto loginByPassword(UserLoginPhoneForm userLoginPhoneForm) {
        //密码经过md5加密处理
        String result = UserServiceImpl.getMd5Password(userLoginPhoneForm.getPassword());
        UserInfo user = userMapper.selectByPhone(userLoginPhoneForm.getPhone());
        if(null == user){
            log.info("用户不存在！");
            throw new SysException("手机号不存在！");
        }else {
            if(!user.getPassword().equals(result)){
                log.info("账号或密码错误");
                throw new SysException("账号或密码错误");
            }else {
                //判断是否异地登录
                String oldToken = redisRepository.selectAccessToken(user.getUserId());
                if(null != oldToken){
                    log.info("异地登录！");
                    //如果存在异地登录，删除原有的token
                    redisRepository.deleteAccessToken(oldToken);
                    redisRepository.deleteLoginAccessToken(user.getUserId());
                }
                //得到一个accessToken，生成tokenInfo
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setAccessToken(TokenUtil.genToken());
                tokenInfo.setUserId(user.getPhoneNumber());
                MessageSuccessLoginDto messageSuccessLoginDto = UserServiceImpl.getMessageSuccessLoginDto(user,tokenInfo);
                //保存这个用户和token信息
                redisRepository.saveAccessToken(tokenInfo);
                redisRepository.saveLoginAccessToken(tokenInfo);
                return messageSuccessLoginDto;
            }
        }
    }

    /**
     * 用户验证码登录
     * @param userLoginCodeForm
     * @return
     */
    @Override
    public MessageSuccessLoginDto loginByCode(UserLoginCodeForm userLoginCodeForm) {

        UserInfo user = userMapper.selectByPhone(userLoginCodeForm.getPhone());
        if(null == user){
            log.info("异地登录！");
            throw new SysException("该手机号未进行注册！");
        }else {
            if(!redisRepository.selectMessageCodeByPhone(userLoginCodeForm.getPhone()).equals(userLoginCodeForm.getCode())){
                log.info("验证码错误！");
                throw new SysException("验证码错误！");
            }else {
                String oldToken = redisRepository.selectAccessToken(user.getUserId());
                //判断是否存在异地登录
                if(null != oldToken){
                    log.info("异地登录！");
                    //如果存在异地登录，删除原有的token
                    redisRepository.deleteAccessToken(oldToken);
                    redisRepository.deleteLoginAccessToken(user.getUserId());
                }
                //得到token，生成tokenInfo对象
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setUserId(user.getUserId());
                tokenInfo.setAccessToken(TokenUtil.genToken());
                //保存到redis
                redisRepository.saveAccessToken(tokenInfo);
                redisRepository.saveLoginAccessToken(tokenInfo);
                //设置返回值
                MessageSuccessLoginDto messageSuccessLoginDto = UserServiceImpl.getMessageSuccessLoginDto(user,tokenInfo);
                return messageSuccessLoginDto;
            }
        }
    }

    /**
     * 用户注册
     * @param userRegisterForm
     * @return
     */
    @Override
    public MessageSuccessDto register(UserRegisterForm userRegisterForm) {
        if(redisRepository.selectMessageCodeByPhone(userRegisterForm.getPhone()) == null){
            log.info("系统未能检测到您的验证码，请稍后再试");
            throw new SysException ( "系统未能检测到您的验证码，请稍后再试" );
        }else if(!redisRepository.selectMessageCodeByPhone(userRegisterForm.getPhone()).equals(userRegisterForm.getCode())){
            log.info("验证码错误！");
            throw new SysException("验证码错误！");
        }else {
            if(null != userMapper.selectByPhone(userRegisterForm.getPhone())){
                log.info("该手机号已被注册！");
                throw new SysException("该手机号已被注册！");
            }else {
                log.info("验证成功！");
                //默认头像
                String userImage = "https://yc-song.oss-cn-beijing.aliyuncs.com/image/%E4%BA%91%E9%A1%B6%E4%B9%A6%E9%99%A2logo.jpg";
                //默认昵称
                String username = "闯关小勇士";
                //生成的id
                String userId = UUID.randomUUID().toString().replace("-","");
                //md5加密后的密码
                String result = UserServiceImpl.getMd5Password(userRegisterForm.getPassword());
                //默认性别为男
                String userGender = "男";
                //默认积分数为0
                Integer integral = 0;
                //等级默认为0
                Integer userGrade = 0;
                //闯到的关卡默认为0
                Integer priorStage = 0;
                //总的做题数默认为0
                Integer totalExercisesQuantity = 0;
                userMapper.insertNewUser(userRegisterForm.getPhone(),result,userId,username,userImage,userGender,userGrade,priorStage,integral,totalExercisesQuantity);
                MessageSuccessDto registerSuccessDto = new MessageSuccessDto();
                registerSuccessDto.setMessage("注册成功！");
                return registerSuccessDto;
            }
        }
    }

    /**
     * 忘记密码
     * @param userRegisterForm
     * @return
     */
    @Override
    public MessageSuccessDto updatePasswordByPhone(UserRegisterForm userRegisterForm) {
        UserInfo user = userMapper.selectByPhone(userRegisterForm.getPhone());
        if(null == user){
            log.info("用户不存在！");
            throw new SysException("用户不存在！");
        }else if(null == redisRepository.selectMessageCodeByPhone(userRegisterForm.getPhone())){
            log.info("系统未能检测到您的验证码，请稍后再试");
            throw new SysException("检测不到与手机号对应的验证码");
        }else if(!redisRepository.selectMessageCodeByPhone(userRegisterForm.getPhone()).equals(userRegisterForm.getCode())){
            log.info("验证码错误!");
            throw new SysException("验证码错误！");
        }else {
            String oldToken = redisRepository.selectLoginAccessToken(user.getUserId());
            String result = UserServiceImpl.getMd5Password(userRegisterForm.getPassword());
            userMapper.updatePasswordByPhone(userRegisterForm.getPhone(),result.toString());
            if(oldToken != null){
                //如果存在token，删除旧的token
                redisRepository.deleteLoginAccessToken(user.getUserId());
                redisRepository.deleteAccessToken(oldToken);
            }
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("重置密码成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 重置密码
     * @param updatePasswordForm
     * @return
     */
    @Override
    public MessageSuccessDto updatePasswordLoginByPhone(UpdatePasswordForm updatePasswordForm) {
        UserInfo user = userMapper.selectByPhone(updatePasswordForm.getLoginSuccessForm().getPhone());
        if(null == user){
            log.info("用户不存在！");
            throw new SysException("违规操作");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(updatePasswordForm.getLoginSuccessForm().getAccessToken())
                ||(!redisRepository.selectAccessToken(updatePasswordForm.getLoginSuccessForm().getAccessToken()).equals(user.getUserId())))){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        } else if(null == redisRepository.selectMessageCodeByPhone(updatePasswordForm.getLoginSuccessForm().getPhone())){
            log.info("系统未能检测到您的验证码，请稍后再试");
            throw new SysException("检测不到与手机号对应的验证码");
        }else if(!redisRepository.selectMessageCodeByPhone(updatePasswordForm.getLoginSuccessForm().getPhone()).equals(updatePasswordForm.getLoginSuccessForm().getType())){
            log.info("验证码错误!");
            throw new SysException("验证码错误！");
        }else {
            String oldToken = redisRepository.selectLoginAccessToken(user.getUserId());
            String newPassword = UserServiceImpl.getMd5Password(updatePasswordForm.getNewPassword());
            userMapper.updatePasswordByPhone(updatePasswordForm.getLoginSuccessForm().getPhone(),newPassword);
            if(oldToken != null){
                //如果存在token，删除旧的token
                redisRepository.deleteLoginAccessToken(user.getUserId());
                redisRepository.deleteAccessToken(oldToken);
            }
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("重置密码成功！");
            return messageSuccessDto;
        }
    }
    /**
     * 通过旧密码修改新密码
     * @param updatePasswordForm
     * @return
     */
    public MessageSuccessDto updatePasswordByPassword(UpdatePasswordForm updatePasswordForm){
        UserInfo user = userMapper.selectByPhone(updatePasswordForm.getLoginSuccessForm().getPhone());
        String oldPassword =UserServiceImpl.getMd5Password(updatePasswordForm.getOldPassword());
        if(null == user){
            log.info("用户不存在，违规操作！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectAccessToken(updatePasswordForm.getLoginSuccessForm().getAccessToken()).equals(user.getUserId())
        ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(updatePasswordForm.getLoginSuccessForm().getAccessToken())))){
            log.info("登录之后，手机号或token信息被改动，违规操作！");
            throw new SysException("违规操作！");
        }else if(!user.getPassword().equals(oldPassword.toString())){
            log.info("密码错误！");
            throw new SysException("密码错误！");
        }else {
            String newPassword = UserServiceImpl.getMd5Password(updatePasswordForm.getNewPassword());
            userMapper.updatePasswordByPhone(updatePasswordForm.getLoginSuccessForm().getPhone(),newPassword);
            String oldToken = redisRepository.selectLoginAccessToken(user.getUserId());
            if(oldToken != null){
                //如果存在token，删除旧的token
                redisRepository.deleteLoginAccessToken(user.getUserId());
                redisRepository.deleteAccessToken(oldToken);
            }
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("重置密码成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 个人中心
     * @param loginSuccessForm
     * @return
     */
    @Override
    public UserPersonalInfoDto getUserInfo(LoginSuccessForm loginSuccessForm){
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        UserPersonalInfoDto userInfoDto = new UserPersonalInfoDto();
        if(null == user){
            log.info("登录之后，手机被改动，违规操作！");
            throw new SysException("违规操作！");
        }else if ((!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))
                ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))) {
            log.info("登录之后，手机号或token信息被改动，违规操作！");
            throw new SysException("违规操作！");
        }else{
            userInfoDto.setUsername(user.getUserName());
            userInfoDto.setSchool(user.getSchool());
            userInfoDto.setCity(user.getCity());
            userInfoDto.setCounty(user.getCounty());
            userInfoDto.setProvince(user.getProvince());
            userInfoDto.setGrade(user.getGrade());
            userInfoDto.setPortrait(user.getPortrait());
            userInfoDto.setIntegral(user.getIntegral());
            userInfoDto.setGender(user.getGender());
            userInfoDto.setRealName(user.getRealName());
            return userInfoDto;
        }
    }

    /**
     * 修改个人中心
     * @param userUpdateInfoForm
     * @return
     */
    @Override
    public MessageSuccessDto updateUserInfo(UserUpdateInfoForm userUpdateInfoForm) {
        LoginSuccessForm loginSuccessForm = userUpdateInfoForm.getLoginSuccessForm();
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))
                ||(!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else {
            UserPersonalForm userPersonalForm = userUpdateInfoForm.getUserPersonalForm();
            userMapper.updateUserInfo(user.getUserId(),userPersonalForm.getUsername(),
                    userPersonalForm.getSchool(), userPersonalForm.getProvince(),
                    userPersonalForm.getCity(),userPersonalForm.getCounty(),
                    userPersonalForm.getPortrait(),userPersonalForm.getGender(),
                    userPersonalForm.getRealName());
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("修改成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 退出登录
     * @param loginSuccessForm
     * @return
     */
    @Override
    public MessageSuccessDto userLogout(LoginSuccessForm loginSuccessForm){
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("登录之后，手机被改动，违规操作！");
            throw new SysException("违规操作！");
        }else if ((!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))
                ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))) {
            log.info("登录之后，手机号或token信息被改动，违规操作！");
            throw new SysException("违规操作！");
        }else{
            redisRepository.deleteAccessToken(loginSuccessForm.getAccessToken());
            redisRepository.deleteLoginAccessToken(user.getUserId());
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("成功退出！");
            return messageSuccessDto;
        }
    }

    /**
     * 更改绑定的手机号
     * @param updatePhoneNumberForm
     * @return
     */
    @Override
    public MessageSuccessDto updatePhone(UpdatePhoneNumberForm updatePhoneNumberForm) {
        UserInfo user = userMapper.selectByPhone(updatePhoneNumberForm.getOldPhone());
        if((!redisRepository.selectAccessToken(updatePhoneNumberForm.getAccessToken()).equals(user.getUserId()))
        ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(updatePhoneNumberForm.getAccessToken()))){
            log.info("违规操作！");
            throw new SysException("违规操作！");
        }else if(!redisRepository.selectMessageCodeByPhone(updatePhoneNumberForm.getNewPhone())
                    .equals(updatePhoneNumberForm.getCode())){
            log.info("验证码错误！");
            throw new SysException("验证码错误！");
        }else if(null != userMapper.selectByPhone(updatePhoneNumberForm.getNewPhone())){
            log.info("该手机号已经与其他账号绑定！");
            throw new SysException("该手机号已经与其他账号绑定！");
        }else {
            userMapper.updatePhoneNumber(user.getUserId(),updatePhoneNumberForm.getNewPhone());
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("更改绑定手机号成功！");
            return messageSuccessDto;
        }
    }

    /**
     * 通过地区查看排行榜
     * @param rankingAreaForm
     * @return
     */
    public QuestionListLoginDto<RankingListDto> findRankingByArea(RankingAreaForm rankingAreaForm){
        UserInfo user = userMapper.selectByPhone(rankingAreaForm.getLoginSuccessForm().getPhone());
        if(null == user){
            log.info("该手机号未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectAccessToken(rankingAreaForm.getLoginSuccessForm().getAccessToken()).equals(user.getUserId()))
        ||(!redisRepository.selectLoginAccessToken(user.getUserId()).equals(rankingAreaForm.getLoginSuccessForm().getAccessToken()))){
            log.info("手机号与token信息不匹配！");
            throw new SysException("违规操作！");
        }else {
            List<RankingListDto> list = UserServiceImpl.rankingList(userMapper.findRankingByArea(rankingAreaForm.getProvince(),
                    rankingAreaForm.getCity(),rankingAreaForm.getCounty()));
            QuestionListLoginDto questionListLoginDto = new QuestionListLoginDto();
            questionListLoginDto.setObjects(list);
            return questionListLoginDto;
        }
    }

    /**
     * 根据年级得到排行榜
     * @param loginSuccessForm
     * @return
     */
    @Override
    public QuestionListLoginDto<RankingListDto> findRankingByGrade(LoginSuccessForm<String> loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("该手机号未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))
        ||(!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))){
            log.info("手机号与token信息不匹配！");
            throw new SysException("违规操作！");
        }else {
            List<RankingListDto> list = UserServiceImpl.rankingList(userMapper.findRankingByGrade(loginSuccessForm.getType()));
            QuestionListLoginDto questionListLoginDto = new QuestionListLoginDto();
            questionListLoginDto.setObjects(list);
            return questionListLoginDto;
        }
    }

    /**
     * 根据学校得到排行榜
     * @param loginSuccessForm
     * @return
     */
    @Override
    public QuestionListLoginDto<RankingListDto> findRankingBySchool(LoginSuccessForm<String> loginSuccessForm) {
        UserInfo user = userMapper.selectByPhone(loginSuccessForm.getPhone());
        if(null == user){
            log.info("该手机号未注册！");
            throw new SysException("违规操作！");
        }else if((!redisRepository.selectLoginAccessToken(user.getUserId()).equals(loginSuccessForm.getAccessToken()))
                ||(!redisRepository.selectAccessToken(loginSuccessForm.getAccessToken()).equals(user.getUserId()))){
            log.info("手机号与token信息不匹配！");
            throw new SysException("违规操作！");
        }else {
            List<RankingListDto> list = UserServiceImpl.rankingList(userMapper.findRankingBySchool(loginSuccessForm.getType()));
            QuestionListLoginDto questionListLoginDto = new QuestionListLoginDto();
            questionListLoginDto.setObjects(list);
            return questionListLoginDto;
        }
    }

    public static String getMd5Password(String password){
        Object result = new SimpleHash("MD5", password, "ok", 1);
        return result.toString();
    }

    /**
     * 得到登录成功返回的一个token信息
     * @param user
     * @return
     */
    public static MessageSuccessLoginDto getMessageSuccessLoginDto(UserInfo user,TokenInfo tokenInfo){
        MessageSuccessLoginDto messageSuccessLoginDto = new MessageSuccessLoginDto();
        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setAccessToken(tokenInfo.getAccessToken());
        loginSuccessDto.setPhoneNumber(user.getPhoneNumber());
        messageSuccessLoginDto.setGender(user.getGender());
        messageSuccessLoginDto.setGrade(user.getGrade());
        messageSuccessLoginDto.setIntegral(user.getIntegral());
        messageSuccessLoginDto.setPortrait(user.getPortrait());
        messageSuccessLoginDto.setUsername(user.getUserName());
        messageSuccessLoginDto.setLoginSuccessDto(loginSuccessDto);
        return messageSuccessLoginDto;
    }

    /**
     * 给排行榜添加排名
     * @param list
     * @return
     */
    public static List<RankingListDto> rankingList(List<RankingListDto> list){
        int i =1;
        for(RankingListDto r : list){
            r.setRanking(i++);
        }
        return list;
    }

    /**
     * 获取用户学习计划
     * @param userId
     * @return
     */
    @Override
    public LearningPlanDto getLearningPlan(String userId) {
        return userMapper.selectLearningPlan(userId);
    }

    /**
     * 用户签到
     * @param userId
     */
    @Override
    public boolean userLearningSign(String userId) {
        LearningSign sign = userMapper.selectLearningSignData(userId);
        String isSign = userMapper.selectIsSing(userId);
        if ("0".equals(isSign)) {
            if (sign == null) {
                userMapper.insertLearningSign(userId);
                return true;
            } else {
                userMapper.updateLearningSign(userId);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public PresentIntegralDto getPresentIntegral(String userId) {
        return userMapper.selectPresentIntegral(userId);
    }

    @Override
    public AnalysisDto getAnalysis(String userId) {
        return userMapper.selectAnalysis(userId);
    }
}
