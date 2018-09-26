<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/01/2018
  Time: 22:05
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
    <title><spring:message code="viewParentCourse.viewParentCourse"/></title>
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
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-courses/majors" class="icon-home"><spring:message code="manageCourse2.parenetCourse" /></a></li>
            <li class="last active"><a ><spring:message code="viewParentCourse.viewParentCourse" /></a></li>
        </ul>
    </div>



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
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageCourse2.major"/> : ${parentDetails.majorName}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseId" /> : ${parentDetails.parentCourseId}</h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseType" /> : <c:out value="${parentDetails.courseType}"/></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageCourse2.credits"/>: <c:out value="${parentDetails.credits}"/></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="viewParentCourse.dateCreated"/>  : <c:out value="${parentDetails.createdAt}"/></h4>

                </div>

            </div>
            <br/><br/>



            <div style="background: #337ab7" class="panel-heading">
                <h4 class="semesterHeader panel-title">
                    <a data-toggle="collapse" href="#collapse1"><spring:message code="addExamForm.selectCourse"/></a>
                </h4>
            </div>
            <div class="card  filterBoxCourse ">
                <div>
                    <div style="margin-top: -10px" class="list-group">
                        <c:forEach var = "navCourse" items="${sameMajorCourses}" varStatus="index">
                            <a href="#"  onclick="filterParentCourses(${navCourse.parentCourseId})" class="list-group-item <c:if test='${parentCourseId eq navCourse.parentCourseId}'>activeFilter</c:if>">${navCourse.courseName}</a>
                        </c:forEach>
                    </div>

                </div>
            </div>



        </div>
            <br/><br/>
        <!--Small cards-->
        <%----------------%>
        <div class="col-sm-9 ">

            <h3 class="title"> ${parentDetails.courseName}</h3>
            <%--COURSE DESCRIPTION CONTAINER --%>
            <%--<a class="courseButton"  data-toggle="modal" data-target="#importCourseExcel" href="#">Edit Course</a>--%>
            <%-------------------------------------------------------------%>

                <%--<span class="introText dates"><spring:message code="coursePage.from" />: &nbsp;${courseDetails.startDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="coursePage.to" />:&nbsp; ${courseDetails.endDate}</span><br/>--%>




            <!--MAIN CARD CONTAINER -->
            <div class=" card mainCardContainer">

                <!--SEARCH CONTAINER -->
                <c:if test="${currentUserRole eq 'admin'}">
                    <div class="searchingBox">
                        <div class="form-group pull-right">
                            <div class="btn-group" role="group" aria-label="...">
                                <a type="button" class="btn btn-default"  href="${url}/admin/parent-course/create-child-course/${parentCourseId}?nv=pc"><spring:message code="viewParentCourse.createNewChildCourse"/> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
                                <a type="button" class="btn btn-default"  href="${url}/admin/edit-parent-course-major/parent_course/${parentCourseId}?nv=pc"><spring:message code="main.edit"/> <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            </div>
                        </div>
                    </div>
                </c:if>
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
                                        <th><spring:message code="manageUsers2.semester"/></th>
                                        <th><spring:message code="viewParentCourse.shortName"/></th>
                                        <th><spring:message code="courseForm.teacher"/></th>
                                        <th><spring:message code="courseExamPage.enrolledStudents"/></th>
                                        <th><spring:message code="main.action"/></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var = "ccList" items="${childCourseList}" varStatus="index">
                                        <tr>
                                            <td>${index.count}</td>
                                            <td><c:out value="${ccList.semesterCode}"/></td>
                                            <td><a style="color: inherit" href="${url}/admin/view/course/${ccList.childCourseId}"><c:out value="${ccList.childCourseName}"/></a></td>
                                            <td><c:out value="${ccList.teacherName}"/></td>
                                            <td><a style="color: inherit" href="${url}/admin/manage-courses/enrolment-list/${ccList.childCourseId}/2"><c:out value="${ccList.totalEnrolledStudents}"/></a></td>
                                            <td >
                                                <div class="dropdown">
                                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right actionDropdown">
                                                        <li><a href="${url}/admin/view/course/${ccList.childCourseId}"><spring:message code="studentPageCourseLables.goToCourseBtn" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-education" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}"><li><a href="${url}/admin/manage-courses/enroll-student/${ccList.childCourseId}/1"><spring:message code="manageCourse.enrolledStudents" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li></c:if>
                                                        <li><a href="${url}/admin/manage-courses/enrolment-list/${ccList.childCourseId}/2"><spring:message code="enrollmentList.title" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}"><li><a href="${url}/admin/manage-course/edit/${ccList.childCourseId}/1"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li></c:if>
                                                        <c:if test="${currentUserRole eq 'admin'}"><li ><a style="cursor: pointer" id="delete" onClick="deleteCourse(${ccList.childCourseId})"  ><spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li></c:if>

                                                    </ul>
                                                </div>

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

    function filterParentCourses(parentCourseId) {
        window.location.href = '${url}/admin/parent-course/view/'+parentCourseId;
    }


    function  unEnrollStudent(userId){
        var r = confirm('<spring:message code="viewParentCourse.msg1"/>');
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
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        } else{
            //do nothing
            <%--window.location.href = '${url}/admin/dashboard';--%>
        }
    }

    function enrollStudent(userId){
        var r = confirm('<spring:message code="viewParentCourse.msg2"/>');
        if(r == true){

            <%--alert(userId+""+${courseId});--%>
            $.ajax({
                url:'${url}/admin/manage-course/enroll-student/${courseId}/'+userId,
                type:'post',
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }else{
            //do nothing
        }
    }

    function declineStudent(userId){
        var r = confirm('<spring:message code="viewParentCourse.msg3"/>');
        if(r == true){
            $.ajax({
                url:'${url}/admin/manage-course/decline-request/${courseId}/'+userId,
                type:'post',
                success:function(data){
                    alert(data);
                    location.reload(true);
                },
                error : function(data){
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