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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="eEnrolmentList.title1" /></title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>

</head>

<body>

<%--TEST DATASOURCE --%>


 <%--navs--%>

    <!-- Tab panes -->
    <div class="content">
       <c:choose>
           <c:when test="${nav eq 1}">
               <div class="wrapper row">
                   <ul class="breadcrumbs">
                       <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><spring:message code="adminNavLable.dashboard" /></a></li>
                       <li class="last active"><a href="#"><spring:message code="enrollmentList.examEnrollmentList" /></a></li>
                   </ul>
               </div>
           </c:when>
           <c:when test="${nav eq 2}">
               <div class="wrapper row">
                   <ul class="breadcrumbs">
                       <li class="first"><a href="${url}/admin/manage-exams/exams" class="icon-home"><spring:message code="adminNavLable.manageExams" /></a></li>
                       <li class="last active"><a href="#"><spring:message code="enrollmentList.examEnrollmentList" /></a></li>
                   </ul>
               </div>
           </c:when>
           <c:when test="${param.nv eq 'ce'}">
               <div class="wrapper row">
                   <ul class="breadcrumbs">
                       <li class="first"><a href="${url}/admin/manage-courses/course-exam/${examDetails.childCourseId}" class="icon-home"><spring:message code="courseExamPage.text1"/></a></li>
                       <li class="last active"><a ><spring:message code="enrollmentList.examEnrollmentList" /></a></li>
                   </ul>
               </div>
           </c:when>
           <c:otherwise>
               <div class="wrapper row">
                   <ul class="breadcrumbs">
                       <li class="first"><a href="${url}/admin/manage-exams/exams" class="icon-home"><spring:message code="adminNavLable.manageExams" /></a></li>
                       <li class="last active"><a href="#"><spring:message code="enrollmentList.examEnrollmentList" /></a></li>
                   </ul>
               </div>
           </c:otherwise>
       </c:choose>



            <div class="row mainContainer">


                <!--Small cards-->
                <div class="col-sm-3">
                    <div class="upcomingEventsContainer ">
                        <div>
                            <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.ExamId" />: ${examDetails.id}</h4>
                            <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.ExamName" />: <c:out value="${examDetails.examName}"/></h4>
                            <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.examDate" /> : <c:out value="${examDetails.dateOfExam}"/></h4>
                        </div>
                        <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseName" /> : <c:out value="${examDetails.parentCourseShortName}"/></h4>
                        <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.deadline" /> : <c:out value="${examDetails.enrolmentCloseDate}"/></h4>

                        <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolledStudents" />: ${examDetails.enrollMentStudents}</h4>
                        <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.enrolmentRequests" />: ${examDetails.enrollMentStudents}</h4>
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
                                    <a type="button" class="btn btn-default"  href="${url}/admin/download/${examId}/exam-enrolment-list-excel"><spring:message code="eEnrolmentList.exportExamList" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                                    <a type="button" class="btn btn-default" href="${url}/admin/manage-exam/enroll/${examId}/2"><spring:message code="enrollmentList.enrollStudent" /> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
                                    <a type="button" class="btn btn-default"  href="${url}/admin/manage-exams/edit/${examId}"><spring:message code="eEnrolmentList.editExam" /> <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a>

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
                                <li><a data-toggle="tab" href="#menu2"><spring:message code="enrollmentList.enrolmentRequests" /> <span class="badge">${examDetails.enrollMentStudents}</span></a></li>


                            </ul>
                            <%--/NAV TABS--%>

                            <!--TAB CONTENT-->
                            <div class="tab-content">
                                <br/>

                                <%--MANAGE USER TABLE --%>
                                <div id="home" class="tab-pane fade in active">

                                    <%--<h3>Course</h3>--%>

                                    <%--!TABLE--%>
                                    <div class="table-responsive">
                                        <br/>
                                        <table id="enrolmentList" class="table table-bordered table-hover">

                                            <thead>
                                            <tr>
                                                <th>#</th>
                                                <th><spring:message code="enrollmentList.studentName" /></th>
                                                <th><spring:message code="enrollmentList.studentId" /></th>
                                                <th><spring:message code="enrollmentList.degreeType" /></th>
                                                <th><spring:message code="enrollmentList.enrolmentDate" /></th>
                                                <%--<th><spring:message code="enrollmentList.examAttempts" /></th>--%>
                                                <th><spring:message code="enrollmentList.action" /></th>
                                            </tr>
                                            </thead>

                                            <tbody>
                                            <c:forEach varStatus="index" var = "eList" items="${enrolmentList}">
                                            <tr>
                                                    <td>${index.count}</td>
                                                    <td><c:out value="${eList.userName}"/></td>
                                                    <td><c:out value="${eList.userId}"/></td>
                                                    <td><c:out value="${eList.degreeType}"/></td>
                                                    <td><c:out value="${eList.enrolmentDate}"/></td>
                                                    <%--<td><c:out value="${1}"/></td>--%>
                                                    <td>

                                                        <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> onclick="unEnrollStudent(${eList.userId},${examId})" class="btn btn-danger btn-xs" type="button" ><spring:message code="studentPageCourseLables.unEnroll" /></button>
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
                                    <%--<h3>Manage Category</h3>--%>

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
                                                <%--<th><spring:message code="enrollmentList.examAttempts" /></th>--%>
                                                <th colspan="4"><spring:message code="enrollmentList.action" /></th>
                                            </tr>
                                            </thead>

                                            <tbody>

                                            <c:forEach var = "rList" items="${requestList}">
                                                <tr>
                                                    <td>1</td>
                                                    <td><c:out value="${rList.userName}"/></td>
                                                    <td><c:out value="${rList.userId}"/></td>
                                                    <td><c:out value="${rList.degreeType}"/></td>
                                                    <td><c:out value="${rList.submissionDate}"/></td>
                                                    <%--<td><c:out value="${1}"/></td>--%>
                                                    <td>
                                                        <div>
                                                            <div class="btn-group" role="group" aria-label="...">
                                                                <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> onclick="enrollStudent(${rList.userId},${examId},${examDetails.courseId})" type="button" class="btn btn-success btn-xs btn-default"><spring:message code="admin.enroll" /></button>
                                                                <button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if> onclick="declineStudent(${rList.userId},${examId})" type="button" class="btn btn-danger btn-xs btn-default"><spring:message code="main.decline" /></button>
                                                            </div>
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

    function  unEnrollStudent(userId,examId){
        var r = confirm('<spring:message code="eEnrolmentList.msg1" />');
        if (r == true) {
//            alert("exam="+examId+" courseId="+courseId)
            $.ajax({
                url: '${url}/admin/manage-exam/un-enroll/'+examId,
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

    function enrollStudent(userId,examId,courseId){
        var r = confirm('<spring:message code="eEnrolmentList.msg2" />');
        if(r == true){
//            alert("exam="+examId+" courseId="+courseId)
            $.ajax({
                url: '${url}/admin/manage-exam/enroll/'+courseId+'/'+examId,
                type: 'POST',
                data: {'userId':userId,'method':"request"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        }else{
            //do nothing
        }
    }

    function declineStudent(userId,examId){
        var r = confirm('<spring:message code="eEnrolmentList.msg3" />');
        if(r == true){

            $.ajax({
                url: '${url}/admin/manage-exam/enrollment-delete/'+examId,
                type: 'POST',
                data: {'userId':userId,'method':"request"}, // An object with the key 'submit' and value 'true;
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
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