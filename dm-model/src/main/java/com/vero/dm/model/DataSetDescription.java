package com.vero.dm.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:49 2018/1/24.
 * @since data-minning-platform
 */

@Entity
@Data
@Table(name = "data_set_description")
public class DataSetDescription
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long descriptionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "collectionId")
    private DataSetCollection dataSetCollection;

    private String title;

    private String detail;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataSetDescription that = (DataSetDescription)o;
        return Objects.equal(descriptionId, that.descriptionId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), descriptionId);
    }
}
