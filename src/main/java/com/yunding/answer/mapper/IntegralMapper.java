package com.yunding.answer.mapper;

import com.yunding.answer.dto.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Repository
public interface IntegralMapper {
    /**
     * 查看积分商城
     * @return
     */
    @Select("SELECT prize_id, prize_name, needs_integral, prize_picture " +
            "FROM integral_to_goods")
    List<GoodsListDto> selectGoodsList();

    /**
     * 查询单个物品详情
     * @param prizeId
     * @return
     */
    @Select("SELECT prize_id, prize_name, needs_integral, prize_picture, prize_introduction, prize_specification " +
            "FROM integral_to_goods " +
            "WHERE prize_id = #{prizeId};")
    GoodsInfoDto selectGoodsInfo(@Param("prizeId") int prizeId);

    /**
     * 根据商品id和商品规格确定商品
     * @param prizeId
     * @param specification
     * @return
     */
    @Select("SELECT prize_id, prize_name, needs_integral, prize_picture, prize_introduction, prize_specification " +
            "FROM integral_to_goods " +
            "WHERE prize_id = #{prizeId} AND prize_specification = #{specification};")
    GoodsInfoDto selectGoodsInfoBySpecification(@Param("prizeId") int prizeId, @Param("specification") String specification);

    /**
     * 交换物品 更新积分
     * @param userId
     * @param integral
     */
    @Update("UPDATE user_info SET integral = integral - #{integral} WHERE user_id = #{userId};")
    void updateUserIntegral(@Param("userId") String userId, @Param("integral") int integral);

    /**
     * 查询用户积分
     * @param userId
     * @return
     */
    @Select("SELECT integral FROM user_info WHERE user_id = #{userId};")
    int selectUserIntegral(@Param("userId") String userId);

    /**
     * 新增用户地址
     * @param consigneeName
     * @param phone
     * @param country
     * @param province
     * @param city
     * @param district
     * @param detailedAddress
     */
    @Insert("INSERT INTO user_address (user_id, consignee_name, consignee_phone, consignee_country, " +
            "consignee_province, consignee_city, consignee_district, detailed_address) " +
            "VALUES ( #{userId}, #{consigneeName}, #{phone}, #{country}, #{province}, " +
            "#{city}, #{district}, #{detailedAddress});")
    void insertUserAddress(@Param("userId") String userId,
                           @Param("consigneeName") String consigneeName,
                           @Param("phone") String phone,
                           @Param("country") String country,
                           @Param("province") String province,
                           @Param("city") String city,
                           @Param("district") String district,
                           @Param("detailedAddress") String detailedAddress);

    /**
     * 查询用户所有地址
     * @param userId
     * @return
     */
    @Select("SELECT consignee_name, consignee_phone, consignee_country, consignee_province, " +
            "consignee_city, consignee_district, detailed_address " +
            "FROM user_address " +
            "WHERE user_id = #{userId};")
    List<UserAddressDto> selectUserAddress(@Param("userId") String userId);

    /**
     * 查询可兑换头衔列表
     * @return
     */
    @Select("SELECT title_id, title_name, needs_integral, title_picture " +
            "FROM integral_to_title;")
    List<TitleListDto> selectTitleList();

    /**
     * 查询某头衔兑换所需积分
     * @param titleId
     * @return
     */
    @Select("SELECT needs_integral FROM integral_to_title WHERE title_id = #{titleId};")
    int selectTitleIntegral(@Param("titleId") int titleId);

    /**
     * 查询用户所拥有的头衔
     * @param userId
     * @return
     */
    @Select("SELECT title_id FROM user_title WHERE user_id = #{userId};")
    List<UserTitleDto> selectUserTitle(@Param("userId") String userId);

    /**
     * 更新用户拥有的头衔
     * @param userId
     * @param titleId
     */
    @Insert("INSERT INTO user_title (user_id, title_id) VALUES (#{userId}, #{titleId})")
    void insertUserTitleInfo(@Param("userId") String userId, @Param("titleId") int titleId);

}
