package com.lemonzuo.servlet;


import com.lemonzuo.dao.HdfsDAO;
import com.lemonzuo.daoimpl.HdfsDAOImpl;
import com.lemonzuo.util.HadoopUtil;
import com.lemonzuo.util.JDBCUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author LemonZuo
 * @create 2019-03-25 19:06
 */
public class FileUploadServlet extends HttpServlet {
    private HdfsDAO hdfsDAO = new HdfsDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // 创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 核心对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        // 设置编码集
        servletFileUpload.setHeaderEncoding("utf-8");
        try {
            String fileName = "";
            String filePath = "";
            int fileType = 0;
            InputStream inputStream = null;
            // 解析上传数据
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            // 遍历解析数据
            for (FileItem fileItem: fileItems) {
                boolean isFormField = fileItem.isFormField();
                String fieldName = fileItem.getFieldName();
                if(!isFormField) {
                    // 文件域
                    // 文件输入流
                    inputStream = fileItem.getInputStream();
                    // 文件名
                    fileName = fileItem.getName();
                } else if( isFormField && "filePath".equals(fieldName) ) {
                    // 上传路径
                    filePath = fileItem.getString();
                } else if( isFormField && "fileType".equals(fieldName) ) {
                    // 文件类型
                    fileType = Integer.valueOf(fileItem.getString());
                } else {

                }
            }
            // 处理上传路径
            filePath = filePath == null || "".equals(filePath) ? "/" : "/" + filePath + "/";
            // 执行上传
            boolean fileUploadStatus = HadoopUtil.fileUpload(filePath, fileName, inputStream);
            if( fileUploadStatus ) {
                JDBCUtil util = new JDBCUtil();
                util.getConnection();
                String sql = "INSERT INTO `tb_file`(`file_path`, `file_name`, `file_type`) VALUES (?, ?, ?);";
                hdfsDAO.insert(sql, filePath, fileName, fileType);
                // 跳转文件列表
                request.getRequestDispatcher("fileList").forward(request, response);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
