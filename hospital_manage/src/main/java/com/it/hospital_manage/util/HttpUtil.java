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

    /**
     * 以post方式发送http请求
     *
     * @param strUrl
     * @param reqData
     * @return
     */
    public static byte[] doPost(String strUrl, byte[] reqData) {
        return send(strUrl, POST, reqData);
    }

    /**
     * 以get方式提交http请求
     *
     * @param strUrl
     * @return
     */
    public static byte[] doGet(String strUrl) {
        return send(strUrl, GET, null);
    }

    /**
     * @param strUrl
     * @param reqmethod
     * @param reqData
     * @return
     */
    public static byte[] send(String strUrl, String reqmethod, byte[] reqData) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setDoOutput(true);  // 打开输出流，向服务器提交数据
            httpcon.setDoInput(true);   // 打开输入流，从服务器获取数据
            httpcon.setUseCaches(false);
            httpcon.setInstanceFollowRedirects(true);    //是否可以自动重定向
            httpcon.setConnectTimeout(CONN_TIMEOUT);
            httpcon.setReadTimeout(READ_TIMEOUT);
            httpcon.setRequestMethod(reqmethod);
            httpcon.connect();
            if (reqmethod.equalsIgnoreCase(POST)) {      //字符串与指定的POST做比较
                // 获取输出流，向服务器提交数据
                OutputStream os = httpcon.getOutputStream();
                os.write(reqData);
                os.flush();
                os.close();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "utf-8"));
            String inputLine;
            StringBuilder bankXmlBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                bankXmlBuffer.append(inputLine);
            }
            in.close();
            httpcon.disconnect();
            return bankXmlBuffer.toString().getBytes();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            return null;
        }
    }

    /**
     * 从输入流中读取数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        byte[] data = outputStream.toByteArray();  //网页的二进制数据
        outputStream.close();
        inputStream.close();
        return data;
    }
}
