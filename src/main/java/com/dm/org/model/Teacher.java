package com.dm.org.model;


        import com.google.common.base.MoreObjects;
        import com.google.common.base.Objects;

        import javax.persistence.*;
        import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:37 2017/7/14.
 * @description
 * @modified by:
 */
@Entity
@DiscriminatorValue(value = "Teacher")
public class Teacher extends User
{
    private String teacherName;

    private String teacherId;

    private Set<Student> favoriteStudent;

    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(String teacherId)
    {
        this.teacherId = teacherId;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_stu_rel"
            , joinColumns = @JoinColumn(name = "userId")
            , inverseJoinColumns = @JoinColumn(name = "studentId"))
    public Set<Student> getFavoriteStudent()
    {
        return favoriteStudent;
    }

    public void setFavoriteStudent(Set<Student> favoriteStudent)
    {
        this.favoriteStudent = favoriteStudent;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("teacherName", teacherName)
                .add("teacherId", teacherId)
                .add("favoriteStudent", favoriteStudent)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher that = (Teacher) o;

        return Objects.equal(this.teacherId, that.teacherId) &&
                Objects.equal(this.userId, that.userId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(teacherName, teacherId, favoriteStudent, userId, username, password,
                publicSalt, privateSalt, accountStatus);
    }
}
