package com.lemonzuo.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author LemonZuo
 * @create 2019-05-07 10:48
 */
public class PhoneJob {
    public static class PhoneJobMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String buffer = value.toString();
            String[] line = value.toString().split("\t");
            String phone = line[1];
            context.write(new Text(phone), NullWritable.get());
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1 获取配置信息以及封装任务
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 设置jar加载路径
        job.setJarByClass(PhoneJob.class);

        // 3 设置map
        job.setMapperClass(PhoneJobMapper.class);

        // 4 设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setPartitionerClass(MyPatitioner.class);
        job.setNumReduceTasks(5);


        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("F:\\wordCount\\phonein"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\wordCount\\phoneout"));
        // 7 提交
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
