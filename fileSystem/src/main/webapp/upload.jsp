<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme()+"://"+
            request.getServerName()+":"+request.getServerPort() + request.getContextPath() + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath %>">
    <title>文件上传</title>
    <style>
        div {
            margin-left: 200px;
            margin-top: 100px;
        }

        form input {
            padding: 4px;
        }
    </style>
</head>

<body>
<div>
<%--    <c:if test="${msg != null}">
        <p style="color: red">${msg}</p>
    </c:if>--%>
    <form action="fileUpload" enctype="multipart/form-data"
          method="post">
        上传路径：/<input type="text" name="filePath" style="height: 15px;width: 80px;"><br/>
        <hr/>
        上传文件：<input type="file" name="file"><br/>
        <hr/>
        文件类型：
            学习:<input type="radio" name="fileType" value="0" checked>
            娱乐:<input type="radio" name="fileType" value="1">
            视频:<input type="radio" name="fileType" value="2">
            图片:<input type="radio" name="fileType" value="3"><br/>
        <hr/>
        <input type="submit" value="提交">
    </form>
</div>

</body>
</html>