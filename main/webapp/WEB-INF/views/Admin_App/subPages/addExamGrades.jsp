<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 26/09/2017
  Time: 23:40
  To change this template use File | Settings | File Templates.
  afetr grade items
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
    <title><spring:message code="examAddGrades.title"/> </title>
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>



<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
    <ul class="breadcrumbs">
        <li class="first"><a  class="icon-home">${examDetail.parentCourseName}/${examDetail.parentCourseShortName}</a></li>
        <li class="last active"><a ><spring:message code="examAddGrades.title"/></a></li>
    </ul>
</div>

    <%--<h2 class="pageTitle">Manage Courses</h2>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <%--<div class="searchingBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a href="${url}/admin/exam/grade/addExamGrades/${examId}" type="button" class="btn btn-default"><spring:message code="courseStudentGrdeOverview.addEditGrades"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                </div>
            </div>
        </div>--%>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">

                <%---------------------------------------------------------------------------------------------------------------------%>
                <div id="gradeReport" class="tab-pane fade fade in active" >
                    <h3 class="courseTitle"><spring:message code="courseStudentGrdeOverview.gradeOverviewFor"/> ${examDetail.examName}</h3>
                    <div class="divider"></div>
                    <%--!TABLE--%>
                    <div class="table-responsive csgoContaier">
                        <table  id="gradeTable" class="table table-bordered table-hover">
                            <%--<table  cellspacing="0" width="100%" id="gradeTable" class="table table-bordered table-hover">--%>

                            <%--<caption class="">--%
                            <%--</caption>--%>
                            <thead >
                            <tr>
                                <th ><spring:message code="enrollmentList.studentId"/></th>
                                <th ><spring:message code="enrollmentList.studentName"/></th>
                                <th ><spring:message code="enrollmentList.ExamGrade"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:if test="${empty examGrades}">
                                <tr >
                                    <td colspan="100">
                                        <div style="text-align: center">
                                            <i><spring:message code="addGrades.noDataAvailable" /></i>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <c:forEach var="studentList" items="${examGrades}">
                                <tr>
                                    <td><c:out value="${studentList.studentId}"/></td>
                                    <td><a class="usrText"><c:out value="${studentList.studentName}"/></a></td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${studentList.examScore eq 0}">
                                                <input type="text" autocomplete="false" class="form-control" id="${studentList.studentId}" onkeydown="changeEnter()" value="" >
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text" autocomplete="false" class="form-control" id="${studentList.studentId}" onkeydown="changeEnter()" value="${studentList.examScore}">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <%--<td><c:out value="${studentList.courseGradeAverage}"/></td>--%>
                                </tr>
                            </c:forEach>
                            </tbody>

                            <tfoot>
                            <%--<tr>--%>
                                <%--<td>Course Average : </td>--%>
                            <%--</tr>--%>
                            <tr>
                                <th colspan="5" class="text-center"> <button <c:if test="${ empty examGrades}">disabled</c:if>  class=" btn courseButton saveBtn" id="out"  href="#"><spring:message code="extra.saveChanges"/>  </button></th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>

                </div>
                <%----------------------------------------------------------------%>




            </div>




        </div>
        <%--/END OF MAIN CARD CONTAINER --%>

        <div class="finalGradeBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a  href="${url}/admin/exam/grade/updateExamGradesToCourse/${examId}" type="button" class="btn btn-default" id="subC" disabled ><spring:message code="courseStudentGrdeOverview.subCourseGrades"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                </div>
            </div>
        </div>


    </div>
    <!--Content Ends  -->
</div>
<script>

    function dataRow(value1,value2,value3) {
        this.name = value2;
        this.studentId = value1;
//        this.class = value3;
        this.grade = value3
    }

    <%--$('#subC').click(function(){--%>

        <%--var subTableData = new Array();--%>

        <%--$('table tr').each(function(row, tr){--%>
            <%--subTableData[row]={--%>
                <%--"studentId" : $(tr).find('td:eq(0)').text()--%>
                <%--, "studentName" :$(tr).find('td:eq(1)').text()--%>
<%--//                , "studentClass" : $(tr).find('td:eq(2)').text()--%>
                <%--, "examScore" : $(tr).find('input').val()--%>
            <%--}--%>
        <%--});--%>
        <%--subTableData.shift();  // first row is the table header - so remove--%>
        <%--var TableDatas = JSON.stringify(subTableData);--%>
        <%--console.log(subTableData);--%>
        <%--console.log((TableDatas));--%>

        <%--$.ajax({--%>
            <%--headers: {--%>
                <%--'Accept': 'application/json',--%>
                <%--'Content-Type': 'application/json'--%>
            <%--},--%>
            <%--url:'${url}/admin/exam/grade/updateExamGradesToCourse/${examId}',--%>
            <%--type:'post',--%>
            <%--contentType : 'application/json',--%>
            <%--dataType : 'json',--%>
            <%--data:TableDatas,--%>
            <%--success:function(data){--%>
                <%--alert(data.msg);--%>
            <%--},--%>
            <%--error : function(){--%>
                <%--alert('<spring:message code="main.msgError" />');--%>
            <%--}--%>
        <%--});--%>
    <%--})--%>


    $('#out').click(function(){

        var TableData = new Array();

        $('table tr').each(function(row, tr){
            TableData[row]={
                "studentId" : $(tr).find('td:eq(0)').text()
                , "studentName" :$(tr).find('td:eq(1)').text()
//                , "studentClass" : $(tr).find('td:eq(2)').text()
                , "examScore" : $(tr).find('input').val()
            }
        });
        TableData.shift();  // first row is the table header - so remove
        var TableDataq = JSON.stringify(TableData);
        console.log(TableData);
        console.log((TableDataq));
        var test = [
            {
                "studentId": "100",
                "studentName": "nick",
                "studentClass": "Kotha",
                "grade": "89"
            },
            {
                "studentId": "100",
                "studentName": "job",
                "studentClass": "Kofs",
                "grade": "78"
            },
            {
                "studentId": "100",
                "studentName": "job"
            }
        ]

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url:'${url}/admin/exam/grade/updateExamGrades/${examId}',
            type:'post',
            contentType : 'application/json',
            dataType : 'json',
            data:TableDataq,
            success:function(data){
                alert(data.msg);
                window.location.href= "${url}/admin/exam/grade/overview/${examId}"

            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    })

    function changeEnter(){
        if(event.keyCode==13){event.keyCode=9;}
    }

    <%--$(document).ready(function() {--%>
        <%--$('#gradeTable').DataTable({--%>
            <%--"scrollX": true,--%>
            <%--scrollY: 600,--%>
            <%--scrollCollapse: true,--%>
            <%--paging:         false--%>
        <%--} );--%>

    <%--} );--%>

    <%--function userReport(itemId) {--%>
        <%--window.location.href='${url}/admin/grades/student-grade-overview/'+itemId;--%>
    <%--}--%>
</script>
</body>

</html>
