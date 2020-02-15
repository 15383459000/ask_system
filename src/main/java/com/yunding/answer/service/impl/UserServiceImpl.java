package com.yunding.answer.service.impl;

        import com.yunding.answer.common.util.TokenUtil;
        import com.yunding.answer.core.exception.SysException;
        import com.yunding.answer.dto.TokenInfo;
        import com.yunding.answer.dto.UserInfoDto;
        import com.yunding.answer.entity.User;
        import com.yunding.answer.mapper.UserMapper;
        import com.yunding.answer.redis.RedisRepository;
        import com.yunding.answer.service.UserService;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.util.UUID;

/**
 *
 * @author 杜文俊
 */
@Slf4j //打印日志
@Service
public  class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 手机号、密码登录
     */
    @Override
    public TokenInfo login(String phone, String password) {

        /**
         * 根据手机号查找用户
         */

        User selectByphone = userMapper.selectByPhone ( phone );
        if (selectByphone == null) {
            //用户不存在
            log.info ( "手机号输入错误" + phone );
            throw new SysException ( "手机号不存在" );
        } else{
            if (!selectByphone.getUserPassword ().equals ( password )){
                //密码不正确
                log.info ( "密码或账号输入错误" + password );
                throw new SysException ( "密码或账号输入有误" );
            }else{
                //生成accessToken
                String accessToken = TokenUtil.genToken ();
                //生成TokenInfo
                String userId = selectByphone.getUserId ();
                //查询用户的角色
                Integer roleId = selectByphone.getUserRole ();
                if (roleId == 0){
                    log.info ( "该用户为学生" );
                }else{
                    log.info ( "该用户为管理员" );
                }
                //查询旧Token，通知用户在其他地方登陆
                String oldToken = redisRepository.selectAccessToken ( userId );
                if (null != oldToken){
                    log.info ( userId+"用户"+userId+"在异地登陆了" );
                    redisRepository.deleteAccessToken ( oldToken );
                }
                TokenInfo tokenInfo = new TokenInfo ();
                tokenInfo.setUserId ( userId );
                tokenInfo.setAccessToken ( accessToken );
                // 无论是否异地登录，都要保存当前token
                redisRepository.saveAccessToken(tokenInfo);
                redisRepository.saveLoginAccessToken(tokenInfo);
                if (oldToken == null) {
                    log.info("用户" + userId + "刚刚登录了");
                }
                return tokenInfo;
            }
        }
    }


}
