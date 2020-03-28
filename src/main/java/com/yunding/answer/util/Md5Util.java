package com.yunding.answer.util;//package com.yunding.answer.util;
//
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.util.ByteSource;
//
///**
// * @Author: Cui
// * @Date: 2020/3/8
// * @Description:
// */
//public class Md5Util {
//    public static String passwordToMd5(String password){
//        String hashAlgorithmName = "MD5";
//        Object salt = ByteSource.Util.bytes("ok");
//        int hashIterations = 1;
//        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
//    }
//}
