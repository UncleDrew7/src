<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 04/09/2017
  Time: 13:49
  To change this template use File | Settings | File Templates.
  $$$$$$$$$$
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
    <title><spring:message code="studentEnrolmentPage.courseEnrolmentPage"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/studentEnrolmentPage.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body onload="loadScroll()" onunload="saveScroll()">
<%--TEST DATASOURCE --%>


<!--Content Begins  -->
<div class="content">

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
                    <li class="first"><a href="${url}/admin/manage-courses/enrolment-list/${childCourseId}/${nav}" class="icon-home"><spring:message code="enrollmentList.enrolmentList" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollmentList.enrollStudent" /></a></li>
                </ul>
            </div>
        </c:when>


    </c:choose>



    <!--SEARCH CONTAINER -->
    <div class="btnGroupBox">
        <div class="form-group pull-right">
            <div class="btn-group" role="group" aria-label="...">
                <a href="${url}/admin/download/${childCourseId}/students-not-enrolled-in-course-excel" type="button" class="btn btn-default"><spring:message code="studentEnrolmentPage.text1"/> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                    <button data-toggle="modal" data-target="#enrolmentModal" type="button" class="btn btn-default"><spring:message code="classStudentEnrolmentPage.enrollUsingExcel"/> <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>
                </c:if>
            </div>
        </div>
    </div>
    <%--/SEARCH CONTAINER--%>

    <div class=" courseDes ">
        <h3 style="font-weight: 700"><spring:message code="enrollStudent.title2" /></h3><br/>
        <h4 class="totalEnrolled"><spring:message code="enrollStudent.totalEnrolledStd" />: ${enrollmentsCount} </h4>
        <h4><spring:message code="enrollmentList.courseId" />: ${aboutCourse.childCourseId}</h4>
        <h4><spring:message code="enrollmentList.courseName" />: ${aboutCourse.courseName}</h4>
        <h4><spring:message code="manageUsers2.semester"/> : ${aboutCourse.semesterCode}</h4>
        <h4><spring:message code="enrollmentList.teacher" />: ${aboutCourse.teacherName}</h4>
        <h4><spring:message code="enrollmentList.enrolmentDeadline" />: ${aboutCourse.enrollmentDeadline}</h4>

    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <!--Big cards-->
        <div id="#fullList" class="col-sm-6">
            <h5><spring:message code="enrollStudent.unEnrolledStudentList" /></h5>
            <div class="card bigCardContainer">

                <%------------------------------%>

                    <%--<div class="page">--%>
                        <%--<div class="page-header cf">--%>
                        <%--</div>--%>
                        <%--<ul class="student-list">--%>
                            <%--<c:forEach var = "studentList" items="${notEnrolledList}" varStatus="index">--%>
                                <%--&lt;%&ndash;<c:forEach var = "erow" items="${enrollmentList.rows}">&ndash;%&gt;--%>

                                    <%--&lt;%&ndash;<c:if test="${row.user_id != enrollmentId.rowsByIndex[][0]}">&ndash;%&gt;--%>
                                        <%--<li class="student-item cf">--%>
                                            <%--<div class="student-details">--%>
                                                <%--<img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">--%>
                                                <%--<h3><c:out value="${studentList.userName}"/><span class="email"> (<c:out value="${studentList.userId}"/>)</span></h3>--%>
                                                <%--<span class="email">Class: <c:out value="${studentList.className}"/></span>--%>
                                            <%--</div>--%>
                                            <%--<div class="joined-details">--%>
                                                <%--<button class="eBtn enroll"value="${studentList.userId}" >Enroll</button>--%>

                                            <%--</div>--%>
                                        <%--</li>--%>
                                    <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                            <%--</c:forEach>--%>

                        <%--</ul>--%>
                        <%--<div class="line"></div>--%>
                    <%--</div>--%>




                    <div id="unEnrolled" class="page">
                        <div class="searchBox">
                            <div class="input-group">
                                <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                    </ul>
                                </div><!-- /btn-group -->
                            </div><!-- /input-group -->
                        </div>

                        <div class="page-header cf"></div>
                        <div class="listContainer">
                            <ul class="list student-list" >
                                <c:forEach var = "studentList" items="${notEnrolledList}" varStatus="index">
                                    <li class="student-item cf">
                                        <div class="student-details">
                                            <img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">
                                            <h3 class="name"><c:out value="${studentList.userName}"/><span class=" studentId email"> (<c:out value="${studentList.userId}"/>)</span></h3>
                                            <span class="className email"><spring:message code="enrollmentList.class" />: <c:out value="${studentList.className}"/></span>
                                        </div>
                                        <div class="joined-details">
                                            <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                                                <button class="eBtn enroll"value="${studentList.userId}" ><spring:message code="admin.enroll" /></button>
                                            </c:if>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                            <div class="line"></div>
                        </div>

                    </div>

                <%---------------------------------------%>
            </div>

        </div>
        <!--Big cards-->










        <!--Small cards-->
        <div class="col-sm-6">
            <h5><spring:message code="enrollStudent.enrolledStudents" /></h5>

            <div class="card bigCardContainer">

                <%-----------------------------------%>
                    <div id="users" class="page">
                        <div class="searchBox">
                            <div class="input-group">
                                <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                    </ul>
                                </div><!-- /btn-group -->
                            </div><!-- /input-group -->
                        </div>

                            <div class="page-header cf"></div>
                       <div class="listContainer">
                           <ul class="list student-list" >
                               <c:forEach var = "studentList" items="${enrolledList}">
                                   <li class="student-item cf">
                                       <div class="student-details">
                                           <img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">
                                           <h3 class="name"><c:out value="${studentList.userName}"/><span class=" studentId email"> (<c:out value="${studentList.userId}"/>)</span></h3>
                                           <span class="className email"><spring:message code="enrollmentList.class"/>: <c:out value="${studentList.className}"/></span>
                                       </div>
                                       <div class="joined-details">
                                           <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                                                <button class=" eBtn unEnroll " value="${studentList.userId}"><spring:message code="studentPageCourseLables.unEnroll" /></button>
                                           </c:if>
                                               <%--<a class="courseButton">Un-Enroll</a>--%>
                                           <span class="enrollmentDate date"><spring:message code="enrollStudent.enrolled" />: <c:out value="${studentList.enrolmentDate}" /></span>
                                       </div>
                                   </li>
                               </c:forEach>
                           </ul>
                       </div>
                        <div class="line"></div>
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
                    <h3 style="text-align: center" class="modal-title"><spring:message code="studentEnrolmentPage.text2"/> </h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form:form action="${url}/admin/manage-courses/enroll/students-excel-import/${childCourseId}" method="post"
                               enctype="multipart/form-data">
                        <input name="unEnrolledSystemStudents" type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button  type="submit" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile"/> </button>
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

    var options = {
        valueNames: [ 'name', 'born' ]
    };

    var userList = new List('users', options);
    var unEnrolled = new List('unEnrolled', options);
//    var options = {
//        valueNames: [ 'userName', 'className','enrollmentDate' ]
//    };
//    var enrolledList = new List('enrolledList', options);

    $(document).ready(function() {
//        $('.courseButton').click(function() {
//            console.log("working");
//            jQuery('#fullList').load(' #fullList');
//
//
//        });

        $('.enroll').click(function() {
        $.ajax({
            url: '${url}/admin/manage-courses/enroll-student/${childCourseId}',
            type: 'POST',
            data: {'userId':$(this).val(),'action':"add"}, // An object with the key 'submit' and value 'true;
            success: function (result) {
                alert(result);
                document.location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });

        $('.unEnroll').click(function() {
//            alert("working");
            $.ajax({
                url: '${url}/admin/manage-courses/enroll-student/${childCourseId}',
                type: 'POST',
                data: {'userId':$(this).val(),'action':"remove"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });




    });

    var cookieName = "page_scroll";
    var expdays = 365;

    // An adaptation of Dorcht's cookie functions.

    function setCookie(name, value, expires, path, domain, secure) {
        if (!expires) expires = new Date();
        document.cookie = name + "=" + escape(value) +
            ((expires == null) ? "" : "; expires=" + expires.toGMTString()) +
            ((path    == null) ? "" : "; path=" + path) +
            ((domain  == null) ? "" : "; domain=" + domain) +
            ((secure  == null) ? "" : "; secure");
    }

    function getCookie(name) {
        var arg = name + "=";
        var alen = arg.length;
        var clen = document.cookie.length;
        var i = 0;
        while (i < clen) {
            var j = i + alen;
            if (document.cookie.substring(i, j) == arg) {
                return getCookieVal(j);
            }
            i = document.cookie.indexOf(" ", i) + 1;
            if (i == 0) break;
        }
        return null;
    }

    function getCookieVal(offset) {
        var endstr = document.cookie.indexOf(";", offset);
        if (endstr == -1) endstr = document.cookie.length;
        return unescape(document.cookie.substring(offset, endstr));
    }

    function deleteCookie(name, path, domain) {
        document.cookie = name + "=" +
            ((path   == null) ? "" : "; path=" + path) +
            ((domain == null) ? "" : "; domain=" + domain) +
            "; expires=Thu, 01-Jan-00 00:00:01 GMT";
    }

    function saveScroll() {
        var expdate = new Date();
        expdate.setTime(expdate.getTime() + (expdays*24*60*60*1000)); // expiry date

        var x = document.pageXOffset || document.body.scrollLeft;
        var y = document.pageYOffset || document.body.scrollTop;
        var data = x + "_" + y;
        setCookie(cookieName, data, expdate);
    }

    function loadScroll() {
        var inf = getCookie(cookieName);
        if (!inf) { return; }
        var ar = inf.split("_");
        if (ar.length == 2) {
            window.scrollTo(parseInt(ar[0]), parseInt(ar[1]));
        }
    }







</script>

</body>

</html>