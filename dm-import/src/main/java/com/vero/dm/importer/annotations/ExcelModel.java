package com.vero.dm.importer.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 使用在模型类的开头,标注对应的类为Excel模型类
 * 可以被{@link ExcelModelScan}扫描注册
 * 从而启动对应的模板生成算法
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:04 2018/2/3.
 * @since data-mining-platform
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelModel {

    String title() default "";

    /**
     * @return sheet名，用于多sheet共存
     */
    String sheetName() default "";

    /**
     * @return 生成模板名
     */
    String name() default "";

    /**
     * 生成模板的路径
     *
     * @return
     */
    String path() default "";

    /**
     * @return 行高
     */
    short rowHeight() default -1;

    /**
     * @return 列宽
     * -1代表自动计算
     */
    short colWidth() default -1;

    /**
     * 根据列名映射，忽略自定义的列名
     * @return
     */
    boolean mapByProp() default false;

}
