package com.lemonzuo.model;

/**
 * @author LemonZuo
 * @create 2019-07-22 17:49
 */
public class Fileds {
    //事物ID
    public static final int T_ID = 0;
    //手机
    public static final int MSISDN = 1;
    //IMSI
    public static final int IMSI = 2;
    //IMEI
    public static final int IMEI = 3;
    //APN
    public static final int APN = 4;
    //protocol_type
    public static final int PROTOCOL_TYPE = 5;
    //uplink_bytes
    public static final int UPLINK_BYTES = 6;
    //downlink_bytes
    public static final int DOWNLINK_BYTES = 7;
    //start_time
    public static final int START_TIME = 8;
    //end_time
    public static final int END_TIME = 9;
    //method 1,GET 的话是获取内容 2,POST 的话是上传内容
    public static final int METHOD = 10;
    //host
    public static final int HOST = 11;
    //uri
    public static final int URI = 12;
    //User agent 用户代理，客户使用的操作系统及版本、CPU 类型、浏览器及版本等信息
    public static final int USER_AGENT = 13;
    //content_type比如 image/gif 表示资源是一张 gif 图像，比如 text/html 表示 html 页面等
    public static final int CONTENT_TYPE = 14;
    //content_length 资源的长度。
    public static final int CONTENT_LENGTH = 15;
    //client_ip
    public static final int CLIENT_IP = 16;
    //服务器 ip 地址 server_ip 通常就是网站的 ip 地址
    public static final int SERVER_IP = 17;
    //客服端端口号 client_port
    public static final int CLIENT_PORT = 18;
}
