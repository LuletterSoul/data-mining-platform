package com.vero.dm.service.excel;

import com.vero.dm.importer.core.SimpleExcelExporter;
import com.vero.dm.model.Student;
import org.springframework.stereotype.Component;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  18:48 2018/2/7.
 * @since data-mining-platform
 */

@Component
public class StudentExporter extends SimpleExcelExporter<Student> {
}
