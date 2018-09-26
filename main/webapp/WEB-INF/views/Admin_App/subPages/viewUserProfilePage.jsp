<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 24/09/2017
  Time: 00:02
  To change this template use File | Settings | File Templates.
--%>
ange this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title>Admin |View User Profile</title>
    <link href="${url}/resources/app_admin_static/css/subPages/editUserProfile.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/grades.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/viewUserProfile.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-users/users" class="icon-home"> <spring:message code="adminNavLable.manageUsers" /></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a href="#">${userProfile.role}<spring:message code="extra.userProfile" /></a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <h3><spring:message code="extra.userProfile" /> </h3>

        <%--PROFILE CONTAINER --%>
        <div class="profileContainer">

            <%--IMAGE BOX--%>
            <c:if test="${userProfile.role eq 'Student'}">
                <h4 class="roleTitle"><span class="degreeTypeText"><spring:message code="user.DegreeType" />  : ${userProfile.degreeType}</span></h4>
            </c:if>
            <a href="${url}/admin/profile/edit/${userId}/2?nv=pf" class="link"><spring:message code="user.Edit" /></a>
            <div class="media">
                <div class="media-left">
                    <a href="#">
                        <img class="media-object" src="${url}/app_content/uploads/${userProfile.profileImageUrl}" alt="...">
                    </a>
                </div>
                <div class="media-body">
                    <h2 class="media-heading">${userId}
                        <c:choose>
                            <c:when test="${userProfile.role eq 'Student'}">
                                (<c:out value="${userProfile.role}"/>)
                            </c:when>
                            <c:when test="${userProfile.role eq 'Teacher'}">
                                (<c:out value="${userProfile.role}"/>)
                            </c:when>
                        </c:choose>
                    </h2>
                    <h4><spring:message code="user.Name" />   : ${userProfile.firstName}  ${userProfile.lastName} </h4>

                    <c:if test="${userProfile.role eq 'Student'}">
                        <h4>Major  :${userProfile.majorName}</h4>
                        <h4><spring:message code="user.Class" />  : ${userProfile.className}</h4>
                        <h4><spring:message code="user.Intake" /> : ${userProfile.intake}</h4>
                    </c:if>

                    <h4><spring:message code="user.Gender" /> : ${userProfile.gender}</h4>
                    <%--<h4>City   : London</h4>--%>
                    <h4><spring:message code="user.Country" />: ${userProfile.country}</h4>
                </div>
            </div>
            <%--IMAGE BOX--%>


        </div>
        <%--PROFILE CONTAINER --%>


        <!--Big cards-->
        <div class="col-sm-7 ">
            <%--<h4>Infor</h4>--%>
            <div class="card aboutCardContainer ">

                <div class="formTitleBox">
                    <h4><spring:message code="user.About" /> <span class="inIcon glyphicon glyphicon-menu-down" aria-hidden="true"></span></h4>
                </div>

                <p>${userProfile.selfDescription}</p>

            </div>

            <div class="card requestCardContainer">

                <div class="formTitleBox">
                    <h4><spring:message code="user.gradeOverView" /> : ${userProfile.firstName}&nbsp;${userProfile.lastName}</h4>
                </div>

                <div class="tableArea">
                    <!--Grade Table -->

                    <table id="customers">
                        <col class="columnOne">
                        <col class="columnTwo">
                        <tr>
                            <th><spring:message code="studentPageCourseLables.courseName" /></th>
                            <th><spring:message code="studentPageCourseLables.grade" /></th>
                        </tr>
                        <c:forEach var="courseGradeAverage" items='${coursesWithGradeAverageList}' >
                            <tr>

                                <td><a class="gradeText" href="${url}/admin/grades/student-course-grades/${courseGradeAverage.childCourseId}/${userId}"><c:out value="${courseGradeAverage.courseName}/${courseGradeAverage.courseShortName}"/></a></td>
                                <td><c:if test="${courseGradeAverage.gradeAverage ne 0.0}"><c:out value="${courseGradeAverage.gradeAverage}"/></c:if></td>

                            </tr>
                        </c:forEach>

                    </table>
                    <!--End Grade Table-->
                </div>

            </div>


        </div>
        <!--Big cards-->

        <!--Small cards-->
        <div class="col-sm-5 smallCardBox">
            <%--<h4>Courses</h4>--%>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#menu1"><h4><spring:message code="system.allCourses" />  <span class="badge badge-secondary">${allCourseTotal}</span></h4></a></li>
                <li><a data-toggle="tab" href="#allExams"><h4><spring:message code="system.allExam" />  <span class="badge badge-secondary">${allExamCount}</span></h4></a></li>
            </ul>


            <div class="card bigCardContainer tab-content">

                <div id="menu1" class="tab-pane fade in active  ">


                    <c:forEach var = "semesterList" items="${semesterList}">
                        <div class="panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panelT">
                                        <a data-toggle="collapse" href="#${semesterList.semesterId}">${semesterList.semesterCode}</a>
                                    </h4>
                                </div>
                                <div id="${semesterList.semesterId}" class="recordeform panel-collapse collapse">
                                    <c:set var="contains" value="false" />
                                    <c:forEach var = "c" items="${currentCoursesList}">
                                        <c:if test="${c.semseterId eq semesterList.semesterId}">
                                            <ul class="list">
                                                <li class="item"><h4><a style="text-decoration: none" href="${url}/admin/view/course/${c.childCourseId}"><c:out value="${c.courseName}/${c.courseShortName}"/></a></h4></li>
                                            </ul>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div id="allExams" class="tab-pane fade ">

                    <c:forEach var = "semesterList" items="${semesterList}">
                        <div class="panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panelT">
                                        <a data-toggle="collapse" href="#${semesterList.semesterId}b">${semesterList.semesterCode}</a>
                                    </h4>
                                </div>
                                <div id="${semesterList.semesterId}b" class="recordeform panel-collapse collapse">
                                    <c:set var="contains" value="false" />
                                    <c:forEach var = "allExams" items="${examsList}">
                                        <c:if test="${allExams.semesterId eq semesterList.semesterId}">
                                            <ul class="list">
                                                <li class="item"><h4> <a href="${url}/admin/manage-courses/course-exam/${allExams.courseId}?nv=pf"  ><c:out value="${allExams.parentCourseShortName}/${allExams.id}"/></a></h4></li>
                                                <%--<li class="item"><h4> <a href="${url}/admin/manage-courses/course-exam/${allExams.courseId}?nv=pf"  data-toggle="tooltip" data-placement="right" title="${allExams.examName}"><c:out value="${allExams.examCourseShortName}/${allExams.examId}"/></a></h4></li>--%>
                                            </ul>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>



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

    $(document).ready(function(){
        $(".aboutCardContainer").click(function(){
            $(".aboutCardContainer").toggleClass("aboutCardContainerClicked");
        });
    });

</script>

</body>

</html>