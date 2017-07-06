package com.dm.org.dao.supprot;


import com.dm.org.dao.BaseRepository;
import com.dm.org.identifier.EntityIdentifier;
import com.dm.org.query.PaginationDescriptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.dm.org.utils.HibernateQueryAdapter.updateStringAssemble;
import static com.dm.org.utils.HibernateQueryAdapter.whereStringAssemble;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 16:58 2017/7/5.
 * @description
 * @modified by:
 */
public class BaseDao<E, PK extends Serializable> implements BaseRepository<E, PK>
{
    private SessionFactory sessionFactory;

    private Class<E> clazz;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    protected BaseDao(Class<E> eClass)
    {
        clazz = eClass;
    }

    public Class<E> getClazz()
    {
        return clazz;
    }


    public Serializable save(E entity)
    {
        return getSession().save(entity);
    }

    public int saveBatch(Set<E> set)
    {
        int count = 0;
        for (E entity : set)
        {
            getSession().save(entity);
            ++count;
            if (count % 50 == 0)
            {
                getSession().flush();
                getSession().clear();
            }
        }
        return set.size();
    }

    public void update(E entity)
    {
        getSession().saveOrUpdate(entity);
    }

    public void delete(E e)
    {
        getSession().delete(e);
    }

    public void deleteByProperty(String propertyName, Object value)
    {
        List<E> es = findByProperty("", propertyName, value, null);
        for (E e : es)
        {
            delete(e);
        }
    }

    public E findById(PK id)
    {
        return getSession().get(getClazz(), id);
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll(String columns, PaginationDescriptor pagination)
    {
        String hqlString = "";
        if (columns == null || columns.trim().equals(""))
        {
            hqlString = from();
        }
        else
        {
            hqlString = "SELECT " + columns + " " + from();
        }
        Query query = getSession().createQuery(hqlString);
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return query.list();
    }

    public List<E> findByProperties(String columns, String[] propertyNames, Object[] values,
                                    PaginationDescriptor pagination)
    {
        String hqlString = "";
        if (columns == null || columns.trim().equals(""))
        {
            hqlString = from();
        }
        else
        {
            hqlString = "SELECT " + columns + " " + from();
        }
        Query query = getSession().createQuery(hqlString + where(propertyNames, values));
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return query.list();
    }

    public List<E> findByProperty(String columns, String propertyName, Object value,
                                  PaginationDescriptor pagination)
    {
        String[] propertyNames = {propertyName};
        Object[] values = {value};
        return findByProperties(columns, propertyNames, values, pagination);
    }

    public List<E> findByPropertiesFuzzy(String columns, String[] propertyNames, Object[] values,
                                         PaginationDescriptor pagination)
    {
        String hqlString = "";
        if (columns == null || columns.trim().equals(""))
        {
            hqlString = from();
        }
        else
        {
            hqlString = "SELECT " + columns + " " + from();
        }
        Query query = getSession().createQuery(hqlString + whereFuzzy(propertyNames, values));
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return query.list();
    }

    public List<E> findByPropertyFuzzy(String columns, String propertyName, Object value,
                                       PaginationDescriptor pagination)
    {
        String[] propertyNames = {propertyName};
        Object[] values = {value};
        return findByProperties(columns, propertyNames, values, pagination);
    }

    public int countAll()
    {
        return Integer.valueOf(getSession().createQuery(count()).uniqueResult().toString());
    }

    public int countByProperty(String propertyName, Object value)
    {
        String[] propertyNames = {propertyName};
        Object[] values = {value};
        return countByProperties(propertyNames, values);
    }

    public int countByProperties(String[] propertyNames, Object[] values)
    {
        Query query = getSession().createQuery(count() + where(propertyNames, values));
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public List<Map<String, Object>> queryMapByHQL(String hqlString, Object[] args,
                                                   PaginationDescriptor pagination)
    {
        Session session = getSession();
        Query query = session.createQuery(hqlString);
        if (args != null && args.length > 0)
        {
            for (int i = 0; i < args.length; i++ )
                query.setParameter(i + "", args[i]);
        }
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return query.list();
    }

    public int updateBatchByHql(Map<String, Object> updateMappingValues,
                                Map<String, Object> conditionMappingValues)
    {
        String hqlString = "";
        if (updateMappingValues == null) return 0;
        if (conditionMappingValues != null)
        {
            hqlString = updateStringAssemble(updateMappingValues,getClazz().getSimpleName())
                        + whereStringAssemble(conditionMappingValues);
        }
        else
        {
            hqlString = updateStringAssemble(updateMappingValues,getClazz().getSimpleName());
        }
        Query query = getSession().createQuery(hqlString);
        Set<String> keySet = updateMappingValues.keySet();
        int setPlaceHolderOrder = 0;
        for (String key : keySet)
        {
            Object value = updateMappingValues.get(key);
            if (value instanceof EntityIdentifier)
            {
                query.setParameter(setPlaceHolderOrder + "", value);
            }
        }
        return query.executeUpdate();
    }

    public int countByPropertiesFuzzy(String[] propertyNames, Object[] values)
    {
        Query query = getSession().createQuery(count() + whereFuzzy(propertyNames, values));
        return Integer.parseInt(query.uniqueResult().toString());
    }

    private String from()
    {
        return "from " + getClazz().getName();
    }

    private String count()
    {
        return "select count(1) " + from();
    }

    private String where(String[] propertyNames, Object[] values)
    {
        StringBuilder whereStringBuilder = new StringBuilder(" where 1 = 1");
        for (int i = 0, len = propertyNames.length; i < len; i++ )
        {
            whereStringBuilder.append(" and ");
            if (values[i] instanceof String)
            {
                whereStringBuilder.append(propertyNames[i]).append("='").append(values[i]).append(
                        "'");
            }
            else
            {
                whereStringBuilder.append(propertyNames[i]).append("=").append(values[i]);
            }
        }
        return whereStringBuilder.toString();
    }

    private String whereFuzzy(String[] propertyNames, Object[] values)
    {
        StringBuilder fuzzyHqlBuilder = new StringBuilder(" where 1 = 1");
        for (int i = 0, len = propertyNames.length; i < len; i++ )
        {
            fuzzyHqlBuilder.append(" and ");
            if (values[i] instanceof String)
            {
                fuzzyHqlBuilder.append(propertyNames[i]).append(" like '%").append(
                        values[i]).append("%'");
            }
            else
            {
                fuzzyHqlBuilder.append(propertyNames[i]).append("=").append(values[i]);
            }
        }
        return fuzzyHqlBuilder.toString();
    }
}
