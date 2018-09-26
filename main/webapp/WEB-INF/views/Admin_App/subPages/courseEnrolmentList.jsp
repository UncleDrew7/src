<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 20/08/2017
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<%--template database --%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>

<html lang="en">

<head>
    <title><spring:message code="cEnrolmentList.title" /></title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/courseEnrolmentList.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>

</head>

<body>

<%--navs--%>

<!-- Tab panes -->
<div class="content">
    <c:choose >
        <c:when test="${nav eq 1}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><spring:message code="adminNavLable.dashboard" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollmentList.title" /></a></li>
                </ul>
            </div>
        </c:when>
       <c:when test="${nav eq 2}">
           <div class="wrapper row">
               <ul class="breadcrumbs">
                   <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
                   <li class="last active"><a ><spring:message code="enrollmentList.enrolmentList" /></a></li>
               </ul>
           </div>
       </c:when>
        <c:when test="${nav eq 3}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/view/course/${courseId}" class="icon-home"><spring:message code="studentHome.course" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollmentList.enrolmentList" /></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="adminNavLable.manageCourses" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollmentList.enrolmentList" /></a></li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>



    <div class="row mainContainer">

        <%--<ol class="breadcrumb">--%>
            <%--<li><a href="#">Home</a></li>--%>
            <%--<li><a href="#">Library</a></li>--%>
            <%--<li class="active">Data</li>--%>
        <%--</ol>--%>

        <!--Small cards-->
        <div class="col-sm-3">
            <div class="upcomingEventsContainer ">
                <div>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseId" /> : ${courseId}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseName" /> : <c:out value="${courseDetails.courseName}"/></h4>
                    <h4 class="titleTextBoxHeaders">Semester : <c:out value="${courseDetails.semesterCode}"/></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.teacher" /> : <c:out value="${courseDetails.teacherName}"/></h4>
                </div>
                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseType" /> : <c:out value="${courseDetails.courseType}"/></h4>
                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolmentDeadline" /> : <c:out value="${courseDetails.enrollmentDeadline}"/></h4>

                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolledStudents" />  : ${enrolledStudentsCounts}</h4>
                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolmentRequests" /> : ${enrolmentRequestCounts}</h4>
                <!--TimeLine container-->
                <div class="timelineWrapperContainer">


                </div>
                <!--TimeLine Container-->

            </div>


        </div>
        <!--Small cards-->
        <%----------------%>
        <div class="col-sm-9 ">



            <!--MAIN CARD CONTAINER -->
            <div class=" card mainCardContainer">

                <!--SEARCH CONTAINER -->
                <div class="searchingBox">
                    <div class="form-group pull-right">
                        <div class="btn-group" role="group" aria-label="...">
                            <a type="button" class="btn btn-default"  href="${url}/admin/download/${courseId}/course-enrolment-list-excel"><spring:message code="cEnrolmentList.exportCourseList" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                            <a type="button" class="btn btn-default"  href="${url}/admin/manage-courses/enroll-student/${courseId}/2"><spring:message code="enrollmentList.enrollStudent" /> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
                        </div>
                    </div>

                    <%--&lt;%&ndash;TEACHER FILE IMPOORT MODEL FORM &ndash;%&gt;--%>
                    <%--&lt;%&ndash;<button type="button" class="btn btn-success" data-toggle="modal" data-target="#popUpWindow">Open Log In Window</button>&ndash;%&gt;--%>
                    <%--<div class="modal fade" id="importCourseExcel">--%>
                        <%--<div class="modal-dialog">--%>
                            <%--<div class="modal-content">--%>
                                <%--<!--Header-->--%>
                                <%--<div class="modal-header">--%>
                                    <%--<button type="button" class="close" data-dismiss="modal">&times</button>--%>
                                    <%--<h3 class="modal-title">Log in</h3>--%>
                                <%--</div>--%>
                                <%--<!--Body (form)-->--%>
                                <%--<div class="modal-body">--%>

                                    <%--&lt;%&ndash;FILE FORM &ndash;%&gt;--%>
                                    <%--<form:form action="${url}/manage-courses/add-course-excel" method="post"--%>
                                               <%--enctype="multipart/form-data">--%>
                                        <%--<div> Course Excel File 2007:</div>--%>
                                        <%--<input name="courseExcelListFile" type="file">--%>
                                        <%--<input type="submit" value="Import Excel File">--%>
                                    <%--</form:form>--%>
                                    <%--&lt;%&ndash;FILE FORM &ndash;%&gt;--%>

                                <%--</div>--%>
                                <%--<!--Button-->--%>
                                <%--<div class="modal-footer">--%>
                                    <%--<button class="btn btn-primary btn-block">Log </button>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;TEACHER FILE IMPOORT MODEL FORM&ndash;%&gt;--%>

                </div>
                <%--/SEARCH CONTAINER--%>


                <!--MAIN TAB CONTAINER -->
                <div class="mainTabContainer">

                    <!--NAV TABS -->
                    <ul class="nav nav-tabs">

                        <li class="active"><a data-toggle="tab" href="#home"><spring:message code="enrollmentList.enrolmentList" /></a></li>
                        <li><a data-toggle="tab" href="#menu2"><spring:message code="enrollmentList.enrolmentRequests" /> <span class="badge">${enrolmentRequestCounts}</span></a></li>


                    </ul>
                    <%--/NAV TABS--%>

                    <!--TAB CONTENT-->
                    <div class="tab-content">
                        <br/>

                        <%--MANAGE USER TABLE --%>
                        <div id="home" class="tab-pane fade in active">

                            <%--<h3><spring:message code="enrollmentList.title" /></h3>--%>

                            <%--!TABLE--%>
                            <div class="table-responsive">
                                <br/>
                                <table id="enrolmentList" class="table table-bordered table-hover">

                                    <%--<div class="select-style">--%>
                                        <%--<select>--%>
                                            <%--<option value="volvo">All</option>--%>
                                            <%--<option value="saab">2017 Spring</option>--%>
                                            <%--<option value="mercedes">2016 Fall</option>--%>
                                            <%--<option value="saab">2016 Spring</option>--%>
                                            <%--<option value="mercedes">2015 Fall</option>--%>
                                            <%--<option value="saab">2015 Spring</option>--%>
                                            <%--<option value="mercedes">2014 Fall</option>--%>
                                            <%--<option value="mercedes">2014 Spring</option>--%>
                                            <%--<option value="mercedes">2013 Fall</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>



                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code="enrollmentList.studentName" /></th>
                                        <th><spring:message code="enrollmentList.studentId" /></th>
                                        <th><spring:message code="enrollmentList.degreeType" /></th>
                                        <th><spring:message code="enrollmentList.enrolmentDate" /></th>
                                        <th><spring:message code="enrollmentList.class" /></th>
                                        <th><spring:message code="enrollmentList.action" /></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var = "eList" items="${enrollmentList}" varStatus="index">
                                        <tr>
                                            <td>${index.count}</td>
                                            <td><c:out value="${eList.userName}"/></td>
                                            <td><c:out value="${eList.userId}"/></td>
                                            <td><c:out value="${eList.degreeType}"/></td>
                                            <td><c:out value="${eList.enrolmentDate}"/></td>
                                            <td><c:out value="${eList.className}"/></td>

                                            <td>
                                                <button  <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> onclick="unEnrollStudent(${eList.userId})" class="btn btn-danger btn-xs" type="button" ><spring:message code="studentPageCourseLables.unEnroll" /></button>
                                            </td>
                                        </tr>
                                    </c:forEach>



                                    </tbody>

                                </table>


                            </div>
                            <%--<--END OF TABLE-->--%>



                        </div>
                        <%--/TAB CONTENT--%>

                        <%--/MANAGE USER TABLE --%>





                        <!--MANAGE CATEGORIES-->
                        <div id="menu2" class="tab-pane fade">
                            <%--<h3><spring:message code="enrollmentList.enrolmentRequests" /></h3>--%>

                            <%--!TABLE--%>
                            <div class="table-responsive">
                                <br/>
                                <table id="eRequests" class="table table-bordered table-hover">


                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code="enrollmentList.studentName" /></th>
                                        <th><spring:message code="enrollmentList.studentId" /></th>
                                        <th><spring:message code="enrollmentList.degreeType" /></th>
                                        <th><spring:message code="enrollmentList.submissionDate" /></th>
                                        <th><spring:message code="enrollmentList.class" /></th>
                                        <th><spring:message code="enrollmentList.action" /></th>
                                    </tr>
                                    </thead>

                                    <tbody>

                                    <c:forEach var = "erList" items="${enrollmentRequetsList}" varStatus="index">
                                        <tr>
                                            <td>${index.count}</td>
                                            <td><c:out value="${erList.userName}"/></td>
                                            <td><c:out value="${erList.userId}"/></td>
                                            <td><c:out value="${erList.degreeType}"/></td>
                                            <td><c:out value="${erList.submissionDate}"/></td>
                                            <td><c:out value="${erList.className}"/></td>
                                            <td>
                                                <div>
                                                    <div class="btn-group" role="group" aria-label="...">
                                                        <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  onclick="enrollStudent(${erList.userId})" type="button" class="btn btn-success btn-xs btn-default"><spring:message code="admin.enroll" /></button>
                                                        <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  onclick="declineStudent(${erList.userId})" type="button" class="btn btn-danger btn-xs btn-default">#Decline</button>
                                                    </div>
                                                    <%--<button type="button" class="btn btn-success btn-xs">Enroll</button><span><button type="button" class="btn btn-danger btn-xs">Delete</button></span>--%>

                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>


                                    </tbody>

                                    <%--<tfoot>--%>
                                    <%--<tr>--%>
                                    <%--<td colspan="5" class="text-center">Data retrieved from <a href="http://www.infoplease.com/ipa/A0855611.html" target="_blank">infoplease</a> and <a href="http://www.worldometers.info/world-population/population-by-country/" target="_blank">worldometers</a>.</td>--%>
                                    <%--</tr>--%>
                                    <%--</tfoot>--%>
                                </table>

                            </div>
                            <%--<--END OF TABLE-->--%>

                        </div>
                        <%--MANAGE CATEGORIES--%>

                    </div>
                    <%--MAIN TAB CONTAINER --%>


                    <%--/MAIN TAB CONTAINER--%>
                </div>




            </div>
            <%--/END OF MAIN CARD CONTAINER --%>


        </div>



    </div>


</div>

<script>

    function  unEnrollStudent(userId){
        var r = confirm('<spring:message code="cEnrolmentList.msg1" />');
        if (r == true) {
            <%--alert(userId+""+${courseId});--%>
            $.ajax({
                url: '${url}/admin/manage-courses/enroll-student/${courseId}',
                type: 'POST',
                data: {'userId':userId,'action':"remove"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        } else{
            //do nothing
            <%--window.location.href = '${url}/admin/dashboard';--%>
        }
    }

    function enrollStudent(userId){
        var r = confirm('<spring:message code="cEnrolmentList.msg2" />');
        if(r == true){

            <%--alert(userId+""+${courseId});--%>
            $.ajax({
                url:'${url}/admin/manage-course/enroll-student/${courseId}/'+userId,
                type:'post',
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }else{
            //do nothing
        }
    }

    function declineStudent(userId){
        var r = confirm('<spring:message code="cEnrolmentList.msg3" />');
        if(r == true){
            $.ajax({
                url:'${url}/admin/manage-course/decline-request/${courseId}/'+userId,
                type:'post',
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }else{
            //do nothing
        }
    }

    $(document).ready(function() {
        $('#enrolmentList').DataTable();
        $('#eRequests').DataTable();
    } );

</script>




<%--footer--%>

</body>

</html>