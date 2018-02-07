package com.vero.dm.importer;


import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:38 2018/2/5.
 * @since data-mining-platform
 */

public class POIUtils
{

    public static final String XLSX_SUFFIX = ".xlsx";

    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final int mDefaultRowAccessWindowSize = 100;

    public static SXSSFWorkbook newSXSSFWorkbook(int rowAccessWindowSize)
    {
        return new SXSSFWorkbook(rowAccessWindowSize);
    }

    public static SXSSFWorkbook newSXSSFWorkbook()
    {
        return newSXSSFWorkbook(mDefaultRowAccessWindowSize);
    }

    public static SXSSFSheet newSXSSFSheet(SXSSFWorkbook wb, String sheetName)
    {
        return (SXSSFSheet)wb.createSheet(sheetName);
    }

    public static SXSSFRow newSXSSFRow(SXSSFSheet sheet, int index)
    {
        return (SXSSFRow)sheet.createRow(index);
    }

    public static SXSSFCell newSXSSFCell(SXSSFRow row, int index)
    {
        return (SXSSFCell)row.createCell(index);
    }

    /**
     * 设定单元格宽度 (手动/自动)
     * 
     * @param sheet
     *            工作薄对象
     * @param index
     *            单元格索引
     * @param width
     *            指定宽度,-1为自适应
     * @param value
     *            自适应需要单元格内容进行计算
     */
    public static void setColumnWidth(SXSSFSheet sheet, int index, short width, String value)
    {
        if (width == -1 && value != null && !"".equals(value))
        {
            sheet.setColumnWidth(index, (short)(value.length() * 750));
        }
        else
        {
            width = width == -1 ? 200 : width;
            sheet.setColumnWidth(index, (short)(width * 71.4));
        }
    }

    public static void writeByLocalOrBrowser(HttpServletResponse response, String fileName,
                                             SXSSFWorkbook wb, OutputStream out)
        throws Exception
    {
        if (response != null)
        {
            // response对象不为空,响应到浏览器下载
            response.setContentType(XLSX_CONTENT_TYPE);
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(
                String.format("%s%s", fileName, XLSX_SUFFIX), "UTF-8"));
            if (out == null)
            {
                out = response.getOutputStream();
            }
        }
        wb.write(out);
        out.flush();
        out.close();
    }

    public static void checkExcelFile(File file)
    {
        if (file == null || !file.exists())
        {
            throw new IllegalArgumentException("excel文件不存在.");
        }

        checkExcelFile(file.getAbsolutePath());
    }

    public static void checkExcelFile(String file)
    {
        if (!file.endsWith(XLSX_SUFFIX))
        {
            throw new IllegalArgumentException("抱歉,目前ExcelKit仅支持.xlsx格式的文件.");
        }
    }

}
