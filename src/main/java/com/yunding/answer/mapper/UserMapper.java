package com.yunding.answer.mapper;

import com.yunding.answer.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    /**
     * 通过手机号查询用户信息/登陆两种、修改密码,忘记密码
     * @param phone
     * @return
     */
    User selectByPhone( String phone);


}
