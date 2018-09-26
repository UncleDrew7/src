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
    <title>404 Page no found</title>
    <link href="${url}/resources/authentication/css/error.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">
    <h1>Admin Search </h1>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <div id="container">
            <div id="top">Oops!</div>
            <div id="middle">404</div>
            <div id="bottom">Sorry, couldn't find what you're looking for</div>
        </div>
        <link href="https://fonts.googleapis.com/css?family=Trocchi" rel="stylesheet">

    </div>
    <!--MAIN CARD CONTAINER -->



</div>
<!--Content Ends  -->

</body>

</html>