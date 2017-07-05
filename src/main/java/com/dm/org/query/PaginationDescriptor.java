package com.dm.org.query;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  16:52 2017/7/5.
 * @description
 * @modified by:
 */
public class PaginationDescriptor
{
    private Integer beginPage;

    private Integer endPage;

    public PaginationDescriptor(Integer beginPage, Integer endPage)
    {
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    public Integer getBeginPage()
    {
        return beginPage;
    }

    public void setBeginPage(Integer beginPage)
    {
        this.beginPage = beginPage;
    }

    public Integer getEndPage()
    {
        return endPage;
    }

    public void setEndPage(Integer endPage)
    {
        this.endPage = endPage;
    }
}
