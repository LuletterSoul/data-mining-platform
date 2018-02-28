package com.vero.dm.importer.core;


import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.file.ExcelModuleAnnotationNotFoundException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import com.vero.dm.importer.POIUtils;
import com.vero.dm.importer.annotations.ExcelAnnotationUtils;
import com.vero.dm.importer.annotations.ExcelColumn;
import com.vero.dm.importer.annotations.ExcelModel;
import com.vero.dm.util.PathUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:02 2018/2/3.
 * @since data-mining-platform
 */
@Slf4j
@Getter
public class SimpleExcelModuleGenerator implements ExcelModuleGenerator
{
    private String absoluteModuleDir = "";

    @Override
    public String generate(Class<?> clazz)
    {
        if (!clazz.isAnnotationPresent(ExcelModel.class))
        {
            String message = "Unsupported Annotation for Excel Module Scanning .";
            throw new ExcelModuleAnnotationNotFoundException(message, ExceptionCode.ExcelAnnotationNotFound);
        }
        ExcelModel excelModel = clazz.getAnnotation(ExcelModel.class);
        // 获取注解信息
        List<Field> annotatedFields = ExcelAnnotationUtils.extractAnnotatedFields(clazz,
            ExcelColumn.class);
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet();
        sheet.setAutobreaks(true);
        // 创建标题样式
        createExcelTitle(clazz, excelModel, annotatedFields, wb, sheet);
        int colSzie = 0;
        // 表头样式
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        createFont(wb, headStyle, (short)240);
        Row headRow = sheet.createRow(1);
        // 根据注解信息生成表头
        createTableHead(excelModel, annotatedFields, sheet, headStyle, headRow);
        String filePath = handleExcelGeneration(clazz, wb);
        log.debug("生成[{}]的Excel导入模板,文件路径:{}", clazz.getSimpleName(), filePath);
        return filePath;
    }

    private String handleExcelGeneration(Class<?> clazz, SXSSFWorkbook wb)
    {
        String filePath = modulePath(clazz);
        try
        {
            wb.write(new FileOutputStream(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return filePath;
    }

    private String modulePath(Class<?> clazz)
    {
        String fileName = String.format("%s%s", clazz.getSimpleName().toLowerCase(),
            POIUtils.XLSX_SUFFIX);
        String absolutePath = PathUtils.getAbsolutePath(ExcelModuleGenerator.outputFilePath);
        return PathUtils.concat(absolutePath, fileName);
    }

    private void createTableHead(ExcelModel excelModel, List<Field> annotatedFields,
                                 SXSSFSheet sheet, CellStyle headStyle, Row headRow)
    {
        if (excelModel.mapByProp())
        {
            createByProp(excelModel, sheet, headStyle, headRow, annotatedFields);
        }
        else
        {
            createCellByExcelColumnAnnotation(excelModel, sheet, headStyle, headRow,
                annotatedFields);
        }
    }

    private void createExcelTitle(Class<?> clazz, ExcelModel excelModel,
                                  List<Field> annotatedFields, SXSSFWorkbook wb, SXSSFSheet sheet)
    {
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        createFont(wb, titleStyle, (short)400);
        doCreateExcelTitle(clazz, excelModel, sheet, titleStyle, annotatedFields.size());
    }

    private void createFont(SXSSFWorkbook wb, CellStyle titleStyle, short height)
    {
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeight(height);
        titleStyle.setFont(font);
    }

    private void createByProp(ExcelModel excelModel, SXSSFSheet sheet, CellStyle headStyle,
                              Row headRow, List<Field> allFields)
    {
        for (int i = 0; i < allFields.size(); i++ )
        {
            Field field = allFields.get(i);
            Cell cell = headRow.createCell(i);
            if (field.getAnnotation(ExcelColumn.class).skip())
            {
                continue;
            }
            POIUtils.setColumnWidth(sheet, i, excelModel.colWidth(), field.getName());
            cell.setCellStyle(headStyle);
            cell.setCellValue(field.getName());
        }
    }

    private void createCellByExcelColumnAnnotation(ExcelModel excelModel, SXSSFSheet sheet,
                                                   CellStyle headStyle, Row headRow,
                                                   List<Field> annotatedFields)
    {
        for (int i = 0; i < annotatedFields.size(); i++ )
        {
            Field field = annotatedFields.get(i);
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            Integer colIndex = excelColumn.colIndex();
            Cell cell;
            if (excelColumn.colIndex() != -1)
            {
                cell = headRow.createCell(excelColumn.colIndex());
            }
            else
            {
                cell = headRow.createCell(i);
                colIndex = i;
            }
            if (!excelColumn.name().equals(""))
            {
                cell.setCellValue(excelColumn.name());
            }
            else
            {
                cell.setCellValue(field.getName());
            }
            cell.setCellStyle(headStyle);
            if (excelColumn.autoSizeColumn())
            {
                POIUtils.setColumnWidth(sheet, colIndex, excelModel.colWidth(), field.getName());
            }
        }
    }

    private void doCreateExcelTitle(Class<?> clazz, ExcelModel excelModel, SXSSFSheet sheet,
                                    CellStyle titleStyle, int cellSize)
    {

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellSize - 1);
        // 合并单元格
        sheet.addMergedRegion(cellRangeAddress);
        // 创建第一行，并在该行创建单元格，设置内容，做为标题行
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        String title = excelModel.title();
        if (title.equals(""))
        {
            title = clazz.getSimpleName();
        }
        titleCell.setCellValue(new XSSFRichTextString(title));
        titleCell.setCellStyle(titleStyle);
    }

    /**
     * 添加数据有效性检查.
     *
     * @param sheet
     *            要添加此检查的Sheet
     * @param firstRow
     *            开始行
     * @param lastRow
     *            结束行
     * @param firstCol
     *            开始列
     * @param lastCol
     *            结束列
     * @param explicitListValues
     *            有效性检查的下拉列表
     * @throws IllegalArgumentException
     *             如果传入的行或者列小于0(< 0)或者结束行/列比开始行/列小
     */
    public void setValidationData(Sheet sheet, int firstRow, int lastRow, int firstCol,
                                  int lastCol, String[] explicitListValues)
        throws IllegalArgumentException
    {
        if (firstRow < 0 || lastRow < 0 || firstCol < 0 || lastCol < 0 || lastRow < firstRow
            || lastCol < firstCol)
        {
            throw new IllegalArgumentException("Wrong Row or Column index : " + firstRow + ":"
                                               + lastRow + ":" + firstCol + ":" + lastCol);
        }
        if (sheet instanceof XSSFSheet)
        {
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(
                explicitListValues);
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow,
                firstCol, lastCol);
            XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(
                dvConstraint, addressList);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        }
        else if (sheet instanceof HSSFSheet)
        {
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow,
                firstCol, lastCol);
            DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(
                explicitListValues);
            DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            sheet.addValidationData(validation);
        }
    }

}