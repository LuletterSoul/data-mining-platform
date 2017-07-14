package com.dm.org.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:14 2017/7/10.
 * @description
 * @modified by:
 */

@Entity
@Table(name = "data_set_row", catalog = "")
public class DataSetRow
{
    private Long rowId;

    private DataSetContainer dataSetContainer;

    private List<DataSetCell> cellList;

    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerId",
            foreignKey = @ForeignKey(name = "CONTAINER_ID_FK"))
    public DataSetContainer getDataSetContainer() {
        return dataSetContainer;
    }

    public void setDataSetContainer(DataSetContainer dataSetContainer) {
        this.dataSetContainer = dataSetContainer;
    }



    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true
            ,mappedBy = "row",fetch = FetchType.LAZY)
    public List<DataSetCell> getCellList()
    {
        return cellList;
    }

    public void setCellList(List<DataSetCell> cells)
    {
        this.cellList = cells;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetRow that = (DataSetRow) o;

        return Objects.equal(this.rowId, that.rowId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(rowId);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("rowId", rowId)
                .add("dataSetContainer", dataSetContainer)
                .toString();
    }
}
