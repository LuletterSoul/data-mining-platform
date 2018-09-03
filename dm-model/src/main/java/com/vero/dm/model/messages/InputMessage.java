package com.vero.dm.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:38 2018/8/12.
 * @since data-mining-platform
 */

@Data
@AllArgsConstructor
public class InputMessage
{
    private String from;

    private String text;

    private String time;

    public InputMessage() {
    }
}
