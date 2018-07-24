package com.vero.dm.exception.error;


import java.util.HashMap;
import java.util.Map;

import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.group.*;
import org.apache.http.auth.InvalidCredentialsException;

import com.vero.dm.exception.auth.*;
import com.vero.dm.exception.business.BusinessException;
import com.vero.dm.exception.business.StudentIdDuplicatedException;
import com.vero.dm.exception.common.CommonException;
import com.vero.dm.exception.common.ConstratInvalidException;
import com.vero.dm.exception.common.DataDuplicatedException;
import com.vero.dm.exception.common.ErrorCodeLostNotFoundException;
import com.vero.dm.exception.constract.HeaderLostException;
import com.vero.dm.exception.file.*;
import org.hibernate.LazyInitializationException;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:53 2018/2/10.
 * @since data-mining-platform
 */

public enum ExceptionCode {
    PlatformError(10000, "服务异常,请联系管理员", PlatformBaseException.class),
    CommonError(20000, "服务器响应信息丢失.", CommonException.class),
    ErrorCodeLost(20001,"找不到异常提示信息.", ErrorCodeLostNotFoundException.class),
    FileOperationError(30000, "文件操作出错.", FileOperationException.class),
    ExcelModuleInvalid(30001, "Excel模板不符合导入规则.", ExcelModuleInValidException.class),
    UnsupportedFileType(30002, "不支持的文件类型", UnsupportedFileTypeException.class),
    ExcelAnnotationNotFound(30002, "找不到特定的Excel注解", ExcelModuleAnnotationNotFoundException.class),
    DataSetDeleteError(30003, "无法删除该数据集", DataSetDeleteException.class),
    ZipSetError(30004, "压缩数据集时出错",SetZipException.class),
    BusinessLogicError(40000,"业务逻辑异常.", BusinessException.class),
    StudentIdDuplicated(40001,"学号重复.", StudentIdDuplicatedException.class),
    DataDuplicated(40002,"数据重复", DataDuplicatedException.class),
    StudentNotFound(40003,"找不到对应的学生", StudentNotFoundException.class),
    SpecifStudentNotFound(40004,"无法找到指定的学生信息", SpecificStudentNotFoundException.class),
    PreviewGroupsNotFound(40005, "找不到对应的预览分组列表.", PreviewGroupsNotFoundException.class),
    TasksEmpty(40006, "待分配的任务不能为空", TasksEmptyException.class),
    InvalidGroupingConfig(40007, "当前参数产生的分组无法接收所有待分配的任务", InvalidGroupingConfigException.class),
    AuthenticationError(50000, "权限认证出错,你不具备当前模块的访问权限.", InternalAuthenticationException.class),
    InvalidCredentials(50001, "证书不合法,请确保你已登录系统.", InvalidCredentialsException.class),
    ExpiredToken(50002, "证书已过期,请重新登录.", ExpiredCredentialsException.class),
    MultipleClientAccessException(50003, "已在其他客户端登录.", MultipleEndpointsAccessExceptionInternal.class),
    UnknownAccount(50004, "账号不存在.", UnknownAccountException.class),
    InvalidAccount(50005, "账号或者密码错误.", IncorrectCredentialsException.class),
    TokenNotExist(50006, "证书不存在,请先申请正确的证书.",AccessTokenNotExistException.class),
    ConcurrencyError(50007,"请求太频繁.",ConcurrentAccessException.class),
    TokenListNotFound(50008,"找不到符合要求的一次性令牌列表.",EmptyTokenListException.class),
    CandidateTokenSizeError(50009,"服务器找不到足够的一次性令牌匹配认证信息.",CandidateTokenSizeException.class),
    HighFrequencyAccessError(50010, "访问太频繁,请稍后重试.",HighFrequencyAccessException.class),
    LogoutTokenNotExist(50011, "无效的登出请求.",LogoutAccessException.class),
    ContractInvalid(60000,"协商内容不符.", ConstratInvalidException.class),
    HeaderLost(60001, "与服务端协商的请求头丢失,请确定您的请求包含了必要的请求头.", HeaderLostException.class),
    LazyInitializationError(60002,"服务器数据懒加载错误",LazyInitializationException.class);

    /**
     * 业务代码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String tip;

    private Class<?> clazz;

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

    ExceptionCode(Integer code, String tip,Class<?> clazz)
    {
        this.code = code;
        this.tip = tip;
        this.clazz = clazz;
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
