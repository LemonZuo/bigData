package com.lemonzuo.daoimpl;

import com.lemonzuo.dao.HdfsDAO;
import com.lemonzuo.model.FileInfo;
import com.lemonzuo.util.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LemonZuo
 * @create 2019-03-26 11:20
 */
public class HdfsDAOImpl implements HdfsDAO {
    private JDBCUtil util;
    private ResultSet result;
    /**
     * 保存文件上传信息
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param fileType 文件类型
     * @return
     */
    @Override
    public boolean insert(String sql, String fileName, String filePath, int fileType) {
        util = new JDBCUtil();
        util.getConnection();
        int len = util.executeUpdate(sql, fileName, filePath, fileType);
        util.close();
        if( len == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询全部文件信息
     *
     * @return
     */
    @Override
    public List<FileInfo> findAllFileInfo() throws SQLException {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
        FileInfo fileInfo;
        util = new JDBCUtil();
        util.getConnection();
        String sql = "SELECT `file_id`, `file_path`, `file_name`, `file_type` FROM `tb_file`;";
        result = util.executeQuery(sql);
        while (result.next()) {
            fileInfo = new FileInfo();
            fileInfo.setFileId(result.getInt("file_id"));
            fileInfo.setFileName(result.getString("file_name"));
            fileInfo.setFilePath(result.getString("file_path"));
            fileInfo.setFileType(result.getInt("file_type"));
            fileInfoList.add(fileInfo);
        }
        util.close();
        return fileInfoList;
    }
}
