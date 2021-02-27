package com.lemonzuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author LemonZuo
 * @create 2019-03-27 10:45
 */
public class WordCount {
    public static class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
        /**
         * map 阶段
         * map阶段输出数据形式{value, num}
         * @param key 数据偏移量
         * @param value 数据内容
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 输出到缓冲区等待数据被拉取
            context.write(value, new IntWritable(1));
        }
    }

    public static class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
        /**
         *
         * @param key 数据索引键
         * @param values 迭代器集合
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            // 获取迭代器集合
            Iterator<IntWritable> iterator = values.iterator();
            // 迭代数据
            int sum = 0;
            while (iterator.hasNext()) {
                // 获取当前迭代器
                IntWritable next = iterator.next();
                // 获取当前迭代器元素值
                int i = next.get();
                // 合并次数
                sum += i;
            }
            // 输出到缓冲区
            context.write(key, new IntWritable(sum));
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1 获取配置信息以及封装任务
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 设置jar加载路径
        job.setJarByClass(WordCount.class);

        // 3 设置map和reduce类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4 设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("F:\\wordCount\\in"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\wordCount\\out"));
        // 7 提交
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);

    }



}
