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
    <title><spring:message code="addClearanceExamForm.addClearanceExam" /></title>
    <link href="${url}/resources/app_admin_static/css/subPages/addUserForm.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/addCourseForm.css" rel="stylesheet">
    <link rel="stylesheet" href="${url}/resources/main/jquery/jquery-ui.css">
    <script src="${url}/resources/main/jquery/jquery-ui.min.js"></script>


</head>

<body>

<!--Content Begins  -->
<div class="content">
    <br/>

    <c:choose>
        <c:when test="${param.nv eq 'ce'}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/course-exam/${parentCourseId}" class="icon-home"><spring:message code="courseExamPage.text1"/></a></li>
                    <li class="last active"><a ><spring:message code="addClearanceExamForm.addClearanceExam" /></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-exams/exams" class="icon-home"> <spring:message code="manageExams.manageExams" /></a></li>
                    <li class="last active"><a ><spring:message code="addClearanceExamForm.addClearanceExam" /></a></li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>


    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">


        <!-- BIG CONTAINER -->
        <div class="col-sm-9 bigCardContainerParent \">



            <%--BIG CARD CONTAINER BOX --%>
            <div class="card bigCardContainer">

                <h1><spring:message code="addClearanceExamForm.createIntermidetExam" /> </h1>
                <div class="formTitleBox">
                    <h4><spring:message code="addClearanceExamForm.parentExamName" /> ${parentExamData.examName}</h4>
                    <h4><spring:message code="addClearanceExamForm.courseName" /> ${parentExamData.parentCourseShortName}</h4>
                </div>


                <form id="examForm" class="form-horizontal" >

                    <h3><spring:message code="addClearanceExamForm.examDetails" /> </h3>

                    <div class="formBox">
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examName"><spring:message code="enrollmentList.ExamName" /></label>
                            <div class="col-sm-9">
                                <input type="text"  autocomplete="off" class="form-control" id="examName" placeholder='<spring:message code="addClearanceExamForm.enterExamName" />' name="examName" required>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="control-label col-sm-3" for="examDate"><spring:message code="manageExams2.dateOfExam" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="examDate" placeholder='<spring:message code="addClearanceExamForm.enterExamStartDate" />' name="examDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                        <label class="control-label col-sm-3" for="gradeRange"><spring:message code="addClearanceExamForm.GradeRange" /></label>
                        <div class="col-sm-9">
                            <input readonly autocomplete="off" type="text" class="form-control " id="gradeRange" value="F | P" name="maxGradeRange">
                        </div>
                    </div>
                <%--  <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentStartDate"><spring:message code="addClearanceExamForm.enrolmentStartDate" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="enrolmentStartDate" placeholder='<spring:message code="addClearanceExamForm.enterExamEnrolmentStartDate" />' name="enrolmentStartDate" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="enrolmentDeadLine"><spring:message code="examForm.enrolmentEndDate" /></label>
                            <div class="col-sm-9">
                                <input autocomplete="off" type="text" class="form-control datepicker" id="enrolmentDeadLine" placeholder='<spring:message code="addClearanceExamForm.enterExamEnrolmentDeadline" />' name="enrolmentDeadLine" required>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="control-label col-sm-3" for="minGradeRange"><spring:message code="addClearanceExamForm.minGradeRange" /></label>
                            <div class="col-sm-9">
                                <input readonly autocomplete="off" type="text" class="form-control" id="minGradeRange" value="48" name="minGradeRange">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="maxGradeRange"><spring:message code="addClearanceExamForm.maxGradeRange" /></label>
                            <div class="col-sm-9">
                                <input readonly autocomplete="off" type="text" class="form-control " id="maxGradeRange" value="60" name="maxGradeRange">
                            </div>
                        </div>      --%>




                    </div>
                    <%--FORM BOX1--%>
                    <%--FORM DIVIDER--%>
                    <div class="formDivider"></div>
                    <%--FORM DIVIDER--%>


                    <%--FORM HEADER--%>

                    <div class="form-group">
                        <div class="submitBtnBox">
                            <button type="submit" class="btn btn-default"><spring:message code="examForm.createExam" /></button>
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

                <h1><spring:message code="examForm.lastAddedExams" /></h1>


                <div class="table-responsive">
                    <table class="table table-condensed">
                        <c:forEach var = "aList" items="${lastAddedList}">
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
            url:'${url}/admin/manage-exams/exam/process/add-clearance-exam/${parentExamId}',
            type:'post',
            data:$(this).serialize(),
            success:function(data){
                if(data === '200'){
                    var r = confirm('<spring:message code="addClearanceExamForm.examMsgSucc" />');
                    if (r == true) {
                        location.reload(true);
                    } else{
                        window.location.href = '${url}/admin/manage-courses/course-exam/${parentCourseId}';
                    }
                }else{
                    alert('<spring:message code="addClearanceExamForm.errMsg1" />');
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

