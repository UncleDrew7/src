<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 03:27
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
    <title><spring:message code="reports1.title" /></title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/reports.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>


<%--<sql:query dataSource = "${snapshot}" var = "result">--%>
    <%--SELECT * FROM ucms.log_history;--%>
<%--</sql:query>--%>
<%--TEST DATASOURCE --%>



<!--Content Begins  -->
<div class="content">
    <br/>
    <h3 class="pageTitle"><spring:message code="reports.reports" /></h3>


    <!--MAIN CARD CONTAINER -->
    <div class="card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <%--<div class="searchingBox">--%>
            <%--<div class="form-group pull-right">--%>
                <%--<input type="text" class="search form-control" placeholder="Search Logs">--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">

            <!--NAV TABS -->
            <br/>
            <ul class="nav nav-tabs">

                <li class="active"><a data-toggle="tab" href="#home"><spring:message code="reports.reports" /></a></li>

            </ul>
            <%--/NAV TABS--%>

            <!--TAB CONTENT-->
            <div class="tab-content">

                <%--MANAGE USER TABLE --%>
                <div id="home" class="tab-pane fade in active">

                    <h3><spring:message code="reports.systemLogs" /> </h3>

                    <div class="table-responsive">
                        <table id="logTable" class="table table-bordered table-hover">

                            <%--<div class="select-style">--%>
                                <%--<select>--%>
                                    <%--<option value="">Filter By ;</option>--%>
                                    <%--<option value="volvo">Student</option>--%>
                                    <%--<option value="saab">Teacher</option>--%>
                                    <%--<option value="mercedes">All</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>

                            <%--<caption class="">--%>
                                <%--<span>Showing 1 to 8 of 8 entries</span>--%>
                                <%--<!--course buttons-->--%>
                                <%--<a class="courseButton" href="#">Prev</a>--%>
                                <%--<a class="courseButton" href="#">Next</a>--%>
                                <%--<!--course buttons-->--%>

                            <%--</caption>--%>


                            <thead>
                            <tr>
                                <th>#</th>
                                <th><spring:message code="studentForm.userId" /></th>
                                <th><spring:message code="user.Name" /></th>
                                <th><spring:message code="manageUsers.role" /></th>
                                <th><spring:message code="reports.ipAddress" /></th>
                                <th><spring:message code="reports.time" /></th>
                                <th><spring:message code="extra.platform" /></th>
                                <th><spring:message code="reports.location" /></th>
                                <th><spring:message code="reports.action" /></th>

                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach var = "logs" items="${logList}" varStatus="index">

                                <tr>
                                    <td><c:out value="${index.count}"/></td>
                                    <td><c:out value="${logs.userId}"/></td>
                                    <td><c:out value="${logs.userName}"/></td>
                                        <td><c:out value="${logs.userRole}"/></td>
                                    <td><c:out value="${logs.ipAddress}"/></td>
                                    <td><c:out value="${logs.time}"/></td>
                                        <td><c:out value="${logs.app}"/></td>
                                    <td><c:out value="${logs.locationName}"/></td>
                                    <td><c:out value="${logs.action}"/></td>
                                </tr>

                            </c:forEach>



                            </tbody>

                            <%--<tfoot>--%>
                            <%--<tr>--%>
                            <%--<td colspan="5" class="text-center">Data retrieved from <a href="http://www.infoplease.com/ipa/A0855611.html" target="_blank">infoplease</a> and <a href="http://www.worldometers.info/world-population/population-by-country/" target="_blank">worldometers</a>.</td>--%>
                            <%--</tr>--%>
                            <%--</tfoot>--%>
                        </table>





                    </div><!--end of .table-responsive-->



                </div>


            </div>
            <%--MAIN TAB CONTAINER --%>

        </div>

        <%--/MAIN TAB CONTAINER--%>
    </div>

</div>
<%--/END OF MAIN CARD CONTAINER --%>


</div>
<!--Content Ends  -->
<script>
    $(document).ready(function() {
        $('#logTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });

    } );

    var translation ={
        "sProcessing":   "处理中...",
        "sLengthMenu":   "显示 _MENU_ 项结果",
        "sZeroRecords":  "没有匹配结果",
        "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix":  "",
        "sSearch":       "搜索:",
        "sUrl":          "",
        "sEmptyTable":     "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "首页",
            "sPrevious": "上页",
            "sNext":     "下页",
            "sLast":     "末页"
        },
        "oAria": {
            "sSortAscending":  ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    }
</script>

</body>

</html>
