package com.dm.org.service.impl;

import com.dm.org.dao.BaseRepository;
import com.dm.org.dao.supprot.BaseDao;
import com.dm.org.service.BaseService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:04 2017/7/5.
 * @description
 * @modified by:
 */
@Service
public abstract class AbstractBaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK>
{

    protected BaseDao<E, PK> baseDao;

    public Session getOperableSession()
    {
        return baseDao.getSession();
    }

    @Autowired
    public void setBaseDao(BaseDao<E, PK> baseDao)
    {
        this.baseDao = baseDao;
    }

    public List<E> findAll()
            throws Exception
    {
        return baseDao.findAll("", null);
    }

    public List<E> findAll(String columns)
            throws Exception
    {
        return baseDao.findAll(columns, null);
    }

    public Serializable save(E e)
            throws Exception
    {
       return baseDao.save(e);
    }

    public void saveBatch(Set<E> e) throws Exception
    {
        baseDao.saveBatch(e);
    }

    public void delete(E e)
            throws Exception
    {
        baseDao.delete(e);
    }

    public void deleteByProperty(String propertyName, Object value)
            throws Exception
    {
        baseDao.deleteByProperty(propertyName, value);
    }

    public List<E> findByProperty(String columns, String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByProperty(columns, propertyName, value, null);
    }

    public List<E> findByProperty(String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByProperty("", propertyName, value, null);
    }

    public List<E> findByProperties(String columns, String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.findByProperties(columns, propertyNames, values, null);
    }

    public List<E> findByProperties(String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.findByProperties("", propertyNames, values, null);
    }

    public List<E> findByPropertyFuzzy(String columns, String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByPropertyFuzzy(columns, propertyName, value, null);
    }

    public List<E> findByPropertyFuzzy(String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByPropertyFuzzy("", propertyName, value, null);
    }

    public List<E> findByPropertiesFuzzy(String columns, String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.findByProperties(columns, propertyNames, values, null);
    }

    public List<E> findByPropertiesFuzzy(String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.findByProperties("", propertyNames, values, null);
    }

    public int updateEntityBatch(Map<String, Object> updateMapping,
                                 Map<String, Object> conditionMapping)
    {
        return baseDao.updateBatchByHql(updateMapping, conditionMapping);
    }

    public List<Map<String, Object>> queryMapByHQL(String hql, Object[] args)
            throws Exception
    {
        return baseDao.queryMapByHQL(hql, args, null);

    }

    public int countAll()
            throws Exception
    {
        return baseDao.countAll();
    }

    public int countByProperty(String propertyName, Object value)
            throws Exception
    {
        return baseDao.countByProperty(propertyName, value);
    }

    public int countByProperties(String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.countByProperties(propertyNames, values);
    }

    public int countByPropertiesFuzzy(String propertyName, Object value)
            throws Exception
    {
        String[] properNames = {propertyName};
        Object[] values = {value};
        return baseDao.countByProperties(properNames, values);
    }

    public int countByPropertiesFuzzy(String[] propertyNames, Object[] values)
            throws Exception
    {
        return baseDao.countByProperties(propertyNames, values);
    }

    public void saveOrUpdate(E e)
            throws Exception
    {
        baseDao.save(e);
    }

    public E findById(PK id)
            throws Exception
    {
        return baseDao.findById(id);
    }

    public void update(E e)
            throws Exception
    {
        baseDao.update(e);
    }

    public Class<E> getClazz()
            throws Exception
    {
        return baseDao.getClazz();
    }
}
