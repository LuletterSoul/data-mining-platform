package com.vero.dm.security.credentials;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.vero.dm.util.date.DateStyle;
import com.vero.dm.util.date.DateUtil;

import lombok.Getter;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:20 2018/2/10.
 * @since data-mining-platform
 */
@Profile(value = {"prod","dev","test"})
@Component
@Getter
public class SimpleTokenGenerator implements TokenGenerator
{
    public final Integer DEFAULT_HASH_ITERATIONS = 50000;

    public final Integer SHUFFLE_ITERATIONS = 100;

    private Integer hashIterations;

    public void setHashIterations(Integer hashIterations)
    {
        this.hashIterations = hashIterations;
    }

    @Autowired
    private StatelessCredentialsServer credentialsComputer;


    @Override
    public String generateExpiredToken(String accessToken, String privateSalt)
    {
        return generateMixedToken(privateSalt, accessToken, String.valueOf(new Date().getTime()));
    }

    @Override
    public String generateHighSecurityTAccessToken(String username, String privateSalt)
    {
        // 如果未配置哈希算法的迭代次数,使用默认配置
        Integer iterations = getHashIterations();
        if (iterations == null)
        {
            iterations = DEFAULT_HASH_ITERATIONS;
        }
        // 先简单生成一次摘要作为令牌生成材料
        String tokenSource = this.generateExpiredToken(username, privateSalt);
        // 计算令牌
        String accessToken = credentialsComputer.computeHash(tokenSource, iterations).toBase64();
        return shuffleToken(accessToken);
    }

    /**
     * 混入私盐、用户名、当前时间生成一个可传递的令牌 该一次性令牌的签发应该是轻量级的
     * 
     * @param privateSalt
     *            私盐
     * @param username
     *            用户名
     * @param formattedDate
     *            证书生成日期
     * @return 生成的证书
     */
    private String generateMixedToken(String privateSalt, String username, String formattedDate)
    {
        return credentialsComputer.digest(privateSalt + username,
            formattedDate + UUID.randomUUID()).toBase64();
    }

    private String dateToString(Date date)
    {
        return DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
    }

    @Override
    public String shuffleToken(String sourceToken)
    {
        List<String> tokenElements = Arrays.asList(sourceToken.split(""));
        for (int iteration = 0; iteration < SHUFFLE_ITERATIONS; iteration++ )
        {
            Collections.shuffle(tokenElements);
        }
        StringBuilder builder = new StringBuilder("");
        tokenElements.forEach(builder::append);
        return builder.toString();
    }

}
