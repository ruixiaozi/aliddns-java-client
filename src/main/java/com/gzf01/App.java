package com.gzf01;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.google.gson.Gson;
import com.gzf01.entity.Conf;
import com.gzf01.service.AliDdnsService;
import com.gzf01.utils.NetWorkUtils;
import org.apache.log4j.Logger;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = Logger.getLogger(App.class);
    private static final long SLEEP_TIME = 1000*60*2;

    public static void main( String[] args )
    {
        Reader reader = null;
        try {
            //调试的文件路径
            String path = App.class.getResource("/").toString().substring(6)+"/aliddns.conf";
            reader = new InputStreamReader(new FileInputStream(path),"UTF-8");
            //正式版本使用标准输入
            //reader = new InputStreamReader(System.in,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error:don't suppert UTF-8!");
        } catch (FileNotFoundException e) {
            logger.error("Error:conf file does not find!");
        }

        //读取配置文件
        Conf conf = new Gson().fromJson(reader, Conf.class);
        logger.info("Success:readConfig");
        logger.info("accessKeyId:"+conf.getAccessKeyId());
        char[] hiddenstr = new char[conf.getAccessKeySecret().length()];
        Arrays.fill(hiddenstr,'*');
        for(int strinx=3;strinx<6;strinx++){
            hiddenstr[strinx] = conf.getAccessKeySecret().charAt(strinx);
        }
        logger.info("accessKeySecret:"+String.valueOf(hiddenstr));
        logger.info("domainName:"+conf.getDomainName());
        logger.info("host:"+conf.getHost());
        //需要的记录类型
        String type = conf.isIsIpv6()?"AAAA":"A";
        logger.info("type:"+type);

        //创建服务类
        AliDdnsService aliDdnsService = new AliDdnsService(conf.getAccessKeyId(),conf.getAccessKeySecret());

        while (true){
            //获取解析记录
            DescribeDomainRecordsResponse recordsResponse = aliDdnsService.getRecordList(conf.getDomainName());

            if(recordsResponse == null){
                logger.error("Error:DomainName is don't know!");
                break;
            }

            //获取记录列表
            List<DescribeDomainRecordsResponse.Record> recordList = recordsResponse.getDomainRecords();
            //根据主机名寻找记录
            DescribeDomainRecordsResponse.Record resultRecord = null;
            for(DescribeDomainRecordsResponse.Record record:recordList){
                if(record.getRR().equals(conf.getHost())){
                    resultRecord = record;
                    break;
                }
            }


            //当前的IP地址
            String value = conf.isIsIpv6()? NetWorkUtils.getLocalIPv6Address() :NetWorkUtils.getPublicIPv4Address();
            logger.info("newIP:"+value);

            if(resultRecord!=null){
                //找到记录
                logger.info("oldType:"+resultRecord.getType());
                logger.info("oldIP:"+resultRecord.getValue());
                if(resultRecord.getType().equals(type) && resultRecord.getValue().equals(value)){
                    //值相同，不需要修改
                    logger.info("Infom:need not update!");
                }
                else{
                    //值不同,需要修改
                    resultRecord.setType(type);
                    resultRecord.setValue(value);
                    if(!aliDdnsService.updateRecord(resultRecord)){
                        logger.warn("Warning:update failed!");
                    }

                }

            }
            else {
                //没有找到记录，需要添加
                resultRecord = new DescribeDomainRecordsResponse.Record();
                resultRecord.setDomainName(conf.getDomainName());
                resultRecord.setRR(conf.getHost());
                resultRecord.setType(type);
                resultRecord.setValue(value);

                if(!aliDdnsService.addRecord(resultRecord)){
                    logger.warn("Warning:adding failed!");
                }
            }

            //间隔2分钟
            logger.info("Infom:sleep 2 minutes...");
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("Error:sleep error");
                logger.error("ErrCause:" + e.getCause());
                logger.error("ErrMsg:" + e.getMessage());
            }
        }

    }
}
