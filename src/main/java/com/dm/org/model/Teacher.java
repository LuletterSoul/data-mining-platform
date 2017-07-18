package com.dm.org.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:37 2017/7/14.
 * @description
 * @modified by:
 */
@Entity
@DiscriminatorValue(value = "Teacher")
public class Teacher extends User
 {
     private String teacherName;
     private String teacherId;


     public String getTeacherName()
      {
         return teacherName;
     }

     public void setTeacherName(String teacherName)
     {
         this.teacherName = teacherName;
     }
 }
