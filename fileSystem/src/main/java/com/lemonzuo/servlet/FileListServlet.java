package com.lemonzuo.servlet;

import com.lemonzuo.dao.HdfsDAO;
import com.lemonzuo.daoimpl.HdfsDAOImpl;
import com.lemonzuo.model.FileInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LemonZuo
 * @create 2019-03-26 10:05
 */
public class FileListServlet extends HttpServlet {
    private HdfsDAO hdfsDAO = new HdfsDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<FileInfo> fileInfoList;
        try {
            fileInfoList = hdfsDAO.findAllFileInfo();
            if( fileInfoList.size() != 0 ) {
                request.setAttribute("fileInfoList", fileInfoList);
                // 跳转页面
                request.getRequestDispatcher("FileList.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
