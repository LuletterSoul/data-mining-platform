package com.vero.dm.security.credentials;


import javax.servlet.http.HttpServletResponse;


/**
 * 客户端与服务器协商规则的写入器,规则如下:对于每一条客户端的
 * {@link javax.servlet.http.HttpServletRequest}
 * 对应{@link HttpServletResponse}的{@link org.apache.http.HttpHeaders}
 * 需要写入服务端签发的一次性令牌{@link com.vero.dm.security.constants.Constants}
 * 作为客户端下一次计算消息摘要的依赖,当客户端再次发起请求时,需要携带对应的消息摘要
 * 给服务端
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:50 2018/2/12.
 * @since data-mining-platform
 */

public interface DisposableTokenWriter
{

    /**
     * @param httpServletResponse 返回给客户端的响应
     * @param accessToken 访问令牌
     */
    void write(HttpServletResponse httpServletResponse, String accessToken);
}
