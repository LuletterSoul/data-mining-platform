package com.dm.org.webconfig;


import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.ext.spring.ApplicationContextHolder;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:13 2017/7/18.
 * @since data-mining-platform
 */

@Configuration
@SuppressWarnings("unchecked")
public class LogbackConfiguration
{
    private static final String ENCODER_PATTERN = "%d{yyyy-MM-dd  HH:mm:ss.SSS} [%thread] %-5level %logger{80} - %msg%n";

    private static final String APP_NAME = "Data Mining Platform";

    private static final String LOG_HOME = "${log.dir:-logs}/"+APP_NAME;

    private static final String FILE_OUT_PATTERN = LOG_HOME+"/output.%d{yyyy-MM-dd}.log";

    private static final String ERROR_FILE_OUT_PATTERN = LOG_HOME+"/error.%d{yyyy-MM-dd}.log";

    private static final String SYNC_FILE_OUT_PATTERN = LOG_HOME+"/sync.%d{yyyy-MM-dd}.log";

    @Bean
    public static ApplicationContextHolder applicationContextHolder()
    {
        return new ApplicationContextHolder();
    }

    @Bean
    public static LoggerContext loggerContext()
    {
        return (LoggerContext)LoggerFactory.getILoggerFactory();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public static PatternLayoutEncoder encoder()
    {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext());
        // encoder.setPattern("%date %-5level [%thread] %logger{36} %m%n");
        encoder.setPattern(ENCODER_PATTERN);
        return encoder;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public static ConsoleAppender consoleAppender()
    {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setContext(loggerContext());
        consoleAppender.setEncoder(encoder());
        return consoleAppender;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public static RollingFileAppender loggerFileAppender()
    {
        return buildRollingFileAppender(FILE_OUT_PATTERN, 7, false);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public static RollingFileAppender errorLoggerFileAppender()
    {
        return buildRollingFileAppender(ERROR_FILE_OUT_PATTERN, 14, true);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public static RollingFileAppender syncLoggerAppender()
    {
        return buildRollingFileAppender(SYNC_FILE_OUT_PATTERN, 7, true);
    }

    private static RollingFileAppender buildRollingFileAppender(String fileNamePattern,
                                                                int maxHistory,
                                                                boolean isAddThresholdFilter)
    {
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
        rollingFileAppender.setContext(loggerContext());
        timeBasedRollingPolicy.setFileNamePattern(fileNamePattern);
        timeBasedRollingPolicy.setMaxHistory(maxHistory);
        timeBasedRollingPolicy.setContext(loggerContext());
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.setEncoder(encoder());
        if (isAddThresholdFilter)
        {
            ThresholdFilter thresholdFilter = new ThresholdFilter();
            thresholdFilter.setLevel("WARN");
            thresholdFilter.setContext(loggerContext());
            rollingFileAppender.addFilter(thresholdFilter);
        }
        return rollingFileAppender;
    }
}
