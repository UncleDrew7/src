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
    <title><spring:message code="manageCourse2.title" /> </title>

    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
    <script type="text/javascript" src="${url}/resources/app_admin_static/js/zscriptCourseMangement.js"></script>
</head>

<body>

<%--<!--Content Begins  -->--%>
<div class="content">

    <br/>
    <h3 class="pageTitle"><spring:message code="manageCourse.manageCourses" /></h3>

    <%--<!--USER STATISTICS BOX -->--%>
    <div class="courseStatsBoxContainer">


        <c:choose>
            <c:when test="${currentUserRole eq 'teacher'}">
                <div class="dash-item__content">
                    <ul class="quiz-results">
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${parentCourseCount}</span>
                            <div class="quiz-results__label"><spring:message code="extra.parentCourseCount" /></div>
                        </li>
                        <%--<li class="quiz-results__item quiz-results__item--average-score">--%>
                            <%--<span class="quiz-results__number quiz-results__number--average-score">${activeTeacherCoursesCount}</span>--%>
                            <%--<div class="quiz-results__label"><spring:message code="extra.activeCourses" /></div>--%>
                        <%--</li>--%>
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${activeTeacherCoursesCount}</span>
                            <div class="quiz-results__label"><spring:message code="studentMyCourses.myCourses" /></div>
                        </li>

                    </ul>
                </div>
                <br/>
            </c:when>
            <c:otherwise>
                <div class="dash-item__content">
                    <ul class="quiz-results">
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${totalCourses}</span>
                            <div class="quiz-results__label"><spring:message code="manageCourse.totalCourses" /></div>
                        </li>
                        <%--<li class="quiz-results__item quiz-results__item--average-score">--%>
                            <%--<span class="quiz-results__number quiz-results__number--average-score">${totalGenralCourse}</span>--%>
                            <%--<div class="quiz-results__label"><spring:message code="manageCourse.generalCourses" /></div>--%>
                        <%--</li>--%>
                        <%--<li class="quiz-results__item quiz-results__item--average-score">--%>
                            <%--<span class="quiz-results__number quiz-results__number--average-score">${totalDoubleDegreeCourse}</span>--%>
                            <%--<div class="quiz-results__label"><spring:message code="manageCourse.doubleDegreeCourses" /></div>--%>
                        <%--</li>--%>
                        <li class="quiz-results__item quiz-results__item--average-score">
                            <span class="quiz-results__number quiz-results__number--average-score">${totalSemesters}</span>
                            <div class="quiz-results__label"><spring:message code="manageCourse.semester" /></div>
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
            <a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;
            <select  autocomplete="off" id="majorSelect" class="selectMajor" >
                <c:if test="${currentMajor ne null}"><option value="">${currentMajor.majorName}</option></c:if>
                <option value=""><spring:message code="main.default" /></option>
                <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                    <c:if test="${major.majorId ne currentMajor.majorId}">
                        <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <%--<!--MAIN TAB CONTAINER -->--%>
        <div class="mainTabContainer">

            <%--<!--NAV TABS -->--%>
                <ul class="nav nav-tabs">
                    <li class="<c:if test="${object eq 'majors'}">active</c:if>"><a id="majorTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageCourse2.parenetCourse" /></a></li>
                    <li class="<c:if test="${object eq 'courses'}">active</c:if>"><a id="courseTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageCourse.manageCourses" /></a></li>
                    <c:if test="${currentUserRole eq 'admin'}">
                        <li class="<c:if test="${object eq 'semesters'}">active</c:if>"><a id="semesterTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageSemesters.manageSemesters" /></a></li>
                        <li><a data-toggle="tab" href="#addTab"><spring:message code="manageCourse2.add" /> </a></li>
                    </c:if>
                </ul>
            <%--   <ul class="nav nav-tabs">
                       <li class="<c:if test="${object eq 'majors'}">active</c:if>"><a id="majorTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageCourse2.parenetCourse" /></a></li>
                       <li class="<c:if test="${object eq 'courses'}">active</c:if>"><a id="courseTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageCourse.manageCourses" /></a></li>
                     <c:if test="${currentUserRole eq 'admin'}">
                         <li class="<c:if test="${object eq 'semesters'}">active</c:if>"><a id="semesterTab" data-toggle="tab" href="#mainTabView"><spring:message code="manageSemesters.manageSemesters" /></a></li>
                         <li><a data-toggle="tab" href="#addTab"><spring:message code="manageCourse2.add" /> </a></li>
                     </c:if>
                   </ul> --%>
                   <%--/NAV TABS--%>

            <%--<!--TAB CONTENT-->--%>
            <div class="tab-content">

                <div id="mainTabView" class="tab-pane fade in active">


                    <c:if test="${object eq 'majors'}">
                        <h3><spring:message code="manageCourse2.h1" /></h3>
                        <%--!TABLE--%>
                        <div class="table-responsive">


                            <div class="col-sm-2">
                                <br/>
                                <c:if test="${currentUserRole eq 'admin'}">
                                    <a href="${url}/admin/edit-parent-course-major/major" type="button" class="btn btn-default"><spring:message code="manageCourse2.editMajor" /> </a>
                                </c:if>
                                <c:if test="${currentUserRole eq 'teacher'}">
                                    <br/>
                                </c:if>
                                <div>
                                    <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageCourse2.majorCodes" /></h4>
                                    <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder="Search"></div>
                                </div>
                                <div class="card  filterBoxCourse ">
                                    <div>
                                        <div style="margin-top: -11px" class="list-group">
                                            <a href="${url}/admin/manage-courses/majors" class="list-group-item <c:if test='${param.major eq null}'>activeFilter</c:if>">${"All"}</a>
                                            <c:forEach var = "mslist" items="${majorSelectList}" varStatus="index">
                                                <a href="#" onclick="
                                                        filterMajor(${mslist.majorId})" class="list-group-item <c:if test='${param.major eq mslist.majorId}'>activeFilter</c:if>">${mslist.majorShortName}</a>
                                            </c:forEach>
                                        </div>

                                    </div>
                                </div>

                            </div>

                            <div class="col-sm-10">
                                <br/>
                                <div class="table-responsive">
                                    <br/>
                                    <table id="parentCourseTable" class="table table-bordered table-hover">

                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th><spring:message code="manageCourse2.majorCode" /></th>
                                            <th><spring:message code="manageCourse2.courseId" /></th>
                                            <th><spring:message code="manageCourse2.courseName" /></th>
                                            <th><spring:message code="manageCourse2.courseNameCN" /></th>
                                            <th><spring:message code="manageCourse2.courseType" /></th>
                                            <th><spring:message code="manageCourse2.credits" /></th>
                                            <th><spring:message code="main.action" /></th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var = "sysParentCourse" items="${parentCourseList}" varStatus="index">
                                            <tr>
                                                <td>${index.count}</td>
                                                <td><c:out value="${sysParentCourse.majorShortName}"/></td>
                                                <td><c:out value="${sysParentCourse.courseName}"/></td>
                                                <td><a href="${url}/admin/parent-course/view/${sysParentCourse.parentCourseId}"><c:out value="${sysParentCourse.courseShortName}"/></a></td>
                                                <td><c:out value="${sysParentCourse.courseShortNameCN}"/></td>
                                                <td><c:out value="${sysParentCourse.courseType}"/></td>
                                                <td><c:out value="${sysParentCourse.credits}"/></td>
                                                <td >
                                                    <c:if test="${currentUserRole eq 'admin'}">
                                                        <div class="dropdown">
                                                            <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                                                <span class="caret"></span></button>
                                                            <ul class="dropdown-menu dropdown-menu-right actionDropdown">
                                                                <li><a href="${url}/admin/parent-course/create-child-course/${sysParentCourse.parentCourseId}?nv=mpc">Add Child Course  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                                <%--<li><a href="${url}/admin/parent-course/view/${sysParentCourse.parentCourseId}">View Parent Course &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>--%>
                                                                <li><a href="${url}/admin/edit-parent-course-major/parent_course/${sysParentCourse.parentCourseId}?nv=mpc"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                                    <%--<c:if test="${currentUserRole eq 'admin'}"><li ><a style="cursor: pointer" id="delete" onClick="deleteCourse(${sysCourse.childCourseId})"  ><spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li></c:if>--%>

                                                            </ul>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${currentUserRole eq 'teacher'}">
                                                        <a href="${url}/admin/parent-course/view/${sysParentCourse.parentCourseId}" style="width: 70px ;color: #fff" class="btn btn-danger btn-xs" type="button" ><spring:message code="extra.view" /></a>
                                                    </c:if>

                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </c:if>


                    <c:if test="${object eq 'courses'}">



                        <%--!TABLE--%>
                        <div class="table-responsive">
                            <div class="col-sm-2">

                                <h3><spring:message code="studentHome.course" /></h3>

                                <br/>
                                <%--<a href="${url}/admin/parent-course" type="button" class="btn btn-default"><spring:message code="manageCourse2.parentChildCourse" /> </a>--%>

                                <label><input type="checkbox" id="getClosed" name="getClosed" onclick="includeClosedCourses()" value="getClosed" checked style="height: 18px;width: 18px;"> <spring:message code="manageCourse.closedCourses" /></label>

                                <div>
                                    <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageUsers2.semester" /></h4>
                                    <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder="Search"></div>
                                </div>
                                <div class="card  filterBoxCourse ">
                                    <div>
                                        <div style="margin-top: -11px" class="list-group" id="semesterBox">
                                            <a href="${url}/admin/manage-courses/courses"  class="list-group-item <c:if test='${param.filterId eq 0}'>activeFilter</c:if>">${"All"}</a>
                                            <c:forEach var = "sslist" items="${semesterSelectList}" varStatus="index">
                                                <a href="#" onclick="filterSemester(${sslist.semesterId})" class="list-group-item <c:if test='${param.filterId eq sslist.semesterId}'>activeFilter</c:if>">${sslist.semesterCode}</a>
                                            </c:forEach>
                                        </div>

                                    </div>
                                </div>

                                <br/>

                                <div class="form-group ">
                                    <div class="btn-group" role="group" aria-label="...">
                                        <a type="button" class="btn btn-default"  onclick="closeCourses()"><spring:message code="manageCourse2.closeCourses" /> <span class="glyphicon glyphicon-time" aria-hidden="true"></span></a>
                                            <%--<a type="button" class="btn btn-default"  href="${url}/admin/download/student-grades-excel-1/${semesterId}/${majorId}/${intakeId}"><spring:message code="manageGrades2.exportHighestGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                                    </div>
                                </div>

                            </div>

                            <div class="col-sm-10">
                                <br/>
                                <div class="table-responsive">
                                    <br/>
                                <table id="courseTable" class="table table-bordered table-hover">

                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code="manageCourse2.major" /></th>
                                        <th><spring:message code="manageUsers2.semester" /></th>
                                        <th><spring:message code="manageCourse.courseId" /></th>
                                        <th><spring:message code="manageCourse.courseName" /></th>
                                        <%--<th><spring:message code="manageCourse2.childId" /></th>--%>
                                        <th><spring:message code="manageCourse.teacher" /></th>
                                            <%--<th><spring:message code="manageCourse.startDate" /></th>--%>
                                            <%--<th><spring:message code="manageCourse.endDate" /></th>--%>
                                            <%--<th>Content Language</th>--%>
                                        <th><spring:message code="manageCourse.enrolledStudents" /></th>
                                        <th><spring:message code="manageCourse.status" /></th>
                                        <th ><spring:message code="reports.action" /></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var = "sysCourse" items="${childCourseTable}" varStatus="index">
                                        <tr>
                                            <td>${index.count}</td>
                                            <td><c:out value="${sysCourse.majorShortCode}"/></td>
                                            <td><c:out value="${sysCourse.semesterCode}"/></td>
                                            <td><a href="${url}/admin/parent-course/view/${sysCourse.parentCourseId}?nv=mcc"><c:out value="${sysCourse.courseName}"/></a></td>
                                            <td><a href="${url}/admin/view/course/${sysCourse.childCourseId}"><c:out value="${sysCourse.courseShortName}"/></a></td>
                                            <%--<td><c:out value="${sysCourse.childCourseId}"/></td>--%>
                                            <td><a href="#" data-toggle="tooltip" data-placement="top" title="${sysCourse.teacherId}"><c:out value="${sysCourse.teacherName}"/></a></td>
                                                <%--<td><c:out value="${sysCourse.startDate}"/></td>--%>
                                                <%--<td><c:out value="${sysCourse.endDate}"/></td>--%>
                                                <%--<td><c:out value="${sysCourse.contentLanguage}"/></td>--%>
                                            <td><a href="${url}/admin/manage-courses/enrolment-list/${sysCourse.childCourseId}/2"><c:out value="${sysCourse.totalEnrolledStudents}"/></a></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${sysCourse.active == 0}">
                                                        <spring:message code="manageCourse.opened" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <spring:message code="manageCourse.closed" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td >
                                                <div class="dropdown">
                                                    <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right actionDropdown">
                                                        <li><a href="${url}/admin/view/course/${sysCourse.childCourseId}"><spring:message code="studentPageCourseLables.goToCourseBtn" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-share-alt" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/course/grade/overview/${sysCourse.childCourseId}"><spring:message code="studentPageCourseLables.viewCourseGrades" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-education" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/course/grade/overviewFinal/${sysCourse.childCourseId}"><spring:message code="studentPageCourseLables.viewCourseFinalGrades" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-education" aria-hidden="true"></span></a></li>

                                                        <li><a href="${url}/admin/manage-courses/enroll-student/${sysCourse.childCourseId}/1"><spring:message code="manageCourse.enrolledStudents" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                        <li><a href="${url}/admin/manage-courses/enrolment-list/${sysCourse.childCourseId}/2"><spring:message code="enrollmentList.title" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-th-list" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}"> <li><a href="${url}/admin/manage-exams/add/${sysCourse.parentCourseId}"><spring:message code="manageExams.addNewExam" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li></c:if>
                                                        <li><a href="${url}/admin/manage-course/edit/${sysCourse.childCourseId}/1"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                        <li><a style="cursor: pointer" id="close" onClick="closeOneCourse(${sysCourse.childCourseId})"><spring:message code="coursePage.close" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-time" aria-hidden="true"></span></a></li>
                                                        <c:if test="${currentUserRole eq 'admin'}"><li ><a style="cursor: pointer" id="delete" onClick="deleteCourse(${sysCourse.childCourseId})"  ><spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li></c:if>

                                                    </ul>
                                                </div>

                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                                </div>
                            </div>

                        </div>
                        <%--<--END OF TABLE-->--%>
                    </c:if>


                   <c:if test="${object eq 'semesters'}">
                       <h3><spring:message code="manageSemesters.manageSemesters" /></h3>
                       <br/>
                       <%--!TABLE--%>
                       <div class="table-responsive" style="width: 80%; margin: 0 auto">
                           <br/>
                           <table id="semesterTable" class="table table-bordered table-hover">

                               <thead>
                               <tr>
                                   <th>#</th>
                                   <%--<th><spring:message code="manageSemesters.semesterId" /></th>--%>
                                   <th><spring:message code="manageSemesters.semesterCode" /></th>
                                   <th><spring:message code="manageSemesters.startDate" /></th>
                                   <th><spring:message code="manageSemesters.numberOfCourse" /></th>
                                   <th><spring:message code="reports.action" /></th>
                               </tr>
                               </thead>

                               <tbody>

                               <c:forEach var = "sysSemester" items="${semesterTableList}" varStatus="index">
                                   <tr>
                                       <td>${index.count}</td>
                                       <%--<td><c:out value="${sysSemester.semesterId}"/></td>--%>
                                       <td><c:out value="${sysSemester.semesterCode}"/></td>
                                       <td><c:out value="${sysSemester.startDate}"/></td>
                                       <td><c:out value="${sysSemester.totalNumberOfCourses}"/></td>
                                       <td>
                                           <div class="dropdown">
                                               <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="reports.action" />
                                                   <span class="caret"></span></button>
                                               <ul class="dropdown-menu dropdown-menu-right">
                                                   <li><a href="${url}/admin/semester/list/${sysSemester.semesterId}"><spring:message code="extra.courseList" />&nbsp; <span class="inIcon glyphicon glyphicon-list-alt" aria-hidden="true"></span></a></li>
                                                   <li><a href="${url}/admin/semester/update/${sysSemester.semesterId}"><spring:message code="coursePage.edit" />&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                       <%--<li><a href="#">Delete&nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>--%>
                                               </ul>
                                           </div>

                                       </td>
                                   </tr>
                               </c:forEach>

                               </tbody>

                           </table>

                       </div>

                   </c:if>

                </div>


                <%--MANAGE USER TABLE --%>
                <div id="addTab" class="tab-pane fade ">
                    <h3><spring:message code="addCourseOrSemester.addCourseOrSemester" /></h3>
                    <%--ADD BOX CONTAINER --%>
                    <div class="addBoxContainer">


                        <div class="floating-box">

                            <div class="addButtonBox">
                                <a class="courseButton" href="${url}/admin/major-parentCourse-form/1">
                                    <spring:message code="manageCourse2.addNewMajor" /><br/>
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                            </div>

                            <div class="addButtonBox">
                                <a class="courseButton" href="${url}/admin/semester/add">
                                    <spring:message code="addCourseOrSemester.addNewSemester" /><br/>
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                                <%--<a h style="visibility: hidden" ref="${url}/download/teacher-excel" class="unEnrollText"><br/><br/><spring:message code="addCourseOrSemester.downloadSemesterListExcelTemplate" /></a>--%>

                            </div>
                        </div>

                        <div class="floating-box">

                            <div class="addButtonBox">
                                <a class="courseButton" href="${url}/admin/major-parentCourse-form/2">
                                    <spring:message code="manageCourse2.addNewParentCourse" /><br/>
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                            </div>
                            <%--<div class="addButtonBox">
                                <a class="courseButton" data-toggle="modal" data-target="#parentCourseModal" href="#">
                                    <span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
                                    <br/>
                                    <spring:message code="manageCourse2.importParentExcel" /></a>
                                <a href="${url}/download/parent-course-excel" class="unEnrollText"><br/><br/><spring:message code="manageCourse2.downloadPCE" /></a>
                            </div>--%>

                       <%-- </div>

                        <div class="floating-box">--%>

                            <div class="addButtonBox">
                                <a class="courseButton" href="${url}/admin/manage-courses/add">
                                    <spring:message code="addCourseOrSemester.addNewCourse" /><br/>
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                            </div>
                            <%--<div class="addButtonBox">
                                <a class="courseButton" data-toggle="modal" data-target="#importCourseExcel" href="#">
                                    <span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
                                    <br/>
                                    <spring:message code="addCourseOrSemester.importCourseExcel" /></a>
                                <a href="#" data-toggle="modal" data-target="#downloadChildCourseExcel" class="unEnrollText"><br/><br/><spring:message code="addCourseOrSemester.downloadCourseListExcelTemplate" /></a>
                            </div>--%>

                        </div>

                    </div>
                </div>
                <%--/ADD BOX CONTAINER --%>





            </div>
            <%--MAIN TAB CONTAINER --%>
        <%--/MAIN TAB CONTAINER--%>
        </div>


    </div>
    <%--/END OF MAIN CARD CONTAINER --%>

    <%--parent course modal --%>
    <div  class="modal fade" id="parentCourseModal">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center" class="modal-title"><spring:message code="manageCourse2.importParentCourseExcelFile" /></h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form id="uploadParentCourseForm" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <select class="form-control" name="majorId" required autocomplete="off">
                                <option value=""><i><spring:message code="manageCourse2.selectStudentMajor" /></i></option>
                                <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                                    <option value="${major.majorId}">${major.majorName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input name="parentCourseExcelListFile" required type="file">

                        <div class="modal-footer">
                            <button type="submit" onclick="uploadParentCourseExcel()" class="btn btn-primary btn-block">#Import Excel File</button>
                        </div>
                    </form>
                    <%--FILE FORM --%>

                </div>
                <!--Button-->

            </div>
        </div>
    </div>

    <%--COURSE FILE IMPOORT MODEL FORM --%>
    <div class="modal fade" id="importCourseExcel">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center"  class="modal-title"><spring:message code="manageCourse2.importCourseExcelFile" /> </h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form id="uploadChildCourseForm" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <%--<label for="sel1">Select Major of Courses</label>--%>
                            <select id="semesterSelect" class="form-control" name="semesterId" required autocomplete="off">
                                <option value=""><i><spring:message code="manageCourse2.SelectCourseSemester" /></i></option>
                                <c:forEach var = "semester" items="${semesterSelectList}" varStatus="index">
                                    <option value="${semester.semesterId}">${semester.semesterCode}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input name="childCourseExcelListFile"  autocomplete="off" required type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button  type="submit" onclick="uploadChildCourseExcel()" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile" /> </button>
                        </div>
                    </form>
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
                    <h3 style="text-align: center"  class="modal-title"> <spring:message code="manageCourse2.importSemesterExcel" /></h3>
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
                            <button type="submit" value="Import Excel File" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile" /></button>
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
                <h3 style="text-align: center" class="modal-title"> <spring:message code="manageCourse2.downloadCourseExcelTemp" /></h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <%--FILE FORM --%>
                <form:form  id="classFile" action="${url}/download/child-course-excel" method="POST"
                            enctype="multipart/form-data">
                    <div class="form-group">
                            <label for="majorSelect"><spring:message code="manageCourse2.selectMajorOfCourse" /></label>

                        <select class="form-control" name="majorId" required autocomplete="off">
                            <option value=""><i><spring:message code="manageCourse2.selectStudentMajor" /></i></option>
                            <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                                <option value="${major.majorId}">${major.majorName}</option>
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

<%--CONFIRMATION FORM --%>
<div class="modal fade" id="confirmationTableModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div id="modelHeader" class="modal-header">
                <h4 class="alert alert-danger modal-title"><spring:message code="manageCourse2.error1" /></h4>
            </div>
            <!--Body (form)-->
            <div class="modal-body">
                <div class="holdBox">
                <table id="confTable" class="table  table-striped table-fixed table-condensed">
                    <thead id="tableHead">

                    </thead>
                        <tbody id="userConfirmTable">

                        </tbody>
                </table>
                </div>
                <button type="button" class="btn btn-primary btn-block" data-dismiss="modal"><spring:message code="manageUsers2.ok" /></button>
            </div>


        </div>
    </div>
</div>

<script>

    $(document).ready(function() {
//        $('#confirmationTableModel').modal('toggle');

        //major



        $("#majorTab").click(function(){
            <c:if test="${object ne 'majors'}">

                <c:if test="${!empty currentMajor}">
                    window.location.href = '${url}/admin/manage-courses/majors?major=${majorId}';
                </c:if>
                <c:if test="${empty currentMajor}">
                     window.location.href = '${url}/admin/manage-courses/majors';
                </c:if>

            </c:if>
        });
        $("#courseTab").click(function(){
            <c:if test="${object ne 'courses'}">

                <c:if test="${!empty currentMajor}">
                    window.location.href = '${url}/admin/manage-courses/courses?major=${majorId}';
                </c:if>
                <c:if test="${empty currentMajor}">
                     window.location.href = '${url}/admin/manage-courses/courses';
                </c:if>

            </c:if>


        });
        $("#semesterTab").click(function(){
            <c:if test="${object ne 'semesters'}">

                <c:if test="${!empty currentMajor}">
                    window.location.href = '${url}/admin/manage-courses/semesters?major=${majorId}';
                </c:if>
                <c:if test="${empty currentMajor}">
                     window.location.href = '${url}/admin/manage-courses/semesters';
                </c:if>
            </c:if>
        });




        $('#parentCourseTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
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
        $('#confTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });

    } );

    function getParentCourses(majorId) {
        // alert(majorId+'just got it ');

    }

    function closeOneCourse(courseId) {
        var r = confirm('<spring:message code="manageCourse2.msg7" />');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-courses/closeCourse/'+courseId,
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

    function deleteCourse(courseId) {
        var r = confirm('<spring:message code="manageCourse2.msg1" />');
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
                    alert('<spring:message code="main.msgError" />');
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


    //////-----------------------------------------------//////////////////////
    function filterSemester(semesterId) {
        // $('#courseTable tbody').remove();

        <c:if test="${empty currentMajor}">
            window.location.href = '${url}/admin/manage-courses/courses?filterId='+semesterId;
        </c:if>
        <c:if test="${!empty currentMajor}">
             window.location.href = '${url}/admin/manage-courses/courses?filterId='+semesterId+'&major=${currentMajor.majorId}';
        </c:if>

    }

    function filterMajor(majorId){
        window.location.href = '${url}/admin/manage-courses/majors?major='+majorId;
    }


    function uploadChildCourseExcel() {
        var data = new FormData($("#uploadChildCourseForm")[0]);

        $('#uploadChildCourseForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/manage-courses/add-child-course-excel',
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                    $('#importCourseExcel').modal('toggle');
                    $("#uploadChildCourseForm").get(0).reset();
                    $("#semesterSelect").val('');
                    console.log(data.list);
                    if(data.status_code === 406){
                        alert('<spring:message code="manageCourse2.msg2" />');

                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
                        $('#modelHeader').append('<h4 class="alert alert-danger modal-title"><spring:message code="manageCourse2.msg3" /></h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageCourse2.parentCourseId" /></th>'+
                            '<th><spring:message code="manageCourse2.courseShortName" /></th>'+
                            '<th><spring:message code="manageCourse2.teacherName" /></th>'+
                            '<th><spring:message code="manageCourse2.enrolmentDeadline" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].parentCourseId+'</td>'+
                                '<td>'+data.list[i].childCourseName+'</td>'+
                                '<td>'+data.list[i].teacherName+'</td>'+
                                '<td>'+data.list[i].enrolmentDeadline+'</td>'+
                                '</tr>'
                            );

                        }

                        $('#confirmationTableModel').modal('toggle');
                    }
                    if(data.status_code === 200){
                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
//                        $('#addNewUsers').empty()
                        $('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') Child Courses Added Successfully </h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageCourse2.parentCourseId" /></th>'+
                            '<th><spring:message code="manageCourse2.courseShortName" /></th>'+
                            '<th><spring:message code="manageCourse2.teacherName" /></th>'+
                            '<th><spring:message code="manageCourse2.enrolmentDeadline" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].parentCourseId+'</td>'+
                                '<td>'+data.list[i].childCourseName+'</td>'+
                                '<td>'+data.list[i].teacherName+'</td>'+
                                '<td>'+data.list[i].enrolmentDeadline+'</td>'+
                                '</tr>'
                            );

                            <%--$('#addNewUsers').prepend(--%>
                            <%--'<tr class="success">'+--%>
                            <%--'<td>#</td>'+--%>
                            <%--'<td><a  href="${url}/admin/profile/view/'+data.list[i].userId+'">'+data.list[i].firstName+'</a> </td>'+--%>
                            <%--'<td>'+data.list[i].userId+'</td>'+--%>
                            <%--'<td>'+data.list[i].role+'</td>'+--%>
                            <%--'<td>'+data.list[i].gender+'</td>'+--%>
                            <%--'<td></td>'+--%>
                            <%--'<td>'+data.list[i].country+'</td>'+--%>
                            <%--'<td>Never</td>'+--%>
                            <%--'<td>'+--%>
                            <%--'<div class="dropdown">'+--%>
                            <%--'<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />'+--%>
                            <%--'<span class="caret"></span></button>'+--%>
                            <%--'<ul class="dropdown-menu dropdown-menu-right">'+--%>
                            <%--'<li><a href="${url}/admin/profile/view/'+data.list[i].userId+'">#View    &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>'+--%>
                            <%--'<li><a href="${url}/admin/profile/edit/'+data.list[i].userId+'/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>'+--%>
                            <%--'<li><a style="cursor: pointer" onclick="deleteStudent('+data.list[i].userId+')"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>'+--%>
                            <%--'</ul>'+--%>
                            <%--'</div>'+--%>
                            <%--'</td>'+--%>
                            <%--'</tr>'--%>
                            <%--);--%>

                        }
                        $('#confirmationTableModel').modal('toggle');
                    }
                },
                error : function(data){
                    $('#importCourseExcel').modal('toggle');
                    alert('<spring:message code="main.msgError" />');
                }
            });
            return false;
        });
    }

    ////-------------------------------------------------------------------////
    function uploadParentCourseExcel() {
        var data = new FormData($("#uploadParentCourseForm")[0]);

        $('#uploadParentCourseForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/manage-courses/add-parent-course-excel',
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                    $('#parentCourseModal').modal('toggle');
                    $("#uploadParentCourseForm").get(0).reset();
                    console.log(data.list);
                    if(data.status_code === 406){
                        alert('Error Uploading Data !!');

                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
                        $('#modelHeader').append('<h4 class="alert alert-danger modal-title">Error Parent Course IDs Already In System !!</h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageCourse2.parentCourseId" /></th>'+
                            '<th><spring:message code="manageCourse2.courseName" /></th>'+
                            '<th><spring:message code="manageCourse2.courseType" /></th>'+
                            '<th><spring:message code="manageCourse2.credits" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].parentCourseId+'</td>'+
                                '<td>'+data.list[i].courseName+'</td>'+
                                '<td>'+data.list[i].courseType+'</td>'+
                                '<td>'+data.list[i].credits+'</td>'+
                                '</tr>'
                            );

                        }

                        $('#confirmationTableModel').modal('toggle');
                    }
                    if(data.status_code === 200){
                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
//                        $('#addNewUsers').empty()
                        $('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') <spring:message code="manageCourse2.msg4" /> </h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageCourse2.parentCourseId" /></th>'+
                            '<th><spring:message code="manageCourse2.courseName" /></th>'+
                            '<th><spring:message code="manageCourse2.courseType" /></th>'+
                            '<th><spring:message code="manageCourse2.credits" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].parentCourseId+'</td>'+
                                '<td>'+data.list[i].courseName+'</td>'+
                                '<td>'+data.list[i].courseType+'</td>'+
                                '<td>'+data.list[i].credits+'</td>'+
                                '</tr>'
                            );

                            <%--$('#addNewUsers').prepend(--%>
                                <%--'<tr class="success">'+--%>
                                <%--'<td>#</td>'+--%>
                                <%--'<td><a  href="${url}/admin/profile/view/'+data.list[i].userId+'">'+data.list[i].firstName+'</a> </td>'+--%>
                                <%--'<td>'+data.list[i].userId+'</td>'+--%>
                                <%--'<td>'+data.list[i].role+'</td>'+--%>
                                <%--'<td>'+data.list[i].gender+'</td>'+--%>
                                <%--'<td></td>'+--%>
                                <%--'<td>'+data.list[i].country+'</td>'+--%>
                                <%--'<td>Never</td>'+--%>
                                <%--'<td>'+--%>
                                <%--'<div class="dropdown">'+--%>
                                <%--'<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />'+--%>
                                <%--'<span class="caret"></span></button>'+--%>
                                <%--'<ul class="dropdown-menu dropdown-menu-right">'+--%>
                                <%--'<li><a href="${url}/admin/profile/view/'+data.list[i].userId+'">#View    &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>'+--%>
                                <%--'<li><a href="${url}/admin/profile/edit/'+data.list[i].userId+'/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>'+--%>
                                <%--'<li><a style="cursor: pointer" onclick="deleteStudent('+data.list[i].userId+')"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>'+--%>
                                <%--'</ul>'+--%>
                                <%--'</div>'+--%>
                                <%--'</td>'+--%>
                                <%--'</tr>'--%>
                            <%--);--%>

                        }
                        $('#confirmationTableModel').modal('toggle');
                    }
                },
                error : function(data){
                    $('#parentCourseModal').modal('toggle');
                    alert('<spring:message code="main.msgError" />');
                }
            });
            return false;
        });
    }

//    major manipulations


    $("#majorSelect").change(function(){

        var thisvalue = $(this).find("option:selected").val();

        if(thisvalue === ""){
            window.location.href = '${url}/admin/manage-courses/${object}';
        }else {
            window.location.href = '${url}/admin/manage-courses/${object}?major='+thisvalue;
        }

    });

    function closeCourses() {
        var r = confirm('<spring:message code="manageCourse2.msg5" />');
       <%--alert("hello"+ '${param.filterId}');--%>
        <%--if ( '${param.filterId}' == "" ){--%>
            <%--alert('<spring:message code="manageCourse2.msg6" />');--%>
        <%--}--%>

        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-courses/closeCoursesBySemester/${param.filterId}',
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
            alert("nono");
            //do nothing
        }

    }

    function includeClosedCourses() {
        window.location.href='${url}/admin/manage-courses/courses';
    }

</script>

</body>

</html>
