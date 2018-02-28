package com.vero.dm.security.config;


import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.vero.dm.security.filter.AuthenticationExceptionFilter;
import com.vero.dm.security.filter.PreLogoutFilter;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.security.credentials.DefaultStatelessCredentialsComputer;
import com.vero.dm.security.credentials.StatelessCredentialsMatcher;
import com.vero.dm.security.filter.AllowOriginFilter;
import com.vero.dm.security.filter.StatelessAuthenticatingFilter;
import com.vero.dm.security.manager.StatelessDefaultSubjectFactory;
import com.vero.dm.security.realm.StatelessRealm;
import com.vero.dm.security.strategy.*;
import com.vero.dm.util.DateStyle;

import net.sf.ehcache.CacheManager;


/**
 * 该类定义了shiro 安全体系的Bean组件
 * 自定义的Filter必须放在{@link ShiroFilterFactoryBean}之前注册在spring容器中,
 * 然后被它内部的逻辑启动扫描对应的filter,注册在自身维护的filters中
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 15:02 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@Profile("dev")
@Import(value = EhCacheConfiguration.class)
public class ShiroSecurityDevConfiguration
{

    private Integer hashIterations = 1000;

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
    public StatelessRealm statelessRealm()
    {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setAuthenticationCachingEnabled(true);
        statelessRealm.setAuthorizationCachingEnabled(true);
        statelessRealm.setCachingEnabled(true);
        statelessRealm.setAuthenticationCacheName("authenticationCache");
        statelessRealm.setAuthorizationCacheName("authorizationCache");
        statelessRealm.setCredentialsMatcher(statelessCredentialsMatcher());
        return statelessRealm;
    }


    @Bean
    public StrategyMatchChain strategyMatchChain()
    {
        ApplicationStrategyMatchChain matchChain = new ApplicationStrategyMatchChain();
        matchChain.addStrategy(
            new DefaultMatchStrategyConfig(new SimpleMatchStrategy(computer()), "simple"));
        matchChain.addStrategy(new DefaultMatchStrategyConfig(
            new LimitedMatchAttemptsStrategy(computer()), "limited"));
        matchChain.addStrategy(
            new DefaultMatchStrategyConfig(new MatchAllCandidateStrategy(computer()), "all"));
        return matchChain;
    }

    @Bean
    public StatelessCredentialsMatcher statelessCredentialsMatcher()
    {
        StatelessCredentialsMatcher matcher = new StatelessCredentialsMatcher();
        matcher.setHashIterations(hashIterations);
        matcher.setHashAlgorithmName(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
        matcher.setStatelessCredentialsComputer(computer());
        return matcher;
    }

    /**
     * Define stateless credentials bean. {@link DefaultPasswordService#DefaultPasswordService()}
     * will set {@link DefaultHashService#setGeneratePublicSalt(boolean)} } true,and
     * {@link DefaultStatelessCredentialsComputer} extends {@link DefaultHashService},we have to
     * get corresponding embed hash service,reset it's boolean generate public salt configuration.
     * Developer will generate salt by himself via
     * {@link DefaultStatelessCredentialsComputer#generateRandomSalt(int)}
     */
    @Bean
    public DefaultStatelessCredentialsComputer computer()
    {
        DefaultStatelessCredentialsComputer service = new DefaultStatelessCredentialsComputer();
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
    public ObjectMapper objectMapper()
    {
        Jackson2ObjectMapperFactoryBean mapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        mapperFactoryBean.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapperFactoryBean.setDateFormat(
            new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean.getObject();
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

    @Bean
    public AuthenticationExceptionFilter exceFilter() {
        return new AuthenticationExceptionFilter();
    }

    @Bean
    public AllowOriginFilter allowOriginFilter()
    {
        return new AllowOriginFilter();
    }

    @Bean(name = "noSess")
    public NoSessionCreationFilter noSessionCreationFilter()
    {
        return new NoSessionCreationFilter();
    }



    @Bean(name = "preLogout")
    public PreLogoutFilter preLogout()
    {
        return new PreLogoutFilter();
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

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean)
    {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/token/**", "anon");
        filterChainDefinitionMap.put("/public_salt/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/api/**",
            "noSess,allowOriginFilter,preLogout");
        filterChainDefinitionMap.put("/**", "noSess,allowOriginFilter");
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
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor()
    {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean()
    {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }
}
