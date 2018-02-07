package com.vero.dm.importer.core;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vero.dm.importer.scan.ExcelModuleDefinitionRegistrar;

import lombok.extern.slf4j.Slf4j;


/**
 * 维护Excel导入模板的文件信息 服务层可以从该类获取到系统注册的模板文件路径 可以删除模板
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:32 2018/2/7.
 * @since data-mining-platform
 */

@Slf4j
@Component
public class DefaultExcelModuleManager implements ExcelModuleManager
{

    private Map<Class<?>, String> class2ModelPaths = new HashMap<>();

    @Autowired
    public DefaultExcelModuleManager()
    {
        registerExcelModulePath();
    }

    @Override
    public void registerExcelModulePath()
    {
        this.class2ModelPaths = ExcelModuleDefinitionRegistrar.class2ModelPaths;
        if (this.class2ModelPaths.isEmpty())
        {
            throw new IllegalArgumentException("未注册对应的Excel模板文件.");
        }
    }

    @Override
    public void put(String path, Class<?> clazz)
    {
        this.class2ModelPaths.put(clazz, path);
    }

    @Override
    public String getModulePath(Class<?> clazz)
    {

        String modulePath = class2ModelPaths.get(clazz);
        if (modulePath == null)
        {
            throw new IllegalArgumentException("找不到对应的Excel模板文件.");
        }
        return modulePath;
    }

    @Override
    public String deleteModule(Class<?> clazz)
    {
        String modulePath = getModulePath(clazz);
        File excelModule = new File(modulePath);
        if (excelModule.isFile())
        {
            if (excelModule.delete())
            {
                log.debug("删除Excel模板[{}]", modulePath);
            }
        }
        return modulePath;
    }

    @Override
    public List getExcelModuleHeadTitle(Class<?> clazz)
    {
        return null;
    }
}
