<%--
  Created by IntelliJ IDEA.
  User: Oksa
  Date: 29.11.2017
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
<head>
    <meta charset="utf-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/../../res/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../res/addPost.js"></script>

    <script type="text/javascript" src="../../res/multiselect/js/bootstrap-multiselect.js"></script>
    <link rel="stylesheet" href="${ctx}/../../res/multiselect/css/bootstrap-multiselect.css" type="text/css"/>
    <title>Title</title>
    <style>
        .require {
            color: #666;
        }

        label small {
            color: #999;
            font-weight: normal;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-8">
            <h1>Edit post</h1>
            <form action="${pageContext.request.contextPath}/editPost" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="previousPage" value="${previousPage}">
                <input type="hidden" name="id" value="${post.id}">
                <div class="form-group">
                    <label for="title">Tittle <span class="require">*</span></label>
                    <input type="text" class="form-control" name="tittle" value="${post.tittle}">
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea rows="14" class="form-control" name="description">${post.description}</textarea>
                </div>

                <div class="form-group">
                        <label>Pick image</label>
                        <div class="input-group">
                            <label class="input-group-btn">
                        <span class="btn btn-primary">Browse&hellip;
                            <input name="file" type="file" style="display: none;" multiple>
                        </span>
                            </label>

                            <input type="text" class="form-control" readonly
                                   value="${post.imageLink.substring(12)}">
                        </div>
                    <script type="text/javascript">
                        $(function () {
                            // We can attach the `fileselect` event to all file inputs on the page
                            $(document).on('change', ':file', function () {
                                var input = $(this),
                                    numFiles = input.get(0).files ? input.get(0).files.length : 1,
                                    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                                input.trigger('fileselect', [numFiles, label]);
                            });
                            // We can watch for our custom `fileselect` event like this
                            $(document).ready(function () {
                                $(':file').on('fileselect', function (event, numFiles, label) {
                                    var input = $(this).parents('.input-group').find(':text'),
                                        log = numFiles > 1 ? numFiles + ' files selected' : label;
                                    if (input.length) {
                                        input.val(log);
                                    } else {
                                        if (log) alert(log);
                                    }
                                });
                            });
                        });
                    </script>
                </div>

                <div class="form-group">
                    <div class="form-group">
                        <div class="btn-group">
                        <span class="button-checkbox">
                            <button type="button" class="btn" data-color="success">Published</button>
                            <input type="checkbox" class="hidden" name="published" checked/>
                         </span>
                        </div>
                        <select id="dropdownselect" multiple="multiple" name="multipleSelect">
                            <c:forEach items="${categoryList}" var="oneCategory">
                                <option value="${oneCategory.name}"
                                        <c:forEach items="${post.categories}" var="categoryInPost">
                                            <c:if test="${categoryInPost.name eq oneCategory.name}">
                                                selected
                                            </c:if>
                                        </c:forEach>>${oneCategory.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary" href="${ctx}/editPost">
                            Confirm
                        </button>
                        <a href="${ctx}/${previousPage}">
                        <button type="button" class="btn btn-default">
                            Cancel
                        </button>
                        </a>
                        <c:if test="${isUserLogined && loginedUser.getId().equals(post.getUserCreator().getId())}">
                            <a id="deleteBtn">
                                <button type="button" class="btn btn-danger">Delete post
                                </button>
                            </a>
                        </c:if>
                    </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModalDelete" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="text-center">
                <div class="modal-body">
                    <p>
                    <h4 style="color: red;">Are you sure that you want to delete post?</h4></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="delete">Yes</button>
                <button type="button" class="btn btn-success" data-dismiss="modal">No</button>
            </div>
        </div>
    </div>
</div>
<div class="col-md-1"></div>
<div class="col-md-1"></div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#dropdownselect').multiselect({
            nonSelectedText: 'Select category'
        });
    });

    $(document).ready(function () {
        $("#deleteBtn").click(function () {
            $("#myModalDelete").modal();
        });
    });

    $("#delete").click(function () {
        $.get("/deletePost?id=${post.id}",
            function (data) {
                window.location = '/posts';
            });
    });
</script>
</body>
</html>
