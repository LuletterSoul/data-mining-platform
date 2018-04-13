package com.vero.dm.service.impl;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:39 2018/2/20.
 * @since data-mining-platform
 */

import com.vero.dm.service.upload.BreakPointUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * BreakPointUpload Service模块，同Plupload实体类一样，因为要用到Spring web相关依赖，所以不将其放在Service模块
 */
@Service
@Slf4j
public class BreakPointUploadServiceImpl {

    public static void upload(BreakPointUpload breakPointUpload, File uploadDir) {
        String fileName = "" + System.currentTimeMillis() + breakPointUpload.getName();// 在服务器内生成唯一文件名
        upload(breakPointUpload, uploadDir, fileName);
    }

    private static void upload(BreakPointUpload breakPointUpload, File uploadDir, String fileName) {
        int chunks = breakPointUpload.getChunks();// 用户上传文件被分隔的总块数
        int nowChunk = breakPointUpload.getChunk();// 当前块，从0开始

        // 这里Request请求类型的强制转换可能出错，配置文件中向SpringIOC容器引入multipartResolver对象即可。
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) breakPointUpload.getRequest();
        // 调试发现map中只有一个键值对
        MultiValueMap<String, MultipartFile> map = multipartHttpServletRequest.getMultiFileMap();
        if (map != null) {
            try {
                Iterator<String> iterator = map.keySet().iterator();
                String key = null;
                List<MultipartFile> multipartFileList = null;
                File targetFile = null;
                File tempFile = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    multipartFileList = (List<MultipartFile>) map.get(key);
                    for (MultipartFile multipartFile : multipartFileList) {// 循环只进行一次
                        breakPointUpload.setMultipartFile(multipartFile);// 手动向Plupload对象传入MultipartFile属性值
                        targetFile = new File(uploadDir, fileName);// 新建目标文件，只有被流写入时才会真正存在
                        if (chunks > 1) {// 用户上传资料总块数大于1，要进行合并
                            tempFile = new File(uploadDir.getPath(), multipartFile.getName());
                            // 第一块直接从头写入，不用从末端写入
                            saveUploadFile(multipartFile.getInputStream(), tempFile, nowChunk != 0);

                            if (chunks - nowChunk == 1) {// 全部块已经上传完毕，此时targetFile因为有被流写入而存在，要改文件名字
                                tempFile.renameTo(targetFile);
                            }
                        } else {
                            // 只有一块，就直接拷贝文件内容
                            multipartFile.transferTo(targetFile);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 描述 : 写入文件到磁盘. <br>
     *
     * @author yjw@jusfoun.com
     * @date 2017年10月18日 上午9:32:15
     * @param inputStream
     *            数据流
     * @param tempFile
     *            临时文件
     * @param flag
     *            是否是追加，是的话需要在原来的基础上添加，否则直接写入
     */
    private static void saveUploadFile(InputStream inputStream, File tempFile, boolean flag) {
        OutputStream outputStream = null;
        try {
            if (!flag) {
                // 从头写入
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            } else {
                // 从末端写入,追加
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile, true));
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = (inputStream.read(bytes))) > 0) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
