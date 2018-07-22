package com.vero.dm.model.enums;

import java.io.Serializable;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:40 2018/2/24.
 * @since data-mining-platform
 */

public interface BaseEnum extends Serializable
{
    int getValue();

    String getStatus();

    String getDescription();
}
