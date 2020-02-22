package com.gzf01.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import com.gzf01.App;
import org.apache.log4j.Logger;

public class AliDdnsService {

    private static Logger logger = Logger.getLogger(App.class);
    private String regionId = "cn-hangzhou"; //必填固定值，必须为“cn-hanghou”

    private IClientProfile profile;
    // 若报Can not find endpoint to access异常，请添加以下此行代码
    // DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
    private IAcsClient client;



    public AliDdnsService(String accessKeyId, String accessKeySecret) {
        this.profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        this.client = new DefaultAcsClient(profile);
    }

    public DescribeDomainRecordsResponse getRecordList(String domainName){
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        DescribeDomainRecordsResponse response = null;
        request.setDomainName(domainName);
        try {
            response = client.getAcsResponse(request);

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return response;
    }
}
