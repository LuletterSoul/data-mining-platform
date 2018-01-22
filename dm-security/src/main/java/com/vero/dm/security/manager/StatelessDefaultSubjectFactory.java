package com.vero.dm.security.manager;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 15:04 2017/7/17.
 * @description
 * @modified by:
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory
{

    @Override
    public Subject createSubject(SubjectContext context)
    {
        // 不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
