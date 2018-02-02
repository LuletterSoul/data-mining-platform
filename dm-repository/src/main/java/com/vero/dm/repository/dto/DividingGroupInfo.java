package com.vero.dm.repository.dto;


import java.util.List;

import com.vero.dm.model.DataMiningGroup;

import com.vero.dm.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 启动分组操作后，服务器将默认分组的信息及其缓存Key返回给客户端 待用户确认
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:56 2018/2/1.
 * @since data-mining-platform
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DividingGroupInfo
{


    /**
     * 将作为用户确认分组的Key 服务器根据该Key获取之前分配好的分组信息
     */
    private String queryKey;

    /**
     * 系统帮用户分配好的分组信息
     */
    private List<PreviewDividingGroupDto> dataMiningGroups;


}
