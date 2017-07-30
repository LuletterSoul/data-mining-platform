package com.dm.org.exceptions.error;


        import com.dm.org.utils.ExceptionUtil;
        import org.springframework.http.HttpStatus;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:28 2017/7/30.
 * @since data-mining-platform
 */

public class ErrorInfo
{
    public final String error;

    public final String message;

    public final int status;

    public final String date;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public ErrorInfo(Throwable throwable, String message, HttpStatus status)
    {
        this.error = ExceptionUtil.getRootMessage(throwable);
        this.message = message;
        this.date = dateFormat.format(new Date());
        this.status = status.value();
    }

    @Override
    public String toString()
    {
        return "ErrorInfo [status=" + status + ", error=" + error + ", date=" + date + "]";
    }
}
