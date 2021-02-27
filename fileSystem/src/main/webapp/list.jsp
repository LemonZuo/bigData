<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme()+"://"+
            request.getServerName()+":"+request.getServerPort() + request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath %>">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet"/>
    <link rel="stylesheet" href="css/Site.css"/>
    <link rel="stylesheet" href="css/zy.all.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" href="css/amazeui.min.css"/>
    <link rel="stylesheet" href="css/admin.css"/>
</head>
<body>
<div class="dvcontent">

    <div>
        <!--tab start-->
        <div class="tabs">
            <div class="bd">
                <ul style="display: block;padding: 20px;">
                    <li>
                        <!--分页显示角色信息 start-->
                        <div id="dv1">
                            <table class="table" id="tbRecord">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>文件名</th>
                                    <th>文件路径</th>
                                    <th>文件类型</th>
                                    <th>下载</th>
                                    <th>删除</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${fileInfoList}" var="file">
                                    <tr>
                                        <td>${file.fileId}</td>
                                        <td>${file.fileName}</td>
                                        <td>${file.filePath}</td>
                                        <td>${file.fileType}</td>
                                        <td class="delete">
                                            <button>
                                                <i class="icon-trash bigger-120"></i>
                                                <a href="download?filePath=${file.filePath}&fileName=${file.fileName}">下载</a>
                                            </button>
                                        </td>
                                        <td class="delete">
                                            <button onclick="btn_delete(1)"><i class="icon-trash bigger-120"></i>
                                                <a href="filedelete?fileId=${file.fileId}&filePath=${file.filePath}&fileName=${file.fileName}">删除</a>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                        </div>
                        <!--分页显示角色信息 end-->
                    </li>
                </ul>
            </div>
        </div>
        <!--tab end-->

    </div>


    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="js/plugs/Jqueryplugs.js" type="text/javascript"></script>
    <script src="js/_layout.js"></script>
    <script src="js/plugs/jquery.SuperSlide.source.js"></script>
    <script>
        var num = 1;
        $(function () {

            $(".tabs").slide({trigger: "click"});

        });

        // var btn_delete = function (id) {
        //     $.jq_Confirm({
        //         message: "您确定要删除吗?",
        //         btnOkClick: function () {
        //             $.ajax({
        //                 type: "post",
        //                 url: "file?method=delete",
        //                 data: {id: id},
        //                 success: function (data) {
        //                     if (data > 0) {
        //                         $.jq_Alert({
        //                             message: "删除成功",
        //                             btnOkClick: function () {
        //                                 page1();
        //                             }
        //                         });
        //                     }
        //                 }
        //             });
        //         }
        //     });
        // }
    </script>

</div>
</body>

</html>