package com.lemonzuo.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author LemonZuo
 * @create 2019-03-25 22:05
 */
public class HadoopUtil {
    private static Configuration CONFIGURATION;
    private static FileSystem FILE_SYSTEM;
    private static Path PATH;
    private static URI URI;
    private static String USER;

    static {
        try {
            Properties properties = new Properties();
            InputStream in = HadoopUtil.class.getResourceAsStream("/hadoop.properties");
            properties.load(in);
            URI = new URI(properties.getProperty("hadoop.uri"));
            USER = properties.getProperty("hadoop.user");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取fileSystem
     * @throws IOException
     * @throws InterruptedException
     */
    private static void getFileSystem() throws IOException, InterruptedException {
        CONFIGURATION = new Configuration();
        FILE_SYSTEM = FileSystem.get(URI, CONFIGURATION, USER);
    }

    /**
     * 文件上传
     *
     * @param filePath    文件路径
     * @param fileName    文件名
     * @param inputStream 文件输入流
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean fileUpload(String filePath, String fileName, InputStream inputStream) throws IOException, InterruptedException {
        getFileSystem();
        // 1、检测路径是否存在
        PATH = new Path(filePath);
        boolean isPathExist = FILE_SYSTEM.exists(PATH);
        if (!isPathExist) {
            // 目录不存在创建目录
            FILE_SYSTEM.mkdirs(PATH);
        }
        // 2、检测文件是否已存在
        PATH = new Path(filePath + fileName);
        boolean isFileExist = FILE_SYSTEM.exists(PATH);
        if (isFileExist) {
            return false;
        }
        // 文件流上传
        FSDataOutputStream fsDataOutputStream = FILE_SYSTEM.create(PATH);
        IOUtils.copyBytes(inputStream, fsDataOutputStream, CONFIGURATION);
        // 3、检测上传结果
        isFileExist = FILE_SYSTEM.exists(PATH);
        // 释放资源
        FILE_SYSTEM.close();
        if (isFileExist) {
            // 上传成功
            return true;
        } else {
            // 上传失败
            return false;
        }
    }

}
