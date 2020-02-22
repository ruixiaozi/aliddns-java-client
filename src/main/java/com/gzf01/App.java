package com.gzf01;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.gzf01.service.AliDdnsService;
import org.apache.log4j.Logger;


import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        AliDdnsService aliDdnsService = new AliDdnsService("LTAI4FosaDd7qTHckm9oED1e","tc1UTl8tnXhNLJWK4eyXrArZVqfL3L");

        DescribeDomainRecordsResponse recordsResponse = aliDdnsService.getRecordList("ruixiaozi.com");
        if(recordsResponse == null){
            logger.error("域名错误："+);
        }

        List<DescribeDomainRecordsResponse.Record> recordList = .getDomainRecords();


    }
}
