<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2021-07-06
  Time: 오후 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now()); %>
    <c:if test="${sessionScope.findUser == null}">
        <script type="text/javascript">
            alert('로그인 하쇼');
            window.location.replace('/alpaca/login');
        </script>
    </c:if>
    <input type="button" value="List" onclick="location.href='/alpaca/QnA/noteList'">
    <table>
        <tr>
            <td>글번호 </td>
            <td>${note.id}</td>
            <td>제목 </td>
            <td>${note.subjectName}</td>
            <td>조회 수 </td>
            <td>${note.inquiry}</td>
        </tr>
        <tr>
            <td>등록일/수정일</td>
            <td>${note.date}</td>
            <td></td>
            <td></td>
            <td>작성자</td>
            <td>${note.userid}</td>
        </tr>
        <tr>
            <td>첨부파일 </td>
            <c:choose>
                <c:when test="${note.file != null}">
                    <td><a href="${note.file.fs_filePath}">${note.file.fileName}(${note.file.fileSize})</a></td>
                </c:when>
                <c:otherwise>
                    <td>파일 없음</td>
                </c:otherwise>
            </c:choose>

        </tr>
        <tr>
            <td>${note.content}</td>
        </tr>
        <tr>
            <td>${sessionScope.findUser.userid} </td>
            <td>
                <form method="post" action=${"/alpaca/QnA/note/"+= note.id.toString()+="/registerComment"}>
                    <input type="hidden" name="commentMan" value=${sessionScope.findUser.userid}>
                    <textarea name="commentContent" cols="30" rows="4"></textarea>
                    <input type="submit" value="submit">
                </form>
            </td>
        </tr>

            <c:if test="${sessionScope.findUser.userid == note.userid}">
                <tr>
                    <input type="button" value="modify" onclick="location.href='${"/alpaca/QnA/"+= note.id.toString()+="/modify"}'">
                    <input type="button" value="delete" onclick="location.href='${"/alpaca/QnA/"+= note.id.toString()+="/delete"}'">
                </tr>
            </c:if>
        <tr>
            <td>댓글 작성자    </td>
            <td>댓글 내용</td>
            <td>댓글 작성일자</td>
        </tr>
                <c:if test="${comment != null}">
                    <c:choose>
                        <c:when test="${comment.size()-1>=0}">
                            <c:forEach var="i" begin="0" end="${comment.size()-1}">
                                <c:if test="${comment.get(i).commentClass==0}">
                                    <tr>
                                        <td>${comment.get(i).commentMan}</td>
                                        <td>${comment.get(i).commentContent}</td>
                                        <td>${comment.get(i).commentDate}</td>
                                        <c:if test="${comment.get(i).commentMan==sessionScope.findUser.userid}">
                                            <td>
                                                <input type="button" value="modify">
                                                <input type="button" value="delete">
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <td>-</td>
                            <td>댓글 없음</td>
                            <td>댓글 시간 없음</td>
                        </c:otherwise>
                    </c:choose>
                </c:if>
    </table>
</body>
</html>
