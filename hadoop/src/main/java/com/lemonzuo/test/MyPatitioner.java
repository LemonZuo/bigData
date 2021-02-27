package com.lemonzuo.test;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author LemonZuo
 * @create 2019-05-07 10:53
 */
public class MyPatitioner extends Partitioner<Text, NullWritable> {
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {
        String phone = text.toString();
        if(phone.startsWith("136")) {
            return 0;
        } else if (phone.startsWith("137")) {
            return 1;
        } else if (phone.startsWith("138")) {
            return 2;
        } else if(phone.startsWith("139")) {
            return 3;
        } else {
            return 4;
        }
    }
}
