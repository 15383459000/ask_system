package com.yunding.answer.form;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/3/23
 * @Description:
 */
@Data
public class UpdatePasswordForm {
    private LoginSuccessForm loginSuccessForm;

    private String newPassword;

    private String oldPassword;
}
