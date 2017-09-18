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
@Table(name = "student_status",catalog = "")
public class StudentStatus {
    private int statusId;
    private String chineseValue;
    private String englishValue;

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getChineseValue() {
        return chineseValue;
    }

    public void setChineseValue(String chineseValue) {
        this.chineseValue = chineseValue;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String value) {
        this.englishValue = value;
    }
}
