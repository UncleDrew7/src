<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 13/08/2017
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<nav class="navbar navbar-fixed-top">
    <div class="titleBox">
        <a class="navbar-brand" href="/student/home">CDAI</a>
        <!--Profile dropdown-->
        <div class=" profilBox">
            <div class="dropdown">

                <span class="languageBox">
                    <c:if test="${pageContext.response.locale == 'en'}">
                        <a href="?lang=cn">Chinese</a>
                    </c:if>
                    <c:if test="${pageContext.response.locale == 'cn'}">
                        <a href="?lang=en">English</a>
                    </c:if>
                </span>

                <a class="dropdown-toggle dropDownbox" data-toggle="dropdown" href="#">
                    |&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    <span class="userName">Administrator</span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="user_profile.html"><spring:message code="application.myProfile"/></a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="student_main.html"><spring:message code="application.logOut" /></a></li>
                </ul>
            </div>
        </div>
        <!--Profile dropdown-->

    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="${url}/teacher/dashboard" ><spring:message code="adminNavLable.dashboard" /></a></li>
        <li role="presentation"><a href="${url}/teacher/manage-students" ><spring:message code="teacherNavLable.manageStudents" /></a></li>
        <li role="presentation"><a href="${url}/teacher/manage-courses" ><spring:message code="adminNavLable.manageCourses" /></a></li>
        <li role="presentation"><a href="${url}/teacher/manage-grades"><spring:message code="adminNavLable.grades" /></a></li>
        <li role="presentation"><a href="${url}/teacher/reports" ><spring:message code="adminNavLable.reports" /></a></li>
        <li role="presentation"><a href="${url}/teacher/publish" ><spring:message code="adminNavLable.publish" /></a></li>
        <li role="presentation"><a href="${url}/teacher/activity" ><spring:message code="teacherNavLable.activity" /></a></li>
    </ul>
</nav>