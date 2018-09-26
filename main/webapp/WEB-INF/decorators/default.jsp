<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 05/08/2017
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--urls shortcut--%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${url}/resources/main/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${url}/resources/test.css"/>
    <!-- jquery must put before js files of bootstrap -->
    <script src="${url}/resources/main/jquery/jquery.js"></script>
    <script src="${url}/resources/main/bootstrap/js/bootstrap.min.js"></script>
    <title>
        <dec:title default="Kelab" />
    </title>
</head>
<body>

<div class="container bs-docs-container">
    <div id="m_header">
        <%@include file="header.jsp"%>
    </div>
    <%--/header--%>
    <div id="m_menu_top">
        <%@ include file="menu-top.jsp"%>
    </div>
    <!-- /top menu -->
    <div id="m_container">
        <!-- if not using sidebar, only add <dec:body /> in here -->
        <div id="content">
            <div class="row">
                <div class="col-sm-8">
                    <dec:body />
                </div>
                <div class="col-sm-4" style="border-left: 1px solid #fff; min-height: 300px;">
                    <%@ include file="sidebar.jsp"%>
                </div>
            </div>
        </div>
    </div>

    <%--/main--%>
    <div id="m_footer">
        <%@ include file="footer.jsp"%>
    </div>
    <!-- /footer -->

</div>

</body>
</html>
