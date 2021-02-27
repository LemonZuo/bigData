package com.lemonzuo;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author LemonZuo
 * @create 2019-04-24 11:19
 */
public class Lower extends UDF {
    public String evaluate(final String s){
        if(s == null){
            return null;
        }
        return s.toLowerCase();
    }
}
