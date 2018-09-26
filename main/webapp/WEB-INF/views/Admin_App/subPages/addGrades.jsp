<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 07/09/2017
  Time: 01:12
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
    <title><spring:message code="addGrades.addGrade" /> </title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addGrades.css" rel="stylesheet">
</head>

<body>
<%--TEST DATASOURCE --%>



<!--Content Begins  -->
<div class="content">

    <c:choose >
        <c:when test="${param.ng eq 1}">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/admin/dashboard" class="icon-home"><spring:message code="adminNavLable.dashboard" /></a></li>
                <li><a href="${url}/admin/manage-courses/enrolment-list/${courseId}?ng=${param.ng}"><spring:message code="enrollmentList.title" /></a></li>
                <li class="last active"><a ><spring:message code="enrollStudent.title" /></a></li>
            </ul>
        </div>
        </c:when>

        <c:when test="${param.nv eq 'ce'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/course-exam/${courseId}" class="icon-home"><spring:message code="courseExamPage.text1"/></a></li>
                    <li class="last active"><a ><spring:message code="courseExams.editExam"/></a></li>
                </ul>
            </div>
        </c:when>

        <c:otherwise>
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first">
                    <div class="dropdown">
                        <div class="mainLk">
                            <a href="${url}/admin/grades/grade-items/${courseId}" class="icon-home mainLink"><spring:message code="addGrades.gradeItems" /></a>
                        </div>
                        <div class="dropdown-content">
                            <a href="${url}/admin/course/grade/overview/${courseId}"><spring:message code="studentGradesOverviewReport.gradeOverView" /></a>
                            <a href="${url}/admin/view/course/${courseId}"><spring:message code="editCourseForm.coursePage" /></a>
                            <a href="${url}/admin/course/content/add/${courseId}"><spring:message code="coursePage.addTopic" /></a>
                            <a href="${url}/admin/manage-courses/enrolment-list/${courseId}/3"><spring:message code="enrollmentList.enrolmentList" /></a>
                            <a href="${url}/admin/manage-courses/enroll-student/${courseId}/2"><spring:message code="enrollStudent.title" /></a>
                        </div>
                    </div>
                </li>
                <%--<li class="first"><a href="${url}/admin/grades/grade-items/${courseId}" class="icon-home"><spring:message code="addGrades.gradeItems" /></a></li>--%>
                <li class="last active"><a href="#"><spring:message code="addGrades.addGrade" /> </a></li>
            </ul>
        </div>
        </c:otherwise>
    </c:choose>


    <!--MAIN CARD CONTAINER -->
    <div class=" card mainCardContainer">
        <!--SEARCH CONTAINER -->
        <div class="searchingBox">
            <div class="form-group pull-right">
                <div class="btn-group" role="group" aria-label="...">
                        <a href="${url}/admin/download/${courseId}/${gradeItemId}/${gradeItemTypeId}/course-student-add-grade-list-excel" type="button" class="btn btn-default"><spring:message code="addGrades.btn1" /> <span class="glyphicon glyphicon-download" aria-hidden="true"></span></a>
                   <%--  <c:if test="${hasPermission or currentUserRole eq 'admin'}">
                        <button data-toggle="modal" data-target="#importGradeModal" type="button" class="btn btn-default"><spring:message code="addGrades.btn2" /> <span class="glyphicon glyphicon-upload" aria-hidden="true"></span></button>
                    </c:if>--%>
                </div>
            </div>


        </div>
        <%--/SEARCH CONTAINER--%>




        <!--MAIN TAB CONTAINER -->
        <div class="mainTabContainer">




            <div class="tab-content">
                <div id="gradeItem" class="tab-pane fade in active">
                    <h3><spring:message code="addGrades.addGradesFor" />  ${courseName.courseName}</h3>
                    <div class="divider"></div>
                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="gradeItemTable" class="table table-bordered table-hover">

                            <thead>
                            <tr>
                                <th><spring:message code="enrollmentList.studentId" /></th>
                                <th><spring:message code="enrollmentList.studentName" /></th>
                                <th><spring:message code="enrollmentList.class" /></th>
                                <th><spring:message code="gradeReport.grade" /></th>
                            </tr>
                            </thead>

                            <tbody>


                            <c:if test="${empty studentGradeList}">
                                <tr >
                                    <td colspan="100">
                                        <div style="text-align: center">
                                            <i><spring:message code="addGrades.noDataAvailable" /></i>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                            <c:forEach var="stgList" items='${studentGradeList}' >
                                <tr>
                                    <td><c:out value="${stgList.studentId}"/></td>
                                    <td><a href="${url}/admin/view/course"><c:out value="${stgList.studentName}"/></a></td>
                                    <td><c:out value="${stgList.studentClass}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${stgList.grade eq 0}">
                                                <input type="text"  autocomplete="false" class="form-control" id="weight" value="">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="text"  autocomplete="false" class="form-control" id="weight" value="${stgList.grade}">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>

                            <tfoot>
                            <%--<tr>--%>
                                <%--<th colspan="5" >Grade Average: ${weightTotal.rowsByIndex[0][0]}</th>--%>
                            <%--</tr>--%>
                            <tr>
                                <th colspan="5" class="text-center"> <button <c:if test="${!hasPermission and currentUserRole ne 'admin'|| empty studentGradeList}">disabled</c:if>  class=" btn courseButton saveBtn" id="out"  href="#"><spring:message code="extra.saveChanges"/>  </button></th>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                    <%--<--END OF TABLE-->--%>
                </div>
                <%--<input type="button" value = "Submit" id="out">--%>



        </div>
        <%--/END OF MAIN CARD CONTAINER --%>

    </div>
    <!--Content Ends  -->
    </div>
</div>

<%--ENROLLMENT MODAL --%>
<div class="modal fade" id="importGradeModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times</button>
                <h3 style="text-align: center" class="modal-title"><spring:message code="addGrades.importStudentGradeList" /></h3>
            </div>
            <!--Body (form)-->
            <div class="modal-body">

                <%--FILE FORM --%>
                <form:form action="${url}/admin/grades/import-grades/${courseId}/${gradeItemId}/${gradeItemTypeId}" method="post"
                           enctype="multipart/form-data">
                    <input name="studentGradesFile" type="file">
                    <%--<input type="submit" value="Import Excel File">--%>
                    <!--Button-->
                    <div class="modal-footer">
                        <button  type="submit" class="btn btn-primary btn-block"><spring:message code="main.importExcelFile"/>  </button>
                    </div>
                </form:form>
                <%--FILE FORM --%>

            </div>

        </div>
    </div>
</div>
<%--ENROLL MENT MODAL--%>

<script>

    // object to hold your data
    function dataRow(value1,value2,value3,value4) {
        this.name = value1;
        this.studentId = value2;
        this.class = value3;
        this.grade = value4
    }

    $('#out').click(function(){

        var TableData = new Array();

        $('table tr').each(function(row, tr){
            TableData[row]={
                "studentId" : $(tr).find('td:eq(0)').text()
                , "studentName" :$(tr).find('td:eq(1)').text()
                , "studentClass" : $(tr).find('td:eq(2)').text()
                , "grade" : $(tr).find('input').val()
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
            url:'${url}/admin/grades/add-grades/${courseId}/${gradeItemId}',
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

<%--SELECT  usr.user_name,ee.student_id,c.class_name--%>
<%--FROM USER usr , exam_enrolment ee , grade_items gi, class c , student_class sc--%>
<%--WHERE ee.student_id = usr.user_id AND gi.grade_item_id = ee.exam_id AND--%>
<%--c.class_id = sc.class_id AND ee.student_id = sc.student_id AND ee.exam_id = 9--%>
<%--ORDER BY usr.user_name--%>


<%--SELECT  usr.user_name,ee.student_id,c.class_name--%>
<%--FROM USER usr , exam_enrolment ee , grade_items gi, class c , student_class sc--%>
<%--WHERE ee.student_id = usr.user_id AND gi.grade_item_id = ee.exam_id AND--%>
<%--c.class_id = sc.class_id AND ee.student_id = sc.student_id AND ee.exam_id = 9--%>
<%--ORDER BY c.class_name--%>

<%--SELECT  usr.user_name,ee.student_id,c.class_name--%>
<%--FROM USER usr , exam_enrolment ee , grade_items gi, class c , student_class sc--%>
<%--WHERE ee.student_id = usr.user_id AND gi.grade_item_id = ee.exam_id AND--%>
<%--c.class_id = sc.class_id AND ee.student_id = sc.student_id AND ee.exam_id = 9--%>
<%--ORDER BY ee.student_id--%>