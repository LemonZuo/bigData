package com.lemonzuo.dao;

import com.lemonzuo.model.FileInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author LemonZuo
 * @create 2019-03-26 10:59
 */
public interface HdfsDAO {
    /**
     * 保存文件上传信息
     * @param sql sql语句
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param fileType 文件类型
     * @return
     */
    public boolean insert(String sql, String fileName, String filePath, int fileType);

    /**
     * 查询全部文件信息
     * @return
     */
    public List<FileInfo> findAllFileInfo() throws SQLException;
}
