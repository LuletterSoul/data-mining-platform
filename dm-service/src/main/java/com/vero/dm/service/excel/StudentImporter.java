package com.vero.dm.service.excel;


import org.springframework.stereotype.Component;

import com.vero.dm.importer.core.SimpleExcelImporter;
import com.vero.dm.model.Student;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:37 2018/2/7.
 * @since data-mining-platform
 */

@Component
public class StudentImporter extends SimpleExcelImporter<Student>
{
    public StudentImporter()
    {
        super(Student.class);
    }
}
