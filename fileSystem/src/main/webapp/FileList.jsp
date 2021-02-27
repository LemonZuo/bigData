<%--
  User: 左铠琦
  Date: 2019/3/26
  Time: 22:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <base href="<%=basePath %>">
</head>
<body>
    <c:forEach items="${fileInfoList}" var="file">
        <p>1</p>
    </c:forEach>
</body>
</html>
