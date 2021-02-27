package com.lemonzuo.mapreduce;

import com.lemonzuo.model.Flow;
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

/**
 * @author LemonZuo
 * @create 2019-07-22 22:41
 * 流量统计输出
 */
public class FlowJob {
    public static class FlowMapper extends Mapper<LongWritable, Text, Flow, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split(",");
            String phone = line[1];
            double upFlow = Double.parseDouble(line[6]);
            double downFlow = Double.parseDouble(line[7]);
            double totalFlow = upFlow + downFlow;
            Flow flow = new Flow();
            flow.setPhone(phone);
            flow.setDownFlow(downFlow);
            flow.setUpFlow(upFlow);
            flow.setTotalFlow(totalFlow);
            context.write(flow, NullWritable.get());
        }
    }

    public static class FlowReduce extends Reducer<Flow, NullWritable, Flow, NullWritable> {
        @Override
        protected void reduce(Flow key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
           context.write(key, NullWritable.get());
        }
    }
    public static void main(String[] args) {
        try {
            // 1 获取配置信息以及封装任务
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration);

            // 2 设置jar加载路径
            job.setJarByClass(FlowJob.class);

            // 3 设置map和reduce类
            job.setMapperClass(FlowMapper.class);
            job.setReducerClass(FlowReduce.class);

            // 4 设置map输出
            job.setMapOutputKeyClass(Flow.class);
            job.setMapOutputValueClass(NullWritable.class);

            // 5 设置最终输出kv类型
            job.setOutputKeyClass(Flow.class);
            job.setOutputValueClass(NullWritable.class);

            // 6 设置输入和输出路径
            FileInputFormat.setInputPaths(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\res"));
            FileOutputFormat.setOutputPath(job, new Path("E:\\Idea\\bigData\\UserBehaviorAnalysisSystem\\src\\main\\flowOut"));
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
