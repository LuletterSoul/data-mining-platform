package com.dm.org.dao.impl;

import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class GroupDao extends BaseDao<DataMiningGroup,String> {
    public GroupDao() {
        super(DataMiningGroup.class);
    }

    public List<DataMiningGroup> fetchGroups(List<String> groupIds) {
        String hqlString = "select distinct g from DataMiningGroup g " +
                "left join  g.groupMembers where g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameterList("groupIds",groupIds)
                .getResultList();
    }
    public DataMiningGroup fetchGroup(String groupId) {
        String hqlString = "select distinct g from DataMiningGroup g " +
                "left join g.groupMembers " +
                "left join DataMiningTask t " +
                "left join t.algorithms " +
                "where g.groupId = :groupId";
        return (DataMiningGroup) getSession().createQuery(hqlString)
                .setParameter("groupId",groupId)
                .getSingleResult();
    }

    public List<DataMiningGroup> getGroups(List<String> groupIds) {
        String hqlString = "select g from DataMiningGroup g " +
                "where g.groupId in :groupIds";
        return getSession().createQuery(hqlString)
                .setParameterList("groupIds",groupIds)
                .getResultList();
    }

    public List<Student> fetchGroupMembers(String groupId) {
        String hqlString = "select g from DataMiningGroup g left join g.groupMembers where g.groupId =:groupId";
        return getSession().createQuery(hqlString).setParameter("groupId", groupId)
                .getResultList();
    }

    public int removeMemberById(String groupId, String studentId) {
        String sqlString = "delete group_student_ref form group_info g " +
                             "left join group_student_ref " +
                                "left join user_info u where u.studentId = :studentId and g.groupId = :groupId";
        return getSession().createNativeQuery(sqlString)
                            .setParameter("studentId",studentId)
                                .setParameter("groupId",groupId).executeUpdate();
    }

    public int removeMembersWithArray(String groupId, List<String> studentIds) {
        String hqlString = "delete DataMiningGroup.groupMembers member " +
                            "where DataMiningGroup.groupId =:groupId " +
                            "and member.studentId in :studentIds";
        return getSession().createQuery(hqlString).setParameter("groupId", groupId)
                .setParameter("studentIds", studentIds).executeUpdate();
    }

    public int removeAllMembers(String groupId) {
        String hqlString = "delete DataMiningGroup.groupMembers where DataMiningGroup.groupId = :groupId";
        return getSession().createQuery(hqlString).setParameter("groupId", groupId).executeUpdate();
    }
}
