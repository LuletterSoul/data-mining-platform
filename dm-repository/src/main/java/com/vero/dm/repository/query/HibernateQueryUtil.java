//package com.vero.dm.repository.query;
//
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.persister.entity.AbstractEntityPersister;
//import org.hibernate.query.Query;
//import org.springframework.stereotype.Component;
//
//import com.vero.dm.exception.ColumnMappingNotExistException;
//import com.vero.dm.exception.ConstrainMappingNotNullException;
//import com.vero.dm.exception.DataAccessObjectException;
//import com.vero.dm.exception.DataObjectNotFoundException;
//import com.vero.dm.model.EntityIdentifier;
//
//
///**
// * @author 刘祥德 qq313700046@icloud.com .
// * @date created in 17:00 2017/7/5.
// * @description
// * @modified by:
// */
//@Component
//public class HibernateQueryUtil
//{
//
//    private static int currentPlaceholder;
//
//    private static SessionFactory factory;
//
//    private static final int RESOLUTION_TYPE_WHERE = 1;
//
//    private static final int RESOLUTION_TYPE_UPDATE = 0;
//
//    // @Autowired
//    // public void setSessionFactory(SessionFactory sessionFactory)
//    // {
//    // factory = sessionFactory;
//    // }
//
//    public static int setHqlQueryParameters(Query query, Map<String, Object> mapping,
//                                            int orderFeedback)
//    {
//        Set keySet = mapping.keySet();
//        int placeHolderOrder = orderFeedback;
//        for (Object o : keySet)
//        {
//            String key = (String)o;
//            Object value = mapping.get(key);
//            if (value instanceof EntityIdentifier)
//            {
//                query.setParameter("" + placeHolderOrder, value);
//                placeHolderOrder++ ;
//            }
//        }
//        return placeHolderOrder;
//    }
//
//    public boolean isExistPropertyMapping(Class clazz, String columnName)
//    {
//        AbstractEntityPersister persister = buildAbstractEntityPersister(clazz);
//        String[] propertyNames = persister.getPropertyNames();
//        return resolvePropertyMapExistence(propertyNames, columnName, persister);
//    }
//
//    public static boolean checkQueryPropertyJustifiability(Set columnNames, Class clazz)
//    {
//        try
//        {
//            String message = isExistPropertyMapping(clazz, columnNames);
//            if (!message.equals(""))
//            {
//                message += " column couldn't match appropriate mapping in " + clazz.getSimpleName()
//                           + " table.";
//                throw new ColumnMappingNotExistException(message);
//            }
//            return true;
//
//        }
//        catch (ColumnMappingNotExistException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static boolean checkListIsEmptyOrNot(List list)
//    {
//        try
//        {
//            if (list.size() == 0)
//            {
//                throw new DataObjectNotFoundException(
//                    "could not find object that matching certain conditions.");
//            }
//            return true;
//        }
//        catch (DataObjectNotFoundException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static String buildOptionalSelectStatement(String[] columns, String from)
//    {
//        StringBuilder selectStatementBuilder = new StringBuilder();
//        if (columns == null)
//        {
//            selectStatementBuilder.append(from);
//        }
//        else
//        {
//            selectStatementBuilder.append("SELECT ").append(
//                buildStandardSelectClause(columns)).append(" ").append(from);
//        }
//        return selectStatementBuilder.toString();
//    }
//
//    public static String buildUpdateStatement(Map<String, Object> updateMappingValues,
//                                              Map<String, Object> conditionMappingValues,
//                                              Class clazz)
//        throws DataAccessObjectException
//    {
//        StringBuilder updateStatementBuilder = new StringBuilder();
//        if (updateMappingValues == null)
//            throw new ConstrainMappingNotNullException("Constraint Mapping Couldn't Be Null.");
//        if (conditionMappingValues != null)
//        {
//            updateStatementBuilder.append(
//                buildUpdateClause(updateMappingValues, clazz.getSimpleName())).append(
//                    buildWhereClause(conditionMappingValues));
//        }
//        else
//        {
//            updateStatementBuilder.append(
//                buildUpdateClause(updateMappingValues, clazz.getSimpleName()));
//        }
//        return updateStatementBuilder.toString();
//    }
//
//    public static String buildDeleteStatement(Map<String, Object> constraintMap, String from)
//    {
//        StringBuilder deleteStatementBuilder = new StringBuilder("DELETE " + from);
//        deleteStatementBuilder.append(buildWhereClause(constraintMap));
//        return deleteStatementBuilder.toString();
//    }
//
//    private static String buildStandardSelectClause(String[] columns)
//    {
//        StringBuilder stringBuilder = new StringBuilder(columns[0]);
//        for (int i = 1; i < columns.length; i++ )
//        {
//            stringBuilder.append(",").append(columns[i]);
//        }
//        return stringBuilder.toString();
//    }
//
//    /*
//     * 构建where子句
//     */
//    // public static String buildWhereClause(Map<String, Object> constraintMap)
//    // {
//    // StringBuilder whereStringBuilder = new StringBuilder(" where 1 = 1");
//    // Set keySet = constraintMap.keySet();
//    // for (Object object : keySet) {
//    // whereStringBuilder.append(" and ");
//    // String key = (String) object;
//    // Object value = constraintMap.get(key);
//    // if (value instanceof String) {
//    // whereStringBuilder.append(key).append("='").append(value).append("'");
//    // } else {
//    // whereStringBuilder.append(key).append("=").append(value);
//    // }
//    //
//    // }
//    // return whereStringBuilder.toString();
//    // }
//    public static synchronized String buildWhereClause(Map<String, Object> mapping)
//    {
//        StringBuilder fragmentBuilder = new StringBuilder(" where 1 = 1 ");
//        propertyTypeResolveAndBuild(mapping, fragmentBuilder, RESOLUTION_TYPE_WHERE);
//        currentPlaceholder = 0;
//        return fragmentBuilder.toString();
//    }
//
//    public static String buildWhereFuzzyClause(Map<String, Object> constraintMap)
//    {
//        StringBuilder fuzzyHqlBuilder = new StringBuilder(" where 1 = 1");
//        Set keySet = constraintMap.keySet();
//        for (Object object : keySet)
//        {
//            fuzzyHqlBuilder.append(" and ");
//            String key = (String)object;
//            Object value = constraintMap.get(key);
//            if (value instanceof String)
//            {
//                fuzzyHqlBuilder.append(key).append(" like '%").append(value).append("%' ");
//            }
//            else
//            {
//                fuzzyHqlBuilder.append(key).append("=").append(value);
//            }
//        }
//        return fuzzyHqlBuilder.toString();
//    }
//
//    public static String isExistPropertyMapping(Class clazz, Set columnNames)
//    {
//        AbstractEntityPersister abstractEntityPersister = buildAbstractEntityPersister(clazz);
//        String[] propertyNames = abstractEntityPersister.getPropertyNames();
//        StringBuilder stringBuilder = new StringBuilder("");
//        boolean isFirst = true;
//        for (Object key : columnNames)
//        {
//            String columnName = (String)key;
//            if (!resolvePropertyMapExistence(propertyNames, columnName, abstractEntityPersister))
//            {
//                if (isFirst)
//                {
//                    isFirst = false;
//                    stringBuilder.append(columnName);
//                }
//                else
//                {
//                    stringBuilder.append(",").append(columnName);
//                }
//            }
//        }
//        return stringBuilder.toString();
//    }
//
//    private static AbstractEntityPersister buildAbstractEntityPersister(Class clazz)
//    {
//        // Metamodel metaModel = factory.getMetamodel();
//        // MetamodelImpl metamodelImpl = (MetamodelImpl) metaModel;
//        // return (AbstractEntityPersister) metamodelImpl.entityPersister(clazz);
//        return null;
//    }
//
//    private static boolean resolvePropertyMapExistence(String[] propertyNames, String column,
//                                                       AbstractEntityPersister persister)
//    {
//        boolean isExist = false;
//        for (String prop : propertyNames)
//        {
//            String[] cols = persister.getPropertyColumnNames(prop);
//            for (String col : cols)
//            {
//                if (col.equals(column))
//                {
//                    isExist = true;
//                }
//            }
//        }
//        return isExist;
//    }
//
//    // private static String[] mergeAllProperties(AbstractEntityPersister entityPersister)
//    // {
//    // String [] properties=entityPersister.getPropertyColumnNames()
//    // }
//    // public static String updateStringAssemble(Map<String, Object> updateMappingValues,
//    // String classSimpleName) {
//    // return getHqlUpdateFragmentStringByMapping(updateMappingValues, classSimpleName);
//    // }
//    //
//    // public static String whereStringAssemble(Map<String, Object> conditionMappingValues) {
//    // return getHqlConditionFragmentStringByMapping(conditionMappingValues);
//    // }
//
//    public static synchronized String buildUpdateClause(Map<String, Object> mapping,
//                                                        String classSimpleName)
//    {
//        currentPlaceholder = 0;
//        StringBuilder updateHqlBuilder = new StringBuilder("UPDATE " + classSimpleName + " set ");
//        propertyTypeResolveAndBuild(mapping, updateHqlBuilder, RESOLUTION_TYPE_UPDATE);
//        return updateHqlBuilder.toString();
//    }
//
//    private static String setHqlFragmentPlaceholders(String key, int placeholder)
//    {
//        return key + " = ?" + placeholder;
//    }
//
//    private static void propertyTypeResolveAndBuild(Map<String, Object> mapping,
//                                                    StringBuilder fragmentBuilder,
//                                                    int resolutionType)
//    {
//        Set keySet = mapping.keySet();
//        boolean isFirstProperty = true;
//        for (Object o : keySet)
//        {
//            String key = (String)o;
//            Object value = mapping.get(key);
//            if (isFirstProperty)
//            {
//                isFirstProperty = false;
//            }
//            else if (resolutionType != RESOLUTION_TYPE_WHERE)
//            {
//                fragmentBuilder.append(",");
//            }
//            if (resolutionType == RESOLUTION_TYPE_WHERE)
//            {
//                if (value instanceof String)
//                {
//                    fragmentBuilder.append(" and ").append(key).append("='").append(value).append(
//                        "'");
//                }
//                else if (value instanceof EntityIdentifier)
//                {
//                    fragmentBuilder.append(" and ").append(
//                        setHqlFragmentPlaceholders(key, currentPlaceholder));
//                    ++currentPlaceholder;
//                }
//                else
//                {
//                    fragmentBuilder.append(" and ").append(key).append(" = ").append(value);
//                }
//
//            }
//            else
//            {
//                if (value instanceof String)
//                {
//                    fragmentBuilder.append(key).append("='").append(value).append("'");
//                }
//                else if (value instanceof EntityIdentifier)
//                {
//                    fragmentBuilder.append(setHqlFragmentPlaceholders(key, currentPlaceholder));
//                    ++currentPlaceholder;
//                }
//                else
//                {
//                    fragmentBuilder.append(key).append(" = ").append(value);
//                }
//
//            }
//        }
//    }
//}
