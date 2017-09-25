package com.dm.org.dto;

import com.dm.org.model.FavoriteStatus;
import com.dm.org.model.Student;
import com.dm.org.model.StudentStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public class StudentDTO {
    private String studentId;
    private String studentName;
    private String grade;
    private String profession;
    private String className;
    private StudentStatus status;
    private FavoriteStatus favorite;
    private long finishedTaskCount;

    public StudentDTO(Student student) {
        setStudentId(student.getStudentId());
        setClassName(student.getClassName());
        setGrade(student.getGrade());
        setStudentName(student.getStudentName());
        setProfession(student.getProfession());
        setStatus(student.getStatus());
        setFavorite(student.getFavorite());
    }

    public StudentDTO() {
    }


    public StudentDTO(Student student, long count) {
        this(student);
        setFinishedTaskCount(count);
    }

    public StudentDTO(String studentId, String studentName, String grade, String profession, String className, StudentStatus status, FavoriteStatus favorite)
    {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
        this.profession = profession;
        this.className = className;
        this.status = status;
        this.favorite = favorite;
    }

    public static StudentDTO build(Student student) {
        return new StudentDTO(student.getStudentId(),
                student.getStudentName(),
                student.getGrade(),
                student.getProfession(),
                student.getClassName(),
                student.getStatus(),
                student.getFavorite());
    }

    public static List<StudentDTO> build(List<Student> students) {
        List<StudentDTO> studentDTOS = new LinkedList<StudentDTO>();
        for (Student student:
             students) {
            studentDTOS.add(StudentDTO.build(student));
        }
        return studentDTOS;
    }

//    public static Student clone(StudentDTO studentDTO) {
//        Student student =new Student();
//        student.setStudentName(student.getStudentName());
//        student.setClassName(student.getClassName());
//        student.setStudentId(student.getStudentId());
//        student.setGrade(student.getGrade());
//        student.setFavorite(student.getFavorite());
//        student.setStatus(student.getStatus());
//        student.setProfession(student.getProfession());
//        return student;
//    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public FavoriteStatus getFavorite() {
        return favorite;
    }

    public void setFavorite(FavoriteStatus favorite) {
        this.favorite = favorite;
    }

    public long getFinishedTaskCount() {
        return finishedTaskCount;
    }

    public void setFinishedTaskCount(long finishedTaskCount) {
        this.finishedTaskCount = finishedTaskCount;
    }
}
