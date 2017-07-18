package com.dm.org.webconfig.security;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dm.org.security.filter.StatelessAuthenticatingFilter;
import com.dm.org.security.manager.StatelessDefaultSubjectFactory;
import com.dm.org.security.realm.StatelessRealm;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 15:02 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
public class ShiroSecurityConfiguration
{

    @Bean
    public StatelessRealm statelessRealm()
    {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setCachingEnabled(false);
        return statelessRealm;
    }

    @Bean
    public StatelessDefaultSubjectFactory statelessDefaultSubjectFactory()
    {
        return new StatelessDefaultSubjectFactory();
    }

    @Bean
    public DefaultSessionManager sessionManager()
    {
        DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
        defaultSessionManager.setSessionValidationSchedulerEnabled(false);
        return defaultSessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager()
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(statelessRealm());
        securityManager.setSubjectFactory(statelessDefaultSubjectFactory());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean bindingSecurityManager()
    {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod(
            "org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[] {securityManager()});
        return methodInvokingFactoryBean;
    }

    @Bean(name = "stateless")
    public StatelessAuthenticatingFilter statelessAuthenticatingFilter()
    {
        return new StatelessAuthenticatingFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter()
    {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/static/login.html");
        loadShiroFilterChain(shiroFilter);
        return shiroFilter;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean)
    {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // filterChainDefinitionMap.put("/securitry/**", "anon");
        // filterChainDefinitionMap.put("/api/**", "anon");
        // filterChainDefinitionMap.put("/favicon.ico", "anon");
        // filterChainDefinitionMap.put("/api-docs/**", "anon");
        // filterChainDefinitionMap.put("/lib/**", "anon");
        // filterChainDefinitionMap.put("/**", "authc");
        // filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "anon");
        /*
         * filterChainDefinitionMap.put("/mydemo/login", "anon");
         * filterChainDefinitionMap.put("/mydemo/getVerifyCodeImage", "anon");
         * filterChainDefinitionMap.put("/main**", "authc");
         * filterChainDefinitionMap.put("/user/info**", "authc");
         * filterChainDefinitionMap.put("/admin/listUser**", "authc,perms[admin:manage]");
         */
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
}