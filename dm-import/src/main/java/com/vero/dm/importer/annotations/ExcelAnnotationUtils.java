package com.vero.dm.importer.annotations;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:57 2018/2/6.
 * @since data-mining-platform
 */

public class ExcelAnnotationUtils
{
    public static List<Field> extractAnnotatedFields(Class<?> clazz,
                                                     Class<? extends Annotation> annotationClass)
    {
        if (clazz.isAnnotationPresent(ExcelModel.class))
        {
            ExcelModel excelModel = clazz.getAnnotation(ExcelModel.class);
            if (excelModel.mapByProp())
            {
                Field[] fields = clazz.getDeclaredFields();
                return new ArrayList<>(Arrays.asList(fields));
            }
        }
        Field[] fields = clazz.getDeclaredFields();
        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(annotationClass))
            {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }
}
