package com.yunding.answer.form;

import lombok.Data;

/**
 * @author HaoJun
 * @create 2020-03
 */
@Data
public class UserAddressForm {

    private String consigneeName;
    private String phone;
    private String country;
    private String province;
    private String city;
    private String district;
    private String detailedAddress;

}
