<%--
  Created by IntelliJ IDEA.
  User: Oksa
  Date: 27.11.2017
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Title</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap.min.css">
    <style>
        .textInside {
            height: 60px;
            overflow: hidden;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<form method="POST" action="${pageContext.request.contextPath}/posts">
    <div class="container">
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-8">
                <div id="postlist">
                    <c:if test="${isUserLogined}">
                        <div class="panel">
                            <div class="text-center">
                                <div class="btn-group">
                                    <a class="btn dropdown-toggle btn-primary" data-toggle="dropdown" href="#">Sort
                                        posts by<span
                                                class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${ctx}/posts?searchLine=${searchLine}&sortBy=firstMy">First my</a></li>
                                        <li><a href="${ctx}/posts?searchLine=${searchLine}&sortBy=lastMy">Last my</a></li>
                                        <li class="divider"></li>
                                        <li><a href="${ctx}/posts?searchLine=${searchLine}&sortBy=default">All by date</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:forEach items="${posts}" var="onePost">
                        <div class="panel">
                            <div class="panel-heading">
                                <div class="text-center">
                                    <div class="row">
                                        <div class="col-sm-9">
                                            <a href="${ctx}/post?id=${onePost.getId()}" style="color:black;">
                                                <h3 class="pull-left">${onePost.tittle}</h3>
                                            </a>
                                        </div>
                                        <div class="col-sm-3">
                                            <h4 class="pull-right">
                                                <small><em>${onePost.dateOfThePost.substring(0,16)}</em></small>
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel-body">
                                <c:if test="${onePost.imageLink != null}">
                                    <a href="${ctx}/post?id=${onePost.getId()}">
                                        <img class="img-thumbnail" alt="Image" src="${onePost.imageLink}">
                                    </a>
                                </c:if>
                                <div class="textInside">${onePost.description}</div>
                                <p>
                                <p>
                                    <button type="button" class="btn btn-success"
                                            onclick="window.location.href='${ctx}/post?id=${onePost.getId()}'">Read more
                                    </button>
                                    <c:if test="${isUserLogined && loginedUser.getId().equals(onePost.getUserCreator().getId())}">
                                    <button type="button" class="btn btn-primary"
                                            onclick="window.location.href='${ctx}/editPost?id=${onePost.getId()}'">Edit
                                    </button>
                                    </c:if>
                            </div>

                            <div class="panel-footer">
                                <span class="label label-primary">Category:</span>
                                <c:forEach items="${onePost.categories}" var="oneCategory">
                                    <span class="label label-default">${oneCategory.name}</span>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="text-center">
                    <ul class="pagination">
                        <c:if test="${numberOfPages>1}">
                            <li><c:if test="${currentPage>1}"><a
                                    href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage-1}">&laquo;</a></c:if>
                            </li>
                            <c:if test="${numberOfPages < 8}">
                                <c:forEach var="i" begin="1" end="${numberOfPages}">
                                    <li
                                            <c:if test="${currentPage == i}">class="active"</c:if> >
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${i}">${i}</a>
                                    </li>
                                </c:forEach>
                            </c:if>

                            <c:if test="${numberOfPages >= 8}">
                                <li
                                        <c:if test="${currentPage == 1}">class="active"</c:if> >
                                    <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=1">1</a>
                                </li>

                                <c:if test="${(currentPage == 1) || (currentPage == 2) || (currentPage == 3) || (currentPage == 4)}">
                                    <li
                                            <c:if test="${currentPage == 2}">class="active"</c:if> >
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=2">2</a>
                                    </li>
                                    <li
                                            <c:if test="${currentPage == 3}">class="active"</c:if> >
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=3">3</a>
                                    </li>
                                    <li
                                            <c:if test="${currentPage == 4}">class="active"</c:if> >
                                        <a href="posts?searchLine=${searchLine}&sortBy=${sortBy}&page=4">4</a>
                                    </li>
                                    <li
                                            <c:if test="${currentPage == 5}">class="active"</c:if> >
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=5">5</a>
                                    </li>
                                    <li><span>...</span></li>
                                </c:if>

                                <c:if test="${(currentPage > 4) && ((currentPage + 4) <= numberOfPages)}">
                                    <li><span>...</span></li>
                                    <li>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage-1}">${currentPage - 1}</a>
                                    </li>
                                    <li class="active">
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage}">${currentPage}</a>
                                    </li>
                                    <li>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage+1}">${currentPage + 1}</a>
                                    </li>
                                    <li><span>...</span></li>
                                </c:if>

                                <c:if test="${(currentPage > 4) && ((currentPage + 4) > numberOfPages)}">
                                    <li><span>...</span></li>
                                    <li <c:if test="${currentPage == numberOfPages - 4}">class="active"</c:if>>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 4}">${numberOfPages - 4}</a>
                                    </li>
                                    <li <c:if test="${currentPage == numberOfPages - 3}">class="active"</c:if>>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 3}">${numberOfPages - 3}</a>
                                    </li>
                                    <li <c:if test="${currentPage == numberOfPages - 2}">class="active"</c:if>>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 2}">${numberOfPages - 2}</a>
                                    </li>
                                    <li <c:if test="${currentPage == numberOfPages - 1}">class="active"</c:if>>
                                        <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 1}">${numberOfPages - 1}</a>
                                    </li>
                                </c:if>

                                <li
                                        <c:if test="${currentPage == numberOfPages}">class="active"</c:if> >
                                    <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages}">${numberOfPages}</a>
                                </li>
                            </c:if>
                            <li><c:if test="${currentPage<numberOfPages}">
                                <a href="${ctx}/posts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage+1}">&raquo;</a>
                            </c:if>
                            </li>
                        </c:if>
                    </ul>
                </div>

            </div>
            <div class="col-md-1"></div>
            <div class="col-md-1">
            </div>
            <div class="col-md-1">
            </div>
        </div>
    </div>
</form>
</body>
</html>
