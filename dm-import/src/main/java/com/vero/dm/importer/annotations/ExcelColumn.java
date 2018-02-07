package com.vero.dm.importer.annotations;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Excel
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:38 2018/2/3.
 * @since data-mining-platform
 */

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ExcelColumn {

    /**
     * @return 列名
     */
    String name() default "";

    /**
     * @return 自适应列宽
     */
    boolean autoSizeColumn() default true;

    /**
     * @return 列标
     */
    int colIndex() default -1;

    /**
     * @return 忽略此列
     */
    boolean skip() default false;

}
