  <%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/01/2018
  Time: 22:07
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
    <title> <spring:message code="clearanceExamAddGrades.text1"/> </title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addGrades.css" rel="stylesheet">
</head>

<body>
<%--TEST DATASOURCE --%>



<!--Content Begins  -->
<div class="content">

    <%--<c:choose >--%>
        <%--<c:when test="${param.ng eq 1}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><spring:message code="adminNavLable.dashboard" /></a></li>
                    <li><a href="${url}/admin/manage-courses/enrolment-list/${courseId}?ng=${param.ng}"><spring:message code="enrollmentList.title" /></a></li>
                    <li class="last active"><a ><spring:message code="enrollStudent.title" /></a></li>
                </ul>
            </div>
        </c:when>--%>

        <%--<c:otherwise>--%>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <%--<li class="first"><a href="${url}/admin/grades/grade-items/${courseId}" class="icon-home"><spring:message code="addGrades.gradeItems"/></a></li>--%>
                    <li class="first"><a  class="icon-home"><spring:message code="clearanceExamAddGrades.text0"/></a></li>
                        <%--<li><a href="#"></a></li>--%>
                        <%--<li><a href="#">Second Level Interior Page</a></li>--%>
                        <%--<li><a href="#">Third Level Interior Page</a></li>--%>
                    <li class="last active"><a href="#"><spring:message code="addGrades.addGrade"/> </a></li>
                </ul>
            </div>
        <%--</c:otherwise>--%>
    <%--</c:choose>--%>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">
        <!--SEARCH CONTAINER -->
       <%-- <div class="searchingBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                    <a href="${url}/admin/download/${courseId}/${gradeItemId}/${gradeItemTypeId}/course-student-add-grade-list-excel" type="button" class="btn btn-default"><spring:message code="clearanceExamAddGrades.text2"/> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                    <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                        <button data-toggle="modal" data-target="#importGradeModal" type="button" class="btn btn-default"><spring:message code="clearanceExamAddGrades.text3"/> <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>
                    </c:if>
                </div>
            </div>


        </div>--%>
        <%--/SEARCH CONTAINER--%>




        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">
                <div id="gradeItem" class="tab-pane fade in active">
                    <h3><spring:message code="addGrades.addGradesFor" /> ${cleExam.examName}</h3>
                    <div class="divider"></div>
                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="gradeItemTable" class="table table-bordered table-hover">

                            <thead>
                            <tr>
                                <%--<th>#</th>--%>
                                <th><spring:message code="enrollmentList.studentId"/></th>
                                <th><spring:message code="enrollmentList.studentName"/></th>
                                <th><spring:message code="enrollmentList.class"/></th>
                                <th><spring:message code="gradeReport.grade"/></th>
                            </tr>
                            </thead>

                            <tbody>


                            <c:if test="${empty gradeList}">
                                <tr >
                                    <td colspan="100">
                                        <div style="text-align: center">
                                            <i><spring:message code="addGrades.noDataAvailable"/></i>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <%--<c:forEach var="stgList" items='${gradeList}' varStatus="index">
                                <tr>
                                        &lt;%&ndash;<td>${index.count}</td>&ndash;%&gt;
                                    <td><c:out value="${stgList.studentId}"/></td>
                                    <td><a href="${url}/admin/view/course"><c:out value="${stgList.studentName}"/></a></td>
                                    <td><c:out value="${stgList.className}"/></td>
                                    <td>

                                                <c:choose>
                                                    <c:when test="${stgList.clexamGrade eq 0}">
                                                        <input type="text"  autocomplete="false" class="form-control" id="weight" value="">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="text"  autocomplete="false" class="form-control" id="weight" value="${stgList.clexamGrade}">
                                                    </c:otherwise>
                                                </c:choose>

                                    </td>
                                </tr>
                            </c:forEach>--%>
                            <c:forEach var="stgList" items='${gradeList}' varStatus="index">
                                <tr>
                                    <%--<td>${index.count}</td>--%>
                                    <td><c:out value="${stgList.studentId}"/></td>
                                    <td><a href="${url}/admin/view/course"><c:out value="${stgList.studentName}"/></a></td>
                                    <td><c:out value="${stgList.className}"/></td>
                                    <td>
                                        <%--<input type="text"  autocomplete="false" class="form-control" id="weight" value="${stgList.clearanceGrade}">--%>

                                           <%-- <c:if test="${stgList.clearanceGrade == 1 }">
                                                <label class="radio-inline active"><input id="a1" type="radio" checked="checked" name="optradio"><spring:message code="clearanceExamAddGrades.passed"/></label>
                                                <label class="radio-inline"><input id="a2" type="radio" value="d" name="optradio"><spring:message code="clearanceExamAddGrades.failed"/></label>
                                            </c:if>--%>

                                        <c:if test="${stgList.clexamGrade == 'Passed' }">
                                            <label class="radio-inline active"><input id="a1" type="radio" checked="checked" name="radio${stgList.studentId}" value="Passed"><spring:message code="clearanceExamAddGrades.passed"/></label>
                                            <label class="radio-inline"><input id="a2" type="radio" name="radio${stgList.studentId}" value="Failed"><spring:message code="clearanceExamAddGrades.failed"/></label>
                                        </c:if>
                                            <c:if test="${stgList.clexamGrade eq 'Failed' }">
                                                <label class="radio-inline"><input type="radio"  name="radio${stgList.studentId}" value="Passed"><spring:message code="clearanceExamAddGrades.passed"/></label>
                                                <label class="radio-inline active"><input type="radio" checked  name="radio${stgList.studentId}" value="Failed"><spring:message code="clearanceExamAddGrades.failed"/></label>
                                            </c:if>
                                            <c:if test="${stgList.clexamGrade ne 'Passed' && stgList.clexamGrade ne 'Failed' }">
                                                <label class="radio-inline"><input type="radio"  name="radio${stgList.studentId}" value="Passed"><spring:message code="clearanceExamAddGrades.passed"/></label>
                                                <label class="radio-inline"><input type="radio"  checked name="radio${stgList.studentId}" value="Failed"><spring:message code="clearanceExamAddGrades.failed"/></label>
                                                <%--<label style="display: none" class="radio-inline active"><input style="display: none" type="radio" value="" checked name="radio${stgList.studentId}"><spring:message code="clearanceExamAddGrades.none"/></label>--%>
                                            </c:if>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>

                            <tfoot>
                            <%--<tr>--%>
                            <%--<th colspan="5" >Grade Average: ${weightTotal.rowsByIndex[0][0]}</th>--%>
                            <%--</tr>--%>
                            <tr>
                                <th colspan="5" class="text-center"> <button
                                        <%--<c:if test="${!hasPermission and currentUserRole ne 'admin'|| empty studentGradeList}">disabled</c:if> --%>
                                          class=" btn cleButton saveBtn" id="outcle"  href="#"><spring:message code="extra.saveChanges"/> </button></th>


                            </tr>
                            </tfoot>
                        </table>

                    </div>
                    <%--<--END OF TABLE-->--%>
                </div>
                <%--<input type="button" value = "Submit" id="out">--%>

                <div class="finalGradeBox">
                    <div class="form-group pull-right">
                        <div class="btn-group" role="group" aria-label="...">
                            <a  type="button" class="btn btn-default" id="subCleExam"  href="#"><spring:message code="courseStudentGrdeOverview.subCourseGrades"/> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
                        </div>
                    </div>
                </div>


            </div>
            <%--/END OF MAIN CARD CONTAINER --%>

        </div>
        <!--Content Ends  -->
    </div>
</div>

<%--ENROLLMENT MODAL --%>
<div class="modal fade" id="importGradeModal">
    <%--<div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times</button>
                <h3 style="text-align: center" class="modal-title"><spring:message code="clearanceExamAddGrades.text4"/></h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                &lt;%&ndash;FILE FORM &ndash;%&gt;
                <form:form action="${url}/admin/grades/import-grades/${courseId}/${gradeItemId}/${gradeItemTypeId}" method="post"
                           enctype="multipart/form-data">
                    <input name="studentGradesFile" type="file">
                    &lt;%&ndash;<input type="submit" value="Import Excel File">&ndash;%&gt;
                    <!--Button-->
                    <div class="modal-footer">
                        <button  type="submit" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile"/> </button>
                    </div>
                </form:form>
                &lt;%&ndash;FILE FORM &ndash;%&gt;

            </div>

        </div>
    </div>--%>
</div>
<%--ENROLL MENT MODAL--%>

<script>

   /*  $(document).ready(function(){


     });*/

    // object to hold your data
    function dataRow(value1,value2,value3,value4) {
        this.studentId = value1;
        this.name = value2;
        this.class = value3;
        this.grade = value4
    }

    $('#subCleExam').click(function () {
        var cleTableData = new Array();

        $('table tr').each(function(row, tr){
            cleTableData[row]={
                "studentId" : $(tr).find('td:eq(0)').text()
                , "studentName" :$(tr).find('td:eq(1)').text()
                , "className" : $(tr).find('td:eq(2)').text()
                , "clexamGrade" : $(tr).find("input[type='radio']:checked").val()
            }
        });

        cleTableData.shift();  // first row is the table header - so remove
        var cleTableDataq = JSON.stringify(cleTableData);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url:'${url}/admin/exam/grade/updateExamGrades260/${cleExam.clearanceExamId}',
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

    })


    $('#outcle').click(function(){

        var TableData = new Array();

        $('table tr').each(function(row, tr){
            TableData[row]={
                "studentId" : $(tr).find('td:eq(0)').text()
                , "studentName" :$(tr).find('td:eq(1)').text()
                , "className" : $(tr).find('td:eq(2)').text()
                , "clexamGrade" : $(tr).find("input[type='radio']:checked").val()
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
            url:'${url}/admin/exam/grade/updateCleExamGrades/${cleExam.clearanceExamId}',
            type:'post',
            contentType : 'application/json',
            dataType : 'json',
            data:TableDataq,
            success:function(data){
                alert(data.msg);
            },
            error : function(){
                alert('<spring:message code="main.msgError" />');
            }
        });
    })


</script>

</body>

</html>

