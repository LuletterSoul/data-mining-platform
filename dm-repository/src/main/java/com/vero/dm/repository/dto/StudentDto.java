package com.vero.dm.repository.dto;


import java.util.LinkedList;
import java.util.List;

import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
public class StudentDto
{
    private String studentId;

    private String studentName;

    private String grade;

    private String profession;

    private String className;

    private StudentStatus status;

    private FavoriteStatus favorite;

    private long finishedTaskCount;

    public StudentDto(Student student)
    {
        setStudentId(student.getStudentId());
        setClassName(student.getClassName());
        setGrade(student.getGrade());
        setStudentName(student.getStudentName());
        setProfession(student.getProfession());
        setStatus(student.getStatus());
        setFavorite(student.getFavorite());
    }

    public StudentDto() {
    }

    public StudentDto(String studentName, String grade, String profession, String className,
                      StudentStatus status, FavoriteStatus favorite)
    {}

    public StudentDto(Student student, long count)
    {
        this(student);
        setFinishedTaskCount(count);
    }

    public StudentDto(String studentId, String studentName, String grade, String profession,
                      String className, StudentStatus status, FavoriteStatus favorite)
    {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
        this.profession = profession;
        this.className = className;
        this.status = status;
        this.favorite = favorite;
    }

    public static StudentDto build(Student student)
    {
        return new StudentDto(student.getStudentId(), student.getStudentName(), student.getGrade(),
            student.getProfession(), student.getClassName(), student.getStatus(),
            student.getFavorite());
    }

    public static List<StudentDto> build(List<Student> students)
    {
        List<StudentDto> studentDtos = new LinkedList<StudentDto>();
        for (Student student : students)
        {
            studentDtos.add(StudentDto.build(student));
        }
        return studentDtos;
    }

    // public static Student clone(StudentDto studentDTO) {
    // Student student =new Student();
    // student.setStudentName(student.getStudentName());
    // student.setClassName(student.getClassName());
    // student.setStudentId(student.getStudentId());
    // student.setGrade(student.getGrade());
    // student.setFavorite(student.getFavorite());
    // student.setStatus(student.getStatus());
    // student.setProfession(student.getProfession());
    // return student;
    // }

}
