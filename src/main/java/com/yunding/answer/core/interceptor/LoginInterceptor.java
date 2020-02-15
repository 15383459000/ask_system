package com.yunding.answer.core.interceptor;

import com.yunding.answer.common.constant.SysConstant;
import com.yunding.answer.common.util.JsonUtil;
import com.yunding.answer.common.util.ThreadLocalMap;
import com.yunding.answer.constant.AnswerConstant;
import com.yunding.answer.dto.TokenInfo;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.entity.User;
import com.yunding.answer.service.TokenService;
import com.yunding.answer.core.wrapper.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 宋万顷
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getMethod().equalsIgnoreCase("options")) {
            return true;
        }
        String accessToken = null;
        accessToken = request.getHeader(AnswerConstant.HTTP_HEADER_ANSWER_ACCESS_TOKEN);

        if (null == accessToken){
            accessToken = request.getParameter(AnswerConstant.RQE_PARAM_ANSWER_ACCESS_TOKEN);
        }

        ResultWrapper resultWrapper = null;
        response.setHeader("Content-Type", "application/json;charset=utf-8");

        /**
         * 未传递Token
         */
        if (null == accessToken){
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);
            resultWrapper = ResultWrapper.failure("未认证");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        TokenInfo tokenInfo = tokenService.getTokenInfo(accessToken);
        if (null == tokenInfo){
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);
            resultWrapper = ResultWrapper.failure("错误的Token");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 将userInfo设置到ThreadLocal
         */
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(tokenInfo.getUserId());
        ThreadLocalMap.put(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER, userInfoDto);
        return true;
    }
}
