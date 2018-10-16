package com.vero.dm.model;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:27 2018/7/25.
 * @since data-mining-platform
 */


@Entity
@Table(name = "result_record_info",schema = "")
@Data
public class ResultRecord {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer recordId;


//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "resultId", foreignKey = @ForeignKey(name = "RESULT_FK"))
//    private MiningResult result;
    private String fileName;

    /**
     * 提交日期
     */
    private Date submittedDate;

    /**
     * 备注
     */
    private String comment;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 是否已经检阅
     */
    private boolean isChecked = false;

}
