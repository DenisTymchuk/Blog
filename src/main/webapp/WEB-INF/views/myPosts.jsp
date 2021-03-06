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
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .textInside {
            height: 60px;
            overflow: hidden;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${ctx}/posts">MyBlog</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="${ctx}/posts">Home</a></li>
            <c:if test="${isUserLogined}">
                <li><a href="${ctx}/newPost">Create post</a></li>
                <li><a href="${ctx}/myPosts">My posts</a></li>
            </c:if>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <form class="navbar-form navbar-left" action="myPosts">
                <div class="form-group">
                    <input type="text" name="searchLine" class="form-control" placeholder="Search"
                           value="${searchLine}">
                </div>
                <button type="submit" class="btn btn-default">Search
                </button>
            </form>
            <c:if test="${!isUserLogined}">
                <li><a href="${ctx}/registr"><span class="glyphicon glyphicon-user"></span> Register</a></li>
                <li><a id="myBtn"><span class="glyphicon glyphicon-log-in"></span>
                    Login</a>
                </li>
            </c:if>
            <c:if test="${isUserLogined}">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" style="color: white"
                       href="#"><b>Hello, ${nameLoginedUser}</b>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/logout">Log out</a></li>
                    </ul>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
<form method="POST" action="${pageContext.request.contextPath}/myPosts">
    <div class="container">
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-8">
                <div id="postlist">
                    <div class="panel">
                        <div class="text-center">
                            <div class="btn-group">
                                <a class="btn dropdown-toggle btn-primary" data-toggle="dropdown" href="#">Sort my posts
                                    by<span
                                            class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=firstPublished">First
                                        published</a></li>
                                    <li><a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=firstNotPublished">First
                                        unpublished</a></li>
                                    <li class="divider"></li>
                                    <li><a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=default">All by date</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${myposts}" var="onePost">
                        <div class="panel" <c:if
                                test="${!onePost.isPublished()}"> style="background-color: #d9534f30;" </c:if>>
                            <div class="panel-heading">
                                <div class="text-center">
                                    <div class="row">
                                        <div class="col-sm-9">
                                            <h3 class="pull-left">${onePost.tittle}</h3>
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
                                        <img  class="img-thumbnail" alt="Image" src="${onePost.imageLink}">
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
                                <c:if test="${!onePost.isPublished()}">
                                    <span class="label label-danger pull-right">Unpublished</span>
                                </c:if>
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
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>

                        <c:if test="${numberOfPages >= 8}">
                            <li
                                    <c:if test="${currentPage == 1}">class="active"</c:if> >
                                <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=1">1</a>
                            </li>

                            <c:if test="${(currentPage == 1) || (currentPage == 2) || (currentPage == 3) || (currentPage == 4)}">
                                <li
                                        <c:if test="${currentPage == 2}">class="active"</c:if> >
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=2">2</a>
                                </li>
                                <li
                                        <c:if test="${currentPage == 3}">class="active"</c:if> >
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=3">3</a>
                                </li>
                                <li
                                        <c:if test="${currentPage == 4}">class="active"</c:if> >
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=4">4</a>
                                </li>
                                <li
                                        <c:if test="${currentPage == 5}">class="active"</c:if> >
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=5">5</a>
                                </li>
                                <li><span>...</span></li>
                            </c:if>

                            <c:if test="${(currentPage > 4) && ((currentPage + 4) <= numberOfPages)}">
                                <li><span>...</span></li>
                                <li>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage-1}">${currentPage - 1}</a>
                                </li>
                                <li class="active">
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage}">${currentPage}</a>
                                </li>
                                <li>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${currentPage+1}">${currentPage + 1}</a>
                                </li>
                                <li><span>...</span></li>
                            </c:if>

                            <c:if test="${(currentPage > 4) && ((currentPage + 4) > numberOfPages)}">
                                <li><span>...</span></li>
                                <li <c:if test="${currentPage == numberOfPages - 4}">class="active"</c:if>>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 4}">${numberOfPages - 4}</a>
                                </li>
                                <li <c:if test="${currentPage == numberOfPages - 3}">class="active"</c:if>>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 3}">${numberOfPages - 3}</a>
                                </li>
                                <li <c:if test="${currentPage == numberOfPages - 2}">class="active"</c:if>>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 2}">${numberOfPages - 2}</a>
                                </li>
                                <li <c:if test="${currentPage == numberOfPages - 1}">class="active"</c:if>>
                                    <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages - 1}">${numberOfPages - 1}</a>
                                </li>
                            </c:if>

                            <li
                                    <c:if test="${currentPage == numberOfPages}">class="active"</c:if> >
                                <a href="${ctx}/myPosts?searchLine=${searchLine}&sortBy=${sortBy}&page=${numberOfPages}">${numberOfPages}</a>
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
