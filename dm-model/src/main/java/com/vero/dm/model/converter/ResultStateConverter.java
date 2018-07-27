package com.vero.dm.model.converter;

import javax.persistence.AttributeConverter;

import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.model.enums.ResultState;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  15:02 2018/2/24.
 * @since data-mining-platform
 */

public class ResultStateConverter implements AttributeConverter<ResultState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ResultState attribute) {
        return attribute.getValue();
    }

    @Override
    public ResultState convertToEntityAttribute(Integer dbData) {
        ResultState[] status = ResultState.values();
        if (dbData == null) {
            return null;
        }
        for (ResultState s : status) {
            if (s.getValue() == dbData) {
                return s;
            }
        }
        return null;
    }
}
