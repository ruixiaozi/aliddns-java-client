package com.gzf01.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class NetWorkUtils {

    public static String httpPost(String url, Map<String,String> para){
        String result = "";
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> formparams = new ArrayList<>();
            for(Map.Entry<String,String> entry:para.entrySet()){
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams,"UTF-8");
            httpPost.setEntity(uefEntity);

            try(CloseableHttpResponse response = httpClient.execute(httpPost)){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getLocalIPv6Address() {
        InetAddress inetAddress = null;
        String ipAddr = "";
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        outer:
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAds = networkInterfaces.nextElement().getInetAddresses();
            while (inetAds.hasMoreElements()) {
                inetAddress = inetAds.nextElement();
                //Check if it's ipv6 address and reserved address
                if (inetAddress instanceof Inet6Address
                        && !isReservedAddr(inetAddress) && !isInerAddr(inetAddress)) {
                    break outer;
                }
            }
        }
        ipAddr = inetAddress.getHostAddress();
        // Filter network card No
        int index = ipAddr.indexOf('%');
        if (index > 0) {
            ipAddr = ipAddr.substring(0, index);
        }
        return ipAddr;
    }


    private static boolean isReservedAddr(InetAddress inetAddr) {
        if (inetAddr.isAnyLocalAddress() || inetAddr.isLinkLocalAddress()
                || inetAddr.isLoopbackAddress()) {
            return true;
        }
        return false;
    }

    private static boolean isInerAddr(InetAddress inetAddress){
        if(inetAddress instanceof Inet6Address){
            String head = inetAddress.getHostAddress().substring(0,2);
            if(head.equals("fc")||head.equals("fd")){
                return true;
            }
        }
        return false;
    }

}
