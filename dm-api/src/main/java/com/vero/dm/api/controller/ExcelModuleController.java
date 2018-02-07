package com.vero.dm.api.controller;


import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.service.StudentService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:02 2018/2/7.
 * @since data-mining-platform
 */

@RestController
@RequestMapping(value = ResourcePath.MODULE_PATH)
public class ExcelModuleController
{
    @Autowired
    private StudentService studentService;

    @ApiOperation("下载学生信息的导入模板")
    @GetMapping(value = "/student", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadStudentExcelModule(HttpServletResponse response)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=student.xlsx");
        headers.setContentType(APPLICATION_OCTET_STREAM);
        byte[] fileBytes = studentService.handleStudentExcelModuleDownload();
        headers.setContentLength(fileBytes.length);
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
}
