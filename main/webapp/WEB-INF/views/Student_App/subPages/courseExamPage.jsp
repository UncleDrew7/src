<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 12/09/2017
  Time: 07:50
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
    <title><spring:message code="studentCourseExamPage.courseExam"/></title>
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/my_course.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/subPages/courseExamPage.css" rel="stylesheet">
</head>

<body>


<div>

    <!-- Tab panes -->
    <div class="content">
        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class=""><a href="${url}/student/my-courses" class="icon-home"><spring:message code="system.currentCourses"/></a></li>
                <%--<li><a href="#"></a></li>--%>
                <%--<li><a href="#">Second Level Interior Page</a></li>--%>
                <%--<li><a href="#">Third Level Interior Page</a></li>--%>
                <li class="last active"><a > <spring:message code="courseExamPage.text1"/></a></li>
            </ul>
        </div>

        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="row mainContainer">

                <!--Small cards-->
                <%-------------------------------%>

                <div class="row">

                    <div class="col-sm-4">
                        <div class="upcomingEventsContainer ">
                            <div>
                                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseId" /> : ${courseDetails.parentCourseId}</h4>
                                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.courseName" /> : <c:out value="${courseDetails.courseName}"/></h4>
                                <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers2.semester"/> : <c:out value="${courseDetails.semesterCode}"/></h4>
                                <h4 class="titleTextBoxHeaders"><spring:message code="enrollmentList.teacher" /> : <c:out value="${courseDetails.teacherName}"/></h4>
                            </div>
                            <h4 class="titleTextBoxHeaders"><spring:message code="studentCourseExamPage.totalEnrolledExams"/> : <c:out value="${courseDetails.totalEnrollments}"/></h4>

                            <h4 class="titleTextBoxHeaders"><spring:message code="studentCourseExamPage.courseCredits"/> : ${courseDetails.credits}</h4>
                            <h4 class="titleTextBoxHeaders"><spring:message code="courseForm.courseStatus"/> :
                                <c:choose>
                                    <c:when test="${ ''eq ''}">
                                        <span style="padding-left: 20px;padding-right:20px; " class="label label-default"><spring:message code="studentCourseExamPage.statusNotAvailable"/></span>
                                    </c:when>
                                    <c:when test="${ 59 >= 60}">
                                        <span style="padding-left: 20px;padding-right:20px; " class="label label-success"><spring:message code="clearanceExamAddGrades.passed"/></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="padding-left: 20px;padding-right:20px; " class="label label-danger"><spring:message code="clearanceExamAddGrades.failed"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </h4>
                            <br/><br/>
                            <!--TimeLine container-->
                            <div class="timelineWrapperContainer">


                            </div>
                            <!--TimeLine Container-->

                        </div>

                    </div>

                    <div class="col-sm-8 ">

                        <!--Coures list Begins-->
                        <ul class="list-group">
                            <h4><spring:message code="studentCourseExamPage.courseExams"/></h4>

                            <c:if test="${empty courseExamsList}">
                                <div style="margin-top: 20px" class="divider"></div>
                                <div style="text-align: center;">
                                    <i><spring:message code="studentCourseExamPage.noEnrolledExams"/></i>
                                </div>
                            </c:if>

                            <c:forEach var = "courseExams" items="${courseExamsList}">
                                <!--List Item-->
                                <li class="list-group-item listCard">
                                    <!-- course box content-->
                                    <div class="courseCard">
                                        <div class="row">
                                            <!--intro names -->

                                            <div class="col-sm-1">
                                                <div class="tagContainer">
                                                    <c:choose>
                                                        <c:when test="${courseExams.examType eq 'parentExam'}">
                                                            <span class="label label-default"><spring:message code="studentCourseExamPage.mainExam"/></span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-default"><spring:message code="studentCourseExamPage.intermediate"/></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>

                                            <div class="col-sm-8">
                                                <div class="examContent">
                                                    <div class="courseTextStyle" >
                                                        <h3 style="margin: 0" class="courseOthersText"><c:out value="${courseExams.examName}"/></h3>
                                                    </div>
                                                    <div class="courseTextStyle" >
                                                        <span class="courseOthersText"><spring:message code="studentCourseExamPage.examStartDate"/> :  <c:out value="${courseExams.examDate}"/></span>
                                                    </div>
                                                    <div class="courseTextStyle" >
                                                        <span class="courseOthersText"><spring:message code="enrollmentList.deadline"/> :  <c:out value="${courseExams.examEnrollmentDeadline}"/></span>
                                                    </div>

                                                </div>

                                            </div>


                                            <div class="col-sm-3 courseCardBox3" >

                                                <!--course buttons-->
                                                <div class="enrollmentBox">

                                                    <div class="unEnrollTextBox">
                                                        <a class="courseButton" href="${url}/student/grades/full-report/${courseId}"><spring:message code="extra.viewGrades"/></a>
                                                    </div>

                                                    <div style="margin-left: 20px;">
                                                       <c:if test="${courseExams.examType eq 'childExam'}">
                                                           <c:if test="${courseExams.grade eq 'pass'}">
                                                               <span style="padding-left: 20px;padding-right:20px; margin-left: 23px;" class="label label-success">${courseExams.grade}</span>
                                                           </c:if>
                                                           <c:if test="${courseExams.grade eq 'fail'}">
                                                               <span style="padding-left: 20px;padding-right:20px; margin-left: 23px;" class="label label-success">${courseExams.grade}</span>
                                                           </c:if>
                                                       </c:if>
                                                    </div>
                                                </div>
                                                <br/>


                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <!--List Item-->
                            </c:forEach>

                        </ul>

                    </div>
                    <!--Coures list Ends-->
                </div>


                    <div class="divider"></div>

                    <h3 class="avilableExamsTitle"><spring:message code="studentCourseExamPage.availableExamsForCourse"/></h3>
                    <%--<div class="divider"></div>--%>


                    <%--Available Exams--%>
                    <div class="avilableExamsContainer">
                        <c:if test="${ empty recommendedAndAvailableExams}">
                            <div style="text-align: center"><i><spring:message code="studentCourseExamPage.availableExamsForCourse"/></i></div>
                        </c:if>

                        <!--Coures list Begins-->
                        <ul class="courseList  list-group">


                            <%--<c:if test="${ empty searchContentList}">--%>
                            <%--<div style="text-align: center"><i>No Data Found !!!</i></div>--%>
                            <%--</c:if>--%>

                            <%------------------------%>

                            <!--List Item-->

                            <c:forEach var = "examRecomendations" items="${recommendedAndAvailableExams}">
                                <li class="list-group-item listCard">
                                    <!-- course box content-->
                                    <div class="courseCard">
                                        <div class="row">
                                            <!--intro names -->
                                            <div class="col-sm-8 ">
                                                <div class="media">
                                                    <div class="media-left">
                                                        <h4><span class="label label-default"><spring:message code="studentHome.exam"/></span></h4>
                                                    </div>
                                                    <div class="media-body">
                                                        <h5 class="media-heading"><c:out value="${examRecomendations.parentCourseId}"/></h5>
                                                        <div class="courseTextStyle" >
                                                            <span class="courseOthersText"><spring:message code="enrollmentList.ExamName"/>:</span>
                                                            <a class="courseOthersText" href="${url}/admin/view/user-profile?userId=${searchList.examName}/>">
                                                                <c:out value="${examRecomendations.examName}"/>
                                                            </a>
                                                        </div>
                                                        <div class="courseTextStyle" >
                                                            <span class="courseOthersText"><spring:message code="studentHome.examDate"/>:</span>
                                                            <c:out value="${examRecomendations.examDate}"/>
                                                        </div>

                                                    </div>
                                                </div>

                                            </div>

                                            <div class="col-sm-4 courseCardBox3" >
                                                <div class="courseTextStyle" >
                                                    <div>
                                                        <span class="courseDateTextBox courseOthersText"><span class="label label-danger"><spring:message code="studentIndex.deadline"/></span>&nbsp;:&nbsp;<c:out value="${examRecomendations.examEnrollmentDeadline}"/></span>
                                                    </div>
                                                    <!--course buttons-->
                                                    <div class="courseBtnBox">
                                                        <a class="courseButton" href="#" onclick="examEnrollmentRequest(${examRecomendations.parentExamId},${examRecomendations.childCourseId},'${examRecomendations.examName}','${examRecomendations.parentCourseName}','${"no description .. "}','${"no start date "}','${examRecomendations.examDate}','${examRecomendations.examEnrollmentDeadline}')"><spring:message code="studentPageCourseLables.enroll" /></a>
                                                    </div>
                                                    <!--course buttons-->
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                            <!--List Item-->

                        </ul>
                        <br/><br/><br/><br/>
                        <!--Coures list Ends-->
                    </div>
                    <%--Available Exams--%>

            </div>


        </div>


    </div>

</div>



<%--exam enrollment modal--%>
<div class="modal fade" id="examEnrollModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

            <div class="modal-header">
                <h4  class="modal-title courseOthersText"><span id="examNameE"></span><button type="button" class="close" data-dismiss="modal">&times;</button></h4>
            </div>
            <h5 class="modelHead"><spring:message code="extra.examEnrolment"/> </h5>
            <div class="modal-body">
                <div class="courseTextStyle boxSpace" >
                    <spring:message code="courseForm.courseName"/> :
                    <span id="courseE" class="courseOthersText"></span><br/><br/>
                    <%--<span class="courseOthersText" id="descripE"></span>--%>
                </div>
                <div class="courseTextStyle" >
                    <span class="label label-default"><spring:message code="studentHome.examDate"/></span> : <span class="courseOthersText" id="eDate"></span><div></div>
                    <span class="label label-danger"><spring:message code="enrollmentList.deadline"/></span> : <span class="courseOthersText" id="deadline"></span>
                </div>
            </div>
            <div class="modal-footer">
                <div class="btn-group btn-group-justified">
                    <a href="#"   id="makeExamRequest" onclick="makeExamEnrollmentRequest(this.value)" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModalExams" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                    <a href="#" id="reqE" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.cancel"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<%--CONFRIMATION MODAL EXAMS --%>

<script>
    function examEnrollmentRequest(examId, examName,courseName,courseDescr,examDate,enrolSrt,enrolEnd) {

        $('#examEnrollModal').modal('toggle');
     //   $('#reqE').val(courseId);
        $('#courseE').text(courseName);
        $('#descripE').text(courseDescr);
        $('#examNameE').text(examName);
        $('#eDate').text(enrolSrt);
        $('#deadline').text(enrolEnd);
        $('#makeExamRequest').val(examId);

    }

    function makeExamEnrollmentRequest(examId) {
        var r = confirm('<spring:message code="studentSearchCourseAndExam.msg8"/>');
        if(r == true){
            $.ajax({
            //    url:'${url}/student/exam/request-enrollment/'+$('#reqE').val()+'/'+examId,
                url:'${url}/student/exam/request-enrollment/'+examId,
                type:'post',
                success:function(data){
                    switch(data) {
                        case "410":
                            alert('<spring:message code="studentSearchCourseAndExam.msg1"/>');
                            break;
                        case "420":
                            alert('<spring:message code="studentSearchCourseAndExam.msg2"/>');
                            break;
                        case "430":
                            alert('<spring:message code="studentSearchCourseAndExam.msg3"/>');
                            break;
                        case "440":
                            alert('<spring:message code="studentSearchCourseAndExam.msg4"/>');
                            break;
                        case "210":
                            alert('<spring:message code="studentSearchCourseAndExam.msg5"/>');
                            break;
                        case "200":
                        {
                            alert('<spring:message code="studentSearchCourseAndExam.msg6"/>');
                            document.location.reload(true);
                            break;
                        }
                        default:
                            alert('<spring:message code="studentSearchCourseAndExam.msg7"/>');
                    }

                    $('#examEnrollModal').modal('toggle');
//                    $('#confirmationModalExams').modal('toggle');
//                    $('#suc').text("Exam Request Made");
//                    $(".alert-success").css("display", "block");

                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //do nothing
        }

    }
</script>


</body>

</html>