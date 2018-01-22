package com.vero.dm.model;


import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:14 2017/7/10.
 * @description
 * @modified by:
 */

@Data
@Entity
@Table(name = "data_set_row", catalog = "")
public class DataSetRow
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private Long rowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerId", foreignKey = @ForeignKey(name = "CONTAINER_ID_FK"))
    private DataSetContainer dataSetContainer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "row", fetch = FetchType.LAZY)
    private List<DataSetCell> cellList;

}
