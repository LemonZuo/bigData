package com.lemonzuo;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LemonZuo
 * @create 2019-04-17 23:06
 */
public class IP implements Writable, DBWritable {
    private  Long id;
    private String ip;
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Serialize the fields of this object to <code>out</code>.
     *
     * @param out <code>DataOuput</code> to serialize this object into.
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(id);
        out.writeUTF(ip);
        out.writeUTF(time);
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
        id = in.readLong();
        ip = in.readUTF();
        time = in.readUTF();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, ip);
        preparedStatement.setString(3, time);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong("ID");
        ip = resultSet.getString("IP");
        time = resultSet.getString("TIME");
    }
}
