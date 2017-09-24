package com.dm.org.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Entity
@Table(name = "sys_statistics")
public class SystemStatistics
{
    private int statisticId;


    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }
}
