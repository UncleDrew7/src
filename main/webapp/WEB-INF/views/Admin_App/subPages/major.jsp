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
  Date: 04/09/2017
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<%--for date --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<html lang="en">

<head>
    <title>Major</title>
    <link href="${url}/resources/app_admin_static/css/subPages/studentEnrolmentPage.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
    <%--<script src="${url}/resources/main/list.min.js"></script>--%>
</head>

<body onload="loadScroll()" onunload="saveScroll()">
<%--TEST DATASOURCE --%>


<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
            <li class="last active"><a ><spring:message code="enrollStudent.title" /></a></li>
        </ul>
    </div>
    <c:choose >
        <c:when test="${nav eq 1}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollStudent.title" /></a></li>
                </ul>
            </div>
        </c:when>

        <c:when test="${nav eq 2}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/enrolment-list/${courseId}/${nav}" class="icon-home"><spring:message code="enrollmentList.enrolmentList" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollmentList.enrollStudent" /></a></li>
                </ul>
            </div>
        </c:when>


    </c:choose>



    <!--SEARCH CONTAINER -->
    <%--<div class="btnGroupBox">--%>
        <%--<div class="form-group pull-right">--%>
            <%--<div class="btn-group" role="group" aria-label="...">--%>
                <%--<a href="${url}/admin/download/${courseId}/students-not-enrolled-in-course-excel" type="button" class="btn btn-default">Download Un-enrolled Student List Excel Template <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                <%--<c:if test="${hasPermission or currentUserRole eq 'admin'}">--%>
                    <%--<button data-toggle="modal" data-target="#enrolmentModal" type="button" class="btn btn-default">Enroll Using Excel <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>--%>
                <%--</c:if>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--/SEARCH CONTAINER--%>

    <%--<div class=" courseDes ">--%>
        <%--<h3 style="font-weight: 700">Major</h3><br/>--%>
        <%--<h4 class="totalEnrolled">Course</h4>--%>
    <%--</div>--%>






    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <h3>Major</h3>
        <!--Big cards-->
        <div  class="col-sm-7">
            <%--<h5><spring:message code="enrollStudent.unEnrolledStudentList" /></h5>--%>
            <div class="card bigCardContainer">

                <div id="unEnrolled" class="page">

                    <br/>
                    <div class="table-responsive">
                            <table id="majorTable" class="table table-bordered table-hover">


                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Major Short Code</th>
                                    <th>Major Name</th>
                                    <th>No Students</th>
                                    <th>No Courses</th>
                                    <th>Action</th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr>
                                    <td>1</td>
                                    <td> CVE</td>
                                    <td> Civil Engineering </td>
                                    <td>80</td>
                                    <td>12</td>
                                    <td>
                                    <div  class="dropdown">
                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                    <span class="caret"></span></button>
                                    <ul style="margin-right:32% " class="dropdown-menu  dropdown-menu-right">
                                    <li><a href="${url}/admin/semester/list/${sysSemester.semesterId}"><spring:message code="extra.courseList" />&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                    <li><a href="${url}/admin/semester/update/${sysSemester.semesterId}"><spring:message code="coursePage.edit" />&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                    <%--<li><a href="#">Delete&nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>--%>
                                    </ul>
                                    </div>

                                    </td>
                                </tr>
                                <%--<c:forEach var = "sysCourse" items="${courseTableList}" varStatus="index">--%>
                                    <%--<tr>--%>
                                        <%--<td>1</td>--%>
                                        <%--<td>班级成绩汇总</td>--%>
                                        <%--<td>班级成绩汇总</td>--%>
                                        <%--<td>80</td>--%>
                                        <%--<td>12</td>--%>
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

                                </tbody>

                            </table>
                        </div>






                </div>

                <%---------------------------------------%>
            </div>

        </div>
        <!--Big cards-->


        <!--Small cards-->
        <div class="col-sm-5">
            <%--<h5><spring:message code="enrollStudent.enrolledStudents" /></h5>--%>

            <div class="card bigCardContainer">

                <%-----------------------------------%>
                <div id="users" class="page">

                    <div style="margin-bottom: 10px; padding-top: 10px" class="btn-group btn-group-justified" role="group" aria-label="...">
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-sm btn-default">Course</button>
                        </div>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-sm btn-default">Students</button>
                        </div>
                    </div>
                    <%--<div class="searchBox">--%>
                        <div class="input-group">
                            <input style="width:100%; float: right; height: 30px" type="text" class="form-control search" aria-label="...">
                            <div class="input-group-btn">
                                <button style="height: 30px" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                </ul>
                            </div><!-- /btn-group -->
                        </div><!-- /input-group -->
                    <%--</div>--%>


                    <%--<div class="page-header cf"></div>--%>

                    <%--<div class="listContainer">--%>
                        <%--<ul class="list student-list" >--%>
                            <%--&lt;%&ndash;<c:forEach var = "studentList" items="${enrolledList}">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<li class="student-item cf ">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<div class="student-details">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<h3 class="name"><c:out value="${'Bishop Hobs'}"/><span class=" studentId email"> (<c:out value="${'800'}"/>)</span></h3>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<span class="className email">Class: <c:out value="${'Kights'}"/></span>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<div class="joined-details">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;&lt;%&ndash;<c:if test="${hasPermission or currentUserRole eq 'admin'}">&ndash;%&gt;&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<span class="enrollmentDate date">Intake: <span class="badge">2017-1</span></span>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;&lt;%&ndash;</c:if>&ndash;%&gt;&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;&lt;%&ndash;<a class="courseButton">Un-Enroll</a>&ndash;%&gt;&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<span class="enrollmentDate date">No Courses: 4</span>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</li>&ndash;%&gt;--%>

                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                        <%--</ul>--%>

                       <%----%>
                    <%--</div>--%>
                    <br/>
                    <ul class="list-group">
                        <li style="padding: 8px" class="list-group-item">
                            <div class="media">
                                <div class="media-left">
                                    <a href="#">
                                        <img class="media-object"   style="width: 50px; height: 50px" src="${url}/resources/student_app_static/images/tempimg.jpg" alt="...">
                                    </a>
                                </div>
                                <div class="media-body">
                                    <h4 class="media-heading">New HSK Elementary Speaking Course (2)</h4>
                                </div>
                            </div>
                        </li>
                        <li style="padding: 8px" class="list-group-item">
                            <div class="media">
                                <div class="media-left">
                                    <a href="#">
                                        <img class="media-object"   style="width: 50px; height: 50px" src="${url}/resources/student_app_static/images/tempimg.jpg" alt="...">
                                    </a>
                                </div>
                                <div class="media-body">
                                    <h4 class="media-heading">New HSK Elementary Speaking Course (2)</h4>
                                </div>
                            </div>
                        </li>

                    </ul>





                    <%--<div class="line"></div>--%>
                </div>

                <%-----------------------------------------%>
            </div>

        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->

    <%--ENROLLMENT MODAL --%>
    <div class="modal fade" id="enrolmentModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center" class="modal-title">Import student list to enroll in course </h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form:form action="${url}/admin/manage-courses/enroll/students-excel-import/${courseId}" method="post"
                               enctype="multipart/form-data">
                        <input name="unEnrolledSystemStudents" type="file">
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
    <%--ENROLL MENT MODAL--%>

</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {
        $('#majorTable').DataTable();
    } );
</script>



</body>

</html>