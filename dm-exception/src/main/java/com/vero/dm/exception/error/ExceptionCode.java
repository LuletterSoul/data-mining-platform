package com.vero.dm.exception.error;


import java.util.HashMap;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:53 2018/2/10.
 * @since data-mining-platform
 */

public enum ExceptionCode {
    CommonError(20000, "服务器响应信息丢失."),
    ErrorCodeLost(20001,"找不到异常提示信息"),
    AuthenticationError(50000, "权限认证出错,你不具备当前模块的访问权限."),
    InvalidCredentials(50001, "证书不合法,请确保你已登录系统."),
    ExpiredToken(50002, "证书已过期,请重新登录."),
    MultipleClientAccessException(50003, "已在其他客户端登录."),
    UnknownAccount(50004, "账号不存在."),
    InvalidAccount(50005, "账号或者密码错误."),
    TokenNotExist(50006, "证书不存在,请先申请正确的证书."),
    ConcurrencyError(50007,"请求太频繁."),
    TokenListNotFound(50008,"找不到符合要求的一次性令牌列表."),
    CandidateTokenSizeError(50009,"服务器找不到足够的一次性令牌匹配认证信息."),
    HighFrequencyAccessError(50010, "访问太频繁,请稍后重试."),
    LogoutTokenNotExist(50011, "无效的登出请求."),
    ContractInvalid(60000,"协商内容不符."),
    HeaderLost(60001, "与服务端协商的请求头丢失,请确定您的请求包含了必要的请求头.");

    /**
     * 业务代码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String tip;

    private static Map<Integer, String> codeToTipMap = new HashMap<>();

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getTip()
    {
        return tip;
    }

    public void setTip(String tip)
    {
        this.tip = tip;
    }

    ExceptionCode(Integer code, String tip)
    {
        this.code = code;
        this.tip = tip;
    }

    public static Map<Integer, String> getCodeToTipMap()
    {
        if (codeToTipMap == null)
        {
            ExceptionCode[] exceptionCodes = values();
            for (ExceptionCode exceptionCode : exceptionCodes)
            {
                codeToTipMap.put(exceptionCode.code, exceptionCode.tip);
            }
        }
        return codeToTipMap;
    }

    public static String getTip(Integer code)
    {
        Map<Integer, String> map = getCodeToTipMap();
        return map.get(code);
    }
}
