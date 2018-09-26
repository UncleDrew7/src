<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 08/01/2018
  Time: 06:41
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="studentCourseGrades.text1"/> </title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <div class="wrapper">
        <ul class="breadcrumbs">
            <li class=""><a href="${url}/student/grades" class="icon-home"><spring:message code="gradeReport.gradeReportT2" /></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a ><spring:message code="coursePage.gradeReport" /></a></li>
        </ul>
    </div>

    <%--<h2 class="pageTitle">Grade Report</h2>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <%--<div class="searchingBox">--%>
        <%--&lt;%&ndash;<div class="form-group pull-right">&ndash;%&gt;--%>

        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>


        <%--</div>--%>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">


                <%----------------------------------------------------------------%>

                <div id="userReport" class="tab-pane fade in active">
                    <br/>
                    <h3><c:out value="${courseName.courseName}"/> <spring:message code="extra.gradeReportFor" /> - ${userName}</h3><br/>
                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="userReportTable" class="table table-bordered table-hover">



                            <thead>
                            <tr>
                                <th><spring:message code="extra.type" /></th>
                                <th><spring:message code="gradeReport.gradeItem" /></th>
                                <th><spring:message code="gradeReport.grade" /></th>
                                <th><spring:message code="gradeReport.range" /></th>
                                <th><spring:message code="gradeReport.percentage" /></th>
                                <th><spring:message code="gradeReport.letter" /></th>
                            </tr>
                            </thead>

                            <tbody>


                            <c:forEach var="gradeReport" items='${courseGradeFullReportList}' >
                                <c:if test="${gradeReport.gradeItemType eq 'Exam'}">
                                    <tr>
                                        <td><span class="label label-default"><c:out value="${gradeReport.gradeItemType}"/></span></td>
                                        <td><c:out value="${gradeReport.gradeItemName}"/></td>
                                        <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.grade}"/></c:if></td>
                                        <td><c:out value="${gradeReport.minGrade}"/> - <c:out value="${gradeReport.maxGrade}"/></td>
                                        <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.percentage}"/>%</c:if></td>
                                        <td><c:out value="${gradeReport.letter}"/></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:forEach var="gradeReport" items='${courseGradeFullReportList}' >
                                <c:if test="${gradeReport.gradeItemType ne 'Exam'}">
                                    <tr>
                                        <td><span class="label label-default"><c:out value="${gradeReport.gradeItemType}"/></span></td>
                                        <td><c:out value="${gradeReport.gradeItemName}"/></td>
                                        <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.grade}"/></c:if></td>
                                        <td><c:out value="${gradeReport.minGrade}"/> - <c:out value="${gradeReport.maxGrade}"/></td>
                                        <td><c:if test="${gradeReport.grade ne 0.0}"><c:out value="${gradeReport.percentage}"/>%</c:if></td>
                                        <td><c:out value="${gradeReport.letter}"/></td>
                                    </tr>
                                </c:if>
                            </c:forEach>

                            </tbody>

                            <tfoot>
                            <tr>
                                <th colspan="6" ><spring:message code="gradeReport.total" /> : ${courseTotal}</th>
                            </tr>
                            <tr>
                                <td colspan="6" class="text-center"> <span><spring:message code="gradeReport.OverviewStmt" /> - ${userName} </span></td>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                    <%--<--END OF TABLE-->--%>


                </div>



            </div>


        </div>




    </div>
    <%--/END OF MAIN CARD CONTAINER --%>


</div>
<!--Content Ends  -->

<script>
    $(document).ready(function() {
        $('#userReportTable').DataTable({
            paging: false,
            searching: false,
            ordering:  false,
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>

        });
    });

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
