<%--
  Created by IntelliJ IDEA.
  User: Oksa
  Date: 28.11.2017
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <form class="navbar-form navbar-left" action="posts">
                <div class="form-group">
                    <input type="text" name="searchLine" class="form-control" placeholder="Search"
                           value="${searchLine}">
                </div>
                <button type="submit" class="btn btn-default">Search
                </button>
            </form>
            <c:if test="${!isUserLogined}">
                <li><a id="registrBtn"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
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

        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header" style="padding:35px 50px;">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 style="text-align:center; font-size:30px"><span class="glyphicon glyphicon-lock"></span>
                            Login</h4>
                    </div>
                    <div class="modal-body" style="padding:40px 50px;">
                        <div id="Error" style="color: red"></div>
                        <div class="form-group">
                            <label for="username"><span class="glyphicon glyphicon-user"></span> Username</label>
                            <input type="text" class="form-control" id="username" name="userName"
                                   placeholder="Enter login">
                        </div>
                        <div class="form-group">
                            <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
                            <input type="password" name="password" class="form-control" id="psw"
                                   placeholder="Enter password">
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" name="rememberMe" value="Y" checked>Remember me</label>
                        </div>
                        <button class="btn btn-success btn-block" id="login"><span
                                class="glyphicon glyphicon-off"></span> Login
                        </button>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger pull-left" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span> Cancel
                        </button>
                        <%--<p>Not a member? <a id="signUpRef">Sign Up</a></p>--%>
                    </div>
                </div>

            </div>
        </div>

        <div class="modal fade" id="registrationModal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header" style="padding:35px 50px;">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 style="text-align:center; font-size:30px"><span class="glyphicon glyphicon-lock"></span>
                            Registration</h4>
                    </div>
                    <div class="modal-body" style="padding:40px 50px;">
                        <div class="center-block" id="registrError" style="color: red"></div>
                        <div class="form-group">
                            <label for="username"><span class="glyphicon glyphicon-user"></span> Username</label>
                            <input type="text" class="form-control" id="newUserName" name="newUserName"
                                   placeholder="Enter login">
                        </div>
                        <div class="form-group">
                            <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
                            <input type="password" name="password" class="form-control" id="password"
                                   placeholder="Enter password">
                        </div>
                        <div class="form-group">
                            <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> Confirm password</label>
                            <input type="password" name="confirmPassword" class="form-control" id="confirmPassword"
                                   placeholder="Confirm password">
                        </div>
                        <button class="btn btn-success btn-block" id="signUp"><span
                                class="glyphicon glyphicon-off"></span> Sign up
                        </button>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger pull-left" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove"></span> Cancel
                        </button>
                        <%--<p>Have an account? <a id="loginRef">Login</a></p>--%>
                    </div>
                </div>

            </div>
        </div>
    </div>
    </div>
</nav>
<script>
    $(document).ready(function () {
        $("#myBtn").click(function () {
            $("#myModal").modal();
        });/*

        $("#loginRef").click(function () {
            $("#registrationModals").modal('hide');
            $("#myModal").modal('show');
        });*/

        $("#registrBtn").click(function () {
            $("#registrationModal").modal();
        });

        /*$("#signUpRef").click(function () {
            $("#registrationModal").modal('show');
        });*/

        $("#login").click(function () {
            var name = $('#username').val();
            var password = $('#psw').val();
            $.post("${ctx}/login",
                {
                    userName : name,
                    password : password
                },
                function (data) {
                    if (data =='') {
                        window.location.href = '${ctx}/posts';
                    } else {
                        $('#Error').text(data);
                    }

                });
        });

        $("#signUp").click(function () {
            var userName = $('#newUserName').val();
            var password = $('#password').val();
            var confirmPassword = $('#confirmPassword').val();
            $.post("${ctx}/registr",
                {
                    userName : userName,
                    password : password,
                    confirmPassword : confirmPassword
                },
                function (data) {
                    if (data =='') {
                        window.location.href = '${ctx}/posts';
                    } else {
                        $('#registrError').text(data);
                    }
                });
        });
    });
</script>

<style>
    .modal-header, .close {
        background-color: #333;
        color: white !important;
        text-align: center;
        font-size: 30px;
    }

    .modal-footer {
        background-color: #f9f9f9;
    }
</style>