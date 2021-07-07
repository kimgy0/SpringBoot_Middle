<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="alpacaCorp.shopSite.DTO.Member" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021-06-28
  Time: 오후 6:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:choose>
        <c:when test="${sessionScope.findUser==null}">
            <form method="post" action="/alpaca/mainPage">
                <input type="text" name="userid" placeholder="ID">
                <input type="password" name="password" placeholder="PW">
                <input type="submit" value="LOGIN">
            </form>
            <a href="/alpaca/join">회원가입</a>
        </c:when>
        <c:otherwise>
            <h3>${sessionScope.findUser.nickName}님 안녕하세요</h3>
            <a href="/alpaca/logout">로그아웃</a>
            <a href="/alpaca/QnA/noteList">QnA</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
