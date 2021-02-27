package com.lemonzuo;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author LemonZuo
 * @create 2019-04-05 20:46
 */
public class ProOrder implements Writable {
    private String productId;
    private String productName;
    private String orderId;
    private String orderNums;
    private String flag;

    public ProOrder() {
        productId = "";
        productName = "";
        orderId = "";
        orderNums = "";
        flag = "";
    }

    public ProOrder(String productId, String productName, String orderId, String orderNums, String flag) {
        this.productId = productId;
        this.productName = productName;
        this.orderId = orderId;
        this.orderNums = orderNums;
        this.flag = flag;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNums() {
        return orderNums;
    }

    public void setOrderNums(String orderNums) {
        this.orderNums = orderNums;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return orderId + " " + productName + " " +  orderNums;
    }

    @Override
    public void write(DataOutput out) throws IOException {
         out.writeUTF(productId);
         out.writeUTF(productName);
         out.writeUTF(orderId);
         out.writeUTF(orderNums);
         out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        productId = in.readUTF();
        productName = in.readUTF();
        orderId = in.readUTF();
        orderNums = in.readUTF();
        flag = in.readUTF();
    }
}
