package com.dm.org.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Entity
@Table(name = "algorithm_param")
public class AlgorithmParameter {
    private Long paramId;
    private String paramDesc;
    private String dataType;
    private boolean isNecessary;
    private Algorithm algorithm;

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isNecessary() {
        return isNecessary;
    }

    public void setNecessary(boolean necessary) {
        isNecessary = necessary;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="algorithmId",foreignKey = @ForeignKey(name="ALGORITHM_FK_ID"))
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
