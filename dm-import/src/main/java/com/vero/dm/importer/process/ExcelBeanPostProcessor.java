package com.vero.dm.importer.process;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:44 2018/2/3.
 * @since data-mining-platform
 */

public class ExcelBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements InitializingBean, BeanFactoryAware
{
    @Override
    public void setBeanFactory(BeanFactory beanFactory)
        throws BeansException
    {

    }

    @Override
    public void afterPropertiesSet()
        throws Exception
    {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return super.postProcessBeforeInitialization(bean, beanName);
    }
}
