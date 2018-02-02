package com.vero.dm.repository.dto;


import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.vero.dm.model.*;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@ApiModel
@Data
public class CollectionDto
{
    /**
     * 数据集的ID
     */
    private String collectionId;

    /**
     * 数据集名称
     */
    private String collectionName;

    /**
     * 是否允许缺省值
     */
    private String isMissingValues;

    /**
     * 捐赠时间
     */
    private Date dateDonated;

    /**
     * 数据集数目
     */
    private Long numberOfInstances;

    /**
     * 数据集属性数目
     */
    private Long numberOfAttributes;

    /**
     * 网页点击数
     */
    private Long numberOfWebHits;

//    private Set<DataSetDescription> descriptions;
    private List<Long> descriptionIds;

    // /**
    // * 储存的数据文件夹路径
    // */
    // private String dataSetFolderPath;

    /**
     * 数据集相关的任务分类
     */
    // private Set<AssociatedTask> associatedTasks;

    private List<Integer> associatedTaskIds;

    /**
     * 属性特征
     */
    private List<Integer> attrCharIds;

    /**
     * 数据集相关的主题区域
     */

    private Integer areaId;

    /**
     * 数据集特征
     */
    private List<Integer> dataSetCharIds;

    /**
     * 标识书是否是已更新过的传输体
     */
    private Boolean isUpdated;

    // /**
    // * 数据集的文件容器（包括文件详情、服务器的存储位置等）
    // */
    // private Set<DataSetContainer> dataSetContainers;
}
