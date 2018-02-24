package com.vero.dm.api.converters;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

import com.vero.dm.util.DateStyle;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 2:21 2018/2/25.
 * @since data-mining-platform
 */

public class DateConverterFactory implements ConverterFactory<String, Date>
{
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    private Converter dateConverter = new DateConverter();

    @Override
    public <T extends Date> Converter<String, T> getConverter(Class<T> targetType)
    {
        return dateConverter;
    }

    class DateConverter implements Converter<String, Date>
    {
        private SimpleDateFormat format = new SimpleDateFormat(
            DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());

        @Override
        public Date convert(String source)
        {
            try
            {
                return format.parse(source);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
