<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 10/09/2017
  Time: 21:23
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
    <title><spring:message code="manageUsers.search"/></title>
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/subPages/searchCourseAndExam.css" rel="stylesheet">
</head>

<body>


<div>

    <%--navs--%>

    <!-- Tab panes -->
    <div class="content">

        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/student/home" class="icon-home"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></li>
                <%--<li><a href="#"></a></li>--%>
                <%--<li><a href="#">Second Level Interior Page</a></li>--%>
                <%--<li><a href="#">Third Level Interior Page</a></li>--%>
                <li class="last active"><a href="#"><spring:message code="studentSearchCourseAndExam.searchExamsAndCourses"/> </a></li>
            </ul>
        </div>

        <div role="tabpanel" class="tab-pane active" id="home">

            <div class="row mainContainer">




                <!--Small cards-->
                <div class="col-sm-3">

                    <div class="card">
                        <h4 class="titleTextBoxHeaders"><spring:message code="studentSearchCourseAndExam.filters"/></h4>
                        <div style="margin-top: -11px" class="list-group">
                            <%--<a href="${url}/student/search/1?search=${search}" class="list-group-item <c:if test='${filter eq 1}'>active</c:if>">All<span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>--%>
                            <a href="${url}/student/search/courses?search=${search}" class="list-group-item <c:if test='${object eq "courses"}'>active</c:if>">Courses</a>
                            <a href="${url}/student/search/exams?search=${search}" class="list-group-item <c:if test='${object eq "exams"}'>active</c:if>">Exams</a>
                        </div>
                    </div>



                    <!-- <div class="card smallCardContainer ">
                             <h4 class="titleTextBoxHeaders">Calender</h4>
                     </div>-->

                    <div class="card smallCardContainer ">
                        <div>
                            <h4 class="titleTextBoxHeaders"><spring:message code="manageUsers2.semester"/></h4>
                        </div>
                        <div style="margin-top: -11px" class="list-group">
                            <a href="${url}/student/search/${object}?search=${search}" class="list-group-item <c:if test='${semester eq null}'>active</c:if>">
                                <spring:message code="extra.all"/>
                            </a>
                            <c:forEach var = "sList" items="${semesterList}">
                                <a href="${url}/student/search/${object}?search=${search}&semester=${sList.semesterId}" class="list-group-item <c:if test='${semester eq sList.semesterId}'>active</c:if>">
                                    <c:out value="${sList.semesterCode}"/>
                                </a>
                            </c:forEach>

                        </div>
                    </div>


                </div>
                <!--Small cards-->


                <div class="col-sm-9 ">
                    <h4><spring:message code="studentPageTitle.home" /></h4>
                    <div class="card bigCardContainer">

                        <!--Search form-->
                        <div class="formWrapperContainer">
                            <div class="searchFormContainer">
                                <form class=" searchForm" role="search" action="${url}/student/search/${object}?search" method="GET">
                                    <div class="focus-container" tabindex="-1">
                                        <div class="input-group add-on">
                                            <input type="text" class="form-control" value="${search}" placeholder="<spring:message code="homePageLable.search" />" name="search" id="srch-term">
                                            <div class="input-group-btn">
                                                <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!--Coures list Begins-->
                        <ul class="courseSearchList list-group">

                            <%--<c:choose>--%>
                                <%--<c:when test="${empty param.filter}">--%>
                                    <%--show because no filter.... ${requestScope['javax.servlet.forward.query_string']}&filter=exam--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--filter dont show--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <c:if test="${ empty searchContentList}">
                                <div style="text-align: center"><i><spring:message code="studentSearchCourseAndExam.noDataFound"/> </i></div>
                            </c:if>

                            <c:forEach var = "searchList" items="${searchContentList}">


                                       <c:if test="${object eq 'courses'}">
                                           <!--List Item-->
                                           <li class="list-group-item listCard">
                                               <!-- course box content-->
                                               <div class="courseCard">
                                                   <div class="row">
                                                       <!--intro names -->
                                                       <div class="col-sm-8 ">
                                                           <div class="media">
                                                               <div class="media-left">
                                                                   <h4><span class="label label-default"><spring:message code="main.course"/></span></h4>
                                                               </div>
                                                               <div class="media-body">
                                                                   <h5 class="media-heading"><c:out value="${searchList.courseName}"/></h5>
                                                                   <div class="courseTextStyle" >
                                                                       <span class="courseOthersText"><spring:message code="studentPageCourseLables.teacher" />:</span>
                                                                       <a class="courseOthersText" href="${url}/student/view/${searchList.teacherId}/?nav=4">
                                                                           <c:out value="${searchList.teacherName}"/>
                                                                       </a>
                                                                   </div>
                                                                   <div class="courseTextStyle boxSpace" >
                                                        <span class="courseOthersText">
                                                            <c:out value="${searchList.courseDescriptionEn}"/>
                                                        </span>
                                                                   </div>
                                                               </div>
                                                           </div>

                                                       </div>

                                                       <div class="col-sm-4 courseCardBox3" >
                                                           <div class="courseTextStyle" >
                                                               <div>
                                                    <span class="courseDateTextBox courseOthersText"><spring:message code="manageCourse2.enrolmentDeadline"/>: <c:out value="${searchList.examEnrolmentEndDate}"/>
                                                               </div>
                                                               <!--course buttons-->
                                                               <div class="courseBtnBox">
                                                                   <a class="courseButton" onclick="enrollmentCheck(${searchList.courseId},'${searchList.courseName}','${searchList.teacherName}','${searchList.courseDescriptionEn}','${searchList.courseStartDate}','${searchList.courseEndDate}')" ><spring:message code="extra.view" /></a>
                                                               </div>
                                                               <!--course buttons-->
                                                           </div>

                                                       </div>
                                                   </div>
                                               </div>
                                           </li>
                                       </c:if>
                                        <!--List Item-->

                                    <%--<c:otherwise>--%>
                                        <%--filter dont show--%>
                                    <%--</c:otherwise>--%>


                                <%------------------------%>

                                <!--List Item-->
                                <c:if test="${object eq 'exams'}">
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
                                                            <h5 class="media-heading"><c:out value="${searchList.courseName}"/></h5>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText"><spring:message code="manageUsers2.semester"/>:</span>
                                                                <c:out value="${searchList.semesterCode}"/>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText"><spring:message code="examForm.examCode"/>:</span>
                                                                <a class="courseOthersText" href="${url}/admin/view/user-profile?userId=${searchList.examName}/>">
                                                                    <c:out value="${searchList.examName}"/>
                                                                </a>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText"><spring:message code="studentHome.examDate"/>:</span>
                                                                <c:out value="${searchList.examDate}"/>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>

                                                <div class="col-sm-4 courseCardBox3" >
                                                    <div class="courseTextStyle" >
                                                        <div>
                                                            <span class="courseDateTextBox courseOthersText"><span class="label label-danger"><spring:message code="studentIndex.deadline"/></span>&nbsp;:&nbsp;<c:out value="${searchList.examEnrolmentEndDate}"/></span>
                                                        </div>
                                                        <!--course buttons-->
                                                        <div class="courseBtnBox">
                                                            <a class="courseButton" href="#" onclick="examEnrollmentRequest(${searchList.examId},${searchList.childCourseId},'${searchList.examName}','${searchList.parentCouresName}','${searchList.courseDescriptionEn}','${searchList.getExamEnrolmentStartDate}','${searchList.examDate}','${searchList.examEnrolmentEndDate}')"><spring:message code="studentPageCourseLables.enroll" /></a>
                                                        </div>
                                                        <!--course buttons-->
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </c:if>
                                <!--List Item-->
                            </c:forEach>


                        </ul>
                        <!--Coures list Ends-->

                    </div>
                </div>


            </div>


        </div>


    </div>


        <%--course enrollment model --%>
        <div class="modal fade" id="enrollModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 id="courseN" class="modal-title"> </h4>
                    </div>
                    <h5 class="modelHead"><spring:message code="extra.courseEnrolment"/> </h5>
                    <div class="modal-body">
                        <div class="courseTextStyle boxSpace" >
                        <span class="courseOthersText" id="descrip">

                        </span>
                        </div>
                        <div class="courseTextStyle" >
                            <span class="courseOthersText"><spring:message code="studentPageCourseLables.teacher" /> : </span>
                            <a id="teacherN" class="courseOthersText" href="${url}/admin/view/user-profile?userId=${course.teacherId}/>">

                            </a>
                        </div>
                        <span class="courseDateTextBox courseOthersText">
                        <spring:message code="studentPageCourseLables.timePeriodFrom" /> : <span id="from"></span>
                        <spring:message code="studentPageCourseLables.timePeriodTo" /> : <span id="to"></span> </span>
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group btn-group-justified">
                            <a href="#" id="makeRequest" onclick="makeEnrollmentRequest(this.value)" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModal" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                            <a href="#" class="btn btn-primary" data-dismiss="modal" >Cancel</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="examEnrollModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">

                    <div class="modal-header">
                        <h4  class="modal-title courseOthersText"><span id="examNameE"></span><button type="button" class="close" data-dismiss="modal">&times;</button></h4>
                    </div>
                    <h5 class="modelHead"><spring:message code="studentIndex.examEnrollment"/> </h5>
                    <div class="modal-body">
                        <div class="courseTextStyle " >
                            <span id="courseE" class="courseOthersText"></span>
                            <br/>
                            <span style="margin-top: 20px;margin-bottom: 20px;" class="courseOthersText" id="descripE">

                        </span>
                        </div>
                        <div class="courseTextStyle" >

                            <span style=":"  class="label label-default"><spring:message code="studentHome.examDate"/></span> : <span class="courseOthersText" id="eDate"></span><div></div>
                            <span class="label label-danger"><spring:message code="manageCourse2.enrolmentDeadline"/></span> : <span class="courseOthersText" id="deadline"></span>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group btn-group-justified">
                            <a href="#"  id="makeExamRequest" onclick="makeExamEnrollmentRequest(this.value)" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModalExams" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                            <a href="#" id="reqE" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.cancel"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <%--pending modal--%>

        <div class="confirmModal">
            <div class="modal fade" id="pendingModal" role="dialog">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title confirmTitle"><spring:message code="studentSearchCourseAndExam.enrollmentRequestPending"/> </h4>
                        </div>
                        <div class="modal-body">
                            <div class="btn-group btn-group-lg btn-group-justified">
                                <button style="width: 100%" type="button" data-dismiss="modal" class="btn btn-danger"><spring:message code="main.cancel"/></button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>


</div>

<%--footer--%>

<script>
    //----------------------------------------
    // Breadcrumbs
    //----------------------------------------


    $('.breadcrumbs li a').each(function(){

        var breadWidth = $(this).width();

        if($(this).parent('li').hasClass('active') || $(this).parent('li').hasClass('first')){



        } else {

            $(this).css('width', 75 + 'px');

            $(this).mouseover(function(){
                $(this).css('width', breadWidth + 'px');
            });

            $(this).mouseout(function(){
                $(this).css('width', 75 + 'px');
            });
        }


    });

    function enrollmentCheck(courseId,courseName,teacherName,courseDescr,from ,dateTo) {
        $.ajax({
            url:'${url}/student/check-course-status/'+courseId,
            type:'POST',
            success:function(data){
                if(data === 'enrolled'){
                    window.location.href = '${url}/student/view/course/'+courseId+'?nav=4';
                }
                if(data === 'enroll'){
                    $('#enrollModal').modal('toggle');
                    $('#courseN').text(courseName);
                    $('#descrip').text(courseDescr);
                    $('#teacherN').text(teacherName);
                    $('#from').text(from );
                    $('#to').text(dateTo);
                    $('#makeRequest').val(courseId);
                }
                if(data === 'pending'){
//                    alert(data);
                    $('#pendingModal').modal('toggle');
                }
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');

            }
        });
    }

    function makeEnrollmentRequest(courseId){

        var r = confirm('<spring:message code="studentSearchCourseAndExam.msg8"/>');
        if(r == true){
            $.ajax({
                url:'${url}/student/course/request/'+courseId,
                type:'post',
                success:function(data){
                    alert(data);
                    $('#enrollModal').modal('toggle');
                    $('#confirmationModal').modal('toggle');
                    $(".alert-success").css("display", "block");
                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //do nothing
        }
    }

    function enrollmentCheckExam(examId,courseId,examName,courseName,courseD,examDate,enrolSrt,enrolEnd) {

        $.ajax({
            url:'${url}/student/check-exam-status/'+examId,
            type:'POST',
            success:function(data){
                if(data == 'enrolled'){
                    window.location.href = '${url}/student/course-exam/'+courseId+'?nav=4';
                }
                if(data == 'enroll'){
                    $('#examEnrollModal').modal('toggle');
                    $('#reqE').val(courseId);
                    $('#courseE').text(courseName);
                    $('#descripE').text(courseD);
                    $('#examNameE').text(examName);
                    $('#eDate').text(examDate);
                    $('#deadline').text(enrolEnd);
                    $('#makeExamRequest').val(examId);
                }
                if(data == 'pending'){
                    $('#pendingModal').modal('toggle');
                }
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');

            }
        });
    }

    function examEnrollmentRequest(examId,courseId,examName,courseName,courseDescr,examDate,enrolSrt,enrolEnd) {

        $('#examEnrollModal').modal('toggle');
        $('#reqE').val(courseId);
        $('#courseE').text(courseName);
        $('#descripE').text(courseDescr);
        $('#examNameE').text(examName);
        $('#eDate').text(examDate);
        $('#deadline').text(enrolEnd);
        $('#makeExamRequest').val(examId);

    }

    function makeExamEnrollmentRequest(examId) {

        var r = confirm('<spring:message code="studentSearchCourseAndExam.msg8"/>');
        if(r == true){
            $.ajax({
                url:'${url}/student/exam/request-enrollment/'+$('#reqE').val()+'/'+examId,
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