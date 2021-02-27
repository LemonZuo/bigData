package com.lemonzuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
 * @create 2019-03-31 14:17
 */
public class UpDownCountJob {
    public static class UpDownMap extends Mapper<LongWritable, Text, Text, FlowBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] data = line.split("\t");
            String tel = data[1];
            double upFlow = Double.parseDouble("".equals(data[4]) || data[4] == null ? "0" : data[4]);
            double downFlow = Double.parseDouble("".equals(data[5]) || data[5] == null ? "0" : data[5]);
            context.write(new Text(tel), new FlowBean(upFlow, downFlow, 0));

        }
    }

    public static class UpDownReduce extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            Iterator<FlowBean> iterator = values.iterator();
            double up = 0;
            double down = 0;
            double total = 0;
            while (iterator.hasNext()) {
                FlowBean flowBean = iterator.next();
                up += flowBean.getUpFlow();
                down += flowBean.getUpFlow();
                total += up + down;
            }
            context.write(key, new FlowBean(up, down, total));
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(UpDownCountJob.class);
        job.setMapperClass(UpDownMap.class);
        job.setReducerClass(UpDownReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path("F:\\STU\\BigData\\data"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\STU\\BigData\\data\\out"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
