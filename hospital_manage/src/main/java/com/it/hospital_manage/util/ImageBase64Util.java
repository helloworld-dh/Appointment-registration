package com.it.hospital_manage.util;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageBase64Util {
    public static void main(String[] args) {

        //待处理的图片
        String imageFile = "D:\\yygh_work\\xh.png";
        System.out.println(getImageString(imageFile));
    }

    public static String getImageString(String imageFile){
        InputStream is = null;
        try {
            is = new FileInputStream(new File(imageFile));
            byte[] data = new byte[is.available()];
            is.read(data);
            byte[] bytes = Base64.encodeBase64(data);    //对字节数组进行Base64编码
            return new String(bytes);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (is !=null){
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
