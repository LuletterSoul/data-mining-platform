package com.vero.dm.service.impl;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.vero.dm.repository.*;
import com.vero.dm.service.BaseService;

import lombok.Getter;
import lombok.Setter;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:04 2017/7/5.
 * @description
 * @modified by:
 */
@Getter
@Setter
@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractBaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK>
{
    public AbstractBaseServiceImpl()
    {}

    @Autowired
    protected DataMiningTaskJpaRepository taskJpaRepository;

    @Autowired
    protected DataMiningGroupJpaRepository groupJpaRepository;

    @Autowired
    protected DataSetCollectionJpaRepository collectionJpaRepository;

    @Autowired
    protected DataSetContainerJpaRepository containerJpaRepository;

    @Autowired
    protected PermissionJpaRepository permissionJpaRepository;

    @Autowired
    protected RoleJpaRepository roleJpaRepository;

    @Autowired
    protected StudentJpaRepository studentJpaRepository;

    @Autowired
    protected TeacherJpaRepository teacherJpaRepository;

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Autowired
    protected DataSetDescriptionJpaRepository descriptionJpaRepository;

    @Autowired
    protected AreaJpaRepository areaJpaRepository;

    @Autowired
    protected AttributeCharJpaRepository attributeCharJpaRepository;

    @Autowired
    protected AssociatedTaskJpaRepository associatedTaskJpaRepository;

    @Autowired
    protected DataSetCharJpaRepository setCharJpaRepository;

    @Autowired
    protected AlgorithmJpaRepository algorithmJpaRepository;

    @Autowired
    protected MiningGrammarRepository miningGrammarRepository;

    @Autowired
    protected MiningResultRepository miningResultRepository;

    @Autowired
    protected ResultRecordRepository resultRecordRepository;

    @Autowired
    protected StageRepository stageRepository;

    @Autowired
    protected TaskStageJpaRepository taskStageJpaRepository;

    @Override
    public List<E> get(Pageable pageable)
    {
        return null;
    }

    @Override
    public List<E> findAll()
    {
        return null;
    }

    @Override
    public List<Object[]> findAll(String[] columns)
        throws Exception
    {
        return null;
    }

    @Override
    public Serializable save(E e)
    {
        return null;
    }

    @Override
    public void saveBatch(Set<E> set)
    {

    }

    @Override
    public void delete(E e)
        throws Exception
    {

    }

    @Override
    public Integer deleteAll()
    {
        return null;
    }

    @Override
    public void deleteById(PK pk)
    {

    }

    @Override
    public void deleteByProperty(String propertyName, Object value)
        throws Exception
    {

    }

    @Override
    public void deleteByProperties(Map<String, Object> constraintMap)
    {

    }

    @Override
    public List<E> findByProperty(String[] columns, String propertyName, Object value)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByProperty(String propertyName, Object value)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByProperties(String[] columns, Map<String, Object> constraintMap)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByProperties(Map<String, Object> constraintMap)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByPropertyFuzzy(String[] columns, String propertyName, Object value)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByPropertyFuzzy(String propertyName, Object value)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByPropertiesFuzzy(String[] columns, Map<String, Object> constraintMap)
        throws Exception
    {
        return null;
    }

    @Override
    public List<E> findByPropertiesFuzzy(Map<String, Object> constraintMap)
        throws Exception
    {
        return null;
    }

    @Override
    public int updateEntityBatch(Map<String, Object> updateMapping,
                                 Map<String, Object> conditionMapping)
    {
        return 0;
    }

    @Override
    public List<Map<String, Object>> queryMapByHQL(String hql, Object[] args)
        throws Exception
    {
        return null;
    }

    @Override
    public int countAll()
        throws Exception
    {
        return 0;
    }

    @Override
    public int countByProperty(String propertyName, Object value)
        throws Exception
    {
        return 0;
    }

    @Override
    public int countByProperties(Map<String, Object> constraintMap)
        throws Exception
    {
        return 0;
    }

    @Override
    public int countByPropertiesFuzzy(String propertyName, Object value)
        throws Exception
    {
        return 0;
    }

    @Override
    public int countByPropertiesFuzzy(Map<String, Object> constraintMap)
        throws Exception
    {
        return 0;
    }

    @Override
    public void saveOrUpdate(E e)
        throws Exception
    {

    }

    @Override
    public E findById(PK id)
    {
        return null;
    }

    @Override
    public void update(E e)
    {

    }

    @Override
    public Class<E> getClazz()
        throws Exception
    {
        return null;
    }
    //
    // public List<E> get(Pageable pageable)
    // {
    // return baseDao.get(pageable);
    // }
    //
    // public List<E> findAll()
    // {
    // return baseDao.findAll();
    // }
    //
    // public List<Object[]> findAll(String[] columns)
    // throws Exception
    // {
    // return baseDao.findAll(columns, null);
    // }
    //
    // public Serializable save(E e)
    // {
    // return baseDao.save(e);
    // }
    //
    // public void saveBatch(Set<E> e)
    // {
    // baseDao.saveBatch(e);
    // }
    //
    // public void delete(E e)
    // throws Exception
    // {
    // baseDao.delete(e);
    // }
    //
    // @Override
    // public Integer deleteAll()
    // {
    // return baseDao.deleteAll();
    // }
    //
    // public List<E> findByProperty(String propertyName, Object value)
    // throws Exception
    // {
    // return baseDao.findByProperty(null, propertyName, value, null);
    // }
    //
    // public List<E> findByProperties(String[] columns, Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.findByProperties(columns, constraintMap, null);
    // }
    //
    // public List<E> findByProperties(Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.findByProperties(null, constraintMap, null);
    // }
    //
    // public List<E> findByPropertyFuzzy(String[] columns, String propertyName, Object value)
    // throws Exception
    // {
    // return baseDao.findByPropertyFuzzy(columns, propertyName, value, null);
    // }
    //
    // public List<E> findByPropertyFuzzy(String propertyName, Object value)
    // throws Exception
    // {
    // return baseDao.findByPropertyFuzzy(null, propertyName, value, null);
    // }
    //
    // public List<E> findByPropertiesFuzzy(String[] columns, Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.findByProperties(columns, constraintMap, null);
    // }
    //
    // public List<E> findByPropertiesFuzzy(Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.findByProperties(null, constraintMap, null);
    // }
    //
    // public int updateEntityBatch(Map<String, Object> updateMapping,
    // Map<String, Object> conditionMapping)
    // {
    // return baseDao.updateBatchByHql(updateMapping, conditionMapping);
    // }
    //
    // public List<Map<String, Object>> queryMapByHQL(String hql, Object[] args)
    // throws Exception
    // {
    // return baseDao.queryMapByHQL(hql, args, null);
    //
    // }
    //
    // public int countAll()
    // throws Exception
    // {
    // return baseDao.countAll();
    // }
    //
    // public int countByProperty(String propertyName, Object value)
    // throws Exception
    // {
    // return baseDao.countByProperty(propertyName, value);
    // }
    //
    // public int countByProperties(Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.countByProperties(constraintMap);
    // }
    //
    // public int countByPropertiesFuzzy(String propertyName, Object value)
    // throws Exception
    // {
    // Map<String, Object> constraintMap = new HashMap<String, Object>();
    // constraintMap.put(propertyName, value);
    // return baseDao.countByProperties(constraintMap);
    // }
    //
    // public int countByPropertiesFuzzy(Map<String, Object> constraintMap)
    // throws Exception
    // {
    // return baseDao.countByProperties(constraintMap);
    // }
    //
    // public void saveOrUpdate(E e)
    // throws Exception
    // {
    // baseDao.save(e);
    // }
    //
    // public E findById(PK id)
    // {
    // return baseDao.findById(id);
    // }
    //
    // public void update(E e)
    // {
    // baseDao.update(e);
    // }
    //
    // public Class<E> getClazz()
    // throws Exception
    // {
    // return baseDao.getClazz();
    // } public void deleteById(PK pk)
    // {
    // baseDao.deleteById(pk);
    // }
    //
    // public void deleteByProperty(String propertyName, Object value)
    // throws Exception
    // {
    // baseDao.deleteByProperty(propertyName, value);
    // }
    //
    // public void deleteByProperties(Map<String, Object> constraintMap)
    // {
    // baseDao.deleteByProperties(constraintMap);
    // }
    //
    // public List<E> findByProperty(String[] columns, String propertyName, Object value)
    // throws Exception
    // {
    // return baseDao.findByProperty(columns, propertyName, value, null);
    // }

}
