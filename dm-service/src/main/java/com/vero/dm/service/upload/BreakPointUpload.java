package com.vero.dm.service.upload;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:38 2018/2/20.
 * @since data-mining-platform
 */

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Data
public class BreakPointUpload {
    /** 文件原名 */
    private String name;
    /** 用户上传资料被分解总块数 */
    private int chunks = -1;
    /** 当前块数（从0开始计数） */
    private int chunk = -1;
    /** HttpServletRequest对象，不会自动赋值，需要手动传入 */
    private HttpServletRequest request;
    /** 保存文件上传信息，不会自动赋值，需要手动传入 */
    private MultipartFile multipartFile;
}

