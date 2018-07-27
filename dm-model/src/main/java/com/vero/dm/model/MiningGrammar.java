package com.vero.dm.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:19 2018/7/24.
 * @since data-mining-platform
 */

@Data
@Entity
@Table(name = "grammar_info", schema = "")
public class MiningGrammar
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer grammarId;

    private String grammarName;

    private String description;
}
