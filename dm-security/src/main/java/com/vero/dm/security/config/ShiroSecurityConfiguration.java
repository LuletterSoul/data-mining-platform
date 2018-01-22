package com.vero.dm.security.config;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.vero.dm.security.UserPasswordServiceImpl;
import com.vero.dm.security.credentials.StatelessCredentialsMatcher;
import com.vero.dm.security.credentials.StatelessCredentialsServiceImpl;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.security.filter.StatelessAuthenticatingFilter;
import com.vero.dm.security.manager.StatelessDefaultSubjectFactory;
import com.vero.dm.security.realm.StatelessRealm;

import net.sf.ehcache.CacheManager;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 15:02 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@Import(value = EhCacheConfiguration.class)
public class ShiroSecurityConfiguration
{

    @Resource
    private CacheManager ehCacheCacheManager;

    @Bean
    public EhCacheManager shiroCacheManager()
    {
        EhCacheManager shiroCacheManager = new EhCacheManager();
        shiroCacheManager.setCacheManager(ehCacheCacheManager);
        return shiroCacheManager;
    }

    @Bean
    public TokenManager tokenManager()
    {
        return new TokenManager();
    }

    // @Bean
    // public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher()
    // {
    // RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher();
    // matcher.setHashAlgorithmName("md5");
    // matcher.setHashIterations(2);
    // matcher.setStoredCredentialsHexEncoded(true);
    // return matcher;
    // }

    @Bean
    public StatelessRealm statelessRealm()
    {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setAuthenticationCachingEnabled(false);
        statelessRealm.setAuthorizationCachingEnabled(true);
        statelessRealm.setAuthorizationCacheName("authorizationCache");
        statelessRealm.setCachingEnabled(false);
        statelessRealm.setCredentialsMatcher(statelessCredentialsMatcher());
        return statelessRealm;
    }

    // @Bean
    // public UserRealm userRealm()
    // {
    // UserRealm userRealm = new UserRealm();
    // userRealm.setCachingEnabled(true);
    // userRealm.setCacheManager(shiroCacheManager());
    // userRealm.setAuthenticationCachingEnabled(true);
    // userRealm.setAuthenticationCacheName("authenticationCache");
    // userRealm.setAuthorizationCachingEnabled(true);
    // userRealm.setAuthorizationCacheName("authorizationCache");
    // userRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
    // return userRealm;
    // }

    // @Bean
    // public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator()
    // {
    // return new JavaUuidSessionIdGenerator();
    // }

    // @Bean
    // public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO()
    // {
    // EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
    // sessionDAO.setCacheManager(shiroCacheManager());
    // sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
    // sessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator());
    // return sessionDAO;
    // }

    @Bean
    public StatelessCredentialsMatcher statelessCredentialsMatcher()
    {
        StatelessCredentialsMatcher matcher = new StatelessCredentialsMatcher();
        matcher.setHashIterations(1000);
        matcher.setHashAlgorithmName(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
        matcher.setStatelessCredentialsService(statelessCredentialsService());
        return matcher;
    }

    /**
     * Define stateless credentials bean. {@link DefaultPasswordService#DefaultPasswordService()}
     * will set {@link DefaultHashService#setGeneratePublicSalt(boolean)} } true,and
     * {@link StatelessCredentialsServiceImpl} extends {@link DefaultHashService},we have to get
     * corresponding embed hash service,reset it's boolean generate public salt configuration.
     * Developer will generate salt by himself via
     * {@link StatelessCredentialsServiceImpl#generateRandomSalt(int)}
     * 
     * @since
     */
    @Bean
    public StatelessCredentialsServiceImpl statelessCredentialsService()
    {
        StatelessCredentialsServiceImpl service = new StatelessCredentialsServiceImpl();
        DefaultPasswordService defaultPasswordService = (DefaultPasswordService)service;
        HashService hashService = defaultPasswordService.getHashService();
        ((DefaultHashService)hashService).setGeneratePublicSalt(false);
        return service;
    }

    // @Bean
    // public SessionValidationScheduler sessionValidationScheduler()
    // {
    // QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
    // scheduler.setSessionValidationInterval(1800000);
    // return scheduler;
    // }

    @Bean
    public StatelessDefaultSubjectFactory statelessDefaultSubjectFactory()
    {
        return new StatelessDefaultSubjectFactory();
    }

    @Bean
    public DefaultWebSessionManager sessionManager()
    {
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        // defaultSessionManager.setGlobalSessionTimeout(1800000);
        // defaultSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultSessionManager.setSessionValidationSchedulerEnabled(false);
        // defaultSessionManager.setSessionIdCookieEnabled(false);
        // defaultSessionManager.setSessionIdCookie(sessionCookie());
        // // defaultSessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        // defaultSessionManager.setSessionDAO(enterpriseCacheSessionDAO());
        return defaultSessionManager;
    }

    /**
     * Shiro 权限管理框架核心处理器
     * 
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager()
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // securityManager.setRealm(userRealm());
        securityManager.setRealm(statelessRealm());
        // securityManager.setCacheManager(shiroCacheManager());
        securityManager.setSubjectFactory(statelessDefaultSubjectFactory());
        securityManager.setSessionManager(sessionManager());
        securityManager.setSubjectDAO(subjectDAO());
        return securityManager;
    }

    /**
     * {@link ModularRealmAuthenticator}是Shiro 官方默认提供的鉴权器 但默认的初始化后，其不具有授权成功、失败、登出等操作的监听器
     * {@link AuthenticationListener} 这里实现了监听器简单的注册逻辑
     * 
     * @return 重新配置的统一鉴权器
     */
    // @Bean
    // public ModularRealmAuthenticator authenticator() {
    // ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
    //// List<AuthenticationListener> listeners = new LinkedList<AuthenticationListener>();
    //// listeners.add(tokenCleanListener());
    //
    // }

    @Bean
    public DefaultSubjectDAO subjectDAO()
    {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(statelessEvaluator());
        return defaultSubjectDAO;
    }

    /**
     * In purely stateless applications, such as some REST applications or those where every
     * request is authenticated, it is usually not needed or desirable to use Sessions to store
     * this state (since it is in fact re-created on every request). In these applications,
     * sessions would never be used.
     */
    @Bean
    public DefaultSessionStorageEvaluator statelessEvaluator()
    {
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        return evaluator;
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

    @Bean(name = "statelessFilter")
    public StatelessAuthenticatingFilter statelessAuthenticatingFilter()
    {
        return new StatelessAuthenticatingFilter();
    }

    // @Bean("originFilter")
    // public AllowOriginFilter allowOriginFilter(){
    // return new AllowOriginFilter();
    // }

    @Bean
    public UserPasswordServiceImpl passwordService()
    {
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
        shiroFilter.setLoginUrl("/login");
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
        filterChainDefinitionMap.put("/user/{username}/token", "anon");
        filterChainDefinitionMap.put("/*", "statelessFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean
    public DefaultHashService hashService()
    {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setGeneratePublicSalt(false);
        hashService.setHashAlgorithmName("SHA-256");
        hashService.setHashIterations(1000);
        return hashService;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }
}
