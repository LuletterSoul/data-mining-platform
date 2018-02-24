package com.vero.dm.model.converter;

import com.vero.dm.model.enums.MiningTaskStatus;

import javax.persistence.AttributeConverter;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  15:02 2018/2/24.
 * @since data-mining-platform
 */

public class MiningTaskStatusConverter implements AttributeConverter<MiningTaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MiningTaskStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public MiningTaskStatus convertToEntityAttribute(Integer dbData) {
        MiningTaskStatus[] status = MiningTaskStatus.values();
        for (MiningTaskStatus s : status) {
            if (s.getValue() == dbData) {
                return s;
            }
        }
        return null;
    }
}
