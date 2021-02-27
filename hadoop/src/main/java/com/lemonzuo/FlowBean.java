package com.lemonzuo;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author LemonZuo
 * @create 2019-04-02 9:27
 */
public class FlowBean implements Writable {
    private double upFlow;
    private double downFlow;
    private double totalFlow;

    public FlowBean(){}

    public FlowBean(double upFlow, double downFlow, double totalFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.totalFlow = totalFlow;
    }

    public double getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(double upFlow) {
        this.upFlow = upFlow;
    }

    public double getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(double downFlow) {
        this.downFlow = downFlow;
    }

    public double getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(double totalFlow) {
        this.totalFlow = totalFlow;
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + totalFlow;
    }

    /**
     * Serialize the fields of this object to <code>out</code>.
     *
     * @param out <code>DataOuput</code> to serialize this object into.
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeDouble(upFlow);
        out.writeDouble(downFlow);
        out.writeDouble(totalFlow);
    }

    /**
     * Deserialize the fields of this object from <code>in</code>.
     *
     * <p>For efficiency, implementations should attempt to re-use storage in the
     * existing object where possible.</p>
     *
     * @param in <code>DataInput</code> to deseriablize this object from.
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readDouble();
        downFlow = in.readDouble();
        totalFlow = in.readDouble();
    }
}
