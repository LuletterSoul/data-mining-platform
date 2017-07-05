package com.dm.org.utils;

import com.dm.org.identifier.EntityIdentifier;

import java.util.Map;
import java.util.Set;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:00 2017/7/5.
 * @description
 * @modified by:
 */
public class HibernateQueryAdapter
{
    private static int currentPlaceholder;

    public static String updateStringAssemble(Map<String, Object> updateMappingValues,String classSimpleName)
    {
        return getHqlUpdateFragmentStringByMapping(updateMappingValues,
                        classSimpleName);
    }

    public static String whereStringAssemble(Map<String, Object> conditionMappingValues)
    {
        return getHqlConditionFragmentStringByMapping(conditionMappingValues);
    }

    private static StringBuilder buildUpdateFragmentString(String classSimpleName)
    {
        return new StringBuilder(
                "update " + classSimpleName + " set ");
    }
    public static String getHqlConditionFragmentStringByMapping(Map<String, Object> mapping)
    {
        StringBuilder fragmentBuilder = new StringBuilder(" where 1 = 1 ");
        Set<String> propertySet = mapping.keySet();
        for (String key : propertySet)
        {
            Object value = mapping.get(key);
            propertyTypeResolveAndBuild(key, value, fragmentBuilder, 1);
        }
        currentPlaceholder = 0;
        return fragmentBuilder.toString();
    }

    public static String getHqlUpdateFragmentStringByMapping(Map<String, Object> mapping,
                                                             String classSimpleName)
    {
        StringBuilder updateHqlBuilder = buildUpdateFragmentString(classSimpleName);
        Set<String> propertySet = mapping.keySet();
        boolean isFirstProperty = true;
        for (String key : propertySet)
        {
            Object value = mapping.get(key);
            if (isFirstProperty)
            {
                propertyTypeResolveAndBuild(key, value, updateHqlBuilder, 0);
                isFirstProperty = false;
            }
            else
            {
                propertyTypeResolveAndBuild(key, value, updateHqlBuilder, 0).insert(0,",");
            }
        }
        currentPlaceholder = 0;
        return updateHqlBuilder.toString();
    }

    private static String setHqlFragmentPlaceholders(String key, int placeholder)
    {
        return key + " = ?" + placeholder;
    }

    private static StringBuilder propertyTypeResolveAndBuild(String key, Object value,
                                                             StringBuilder fragmentBuilder,
                                                             int resolveType)
    {
        if (resolveType == 1)
        {
            if (value instanceof String)
            {
                fragmentBuilder.append(" and ").append(key).append("='").append(value).append("'");
            }
            else if (value instanceof EntityIdentifier)
            {
                fragmentBuilder.append(" and ").append(
                        setHqlFragmentPlaceholders(key, currentPlaceholder));
                ++currentPlaceholder;
            }
            else
            {
                fragmentBuilder.append(" and ").append(key).append(" = ").append(value);
            }

        }
        else
        {
            if (value instanceof String)
            {
                fragmentBuilder.append(key).append("='").append(value).append("'");
            }
            else if (value instanceof EntityIdentifier)
            {
                fragmentBuilder.append(setHqlFragmentPlaceholders(key, currentPlaceholder));
                ++currentPlaceholder;
            }
            else
            {
                fragmentBuilder.append(key).append(" = ").append(value);
            }

        }
        return fragmentBuilder;
    }
}
