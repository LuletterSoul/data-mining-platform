package com.vero.dm.importer.annotations;


import static org.springframework.context.annotation.ComponentScan.Filter;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.vero.dm.importer.scan.ExcelModuleDefinitionRegistrar;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:56 2018/2/3.
 * @since data-mining-platform
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ExcelModuleDefinitionRegistrar.class)
public @interface ExcelModelScan {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Filter[] includeFilters() default {};

    Filter[] excludeFilters() default {};
}
