package com.yunding.answer.service.impl;

import com.yunding.answer.dto.*;
import com.yunding.answer.form.ExchangeGoodsForm;
import com.yunding.answer.form.ExchangeTitleForm;
import com.yunding.answer.form.GoodsInfoForm;
import com.yunding.answer.form.UserAddressForm;
import com.yunding.answer.mapper.IntegralMapper;
import com.yunding.answer.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Service
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    IntegralMapper integralMapper;

    @Override
    public List<GoodsListDto> getGoodsList() {
        return integralMapper.selectGoodsList();
    }

    @Override
    public GoodsInfoDto getGoodsInfo(GoodsInfoForm goodsInfoForm) {
        int prizeId = goodsInfoForm.getPrizeId();
        return integralMapper.selectGoodsInfo(prizeId);
    }

    @Override
    public boolean exchangeGoods(ExchangeGoodsForm form, String userId) {
        // 兑换物品需要的积分
        int prizeIntegral = integralMapper.selectGoodsInfoBySpecification(form.getPrizeId(), form.getPrizeSpecification())
                .getNeedsIntegral();
        int needsIntegral = form.getPrizeNum() * prizeIntegral;
        // 用户拥有的积分
        int userIntegral = integralMapper.selectUserIntegral(userId);
        // 检验积分是否足够
        if (userIntegral >= needsIntegral) {
            integralMapper.updateUserIntegral(userId, needsIntegral);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addUserAddress(UserAddressForm userAddressForm, String userId) {
        String consigneeName = userAddressForm.getConsigneeName();
        String phone = userAddressForm.getPhone();
        String country = userAddressForm.getCountry();
        String province = userAddressForm.getProvince();
        String city = userAddressForm.getCity();
        String district = userAddressForm.getDistrict();
        String detailedAddress = userAddressForm.getDetailedAddress();
        integralMapper.insertUserAddress(userId, consigneeName, phone, country,
                province, city, district, detailedAddress);
    }

    @Override
    public List<UserAddressDto> getUserAddresss(String userId) {
        return integralMapper.selectUserAddress(userId);
    }

    @Override
    public List<TitleListDto> getTitleList() {
        return integralMapper.selectTitleList();
    }

    /**
     *
     * @param userId
     * @param exchangeTitleForm
     * @return 0：成功  1：头衔已存在  2：积分不足
     */
    @Override
    public int exchangeTitles(String userId, ExchangeTitleForm exchangeTitleForm) {
        // 头衔 id
        int titleId = exchangeTitleForm.getTitleId();
        // 头衔标价
        int integral = integralMapper.selectTitleIntegral(titleId);
        // 用户持有积分
        int userIntegral = integralMapper.selectUserIntegral(userId);
        // 获取用户已拥有的头衔
        List<UserTitleDto> userTitles = integralMapper.selectUserTitle(userId);
        // 兑换
        // 遍历查询用户是否已有该头衔
        for (UserTitleDto ut : userTitles) {
            if (titleId == ut.getTitleId()) {
                // 头衔已存在
                return 1;
            }
        }
        if (userIntegral >= integral) {
            // 积分足够
            // 修改积分
            integralMapper.updateUserIntegral(userId, integral);
            // 更新已拥有头衔
            integralMapper.insertUserTitleInfo(userId, titleId);
            return 0;
        } else {
            // 积分不足
            return 2;
        }

    }

}
