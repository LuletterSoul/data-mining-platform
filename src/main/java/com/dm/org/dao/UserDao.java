package com.dm.org.dao;

import com.dm.org.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ÁõÏéµÂ qq313700046@icloud.com .
 * @date created in  22:50 2017/6/28.
 * @description
 * @modified by:
 */
@Repository
public class UserDao
{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void saveUser(User user)
    {
        sessionFactory.getCurrentSession().save(user);
    }

    public User findByUserId(String id)
    {
        return (User)sessionFactory.getCurrentSession().get(User.class,id);
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserList(){
        return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

}
