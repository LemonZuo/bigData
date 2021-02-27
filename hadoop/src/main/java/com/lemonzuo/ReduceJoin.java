package com.lemonzuo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author LemonZuo
 * @create 2019-04-04 9:30
 */
public class ReduceJoin {
    public static class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, ProOrder> {
        String fileName = "";
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            // 获取文件分块
            FileSplit split = (FileSplit) context.getInputSplit();
            // 获取文件名
            fileName = split.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split("\t");
            ProOrder proOrder = new ProOrder();
            if(fileName.startsWith("order")) {
                // 订单信息
                proOrder.setFlag("order");
                proOrder.setOrderId(line[0]);
                proOrder.setOrderNums(line[2]);
                context.write(new Text(line[1]), proOrder);
            } else if(fileName.startsWith("pd")) {
                // 商品信息
                proOrder.setFlag("product");
                proOrder.setProductName(line[1]);
                context.write(new Text(line[0]), proOrder);
            } else {

            }
        }
    }
    public static class ReduceJoinReducer extends Reducer<Text, ProOrder, ProOrder, NullWritable> {
        /**
         * pid,{ProOrder},{ProOrder}
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void reduce(Text key, Iterable<ProOrder> values, Context context) throws IOException, InterruptedException {
            Iterator<ProOrder> iterator = values.iterator();
            // 订单列表
            List<ProOrder> orderList = new ArrayList<ProOrder>();
            // 商品信息
            ProOrder orderInfo = new ProOrder();
            while (iterator.hasNext()) {
                ProOrder buffer = iterator.next();

                String proOrderFlag = buffer.getFlag();
                if("order".equals(proOrderFlag)) {
                    // list
                    ProOrder proOrder = new ProOrder();
                    proOrder.setOrderId(buffer.getOrderId());
                    proOrder.setOrderNums(buffer.getOrderNums());
                    orderList.add(proOrder);
                } else {
                    // 商品信息
                    orderInfo.setProductName(buffer.getProductName());
                }
            }

            // 遍历订单信息列表
            for (ProOrder proOrder:orderList) {
                System.out.println(proOrder);
                // 输出数据
                proOrder.setProductName(orderInfo.getProductName());
                context.write(proOrder, NullWritable.get());
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        File file = new File("F:\\STU\\BigData\\data\\reduceJoin\\out");
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f:files) {
                f.delete();
            }
            file.delete();
        }

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        job.setJarByClass(ReduceJoin.class);
        job.setMapperClass(ReduceJoinMapper.class);
        job.setReducerClass(ReduceJoinReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ProOrder.class);

        job.setOutputKeyClass(ProOrder.class);
        job.setOutputValueClass(NullWritable.class);


        FileInputFormat.addInputPath(job, new Path("F:\\STU\\BigData\\data\\reduceJoin\\in"));
        FileOutputFormat.setOutputPath(job, new Path("F:\\STU\\BigData\\data\\reduceJoin\\out"));

        boolean b = job.waitForCompletion(true);

        System.exit(b ? 0 : 1);

    }
}
