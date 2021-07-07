<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021-06-29
  Time: 오후 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form method="post" action="/alpaca/join/process">
        <input type="text" name="nickName" placeholder="Name" required>
        <input type="text" name="userid" placeholder="id" required>
        <input type="password" name="password" placeholder="pw" required>
        <input type="password" name="repassword" placeholder="rePw">
        <input type="submit" value="회원 가입">
        <input type="reset" value="취소">
    </form>
</body>
</html>
