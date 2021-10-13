package com.it.hospital_manage.util;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class HttpRequestHelper {

    public static void main(String[] args) {

    }

    /**
     *
     * @param paramMap
     * @return
     */
    public static Map<String, Object> switchMap(Map<String, String[]> paramMap){
        HashMap<String, Object> resultMap = new HashMap<>();
        Set<Map.Entry<String, String[]>> entries = paramMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            resultMap.put(entry.getKey(),entry.getValue()[0]);
        }
        return resultMap;
    }

    /**
     *   请求数据获取签名
     * @param paramMap
     * @param signKey
     * @return
     */
    public static String getSign(Map<String,Object> paramMap, String signKey){
        if (paramMap.containsKey("sign")){
            paramMap.remove("sign");
        }
        TreeMap<String, Object> sorted = new TreeMap<>(paramMap);
        StringBuilder str = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = sorted.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            str.append(entry.getValue()).append("|");
        }
        str.append(signKey);
        log.info("加密前:" +str.toString());
        String md5Str = MD5.encrypt(str.toString());
        log.info("加密后:"+md5Str);
        return md5Str;
    }

    /**
     *    签名校验
     * @param paramMap
     * @param signKey
     * @return
     */
    public static boolean isSignEquals(Map<String, Object> paramMap, String signKey){
        String sign = (String)paramMap.get("sign");
        String md5Str = getSign(paramMap, signKey);
        if (!sign.equals(md5Str)){
            return false;
        }
        return true;
    }

    /**
     *  获得时间戳
     * @return
     */
    public static long getTimestamp(){
        return new Date().getTime();     //获得毫秒数
    }

    /**
     *   封装同步请求
     * @param paramMap
     * @param url
     * @return
     */
    public static JSONObject sendRequest(Map<String, Object> paramMap, String url){
        String result = "";
        try {
            //封装post参数
            StringBuilder postData = new StringBuilder();
            Set<Map.Entry<String, Object>> entries = paramMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                postData.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
            }
            log.info(String.format("--> 发送请求: post data %1s", postData));
            byte[] reqData = postData.toString().getBytes("utf-8");
            byte[] respData = HttpUtil.doPost(url, reqData);
            result = new String(respData);
            log.info(String.format("--> 应答结果: result data %1s", result));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }
}
