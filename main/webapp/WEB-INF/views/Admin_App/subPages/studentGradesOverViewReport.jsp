<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 08/01/2018
  Time: 06:21
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
    <title><spring:message code="studentGradesOverviewReport.gradeOverView"/> </title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>



<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/view/course/" class="icon-home">Grade Report</a></li>
            <li class="last active"><a ><spring:message code="studentGradesOverviewReport.gradeOverViewReport"/></a></li>
        </ul>
    </div>

    <%--<h2 class="pageTitle">Manage Courses</h2>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <%--<div class="searchingBox">--%>
            <%--<div class="form-group pull-right">--%>
                <%--<div class="btn-group" role="group" aria-label="...">--%>
                    <%--<a href="${url}/admin/grades/grade-items/${courseId}" type="button" class="btn btn-default">Add/Edit Grades <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">


                <%---------------------------------------------------------------------------------------------------------------------%>
                <div id="gradeReport" class="tab-pane fade fade in active" >
                    <br/>
                    <h3 class="courseTitle"><spring:message code="studentGradesOverviewReport.courseGradeOverViewFor"/> </h3>
                    <div class="divider"></div>
                    <%--!TABLE--%>
                    <div class="table-responsive csgoContaier">


                        <div class="col-sm-3">
                            <div class="card">
                                <div style="background: #f8f8f8;margin-bottom: 11px;">
                                    <h4 class="titleTextBoxHeaders headBoxT"><spring:message code="sharedPageLable.couresCategory" /></h4>
                                </div>
                                <div style="margin-top: -11px" class="list-group">
                                    <a style="border-radius: 0" href="${url}/admin/grades/student-grade-overview/${studentId}" class="list-group-item <c:if test='${semesterId eq null}'>active</c:if>">
                                        <spring:message code="extra.all"/>
                                    </a>
                                    <c:forEach var = "sList" items="${semesterList}">
                                        <a href="${url}/admin/grades/student-grade-overview/${studentId}?semester=${sList.semesterId}" class="list-group-item <c:if test='${semesterId eq sList.semesterId}'>active</c:if>">
                                            <c:out value="${sList.semesterCode}"/>
                                        </a>
                                    </c:forEach>

                                </div>
                            </div>

                        </div>


                        <div class="col-sm-9">
                            <table  cellspacing="0" width="100%" id="gradeTable" class="table table-bordered table-hover">

                                <%--<caption class="">--%
                                <%--</caption>--%>
                                <thead >
                                <tr>
                                    <th><spring:message code="studentPageCourseLables.courseName" /></th>
                                    <th><spring:message code="studentPageCourseLables.courseShortName" /></th>
                                    <th><spring:message code="manageCourse2.credits"/></th>
                                    <th><spring:message code="studentPageCourseLables.grade" /></th>

                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="courseGradeAverage" items='${coursesWithGradeAverageList}' >
                                    <tr>
                                        <td><a class="gradeText" href="${url}/admin/grades/student-course-grades/${courseGradeAverage.childCourseId}/${studentId}"><c:out value="${courseGradeAverage.courseId}"/></a></td>
                                        <td><a class="gradeText" href="${url}/admin/grades/student-course-grades/${courseGradeAverage.childCourseId}/${studentId}"><c:out value="${courseGradeAverage.courseName}"/></a></td>
                                        <td><c:if test="${courseGradeAverage.credits ne 0.0}"><c:out value="${courseGradeAverage.credits}"/></c:if></td>
                                        <td><c:if test="${courseGradeAverage.gradeAverage ne 0.0}"><c:out value="${courseGradeAverage.gradeAverage}"/></c:if></td>

                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                        </div>
                    </div>

                </div>
                <%----------------------------------------------------------------%>




            </div>




        </div>
        <%--/END OF MAIN CARD CONTAINER --%>


    </div>
    <!--Content Ends  -->
</div>
<script>

    $(document).ready(function() {
        $('#gradeTable').DataTable({
            "scrollX": true,
            scrollY: 600,
            scrollCollapse: true,
            paging: false,
            "info": false
        } );

    } );

    function userReport(itemId) {
        alert(itemId);
        <%--window.location.href='${url}/admin/manage-exams/exam/add-clearance-exam/${courseId}/'+itemId;--%>
    }
</script>
</body>

</html>
