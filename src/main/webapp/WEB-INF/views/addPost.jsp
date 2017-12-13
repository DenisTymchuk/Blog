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
    <script type="text/javascript" src="${ctx}/../../res/addPost.js"></script>

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
            <h1>Create new post</h1>
            <form action="${pageContext.request.contextPath}/newPost" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="title">Tittle <span class="require">*</span></label>
                    <input type="text" class="form-control" name="tittle">
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea rows="14" class="form-control" name="description"></textarea>
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
                               value="<c:if test="${edit}">${post.getLinkImage()}</c:if>">
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
                    <div class="btn-group">
                        <span class="button-checkbox">
                            <button type="button" class="btn" data-color="success">Published</button>
                            <input type="checkbox" class="hidden" name="published" value="Y" checked/>
                         </span>
                    </div>
                    <select id="dropdownselect" multiple="multiple" name="multipleSelect">
                        <c:forEach items="${categoryList}" var="oneCategory">
                            <option value="${oneCategory.name}">${oneCategory.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">
                        Create
                    </button>
                    <a href="${ctx}/posts">
                        <button type="button" class="btn btn-default">
                            Cancel
                        </button>
                    </a>
                </div>

            </form>
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
</script>
</body>
</html>


