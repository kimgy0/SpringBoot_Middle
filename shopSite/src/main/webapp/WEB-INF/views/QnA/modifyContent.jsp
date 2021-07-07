<%@ page import="java.net.URLEncoder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021-07-07
  Time: 오후 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${sessionScope.findUser == null}">
        <script type="text/javascript">
            alert('로그인 하쇼');
            window.location.replace('/alpaca/login');
        </script>
    </c:if>

    <input type="button" value="List" onclick="location.href='/alpaca/QnA/noteList'">
    <form action=${"/alpaca/QnA/note/"+= note.id.toString()+="/modified"} method="post">
        <input type="text" name="subjectName" value="${note.subjectName}" required><br>
        <h4>파일은 수정이 불가능합니다.</h4>
        <c:choose>
            <c:when test="${note.file!=null}">
                파일명 : ${note.file.fileName}<br>
            </c:when>
            <c:otherwise>
                파일명 : 파일 없음<br>
            </c:otherwise>
        </c:choose>
        <textarea name="content" cols="30" rows="10">${note.content}</textarea><br>
        <input type="hidden" name="requestNote" value="${note}">
        <input type="submit" value="modify">
    </form>
</body>
</html>
