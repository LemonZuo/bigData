package com.lemonzuo.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author LemonZuo
 * @create 2019-05-07 10:23
 */
public class FileUpLoad {
    public void fileUpLoad() throws URISyntaxException, IOException, InterruptedException {
        Configuration configuration = new Configuration();
        URI uri = new URI("hdfs://lemonzuo:9000");
        FileSystem fileSystem = FileSystem.get(uri, configuration, "root");
        Path localPath = new Path("D:/test.txt");
        Path hdfsPath = new Path("/");
        fileSystem.copyFromLocalFile(localPath, hdfsPath);
        System.out.println("文件上传结束");
        fileSystem.close();
    }
}
