package com.vero.dm.api.converters;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vero.dm.util.date.ConcurrentDateUtil;
import org.springframework.core.convert.converter.Converter;

import com.vero.dm.util.date.DateStyle;
import org.springframework.util.StringUtils;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 2:02 2018/2/25.
 * @since data-mining-platform
 */

public class DateConverter implements Converter<String, Date>
{

    @Override
    public Date convert(String source)
    {
        if (StringUtils.isEmpty(source))
        {
            return null;
        }
        try {
            return ConcurrentDateUtil.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
