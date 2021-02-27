package com.lemonzuo.partition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author LemonZuo
 * @create 2019-07-22 20:22
 * 正常数据的脏数据分区处理
 */
public class DataInitPartitioner extends Partitioner<Text, NullWritable> {
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {
        String[] line = text.toString().split(",");
        int length = line.length;
        System.out.println(length);
        if (length == 18) {
            return 0;
        } else {
            return 1;
        }
    }
}
