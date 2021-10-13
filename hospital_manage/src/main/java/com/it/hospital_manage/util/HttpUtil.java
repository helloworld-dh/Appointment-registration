package com.it.hospital_manage.util;

import lombok.extern.slf4j.Slf4j;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;

@Slf4j
public final class HttpUtil {
    static final String POST = "POST";
    static final String GET = "GET";
    static final int CONN_TIMEOUT = 30000;
    static final int READ_TIMEOUT = 30000;

    public static byte[] doPost(String strUrl, byte[] reqData){
        return send(strUrl,POST,reqData);
    }

    public static byte[] send(String strUrl, String reqmethod, byte[] reqData){
        try {
           URL url = new URL(strUrl);
           HttpURLConnection httpcon =  (HttpURLConnection)url.openConnection();
           httpcon.setDoOutput(true);  //指定可以进行写操作
           httpcon.setDoInput(true);
           httpcon.setUseCaches(false);
           httpcon.setInstanceFollowRedirects(true);    //是否可以自动重定向
           httpcon.setConnectTimeout(CONN_TIMEOUT);
           httpcon.setReadTimeout(READ_TIMEOUT);
           httpcon.setRequestMethod(reqmethod);
           httpcon.connect();
           if (reqmethod.equalsIgnoreCase(POST)){      //字符串与指定的POST做比较
             OutputStream os = httpcon.getOutputStream();
             os.write(reqData);
             os.flush();
             os.close();
           }
            BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"utf-8"));
            String inputLine;
            StringBuilder bankXmlBuffer = new StringBuilder();
            while ((inputLine = in.readLine())!=null){
                bankXmlBuffer.append(inputLine);
            }
            in.close();
            httpcon.disconnect();
            return bankXmlBuffer.toString().getBytes();
        } catch (Exception e) {
            log.error(ex.toString(), ex);
            return null;
        }
    }
}
