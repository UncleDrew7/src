<%--
  Created by IntelliJ IDEA.
  User: Lani
  Date: 1/24/2018
  Time: 11:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 08/01/2018
  Time: 03:37
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
    <title><spring:message code="editClearanceExamForm.text1"/></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>


</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class="first"><a href="${url}/admin/manage-exams/clexams" class="icon-home"><spring:message code="editClearanceExamForm.manageChildExam"/></a></li>
            <li class="last active"><a ><spring:message code="editClearanceExamForm.editClExam"/></a></li>
        </ul>
    </div>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">



            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">


                <div class="formTitleBox">
                    <h1><spring:message code="editClearanceExamForm.text2"/> </h1>
                </div>


                <form id="examForm" class="form-horizontal" >

                    <h3><spring:message code="addExamForm.examDetails"/></h3>

                    <div class="formBox">
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="parentCouresId"><spring:message code="enrollmentList.courseId"/></label>
                            <div class="col-sm-9">
                                <input type="text"  value="${clExamData.parentCourseId}" autocomplete="off" class="form-control" id="parentCouresId" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="parentCouresName"><spring:message code="courseForm.courseName"/></label>
                            <div class="col-sm-9">
                                <input type="text"  value="${clExamData.parentCourseName}" autocomplete="off" class="form-control" id="parentCouresName" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="parentExamName"><spring:message code="editClearanceExamForm.parentExam"/></label>
                            <div class="col-sm-9">
                                <input type="text"  value="${clExamData.parentExamName}" autocomplete="off" class="form-control" id="parentExamName" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examName"><spring:message code="manageExams2.childExamName"/></label>
                            <div class="col-sm-9">
                                <input type="text"  value="${clExamData.examName}" autocomplete="off" class="form-control" id="examName" placeholder="Enter Exam Exam" name="examName" required>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examDate"><spring:message code="manageExams2.dateOfExam"/></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" value="${clExamData.examDate}" type="text" class="form-control datepicker" id="examDate" placeholder="Enter Exam Start Date" name="examDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentStartDate"><spring:message code="examForm.enrolmentStartDate"/></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" value="${clExamData.enrolmentStartDate}" class="form-control datepicker" id="enrolmentStartDate" placeholder="Enter Exam Enrolment Start Date" name="enrolmentStartDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentDeadLine"><spring:message code="examForm.enrolmentEndDate"/></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" value="${clExamData.enrolmentEndDate}" class="form-control datepicker" id="enrolmentDeadLine" placeholder="Enter Exam Enrolment Deadline" name="enrolmentDeadLine" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="minGradeRange"><spring:message code="addClearanceExamForm.minGradeRange"/></label>
                            <div class="col-sm-9">
                                <input  autocomplete="off" value="${clExamData.enrolmentGradeMin}" type="text" class="form-control" id="minGradeRange"  name="minGradeRange">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="maxGradeRange"><spring:message code="addClearanceExamForm.maxGradeRange"/></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" value="${clExamData.enrolmentGradeMax}" type="text" class="form-control " id="maxGradeRange"  name="maxGradeRange">
                            </div>
                        </div>




                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button type="submit" class="btn btn-default"><spring:message code="extra.saveChanges"/></button>
                        </div>
                    </div>

                </form>
                <%--/FORM--%>


            </div>
            <%--BIG CARD CONTAINER BOX--%>




        </div>
        <!--BIG CONTAINER -->






        <!--Small cards-->
        <div class="col-sm-3 smallCardContainerParent">


            <div class="card bigCardContainer ">

                <h4 style="text-align: center"><spring:message code="editClearanceExamForm.lastEditedExams"/></h4>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "aList" items="${lastEditedClearanceExams}">
                            <tr>
                                <td ><c:out value="${aList.clearanceExamId}"/></td>
                                <td ><c:out value="${aList.examName}"/></td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>

            </div>


        </div>
        <!--Small cards-->







    </div>
    <!--MAIN CARD CONTAINER -->




</div>
<!--Content Ends  -->
<script>

    $( ".datepicker" ).datepicker({
        dateFormat: 'yy-mm-dd'
    });

    $('#examForm').submit(function(e){
        e.preventDefault();
        $.ajax({
            url:'${url}/admin/manage-exams/exam/process/add-clearance-exam/${childCourseId}/${parentExamId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    var r = confirm('<spring:message code="editClearanceExamForm.msg1"/>');
                    if (r == true) {
                        location.reload(true);
                    } else{
                        window.location.href = '${url}/admin/manage-courses/course-exam/${childCourseId}';
                    }
                }else{
                    alert('<spring:message code="editClearanceExamForm.msg2"/>');
                }
                alert(data);
                location.reload(true);
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    });




</script>

</body>

</html>

