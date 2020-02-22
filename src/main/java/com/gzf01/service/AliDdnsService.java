package com.gzf01.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
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
    private static Gson gson = new Gson();

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
            logger.info("Success:getRecordList");
        }  catch (ClientException e) {
            logger.error("Error:getRecordList");
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("ErrRequestId:" + e.getRequestId());
        }

        return response;
    }

    public boolean updateRecord(DescribeDomainRecordsResponse.Record record){
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        boolean result = false;
        request.setRecordId(record.getRecordId());
        request.setRR(record.getRR());
        request.setType(record.getType());
        request.setValue(record.getValue());
        try {
            client.getAcsResponse(request);
            logger.info("Success:updateRecord as follow:");
            logger.info(gson.toJson(record));
            result = true;
        }  catch (ClientException e) {
            logger.error("Error:updateRecord");
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("ErrRequestId:" + e.getRequestId());
        }

        return result;

    }


    public boolean addRecord(DescribeDomainRecordsResponse.Record record){
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        boolean result = false;
        request.setDomainName(record.getDomainName());
        request.setRR(record.getRR());
        request.setType(record.getType());
        request.setValue(record.getValue());
        try {
            client.getAcsResponse(request);
            logger.info("Success:addRecord as follow:");
            logger.info(gson.toJson(record));
            result = true;
        }  catch (ClientException e) {
            logger.error("Error:updateRecord");
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("ErrRequestId:" + e.getRequestId());
        }

        return result;
    }
}
