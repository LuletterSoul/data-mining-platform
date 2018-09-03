package com.vero.dm.model.messages;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:38 2018/8/12.
 * @since data-mining-platform
 */

@AllArgsConstructor
@Data
public class OutputMessage
{
    private String from;

    private String text;

    private String time;
}
