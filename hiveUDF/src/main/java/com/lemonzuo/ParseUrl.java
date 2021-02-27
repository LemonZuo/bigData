package com.lemonzuo;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author LemonZuo
 * @create 2019-05-09 17:31
 *
 */
public class ParseUrl extends UDF {
    public String evaluate(final String s) {
        if(s == null) {
            return null;
        } else {
            return s.replace("http://", "");
        }
    }
}
