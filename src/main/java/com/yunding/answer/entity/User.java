package com.yunding.answer.entity;

        import lombok.Data;

        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;

@Data
public class User {
    private String userId;

    private String userNickname;

    private String userPhone;

    private String userSign;

    private String userGroup;

    private String userClass;

    private String userPassword;

    private String userImage;

    private Integer userRole;

    private Integer drawCount;

    private Integer askCount;

    private String userProfession;

    private Integer userGender;

}
