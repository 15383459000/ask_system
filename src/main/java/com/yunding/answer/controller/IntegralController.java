package com.yunding.answer.controller;

import com.yunding.answer.core.support.web.controller.BaseController;
import com.yunding.answer.core.wrapper.ResultWrapper;
import com.yunding.answer.dto.UserInfoDto;
import com.yunding.answer.form.ExchangeGoodsForm;
import com.yunding.answer.form.ExchangeTitleForm;
import com.yunding.answer.form.GoodsInfoForm;
import com.yunding.answer.form.UserAddressForm;
import com.yunding.answer.service.IntegralService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Api(value = "IntegralController", tags = {"积分商城API"})
@RestController
@RequestMapping("/integral")
public class IntegralController extends BaseController<UserInfoDto> {

    @Autowired
    private IntegralService integralService;

    /**
     * 查看积分商城
     * @return
     */
    @ApiOperation(value = "查看积分商城")
    @GetMapping("/goods")
    public ResultWrapper getGoodsList() {
        return ResultWrapper.successWithData(integralService.getGoodsList());
    }

    /**
     * 获取单个物品详情
     * @param goodsInfoForm
     * @return
     */
    @ApiOperation(value = "获取单个物品详情")
    @PostMapping("/goods/info")
    public ResultWrapper postGoodsInfo(@RequestBody GoodsInfoForm goodsInfoForm) {
        return ResultWrapper.successWithData(integralService.getGoodsInfo(goodsInfoForm));
    }

    /**
     * 兑换物品
     * @param exchangeGoodsForm
     * @return
     */
    @ApiOperation(value = "兑换物品")
    @PostMapping("/goods/exchange")
    public ResultWrapper exchageGoods(@RequestBody ExchangeGoodsForm exchangeGoodsForm) {
        if (integralService.exchangeGoods(exchangeGoodsForm, getCurrentUserInfo().getUserId())) {
            return ResultWrapper.success();
        } else {
            return ResultWrapper.failure("积分不足");
        }
    }

    /**
     * 添加用户地址
     * @param userAddressForm
     * @return
     */
    @ApiOperation(value = "添加用户地址")
    @PostMapping("/address/edit")
    public ResultWrapper addUserAddress(@RequestBody UserAddressForm userAddressForm) {
        try {
            integralService.addUserAddress(userAddressForm, getCurrentUserInfo().getUserId());
            return ResultWrapper.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultWrapper.failure();
        }
    }

    /**
     * 查询用户所有地址
     * @return
     */
    @ApiOperation(value = "查询用户所有地址")
    @PostMapping("/address/check")
    public ResultWrapper getUserAddress() {
        return ResultWrapper.successWithData(integralService.getUserAddresss(getCurrentUserInfo().getUserId()));
    }

    /**
     * 查询头衔列表
     * @return
     */
    @ApiOperation(value = "查询头衔列表")
    @GetMapping("/title")
    public ResultWrapper getTitleList() {
        return ResultWrapper.successWithData(integralService.getTitleList());
    }

    /**
     * 兑换头衔
     * @param exchangeTitleForm
     * @return
     */
    @ApiOperation(value = "兑换头衔")
    @PostMapping("/title/exchange")
    public ResultWrapper exchangeTitle(@RequestBody ExchangeTitleForm exchangeTitleForm) {
        int returnId = integralService.exchangeTitles(getCurrentUserInfo().getUserId(), exchangeTitleForm);
        if (returnId == 0) {
            return ResultWrapper.success();
        } else if (returnId == 1) {
            return ResultWrapper.failure("头衔已存在");
        } else {
            return ResultWrapper.failure("积分不足");
        }
    }

}
