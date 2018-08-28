package com.lv.zupu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lv.zupu.exception.ZupuException;

import java.io.IOException;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/6/25 19:42
 * @Version: 1.0
 * modified by:
 */
public class JsonMapperUtil {

    private static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        // 禁用遇到未知属性抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 对于空的对象转json的时候不抛出错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
    public static String objToJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ZupuException("json转换异常",e);
        }
    }

    public static <T> T JsonToObj(String jsonStr,Class<T> tClass){
        try {
           return objectMapper.readerFor(tClass).readValue(jsonStr);
        } catch (IOException e) {
            throw new ZupuException("json转换异常",e);
        }
    }
    public static <T> T JsonToObj(String jsonStr,TypeReference<T> type){
        try {
            return objectMapper.readerFor(type).readValue(jsonStr);
        } catch (IOException e) {
            throw new ZupuException("json转换异常",e);
        }
    }

}
