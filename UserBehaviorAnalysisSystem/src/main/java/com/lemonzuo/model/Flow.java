package com.lemonzuo.model;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author LemonZuo
 * @create 2019-07-22 21:46
 */
public class Flow implements Writable, WritableComparable<Flow> {
    private String phone;
    private Double upFlow;
    private Double downFlow;
    private Double totalFlow;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone.replace("+", "");
    }

    public Double getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Double upFlow) {
        this.upFlow = upFlow;
    }

    public Double getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Double downFlow) {
        this.downFlow = downFlow;
    }

    public Double getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(Double totalFlow) {
        this.totalFlow = totalFlow;
    }

    @Override
    public String toString() {
        return phone + "," + upFlow + "," + downFlow + "," + totalFlow;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phone);
        out.writeDouble(upFlow);
        out.writeDouble(downFlow);
        out.writeDouble(totalFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        phone = in.readUTF();
        upFlow = in.readDouble();
        downFlow = in.readDouble();
        totalFlow = in.readDouble();
    }

    @Override
    public int compareTo(Flow buffer) {
        int flag = 0;
        if (buffer.getTotalFlow().doubleValue() > this.getTotalFlow().doubleValue()) {
            flag = -1;
        } else if(buffer.getTotalFlow().doubleValue() < this.getTotalFlow().doubleValue()) {
            flag = 1;
        } else {
            flag = 0;
        }
        return flag;
    }
}
