<%@ page import="alpacaCorp.shopSite.DTO.Member" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021-06-30
  Time: 오후 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Member findUser = (Member) request.getSession().getAttribute("findUser");
    if (findUser==null){

%>
    <script type="text/javascript">
        alert('error');
        window.location.replace('/alpaca/login');
    </script>
<%
}else{
%>
    <input type="button" value="List" onclick="location.href='/alpaca/QnA/noteList'">
    <form action="/alpaca/QnA/processRegister" method="post" enctype="multipart/form-data">
        <input type="text" name="subjectName" placeholder="subject" required><br>
        <input type="file" name="filePath" size="60"><br>
        <textarea name="content" cols="30" rows="10"></textarea><br>
        <input type="submit" value="register">
    </form>
<%
    }
%>
</body>
</html>
