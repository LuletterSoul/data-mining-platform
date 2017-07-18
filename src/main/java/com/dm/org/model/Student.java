package com.dm.org.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:36 2017/7/14.
 * @description
 * @modified by:
 */
@Entity
@DiscriminatorValue(value = "Student")
public class Student extends User
 {
     private String studentName;

     private String studentId;

     public String getStudentName()
      {
         return studentName;
     }

     public void setStudentName(String studentName)
     {
         this.studentName = studentName;
     }

     public String getStudentId()
     {
         return studentId;
     }

     public void setStudentId(String studentId)
     {
         this.studentId = studentId;
     }
 }
