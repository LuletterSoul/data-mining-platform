package com.dm.org.model;

import com.dm.org.enums.CellType;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:14 2017/7/10.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "data_set_cell", catalog = "")
public class DataSetCell
{

    private Long cellId;
    private DataSetAttribute dataSetAttribute;
    private DataSetRow row;
    private CellType cellType;

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Long getCellId() {
        return cellId;
    }

    public void setCellId(Long cellId)
    {
        this.cellId = cellId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="attributeId"
            ,foreignKey = @ForeignKey(name="ATTRIBUTE_FK_ID"))
    public DataSetAttribute getDataSetAttribute()
    {
        return dataSetAttribute;
    }

    public void setDataSetAttribute(DataSetAttribute attribute)
    {
        this.dataSetAttribute = attribute;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rowId",foreignKey = @ForeignKey(name="ROW_FK_ID"))
    public DataSetRow getRow() {
        return row;
    }

    public void setRow(DataSetRow row) {
        this.row = row;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "cellType")
    public CellType getCellType()
    {
        return cellType;
    }

    public void setCellType(CellType cellType)
    {
        this.cellType = cellType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetCell that = (DataSetCell) o;

        return Objects.equal(this.cellId, that.cellId) ;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(cellId, dataSetAttribute, row, cellType);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cellId", cellId)
                .add("dataSetAttribute", dataSetAttribute)
                .add("row", row)
                .add("cellType", cellType)
                .toString();
    }
}
