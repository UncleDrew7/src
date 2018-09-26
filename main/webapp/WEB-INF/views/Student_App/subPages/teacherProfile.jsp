<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 12/09/2017
  Time: 07:46
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
    <title><spring:message code="application.myProfile" /></title>
    <link href="${url}/resources/student_app_static/css/subPages/teacherProfile.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <h3><spring:message code="application.myProfile" /></h3>


        <!--Big cards-->
        <div class="col-sm-7 ">
            <%--<h4>Infor</h4>--%>
                <%--PROFILE CONTAINER --%>
                <div class="profileContainer">

                    <%--IMAGE BOX--%>
                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" src="${url}/resources/app_admin_static/images/admin-default.jpg" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <h2 class="media-heading">${userId}</h2>
                            <h4><spring:message code="user.Name" />   : ${userProfile.firstName}  ${userProfile.lastName} </h4>
                            <%--<h4><spring:message code="user.Class" />  : ${userProfile.className}</h4>--%>
                            <%--<h4><spring:message code="user.Intake" /> : ${userProfile.city}</h4>--%>
                            <h4><spring:message code="user.Gender" /> : ${userProfile.gender}</h4>
                            <%--<h4>City   : London</h4>--%>
                            <h4><spring:message code="user.Country" /> : ${userProfile.country}</h4>
                        </div>
                    </div>
                    <%--IMAGE BOX--%>


                </div>
                <%--PROFILE CONTAINER --%>

            <div class="card requestCardContainer">

                <div class="formTitleBox">
                    <h4><spring:message code="studentPageCourseLables.currentCourses" /> <span class="badge badge-secondary">${currentCourseCounts}</span></h4>
                </div>
                <div class="table-responsive">
                    <ul class="list">
                        <c:forEach var = "allCourses" items="${currentCourses}">
                            <c:choose>
                                <c:when test="${currentUserRole ne 'student'}">
                                    <li class="item"><h4><a style="text-decoration: none; color: inherit" href="${url}/admin/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="item"><h4><a style="text-decoration: none; color: inherit" href="${url}/student/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
                <%--<--END OF TABLE-->--%>
            </div>


        </div>
        <!--Big cards-->

        <!--Small cards-->
        <div class="col-sm-5 smallCardBox">
            <%--<h4>Courses</h4>--%>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#menu1"><h4><spring:message code="studentPageCourseLables.allCourses" />  <span class="badge badge-secondary">${allCoursesCount}</span></h4></a></li>
                <li><a data-toggle="tab" href="#allExams"><h4><spring:message code="system.allExam" />  <span class="badge badge-secondary">${allExamsCount}</span></h4></a></li>
            </ul>


            <div class="card bigCardContainer tab-content">
                <div id="menu1" class="tab-pane fade in active ">
                    <ul class="list">
                        <c:forEach var = "allCourses" items="${allCourses}">
                            <c:choose>
                                <c:when test="${currentUserRole ne 'student'}">
                                    <li class="item"><h4><a style="text-decoration: none" href="${url}/admin/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="item"><h4><a style="text-decoration: none" href="${url}/student/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>

                </div>
                <div id="allExams" class="tab-pane fade ">

                    <ul class="list">
                        <c:forEach var = "allStudentExams" items="${allExams}">
                            <c:choose>
                                <c:when test="${currentUserRole ne 'student'}">
                                    <li class="item"><h4><a href="${url}/admin/manage-courses/course-exam/${allStudentExams.courseId}" data-toggle="tooltip" data-placement="right" title="${allStudentExams.courseName}"><c:out value="${allStudentExams.gradeItemName}"/></a></h4></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="item"><h4><a href="${url}/student/course-exam/${allStudentExams.courseId}" data-toggle="tooltip" data-placement="right" title="${allStudentExams.courseName}"><c:out value="${allStudentExams.gradeItemName}"/></a></h4></li>>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>

                </div>
            </div>


        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->



</div>
<!--Content Ends  -->
<script>
    $("#show").click(function(){
        $(".enrolled").toggle(function(){
            $('#show').toggle();
            $('#hide').toggle();
        });
    });
    $('#hide').click(function () {
        $('.enrolled').toggle(function(){
            $('#show').toggle();
            $('#hide').toggle();
        });
    });

</script>

</body>

</html>