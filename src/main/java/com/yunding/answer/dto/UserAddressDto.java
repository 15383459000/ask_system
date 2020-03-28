package com.yunding.answer.dto;

import lombok.Data;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class UserAddressDto {

    private String consigneeName;
    private String consigneePhone;
    private String consigneeCountry;
    private String consigneeProvince;
    private String consigneeCity;
    private String consigneeDistrict;
    private String detailedAddress;


}
