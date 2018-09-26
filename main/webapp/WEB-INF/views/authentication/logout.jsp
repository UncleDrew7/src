<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 28/08/2017
  Time: 06:32
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
    <title>Search</title>
    <link href="${url}/resources/authentication/css/logout.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">
    <h1>Admin Search </h1>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <!--Big cards-->
        <div class="col-sm-9 ">
            <h4><spring:message code="studentPageTitle.home" /></h4>
            <div class="card bigCardContainer">
            </div>

        </div>
        <!--Big cards-->

        <!--Small cards-->
        <div class="col-sm-3">
            <div class="card bigCardContainer">
            </div>

        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->



</div>
<!--Content Ends  -->

</body>

</html>