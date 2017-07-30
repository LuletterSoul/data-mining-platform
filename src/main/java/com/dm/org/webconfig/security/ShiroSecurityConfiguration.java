package com.dm.org.webconfig.security;


import com.dm.org.security.UserPasswordServiceImpl;
import com.dm.org.security.credentials.RetryLimitHashedCredentialsMatcher;
import com.dm.org.security.realm.UserRealm;
import com.dm.org.webconfig.cache.EhCacheConfiguration;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 15:02 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@Import(EhCacheConfiguration.class)
public class ShiroSecurityConfiguration {

    @Resource
    private CacheManager ehCacheCacheManager;

    @Bean
    public EhCacheManager shiroCacheManager() {
        EhCacheManager shiroCacheManager = new EhCacheManager();
        shiroCacheManager.setCacheManager(ehCacheCacheManager);
        return shiroCacheManager;
    }

    @Bean
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    // @Bean
    // public StatelessRealm statelessRealm()
    // {
    // StatelessRealm statelessRealm = new StatelessRealm();
    // statelessRealm.setCachingEnabled(false);
    // statelessRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
    // return statelessRealm;
    // }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(true);
        userRealm.setCacheManager(shiroCacheManager());
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        userRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        return userRealm;
    }

    @Bean
    public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setCacheManager(shiroCacheManager());
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator());
        return sessionDAO;
    }

    // @Bean
    // public SessionValidationScheduler sessionValidationScheduler()
    // {
    // QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
    // scheduler.setSessionValidationInterval(1800000);
    // return scheduler;
    // }

    //
    // @Bean
    // public StatelessDefaultSubjectFactory statelessDefaultSubjectFactory()
    // {
    // return new StatelessDefaultSubjectFactory();
    // }
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        defaultSessionManager.setGlobalSessionTimeout(1800000);
        defaultSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultSessionManager.setSessionIdCookieEnabled(true);
        defaultSessionManager.setSessionIdCookie(sessionCookie());
        // defaultSessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        defaultSessionManager.setSessionDAO(enterpriseCacheSessionDAO());
        return defaultSessionManager;
    }

    public SimpleCookie sessionCookie() {
        return new SimpleCookie("hotusm.session.id");
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setCacheManager(shiroCacheManager());
        // securityManager.setSubjectFactory(statelessDefaultSubjectFactory());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean bindingSecurityManager() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod(
                "org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[]{securityManager()});
        return methodInvokingFactoryBean;
    }

    // @Bean(name = "stateless")
    // public StatelessAuthenticatingFilter statelessAuthenticatingFilter()
    // {
    // return new StatelessAuthenticatingFilter();
    // }
    @Bean
    public UserPasswordServiceImpl passwordService() {
        UserPasswordServiceImpl passwordService = new UserPasswordServiceImpl();
        passwordService.setHashService(hashService());
        passwordService.setHashFormat(new HexFormat());
        return passwordService;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter()
     {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/static/login/login.html");
        loadShiroFilterChain(shiroFilter);
        return shiroFilter;
    }

    @Bean
    public LogoutFilter logout()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setPostOnlyLogout(true);
        logoutFilter.setRedirectUrl("/static/index.html");
        return logoutFilter;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean)
     {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/static/manager/**", "authc");
        filterChainDefinitionMap.put("/login/*", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user/*", "rest[user]");
        filterChainDefinitionMap.put("/static/login", "anon");
//        filterChainDefinitionMap.put("/static/manager/data_set.html", "rest[admin]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean
    public DefaultHashService hashService() {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setGeneratePublicSalt(false);
        hashService.setHashAlgorithmName("SHA-256");
        hashService.setHashIterations(50000);
        return hashService;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}