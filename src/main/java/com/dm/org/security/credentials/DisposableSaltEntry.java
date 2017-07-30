package com.dm.org.security.credentials;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:54 2017/7/25.
 * @since data-minning-platform
 */

public class DisposableSaltEntry
{
    private String tmpId;

    private String disposableSalt;

    public DisposableSaltEntry()
    {

    }

    public DisposableSaltEntry(String tmpId, String disposableSalt)
    {
        this.tmpId = tmpId;
        this.disposableSalt = disposableSalt;
    }

    public String getTmpId()
    {
        return tmpId;
    }

    public void setTmpId(String tmpId)
    {
        this.tmpId = tmpId;
    }

    public String getDisposableSalt()
    {
        return disposableSalt;
    }

    public void setDisposableSalt(String disposableSalt)
    {
        this.disposableSalt = disposableSalt;
    }
}
