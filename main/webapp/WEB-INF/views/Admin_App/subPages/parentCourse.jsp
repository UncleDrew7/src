<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/12/2017
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 03:26
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<html lang="en">

<head>
    <title>Parent Course</title>

    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>

<%--<!--Content Begins  -->--%>
<div class="content">

    <br/>
    <h3 class="pageTitle">Manage Parent Courses</h3>

    <%--<!--USER STATISTICS BOX -->--%>
    <div class="courseStatsBoxContainer">

        <c:choose>
            <c:when test="${currentUserRole eq 'teacher'}">
                <div class="dash-item__content">
                    <ul class="quiz-results">
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">0</span>
                            <div class="quiz-results__label"><spring:message code="extra.courseEnrollmentRequests" /></div>
                        </li>
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${activeTeacherCoursesCount}</span>
                            <div class="quiz-results__label"><spring:message code="extra.activeCourses" /></div>
                        </li>
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${totalTeacherCoursesCount}</span>
                            <div class="quiz-results__label"><spring:message code="manageCourse.totalCourses" /></div>
                        </li>

                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div class="dash-item__content">
                    <ul class="quiz-results">
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${totalCourses}</span>
                            <div class="quiz-results__label">Total Parent Courses</div>
                        </li>
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${totalGenralCourse}</span>
                            <div class="quiz-results__label">Total Child Courses</div>
                        </li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>


    </div>
    <%--/USER STATISTICS BOX--%>



    <%--<!--MAIN CARD CONTAINER -->--%>
    <div class=" card mainCardContainer">

        <div class="majorFilter">
            <label class="majorLable">Major:</label>&nbsp;
            <select class="selectMajor" id="sel1">
                <option>Default (All)</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
            </select>
        </div>

        <div id="menu4" class="tab-pane fade in active ">
            <h4 class="pageTitle">Parent Course</h4>
            <%--
            --%>
            <%--!TABLE--%>
            <div class="table-responsive">

                <div class="col-sm-4">

                    <div class="searchBox">
                        <div class="input-group">
                            <input style="width:100%; float: right; height: 30px" type="text" class="form-control search" aria-label="...">
                            <div class="input-group-btn">
                                <button style="height: 30px" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                </ul>
                            </div><!-- /btn-group -->
                        </div><!-- /input-group -->
                    </div>
                    <div class="majorParentCrsBox">
                        <div style="background: #2971ac" class="majorParentCrsBoxHeaderBox">
                            <h4   class="majorParentCrsBoxHeaderText">Parent Courses</h4>
                        </div>
                        <div class="list-group">
                            <c:forEach var = "pcList" items="${parentCourseList}" varStatus="index">
                                <div class="flex-container">
                                    <a href="#" style="width:90%; height: 42px" class="list-group-item">${pcList.courseName}<span class="badge">0</span></a>
                                    <div style="width: 10%" class="input-group-btn">
                                        <a  style="width: 100%; height: 42px; border-radius: 0 ; border-right-width: 0" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="caret"></span></a>
                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li><a href="#">View Parent Course</a></li>
                                            <li><a href="#">Edit Parent Course</a></li>
                                            <li><a href="#">Create Child Course</a></li>
                                            <li role="separator" class="divider"></li>
                                            <li><a href="#">Disable</a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                </div>
                            </c:forEach>


                            <%--<a href="#" class="list-group-item">Porta ac consectetur ac</a>--%>
                            <%--<a href="#" class="list-group-item">Vestibulum at eros</a>--%>
                        </div>


                    </div>

                </div>

                <div class="col-sm-8">
                    <table id="majorTable" class="table table-bordered table-hover">

                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Course Name</th>
                            <th>Teacher</th>
                            <th>Semester</th>
                            <th>Enrolled Students</th>
                            <th>Action</th>
                        </tr>
                        </thead>

                        <tbody>

                        <%--<c:forEach var = "sysCourse" items="${courseTableList}" varStatus="index">--%>
                            <tr>
                                <td>${1}</td>
                                <td><c:out value="${'LISTENING-NEW9011-2'}"/></td>
                                <td><c:out value="${'Ellia Martel'}"/></td>
                                <td><c:out value="${'2017-2018-1'}"/></td>
                                <td><c:out value="${40}"/></td>
                                    <td>
                                    <div class="dropdown">
                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                    <span class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                    <li><a href="${url}/admin/semester/list/${sysSemester.semesterId}"><spring:message code="extra.courseList" />&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                    <li><a href="${url}/admin/semester/update/${sysSemester.semesterId}"><spring:message code="coursePage.edit" />&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                    <%--<li><a href="#">Delete&nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>--%>
                                    </ul>
                                    </div>

                                    </td>
                            </tr>
                        <%--</c:forEach>--%>

                        </tbody>

                    </table>
                </div>

            </div>
            <%--<--END OF TABLE-->--%>
        </div>
            <%--<div class="table-responsive">--%>
                <%--<div class="col-sm-8">--%>
                    <%--<table id="majorTable" class="table table-bordered table-hover">--%>

                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th>#</th>--%>
                            <%--<th>Major Short Code</th>--%>
                            <%--<th>Major Name</th>--%>
                            <%--<th>No Students</th>--%>
                            <%--<th>No Courses</th>--%>
                            <%--&lt;%&ndash;<th>No Exams</th>&ndash;%&gt;--%>
                        <%--</tr>--%>
                        <%--</thead>--%>

                        <%--<tbody>--%>

                        <%--<c:forEach var = "sysCourse" items="${courseTableList}" varStatus="index">--%>
                            <%--<tr>--%>
                                <%--<td>${index.count}</td>--%>
                                <%--<td><c:out value="${sysCourse.courseShortName}"/></td>--%>
                                <%--<td><c:out value="${sysCourse.courseName}"/></td>--%>
                                <%--<td><c:out value="${sysCourse.totalEnrollments}"/></td>--%>
                                <%--<td><c:out value="${sysCourse.totalEnrollments}"/></td>--%>
                                    <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<div class="dropdown">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<span class="caret"></span></button>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<ul class="dropdown-menu">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<li><a href="${url}/admin/semester/list/${sysSemester.semesterId}"><spring:message code="extra.courseList" />&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<li><a href="${url}/admin/semester/update/${sysSemester.semesterId}"><spring:message code="coursePage.edit" />&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;<li><a href="#">Delete&nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                            <%--</tr>--%>
                        <%--</c:forEach>--%>

                        <%--</tbody>--%>

                    <%--</table>--%>
                <%--</div>--%>

                <%--<div class="col-sm-4">--%>

                    <%--<div class="searchBox">--%>
                        <%--<div class="input-group">--%>
                            <%--<input style="width:100%; float: right; height: 30px" type="text" class="form-control search" aria-label="...">--%>
                            <%--<div class="input-group-btn">--%>
                                <%--<button style="height: 30px" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>--%>
                                <%--<ul class="dropdown-menu dropdown-menu-right">--%>
                                    <%--<li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>--%>
                                <%--</ul>--%>
                            <%--</div><!-- /btn-group -->--%>
                        <%--</div><!-- /input-group -->--%>
                    <%--</div>--%>
                    <%--<div class="majorParentCrsBox">--%>
                        <%--<div class="majorParentCrsBoxHeaderBox">--%>
                            <%--<h4  class="majorParentCrsBoxHeaderText">Parent Courses</h4>--%>
                        <%--</div>--%>
                        <%--<div class="list-group">--%>
                            <%--<div class="flex-container">--%>
                                <%--<a href="#" style="width:90%; height: 42px" class="list-group-item">Morbi leo risus  <span class="badge">4</span></a>--%>
                                <%--<div style="width: 10%" class="input-group-btn">--%>
                                    <%--<a  style="width: 100%; height: 42px; border-radius: 0 ; border-right-width: 0" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="caret"></span></a>--%>
                                    <%--<ul class="dropdown-menu dropdown-menu-right">--%>
                                        <%--<li><a href="#">View Parent Course</a></li>--%>
                                        <%--<li><a href="#">Edit Parent Course</a></li>--%>
                                        <%--<li><a href="#">Create Child Course</a></li>--%>
                                        <%--<li role="separator" class="divider"></li>--%>
                                        <%--<li><a href="#">Disable</a></li>--%>
                                    <%--</ul>--%>
                                <%--</div><!-- /btn-group -->--%>
                            <%--</div>--%>

                            <%--&lt;%&ndash;<a href="#" class="list-group-item">Porta ac consectetur ac</a>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<a href="#" class="list-group-item">Vestibulum at eros</a>&ndash;%&gt;--%>
                        <%--</div>--%>


                    <%--</div>--%>

                <%--</div>--%>

            <%--</div>--%>
            <%--&lt;%&ndash;<--END OF TABLE-->&ndash;%&gt;--%>
        <%--</div>--%>


    </div>
    <%--/END OF MAIN CARD CONTAINER --%>

    <%--COURSE FILE IMPOORT MODEL FORM --%>
    <div class="modal fade" id="importCourseExcel">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center"  class="modal-title">Import course excel file 2007:</h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form:form action="${url}/manage-courses/add-course-excel" method="post"
                               enctype="multipart/form-data">
                        <div class="form-group">
                                <%--<label for="sel1">Select Major of Courses</label>--%>
                            <select class="form-control" name="major" required autocomplete="off">
                                <option><i>(Select Course Major)</i></option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                            </select>
                        </div>
                        <input name="courseExcelListFile" required type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button  type="submit" class="btn btn-primary btn-block">Import Excel File </button>
                        </div>
                    </form:form>
                    <%--FILE FORM --%>

                </div>

            </div>
        </div>
    </div>
    <%--COURSE FILE IMPOORT MODEL FORM--%>
    <%--SEMESTER FILE IMPOORT MODEL FORM --%>
    <div class="modal fade" id="importSemesterExcel">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center"  class="modal-title"> Import Semester list excel file 2007</h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form:form action="${url}/admin/manage-users/add-class-excel" method="post"
                               enctype="multipart/form-data">
                        <input name="classExcelListFile" required type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button type="submit" value="Import Excel File" class="btn btn-primary btn-block">Import Excel File</button>
                        </div>
                    </form:form>
                    <%--FILE FORM --%>

                </div>

            </div>
        </div>
    </div>
    <%--SEMESTER FILE IMPOORT MODEL FORM--%>

</div>
<!--Content Ends  -->

<%--CLASS FILE IMPOORT MODEL FORM --%>
<div class="modal fade" id="downloadChildCourseExcel">
    <div style="width: 450px" class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times</button>
                <h3 style="text-align: center" class="modal-title"> Download Course Excel Template</h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <%--FILE FORM --%>
                <form:form  id="classFile" action="${url}/admin/manage-users/add-class-excel" method="post"
                            enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="sel1">Select Major of Courses</label>
                        <select class="form-control" name="major" required autocomplete="off">
                            <option><i>(Select Course Major)</i></option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="sel1">Select Semester of Courses</label>
                        <select class="form-control" name="semester" required autocomplete="off">
                            <option><i>(Select Semester )</i></option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" value="Import Excel File" class="btn btn-primary btn-block">Download</button>
                    </div>
                </form:form>
                <%--FILE FORM --%>

            </div>

        </div>
    </div>
</div>
<%--CLASS FILE IMPOORT MODEL FORM--%>

<script>
    $(document).ready(function() {
        $('#courseTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
        $('#semesterTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
        $('#majorTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
    } );

    function deleteCourse(courseId) {
        var r = confirm("Are you sure you want to delete this Course ?");
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-courses/delete-course/'+courseId,
                type:'post',
                data:$(this).serialize(),
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(){
                    alert("error");
                }
            });
        }else{
            //do nothing
        }

    }
    var translation ={
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
</script>

</body>

</html>
