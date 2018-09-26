<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 08/01/2018
  Time: 15:17
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
    <title><spring:message code="clearanceExamEnrollmentList.text1"/> </title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/parentCourseView.css" rel="stylesheet">
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
                    <li class="first"><a href="${url}/admin/manage-exams/?examType=exams" class="icon-home"><spring:message code="manageExams.manageExams" /></a></li>
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

                    <h4 class="titleTextBoxHeaders"><spring:message code="courseForm.courseId"/> : ${clExamData.parentCourseId}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageExams2.parentExamName"/> : ${clExamData.parentExamName}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers2.semester"/>: ${clExamData.semesterCode}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.examDate" /> : <c:out value="${clExamData.examDate}"/></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.deadline" /> : <c:out value="${clExamData.enrolmentEndDate}"/></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="clearanceExamEnrollmentList.totalEligibleStudents"/> : ${0}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolledStudents" />: ${clExamData.enrolledStudents}</h4>

                </div>


                <div class="panel-group">
                    <div class="panel panel-default">
                        <div style="background: #337ab7" class="panel-heading">
                            <h4 class="semesterHeader panel-title">
                                <a data-toggle="collapse" href="#collapse1"><spring:message code="clearanceExamEnrollmentList.exportgradeRecord"/> </a>
                            </h4>
                        </div>
                        <div id="collapse1" class="recordeform panel-collapse collapse">
                            <%--<div class="recordeform">--%>
                            <form id="gradeform">

                                <div class="form-group">
                                    <div class="divide"><spring:message code="manageGrades2.selectMajor"/></div>
                                    <div>
                                        <select class="form-control" id="degreeType" name="degreeType">
                                            <option value="${currentCourseDetails.courseType}"></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="divide"><spring:message code="addCourseForm.selectSemester"/></div>

                                <c:forEach var = "sslist" items="${semesterList}" varStatus="index">
                                    <div class="">
                                        <label><input type="checkbox" value="${sslist.semesterId}"> ${sslist.semesterCode}</label>
                                    </div>
                                </c:forEach>



                                <div class="subBtn">
                                    <a  class="btn btn-default"><spring:message code="manageGrades2.downloadReport"/></a>
                                </div>
                                <%--<button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  style="color: inherit" type="submit" class="btn btn-default"><spring:message code="studentForm.saveSettings" /></button>--%>



                            </form>


                        </div>
                    </div>
                </div>



            </div>






        </div>
        <!--Small cards-->
        <%----------------%>
        <div class="col-sm-9 ">

            <h3 class="title">${clExamData.examName}</h3>
            <%--COURSE DESCRIPTION CONTAINER --%>
            <%--<a class="courseButton"  data-toggle="modal" data-target="#importCourseExcel" href="#">Edit Course</a>--%>
            <%-------------------------------------------------------------%>

            <%--<span class="introText dates"><spring:message code="coursePage.from" />: &nbsp;${courseDetails.startDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="coursePage.to" />:&nbsp; ${courseDetails.endDate}</span><br/>--%>




            <!--MAIN CARD CONTAINER -->
            <div class=" card mainCardContainer">

                <!--SEARCH CONTAINER -->
                <div class="searchingBox">
                    <div class="form-group pull-right">
                        <div class="btn-group" role="group" aria-label="...">
                            <%--<a type="button" class="btn btn-default"  href="${url}/admin/download/${courseId}/course-enrolment-list-excel">Export Course List <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                            <a type="button" class="btn btn-default"  href="${url}/admin/manage-exams/clearance-exam/enroll-students/${clExamData.clearanceExamId}"><spring:message code="enrollmentList.enrollStudent" /> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
                        </div>
                    </div>

                    <%--TEACHER FILE IMPOORT MODEL FORM --%>
                    <%--<button type="button" class="btn btn-success" data-toggle="modal" data-target="#popUpWindow">Open Log In Window</button>--%>
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

                <br/>

                <%--!TABLE--%>
                <div style="padding: 10px" class="table-responsive">

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
                            <th><spring:message code="enrollmentList.studentId" /></th>
                            <th><spring:message code="enrollmentList.studentName" /></th>

                            <th><spring:message code="enrollmentList.degreeType" /></th>
                            <th><spring:message code="enrollmentList.enrolmentDate" /></th>
                            <th><spring:message code="enrollmentList.class" /></th>
                            <th><spring:message code="enrollmentList.action" /></th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var = "eList" items="${clEnrolmentList}" varStatus="index">
                            <tr>
                                <td>${index.count}</td>
                                <td><c:out value="${eList.userId}"/></td>
                                <td><c:out value="${eList.userName}"/></td>

                                <td><c:out value="${eList.degreeType}"/></td>
                                <td><c:out value="${eList.enrolmentDate}"/></td>
                                <td><c:out value="${eList.className}"/></td>
                                <td>
                                    <button  <c:if test="${ currentUserRole ne 'admin'}">disabled</c:if> onclick="unEnrollStudent( ${eList.userId} )" class="btn btn-danger btn-xs" type="button" ><spring:message code="studentPageCourseLables.unEnroll" /></button>
                                </td>
                            </tr>
                        </c:forEach>



                        </tbody>

                    </table>


                </div>
                <%--<--END OF TABLE-->--%>






            </div>
            <%--/END OF MAIN CARD CONTAINER --%>


        </div>



    </div>


</div>

<script>

    function  unEnrollStudent(userId){
        var r = confirm('<spring:message code="clearanceExamEnrollmentList.text2"/>');
        if (r == true) {


            $.ajax({
                url: '${url}/admin/manage-clexam/un-enroll/${clExamData.clearanceExamId}',
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
        }

    }

    function enrollStudent(userId){
        var r = confirm('<spring:message code="clearanceExamEnrollmentList.text3"/>');
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
        var r = confirm('<spring:message code="clearanceExamEnrollmentList.text4"/>');
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
    });

</script>




<%--footer--%>

</body>

</html>