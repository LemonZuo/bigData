package com.lemonzuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
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
 * @create 2019-03-28 9:55
 */
public class AvgMR {
    public static class AvgMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(",");
            String name = split[0];
            double score = Double.valueOf(split[2]);
            context.write(new Text(name), new DoubleWritable(score));
        }
    }

    public static class AvgReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        @Override
        protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            Iterator<DoubleWritable> iterator = values.iterator();
            double total = 0;
            int count = 0;
            while (iterator.hasNext()) {
                DoubleWritable next = iterator.next();
                total += next.get();
                count ++;
            }
            double avgScore = total / count;
            context.write(key, new DoubleWritable(avgScore));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1 获取配置信息以及封装任务
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 设置jar加载路径
        job.setJarByClass(AvgMR.class);

        // 3 设置map和reduce类
        job.setMapperClass(AvgMapper.class);
        job.setReducerClass(AvgReduce.class);

        // 4 设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        // 5 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("F:\\wordCount\\scorein"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\wordCount\\scoreout"));
        // 7 提交
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
