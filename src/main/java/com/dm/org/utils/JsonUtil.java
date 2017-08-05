package com.dm.org.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:35 2017/7/20.
 * @since data-minning-platform
 */

@Component
public class JsonUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper)
    {
        JsonUtil.objectMapper = objectMapper;
    }

    /**
     * 对象转json字符串
     * 
     * @param object
     * @return
     */
    public static String toJSon(Object object)
    {
        try
        {
            return objectMapper.writeValueAsString(object);
        }
        catch (Exception e)
        {
            LOGGER.error("对象转json字符串", e);
        }
        return "";
    }

    /**
     * 字符串转化为对象
     * 
     * @param v
     * @param json
     * @return
     */
    public static <T> T getObjectFromStr(Class<T> v, String json)
    {
        try
        {
            return objectMapper.readValue(json.getBytes(), objectMapper.constructType(v));
        }
        catch (IOException e)
        {
            LOGGER.error("字符串转化为对象异常", e);
        }
        return null;
    }

    /**
     * HashMap对象转对象
     * 
     * @param v
     * @param map
     * @return
     */
    public static <T> T getObjectFromMap(Class<T> v, HashMap<String, Object> map)
    {
        return objectMapper.convertValue(map, objectMapper.getTypeFactory().constructType(v));
    }

    /**
     * 字符串转化为ArrayList对象
     * 
     * @param v
     * @param json
     * @return
     */
    public static <T> List<T> getArrayListObjectFromStr(Class<T> v, String json)
    {
        try
        {
            return objectMapper.readValue(json.getBytes(),
                objectMapper.getTypeFactory().constructParametricType(ArrayList.class, v));
        }
        catch (IOException e)
        {
            LOGGER.error("字符串转化为ArrayList对象异常", e);
        }
        return null;
    }

    /**
     * 字符串转化为ArrayList的HashMap对象
     * 
     * @param json
     * @return
     */
    public static <T> List<T> getArrayListMapFromStr(String json)
    {
        try
        {
            return objectMapper.readValue(json.getBytes(),
                objectMapper.getTypeFactory().constructParametricType(ArrayList.class,
                    HashMap.class));
        }
        catch (IOException e)
        {
            LOGGER.error("字符串转化为ArrayList的HashMap对象异常", e);
        }
        return null;
    }
}
