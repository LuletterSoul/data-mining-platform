package com.vero.dm.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Map;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  12:10 2017/7/17.
 * @description
 * @modified by:
 */
public class HmacSHA256Utils
{
    public static String digest(String key, String content) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes();
            byte[] dataBytes = content.getBytes();

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            return Base64.encodeBase64String(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String digest(String key, Map<String, ?> map) {
        StringBuilder s = new StringBuilder();
        for(Object values : map.values()) {
            if(values instanceof String[]) {
                for(String value : (String[])values) {
                    s.append(value);
                }
            } else if(values instanceof List) {
                for(String value : (List<String>)values) {
                    s.append(value);
                }
            } else {
                s.append(values);
            }
        }
        return digest(key, s.toString());
    }
}
