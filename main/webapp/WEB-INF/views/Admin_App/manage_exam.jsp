<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 19/08/2017
  Time: 20:46
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
    <title><spring:message code="manageExams2.title" /> </title>
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <%--,--%>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <link href="${url}/resources/app_admin_static/css/manage_exams.css" rel="stylesheet">
    <script src="${url}/resources/app_admin_static/js/index.js"></script>
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
    <%--<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>--%>


</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>
    <h3 class="pageTitle" ><spring:message code="manageExams.manageExams"/> </h3>
    <!--EXAMS STATISTICS BOX -->
    <div class="courseStatsBoxContainer">
        <div class="dash-item__content">

            <ul class="quiz-results">
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${totalEnrolmentRequest}</span>
                    <div class="quiz-results__label"><spring:message code="manageExams.enrolmentRequests"/></div>
                </li>
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${totalActiveExams}</span>
                    <div class="quiz-results__label"><spring:message code="manageExams2.upcomingExams" /></div>
                </li>
                    <li class="quiz-results__item quiz-results__item--average-score">
                        <span class="quiz-results__number quiz-results__number--average-score">${allTeacherCourseExamsCount}</span>
                        <div class="quiz-results__label"><spring:message code="extra.totalExams"/></div>
                    </li>
            </ul>
        </div>
    </div>
    <%--/EXAMS STATISTICS BOX--%>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">

        <div class="majorFilter">
            <a href="${url}/admin/major/null" class="majorLable"><spring:message code="manageUsers2.major" /></a>&nbsp;
            <select  autocomplete="off" id="majorSelect" class="selectMajor" >
                <c:if test="${currentMajor ne null}"><option value="">${currentMajor.majorName}</option></c:if>
                <option value=""><spring:message code="manageUsers2.default" /></option>
                <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                    <c:if test="${major.majorId ne currentMajor.majorId}">
                        <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <br/>

        <!--Big cards-->
        <div class="col-sm-2 exm">
            <%--<br/><br/>--%>
            <%--<div class="  filterBoxCourse ">--%>
                <%--<div>--%>

                        <%--&lt;%&ndash;<a href="#" onclick="filterSemester('')" class=" acfText list-group-item <c:if test='${param.semester eq null}'>activeCustomFilter</c:if>">${"Main Exams"}</a>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<a href="#" onclick="filterSemester('')" class=" acfText list-group-item <c:if test='${param.semester eq null}'>activeCustomFilter</c:if>">${"Child Exams"}</a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                            <%--<div style="margin-top: -11px" class="list-group">--%>
                                <%--<a href="${url}/admin/manage-exams" class="acfText list-group-item <c:if test='${param.examType ne "child_exam"}'>active</c:if>">${"Main Exams"}</a>--%>
                                <%--<a href="${url}/admin/manage-exams?examType=child_exam" class="acfText list-group-item <c:if test='${param.examType eq "child_exam"}'>active</c:if>">${"Child Exams"}</a>--%>
                            <%--</div>--%>

                <%--</div>--%>
            <%--</div>--%>


            <div>
                <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageUsers2.semester" /></h4>
                <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder="Search"></div>
            </div>
            <div class="card  filterBoxCourse">
                <div>
                    <div style="margin-top: -11px" class="list-group">
                        <a href="#" onclick="filterSemester('','${examType}')" class="list-group-item <c:if test='${param.semester eq null}'>activeFilter</c:if>">${"All"}</a>
                        <c:forEach var = "sslist" items="${semesterList}" varStatus="index">
                            <a href="#" onclick="filterSemester(${sslist.semesterId},'${examType}')" class="list-group-item <c:if test='${param.semester eq sslist.semesterId}'>activeFilter</c:if>">${sslist.semesterCode}</a>
                        </c:forEach>
                    </div>

                </div>
            </div>


        </div>
        <%--<!--Big cards-->--%>

        <%--Manage Exams--%>
        <div class="col-sm-10">

            <div class="card bigCardContainer tableContainer ">

                <%--<div class="majorFilter">
                    <a href="${url}/admin/major/null" class="majorLable"><spring:message code="manageUsers2.major" /></a>&nbsp;
                    <select  autocomplete="off" id="majorSelect" class="selectMajor" >
                        <c:if test="${currentMajor ne null}"><option value="">${currentMajor.majorName}</option></c:if>
                        <option value=""><spring:message code="manageUsers2.default" /></option>
                        <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                            <c:if test="${major.majorId ne currentMajor.majorId}">
                                <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>--%>


                <%--<!--NAV TABS -->--%>
                <ul class="nav nav-tabs tabSelectList">

                    <li class="<c:if test="${examType eq 'exams'}">active</c:if>"><a id="examTab" data-toggle="tab" href="#home"><spring:message code="manageExams.manageExams"/></a></li>
                    <li class="<c:if test="${examType eq 'clexams'}">active</c:if>"><a id="clExamTab" data-toggle="tab" href="#home"><spring:message code="manageExams.manageClearanceExams"/></a></li>
                    <c:if test="${currentUserRole eq 'admin'}">
                        <li><a data-toggle="tab" href="#menu1"><spring:message code="manageExams.addExam"/></a></li>
                    </c:if>

                </ul>
                <%--/NAV TABS--%>

                <!--TAB CONTENT-->
                <div class="tab-content">

                    <%--MANAGE USER TABLE --%>
                    <div id="home" class="tab-pane fade in active">

                        <%--<div class="table-responsive">--%>
                           <%-- <c:if test="${param.examType ne 'child_exam'}">
                                <h3><spring:message code="manageExams2.manageMainExam" /> </h3>
                            </c:if>
                            <c:if test="${param.examType eq 'child_exam'}">
                                <h3><spring:message code="manageExams2.h2" /></h3>
                            </c:if>--%>


                            <c:if test="${examType ne 'clexams'}">
                                <table id="examTable" class="table table-bordered table-hover">

                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code="studentPageCourseLables.courseName"/></th>
                                        <th><spring:message code="studentPageCourseLables.courseShortName"/></th>
                                        <th><spring:message code="enrollmentList.ExamName" /></th>
                                        <th><spring:message code="manageUsers2.semester" /></th>
                                        <th><spring:message code="manageExams2.dateOfExam" /></th>
                                        <th><spring:message code="manageExams.participants"/></th>
                                        <th><spring:message code="reports.action"/></th>
                                    </tr>
                                    </thead>

                                    <tbody>

                                    <c:forEach var = "sysExam" items="${examTableList}" varStatus="index">

                                        <tr>
                                            <td>${index.count}</td>
                                            <td><a data-toggle="tooltip" data-placement="top" title="${sysExam.parentCourseId}"href="${url}/admin/manage-exams/enrolmentList/${sysExam.id}/2"><c:out value="${sysExam.parentCourseName}"/></a></td>
                                            <td><a data-toggle="tooltip" data-placement="top" title="${sysExam.parentCourseId}"href="${url}/admin/manage-exams/enrolmentList/${sysExam.id}/2"><c:out value="${sysExam.parentCourseShortName}"/></a></td>
                                            <td><span data-toggle="tooltip" data-placement="top" title="${sysExam.id}" href="#"><c:out value="${sysExam.examName}"/></span></td>
                                            <td><c:out value="${sysExam.semesterCode}"/></td>
                                            <td><c:out value="${sysExam.dateOfExam}"/></td>
                                            <td><c:out value="${sysExam.enrollMentStudents}"/></td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action"/>
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right">
                                                        <li><a href="${url}/admin/manage-exam/enroll/${sysExam.id}/1"><spring:message code="enrollmentList.enrollStudent"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-exams/enrolmentList/${sysExam.id}/2"><spring:message code="enrollmentList.enrolmentList"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                                  <%--      <li><a href="${url}/admin/manage-courses/course-exam/${sysExam.id}"><spring:message code="manageExams.viewCourseExams"/>  &nbsp; <span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li> --%>
                                                        <li><a href="${url}/admin/exam/grade/addExamGrades/${sysExam.id}"><spring:message code="courseExams.addGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/exam/grade/overview/${sysExam.id}"><spring:message code="extra.viewGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-book" aria-hidden="true"></span></a></li>
                                                        <%--<li><a href="${url}/admin/manage-exams/exam/add-clearance-exam/${sysExam.id}"><spring:message code="manageExams2.addChildExam" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>--%>
                                                        <li><a onclick="addClExam(${sysExam.id})"  href="#"><spring:message code="manageExams2.addChildExam" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}">
                                                            <li><a href="${url}/admin/manage-exams/edit/${sysExam.id}"><spring:message code="coursePage.edit"/>    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                            <li><a  onClick="deleteExam(${sysExam.id})" href="#"><spring:message code="coursePage.delete"/>  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>

                                   <%--     <tr>
                                            <td>${index.count}</td>
                                            <td><a data-toggle="tooltip" data-placement="top" title="${sysExam.courseId}"href="${url}/admin/manage-exams/enrolmentList/${sysExam.gradeItemId}/2"><c:out value="${sysExam.courseName}"/></a></td>
                                            <td><a data-toggle="tooltip" data-placement="top" title="${sysExam.courseId}"href="${url}/admin/manage-exams/enrolmentList/${sysExam.gradeItemId}/2"><c:out value="${sysExam.courseShortName}"/></a></td>
                                            <td><span data-toggle="tooltip" data-placement="top" title="${sysExam.gradeItemId}" href="#"><c:out value="${sysExam.gradeItemName}"/></span></td>
                                             <td><c:out value="${sysExam.semesterCode}"/></td>
                                            <td><c:out value="${sysExam.dateOfExam}"/></td>
                                            <td><c:out value="${sysExam.totalEnrollments}"/></td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action"/>
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right">
                                                        <li><a href="${url}/admin/manage-exam/enroll/${sysExam.childCourseId}/${sysExam.gradeItemId}/1"><spring:message code="enrollmentList.enrollStudent"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-exams/enrolmentList/${sysExam.gradeItemId}/2"><spring:message code="enrollmentList.enrolmentList"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-courses/course-exam/${sysExam.childCourseId}"><spring:message code="manageExams.viewCourseExams"/>  &nbsp; <span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-exams/exam/add-clearance-exam/${sysExam.childCourseId}/${sysExam.gradeItemId}"><spring:message code="manageExams2.addChildExam" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/course/grade/overview/${sysExam.childCourseId}"><spring:message code="extra.viewGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-book" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/grades/add-grades/${sysExam.childCourseId}/${sysExam.gradeItemId}/1"><spring:message code="courseExams.addGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}">
                                                            <li><a href="${url}/admin/manage-exams/edit/${sysExam.gradeItemId}"><spring:message code="coursePage.edit"/>    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                            <li><a  onClick="deleteExam(${sysExam.gradeItemId})" href="#"><spring:message code="coursePage.delete"/>  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>   --%>
                                    </c:forEach>

                                    </tbody>

                                </table>
                            </c:if>



                            <c:if test="${examType eq 'clexams'}">

                                <table cellspacing="0" width="100%" id="clexamTable" class="table table-bordered table-hover">

                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code="manageExams2.parentExamName" /></th>
                                        <th><spring:message code="manageExams2.childExamName" /></th>
                                        <th><spring:message code="studentHome.course" /></th>
                                        <th><spring:message code="manageUsers2.semester" /></th>
                                        <th><spring:message code="manageExams2.dateOfExam" /></th>
                                        <th><spring:message code="manageExams.participants"/></th>
                                        <th><spring:message code="reports.action"/></th>

                                    </tr>
                                    </thead>

                                    <tbody>

                                    <c:forEach var = "sysCExam" items="${clearanceExamTableList}" varStatus="index">

                                        <tr>
                                            <td>${index.count}</td>
                                            <td><c:out value="${sysCExam.parentExamName}"/></td>
                                            <td><c:out value="${sysCExam.examName}"/></td>
                                            <td><c:out value="${sysCExam.parentCourseName}"/></td>
                                            <td><c:out value="${sysCExam.semesterCode}"/></td>
                                            <td><c:out value="${sysCExam.examDate}"/></td>
                                            <td><c:out value="${sysCExam.enrolledStudents}"/></td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action"/>
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right">
                                                        <li><a href="${url}/admin/manage-exams/clearance-exam/enroll-students/${sysCExam.clearanceExamId}"><spring:message code="enrollmentList.enrollStudent"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-exams/clearance-exam/enrollment-list/${sysCExam.clearanceExamId}"><spring:message code="enrollmentList.enrolmentList"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                                        <%--<li><a href="${url}/admin/exam/grade/overview/${sysCExam.parentExamId}"><spring:message code="manageExams.viewCourseExams"/>  &nbsp; <span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>--%>
                                                        <li><a href="${url}/admin/manage-exams/clearance-exam/view/grades/${sysCExam.clearanceExamId}"><spring:message code="extra.viewGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-book" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/mange-exams/clearance-exam/add/grades/${sysCExam.clearanceExamId}"><spring:message code="courseExams.addGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span></a></li>
                                                        <%--<li><a href="${url}/admin/mange-exams/clearance-exam/add/grades/${sysCExam.clearanceExamId}"><spring:message code="courseExams.addGrades"/>  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span></a></li>--%>
                                                        <c:if test="${currentUserRole eq 'admin'}">
                                                            <li><a href="${url}/admin/manage-exams/clearance-exam/edit/${sysCExam.clearanceExamId}"><spring:message code="coursePage.edit"/>    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                            <li><a  onClick="deleteClExam(${sysExam.clearanceExamId})" href="#"><spring:message code="coursePage.delete"/>  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>

                                </table>
                            </c:if>

                        </div>





                        <%--<!--end of .table-responsive-->--%>

                    <%--/TAB CONTENT--%>

                        <c:if test="${currentUserRole eq 'admin'}">
                        <%--<!--MANAGE CLASSES TAB-->--%>
                    <div id="menu1" class="tab-pane fade">
                        <h3><spring:message code="manageExams.addExam"/></h3>

                        <div class="addBoxContainer">
                            <div class="floating-box">

                                <div class="addButtonBox">
                                    <a class="courseButton" href="${url}/admin/manage-exams/add">
                                        <spring:message code="manageExams.addNewExam"/><br/>
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                                </div>
                                <%--<div class="addButtonBox">
                                    <a class="courseButton" data-toggle="modal" data-target="#popUpWindow" href="#">
                                        <span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
                                        <br/>
                                        <spring:message code="manageExams.importExamExcel"/></a>
                                    <a href="#" data-toggle="modal" data-target="#downloadChildCourseExcel" class="unEnrollText"><br/><br/><spring:message code="manageExams.downloadExamExcelTemplate"/></a>
                                </div>--%>


                            </div>
                        </div>
                    </div>
                        </c:if>
                </div>
                    <%--/MANAGE CLASSES TAB--%>
                </div>
            <%--Manage Exams --%>
            </div>
    </div>

    <!--MAIN CARD CONTAINER -->


    <!-- Confirmation Modal -->
    <div class="modal fade"  id="confirmModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title"><spring:message code="manageExams2.h3" /></h4>
                </div>
                <p class="modal-title"><spring:message code="manageExams2.msg1" /></p>
                <div class="modal-body">
                    <button id="yesBtn" type="button" class="btn btn-default" ><spring:message code="manageExams2.yes" /></button>
                    <button id="noBtn" type="button" class="btn btn-default"><spring:message code="manageExams2.no" /></button>
                </div>
            </div>
        </div>
    </div>
    <!-- Confirmation Modal -->


<%--CLASS FILE IMPOORT MODEL FORM --%>
<div class="modal fade" id="downloadChildCourseExcel">
    <div style="width: 450px" class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times</button>
                <h3 style="text-align: center" class="modal-title"> <spring:message code="manageCourse2.downloadCourseExcelTemp" /></h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <%--FILE FORM --%>
                <form:form  id="classFile" action="${url}/admin/download/examTemplate" method="GET"
                            enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="majorId2"><spring:message code="manageCourse2.selectMajorOfCourse" /></label>

                        <select class="form-control" id="majorId2" name="majorId" required autocomplete="off">
                            <option value=""><i><spring:message code="manageCourse2.selectStudentMajor" /></i></option>
                            <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                                <option value="${major.majorId}">${major.majorName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="semesterSelect"><spring:message code="manageCourse2.SelectCourseSemester" /></label>
                        <select id="semesterSelect" class="form-control" name="semesterId" required autocomplete="off">
                            <option value="10">2017-2018-1</option>
                            <option value=""><i><spring:message code="manageCourse2.SelectCourseSemester" /></i></option>
                            <c:forEach var = "semester" items="${semesterSelectList}" varStatus="index">
                                <option value="${semester.semesterId}">${semester.semesterCode}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" value="Import Excel File" class="btn btn-primary btn-block"><spring:message code="manageCourse2.download" /></button>
                        <a style="float: left; color: inherit" href="${url}/download/teacher-list-excel" class="unEnrollText"><spring:message code="manageCourse2.downloadTeacherListExcelTemplate" /></a>
                    </div>
                </form:form>
                <%--FILE FORM --%>
            </div>

        </div>
    </div>
</div>
<%--CLASS FILE IMPOORT MODEL FORM--%>

</div>
<!--Content Ends  -->


<%--EXAM FILE IMPOORT MODEL FORM --%>
<div class="modal fade" id="popUpWindow">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times</button>
                <h3 class="modal-title"><spring:message code="manageExams2.importExamExcelFile" /></h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <%--FILE FORM --%>
                <form id="uploadExamForm"  method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="majorId2"><spring:message code="manageCourse2.selectMajorOfCourse" /></label>

                        <select class="form-control" id="majorId3" name="majorId" required autocomplete="off">
                            <option value=""><i><spring:message code="manageCourse2.selectStudentMajor" /></i></option>
                            <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                                <option value="${major.majorId}">${major.majorName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="semesterSelect"><spring:message code="manageCourse2.SelectCourseSemester" /></label>
                        <select id="semesterSelect2" class="form-control" name="semesterId" required autocomplete="off">
                            <option value="10">2017-2018-1</option>
                            <option value=""><i><spring:message code="manageCourse2.SelectCourseSemester" /></i></option>
                            <c:forEach var = "semester" items="${semesterSelectList}" varStatus="index">
                                <option value="${semester.semesterId}">${semester.semesterCode}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input name="examExcelFile"  required type="file">
                    <%--<input type="submit" value="Import Excel File">--%>
                    <div class="modal-footer">
                        <button type="submit" onclick="uploadExamExcel()" class=" btn-primary btn-block"><spring:message code="main.importExcelFile" /></button>
                    </div>
                </form>
                <%--FILE FORM --%>

            </div>
            <!--Button-->
        </div>
    </div>
</div>
<%--EXAM FILE IMPOORT MODEL FORM--%>


<%--CONFIRMATION FORM --%>
<div class="modal fade" id="confirmationTableModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div id="modelHeader" class="modal-header">
                <h4 class="alert alert-danger modal-title"><spring:message code="manageExams2.msg2" /> </h4>
                <%--<div >--%>
                <%--<strong>Danger!</strong> Indicates a dangerous or potentially negative action.--%>
                <%--</div>--%>
            </div>
            <!--Body (form)-->
            <div class="modal-body">
                <div class="holdBox">
                    <table id="confTable" class="table  table-striped table-fixed table-condensed">
                        <thead id="tableHead">
                        <th><spring:message code="manageUsers2.semester" /></th>
                        <th><spring:message code="courseForm.courseName" /></th>
                        <th><spring:message code="enrollmentList.ExamName" /></th>
                        <th><spring:message code="manageExams2.dateOfExam" /></th>
                        <th><spring:message code="enrollmentList.deadline" /></th>

                        </thead>
                        <tbody id="userConfirmTable">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>

                        </tbody>
                    </table>
                </div>
                <button type="button" class="btn btn-primary btn-block" data-dismiss="modal"><spring:message code="manageUsers2.ok" /></button>
            </div>


        </div>
    </div>
</div>



<script>

    function filterSemester(semesterId,examType) {
        // $('#courseTable tbody').remove();
        if(examType === 'exams'){
            if(semesterId === '' ){

                <c:if test="${!empty currentMajor}">
                     window.location.href = '${url}/admin/manage-exams/exams?major=${majorId}';
                </c:if>
                <c:if test="${empty currentMajor}">
                    window.location.href = '${url}/admin/manage-exams/exams/';
                </c:if>
            }else {
                <c:if test="${!empty currentMajor}">
                window.location.href = '${url}/admin/manage-exams/exams?major=${majorId}&semester='+semesterId;
                </c:if>
                <c:if test="${empty currentMajor}">
                     window.location.href = '${url}/admin/manage-exams/exams?semester='+semesterId;
                </c:if>

            }

        }else{
            if(semesterId === '' ){

                <c:if test="${empty currentMajor}">
                window.location.href = '${url}/admin/manage-exams/clexams';
                </c:if>

                <c:if test="${!empty currentMajor}">
                    window.location.href = '${url}/admin/manage-exams/clexams?major=${majorId}';
                </c:if>

            }else{
                <c:if test="${empty currentMajor}">
                    window.location.href = '${url}/admin/manage-exams/clexams?semester='+semesterId;
                </c:if>
                <c:if test="${!empty currentMajor}">
                    window.location.href = '${url}/admin/manage-exams/clexams?major=${majorId}&semester='+semesterId;
                </c:if>

            }

        }
    }

    $(document).ready(function() {
        // $('#confirmationTableModel').modal('toggle');


        $("#examTab").click(function(){
            <c:if test="${examType ne 'exams'}">
            window.location.href = '${url}/admin/manage-exams/exams';
            </c:if>
        });



        $("#clExamTab").click(function(){
            <c:if test="${examType ne 'clexams'}">
            window.location.href = '${url}/admin/manage-exams/clexams';
            </c:if>
        });




        $('#examTable').DataTable({
//            "scrollX": true,
//            "scrollY": 600,
//            scrollCollapse: true,
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });


        <%--$('#confTable').DataTable({--%>
            <%--"paging":   false,--%>
            <%--"ordering": false,--%>
            <%--"info":     false,--%>
            <%--"scrollX": false,--%>
            <%--"searching":false,--%>
<%--//            scrollCollapse: true,--%>
            <%--<c:if test="${pageContext.response.locale eq 'cn'}">--%>
            <%--"language": translation--%>
            <%--</c:if>--%>
        <%--});--%>

    });



    /**
     * *========================END --JQUERY custom confirmation dialog plugin-- END =====================================
     */

        $('#yesBtn').on('click',function() {
            $('#examForm').trigger("reset");
            $('#confirmModal').modal('toggle');
        });

        $('#noBtn').on('click',function() {
            location.reload(true);
        });


        ///date
    $( function() {
        $( ".datepicker" ).datepicker({
            dateFormat: 'yy-mm-dd'
        });


    } );

    function deleteExam(examId) {
        var r = confirm('<spring:message code="manageExams2.msg3" />');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-exams/delete-exam/'+examId,
                type:'post',
                data:$(this).serialize(),
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

    function deleteClExam( clexamId ) {
        var r = confirm('<spring:message code="manageExams2.msg4" />');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-exams/deleteClexam/'+clexamId,
                type:'post',
                data:$(this).serialize(),
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

    function addClExam( ExamId ) {

            $.ajax({
                url:'${url}/admin/manage-exams/exam/add-clearance-exam/check/' + ExamId,
                async: true,
                type:'post',
                dataType:"text",
                data:$(this).serialize(),
                success:function(result){
                    if(result === '200'){
                        window.location.href = '${url}/admin/manage-exams/exam/add-clearance-exam/' + ExamId;
                    }else{
                        alert('<spring:message code="manageExams2.msg5" />');
                    }

                 },
               error : function(){
                    <%--alert('<spring:message code="manageExams2.msg5" />');--%>
                   alert('<spring:message code="main.msgError" />');
                }
            });

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

    $("#majorSelect").change(function(){

        var thisvalue = $(this).find("option:selected").val();

        console.log(${semesterId});
        if(thisvalue === ""){
            window.location.href = '${url}/admin/manage-exams/exams';
           }else {
            <c:if test="${examType eq 'exams'}">
            window.location.href = '${url}/admin/manage-exams/exams?major='+thisvalue;
            </c:if>
            <c:if test="${examType eq 'clexams'}">
            window.location.href = '${url}/admin/manage-exams/clexams?major='+thisvalue;
            </c:if>

        }


    });


    <%--$("#majorSelect").change(function(){--%>

        <%--var thisvalue = $(this).find("option:selected").val();--%>

        <%--console.log(${semesterId});--%>
        <%--if(thisvalue === ""){--%>
            <%--<c:if test="${examType eq 'exams'}">--%>
                <%--window.location.href = '${url}/admin/manage-exams/?examType=exams';--%>
            <%--</c:if>--%>
            <%--<c:if test="${examType ne 'exams'}">--%>
                 <%--window.location.href = '${url}/admin/manage-exams';--%>
            <%--</c:if>--%>

        <%--}else {--%>
            <%--<c:if test="${examType eq 'clexams'}">--%>
                <%--window.location.href = '${url}/admin/manage-exams/?examType=clexams&major='+thisvalue;--%>
            <%--</c:if>--%>
            <%--<c:if test="${examType ne 'clexams'}">--%>
             <%--window.location.href = '${url}/admin/manage-exams/?major='+thisvalue;--%>
            <%--</c:if>--%>

        <%--}--%>


    <%--});--%>

    function uploadExamExcel() {
        var data = new FormData($("#uploadExamForm")[0]);

        $('#uploadExamForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/admin/manage-exams/add-child-course-exam-excel',
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                    $('#importCourseExcel').modal('toggle');
                    $("#uploadChildCourseForm").get(0).reset();
                    $("#semesterSelect").val('');
                    console.log(data.list);
                    <%--if(data.status_code === 406){--%>
                        <%--alert('Error Uploading Data !!');--%>

                        <%--$('#modelHeader').empty();--%>
                        <%--$('#userConfirmTable').empty();--%>
                        <%--$('#tableHead').empty();--%>
                        <%--$('#modelHeader').append('<h4 class="alert alert-danger modal-title">Error Parent Course ID Not In System !!</h4>');--%>

                        <%--$('#tableHead').append(--%>
                            <%--'<tr>'+--%>
                            <%--'<th>#</th>'+--%>
                            <%--'<th>Parent Course Id</th>'+--%>
                            <%--'<th>Course Short Name</th>'+--%>
                            <%--'<th>Teacher Name</th>'+--%>
                            <%--'<th>Enrolment Deadline</th>'+--%>
                            <%--'</tr>'--%>
                        <%--);--%>

                        <%--for (var i = 0, len = data.list.length; i < len; i++) {--%>
                            <%--console.log(data.list[i].userName);--%>
                            <%--$('#userConfirmTable').append(--%>
                                <%--' <tr>'+--%>
                                <%--'<td>'+(i+1)+'</td>'+--%>
                                <%--'<td>'+data.list[i].parentCourseId+'</td>'+--%>
                                <%--'<td>'+data.list[i].childCourseName+'</td>'+--%>
                                <%--'<td>'+data.list[i].teacherName+'</td>'+--%>
                                <%--'<td>'+data.list[i].enrolmentDeadline+'</td>'+--%>
                                <%--'</tr>'--%>
                            <%--);--%>

                        <%--}--%>

                        <%--$('#confirmationTableModel').modal('toggle');--%>
                    <%--}--%>
                    <%--if(data.status_code === 200){--%>
                        <%--$('#modelHeader').empty();--%>
                        <%--$('#userConfirmTable').empty();--%>
                        <%--$('#tableHead').empty();--%>
<%--//                        $('#addNewUsers').empty()--%>
                        <%--$('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') Child Courses Added Successfully </h4>');--%>

                        <%--$('#tableHead').append(--%>
                            <%--'<tr>'+--%>
                            <%--'<th>#</th>'+--%>
                            <%--'<th>Parent Course Id</th>'+--%>
                            <%--'<th>Course Short Name</th>'+--%>
                            <%--'<th>Teacher Name</th>'+--%>
                            <%--'<th>Enrolment Deadline</th>'+--%>
                            <%--'</tr>'--%>
                        <%--);--%>

                        <%--for (var i = 0, len = data.list.length; i < len; i++) {--%>
                            <%--console.log(data.list[i].userName);--%>
                            <%--$('#userConfirmTable').append(--%>
                                <%--' <tr>'+--%>
                                <%--'<td>'+(i+1)+'</td>'+--%>
                                <%--'<td>'+data.list[i].parentCourseId+'</td>'+--%>
                                <%--'<td>'+data.list[i].childCourseName+'</td>'+--%>
                                <%--'<td>'+data.list[i].teacherName+'</td>'+--%>
                                <%--'<td>'+data.list[i].enrolmentDeadline+'</td>'+--%>
                                <%--'</tr>'--%>
                            <%--);--%>

                            <%--&lt;%&ndash;$('#addNewUsers').prepend(&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<tr class="success">'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>#</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td><a  href="${url}/admin/profile/view/'+data.list[i].userId+'">'+data.list[i].firstName+'</a> </td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>'+data.list[i].userId+'</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>'+data.list[i].role+'</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>'+data.list[i].gender+'</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td></td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>'+data.list[i].country+'</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>Never</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<div class="dropdown">'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<span class="caret"></span></button>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<ul class="dropdown-menu dropdown-menu-right">'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<li><a href="${url}/admin/profile/view/'+data.list[i].userId+'">#View    &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<li><a href="${url}/admin/profile/edit/'+data.list[i].userId+'/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'<li><a style="cursor: pointer" onclick="deleteStudent('+data.list[i].userId+')"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'</ul>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'</div>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'</td>'+&ndash;%&gt;--%>
                            <%--&lt;%&ndash;'</tr>'&ndash;%&gt;--%>
                            <%--&lt;%&ndash;);&ndash;%&gt;--%>

                        <%--}--%>
                        // $('#confirmationTableModel').modal('toggle');
                    // }
                },
                error : function(data){
                    $('#importCourseExcel').modal('toggle');
                    alert('<spring:message code="main.msgError" />');
                }
            });
            return false;
        });

    }
</script>

</body>

</html>