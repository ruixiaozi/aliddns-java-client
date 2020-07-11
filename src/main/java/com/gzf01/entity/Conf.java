package com.gzf01.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Conf implements Serializable {

    @Override
    public String toString() {
        return "Conf{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", domainName='" + domainName + '\'' +
                ", host='" + host + '\'' +
                ", isIpv6=" + isIpv6 +
                '}';
    }

    /**
     * accessKeyId :
     * accessKeySecret  :
     * domainName :
     * host :
     * isIpv6 : true
     */


    @SerializedName("accessKeyId")
    private String accessKeyId;
    @SerializedName("accessKeySecret")
    private String accessKeySecret;
    @SerializedName("domainName")
    private String domainName;
    @SerializedName("host")
    private String host;
    @SerializedName("isIpv6")
    private boolean isIpv6;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isIsIpv6() {
        return isIpv6;
    }

    public void setIsIpv6(boolean isIpv6) {
        this.isIpv6 = isIpv6;
    }
}
