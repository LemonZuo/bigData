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

public class WordCountJob {
    public static class WordCountMapper extends Mapper<LongWritable, Text,Text,IntWritable>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(",");
            for (String s:split) {
                context.write(new Text(s),new IntWritable(1));
            }
        }
    }

    public static class WordCountReduce extends Reducer<Text,IntWritable,Text,IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            //迭代器
            Iterator<IntWritable> iterator = values.iterator();
            //记录每个String有多少个
            int sum = 0;
            while (iterator.hasNext()){
                IntWritable next = iterator.next();
                sum +=next.get();
            }
            context.write(key,new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建configuration对象
        Configuration configuration = new Configuration();

        //2.创建job对象
        Job job = Job.getInstance(configuration);

        //3.设置job,mapper,reducer类
        job.setJarByClass(WordCountJob.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);

        //4.设置map输出的key,value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //5.设置最终输出的类型，最终输出的类型就是reduce的输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //6.设置文件的输入和输出类型
        FileInputFormat.setInputPaths(job,new Path("D:\\test\\in"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\test\\out"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1 );
    }
}
