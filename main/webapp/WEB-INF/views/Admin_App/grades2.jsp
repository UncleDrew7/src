    <%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 03:27
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<%--&lt;%&ndash;template database &ndash;%&gt;--%>
<%--<%@ page import = "java.io.*,java.util.*,java.sql.*"%>--%>
<%--<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>--%>
<%--<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>--%>


<html lang="en">

<head>
    <title><spring:message code="manageGrades2.title" /></title>
    <link href="${url}/resources/student_app_static/css/grades.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/grades.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>


<body>


<div>

    <!-- Tab panes -->
    <div class="content">
        <br/>
        <h3 class="pageTitle"><spring:message code="manageGrades2.title" /></h3>
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="row mainContainer">

                <label><input type="checkbox" id="retake" name="retakeCheck" onclick="getRetakeList()" value="retakeCheck" checked  style="height: 18px;width: 18px;"> <spring:message code="manageGrades2.retakeGrade" /></label>

                <div class="form-group pull-right">
                    <div class="btn-group" role="group" aria-label="...">
                        <a type="button" class="btn btn-default"  onclick="exportRetakeGradeRecord()"><spring:message code="manageGrades2.exportRetakeGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                        <%--<a type="button" class="btn btn-default"  href="${url}/admin/download/student-grades-excel-1/${semesterId}/${majorId}/${intakeId}"><spring:message code="manageGrades2.exportHighestGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                    </div>
                </div>

                <%--<div class="form-group pull-right">--%>
                    <%--<div class="btn-group" role="group" aria-label="...">--%>
                        <%--<a type="button" class="btn btn-default"  onclick="exportGradeRecord()"><spring:message code="manageGrades2.exportGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>--%>
                        <%--&lt;%&ndash;<a type="button" class="btn btn-default"  href="${url}/admin/download/student-grades-excel/${semesterId}/${majorId}/${intakeId}"><spring:message code="manageGrades2.exportGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>&ndash;%&gt;--%>
                    <%--</div>--%>
                <%--</div>--%>



                <!--Small cards-->
                <div class="col-sm-2">


                        <%--<div class="panel-group">
                            <div class="panel panel-default">
                                <div style="background: #337ab7" class="panel-heading">
                                    <h4 class="semesterHeader panel-title">
                                        <a data-toggle="collapse" href="#collapse1"><spring:message code="manageGrades2.exportGradeRecord" /></a>
                                    </h4>
                                </div>
                                <div id="collapse1" class="recordeform panel-collapse collapse">
                            &lt;%&ndash;<div class="recordeform">&ndash;%&gt;
                                    <form id="gradeform">

                                        <div class="form-group">
                                            <div class="divide"><spring:message code="manageGrades2.selectMajor" /></div>
                                            <div>
                                                <select class="form-control" id="degreeType" name="degreeType">
                                                    <option value="${currentCourseDetails.courseType}"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="divide"><spring:message code="manageGrades2.selectSemester" /></div>

                                        <c:forEach var = "sslist" items="${semesterList}" varStatus="index">
                                            <div class="">
                                                <label><input type="checkbox" value="${sslist.semesterId}"> ${sslist.semesterCode}</label>
                                            </div>
                                        </c:forEach>



                                        <div class="subBtn">
                                            <a  class="btn btn-default"><spring:message code="manageGrades2.downloadReport" /></a>
                                        </div>
                                        &lt;%&ndash;<button <c:if test="${!hasPermission and currentUserRole ne 'admin'}">disabled</c:if>  style="color: inherit" type="submit" class="btn btn-default"><spring:message code="studentForm.saveSettings" /></button>&ndash;%&gt;



                                    </form>


                                </div>
                            </div>
                        </div>--%>



                        <%--<div class="card  filterBox ">--%>
                            <%--<div>--%>
                                <%--<h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.filterCoures" /></h4>--%>
                            <%--</div>--%>
                            <%--<div>--%>
                                <%--<div style="margin-top: -11px" class="list-group">--%>
                                    <%--<a href="${url}/admin/grades/101/1" class="list-group-item <c:if test='${filter eq 1}'>active</c:if>">Active Courses<span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>--%>
                                    <%--<a href="${url}/admin/grades/102/2" class="list-group-item <c:if test='${filter eq 2}'>active</c:if>">In-Active Courses</a>--%>
                                    <%--<a href="${url}/admin/grades/103/3" class="list-group-item <c:if test='${filter eq 3}'>active</c:if>"><spring:message code="studentPageCourseLables.allCourses" /></a>--%>
                                <%--</div>--%>
                                <%--&lt;%&ndash;<ul class="courseFilterList">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<li></li>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<li></li>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<li><spring:message code="studentPageCourseLables.unEnrolledCourses" /></li>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<li></li>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
                            <%--</div>--%>
                        <%--</div>--%>



                        <div>
                            <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageUsers2.semester" /></h4>
                            <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder='<spring:message code="manageUsers.search" />'></div>
                        </div>
                        <div class="card  filterBoxCourse ">
                            <div>
                                <div style="margin-top: -11px" class="list-group">
                                    <a href="#" style="margin-top: 10px" onclick="filterSemester('')" class="list-group-item <c:if test='${param.semester eq null}'>activeFilter</c:if>">${"All"}</a>
                                    <c:forEach var = "sslist" items="${semesterList}" varStatus="index">
                                        <a href="#" onclick="filterSemester(${sslist.semesterId})" class="list-group-item <c:if test='${param.semester eq sslist.semesterId}'>activeFilter</c:if>">${sslist.semesterCode}</a>
                                    </c:forEach>
                                </div>

                            </div>
                        </div>


                    </div>
                    <!--Small cards-->

                <div class="col-sm-10 ">


                    <div class="row firstContainer">

                        <div class="col-sm-8 ">
                            <div class="majorFilter">
                                <%--<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;--%>
                                <a class="majorLable">Major:</a>&nbsp;
                                <select  autocomplete="off" id="majorSelect" class="selectMajor" style="width:240px;">
                                    <c:if test="${currentMajor ne null}"><option value="">${currentMajor.majorName}</option></c:if>
                                    <option value=""><spring:message code="manageUsers2.default" /></option>
                                    <c:forEach var = "majors" items="${majorSelectList}" varStatus="index">
                                        <c:if test="${majors.majorId ne currentMajor.majorId}">
                                            <option value="${majors.majorId}"><c:out value="${majors.majorName}"/></option>
                                        </c:if>
                                    </c:forEach>
                                </select>

                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                    <b>Intake:</b>&nbsp;
                                    <select  autocomplete="off" id="intakeSelect" class="intakeClass" style="width:120px;">
                                        <c:if test="${intakeId ne null}"><option value="">${intakeId}</option></c:if>
                                        <option value=""><spring:message code="manageUsers2.default" /></option>
                                        <c:forEach var = "intakes" items="${intakeSelectList}" varStatus="index">
                                            <c:if test="${intakes.intakePeriod ne intakeId}">
                                                <option value="${intakes.intakePeriod}"><c:out value="${intakes.intakePeriod}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                    <%--<b>Class:</b>&nbsp;--%>
                                    <%--<select  autocomplete="off" id="classSelect" class="selectClass" >--%>
                                        <%--<c:if test="${currentClassID ne null}"><option value="">${currentClassID.className}</option></c:if>--%>
                                        <%--<option value=""><spring:message code="manageUsers2.default" /></option>--%>
                                        <%--<c:forEach var = "class" items="${classSelectList}" varStatus="index">--%>
                                            <%--<c:if test="${class.id ne currentClassID.id}">--%>
                                                <%--<option value="${class.id}"><c:out value="${class.className}"/></option>--%>
                                            <%--</c:if>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>

                            </div>
                        </div>

                    </div>

                    <div style="min-height: 400px" class=" bigCardContainer">



                        <br/>
                        <!--Grade Table -->

                        <%--<div class="table-responsive csgoContaier">--%>
                            <%--<table  cellspacing="0" width="100%" id="gradeTable" class="table table-bordered table-hover">--%>

                                <%--&lt;%&ndash;<caption class="">--%--%>
                                <%--&lt;%&ndash;</caption>&ndash;%&gt;--%>
                                <%--<thead >--%>
                                <%--<tr>--%>
                                    <%--<th>#</th>--%>
                                    <%--<th ><spring:message code="enrollmentList.studentId"/></th>--%>
                                    <%--<th ><spring:message code="enrollmentList.studentName"/></th>--%>
                                    <%--<c:forEach var="courses" items="${courseList}">--%>
                                        <%--<th ><c:out value="${courses.gradeItemName}"/>${semester.semesterCode}</th>--%>
                                    <%--</c:forEach>--%>
                                    <%--<th ><spring:message code="manageGrades2.gradeAverage"/></th>--%>

                                <%--</tr>--%>
                                <%--</thead>--%>

                                <%--<tbody>--%>
                                <%--<c:forEach var="studentList" items="${studentEnrolledList}">--%>
                                    <%--<tr>--%>
                                        <%--<td><c:out value="${studentList.userId}"/></td>--%>
                                        <%--<td><a onclick="userReport(${studentList.userId})"class="usrText"><c:out value="${studentList.userName}"/></a></td>--%>

                                        <%--<c:forEach var="gradeitems" items="${gradeItems}">--%>
                                            <%--<td>--%>
                                                <%--<c:forEach var="grades" items="${courseGrades}">--%>
                                                    <%--<c:if test="${gradeitems.gradeItemId eq grades.gradeItemId and studentList.userId eq grades.studentId }">--%>
                                                        <%--<c:out value="${grades.grade}"/>--%>
                                                    <%--</c:if>--%>
                                                <%--</c:forEach>--%>
                                            <%--</td>--%>
                                        <%--</c:forEach>--%>
                                        <%--<td><c:out value="${studentList.courseGradeAverage}"/></td>--%>
                                    <%--</tr>--%>
                                <%--</c:forEach>--%>
                                <%--</tbody>--%>

                                <%--<tfoot>--%>
                                <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<td>Course Average : </td>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
                                <%--</tfoot>--%>
                            <%--</table>--%>
                        <%--</div>--%>
                        <table  id="customers">

                            <thead>
                            <tr>
                                <th>#</th>
                                <th ><spring:message code="enrollmentList.studentId"/></th>
                                <th ><spring:message code="enrollmentList.studentName"/></th>
                                <th ><spring:message code="studentPageCourseLables.semester"/></th>
                                <th ><spring:message code="manageCourse2.courseId"/></th>
                                <th ><spring:message code="manageCourse2.courseName"/></th>
                                <th ><spring:message code="manageCourse2.courseNameCN"/></th>
                                <th ><spring:message code="courseAllGrades.finalAll"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="reList" items="${retakeCourseList}" varStatus="index">
                            <tr>
                                <td>${index.count}</td>
                                <td><c:out value="${reList.studentId}"/></td>
                                <td><c:out value="${reList.studentName}"/></td>
                                <td><c:out value="${reList.semester}"/></td>
                                <td><c:out value="${reList.courseCode}"/></td>
                                <td><c:out value="${reList.courseName}"/></td>
                                <td><c:out value="${reList.courseNameCN}"/></td>
                                <td><c:out value="${reList.finalAll}"/></td>
                            </tr>
                            </c:forEach>
                            </tbody>

                        <%--<c:forEach varStatus="index" var = "course" items="${gradeTableList}">--%>
                            <%--<tr>--%>
                                <%--<td>${index.count}</td>--%>
                                <%--<td><a href="${url}/admin/grades/grade-items/${course.childCourseId}" class="gradeText" ><c:out value="${course.courseName}"/></a></td>--%>
                                <%--<td><a href="${url}/admin/grades/grade-items/${course.childCourseId}" class="gradeText" ><c:out value="${course.courseShortName}"/></a></td>--%>
                                <%--<td><c:out value="${course.semesterCode}"/></td>--%>
                                <%--<td><c:out value="${course.credits}"/></td>--%>
                                <%--<td><c:out value="${course.totalEnrollments}"/></td>--%>
                                <%--<td><c:out value="${course.gradeAverage}"/></td>--%>

                            <%--</tr>--%>
                        <%--</c:forEach>--%>
                        </table>

                        <!--End Grade Table-->

                    </div>
                </div>

                <%-----------------%>



            </div>

        </div>

    </div>

</div>



<%--<!--Small cards-->--%>
<%--<div class="col-sm-3">--%>
<%--<div class=" sortGradesBox">--%>
<%--<div>--%>
<%--<h4 class="titleTextBoxHeaders">Grade Settings </h4>--%>
<%--</div>--%>
<%--<div>--%>
<%--<ul class="courseCategoryList">--%>
<%--<li>--%>
<%--<div class="courseCategoryItem">--%>
<%--<a href="#courseCategory" data-toggle="modal" data-target="#myModal" class="courseOthersText">--%>
<%--Letters--%>
<%--</a>--%>
<%--</div>--%>
<%--&lt;%&ndash;---model---&ndash;%&gt;--%>
<%--<!-- Modal -->--%>
<%--<div class="modal fade" id="myModal" role="dialog">--%>
<%--<div class="modal-dialog modal-lg">--%>
<%--<div class="modal-content">--%>
<%--<div class="modal-header">--%>
<%--<button type="button" class="close" data-dismiss="modal">&times;</button>--%>
<%--<h4 class="modal-title">Manage Grade Letters</h4>--%>
<%--<a class="editText" data-toggle="pill" href="#lettersUpdate">Edit Grade Letters </a>--%>
<%--<span> | <a class="editText" data-toggle="pill" href="#letters">View Grade Letters </a></span>--%>
<%--</div>--%>
<%--<div class="modal-body">--%>

<%--<div class="tab-content">--%>
<%--<div id="letters" class="tab-pane fade in active">--%>
<%--&lt;%&ndash;-&ndash;%&gt;--%>
<%--<!--Grade Table -->--%>

<%--<table id="customers">--%>
<%--<tr>--%>
<%--<th>Highest</th>--%>
<%--<th>Lowest</th>--%>
<%--<th>Letter</th>--%>
<%--</tr>--%>
<%--<tr>--%>

<%--<td>100</td>--%>
<%--<td>100</td>--%>
<%--<td>100</td>--%>

<%--</tr>--%>

<%--</table>--%>

<%--<!--End Grade Table-->--%>
<%--&lt;%&ndash;-&ndash;%&gt;--%>
<%--</div>--%>
<%--&lt;%&ndash;----&ndash;%&gt;--%>
<%--<div id="lettersUpdate" class="tab-pane fade">--%>
<%--&lt;%&ndash;FORM&ndash;%&gt;--%>
<%--&lt;%&ndash;<h3>Basic</h3>&ndash;%&gt;--%>
<%--<form class="form-horizontal" action="/action_page.php">--%>

<%--&lt;%&ndash;FORM HEADER&ndash;%&gt;--%>
<%--&lt;%&ndash;FORM BOX 1/&ndash;%&gt;--%>
<%--<div class="formBox">--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl1Letter">Grade Letter 1</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl1Letter" placeholder="Enter Letter" name="gl1Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl1Boundary">Letter grade boundary 1</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control"  id="gl1Boundary" name="gl1Boundary" onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl2Letter">Grade letter 2</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl2Letter" placeholder="Enter Exam Code" name="gl2Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl2Boundary">Letter grade boundary 2</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control" id="gl2Boundary" name="gl2Boundary"onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl3Letter">Grade letter 3</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl3Letter" placeholder="Enter Grade Letter" name="gl3Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl3Boundary">Letter grade boundary 3</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control" id="gl3Boundary" name="gl3Boundary"onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl4Letter">Grade Letter 4</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl4Letter" placeholder="Enter Grade Letter" name="gl4Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl4Boundary">Letter grade boundary 4</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control" id="gl4Boundary" name="gl4Boundary"onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl5Letter">Grade letter 5</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl5Letter" placeholder="Enter Grade letter" name="gl5Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl5Boundary">Letter grade boundary 5</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control" id="gl5Boundary" name="gl5Boundary"onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl6Letter">Grade letter 6</label>--%>
<%--<div class="col-sm-9">--%>
<%--<input type="text" class="form-control" id="gl6Letter" placeholder="Enter Grade Letter" name="gl6Letter">--%>
<%--</div>--%>
<%--</div>--%>

<%--<div class="form-group">--%>
<%--<label class="control-label col-sm-3" for="gl6Boundary">Letter grade boundary 6</label>--%>
<%--<div class="col-sm-9">--%>
<%--<select class="form-control" id="gl6Boundary" name="gl6Boundary"onfocus='this.size=10;' onblur='this.size=1;'--%>
<%--onchange='this.size=1; this.blur();'>--%>
<%--<c:forEach begin="0" end="99" varStatus="count" >--%>
<%--<option>${count.count}%</option>--%>
<%--</c:forEach>--%>
<%--</select>--%>
<%--</div>--%>
<%--</div>--%>


<%--<div class="divider"></div>--%>


<%--</div>--%>
<%--&lt;%&ndash;FORM BOX1&ndash;%&gt;--%>
<%--<div class="form-group">--%>
<%--<div class=" submitBtnBox">--%>
<%--<button type="submit" class="btn btn-default">Save Changes</button>--%>
<%--</div>--%>
<%--</div>--%>

<%--</form>--%>
<%--&lt;%&ndash;/FORM&ndash;%&gt;--%>
<%--</div>--%>

<%--</div>--%>




<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<a>Edit Grade Letters </a>--%>
<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--&lt;%&ndash;---model---&ndash;%&gt;--%>
<%--</li>--%>

<%--</ul>--%>
<%--</div>--%>

<%--</div>--%>

<%--</div>--%>
<%--<!--Small cards-->--%>
<%--&lt;%&ndash;--------&ndash;%&gt;--%>
<script>


    function filterSemester(semesterId) {
        // $('#courseTable tbody').remove();

        window.location.href = '${url}/admin/grades2?major=${majorId}&intake=${intakeId}&semester=' + semesterId;

       <%--if(semesterId === '' ){--%>

            <%--&lt;%&ndash;<c:if test="${empty currentMajor}">&ndash;%&gt;--%>
                 <%--&lt;%&ndash;window.location.href = '${url}/admin/grades?major=${majorId}';&ndash;%&gt;--%>
            <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<c:if test="${empty currentMajor}">&ndash;%&gt;--%>
               <%--&lt;%&ndash;&ndash;%&gt;--%>
            <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
            <%--window.location.href = '${url}/admin/grades';--%>
        <%--}--%>
       <%--else {--%>
           <%--<c:if test="${!empty currentMajor}">--%>
           <%--window.location.href = '${url}/admin/grades?semester='+semesterId+'&major=${majorId}';--%>
           <%--</c:if>--%>

           <%--<c:if test="${empty currentMajor}">--%>
           <%--window.location.href = '${url}/admin/grades?semester='+semesterId;--%>
           <%--</c:if>--%>
       <%--}--%>


    }

    $(document).ready(function() {

        $('#customers').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
    } );

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

//        if($("input[type='checkbox']").is(":checked")) {
//            alert("checkcheck");
//        }
//        if( $("#retake").is(":checked")) {
//            alert("checkcheck");
//        }
//        else {

            var thisvalue = $(this).find("option:selected").val();

            <%--if ( ${semesterId} == null )--%>
                <%--alert("Please select semester to continue...");--%>

            window.location.href = '${url}/admin/grades2?major='+thisvalue + '&intake=${intakeId}&semester=${semesterId}';
//        }


        <%--if(thisvalue == ""){--%>
            <%--window.location.href = '${url}/admin/grades';--%>
        <%--}else{--%>

           <%--<c:if test="${!empty intakeId}">--%>
                <%--window.location.href = '${url}/admin/grades?major='+thisvalue + '&intake=${intakeId}';--%>
            <%--</c:if>--%>

            <%--<c:if test="${empty intakeId}">--%>
                 <%--window.location.href = '${url}/admin/grades?major='+thisvalue;--%>
            <%--</c:if>--%>

            <%--window.location.href = '${url}/admin/grades?major='+thisvalue;--%>
//        }

    });


    $("#intakeSelect").change(function(){

        var thisvalue = $(this).find("option:selected").val();

        window.location.href = '${url}/admin/grades2?major=${majorId}&intake=' + thisvalue + '&semester=${semesterId}';

        <%--if(thisvalue == ""){--%>
            <%--window.location.href = '${url}/admin/grades?major=${majorId}';--%>
        <%--}else {--%>
            <%--window.location.href = '${url}/admin/grades?major=${majorId}&intake='+thisvalue;--%>
        <%--}--%>

    });

    <%--function userReport(userId) {--%>
        <%--window.location.href='${url}/admin/grades/student-grade-overview/'+userId;--%>
    <%--}--%>

    <%--function exportHighestGradeRecord() {--%>

        <%--if ( ${!empty majorId} && ${!empty intakeId} && ${!empty semesterId}){--%>
            <%--window.location.href = '${url}/admin/download/student-grades-excel-1/${semesterId}/${majorId}/${intakeId}';--%>
        <%--}--%>
        <%--else{--%>
           <%--alert("major or intake or semester is null!");--%>
            <%--&lt;%&ndash;alert(<spring:message code="manageGrades2.errormsg" />);&ndash;%&gt;--%>
        <%--}--%>

    <%--}--%>

    function exportRetakeGradeRecord() {

        // alert("to be continued...");
        if (${!empty semesterId}){
            window.location.href='${url}/admin/download/student-retake-grades-excel/?semester=${semesterId}';
        }
        else{
            alert("semester is null!");
            window.location.href='${url}/admin/download/student-retake-grades-excel';
        }




        <%--if ( ${!empty majorId} && ${!empty intakeId} && ${!empty semesterId}){--%>
            <%--window.location.href='${url}/admin/download/student-grades-excel/${semesterId}/${majorId}/${intakeId}';--%>
        <%--}--%>
    <%--else{--%>
            <%--alert("major or intake or semester is null!");--%>
        <%--}--%>

    }

    function getRetakeList() {
//        alert("get retake");
        window.location.href='${url}/admin/grades';
    }

</script>
</body>

</html>
