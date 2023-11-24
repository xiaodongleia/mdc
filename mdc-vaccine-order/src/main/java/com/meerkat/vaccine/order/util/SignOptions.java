package com.meerkat.vaccine.order.util;

import java.util.Set;

/**
 * @author zhujx
 */
public class SignOptions {

    public static final SignOptions DEFAULT = new SignOptions();

    private String timestamp;
    private int expirationInSeconds = 1800;
    private Set<String> headersToSign;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getExpirationInSeconds() {
        return expirationInSeconds;
    }

    public void setExpirationInSeconds(int expirationInSeconds) {
        this.expirationInSeconds = expirationInSeconds;
    }

    public Set<String> getHeadersToSign() {
        return headersToSign;
    }

    public void setHeadersToSign(Set<String> headersToSign) {
        this.headersToSign = headersToSign;
    }
}