<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 23/10/2017
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<html lang="en">

<head>
    <title><spring:message code="classStudentEnrolmentPage.text1"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/studentEnrolmentPage.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>
<%--TEST DATASOURCE --%>


<!--Content Begins  -->
<div class="content">

            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-users/classes" class="icon-home"><spring:message code="classStudentEnrolmentPage.manageClasses"/></a></li>
                        <%--<li><a href="#"></a></li>--%>
                        <%--<li><a href="#">Second Level Interior Page</a></li>--%>
                        <%--<li><a href="#">Third Level Interior Page</a></li>--%>
                    <li class="last active"><a href="#"><spring:message code="classStudentEnrolmentPage.classEnrolment"/> </a></li>
                </ul>
            </div>


    <!--SEARCH CONTAINER -->
    <div class="btnGroupBox">
        <div class="form-group pull-right">
            <div class="btn-group" role="group" aria-label="...">
                <%--<a href="${url}/admin/download/student-with-no-class-excel" type="button" class="btn btn-default"><spring:message code="classStudentEnrolmentPage.text2"/> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                <button data-toggle="modal" data-target="#enrolmentModal" type="button" class="btn btn-default"><spring:message code="classStudentEnrolmentPage.enrollUsingExcel"/> <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>
            </div>
        </div>
    </div>
    <%--/SEARCH CONTAINER--%>

    <%--<h3 style="margin-left: 20px"><spring:message code="enrollStudent.title2" /></h3>--%>

    <div class=" courseDes ">
        <h3 style="font-weight: 700"><spring:message code="classStudentEnrolmentPage.classStudentEnrollment"/> </h3><br/>
        <h4 class="totalEnrolled"><spring:message code="enrollStudent.totalEnrolledStd" />: ${enrollmentCount}</h4>
        <h4><spring:message code="classStudentEnrolmentPage.classId"/>: ${aboutClass.id}</h4>
        <h4><spring:message code="classStudentEnrolmentPage.className"/>: ${aboutClass.majorShortName} | ${aboutClass.className}</h4>
        <h4><spring:message code="classStudentEnrolmentPage.classStatus"/> : ${aboutClass.status}</h4>
        <h4><spring:message code="manageUsers2.intake"/>: ${aboutClass.intakePeriod}</h4>

    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <!--Big cards-->
        <div id="#fullList" class="col-sm-6">
            <h5><spring:message code="classStudentEnrolmentPage.text3"/></h5>
            <div class="card bigCardContainer">


                <div id="unEnrolled" class="page">
                    <div class="searchBox">
                        <div class="input-group">
                            <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /><span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                </ul>
                            </div><!-- /btn-group -->
                        </div><!-- /input-group -->
                    </div>

                    <div class="page-header cf"></div>
                    <div class="listContainer">
                        <ul class="list student-list" >
                            <c:forEach var = "studentList" items="${studentsNotEnrolledInAnyClassList}" varStatus="index">
                                <li class="student-item cf">
                                    <div class="student-details">
                                        <img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">
                                        <h3 class="name"><c:out value="${studentList.userName}"/><span class=" studentId email"> (<c:out value="${studentList.userId}"/>)</span></h3>
                                        <span class="className email"><spring:message code="enrollmentList.class"/>: <c:out value="${studentList.className}"/></span>
                                    </div>
                                    <div class="joined-details">
                                        <button class="eBtn enroll"value="${studentList.userId}" ><spring:message code="admin.enroll" /></button>
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
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /><span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>
                                </ul>
                            </div><!-- /btn-group -->
                        </div><!-- /input-group -->
                    </div>

                    <div class="page-header cf"></div>
                    <div class="listContainer">
                        <ul class="list student-list" >
                            <c:forEach var = "studentList" items="${classEnrolledList}">
                                <li class="student-item cf">
                                    <div class="student-details">
                                        <img class="avatar" src="${url}/resources/student_app_static/images/default_pro_pic.png">
                                        <h3 class="name"><c:out value="${studentList.userName}"/><span class=" studentId email"> (<c:out value="${studentList.userId}"/>)</span></h3>
                                        <span class="className email"><spring:message code="enrollmentList.class" />: <c:out value="${studentList.className}"/></span>
                                    </div>
                                    <div class="joined-details">
                                        <button class=" eBtn unEnroll " value="${studentList.userId}"><spring:message code="studentPageCourseLables.unEnroll" /></button>
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
                    <h3 style="text-align: center" class="modal-title"><spring:message code="classStudentEnrolmentPage.text4"/>  </h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form:form action="${url}/admin/manage-class/enroll/students-excel-import/${classId}" method="post"
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
                url: '${url}/admin/class/process-enrollment/${classId}/'+$(this).val(),
                type: 'POST',
//                data: {'userId':$(this).val(),'method':"manual"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });

        $('.unEnroll').click(function() {
//            alert("working");
            $.ajax({
                url: '${url}/admin/class/remove/${classId}/'+$(this).val(),
                type: 'POST',
//                data: {'userId':$(this).val(),'action':"remove"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });




    });








</script>

</body>

</html>