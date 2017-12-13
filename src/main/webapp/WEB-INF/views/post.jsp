<%--
  Created by IntelliJ IDEA.
  User: Oksa
  Date: 29.11.2017
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap.min.css">
<html>
<head>
    <meta charset="utf-8"/>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-8">
            <h3>${post.getTittle()}</h3>
            <p>
            <p>
                <c:if test="${post.imageLink != null}">
                <img class="img-thumbnail" alt="Image" src="${post.imageLink}" style="float: left; margin-right: 5px">
                </c:if>
                ${post.getDescription()}
        </div>
        <div class="col-md-1"></div>
        <div class="col-md-1"></div>
    </div>
</div>
</body>
</html>
