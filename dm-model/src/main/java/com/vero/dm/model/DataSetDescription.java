package com.vero.dm.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

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
}
