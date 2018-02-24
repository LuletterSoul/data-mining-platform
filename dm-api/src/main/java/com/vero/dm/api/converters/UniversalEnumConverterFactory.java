package com.vero.dm.api.converters;


import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.vero.dm.model.enums.BaseEnum;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:42 2018/2/24.
 * @since data-mining-platform
 */

public class UniversalEnumConverterFactory implements ConverterFactory<String, BaseEnum>
{
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType)
    {
        Converter result = converterMap.get(targetType);
        if (result == null)
        {
            result = new IntegerStrToEnum<T>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    class IntegerStrToEnum<T extends BaseEnum> implements Converter<String, T>
    {
        private final Class<T> enumType;

        private Map<String, T> enumMap = new HashMap<>();

        public IntegerStrToEnum(Class<T> enumType)
        {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for (T e : enums)
            {
                enumMap.put(e.getValue() + "", e);
            }
        }

        @Override
        public T convert(String source)
        {
            T result = enumMap.get(source);
            if (result == null)
            {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
