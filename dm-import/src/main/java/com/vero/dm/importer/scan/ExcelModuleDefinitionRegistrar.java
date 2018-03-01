package com.vero.dm.importer.scan;


import static org.springframework.core.io.support.ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.importer.annotations.ExcelModelScan;
import com.vero.dm.importer.core.ExcelModuleGenerator;
import com.vero.dm.importer.core.SimpleExcelModuleGenerator;
import com.vero.dm.importer.filter.ExcelTypeFilter;
import com.vero.dm.importer.process.FileSpecificationKey;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * 一个Excel模板扫描注册器的简单实现,当Spring Mvc容器启动时，
 * {@link com.vero.dm.importer.annotations.ExcelModelScan}注解的启动类 触发该类进行对应的基包扫描
 * {@link com.vero.dm.importer.core.ExcelModuleGenerator}生成的模板路径 被记录在#class2ModelPaths中
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:33 2018/2/3.
 * @since data-mining-platform
 */

@Slf4j
@Getter
public class ExcelModuleDefinitionRegistrar implements ImportBeanDefinitionRegistrar
{
    private static final String RESOURCE_PATTERN = "/**/*.class";

    private static final Map<String, Class<?>> EXCEL_UNDERLYING_MAPPING = new HashMap<>();

    private ExcelModuleGenerator excelModuleGenerator = new SimpleExcelModuleGenerator();

    /**
     * 只要启动了扫描,该表中就存储了对应模型文件路径信息
     */
    public static Map<Class<?>, String> class2ModelPaths = new HashMap<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry)
    {
        AnnotationAttributes annAttr = AnnotationAttributes.fromMap(
            importingClassMetadata.getAnnotationAttributes(ExcelModelScan.class.getName()));
        String[] basePackages = annAttr.getStringArray("value");
        basePackages = extractBasePackages(importingClassMetadata, annAttr, basePackages);
        log.info("Base packages:[{}]", basePackages[0]);
        List<TypeFilter> includeFilters = extractTypeFilters(
            annAttr.getAnnotationArray("includeFilters"));
        // 增加一个包含的过滤器,扫描到的类只要不是抽象的,接口,枚举,注解,及匿名类那么就算是符合的
        includeFilters.add(new ExcelTypeFilter());
        List<TypeFilter> excludeFilters = extractTypeFilters(
            annAttr.getAnnotationArray("excludeFilters"));
        List<Class<?>> candidates = scanPackages(basePackages, includeFilters, excludeFilters);
        if (candidates.isEmpty())
        {
            log.info("扫描指定Excel基础包[{}]时未发现符合条件的基础类", Arrays.toString(basePackages));
            return;
        }
        generateModulesFromCandidates(candidates);
    }

    private void generateModulesFromCandidates(List<Class<?>> candidates)
    {
        candidates.forEach(this::cacheModulePathInfo);
    }

    private void cacheModulePathInfo(Class<?> c)
    {
        try
        {
            String filePath = excelModuleGenerator.generate(c);
            class2ModelPaths.put(c, filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String[] extractBasePackages(AnnotationMetadata importingClassMetadata,
                                         AnnotationAttributes annAttr, String[] basePackages)
    {
        if (ObjectUtils.isEmpty(basePackages))
        {
            basePackages = annAttr.getStringArray("basePackages");
        }
        if (ObjectUtils.isEmpty(basePackages))
        {
            basePackages = getPackagesFromClasses(annAttr.getClassArray("basePackageClasses"));
        }
        if (ObjectUtils.isEmpty(basePackages))
        {
            basePackages = new String[] {
                ClassUtils.getPackageName(importingClassMetadata.getClassName())};
        }
        return basePackages;
    }

    private void registerBeanDefinitions(List<Class<?>> internalClasses,
                                         BeanDefinitionRegistry registry)
    {
        for (Class<?> clazz : internalClasses)
        {
            if (EXCEL_UNDERLYING_MAPPING.values().contains(clazz))
            {
                log.debug("重复扫描{}类,忽略重复注册.", clazz.getName());
                continue;
            }
            String beanName = generateExcelFileName(clazz);
            EXCEL_UNDERLYING_MAPPING.put(beanName, clazz);
        }
    }

    private String generateExcelFileName(Class<?> underlying)
    {
        String interfaceName = underlying.getInterfaces()[0].getSimpleName();
        String beanName = FileSpecificationKey.class.getSimpleName() + "#" + interfaceName;
        if (EXCEL_UNDERLYING_MAPPING.containsKey(beanName))
        {
            beanName = beanName + "#" + getNextOrderSuffix(interfaceName);
        }
        return beanName;
    }

    /**
     * 生成后注册的重名接口后缀
     *
     * @param className
     * @return
     */
    private Integer getNextOrderSuffix(String className)
    {
        int order = 1;
        for (String hsfBeanName : EXCEL_UNDERLYING_MAPPING.keySet())
        {
            if (hsfBeanName.substring(hsfBeanName.indexOf("#") + 1).startsWith(className))
            {
                String curOrder = hsfBeanName.substring(
                    (FileSpecificationKey.class.getSimpleName() + "#" + className).length());
                if (!StringUtils.isEmpty(curOrder))
                {
                    order = Math.max(Integer.valueOf(curOrder), order);
                }
            }
        }
        return order;
    }

    private List<Class<?>> scanPackages(String[] basePackages, List<TypeFilter> includeFilters,
                                        List<TypeFilter> excludeFilters)
    {
        List<Class<?>> candidates = new ArrayList<Class<?>>();
        for (String pkg : basePackages)
        {
            try
            {
                candidates.addAll(findCandidateClasses(pkg, includeFilters, excludeFilters));
            }
            catch (IOException e)
            {
                log.error("扫描指定Excel基础包[{}]时出现异常", pkg);
            }
        }
        return candidates;
    }

    private List<Class<?>> findCandidateClasses(String basePackage,
                                                List<TypeFilter> includeFilters,
                                                List<TypeFilter> excludeFilters)
        throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("开始扫描指定包{}下的所有类", basePackage);
        }
        List<Class<?>> candidates = new ArrayList<>();
        String packageSearchPath = CLASSPATH_ALL_URL_PREFIX + replaceDotByDelimiter(basePackage) + RESOURCE_PATTERN;
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory(resourceLoader);
        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(
            resourceLoader).getResources(packageSearchPath);
        for (Resource resource : resources)
        {
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            if (isCandidateResource(reader, readerFactory, includeFilters, excludeFilters))
            {
                Class<?> candidateClass = transform(reader.getClassMetadata().getClassName());
                if (candidateClass != null)
                {
                    candidates.add(candidateClass);
                    log.debug("扫描到符合要求导入规则的Excel基础类:[{}]", candidateClass.getName());
                }
            }
        }
        return candidates;
    }

    private Class<?> transform(String className)
    {
        Class<?> clazz = null;
        try
        {
            clazz = ClassUtils.forName(className, this.getClass().getClassLoader());
        }
        catch (ClassNotFoundException e)
        {
            log.info("未找到指定Excel模型类{%s}", className);
        }
        return clazz;
    }

    protected boolean isCandidateResource(MetadataReader reader,
                                          MetadataReaderFactory readerFactory,
                                          List<TypeFilter> includeFilters,
                                          List<TypeFilter> excludeFilters)
        throws IOException
    {
        for (TypeFilter tf : excludeFilters)
        {
            if (tf.match(reader, readerFactory))
            {
                return false;
            }
        }
        for (TypeFilter tf : includeFilters)
        {
            if (tf.match(reader, readerFactory))
            {
                return true;
            }
        }
        return false;
    }

    private List<TypeFilter> extractTypeFilters(AnnotationAttributes[] annAttrs)
    {
        List<TypeFilter> typeFilters = new ArrayList<>();
        for (AnnotationAttributes filter : annAttrs)
        {
            typeFilters.addAll(typeFiltersFor(filter));
        }
        return typeFilters;
    }

    private String replaceDotByDelimiter(String path)
    {
        return StringUtils.replace(path, ".", "/");
    }

    private List<TypeFilter> typeFiltersFor(AnnotationAttributes filterAttributes)
    {
        List<TypeFilter> typeFilters = new ArrayList<TypeFilter>();
        FilterType filterType = filterAttributes.getEnum("type");

        for (Class<?> filterClass : filterAttributes.getClassArray("classes"))
        {
            switch (filterType)
            {
                case ANNOTATION:
                    Assert.isAssignable(Annotation.class, filterClass,
                        "@ExcelModelScan 注解类型的Filter必须指定一个注解");
                    Class<Annotation> annotationType = (Class<Annotation>)filterClass;
                    typeFilters.add(new AnnotationTypeFilter(annotationType));
                    break;
                case ASSIGNABLE_TYPE:
                    typeFilters.add(new AssignableTypeFilter(filterClass));
                    break;
                case CUSTOM:
                    Assert.isAssignable(TypeFilter.class, filterClass,
                        "@ExcelModelScan 自定义Filter必须实现TypeFilter接口");
                    TypeFilter filter = BeanUtils.instantiateClass(filterClass, TypeFilter.class);
                    typeFilters.add(filter);
                    break;
                default:
                    throw new IllegalArgumentException("当前TypeFilter不支持: " + filterType);
            }
        }
        return typeFilters;
    }

    private String[] getPackagesFromClasses(Class[] classes)
    {
        if (ObjectUtils.isEmpty(classes))
        {
            return null;
        }
        List<String> basePackages = new ArrayList<String>(classes.length);
        for (Class<?> clazz : classes)
        {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        return (String[])basePackages.toArray();
    }
}
