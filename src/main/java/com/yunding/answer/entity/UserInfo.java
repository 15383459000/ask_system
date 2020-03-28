package com.yunding.answer.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * @Author: Cui
 * @Date: 2020/3/8
 * @Description:
 */
@Entity
@Data
public class UserInfo {
    //密码
    private String password;
    //用户id
    private String userId;
    //昵称
    private String userName;
    //学校
    private String school;
    //省份
    private String province;
    //市
    private String city;
    //县
    private String county;
    //微信号
    private String weChatNumber;
    //手机号
    private String phoneNumber;
    //闯到的关卡
    private Integer priorStage;
    //头像
    private String portrait;
    //积分
    private Integer integral;
    //头衔id
    private Integer titleId;
    //等级
    private Integer grade;
    //年级
    private String schoolGrade;
    //全部做题量
    private Integer totalExercisesQuantity;
    //创建时间
    private LocalDateTime creatAt;
    //用户性别
    private String gender;
    //真实姓名
    private String realName;
}
