package com.vero.dm.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "data_set_container", catalog = "")
public class DataSetContainer implements EntityIdentifier
{
    /**
     * 文件容器Id
     */
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "containerId")
    private String containerId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件大小
     */
    private Double size;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件的所属的数据集
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collectionId", foreignKey = @ForeignKey(name = "COLLECTION_ID_FK"))
    private DataSetCollection dataSetCollection;

    private String filePath;

}
