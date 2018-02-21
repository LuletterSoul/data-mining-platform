package com.vero.dm.api.controller;

import com.vero.dm.service.impl.BreakPointUploadServiceImpl;
import com.vero.dm.service.upload.BreakPointUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:37 2018/2/20.
 * @since data-mining-platform
 */

@RestController
@RequestMapping(value = "/files")
public class BreakPointUploadController {



    /** Plupload文件上传处理方法 */
    @RequestMapping(value = "/pluploadUpload")
    public void upload(BreakPointUpload breakPointUpload, HttpServletRequest request, HttpServletResponse response) {
        String FileDir = "D://upload";// 文件保存的文件夹
        breakPointUpload.setRequest(request);// 手动传入Plupload对象HttpServletRequest属性
        // 文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"pluploadDir"文件夹，还会以用户唯一id作划分
        File dir = new File(FileDir);
        if (!dir.exists()) {
            dir.mkdirs();// 可创建多级目录，而mkdir()只能创建一级目录
        }
        // 开始上传文件
        BreakPointUploadServiceImpl.upload(breakPointUpload, dir);
    }
}
