package com.vero.dm.repository;

import org.springframework.stereotype.Component;

/**
 * {@link com.dm.org.webconfig.hibernate.HibernateConfiguration}
 * 配置类为Dao层设置的标记接口,无任何方法体等定义操作
 * 目的是让{@link Component @Component}识别当前包为基础包
 * 并启动对基础包及其所有子包下已被{@link Component @Component}标记的Dao组件的Bean注册服务
 *
 * @author 刘祥德 qq313700046@icloud.com.
 * @date created in 0:35 2017/7/18.
 * @description
 * @modified by:
 * @see com.dm.org.service.ServiceScanningMarker
 * @see com.dm.org.controller.ControllerScanningMarker
 * @see com.dm.org.webconfig.aop.AspectScanningMarker
 */
@Component
public interface DaoScanningMarker
{
}
