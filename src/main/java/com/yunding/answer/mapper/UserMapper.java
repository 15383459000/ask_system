package com.yunding.answer.mapper;

import com.yunding.answer.dto.AnalysisDto;
import com.yunding.answer.dto.LearningPlanDto;
import com.yunding.answer.dto.PresentIntegralDto;
import com.yunding.answer.dto.RankingListDto;
import com.yunding.answer.entity.LearningSign;
import com.yunding.answer.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    /**
     * 通过手机号查询用户
     * @param phoneNumber
     * @return
     */
//    @Select("select * from ask_system.user_info where phone_number = #{phoneNumber}")
    UserInfo selectByPhone(String phoneNumber);

    /**
     * 注册
     * @param phoneNumber   手机号
     * @param password  md5加密后的密码
     * @param userId    自动生成的id
     * @param username  默认昵称 闯关小勇士
     * @param userImage 默认头像
     * @param userGender    默认性别为男
     * @param grade 默认等级为0
     * @param priorStage    默认闯关数为0
     * @param integral  默认积分为0
     * @param totalExercisesQuantity    默认总的做题数为0
     */
//    @Insert("insert " +
//            "into ask_system.user_info(user_id,portrait,phone_number,user_name,password,prior_stage,integral,grade,gender,total_exercises_quantity) " +
//            "values(#{userId},#{userImage},#{phoneNumber},#{username},#{password},#{priorStage},#{integral},#{grade},#{userGender},#{totalExercisesQuantity} ) ")
    void insertNewUser(String phoneNumber, String password, String userId, String username, String userImage,
                       String userGender, Integer grade, Integer priorStage, Integer integral,
                       Integer totalExercisesQuantity);

    /**
     * 更改密码
     * @param phoneNumber
     * @param password
     */
//    @Update("update ask_system.user_info " +
//            "set password=#{password} " +
//            "where phone_number = #{phoneNumber}")
    void updatePasswordByPhone(String phoneNumber, String password);

    /**
     * 修改个人信息
     * @param userId
     * @param username
     * @param school
     * @param province
     * @param city
     * @param county
     * @param portrait
     * @param gender
     * @param realName
     */
//    @Update("update ask_system.user_info " +
//            "set user_name=#{username},school=#{school},province=#{province},city=#{city},county=#{county},portrait=#{portrait},gender=#{gender},real_name=#{realName}" +
//            " where user_id = #{userId} ")
    void updateUserInfo(String userId, String username, String school, String province, String city, String county,
                        String portrait, String gender, String realName);

    /**
     * 更绑手机号
     * @param userId
     * @param phone
     */
//    @Update("update ask_system.user_info " +
//            "set phone_number = #{phone} " +
//            "where user_id = #{userId} ")
    void updatePhoneNumber(String userId, String phone);

    /**
     * 根据地区得到排行榜
     * @param province
     * @param city
     * @param county
     * @return
     */
//    @Select("select user_name,portrait,total_exercises_quantity " +
//            "from ask_system.user_info " +
//            "where province=#{province} and city = #{city} and county = #{county} " +
//            "order by total_exercises_quantity desc limit 10 ")
    List<RankingListDto> findRankingByArea(String province, String city, String county);

    /**
     * 根据年级得到排行榜
     * @param grade
     * @return
     */
//    @Select("select user_name,portrait,total_exercises_quantity " +
//            "from ask_system.user_info " +
//            "where school_grade = #{grade} " +
//            "order by total_exercises_quantity desc limit 10 ")
    List<RankingListDto> findRankingByGrade(String grade);

    /**
     * 根据学校得到排行榜
     * @param school
     * @return
     */
//    @Select("select user_name,portrait,total_exercises_quantity " +
//            "from ask_system.user_info " +
//            "where school = #{school} " +
//            "order by total_exercises_quantity desc limit 10 ")
    List<RankingListDto> findRankingBySchool(String school);


    /**
     * @author HaoJun
     * @create 2020-03
     */

    /**
     * 获取用户学习计划
     * @param userId
     * @return
     */
    @Select("SELECT user_name, integral, grade, daily_study_time, daily_exercises_quantity, " +
            "planning_exercises_quantity, planning_finished_time, " +
            "total_exercises_quantity, total_learning_time " +
            "FROM learning_plan, user_info " +
            "WHERE learning_plan.user_id = #{userId} " +
            "AND learning_plan.user_id = user_info.user_id;")
    LearningPlanDto selectLearningPlan(@Param("userId") String userId);

    /**
     * 查询是否有签到数据
     * @param userId
     */
    @Select("SELECT * FROM learning_sign WHERE user_id = #{userId};")
    LearningSign selectLearningSignData(@Param("userId") String userId);

    /**
     * 创建签到数据
     * @param userId
     */
    @Insert("INSERT INTO learning_sign VALUES (#{userId}, 1);")
    void insertLearningSign(@Param("userId") String userId);

    /**
     * 更新签到数据
     * @param userId
     */
    @Update("UPDATE learning_sign SET sign_number = sign_number + 1 WHERE user_id = #{userId};")
    void updateLearningSign(@Param("userId") String userId);

    /**
     * 查询是否签到
     * @param userId
     */
    @Select("SELECT is_sign FROM learning_sign WHERE user_id = #{userId};")
    String selectIsSing(@Param("userId") String userId);

    /**
     * 查询用户当前积分
     * @param userId
     * @return
     */
    @Select("SELECT integral FROM user_info WHERE user_id = #{userId};")
    PresentIntegralDto selectPresentIntegral(@Param("userId") String userId);

    /**
     * 学情分析 - 查看大数据面板
     * @param userId
     * @return
     */
    @Select("SELECT user_name, daily_exercises_quantity, planning_exercises_quantity, planning_finished_time, " +
            "total_learning_time, total_exercises_quantity " +
            "FROM user_info, learning_plan " +
            "WHERE user_info.user_id = #{userId} " +
            "AND user_info.user_id = learning_plan.user_id;")
    AnalysisDto selectAnalysis(@Param("userId") String userId);

}
