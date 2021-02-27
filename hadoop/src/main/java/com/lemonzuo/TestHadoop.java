package com.lemonzuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author LemonZuo
 * @create 2019-03-2019/3/13-8:06
 */
public class TestHadoop {
    private Configuration conf;
    private FileSystem fileSystem;

    public void init() throws IOException, URISyntaxException, InterruptedException {
        conf = new Configuration();
        URI uri = new URI("hdfs://lemonzuo:9000");
//        conf.set("fs.defaultFS","hdfs://lemonzuo:9000");
        fileSystem = FileSystem.get(uri, conf, "root");
    }

    @Test
    /**
     * 测试创建文件夹
     */
    public void testMkidr() throws IOException, URISyntaxException, InterruptedException {
        init();
        boolean mkdirs = fileSystem.mkdirs(new Path("/user/hdfs"));
        fileSystem.close();
        System.out.println(mkdirs);
    }

    @Test
    /**
     * 测试文件上传
     */
    public void testUpload() throws IOException, URISyntaxException, InterruptedException {
        init();
        fileSystem.copyFromLocalFile(new Path("F:\\QQ_Images\\3f6db65a31a339a.png"), new Path("/user/hadoop"));
        fileSystem.close();
    }

    @Test
    /**
     * 测试文件下载
     */
    public void testDownload() throws IOException, URISyntaxException, InterruptedException {
        init();
        fileSystem.copyToLocalFile(false,new Path("/user/hadoop/3f6db65a31a339a.png"), new Path("F:\\"), true);
        fileSystem.close();
    }

    @Test
    /**
     * 测试文件夹删除
     */
    public void testDeleDirectory() throws IOException, URISyntaxException, InterruptedException {
        init();
        boolean delete = fileSystem.delete(new Path("/user/hdfs"), true);
        fileSystem.close();
    }

    @Test
    /**
     * 测试重命名
     */
    public void testRename() throws IOException, URISyntaxException, InterruptedException {
        init();
        fileSystem.rename(new Path("/user/hdfs"), new Path("/user/hdfs"));
        fileSystem.close();
    }

    @Test
    public void testFileInfo() throws IOException, URISyntaxException, InterruptedException {
        init();
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/user/"));
        for (int i = 0; i < fileStatuses.length; i++) {
            FileStatus fileStatus = fileStatuses[i];
            String owner = fileStatus.getOwner();
            short replication = fileStatus.getReplication();
            long blockSize = fileStatus.getBlockSize();
            System.out.println(owner + " ," + replication+ " ," + blockSize);
        }
        fileSystem.close();
    }

    /**
     * 通过文件流上传文件
     */
    @Test
    public void uploadByStream() throws InterruptedException, IOException, URISyntaxException {
        init();
        // 文件输出流
        FileInputStream fileInputStream = new FileInputStream(new File("E:\\小僵尸\\14879028581691.jpg"));
        // HDFS输出流
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/user/hadoop/14879028581691.jpg"));
        IOUtils.copyBytes(fileInputStream, fsDataOutputStream, conf);
        fileInputStream.close();
        fileSystem.close();
    }
    /**
     * 通过文件流下载
     */
    @Test
    public void downloadByStream() throws InterruptedException, IOException, URISyntaxException {
        init();
        
        // HDFS输入流
        FSDataInputStream open = fileSystem.open(new Path("/user/hadoop/14879028581691.jpg"));
        // 本地文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("E:\\14879028581691.jpg"));
        // 流的对拷
        IOUtils.copyBytes(open, fileOutputStream, conf);
        fileSystem.close();




    }
}
