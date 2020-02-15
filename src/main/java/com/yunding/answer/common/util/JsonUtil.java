package com.yunding.answer.common.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunding.answer.common.enums.BaseErrorCodeEnum;
import com.yunding.answer.core.exception.SysException;

/**
 * @author 段启岩
 * JSON数据转换工具
 */

public class JsonUtil {

    /**
     * 对象转换为JSON
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return o instanceof String ? o.toString() : objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException(BaseErrorCodeEnum.JSON_TRANS_ERROR);
        }
    }

    /**
     * JSON转换为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static<T> T toObject(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return clazz.equals(String.class) ? (T) json : objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException(BaseErrorCodeEnum.JSON_TRANS_ERROR);
        }
    }
}