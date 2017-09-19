package com.dm.org.dao;

import com.dm.org.query.PaginationDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
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
public interface BaseRepository<E, PK extends Serializable>
{
    // Object getByUniquePrimaryKey(Class clazz,Serializable id);
    // Object get(String queryString,Object ... values);
    // void saveOrUpdate(Object entity);
    // void delete(Object entity);
    // void update(Object entity);
    // List getList(String queryString);

    /**
     * 基于JPA2.0规范的Criteria新型查询,函数<code>createCriteria()</code>
     * 已被建议弃用
     * 利用SessionFactory返回的Session<code>getCriteriaBuilder()</code>
     * 建立新型的<code>CriteriaQuery</></code>
     * 利于构建良好的domain model的架构
     * 执行更完整的type-safe标准查询
     * @return 当前Session下criteria query object
     * {@link org.hibernate}
     * @see org.hibernate.Criteria
     * @see javax.persistence.criteria.CriteriaBuilder
     * @see javax.persistence.criteria.CriteriaQuery
     */
    CriteriaQuery<E> buildCriteriaQuery();


    CriteriaDelete<E> buildCriteriaDelete();

    /**
     * 保存对象
     *
     * @param entity 对象实例
     *
     * @return
     */
    Serializable save(E entity) throws Exception;

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
     * 与{@link Pageable}结合的分页查询机制
     * 通用的分页查询
     * @param pageable 分页的基础信息
     * @return 指定页面的数据
     */
    Page<E> get(Pageable pageable);

    /**
     * 获取到所有列表
     * @return 所有结果集
     */
    List<E> findAll();

    Integer deleteAll();

    void deleteById(PK pk);
    /**
     * 删除propertyName == value 的记录
     */
    void deleteByProperty(String propertyName, Object value);

    /**
     * 通过ID查询对象
     *
     * @param constraintMap where子句约束调节
     *            主键
     * @return
     */
    int deleteByProperties(Map<String, Object> constraintMap);

    /**
     * 通过ID查询对象
     *
     * @param id
     *            主键
     * @return
     */
    E findById(PK id);

    /**
     * 获取当前对象的所有记录 @ columns 需要返回的列名 @ pagination 分页信息
     *
     * @return 所有记录
     */
    List<Object[]> findAll(String[] columns, PaginationDescriptor pagination);

    /**
     * 通过多个属性查找
     *
     * @param propertyNames
     *            属性名称数组
     * @param values
     *            属性值数组
     * @return
     */
    List<E> findByProperties(String[] columns, Map<String,Object> constraintMap,
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
    List<E> findByProperty(String[] column, String propertyName, Object value,
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
    List<E> findByPropertiesFuzzy(String[] columns, Map<String,Object> constraintMap,
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
    List<E> findByPropertyFuzzy(String[] columns, String propertyName, Object value,
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
    int countByProperties(Map<String,Object> constraintMap);


    /**
     * 统计数据库中当propertyName=value时的记录总数
     *
     * @param propertyName
     * @param value
     * @return
     */
    int countByProperty(String propertyName, Object value);


    int countByPropertiesFuzzy(Map<String,Object> constraintMap);

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
