package com.yunding.answer.core.support.web.controller;


import com.yunding.answer.common.constant.SysConstant;
import com.yunding.answer.common.enums.BaseErrorCodeEnum;
import com.yunding.answer.common.util.ThreadLocalMap;
import com.yunding.answer.core.exception.SysException;
import org.springframework.validation.BindingResult;

/**
 * @author 通用Controller
 * @param <T> 当前登录用户的Java类型
 */
public class BaseController<T> {

    protected T getCurrentUserInfo() {
        T currentUser = (T) ThreadLocalMap.get(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);

        if (null == currentUser) {
            throw new SysException(BaseErrorCodeEnum.NO_USER_INFO_FOUND);
        }

        return currentUser;
    }

    protected void validateParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SysException(BaseErrorCodeEnum.PARAM_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
