<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 23/10/2017
  Time: 12:34
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
    <title><spring:message code="classpage.text1"/></title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/courseEnrolmentList.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>

<%--navs--%>

<!-- Tab panes -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-users/classes" class="icon-home"><spring:message code="manageUsers.manageClasses" /></a></li>
            <li class="last active"><a ><spring:message code="extra.studentList" /></a></li>
        </ul>
    </div>


    <div class="row mainContainer">

        <%--<!--RIGHT FLOAT CONTAINER -->--%>
        <div style="padding-right: 7%" class="btnGroupBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a href="${url}/admin/download/${classId}/class-list-excel" type="button" class="btn btn-default"><spring:message code="manageUsers.downloadStudentExcel" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                    <c:if test="${currentUserRole eq 'admin'}">
                        <a href="${url}/admin/class/enroll/${classId}" type="button" class="btn btn-default"><spring:message code="extra.addStudent" /> <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
                    </c:if>
                </div>
            </div>
        </div>
        <%--/RIGHT FLOAT CONTAINER--%>



        <!--Small cards-->
        <div style="clear: right" class="col-sm-3">
            <div class="upcomingEventsContainer ">
                <div>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers.classId" />: <b>${aboutClass.id}</b></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers.className" />: <b><c:out value="${aboutClass.majorShortName} | ${aboutClass.className}"/></b></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers.intake" /> : <b><c:out value="${aboutClass.intakePeriod}"/></b></h4>
                </div>
                <h4 class="titleTextBoxHeaders"><spring:message code="extra.created" /> : <b><c:out value="${aboutClass.createdAt}"/></b></h4>
                <!--TimeLine container-->
                <div class="timelineWrapperContainer">


                </div>
                <!--TimeLine Container-->

            </div>


        </div>
        <!--Small cards-->
        <%----------------%>
        <div class="col-sm-9 ">
            <%--<h3 style="text-align: center">${aboutClass.className}</h3>--%>
            <!--MAIN CARD CONTAINER -->
            <div class=" card mainCardContainer">


                <!--MAIN TAB CONTAINER -->
                <div class="mainTabContainer">

                    <br/>
                    <h3><spring:message code="extra.studentList" /></h3>

                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="courseTable" class="table table-bordered table-hover">


                            <thead>
                            <tr>
                                <th>#</th>
                                <th><spring:message code="enrollmentList.studentId" /></th>
                                <th><spring:message code="enrollmentList.studentName" /></th>
                                <th><spring:message code="enrollmentList.enrolmentDate" /></th>
                                <th><spring:message code="enrollmentList.action" /></th>


                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var = "cList" items="${classList}" varStatus="index">
                                <tr>
                                    <td>${index.count}</td>
                                    <td><c:out value="${cList.userId}"/></td>
                                    <td><a href="${url}/admin/profile/view/${cList.userId}"><c:out value="${cList.userName}"/></a></td>
                                    <td><c:out value="${cList.enrolmentDate}"/></td>
                                    <td>
                                        <button <c:if test="${currentUserRole ne 'admin'}">disabled</c:if> onclick="unEnrollStudent('${cList.userId}')" class="btn btn-danger btn-xs" type="button" ><spring:message code="studentPageCourseLables.unEnroll" /></button>
                                        <%--<button class=" btn btn-danger btn-xs " value="${studentList.userId}"><spring:message code="studentPageCourseLables.unEnroll" /></button>--%>
                                    </td>
                                </tr>
                            </c:forEach>



                            </tbody>

                        </table>


                    </div>
                    <%--<--END OF TABLE-->--%>

                </div>




            </div>
            <%--/END OF MAIN CARD CONTAINER --%>


        </div>



    </div>


</div>

<script>

    <%--$('.unEnroll').click(function() {--%>
<%--//            alert("working");--%>
        <%--$.ajax({--%>
            <%--url: '${url}/admin/class/remove/${classId}/'+$(this).val(),--%>
            <%--type: 'POST',--%>
<%--//                data: {'userId':$(this).val(),'action':"remove"}, // An object with the key 'submit' and value 'true;--%>
            <%--success: function (result) {--%>
                <%--alert(result);--%>
                <%--document.location.reload(true);--%>
            <%--},--%>
            <%--error : function(data){--%>
                <%--alert('<spring:message code="main.msgError" />');--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>

    $(document).ready(function() {
        $('#courseTable').DataTable({
            <c:if test="${pageContext.response.locale eq 'cn'}">
            "language": translation
            </c:if>
        });
    } );


    function  unEnrollStudent(userId){
        var r = confirm('<spring:message code="classpage.text2"/>');
        if (r == true) {
            alert("remove" + userId +" from class " + ${aboutClass.id});
            $.ajax({
                url: '${url}/admin/class/remove/${aboutClass.id}/'+userId,
                type: 'POST',
                success: function (result) {
                    alert(result);
                    document.location.reload(true);
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        } else{
            //do nothing
            <%--window.location.href = '${url}/admin/dashboard';--%>
        }
    }


    <%--function enrollStudent(userId){--%>
        <%--var r = confirm('<spring:message code="classpage.text3"/>');--%>
        <%--if(r == true){--%>
            <%--alert(userId);--%>
        <%--}else{--%>
            <%--//do nothing--%>
        <%--}--%>
    <%--}--%>

    <%--function declineStudent(userId){--%>
        <%--var r = confirm(<spring:message code="classpage.text4"/>);--%>
        <%--if(r == true){--%>
            <%--alert(userId);--%>
        <%--}else{--%>
            <%--//do nothing--%>
        <%--}--%>
    <%--}--%>

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




<%--footer--%>

</body>

</html>