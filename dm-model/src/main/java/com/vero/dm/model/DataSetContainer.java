package com.vero.dm.model;


import javax.persistence.*;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
public class DataSetContainer
{
    /**
     * 文件容器Id
     */
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer containerId;

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
    private Long size;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataSetContainer container = (DataSetContainer) o;
        return Objects.equal(containerId, container.containerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), containerId);
    }
}
