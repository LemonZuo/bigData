package com.lemonzuo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ZooKeeperTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperTest.class);
    // zookeeper集群地址
    private static final String CONNECTION = "lemonzuo:2181,lemon:2181,zuo:2181";
    // 超时时间
    private static final Integer TIMEOUT = 2000;
    private static ZooKeeper zooKeeperClient = null;

    static {
        try {
            zooKeeperClient = new ZooKeeper(CONNECTION, TIMEOUT, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws InterruptedException {
        if (zooKeeperClient != null) {
            zooKeeperClient.close();
        }
    }

    /**
     * Zookeeper 创建
     * @param path 路径
     * @param data 数据
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void create(String path, String data) throws KeeperException, InterruptedException {
        zooKeeperClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        LOGGER.info("===============================");
        close();
    }

    /**
     * 获取数据
     * @param path 路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String get(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeperClient.getData(path, null, null);
        return new String(data);
    }

    /**
     * 获取子节点信息
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeperClient.getChildren(path, null);
        return children;

    }

    public static void nodeExist(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeperClient.exists(path, null);
        LOGGER.info(stat.toString());
    }
    public static void delete(String path) throws KeeperException, InterruptedException {
        zooKeeperClient.delete(path, 0);
    }
    public static void main(String[] args) throws KeeperException, InterruptedException {
        // create("/api", "hello");
        // LOGGER.info(get("/api"));
        // List<String> children = getChildren("/");
//        for(String s: children) {
//            LOGGER.info(s);
//        }
        // nodeExist("/api");
        delete("/api");
    }
}
