package com.vero.dm.security.constants;

import lombok.Data;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  12:07 2017/7/17.
 * @description
 * @modified by:
 */
@Data
public class Constants
{
    public static final String CLIENT_DIGEST_HEADER = "X-Client-Digest";
    public static final String SUGGESTED_FILE_NAME = "X-Suggested-Filename";
    public static final String USERNAME_HEADER = "X-Username";
    public static final String TIMESTAMP_HEADER = "X-Timestamp";
    public static final String ACCESS_TOKEN_HEADER = "X-Access-Token";
    public static final String DISPOSABLE_TOKEN_HEADER = "X-Disposable-Token";
    public static final String APPLY_CREDENTIAL = "X-Apply-Credential";
    public static final String ACCESS_BY_PRE_TOKEN = "X-Pre-Token";
}