<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 28/08/2017
  Time: 06:56
  To change this template use File | Settings | File Templates.
--%>
<%@page session="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title>403</title>
    <link href="${url}/resources/authentication/css/403.css" rel="stylesheet">
    <script src="${url}/resources/main/jquery/jquery.js"></script>
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <section class="forbidden">
            <h3>403 ${referer}</h3>

            <p>You do not have permission to access this page!</p>
            <span id='r'>Returning to home page: <i>-</i></span>
        </section>

    </div>
    <!--MAIN CARD CONTAINER -->



</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {
        var i = 4;
        setInterval(function() {
            i--;
            if (i < 1) {
                window.location = "/student/home";
            }
            $("#r i").text(i);
        }, 1000);
    });
</script>

</body>

</html>