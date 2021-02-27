package com.lemonzuo.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
 * @create 2019-07-23 19:36
 * 清洗URLRule数据表
 */
public class UrlRuleJob {
    public static class UrlRuleMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\\|");
            String data = line[0] + "," + line[1] + "," + line[2] + "," + line[3] + "," + line[4];
            context.write(new Text(data), NullWritable.get());
        }
    }
    public static void main(String[] args) {
        try {
            // 1 获取配置信息以及封装任务
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration);

            // 2 设置jar加载路径
            job.setJarByClass(UrlRuleJob.class);

            // 3 设置map和reduce类
            job.setMapperClass(UrlRuleMapper.class);

            // 4 设置map输出
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);

            // 6 设置输入和输出路径
            FileInputFormat.setInputPaths(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\resources\\urlRule"));
            FileOutputFormat.setOutputPath(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\resources\\urlRuleOut"));
            // 7 提交
            boolean result = job.waitForCompletion(true);

            System.exit(result ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
