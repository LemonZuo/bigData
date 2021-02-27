package com.lemonzuo.mapreduce;

import com.lemonzuo.partition.DataInitPartitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author LemonZuo
 * @create 2019-07-22 17:54
 * 清洗LOG.txt
 */
public class DataInitJob {
    public static class DataInitMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split(",");
            if(line[1].startsWith("+")) {
                line[1] = line[1].replace("+", "");
            }
            String valStr = Arrays.toString(line).replace(" ", "")
                    .replace("[", "")
                    .replace("]", "");
            value = new Text(valStr);
            context.write(value, NullWritable.get());
        }
    }

    public static class DataInitReduce extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }
    }

    public static void main(String[] args) {
        try {
            // 1 获取配置信息以及封装任务
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration);

            // 2 设置jar加载路径
            job.setJarByClass(DataInitJob.class);

            // 3 设置map和reduce类
            job.setMapperClass(DataInitMapper.class);
            job.setReducerClass(DataInitReduce.class);

            // 4 设置map输出
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);

            // 5 设置最终输出kv类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            job.setNumReduceTasks(2);
            job.setPartitionerClass(DataInitPartitioner.class);


            // 6 设置输入和输出路径
            FileInputFormat.setInputPaths(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\resources"));
            FileOutputFormat.setOutputPath(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\dataOut"));
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
