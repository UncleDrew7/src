<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 02:44
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
    <title><spring:message code="manageUsers.title1" /></title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
    <script type="text/javascript" src="${url}/resources/app_admin_static/js/zscriptUserManagement.js"></script>
</head>

<body>
<%--TEST DATASOURCE --%>
<c:set var="page" value="10"/>
<

<!--Content Begins  -->
<div class="content">
    <%--NOTIFICATION--%>
    <%--<c:if test="${empty rdMessage}">--%>
        <%--<div class="alert alert-danger alert-dismissable fade in">--%>
            <%--<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>--%>
            <%--<strong>Error!</strong> Failed To add new user--%>
        <%--</div>--%>
    <%--</c:if>--%>
        <c:if test="${not empty rdErrorMessage}">
            <div class="alert alert-success alert-dismissable fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>${rdErrorMessage}</strong>
            </div>
        </c:if>
    <c:if test="${not empty rdMessage}">
        <div class="alert alert-success alert-dismissable fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>${rdMessage}</strong>
        </div>
    </c:if>
    <%--NOTIFICATION --%>
        <h3 class="pageTitle"><spring:message code="manageUsers.title1" /></h3>
    <!--USER STATISTICS BOX -->
    <div class="userStatsBoxContainer">

        <div class="dash-item__content">

            <ul class="quiz-results">
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${totalUsers}</span>
                    <div class="quiz-results__label"><spring:message code="manageUsers.totalUsers" /></div>
                </li>
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${toatlStudents}</span>
                    <div class="quiz-results__label"><spring:message code="manageUsers.students" /></div>
                </li>
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${totalTeachers}</span>
                    <div class="quiz-results__label"><spring:message code="manageUsers.teachers" /></div>
                </li>
                <li class="quiz-results__item quiz-results__item--average-score">
                    <span class="quiz-results__number quiz-results__number--average-score">${totalClasses}</span>
                    <div class="quiz-results__label"><spring:message code="manageUsers.classes" /></div>
                </li>
            </ul>
        </div>

    </div>
    <%--/USER STATISTICS BOX--%>

    <!--MAIN CARD CONTAINER -->
    <div style="min-width: 500px"  class="card mainCardContainer">

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

            <!--MAIN TAB CONTAINER -->
            <div class="mainTabContainer">

                <!--NAV TABS -->
                <ul class="nav nav-tabs">
                    <li class="<c:if test="${object eq 'users'}">active</c:if>"><a id="userTab" data-toggle="tab" href="#home"><spring:message code="manageUsers.title1" /></a></li>
                    <li class="<c:if test="${object eq 'classes'}">active</c:if>"><a id="classesTab" data-toggle="tab" href="#home"><spring:message code="manageUsers.manageClasses" /></a></li>
                    <c:if test="${currentUserRoleP eq 'admin'}">
                        <li><a data-toggle="tab" href="#menu2"><spring:message code="manageUsers.addUserOrClass" /></a></li>
                    </c:if>
                </ul>
                <%--/NAV TABS--%>

                <!--TAB CONTENT-->
                <div class="tab-content">



                    <%--MANAGE USER TABLE --%>
                    <div id="home" class="tab-pane fade in active">
                        <c:if test="${object eq 'users'}">
                            <h3><spring:message code="manageUsers.students" /></h3>
                            <!--Big cards-->
                            <div class="col-sm-2">
                                <br/>
                                <div style="margin-top: 20px" class="card  filterBox ">
                                    <div>
                                        <h4  class="sideBoxHeader titleTextBoxHeaders"><spring:message code="manageUsers2.filterUsers" /></h4>
                                    </div>
                                    <div>
                                        <div style="margin-top: -11px" class="list-group">
                                            <a href="${url}/admin/manage-users/users?filter=1" class="list-group-item <c:if test='${param.filter eq 1}'>activeFilter</c:if>"><spring:message code="manageUsers2.all" /><span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>
                                            <a href="${url}/admin/manage-users/users?filter=3" class="list-group-item <c:if test='${param.filter eq 3}'>activeFilter</c:if>"><spring:message code="manageUsers2.students" /></a>
                                            <a href="${url}/admin/manage-users/users?filter=2" class="list-group-item <c:if test='${param.filter eq 2}'>activeFilter</c:if>"><spring:message code="manageUsers2.teacherandAndAdmin" /></a>
                                        </div>
                                    </div>

                                </div>
                                <c:if test="${param.filter eq 3}">
                                    <div>
                                        <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageUsers2.intake" /></h4>
                                        <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder="Search"></div>
                                    </div>
                                    <div class=" filterBoxCourse filterBoxIntake">

                                        <div  class="list-group">
                                            <c:forEach var = "syIntake" items="${studentIntakeList}" varStatus="index">
                                                <c:if test="${empty currentMajor}">
                                                <a href="${url}/admin/manage-users/users?filter=3&intake=${syIntake}" class="list-group-item <c:if test='${param.intake eq syIntake}'>activeFilter</c:if>">${syIntake}</a>
                                                </c:if>
                                                <c:if test="${!empty currentMajor}">
                                                    <a href="${url}/admin/manage-users/users?filter=3&intake=${syIntake}&major=${majorId}" class="list-group-item <c:if test='${param.intake eq syIntake}'>activeFilter</c:if>">${syIntake}</a>
                                                </c:if>
                                            </c:forEach>
                                        </div>

                                    </div>
                                </c:if>

                            </div>


                            <div  class="col-sm-10">


                                <div class="table-responsive">
                                    <table id="userTable" class="table table-bordered table-hover">

                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <c:if test='${param.filter eq 3}'><th><spring:message code="manageCourse2.major" /></th></c:if>
                                            <c:if test='${param.filter ne 3}'><th><spring:message code="manageUsers.role" /></th></c:if>
                                            <th><spring:message code="manageUsers.name" /></th>
                                            <th><spring:message code="manageUsers.namePY" /></th>
                                            <th><spring:message code="manageUsers.idNumber" /></th>
                                            <th><spring:message code="manageUsers.gender" /> </th>
                                            <th><spring:message code="manageUsers.class" /></th>
                                            <%--<th><spring:message code="manageUsers.country" /></th>--%>
                                            <th><spring:message code="manageUsers.lastAccessed" /></th>
                                            <th><spring:message code="manageUsers.action" /></th>
                                        </tr>
                                        </thead>

                                        <tbody id="addNewUsers">

                                        <c:forEach var = "sysUser" items="${userTableList}" varStatus="index">
                                            <tr>
                                                <td>${index.count}</td>
                                                <c:if test='${param.filter eq 3}'><td> <c:out value="${sysUser.majorShortCode}"/></td></c:if>
                                                <c:if test='${param.filter ne 3}'> <td><c:out value="${sysUser.roleName}"/> </td></c:if>
                                                <td><a  href="${url}/admin/profile/view/${sysUser.userId}"><c:out value="${sysUser.userName}"/> </a> </td>
                                                <td><a  href="${url}/admin/profile/view/${sysUser.userId}"><c:out value="${sysUser.lastName}"/> </a> </td>
                                                <td><c:out value="${sysUser.userId}"/></td>
                                                <td><c:out value="${sysUser.gender}"/></td>
                                                <td><c:out value="${sysUser.className}"/></td>
                                                <td><c:out value="${sysUser.lastLogin}"/><c:if test="${empty sysUser.lastLogin }"> Never </c:if></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${currentUserRoleP eq 'admin'}">
                                                            <div class="dropdown">
                                                                <button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />
                                                                    <span class="caret"></span></button>
                                                                <ul class="dropdown-menu dropdown-menu-right">
                                                                    <li><a href="${url}/admin/profile/view/${sysUser.userId}"><spring:message code="extra.view"/>    &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>
                                                                    <li><a href="${url}/admin/profile/edit/${sysUser.userId}/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                                    <c:choose>
                                                                        <c:when test="${sysUser.roleName eq 'student'}">
                                                                            <li><a style="cursor: pointer" onclick="deleteStudent(${sysUser.userId})"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <li><a style="cursor: pointer" onclick="disableUser(${sysUser.userId})"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${url}/admin/profile/view/${sysUser.userId}" style="width: 70px ;color: #fff" class="btn btn-danger btn-xs" type="button" ><spring:message code="extra.view" /></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>

                                    </table>

                                </div><!--end of .table-responsive-->

                            </div>
                        </c:if>

                        <c:if test="${object eq 'classes'}">
                            <h3><spring:message code="manageUsers.manageClasses" /></h3>

                            <%--!TABLE--%>
                            <div class="table-responsive">

                                <div class="col-sm-2">
                                    <br/>


                                    <div>
                                        <h4  class="titleTextBoxHeaders semesterHeader"><spring:message code="manageUsers2.intake" /></h4>
                                        <div style="width: 100%" class="input-group"><input style="width: 100%" type="text" class="form-control" placeholder="Search"></div>
                                    </div>
                                    <div class=" filterBoxCourse">

                                        <div  class="list-group">
                                            <a href="${url}/admin/manage-users/classes" class="list-group-item <c:if test='${intake eq null}'>active</c:if>"><spring:message code="extra.all"/> </a>
                                            <c:forEach var = "syIntake" items="${classIntakeList}" varStatus="index">
                                                <c:if test="${empty currentMajor}">
                                                    <a href="${url}/admin/manage-users/classes?intake=${syIntake}" class="list-group-item <c:if test='${ syIntake eq intake}'>active</c:if>">${syIntake}</a>
                                                </c:if>
                                                <c:if test="${!empty currentMajor}">
                                                    <a href="${url}/admin/manage-users/classes?intake=${syIntake}&major=${majorId}" class="list-group-item <c:if test='${syIntake eq intake}'>activeFilter</c:if>">${syIntake}</a>
                                                </c:if>

                                            </c:forEach>
                                        </div>

                                    </div>
                                </div>
                                <br/>

                                <div class="col-sm-10">
                                    <table  id="classTable" class="table table-bordered table-hover">

                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th><spring:message code="manageCourse2.major"/></th>
                                            <th><spring:message code="manageUsers.intake" /></th>
                                            <th><spring:message code="manageUsers.className" /></th>
                                            <%--<th><spring:message code="manageUsers.classId" /></th>--%>
                                            <th><spring:message code="manageUsers.numbrOfStd" /></th>
                                            <th><spring:message code="manageUsers.status" /></th>
                                            <th><spring:message code="manageUsers.action" /></th>
                                        </tr>
                                        </thead>

                                        <tbody>

                                        <c:forEach var = "sysClass" items="${classesTableList}" varStatus="index">
                                            <tr>
                                                <td>${index.count}</td>
                                                <td><c:out value="${sysClass.majorShortName}"/></td>
                                                <td><c:out value="${sysClass.intakePeriod}"/></td>
                                                <td><a href="${url}/admin/class/view/${sysClass.id}"><c:out value="${sysClass.className}"/></a></td>
                                                <%--<td><c:out value="${sysClass.id}"/></td>--%>
                                                <td><c:out value="${sysClass.numberOfStudents}"/></td>
                                                <td ><c:out value="${sysClass.status}"/></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${currentUserRoleP eq 'admin'}">
                                                            <div class="dropdown">
                                                                <button class="btn btn-danger btn-xs " type="button" data-toggle="dropdown"><spring:message code="main.action" />
                                                                    <span class="caret"></span></button>
                                                                <ul class="dropdown-menu dropdown-menu-right">
                                                                    <li><a href="${url}/admin/class/view/${sysClass.id}"><spring:message code="main.view" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-eye-open" aria-hidden="true"></span></a></li>
                                                                    <li><a href="${url}/admin/class/enroll/${sysClass.id}"><spring:message code="manageUsers2.enrollStudents" />  &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-plus" aria-hidden="true"></span></a></li>
                                                                    <li><a  href="${url}/admin/class/edit/${sysClass.id}"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>
                                                                    <li><a onclick="deleteClass(${sysClass.id})" href="#"><spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>
                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${url}/admin/class/view/${sysClass.id}" style="width: 70px ;color: #fff" class="btn btn-danger btn-xs" type="button" ><spring:message code="extra.view" /></a>
                                                        </c:otherwise>
                                                    </c:choose>


                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <%--<--END OF TABLE-->--%>
                        </c:if>

                    </div>

                    <%--&lt;%&ndash;/TAB CONTENT&ndash;%&gt;<!--MANAGE CLASSES-->--%>
                    <%--<div id="menu1" class="tab-pane fade">--%>
                        <%----%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;/MANAGE CLASSES&ndash;%&gt;--%>

                    <c:if test="${currentUserRoleP eq 'admin'}">
                        <%--MANAGE USER TABLE --%>
                        <div id="menu2" class="tab-pane fade ">
                            <h3><spring:message code="manageUsers.addUserOrClass" /></h3>
                                <%--ADD BOX CONTAINER --%>
                            <div class="addBoxContainer">


                                <div class="floating-box">

                                    <div class="addButtonBox">
                                        <a class="courseButton" href="${url}/admin/manage-users/add/1">
                                            <spring:message code="manageUsers.addNewStudent" /> <br/>
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
                                    </div>
                                    <div class="addButtonBox">
                                        <a class="courseButton" data-toggle="modal" data-target="#popUpWindow" href="#">
                                            <span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>
                                            <br/>
                                            <spring:message code="manageUsers.importStudentExcel" /> </a>
                                        <%--<a href="${url}/download/student-excel" class="unEnrollText"><br/><br/><spring:message code="manageUsers.downloadStudentExcel" /></a>--%>
                                    </div>

                                </div>
                                <div class="floating-box">

                                    <div class="addButtonBox">
                                        <a class="courseButton" href="${url}/admin/manage-users/add/2">
                                            <spring:message code="manageUsers.addNewTeacher" /><br/>
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>

                                    </div>
                                    <%--<div style="visibility: hidden" class="addButtonBox">--%>
                                        <%--<a class="courseButton" data-toggle="modal" data-target="#importTeacherExcel"href="#">--%>
                                            <%--<span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>--%>
                                            <%--<br/><spring:message code="manageUsers.importTeacherExcel" /></a>--%>
                                        <%--<a href="${url}/download/teacher-excel" class="unEnrollText"><br/><br/><spring:message code="manageUsers.downloadTeacherExcel" /></a>--%>
                                    <%--</div>--%>
                                    <div class="addButtonBox">
                                        <a class="courseButton" href="${url}/admin/class/add/">
                                            <spring:message code="manageUsers.addClassTeacher" /><br/>
                                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                        </a>

                                    </div>
                                </div>

                                    <%--adding class--%>
                                <%--<div class="floating-box">--%>


                                    <%--&lt;%&ndash;<div class="addButtonBox">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<a class="courseButton" data-toggle="modal" data-target="#importClassExcel"href="#">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<span class="glyphicon glyphicon-open-file" aria-hidden="true"></span>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<br/><spring:message code="manageUsers.importClassExcel" /></a>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;&lt;%&ndash;<a href="${url}/admin/download/class-excel" class="unEnrollText"><br/><br/><spring:message code="manageUsers.downloadClassExcel" /></a>&ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                <%--</div>--%>
                            </div>
                        </div>
                        <%--/ADD BOX CONTAINER --%>
                    </c:if>
                </div>
                <%--<!--Manage user table-->--%>
            </div>
            <%--MAIN TAB CONTAINER --%>

      </div>
        <%--/MAIN TAB CONTAINER--%>
</div>

    <%--/END OF MAIN CARD CONTAINER --%>


    <%--STUDENT FILE IMPOORT MODEL FORM --%>
    <%--<button type="button" class="btn btn-success" data-toggle="modal" data-target="#popUpWindow">Open Log In Window</button>--%>
    <div  class="modal fade" id="popUpWindow">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center" class="modal-title"><spring:message code="manageUsers2.h1" /> </h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form id="uploadStudentForm" method="POST" enctype="multipart/form-data">
                        <%--<div class="form-group">--%>
                            <%--&lt;%&ndash;<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;&ndash;%&gt;--%>
                            <%--<select id="major" class="form-control" name="majorId" required autocomplete="off">--%>
                                <%--<option value=""><spring:message code="main.selectAMajor" /></option>--%>
                                <%--<c:forEach var = "sysMajor" items="${majorSelectList}" varStatus="index">--%>
                                    <%--<option value="${sysMajor.majorId}">${sysMajor.majorName}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                            <%--<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;--%>
                            <%--<select id="intake" class="form-control" name="intake" autocomplete="off">--%>
                                <%--<option value=""><i><spring:message code="manageUsers2.selectUserIntake" /></i></option>--%>
                                <%--<c:forEach var = "inlist" items="${intakeSelectList}" varStatus="index">--%>
                                    <%--<option value="${inlist}">${inlist}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>

                        <div class="form-group">
                            <%--<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;--%>
                            <select id="classId" class="form-control" name="classId" autocomplete="off">
                                <option value=""><i><spring:message code="manageUsers2.selctClass" /></i></option>
                                <c:forEach var = "cllist" items="${classSelectList}" varStatus="index">
                                    <option value="${cllist.id}">${cllist.majorShortName} | ${cllist.className}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input name="userExcelListFile" required autocomplete="off" type="file">

                        <div class="modal-footer">
                            <button type="submit" onclick="uploadStudentExcel()" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile" /></button>
                        </div>
                    </form>
                    <%--FILE FORM --%>

                </div>
                <!--Button-->

            </div>
        </div>
    </div>

    <%--TEACHER FILE IMPOORT MODEL FORM --%>
    <div class="modal fade" id="importTeacherExcel">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center" class="modal-title"> <spring:message code="manageUsers2.h2" /></h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>
                    <form  id="uploadTeacherForm" method="POST" enctype="multipart/form-data">
                        <input name="teacherExcelListFile" required type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button type="submit" onclick="uploadTeacherExcel()" value="Import Excel File" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile" /></button>
                        </div>
                    </form>
                    <%--FILE FORM --%>

                </div>

            </div>
        </div>
    </div>

    <%--CLASS FILE IMPOORT MODEL FORM --%>
    <div class="modal fade" id="importClassExcel">
        <div style="width: 450px" class="modal-dialog">
            <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times</button>
                    <h3 style="text-align: center" class="modal-title"> <spring:message code="manageUsers2.h3" /></h3>
                </div>
                <!--Body (form)-->
                <div class="modal-body">

                    <%--FILE FORM --%>

                    <form id="uploadClassForm" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <%--<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;--%>
                            <select id="major" class="form-control" name="majorId" required autocomplete="off">
                                <option value=""><spring:message code="main.selectAMajor" /></option>
                                <c:forEach var = "sysMajor" items="${majorSelectList}" varStatus="index">
                                    <option value="${sysMajor.majorId}">${sysMajor.majorName}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="form-group">
                            <%--<a href="${url}/admin/major/null" class="majorLable">Major:</a>&nbsp;--%>
                            <select id="intake" class="form-control" name="intake" autocomplete="off">
                                <option value=""><i><spring:message code="manageUsers2.selectUserIntake" /></i></option>
                                <c:forEach var = "inlist" items="${intakeSelectList}" varStatus="index">
                                    <option value="${inlist}">${inlist}</option>
                                </c:forEach>
                            </select>

                        </div>


                        <input name="classExcelListFile" required type="file">
                        <%--<input type="submit" value="Import Excel File">--%>
                        <!--Button-->
                        <div class="modal-footer">
                            <button type="submit" onclick="uploadClassExcel()" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile" /></button>
                        </div>
                    </form>


                    <%--FILE FORM --%>

                </div>

            </div>
        </div>
    </div>
    <%--CLASS FILE IMPOORT MODEL FORM--%>


<%--User Excel Confirmation form --%>
    <div class="modal fade" id="enrolmentConfirmationTableModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div id="modelHeader" class="modal-header">
                <h4 class="alert alert-danger modal-title"><spring:message code="manageUsers2.error" /></h4>
                <%--<div >--%>
                    <%--<strong>Danger!</strong> Indicates a dangerous or potentially negative action.--%>
                <%--</div>--%>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <table class="table  table-striped table-condensed">
                    <thead id="tableHead">

                    </thead>
                    <tbody id="userConfirmTable">


                    </tbody>
                </table>
                <button type="button" class="btn btn-primary btn-block" data-dismiss="modal"><spring:message code="manageUsers2.ok" /></button>

            </div>

        </div>
    </div>
</div>
<%--CLASS FILE IMPOORT MODEL FORM--%>


<!--Content Ends  -->



<script type="text/javascript">

    $(document).ready(function() {

        $("#userTab").click(function(){
            <c:if test="${object ne 'users'}">
                window.location.href = '${url}/admin/manage-users/users';
            </c:if>
        });
        $("#classesTab").click(function(){
            <c:if test="${object ne 'classes'}">
               window.location.href = '${url}/admin/manage-users/classes';
            </c:if>
        });
       // $('#enrolmentConfirmationTableModel').modal('toggle');


        $('#userTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
        $('#classTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });

//        window.setTimeout(function() {
//            $(".alert").fadeTo(1000, 0).slideUp(1000, function(){
//                $(this).remove();
//            });
//        }, 1000);
    } );



    function deleteClass(classId) {
        var r = confirm('<spring:message code="msg.forclass" />');
        if (r == true) {

            $.ajax({
                url:'${url}/admin/class/delete-class/'+classId,
                type:'post',
                success:function(data){
                    //console.log(data);
                    if(data === "200"){
                        alert('<spring:message code="msg.forClass2" />');
                        location.reload(true);
                    }else {
                        alert('<spring:message code="msg.forClass3" />');
                    }

                },
                error : function(){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //nothing done
        }
    }

    function deleteStudent(studentId) {
        var r = confirm('<spring:message code="msg.forUserDelete" />');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-users/delete-student/'+studentId,
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
            //nothing done
        }
    }

    function disableUser(userId) {
        var r = confirm('<spring:message code="msg.forUserDelete" />');
        if (r == true) {
            $.ajax({
                url:'${url}/admin/manage-users/disable-admin/'+userId,
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
            //nothing done
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

    function uploadStudentExcel() {
        var data = new FormData($("#uploadStudentForm")[0]);

        $('#uploadStudentForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/manage-users/add-user-excel',
                type:'post',
                data:data,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success:function(data){
                    $('#popUpWindow').modal('toggle');
                    $("#uploadStudentForm").get(0).reset();
                    console.log(data.list);
                    if(data.status_code === 406){
                        alert('<spring:message code="manageUsers2.msg4" />');

                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
                        $('#modelHeader').append('<h4 class="alert alert-danger modal-title"><spring:message code="manageUsers2.error" /></h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageUsers2.userId" /></th>'+
                            '<th><spring:message code="manageUsers2.Name" /></th>'+
                            <%--'<th><spring:message code="manageUsers2.Gender" /></th>'+--%>
                            <%--'<th><spring:message code="manageUsers2.DegreeType" /></th>'+--%>
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].userId+'</td>'+
                                '<td>'+data.list[i].userName+'</td>'+
//                                '<td>'+data.list[i].gender+'</td>'+
//                                '<td>'+data.list[i].degreeType+'</td>'+
                                '</tr>'
                            );

                        }

                        $('#enrolmentConfirmationTableModel').modal('toggle');
                    }if(data.status_code === 200){
                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();

                        $('#major').val('');
                        $('#intake').val('');
                        $('#classId').val('');
//                        $('#addNewUsers').empty()
                        $('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') <spring:message code="msg.foraddingStudent" /> </h4>');

                        $('#tableHead').append(
                            '<tr>'+
                                '<th>#</th>'+
                                '<th><spring:message code="manageUsers2.userId" /></th>'+
                                '<th><spring:message code="manageUsers2.Name" /></th>'+
                                <%--'<th><spring:message code="manageUsers2.Gender" /></th>'+--%>
                                <%--'<th><spring:message code="manageUsers2.DegreeType" /></th>'+--%>
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].name);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].userId+'</td>'+
                                '<td>'+data.list[i].name+'</td>'+
//                                '<td>'+data.list[i].gender+'</td>'+
//                                '<td>'+data.list[i].degreeType+'</td>'+
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
                                <%--'<td><spring:message code="manageUsers2.never" /></td>'+--%>
                                <%--'<td>'+--%>
                                <%--'<div class="dropdown">'+--%>
                                <%--'<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />'+--%>
                                <%--'<span class="caret"></span></button>'+--%>
                                <%--'<ul class="dropdown-menu dropdown-menu-right">'+--%>
                                <%--'<li><a href="${url}/admin/profile/view/'+data.list[i].userId+'"><spring:message code="main.view" />   &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>'+--%>
                                <%--'<li><a href="${url}/admin/profile/edit/'+data.list[i].userId+'/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>'+--%>
                                <%--'<li><a style="cursor: pointer" onclick="deleteStudent('+data.list[i].userId+')"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>'+--%>
                                <%--'</ul>'+--%>
                                <%--'</div>'+--%>
                                <%--'</td>'+--%>
                                <%--'</tr>'--%>
                            <%--);--%>

                        }
                        $('#enrolmentConfirmationTableModel').modal('toggle');
                    }
                },
                error : function(data){
                    $('#popUpWindow').modal('toggle');
                    alert('<spring:message code="main.msgError" />');

                }
            });
            return false;
        });
    }

    function uploadTeacherExcel() {
        var data = new FormData($("#uploadTeacherForm")[0]);


        $('#uploadTeacherForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/manage-users/add-teacher-excel',
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                    $("#uploadTeacherForm").get(0).reset();
                    console.log(data);
                    if(data.status_code === 406){
                        alert('<spring:message code="manageUsers2.msg4" />');

                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
                        $('#modelHeader').append('<h4 class="alert alert-danger modal-title"><spring:message code="manageUsers2.msg3" /></h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageUsers2.userId" /></th>'+
                            '<th><spring:message code="manageUsers2.Name" /></th>'+
                            '<th><spring:message code="manageUsers2.Gender" /></th>'+
                            '<th><spring:message code="manageUsers2.role" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].userId+'</td>'+
                                '<td>'+data.list[i].firstName+'</td>'+
                                '<td>'+data.list[i].gender+'</td>'+
                                '<td>'+data.list[i].role+'</td>'+
                                '</tr>'
                            );
                        }
                        $('#enrolmentConfirmationTableModel').modal('toggle');

                    }if(data.status_code === 200){
                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
//                        $('#addNewUsers').empty()
                        $('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') <spring:message code="msg.forAddingTeacher" /> </h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageUsers2.userId" /></th>'+
                            '<th><spring:message code="manageUsers2.Name" /></th>'+
                            '<th><spring:message code="manageUsers2.Gender" /></th>'+
                            '<th><spring:message code="manageUsers2.role" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].userId+'</td>'+
                                '<td>'+data.list[i].firstName+'</td>'+
                                '<td>'+data.list[i].gender+'</td>'+
                                '<td>double-degree</td>'+
                                '</tr>'
                            );

                            $('#addNewUsers').prepend(
                                '<tr class="success">'+
                                '<td>#</td>'+
                                '<td><a  href="${url}/admin/profile/view/'+data.list[i].userId+'">'+data.list[i].firstName+'</a> </td>'+
                                '<td>'+data.list[i].userId+'</td>'+
                                '<td>'+data.list[i].role+'</td>'+
                                '<td>'+data.list[i].gender+'</td>'+
                                '<td></td>'+
                                '<td>'+data.list[i].country+'</td>'+
                                '<td><spring:message code="manageUsers2.never" /></td>'+
                                '<td>'+
                                '<div class="dropdown">'+
                                '<button class="btn btn-danger btn-xs" type="button" data-toggle="dropdown"><spring:message code="manageUsers.action" />'+
                                '<span class="caret"></span></button>'+
                                '<ul class="dropdown-menu dropdown-menu-right">'+
                                '<li><a href="${url}/admin/profile/view/'+data.list[i].userId+'"><spring:message code="main.view" />    &nbsp;&nbsp;&nbsp;&nbsp;<span class="inIcon glyphicon glyphicon-user" aria-hidden="true"></span></a></li>'+
                                '<li><a href="${url}/admin/profile/edit/'+data.list[i].userId+'/2"><spring:message code="coursePage.edit" />    &nbsp;&nbsp;&nbsp;&nbsp; <span class="inIcon glyphicon glyphicon-pencil" aria-hidden="true"></span></a></li>'+
                                '<li><a style="cursor: pointer" onclick="disableUser('+data.list[i].userId+')"> <spring:message code="coursePage.delete" />  &nbsp; <span class="inIcon glyphicon glyphicon-trash" aria-hidden="true"></span></a></li>'+
                                '</ul>'+
                                '</div>'+
                                '</td>'+
                                '</tr>'
                            );

                        }
                        $('#enrolmentConfirmationTableModel').modal('toggle');
                    }
                    if(data.status_code === 500){
                        alert('<spring:message code="msg.systemUploadError" />');
                    }
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });
    }

    function uploadClassExcel() {
        var data = new FormData($("#uploadClassForm")[0]);
        // alert("here");
        $('#uploadClassForm').submit(function(e){
            e.preventDefault();
            e.stopImmediatePropagation();
            $.ajax({
                url:'${url}/admin/manage-users/add-class-excel',
                type:'post',
                data:data,
                contentType: false,
                processData: false,
                success:function(data){
                    $("#uploadClassForm").get(0).reset();
                    console.log(data);
                    // $('#popUpWindow').modal('toggle');
                    $("#uploadStudentForm").get(0).reset();
                    console.log(data.list);
                    if(data.status_code === 406){
                        alert('<spring:message code="manageUsers2.msg4" />');

                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();
                        $('#modelHeader').append('<h4 class="alert alert-danger modal-title"><spring:message code="addClassForm.msg4" /></h4>');

                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageUsers2.intake" /></th>'+
                            '<th><spring:message code="classStudentEnrolmentPage.className" /></th>'+
                            '<th><spring:message code="manageUsers.status" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].intakePeriod+'</td>'+
                                '<td>'+data.list[i].className+'</td>'+
                                '<td>active</td>'+
                                '</tr>'
                            );
                        }

                        $('#enrolmentConfirmationTableModel').modal('toggle');
                    }if(data.status_code === 200){
                        $('#modelHeader').empty();
                        $('#userConfirmTable').empty();
                        $('#tableHead').empty();

                        $('#major').val('');
                        $('#intake').val('');
                        $('#classId').val('');
//                        $('#addNewUsers').empty()
                        $('#modelHeader').append('<h4 class="alert alert-success modal-title">('+data.list.length+') <spring:message code="addClassForm.msg5" /> </h4>');
                        $('#tableHead').append(
                            '<tr>'+
                            '<th>#</th>'+
                            '<th><spring:message code="manageUsers2.intake" /></th>'+
                            '<th><spring:message code="classStudentEnrolmentPage.className" /></th>'+
                            '<th><spring:message code="manageUsers.status" /></th>'+
                            '</tr>'
                        );

                        for (var i = 0, len = data.list.length; i < len; i++) {
                            console.log(data.list[i].userName);
                            $('#userConfirmTable').append(
                                ' <tr>'+
                                '<td>'+(i+1)+'</td>'+
                                '<td>'+data.list[i].intakePeriod+'</td>'+
                                '<td>'+data.list[i].className+'</td>'+
                                '<td>active</td>'+
                                '</tr>'
                            );

                        }
                        $('#enrolmentConfirmationTableModel').modal('toggle');
                    }
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });
        });
    }

    $("#majorSelect").change(function(){

        var thisvalue = $(this).find("option:selected").val();
        <c:if test="${ object eq 'users' }">
            if(thisvalue === "" ){
                <c:if test="${ intake ne  null }">
                window.location.href = '${url}/admin/manage-users/users?filter=3&intake=${intake}';
                </c:if>
            }else {
                <c:if test="${intake eq null}">
                window.location.href = '${url}/admin/manage-users/users?filter=3&major='+thisvalue;
                </c:if>
                <c:if test="${ intake ne  null }">
                window.location.href = '${url}/admin/manage-users/users?filter=3&intake=${intake}&major='+thisvalue;
                </c:if>
            }
        </c:if>

        <c:if test="${ object eq 'classes' }">
            if(thisvalue === "" ){

                <c:if test="${intake eq null}">
                    window.location.href = '${url}/admin/manage-users/classes';
                </c:if>
                <c:if test="${ intake ne  null }">
                window.location.href = '${url}/admin/manage-users/classes?intake=${intake}';
                </c:if>
            }else {
                <c:if test="${intake eq null}">
                        window.location.href = '${url}/admin/manage-users/classes?&major='+thisvalue;
                </c:if>
                <c:if test="${ intake ne  null }">
                     window.location.href = '${url}/admin/manage-users/classes?intake=${intake}&major='+thisvalue;
                </c:if>
            }
        </c:if>


    });

</script>

</body>

</html>
