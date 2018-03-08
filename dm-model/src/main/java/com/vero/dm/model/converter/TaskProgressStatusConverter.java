package com.vero.dm.model.converter;


import javax.persistence.AttributeConverter;

import com.vero.dm.model.enums.TaskProgressStatus;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:42 2018/3/8.
 * @since data-mining-platform
 */

public class TaskProgressStatusConverter implements AttributeConverter<TaskProgressStatus, Integer>
{
    @Override
    public Integer convertToDatabaseColumn(TaskProgressStatus attribute)
    {
        return attribute.getValue();
    }

    @Override
    public TaskProgressStatus convertToEntityAttribute(Integer dbData)
    {
        TaskProgressStatus[] status = TaskProgressStatus.values();
        if (dbData == null)
        {
            return null;
        }
        for (TaskProgressStatus s : status)
        {
            if (s.getValue() == dbData)
            {
                return s;
            }
        }
        return null;
    }
}
