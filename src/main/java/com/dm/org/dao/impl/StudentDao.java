package com.dm.org.dao.impl;

import com.dm.org.dto.StudentDTO;
import com.dm.org.model.FavoriteStatus;
import com.dm.org.model.Student;
import com.dm.org.model.StudentStatus;
import com.dm.org.model.Student_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class StudentDao extends BaseDao<Student,String> {
    protected StudentDao() {
        super(Student.class);
    }

    /**
     * 查询出所有对外暴露的DTO
     * 注意测试时候Student Model 的内嵌对象应该使用外连接
     * 避免查询出空列表
     * @return
     */
    public List<Student> getStudentList() {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot);
        return getSession().createQuery(criteriaQuery).list();
    }

    public int deleteStudentById(String studentId) {
//        buildCriteriaDelete();
//        criteriaDelete.where(baseBuilder.equal(baseRoot.get(Student_.studentId), studentId));
//        return getSession().createQuery(criteriaQuery).executeUpdate();
        String hqlString = "DELETE Student s where s.studentId = :studentId";
        return getSession().createQuery(hqlString).setParameter("studentId", studentId).executeUpdate();
    }

    /**
     * 根据学号查询学生信息
     * @param studentId 学号
     * @return 学生信息
     */
    public Student getStudentById(String studentId) {
        String hqlString = "select s from Student s where s.studentId =:studentId";
        return (Student) getSession()
                            .createQuery(hqlString)
                                .setParameter("studentId", studentId).getSingleResult();
    }

    /**
     * 获取学生的学号
     *
     * @return 全部学生的学号
     */
    @SuppressWarnings("unchecked")
    public List<String> getStudentIds() {
        String hqlString = "select s.studentId from Student s";
        return getSession().createQuery(hqlString).getResultList();
    }

    /**
     * 收藏学生
     * @param studentIds 要收藏的学生的学号列表
     * @return 更新的行数
     */
    public int markStudents(List<String> studentIds) {
        String sqlString = "UPDATE user_info set favoriteId = 1"
                + "where studentId  in :studentIds";
        return getSession().createNativeQuery(sqlString).setParameter("studentIds", studentIds).executeUpdate();
    }

    /**
     * 取消收藏学生
     * @param studentIds 要收藏的学生的学号列表
     * @return 更新的行数
     */
    public int unMarkStudents(List<String> studentIds) {
        String sqlString = "UPDATE user_info set favoriteId = 0"
                + "where studentId  in :studentIds";
        return getSession().createNativeQuery(sqlString).setParameter("studentIds", studentIds).executeUpdate();
    }

    public int deleteByArray(List<String> studentIds) {
        String hqlString = "DELETE Student s where s.studentId in :studentIds";
        return getSession().createQuery(hqlString).setParameter("studentIds", studentIds).executeUpdate();
    }

    public List<Student> getStudentsWithList(List<String> studentIds) {
        String hqlString = "select s from Student s where s.studentId in :studentIds";
        return getSession().createQuery(hqlString).setParameter("studentIds", studentIds).getResultList();
    }

    public FavoriteStatus getFavoriteStatusPersisted(Integer statusId) {
        String hqlString = "from FavoriteStatus f where f.favoriteId = :statusId";
        return (FavoriteStatus) getSession().createQuery(hqlString).setParameter("statusId", statusId).getSingleResult();
    }

    public StudentStatus getStudentStatus(Integer statusId) {
        String hqlString = "from StudentStatus s where s.statusId = :statusId";
        return (StudentStatus) getSession().createQuery(hqlString).setParameter("statusId", statusId).getSingleResult();
    }
}
