package com.dm.org.model;

import javax.persistence.*;
import java.util.Set;

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

     private String grade;

     private String className;

     private String profession;

     private Set<GroupInfo> groupInfos;


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

     public String getGrade() {
         return grade;
     }

     public void setGrade(String grade) {
         this.grade = grade;
     }

     public String getClassName() {
         return className;
     }

     public void setClassName(String className) {
         this.className = className;
     }

     public String getProfession() {
         return profession;
     }

     public void setProfession(String profession) {
         this.profession = profession;
     }

     @OneToMany(cascade = CascadeType.ALL,mappedBy = "student",fetch = FetchType.EAGER)
     public Set<GroupInfo> getGroupInfos() {
         return groupInfos;
     }

     public void setGroupInfos(Set<GroupInfo> groupInfos) {
         this.groupInfos = groupInfos;
     }
 }
