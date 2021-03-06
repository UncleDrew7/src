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
    <title><spring:message code="clearanceExamViewGrades.text1"/> </title>
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
        <li class="first"><a  class="icon-home">${clExamData.parentCourseName}/${clExamData.examName}</a></li>
        <li class="last active"><a ><spring:message code="clearanceExamViewGrades.viewGrade"/></a></li>
    </ul>
</div>

    <%--<h2 class="pageTitle">Manage Courses</h2>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">

        <!--SEARCH CONTAINER -->
        <div class="searchingBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a href="${url}/admin/mange-exams/clearance-exam/add/grades/${clearanceExamId}" type="button" class="btn btn-default"><spring:message code="courseStudentGrdeOverview.addEditGrades"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                </div>
            </div>
        </div>
        <%--/SEARCH CONTAINER--%>


        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">

                <%---------------------------------------------------------------------------------------------------------------------%>
                <div id="gradeReport" class="tab-pane fade fade in active" >
                    <h3 class="courseTitle"><spring:message code="courseStudentGrdeOverview.gradeOverviewFor"/> ${clExamData.examName}</h3>
                    <div class="divider"></div>
                    <%--!TABLE--%>
                    <div class="table-responsive csgoContaier">
                        <table  cellspacing="0" width="100%" id="gradeTable" class="table table-bordered table-hover">

                            <%--<caption class="">--%
                            <%--</caption>--%>
                            <thead >
                            <tr>
                                <th ><spring:message code="enrollmentList.studentId"/></th>
                                <th ><spring:message code="enrollmentList.studentName"/></th>
                                <th ><spring:message code="enrollmentList.class"/></th>
                                <th ><spring:message code="enrollmentList.ExamGrade"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="studentList" items="${gradeList}">
                                <tr>
                                    <td><c:out value="${studentList.studentId}"/></td>
                                    <td><a onclick="userReport(${studentList.studentId})"class="usrText"><c:out value="${studentList.studentName}"/></a></td>
                                    <td><c:out value="${studentList.className}"/></td>
                                    <%--<td>
                                    <c:forEach var="gradeitems" items="${examGrades}">
                                                <c:if test="${gradeitems.studentId eq studentList.userId }">
                                                    <c:out value="${gradeitems.examScore}"/>
                                                </c:if>
                                    </c:forEach>
                                    </td>--%>

                                    <td>
                                         <c:out value="${studentList.clexamGrade}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>

                            <tfoot>
                            <%--<tr>--%>
                                <%--<td>Course Average : </td>--%>
                            <%--</tr>--%>
                            </tfoot>
                        </table>
                    </div>

                </div>
                <%----------------------------------------------------------------%>




            </div>




        </div>
        <%--/END OF MAIN CARD CONTAINER --%>

        <%--<div class="finalGradeBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a  type="button" class="btn btn-default" id="subCleExam"  href="#"><spring:message code="courseStudentGrdeOverview.subCourseGrades"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                </div>
            </div>
        </div>--%>


    </div>
    <!--Content Ends  -->
</div>
<script>

    $(document).ready(function() {
        $('#gradeTable').DataTable({
            "scrollX": true,
            scrollY: 600,
            scrollCollapse: true,
            paging:         false
        } );

    } );

  /*  $('#subCleExam').click(function () {
         var cleTableData = new Array();

        $('table tr').each(function(row, tr){
            cleTableData[row]={
                "studentId" : $(tr).find('td:eq(0)').text()
                , "studentName" :$(tr).find('td:eq(1)').text()
                , "className" : $(tr).find('td:eq(2)').text()
                , "clexamGrade" : $(tr).find('td:eq(3)').text()
            }
        });

        cleTableData.shift();  // first row is the table header - so remove
        var cleTableDataq = JSON.stringify(cleTableData);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url:'${url}/admin/exam/grade/updateExamGrades260/${clExamData.parentExamId}',
            type:'post',
            contentType : 'application/json',
            dataType : 'json',
            data:cleTableDataq,
            success:function(data){
                alert(data.msg);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });

    })*/

    function userReport(itemId) {
        window.location.href='${url}/admin/grades/student-grade-overview/'+itemId;
    }
</script>
</body>

</html>
