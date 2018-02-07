package com.vero.dm.importer.core;


import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vero.dm.importer.annotations.ExcelAnnotationUtils;
import com.vero.dm.importer.annotations.ExcelColumn;
import com.vero.dm.importer.annotations.ExcelModel;
import com.vero.dm.importer.constant.ExcelOperationConstants;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:45 2018/2/6.
 * @since data-mining-platform
 */

@Slf4j
public class SimpleExcelImporter<E> implements ExcelImporter<E>
{
    private Class<E> clazz;

    private List<Field> fields = new ArrayList<>();

    private Map<String, String> columnNameToFieldName = new HashMap<>();

    private Map<String, Field> fieldNameToField = new HashMap<>();

    public SimpleExcelImporter(Class<E> clazz)
    {
        try
        {
            checkClazzAnnotation(clazz);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        this.clazz = clazz;
        fields = ExcelAnnotationUtils.extractAnnotatedFields(clazz, ExcelColumn.class);
        initMap();
    }

    private void checkClazzAnnotation(Class<E> clazz)
        throws NoSuchFieldException
    {
        if (!clazz.isAnnotationPresent(ExcelModel.class))
        {
            throw new NoSuchFieldException(
                "从Excel文件导入数据的类模型必须含有@" + ExcelModel.class.getSimpleName() + "注解");

        }
    }

    private void initMap()
    {
        for (Field field : fields)
        {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn == null || excelColumn.skip())
            {
                continue;
            }
            columnNameToFieldName.put(excelColumn.name(), field.getName());
            fieldNameToField.put(field.getName(), field);
        }
    }

    @Override
    public List<E> importFromExcel(File file)
    {
        Workbook wb = initWorkBook(file);
        List<E> entityList = new ArrayList<E>();
        // 循环工作表
        readExcelSheet(wb, entityList);
        return entityList;
    }

    private Workbook initWorkBook(File file)
    {
        try
        {
            validateFileType(file);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        InputStream is = null;
        try
        {
            is = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Workbook wb = null;
        try
        {
            wb = new XSSFWorkbook(is);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return wb;
    }

    private void readExcelSheet(Workbook wb, List<E> entityList)
    {
        for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++ )
        {
            Sheet sheet = wb.getSheetAt(numSheet);
            log.debug("本文件共有{}行", sheet.getLastRowNum());
            sheet.getLastRowNum();
            Row headRow = sheet.getRow(1);
            String[] heads = validateModuleMappingConstraint(headRow);
            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++ )
            {
                Row row = sheet.getRow(rowIndex);
                entityList.add(readExcelRow(heads, row));
            }
        }
    }

    private void validateFileType(File file)
        throws NoSuchFieldException
    {
        if (!file.getPath().endsWith(ExcelOperationConstants.XLSX_SUFFIX))
        {
            throw new IllegalArgumentException("不支持的excel文件类型");
        }
    }

    private E readExcelRow(String[] heads, Row row)
    {
        E e = this.newInstance();
        for (int cellIndex = 0; cellIndex < row.getPhysicalNumberOfCells(); cellIndex++ )
        {
            readExcelCell(heads, row, e, cellIndex);
        }
        return e;
    }

    private void readExcelCell(String[] head, Row row, E e, int cellIndex)
    {
        Cell cell = row.getCell(cellIndex);
        String fieldName = columnNameToFieldName.get(head[cellIndex]);
        Field field = fieldNameToField.get(fieldName);
        field.setAccessible(true);
        try
        {
            if (cell.getCellTypeEnum() == CellType.NUMERIC)
            {
                if (field.getType().equals(Date.class))
                {
                    field.set(e, cell.getDateCellValue());
                }
                if (field.getType().equals(Long.class))
                {
                    field.set(e, cell.getNumericCellValue());
                }
            }
            else if (cell.getCellTypeEnum() == CellType.STRING)
            {
                if (field.getType().equals(String.class))
                {
                    field.set(e, cell.getStringCellValue());
                }
            }
            else if (cell.getCellTypeEnum() == CellType.BOOLEAN)
            {
                if (field.getType().equals(Boolean.class))
                {
                    field.set(e, cell.getBooleanCellValue());
                }
                field.set(e, cell.getNumericCellValue());
            }
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
    }

    private E newInstance()
    {
        try
        {
            return clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String[] validateModuleMappingConstraint(Row headRow)
    {
        // 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 columnNameToFieldName
        String[] heads = new String[headRow.getPhysicalNumberOfCells()];
        if (headRow.getPhysicalNumberOfCells() != fields.size())
        {
            String message = "标题列跟配置的Excel模型列不一致";
            throw new UnsupportedOperationException(message);
        }
        // 获取标题行的信息
        for (int i = 0; i < headRow.getPhysicalNumberOfCells(); i++ )
        {
            heads[i] = headRow.getCell(i).getStringCellValue();
            // 判断注解映射信息
            if (columnNameToFieldName.get(heads[i]) == null)
            {
                String message = "无法解析被篡改或不符合规则的Excel文件,请确认当前被解析的文件符合模板规则.";
                if (log.isErrorEnabled())
                {
                    log.error(message);
                }
                throw new UnsupportedOperationException(message);
            }
        }
        return heads;
    }
}
