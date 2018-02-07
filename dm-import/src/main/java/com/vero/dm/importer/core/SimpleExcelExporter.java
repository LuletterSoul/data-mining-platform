package com.vero.dm.importer.core;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:41 2018/2/7.
 * @since data-mining-platform
 */

public class SimpleExcelExporter<E> implements ExcelExporter<E>
{

    @Override
    public void exportExcelModule(File file, HttpServletResponse response)
    {
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
