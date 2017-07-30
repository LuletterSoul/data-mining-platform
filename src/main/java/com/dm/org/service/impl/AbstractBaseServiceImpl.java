package com.dm.org.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.org.dao.impl.*;
import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:04 2017/7/5.
 * @description
 * @modified by:
 */
@Service
@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractBaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK>
{
    protected BaseDao<E, PK> baseDao;

    protected DataSetContainerDao containerDao;

    protected DataSetCollectionDao collectionDao;

    protected UserDao userDao;

    protected PermissionDao permissionDao;

    protected RoleDao roleDao;


    @Autowired
    public void setBaseDao(BaseDao<E, PK> baseDao)
    {
        this.baseDao = baseDao;
    }

    @Autowired
    public void setContainerDao(DataSetContainerDao containerDao) {
        this.containerDao = containerDao;
    }

    @Autowired
    public void setCollectionDao(DataSetCollectionDao collectionDao) {
        this.collectionDao = collectionDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    public List<E> findAll()
            throws Exception
    {
        return(List<E>)baseDao.findAll(null, null);
    }


    public List<Object[]> findAll(String[] columns)
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

    @Override
    public Integer deleteAll()
     {
         return baseDao.deleteAll();
    }

    public void deleteById(PK pk) {
        baseDao.deleteById(pk);
    }

    public void deleteByProperty(String propertyName, Object value)
            throws Exception
    {
        baseDao.deleteByProperty(propertyName, value);
    }

    public void deleteByProperties(Map<String, Object> constraintMap)
    {
        baseDao.deleteByProperties(constraintMap);
    }

    public List<E> findByProperty(String []columns, String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByProperty(columns, propertyName, value, null);
    }

    public List<E> findByProperty(String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByProperty(null, propertyName, value, null);
    }

    public List<E> findByProperties(String[]columns, Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.findByProperties(columns, constraintMap, null);
    }

    public List<E> findByProperties(Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.findByProperties(null, constraintMap, null);
    }

    public List<E> findByPropertyFuzzy(String []columns, String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByPropertyFuzzy(columns, propertyName, value, null);
    }

    public List<E> findByPropertyFuzzy(String propertyName, Object value)
            throws Exception
    {
        return baseDao.findByPropertyFuzzy(null, propertyName, value, null);
    }

    public List<E> findByPropertiesFuzzy(String[] columns,  Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.findByProperties(columns, constraintMap, null);
    }

    public List<E> findByPropertiesFuzzy(Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.findByProperties(null, constraintMap, null);
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

    public int countByProperties(Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.countByProperties(constraintMap);
    }

    public int countByPropertiesFuzzy(String propertyName, Object value)
            throws Exception
    {
        Map<String, Object> constraintMap = new HashMap<String, Object>();
        constraintMap.put(propertyName, value);
        return baseDao.countByProperties(constraintMap);
    }

    public int countByPropertiesFuzzy(Map<String, Object> constraintMap)
            throws Exception
    {
        return baseDao.countByProperties(constraintMap);
    }

    public void saveOrUpdate(E e)
            throws Exception
    {
        baseDao.save(e);
    }

    public E findById(PK id) throws DataObjectNotFoundException
    {
        try {
            return baseDao.findById(id);
        } catch (DataAccessObjectException e)
        {
            e.printStackTrace();
            throw new DataObjectNotFoundException(e);
        }
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
