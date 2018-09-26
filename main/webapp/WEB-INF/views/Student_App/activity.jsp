<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="extra.notifications" /></title>
    <link href="${url}/resources/student_app_static/css/activity.css" rel="stylesheet">
    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>


<div>

    <!-- Tab panes -->
    <div class="content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div id="notifications" class="row mainContainer">

                <div class="col-sm-12 ">
                    <h4><spring:message code="extra.notifications" /></h4>
                    <div class="bigCardContainer">

                        <div class="searchBoxSty">
                            <div class="input-group">
                                <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                    <%--<ul class="dropdown-menu dropdown-menu-right">--%>
                                        <%--<li><a class="sort" data-sort="name" href="#"><spring:message code="enrollStudent.sort1" /></a></li>--%>
                                    <%--</ul>--%>
                                </div><!-- /btn-group -->
                            </div><!-- /input-group -->
                        </div>

                        <!--TimeLine-->
                        <div class="timeline-container">
                            <div id="timeline">
                                <ul style="list-style-type: none" class="list">
                                <c:set var="count" value="0" scope="page" />
                                <c:forEach var = "newsFeed" items="${notificationList}">

                                    <c:choose>

                                        <c:when test="${count == 0}">
                                        <li>
                                            <div class="timeline-item">
                                                <div class="timeline-icon">
                                                    <i class="fa fa-envelope-open-o" aria-hidden="true"></i>
                                                </div>
                                                <div class="timeline-content">
                                                    <h2 class="titleName" ><c:out value="${newsFeed.title}"/></h2>
                                                    <c:out value="${newsFeed.message}" escapeXml="false"/>


                                                    <%--<a href="${url}/download/docs/${lessonUploads.uploadUrl}">--%>
                                                        <%--<div class="icon blue">--%>
                                                            <%--<span class="doc-icon ptt">⧉</span>--%>
                                                            <%--<span class="doc-type">file</span>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="downloadText"><c:out value="${lessonUploads.uploadName}"/></div>--%>
                                                    <%--</a>--%>


                                                    <span class="time-stamp"><a class="teacherName" href="subPages/teacherProfile.jsp"><c:out value="${newsFeed.createdByUserName}"/></a></span>
                                                    <br/>
                                                    <span class="time-stamp"><c:out value="${newsFeed.createdAt}"/></span>
                                                </div>
                                            </div>
                                            <c:set var="count" value="${count + 1}" scope="page"/>
                                        </li>

                                        </c:when>

                                        <c:otherwise>

                                            <li>
                                            <div class="timeline-item">
                                                <div class="timeline-icon">
                                                    <i class="fa fa-envelope-open-o" aria-hidden="true"></i>
                                                </div>
                                                <div class="timeline-content">
                                                    <h2 class="titleName"><c:out value="${newsFeed.title}"/></h2>
                                                    <c:out value="${newsFeed.message}" escapeXml="false"/>
                                                    <span class="time-stamp"><a class="teacherName" href="subPages/teacherProfile.jsp"><c:out value="${newsFeed.createdByUserName}"/></a></span>
                                                    <br/>
                                                    <span class="time-stamp"><c:out value="${newsFeed.createdAt}"/></span>
                                                </div>
                                            </div>
                                            <c:set var="count" value="${count - 1}" scope="page"/>
                                            </li>

                                        </c:otherwise>

                                    </c:choose>



                                </c:forEach>
                                </ul>



                            </div>
                        </div>



                        <!--TimeLine-->

                    </div>
                </div>


            </div>

        </div>


    </div>

</div>

<script>
    var options = {
        valueNames: [ 'titleName']
    };
    var notifications  = new List('notifications', options);
</script>

</body>

</html>
