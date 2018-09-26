<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="studentGrades.grades"/></title>
    <link href="${url}/resources/student_app_static/css/grades.css" rel="stylesheet">
</head>

<body>

    <div>

        <!-- Tab panes -->
        <div class="content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <div class="row mainContainer">

                    <%--<div class="form-group pull-right">
                        <div class="btn-group" role="group" aria-label="...">
                            <a type="button" class="btn btn-default"  href="${url}/admin/download/${semesterId}/${majorId}/${intakeId}/student-grades-excel"><spring:message code="manageGrades2.exportGradeRecord" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                        </div>
                    </div>--%>

                    <!--Small cards-->
                    <div class="col-sm-3">
                        <br/><br/>
                        <%--<div class="card  filterBox ">--%>
                            <%--<div>--%>
                                <%--<h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.filterCoures" /></h4>--%>
                            <%--</div>--%>
                            <%--&lt;%&ndash;<div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin-top: -11px" class="list-group">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<a href="${url}/student/grades/101/1" class="list-group-item <c:if test='${filter eq 1}'>active</c:if>"><spring:message code="studentPageCourseLables.currentCourses" /><span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<a href="${url}/student/grades/102/2" class="list-group-item <c:if test='${filter eq 2}'>active</c:if>"><spring:message code="studentPageCourseLables.completedCourses" /></a>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<a href="${url}/student/grades/103/3" class="list-group-item <c:if test='${filter eq 3}'>active</c:if>"><spring:message code="studentPageCourseLables.allCourses" /></a>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                      <%--&lt;%&ndash;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--</div>--%>

                        <div class="card">
                            <div>
                                <h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.semesterCategory" /></h4>
                            </div>
                            <div style="margin-top: -11px" class="list-group">
                                <a href="${url}/student/grades" class="list-group-item <c:if test='${semesterId eq null}'>active</c:if>">
                                    <spring:message code="extra.all"/>
                                </a>
                                <c:forEach var = "sList" items="${semesterList}">
                                    <a href="${url}/student/grades?semester=${sList.semesterId}" class="list-group-item <c:if test='${semesterId eq sList.semesterId}'>active</c:if>">
                                        <c:out value="${sList.semesterCode}"/>
                                    </a>
                                </c:forEach>

                            </div>
                        </div>


                    </div>
                    <!--Small cards-->

                    <div class="col-sm-9 ">
                        <%--<h4><spring:message code="studentPageTitle.grades" /></h4>--%>
                        <br/>

                            <div class="col-sm-6 ">
                            <div class="searchBox" align="center">
                                <div class="searchFormContainer">
                                    <%--<form class="searchForm" role="search" >--%>
                                    <%--<form class="searchForm" role="search" action="${url}/student/search/courses?search" method="GET">--%>
                                    <div class="focus-container" tabindex="-1">
                                        <div class="input-group add-on">
                                            <input type="text" class="form-control" placeholder="<spring:message code="homePageLable.search" />" name="search" id="srch-term" onchange="searching()">
                                            <div class="input-group-btn">
                                                <button class="btn btn-default" ><i class="glyphicon glyphicon-search"></i></button>
                                                <%--<button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>--%>
                                            </div>
                                        </div>
                                    </div>
                                    <%--</form>--%>
                                </div>
                            </div>

                            </div>


                            <br/> <br/><br/>

                        <div class="card bigCardContainer">


                            <!--Grade Table -->

                            <table id="customers">
                                <tr>

                                    <th><spring:message code="studentPageCourseLables.courseName" /></th>
                                    <th><spring:message code="studentPageCourseLables.courseShortName" /></th>
                                    <th><spring:message code="manageCourse2.credits"/></th>
                                    <th><spring:message code="studentPageCourseLables.grade" /></th>
                                </tr>
                                <c:forEach var="courseGradeAverage" items='${coursesWithGradeAverageList}' >
                                    <tr>
                                        <td><a class="gradeText" href="${url}/student/grades/full-report/${courseGradeAverage.childCourseId}"><c:out value="${courseGradeAverage.courseName}"/></a></td>
                                        <td><a class="gradeText" href="${url}/student/grades/full-report/${courseGradeAverage.childCourseId}"><c:out value="${courseGradeAverage.courseShortName}"/></a></td>
                                        <td><c:if test="${courseGradeAverage.credits ne 0.0}"><c:out value="${courseGradeAverage.credits}"/></c:if></td>
                                        <td><c:if test="${courseGradeAverage.gradeAverage ne 0.0}"><c:out value="${courseGradeAverage.gradeAverage}"/></c:if></td>


                                    </tr>
                                </c:forEach>

                            </table>

                            <!--End Grade Table-->

                        </div>
                    </div>



                </div>

            </div>

        </div>

    </div>

</body>

<script>
    function searching() {
    //        alert("test!");
    var srchText = $("#srch-term").val();

    <%--<c:if test="${object ne 'Exam'}">
        window.location.href = '${url}/student/my-courses-search/Courses?search=' + srchText;
    </c:if>
    <c:if test="${object eq 'Exam'}">
        window.location.href = '${url}/student/my-courses-search/Exam?search=' + srchText
    </c:if>--%>
    }
</script>
</html>