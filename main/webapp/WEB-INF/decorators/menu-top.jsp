<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 05/08/2017
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <span class="userName"><c:out value="${sessionScope.userName}" /></span>
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${url}/student/profile"><spring:message code="application.myProfile"/></a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="${url}/logOut"><spring:message code="application.logOut" /></a></li>
                </ul>
            </div>
        </div>
        <!--Profile dropdown-->

    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" ><a href="${url}/student/home" aria-controls="home" role="tab" ><spring:message code="studentLable.home" /></a></li>
        <li role="presentation"><a href="${url}/student/my-courses/Courses" aria-controls="profile" role="tab" ><spring:message code="studentLable.myCourses" /></a></li>
        <li role="presentation"><a href="${url}/student/grades" aria-controls="messages" role="tab" ><spring:message code="studentLable.grades" /></a></li>
        <li role="presentation"><a href="${url}/student/activity" aria-controls="settings" role="tab" ><spring:message code="extra.notifications" /></a></li>
    </ul>
</nav>