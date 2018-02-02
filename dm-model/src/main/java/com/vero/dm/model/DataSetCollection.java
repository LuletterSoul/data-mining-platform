package com.vero.dm.model;


import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description 数据集实体
 * @modified by: 2018.1.24
 */
@Data
@Entity
@Table(name = "data_set_collection", catalog = "")
@ToString(exclude = {"dataSetContainers"})
public class DataSetCollection

{
    /**
     * 数据集的ID
     */
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
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

    /**
     * 数据集详情
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dataSetCollection")
    private Set<DataSetDescription> descriptions;

    /**
     * 储存的数据文件夹路径
     */
    private String dataSetFolderPath;

    /**
     * 数据集相关的任务分类
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "set_task_rel", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<AssociatedTask> associatedTasks;

    /**
     * 属性特征
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attr_type_rel", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<AttributeCharacteristic> attributeCharacteristics;

    /**
     * 数据集相关的主题区域
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "areaId", foreignKey = @ForeignKey(name = "AREA_TYPE_FK"))
    private AreaType area;

    /**
     * 数据集特征
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collection_char_rel", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "charId"))
    private Set<DataSetCharacteristic> dataSetCharacteristics;

    /**
     * 数据集的文件容器（包括文件详情、服务器的存储位置等）
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "dataSetCollection", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DataSetContainer> dataSetContainers;

    public DataSetCollection()
    {}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataSetCollection that = (DataSetCollection)o;
        return Objects.equal(collectionId, that.collectionId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), collectionId);
    }
}
