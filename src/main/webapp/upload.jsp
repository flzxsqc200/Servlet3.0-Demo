<%--
  Created by IntelliJ IDEA.
  User: Lingpj
  Date: 2017/4/5
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>上传文件</title>
</head>
<body>
<h2>文件上传</h2>
<!-- action 指向 UploadServlet.java -->
<form action="/upload.html" method="post"
      enctype="multipart/form-data">
    请选择文件路径：
    <input type="file" name="myfile"/><br>
    <input type="submit" value="点击上传">
</form>
</body>
</html>