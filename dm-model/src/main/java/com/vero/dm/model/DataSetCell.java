package com.vero.dm.model;


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
@Table(name = "data_set_cell", catalog = "")
public class DataSetCell
{

    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private Long cellId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributeId", foreignKey = @ForeignKey(name = "ATTRIBUTE_FK_ID"))
    private DataSetAttribute dataSetAttribute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rowId", foreignKey = @ForeignKey(name = "ROW_FK_ID"))
    private DataSetRow row;

    @Enumerated(EnumType.STRING)
    @Column(name = "cellType")
    private CellType cellType;

}
