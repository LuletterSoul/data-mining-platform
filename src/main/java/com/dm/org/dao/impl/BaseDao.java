package com.dm.org.dao.impl;


import com.dm.org.dao.BaseRepository;
import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.query.PaginationDescriptor;
import com.dm.org.utils.HibernateQueryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 16:58 2017/7/5.
 * @description
 * @modified by:
 */
@SuppressWarnings("unchecked")
public class BaseDao<E, PK extends Serializable> implements BaseRepository<E, PK>
{
    private SessionFactory sessionFactory;

    private Class<E> clazz;

    protected Root<E> baseRoot;

    protected CriteriaBuilder baseBuilder;

    protected CriteriaQuery<E> criteriaQuery;

    protected CriteriaDelete<E> criteriaDelete;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    protected BaseDao(Class<E> eClass)
    {

        clazz = eClass;
    }

    public Root<E> getBaseRoot()
    {
        return baseRoot;
    }

    public CriteriaBuilder getBaseBuilder()
    {
        return baseBuilder;
    }

    public synchronized CriteriaQuery<E> buildCriteriaQuery()
    {
        this.baseBuilder = getSession().getCriteriaBuilder();
        this.criteriaQuery = this.baseBuilder.createQuery(getClazz());
        this.baseRoot = criteriaQuery.from(getClazz());
        return criteriaQuery;
    }

    @Override
    public CriteriaDelete<E> buildCriteriaDelete()
    {
        this.baseBuilder = getSession().getCriteriaBuilder();
        this.criteriaDelete = this.baseBuilder.createCriteriaDelete(getClazz());
        this.baseRoot = criteriaDelete.from(getClazz());
        return criteriaDelete;
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

    @Override
    public Page<E> get(Pageable pageable) {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot);
        List<E> entityList = getSession().createQuery(criteriaQuery)
                                        .setFirstResult(pageable.getOffset())
                                        .setMaxResults(pageable.getPageSize())
                                        .list();
        return new PageImpl<E>(entityList, pageable, entityList.size());
    }

    @Override
    public List<E> findAll() {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot);
        return getSession().createQuery(criteriaQuery).list();
    }

    public Integer deleteAll()
    {
        String hqlString = "delete " + from();
        return getSession().createQuery(hqlString).executeUpdate();
    }

    public void deleteById(PK pk)
    {
        delete(findById(pk));
    }

    public void deleteByProperty(String propertyName, Object value)
    {
        List<E> es = findByProperty(null, propertyName, value, null);
        for (E e : es)
        {
            delete(e);
        }
    }

    public int deleteByProperties(Map<String, Object> constraintMap)
    {
        String hqlString = HibernateQueryUtil.buildDeleteStatement(constraintMap, from());
        Query query = getSession().createQuery(hqlString);
        HibernateQueryUtil.setHqlQueryParameters(query, constraintMap, 0);
        return query.executeUpdate();
    }

    public E findById(PK id)
    {
        return getSession().find(getClazz(), id);
    }

    public List<Object[]> findAll(String[] columns, PaginationDescriptor pagination)
    {
        String hqlString = HibernateQueryUtil.buildOptionalSelectStatement(columns, from());
        Query query = getSession().createQuery(hqlString);
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return query.list();
    }

    public List<E> findByProperties(String[] columns, Map<String, Object> constraintMap,
                                    PaginationDescriptor pagination)
    {
        String hqlString = HibernateQueryUtil.buildOptionalSelectStatement(columns, from());
        Query query = getSession().createQuery(hqlString + where(constraintMap));
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        HibernateQueryUtil.checkQueryPropertyJustifiability(constraintMap.keySet(), clazz);
        List<E> list = query.list();
        HibernateQueryUtil.checkListIsEmptyOrNot(list);
        return list;
    }

    public List<E> findByProperty(String[] columns, String propertyName, Object value,
                                  PaginationDescriptor pagination)
    {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put(propertyName, value);
        return findByProperties(columns, conditionMap, pagination);
    }

    public List<E> findByPropertiesFuzzy(String[] columns, Map<String, Object> constraintMap,
                                         PaginationDescriptor pagination)
    {

        String hqlString = HibernateQueryUtil.buildOptionalSelectStatement(columns, from());
        HibernateQueryUtil.checkQueryPropertyJustifiability(constraintMap.keySet(), clazz);
        Query query = getSession().createQuery(hqlString + whereFuzzy(constraintMap));
        List<E> list = query.list();
        HibernateQueryUtil.checkListIsEmptyOrNot(list);
        if (pagination != null)
        {
            query.setFirstResult(pagination.getBeginPage());
            query.setMaxResults(pagination.getEndPage());
        }
        return list;
    }

    public List<E> findByPropertyFuzzy(String[] columns, String propertyName, Object value,
                                       PaginationDescriptor pagination)
    {
        Map<String, Object> constraintMap = new HashMap<String, Object>();
        constraintMap.put(propertyName, value);
        return findByPropertiesFuzzy(columns, constraintMap, pagination);
    }

    public int countAll()
    {
        return Integer.valueOf(getSession().createQuery(count()).uniqueResult().toString());
    }

    public int countByProperty(String propertyName, Object value)
    {
        Map<String, Object> constraintMap = new HashMap<String, Object>();
        constraintMap.put(propertyName, value);
        return countByProperties(constraintMap);
    }

    public int countByProperties(Map<String, Object> constraintMap)
    {
        Query query = getSession().createQuery(count() + where(constraintMap));
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
        String hqlString = null;
        try
        {
            hqlString = HibernateQueryUtil.buildUpdateStatement(updateMappingValues,
                conditionMappingValues, getClazz());
        }
        catch (DataAccessObjectException e)
        {
            e.printStackTrace();
        }
        Query query = getSession().createQuery(hqlString);
        int order = HibernateQueryUtil.setHqlQueryParameters(query, updateMappingValues, 0);
        HibernateQueryUtil.setHqlQueryParameters(query, conditionMappingValues, order);
        return query.executeUpdate();
    }

    public int countByPropertiesFuzzy(Map<String, Object> constraintMap)
    {
        Query query = getSession().createQuery(count() + whereFuzzy(constraintMap));
        return Integer.parseInt(query.uniqueResult().toString());
    }

    private String from()
    {
        return "from " + getClazz().getSimpleName();
    }

    private String count()
    {
        return "select count(1) " + from();
    }

    private String where(Map<String, Object> constraintMap)
    {
        return HibernateQueryUtil.buildWhereClause(constraintMap);
    }

    private String whereFuzzy(Map<String, Object> constraintMap)
    {
        return HibernateQueryUtil.buildWhereFuzzyClause(constraintMap);
    }
}
