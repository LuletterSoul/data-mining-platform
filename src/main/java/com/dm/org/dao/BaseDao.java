package com.dm.org.dao;

import com.dm.org.query.PaginationDescriptor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  16:47 2017/7/5.
 * @description
 * @modified by:
 */
public interface BaseDao<E, PK extends Serializable>
{
    // Object getByUniquePrimaryKey(Class clazz,Serializable id);
    // Object get(String queryString,Object ... values);
    // void saveOrUpdate(Object entity);
    // void delete(Object entity);
    // void update(Object entity);
    // List getList(String queryString);
    /**
     * 保存对象
     *
     * @param entity 对象实例
     *
     * @return
     */
    Serializable save(E entity);

    /**
     * 批量保存对象
     *
     * @param set 对象实例
     *
     * @return
     */
    int saveBatch(Set<E> set);

    void update(E entity);

    void delete(E entity);

    /**
     * 删除propertyName == value 的记录
     */
    void deleteByProperty(String propertyName, Object value);

    /**
     * 通过ID查询对象
     *
     * @param id
     *            主键
     * @return
     */
    E findById(PK id) throws Exception;

    /**
     * 获取当前对象的所有记录 @ columns 需要返回的列名 @ pagination 分页信息
     *
     * @return 所有记录
     */
    List<E> findAll(String columns, PaginationDescriptor pagination);

    /**
     * 通过多个属性查找
     *
     * @param propertyNames
     *            属性名称数组
     * @param values
     *            属性值数组
     * @return
     */
    List<E> findByProperties(String columns, String[] propertyNames, Object[] values,
                             PaginationDescriptor pagination);

    /**
     * 通过多个属性查找
     *
     * @param propertyName
     *            属性名称数组
     * @param value
     *            属性值数组
     * @return
     */
    List<E> findByProperty(String columns, String propertyName, Object value,
                           PaginationDescriptor pagination);

    /**
     * 通过多个属性模糊查找
     *
     * @param propertyNames
     *            属性名称数组
     * @param values
     *            属性值数组
     * @return
     */
    List<E> findByPropertiesFuzzy(String columns, String[] propertyNames, Object[] values,
                                  PaginationDescriptor pagination);

    /**
     * 通过某个属性模糊查找
     *
     * @param propertyName
     *            属性名称
     * @param value
     *            属性值
     * @return  属性值数组
     */
    List<E> findByPropertyFuzzy(String columns, String propertyName, Object value,
                                PaginationDescriptor pagination);

    /**
     * 统计所有记录的总数
     *
     * @return 总数
     */
    int countAll();

    /**
     * 统计数据库中当propertyName=value时的记录总数
     *
     * @param propertyNames
     * @param values
     * @return
     */
    int countByProperties(String[] propertyNames, Object[] values);


    /**
     * 统计数据库中当propertyName=value时的记录总数
     *
     * @param propertyName
     * @param value
     * @return
     */
    int countByProperty(String propertyName, Object value);


    int countByPropertiesFuzzy(String[] propertyNames, Object[] values);

    // /**
    // * 根据SQL查询记录数
    // */
    // int queryCountBySQL(SqlBean sql);
    //
    // int mapCountByHQL(String hql,Object[] args);
    // /**
    // * 查找并通过某一属性排序
    // *
    // * @param property
    // * 排序依据的顺序
    // * @param isSequence
    // * 是否顺序排序
    // */
    //
    // /**
    // * 根据SQL语法查询
    // *
    // * @param sql
    // * @return
    // */
    // List<E> queryBySQL(SqlBean sql);
    //
    //
    // /**
    // * 根据SQL语法分页查询
    // *
    // * @param sql
    // * @returnsc_system_db
    // */
    // List<E> queryBySQL(SqlBean sql,Pagination pagination);

    /**
     * 根据SQL语法查询
     * @param hqlString
     * @return 返回自定义的Map类型
     */
    List<Map<String, Object>> queryMapByHQL(String hqlString, Object[] args,
                                            PaginationDescriptor pagination);
    /**
     * 根据hql语法批量更新
     * @param updateMappingValues 传入更新属性参数对
     * @param conditionMappingValues 出入条件属性参数对
     * @return 返回自定义的Map类型
     */
    int updateBatchByHql(Map<String, Object> updateMappingValues,
                         Map<String, Object> conditionMappingValues);

}
