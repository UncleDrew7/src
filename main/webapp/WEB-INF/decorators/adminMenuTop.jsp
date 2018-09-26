<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 03:19
  To change this template use File | Settings | File Templates.
  navigation decorator for admin pages
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<nav  class="navbar navbar-fixed-top">
    <div class="titleBox">
        <a class="navbar-brand" href="/student/home">CDAI</a>
        <!--Profile dropdown-->
        <div class=" profilBox">
            <div class="dropdown ">

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
                    <li style="text-align: center; font-weight: 400"><b>(<c:out value="${sessionScope.roleName}" />)</b></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="${url}/admin/view/user-profile"><spring:message code="application.myProfile"/></a></li>
                    <li><a href="${url}/logOut"><spring:message code="application.logOut" /></a></li>
                </ul>
            </div>
        </div>
        <!--Profile dropdown-->

    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" ><a href="${url}/admin/dashboard" ><spring:message code="adminNavLable.dashboard" /></a></li>
        <li role="presentation"><a href="${url}/admin/manage-users/users" ><spring:message code="adminNavLable.manageUsers" /></a></li>
        <c:choose>
            <c:when test="${sessionScope.roleName eq 'teacher'}"> <li role="presentation"><a href="${url}/admin/manage-courses/courses" ><spring:message code="adminNavLable.manageCourses" /></a></li></c:when>
            <c:otherwise><li role="presentation"><a href="${url}/admin/manage-courses/courses" ><spring:message code="adminNavLable.manageCourses" /></a></li></c:otherwise>
        </c:choose>

        <li role="presentation"><a href="${url}/admin/manage-exams/exams" ><spring:message code="manageExams.manageExams" /></a></li>
        <li role="presentation"><a href="${url}/admin/grades"><spring:message code="adminNavLable.grades" /></a></li>
        <li role="presentation"><a href="${url}/admin/publish" ><spring:message code="extra.notifications" /></a></li>
        <!-- <li role="presentation"><a href="${url}/admin/reports" ><spring:message code="extra.logs"/></a></li> -->
        <c:if test="${sessionScope.roleName eq 'admin'}"><li role="presentation"><a href="${url}/admin/reports" ><spring:message code="extra.logs"/></a></li> </c:if>
        <%--<li role="presentation"><a href="${url}/admin/backup" ><spring:message code="adminNavLable.backup" /></a></li>--%>
    </ul>
</nav>

