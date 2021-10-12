package com.it.hospital_manage.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    //默认运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     * @param v1
     * @param v2
     * @return    两个参数的和(double)
     */
    public static double add(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();      //doubleValue返回一个double的值
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double add(double v1,double v2, int scale){
        return round(add(v1,v2),2);
    }

    /**
     *  add
     * @param v1
     * @param v2
     * @return   string类型两数相加
     */
    public static String add(String v1, String v2){
        if (StringUtils.isEmpty(v1)) v1 = "0";
        if (StringUtils.isEmpty(v2)) v2 = "0";

        BigDecimal b1 = new BigDecimal(v1);

        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).toString();
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return   返回string类型的两数相加,结果四舍五入
     */
    public static String add(String v1, String v2, int scale){
        if (StringUtils.isEmpty(v1)) v1 = "0";
        if (StringUtils.isEmpty(v2)) v2 = "0";

        return round(add(v1,v2),2);
    }

    /**
     *
     * @param v1
     * @param v2
     * @return   v1-v2  返回值double类型
     */
    public static double subtract(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return    四舍五入  v1-v2 保留scale小数点
     */
    public static double subtract(double v1, double v2, int scale){
        return round(subtract(v1,v2),scale);
    }

    /**
     *
     * @param v1
     * @param v2
     * @return   v1-v2 string类型
     */
    public static String subtract(String v1, String v2){
        if (StringUtils.isEmpty(v1)) v1 = "0";
        if (StringUtils.isEmpty(v2)) v2 = "0";

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.subtract(b2).toString();
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return  v1-v2 string 保留2位小数
     */
    public static String subtract(String v1, String v2, int scale){
        return round(subtract(v1,v2),2);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return   v1*v2 返回double  保留scale位数
     */
    public static double multiply(double v1, double v2, int scale){
        return round(multiply(v1, v2),scale);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return  v1*v2 返回string类型 返回值保留2位小数
     */
    public static String multiply(String v1, String v2, int scale){
        return round(multiply(v1,v2),scale);

    }

    /**
     *
     * @param v1
     * @param v2
     * @return  v1*v2 返回string类型
     */
    public static String multiply(String v1, String v2){
        if (StringUtils.isEmpty(v1)) v1="0";
        if (StringUtils.isEmpty(v2)) v2="0";

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.multiply(b2).toString();
    }

    /**
     *
     * @param v1
     * @param v2
     * @return  v1/v2 保留10位
     */
    public static double divide(double v1, double v2){

        return divide(v1,v2,DEFAULT_DIV_SCALE);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return    四舍五入方式 保留scale位数
     */
    public static double divide(double v1, double v2, int scale){
        return divide(v1,v2,scale,BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale  保留scale位
     * @param round_mode   取舍的方式
     * @return  v1/v2 double类型
     */
    public static double divide(double v1, double v2, int scale, int round_mode){
        if (scale <0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2,scale,round_mode).doubleValue();
    }

    /**
     *
     * @param v1
     * @param v2
     * @return  v1/v2 返回string
     */
    public static String divide(String v1, String v2){
        if (StringUtils.isEmpty(v1)) v1 = "0";
        if (StringUtils.isEmpty(v2) || Double.parseDouble(v2)==0) v2= "1";

        return divide(v1,v2,DEFAULT_DIV_SCALE);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @return   v1/v2 保留scale位数 返回string
     */
    public static String divide(String v1, String v2, int scale){
        return divide(v1,v2,scale,BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param scale
     * @param round_mode
     * @return
     */
    public static String divide(String v1,String v2, int scale, int round_mode){
        if (scale <0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2,scale,round_mode).toString();
    }

    /**
     *
     * @param v1
     * @param v2
     * @return   v1*v2   double
     */
    public static double multiply(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();
    }

    /**
     *    四舍五入
     * @param v
     * @param scale   小数点保留几位
     * @return    四舍五入的结果
     */
    public static double round(double v, int scale){
        return round(v,scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *    提供精确的小数位四舍五入处理
     * @param v     需要四舍五入的数
     * @param scale  小数点后保留几位
     * @param round_mode  指定的舍入模式
     * @return     四舍五入的结果
     */
    public static double round(double v, int scale, int round_mode){
        if (scale < 0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bigDecimal = new BigDecimal(Double.toString(v));
        return bigDecimal.setScale(scale,round_mode).doubleValue();
    }

    /**
     *  小数位四舍五入
     * @param v  需要四舍五入处理
     * @param scale 小数点保留几位
     * @return    以字符串方式返回结果
     */
    public static String round(String v, int scale){
        return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static String round(String v, int scale, int round_mode){
        if (scale <0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bigDecimal = new BigDecimal(v);
        return bigDecimal.setScale(scale,round_mode).toString();
    }



}
