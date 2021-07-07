
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>

    </title>
</head>
<body>
    <table>
        <tr>
            <td>글번호</td>
            <td>제목</td>
            <td>등록일/수정일</td>
            <td>조회수</td>
            <td>작성자</td>
        </tr>
        <c:if test="${allNote.size()==0}">
            <td></td>
            <td></td>
            <td>글이 존재하지 않습니다.</td>
            <td></td>
            <td></td>
        </c:if>
        <c:if test="${allNote.size()-1 >= 0}">
            <c:forEach var="i" begin="0" end="${allNote.size()-1}">
                <tr>
                        <%--                <c:set var="noteItem" value="${allNote.get(i)}"/>--%>
                    <td>${allNote.get(i).id}</td>
                    <td><a href=${"/alpaca/QnA/note/"+=allNote.get(i).id.toString()}>${allNote.get(i).subjectName}</a></td>
                    <td>${allNote.get(i).date}</td>
                    <td>${allNote.get(i).inquiry}</td>
                    <td>${allNote.get(i).userid}</td>
                </tr>
            </c:forEach>
        </c:if>



        <input type="button" value="write" onclick="location.href='/alpaca/QnA/register'">
        <a href="/alpaca/logout">로그아웃</a>
    </table>
</body>
</html>
