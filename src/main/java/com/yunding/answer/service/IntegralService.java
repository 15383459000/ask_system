package com.yunding.answer.service;

import com.yunding.answer.dto.GoodsInfoDto;
import com.yunding.answer.dto.GoodsListDto;
import com.yunding.answer.dto.TitleListDto;
import com.yunding.answer.dto.UserAddressDto;
import com.yunding.answer.form.ExchangeGoodsForm;
import com.yunding.answer.form.ExchangeTitleForm;
import com.yunding.answer.form.GoodsInfoForm;
import com.yunding.answer.form.UserAddressForm;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
public interface IntegralService {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsListDto> getGoodsList();

    /**
     * 获取单个物品详情
     * @param goodsInfoForm
     * @return
     */
    GoodsInfoDto getGoodsInfo(GoodsInfoForm goodsInfoForm);

    /**
     * 交换物品
     * @param form
     * @param userId
     */
    boolean exchangeGoods(ExchangeGoodsForm form, String userId);

    /**
     * 新增用户住址
     * @param userAddressForm
     */
    void addUserAddress(UserAddressForm userAddressForm, String userId);

    /**
     * 查询用户所有地址
     * @param userId
     * @return
     */
    List<UserAddressDto> getUserAddresss(String userId);

    /**
     * 查询头衔列表
     * @return
     */
    List<TitleListDto> getTitleList();

    /**
     * 兑换头衔
     * @param userId
     * @param exchangeTitleForm
     * @return 0：成功  1：头衔已存在  2：积分不足
     */
    int exchangeTitles(String userId, ExchangeTitleForm exchangeTitleForm);

}
