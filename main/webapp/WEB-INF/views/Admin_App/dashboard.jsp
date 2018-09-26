<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 11/08/2017
  Time: 02:32
  To change this template use File | Settings | File Templates.
  home index admin dashBoard
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%--SHIRO INJECTION--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="dashboard.adminTitle"/></title>
    <link href="${url}/resources/app_admin_static/css/dashboard.css" rel="stylesheet">
    <%--<link href="${url}/resources/app_admin_static/css/subPages/grades-detailed.css" rel="stylesheet">--%>
    <link rel='stylesheet' href="${url}/resources/main/fullcalendar/dist/fullcalendar.min.css" />
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>



</head>

<body>

    <%--CONTENT BEGINES --%>
    <div class="content">

        <br/>
        <h2 class="pageTitle"><spring:message code="adminNavLable.dashboard" /> </h2>
        <%--Hello, <shiro:principal/>, how are you today?--%>

        <%--RESPONSIVE GRID MAIN CONTAINER START --%>
        <div class="row gridContainer">

            <%--LEFT GRID CONTAINER--%>
            <div class="col-sm-8 leftGridContainer ">




                <%--LEFT GRID TOP CONTAINER START --%>
                <div  class="card leftGridTopContainer">
                    <%--TOP LEVEL BOX--%>
                    <div class="row">

                        <%--IMAGE BOX--%>
                        <div class="col-sm-5 headBoxImage">

                            <%--------------------------------%>
                                <div class="avatar cursor-pointer">
                                    <img src="${url}${url}/download/images/${UserProfilePicUri}">
                                    <%--<img src="${url}/resources/student_app_static/images/tempimg.jpg">--%>
                                </div>
                            <%-------------------------------%>

                        </div>

                        <%--STATS BOX --%>
                        <div class="col-sm-7 statsBox">
                               <%-------------------------------%>
                                <%--USER STATISTICS BOX --%>

                                <%--<div class="userStatsBoxContainer">--%>

                                    <%--&lt;%&ndash;USER STATS BOX&ndash;%&gt;--%>
                                    <%--<div class="userStatsBox"><span><h3 class="userStatsBoxDigits">70</h3> <h3 class="userStatsBoxTitles">Total Users</h3></span></div>--%>
                                    <%--<div class="userStatsBox"><span><h3>50</h3><h3>Total Courses</h3></span></div>--%>

                                <%--</div>--%>
                                   <div class="dash-item__content">
                                       <c:if test="${currentUserRole eq 'admin'}">
                                           <ul class="quiz-results">
                                               <li class="quiz-results__item quiz-results__item--average-score">
                                                   <span class="quiz-results__number quiz-results__number--average-score">${totalActiveCourses}</span>
                                                   <div class="quiz-results__label"><spring:message code="dashboard.totalActiveCourse"/> </div>
                                               </li>
                                               <li class="quiz-results__item quiz-results__item--average-score">
                                                   <span class="quiz-results__number quiz-results__number--average-score">${totalActiveExams}</span>
                                                   <div class="quiz-results__label"><spring:message code="dashboard.totalActiveExams"/></div>
                                               </li>
                                           </ul>
                                       </c:if>
                                       <c:if test="${currentUserRole ne 'admin'}">
                                           <ul class="quiz-results">
                                               <li class="quiz-results__item quiz-results__item--average-score">
                                                   <span class="quiz-results__number quiz-results__number--average-score">${myActiveCourses}</span>
                                                   <div class="quiz-results__label"><spring:message code="dashboard.myTotalActiveCourses"/></div>
                                               </li>
                                               <li class="quiz-results__item quiz-results__item--average-score">
                                                   <span class="quiz-results__number quiz-results__number--average-score">${myTotalActiveExams}</span>
                                                   <div class="quiz-results__label"><spring:message code="dashboard.myTotalActiveExams"/></div>
                                               </li>
                                           </ul>
                                       </c:if>


                                   </div>

                                <%-------------------------------%>
                        </div>
                    </div>

                    <%--STATS LIST CONTAINER--%>
                        <div class="row statsListContainer">
                            <%--USER BASED STATS--%>
                            <div class="col-sm-6 userBasedStatsBox">
                                <%--STATS LIST--%>
                               <c:if test="${currentUserRole eq 'admin'}">
                                   <h4><spring:message code="dashboard.totalSystemMajors"/> <span class="statsListNumberItem">${totalSystemMajors}</span></h4>
                                   <h4><spring:message code="dashboard.totalParentCourses"/> <span class="statsListNumberItem">${parentCourseCount}</span></h4>
                                   <h4><spring:message code="dashboard.totalChildCourses"/> <span class="statsListNumberItem">${childCoursesCount}</span></h4>
                                   <h4><spring:message code="dashboard.totalExams"/> <span class="statsListNumberItem">${0}</span></h4>
                                   <h4><spring:message code="dashboard.totalActiveClearaceExams"/> <span class="statsListNumberItem">${totalActiveClearanceExamCount}</span></h4>
                                   <h4><spring:message code="dashboard.totalClearanceExams"/> <span class="statsListNumberItem">${totalClearanceExamCount}</span></h4>
                                   <h4><spring:message code="dashboard.totalClasses"/> <span class="statsListNumberItem">${totalClasses}</span></h4>
                                   <h4><spring:message code="dashboard.totalSystemsSemester"/> <span class="statsListNumberItem">${totalSystemSemesterCount}</span></h4>
                                   <h4><spring:message code="dashboard.upcomingEvents"/> <span class="statsListNumberItem">${upcomingEvents.size()}</span></h4>
                               </c:if>

                                    <c:if test="${currentUserRole ne 'admin'}">
                                        <h4><spring:message code="dashboard.teacherTotalCourse"/> <span class="statsListNumberItem">${myTotalCourses}</span></h4>
                                        <h4><spring:message code="dashboard.teacherTotalExams"/> <span class="statsListNumberItem">${myTotalExams}</span></h4>
                                        <h4><spring:message code="dashboard.myTotalExamEnrolments"/> <span class="statsListNumberItem">${myTotalExamEnrollments}</span></h4>
                                        <h4><spring:message code="dashboard.myTotalCourseEnrolments"/> <span class="statsListNumberItem">${myTotalCourseEnrollments}</span> </h4>
                                        <h4><spring:message code="dashboard.myTotalActiveCleranceExams"/> <span class="statsListNumberItem">${totalActiveClearanceExamCount}</span></h4>
                                    </c:if>





                            </div>
                                <%--COURSE BASED STATS  --%>
                            <div class="col-sm-6 ">
                                <%--STATS LIST --%>
                                    <c:if test="${currentUserRole eq 'admin'}">
                                        <h4><spring:message code="admin.totalUsers" /><span class="statsListNumberItem">${totalUsers}</span></h4>
                                        <h4><spring:message code="dashboard.totalSystemsAdmins"/> <span class="statsListNumberItem">${totalSystemAdmins}</span></h4>
                                        <h4><spring:message code="extra.totalTeacher" /><span class="statsListNumberItem">${totalTeachers}</span></h4>
                                        <h4><spring:message code="admin.totalStudents" /><span class="statsListNumberItem">${totalStudents}</span></h4>
                                        <h4><spring:message code="admin.totalDoubleDegStd" /><span class="statsListNumberItem">${totalDoubleDegreeStudents}</span></h4>
                                        <h4><spring:message code="dashboard.totalChineseDegreeStudents"/> <span class="statsListNumberItem">${totalChineseDegreeStudents}</span></h4>
                                        <h4><spring:message code="admin.totalExamEnrollments" /><span class="statsListNumberItem">${totalExamEnrollments}</span></h4>
                                        <h4><spring:message code="dashboard.totalCourseEnrolments"/> <span class="statsListNumberItem">${totalCourseEnrollments}</span></h4>
                                        <h4><spring:message code="dashboard.totalClearanceExamEnrolment"/> <span class="statsListNumberItem">${totalStudentsEnrolledInClearanceExams}</span></h4>

                                    </c:if>

                                    <c:if test="${currentUserRole ne 'admin'}">


                                        <h4><spring:message code="extra.totalTeacher" /><span class="statsListNumberItem">${totalTeachers}</span></h4>
                                        <h4><spring:message code="admin.totalStudents" /><span class="statsListNumberItem">${totalStudents}</span></h4>
                                        <h4><spring:message code="admin.totalDoubleDegStd" /><span class="statsListNumberItem">${totalDoubleDegreeStudents}</span></h4>
                                        <h4><spring:message code="dashboard.totalChineseDegreeStudents"/> <span class="statsListNumberItem">${totalChineseDegreeStudents}</span></h4>
                                        <h4><spring:message code="dashboard.totalClearanceExams"/> <span class="statsListNumberItem">${totalClearanceExamCount}</span></h4>

                                    </c:if>

                            </div>
                        </div>
                    <%--STATS LIST CONTAINER --%>
                </div>
                <!--/END LEFT GRID TOP CONTAINER  -->


                <%--LEFT GRID BOTTOM CONTAINER START --%>
                <div class=" card leftGridBottomContainer">
                    <c:if test="${!empty degreeTypeChangeRequests}">
                        <ul class="nav nav-tabs">
                            <li class="<c:if test="${!empty degreeTypeChangeRequests}">active</c:if>"><a class="rqLink" id="majorTab" data-toggle="tab" href="#mainTabView"><span class="badge" style="margin-top: -4px">${degreeTypeChangeRequests.size()}</span> <spring:message code="dashboard.studentRequests"/></a></li>
                            <%--<li class=""><a class="rqLink" id="courseTab" data-toggle="tab" href="#mainTabView"><span style="visibility: hidden" class="badge">a</span><spring:message code="dashboard.latestExamEnrolments"/></a></li>--%>
                        </ul>

                        <br/>
                        <span>  <i><spring:message code="dashboard.msg4"/></i> </span>
                    </c:if>



                    <c:if test="${empty degreeTypeChangeRequests}">
                        <span>  <i><spring:message code="dashboard.msg5"/></i> </span>
                    </c:if>

                    <div class="table-responsive">
                        <table id="requestsTable" class="table  table-hover">



                                <c:if test="${!empty degreeTypeChangeRequests}">
                                    <%--<thead>--%>
                                    <%--<tr>--%>
                                    <%--<th></th>--%>
                                    <%--<th width="115px"></th>--%>
                                    <%--<th></th>--%>
                                    <%--<th></th>--%>
                                    <%--<th></th>--%>
                                    <%--</tr>--%>
                                    <%--</thead>--%>

                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th width="220px"></th>
                                        <th></th>
                                        <th width="100px"></th>
                                        <th width="100px" align="right"></th>
                                    </tr>
                                    </thead>
                                </c:if>

                            <c:if test="${empty degreeTypeChangeRequests}">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th width="115px"></th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                            </c:if>



                            <tbody>

                            <c:if test="${empty degreeTypeChangeRequests}">
                                <c:forEach items="${systemLatestCourseEnrollmentNotifications}" var="notification">
                                    <tr class="g-info">
                                        <td >${notification.userName}</td>
                                        <td align="center"><span style="font-size: 15px; font-weight: 900"><spring:message code="dashboard.msg6"/></span></td>
                                        <td><span style="font-size: 20px; " class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></td>
                                        <td align="center">${notification.parentCourseName}</td>
                                            <%--<td><span class="badge">${'asasasasasasasasas'}</span></td>--%>
                                        <td align="right">
                                            <a class="courseButton"  style="padding: 0"  href="${url}/admin/manage-courses/enrolment-list/${notification.childCourseId}/1"><spring:message code="extra.view" /></a>
                                        </td>
                                    </tr>

                                </c:forEach>
                                <c:forEach items="${systemsLatestExamEnrollmentNotifications}" var="notification">
                                    <tr class="g-info">
                                        <td >${notification.userName}</td>
                                        <td align="center"><span style="font-size: 15px; font-weight: 900"><spring:message code="dashboard.msg6"/></span></td>
                                        <td><span style="font-size: 20px; " class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></td>
                                        <td align="center">${notification.parentCourseName}</td>
                                            <%--<td><span class="badge">${'asasasasasasasasas'}</span></td>--%>
                                        <td align="right">
                                            <a class="courseButton"  style="padding: 0"  href="${url}/admin/manage-exams/enrolmentList/${notification.examId}/1"><spring:message code="extra.view" /></a>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </c:if>



                            <c:if test="${!empty degreeTypeChangeRequests}">
                                <c:forEach items="${degreeTypeChangeRequests}" var="requests">
                                    <tr class="g-warning">
                                        <td align="center"><a class="rqLink" href="${url}/admin/profile/view/${requests.studentId}?nv=db">${requests.userName}</a></td>
                                        <td>
                                            <div style="text-align: center">
                                                <span style="font-size: 14px; font-weight: 700"><spring:message code="dashboard.msg7"/></span><br/>
                                                <span class="label label-default">${requests.submissionDate}</span>
                                            </div>
                                        </td>
                                        <td align="center"><span style="font-size: 25px; margin-top: 16px" class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></td>
                                        <td align="center">${requests.newRequestedDegreeType}</td>
                                        <td>
                                            <a class="courseButton" style="padding: 0; margin-top: 10px"   href="#" onclick="acceptRequest('${requests.requestId}','${requests.studentId}','${requests.newRequestedDegreeType}')"><spring:message code="dashboard.accept"/></a><br/>
                                            <a class="courseButton" style="padding: 0"   href="#" onclick="declineRequest(${requests.requestId})"><spring:message code="dashboard.decline"/></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>



                            </tbody>

                            <tfoot>
                            <tr>
                                <%--<td colspan="5" class="text-center"> <span>System students latest course and exam enrollments</span></td>--%>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                    <%--<--END OF TABLE-->--%>

                </div>
                <!--/END LEFT GRID BOTTOM CONTAINER -->






            </div>
            <!--/END OF LEFT GRID CONTAINER -->

                <%--<div class="col-sm-1" ></div>--%>
            <%--RIGHT GRID  CONTAINER--%>
            <div class="col-sm-4 rightGridContainer">

                <%--RIGHT GRID TOP CONTAINER START --%>
                <div class="card rightGridTopContainer">

                    <div>

                        <h4 class="titleTextBoxHeaders"><span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>&nbsp;&nbsp;<a style="color: inherit" href="${url}/admin/events/list"><spring:message code="sharedPageLable.upcomingEvent" /></a><span style="cursor: pointer" id="addEvent" class=" inIcon inIconAni glyphicon glyphicon-plus" aria-hidden="true"></span></h4>
                        <ul class="eventList">
                            <c:forEach varStatus="index"  var="eventList" items="${upcomingEvents}">
                                <li class="eventClick">
                                    <div class="notificationObject">
                                        <div class="ntObjectCase">
                                            <div class="dateCase">
                                                <div class="dash-item__content">
                                                    <div class="a">
                                                        <ul class="quiz-results">
                                                            <li class="quiz-results__item quiz-results__item--average-score">
                                                                <span class="dayNumber"><c:out value="${eventList.eventDay}"/></span>
                                                                <div class="monthSm"><c:out value="${fn:substring(eventList.eventMonth,0,3)}"/></div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div onclick="showEvent('${eventList.title}','${eventList.description}')" class="textCase">
                                                <div class="eventText"><c:out value="${eventList.title}"/></div>
                                                <p><c:out value="${fn:substring(eventList.description,0,40)}" /><c:if test="${fn:length(eventList.description) > 40}">...</c:if></p>
                                            </div>
                                            <div class="dateCase">
                                                <div class="dash-item__content">
                                                    <div class="a">
                                                        <ul class="quiz-results">
                                                            <li class="quiz-results__item quiz-results__item--average-score">
                                                                <a href="${url}/admin/events/edit/${eventList.event_id}/1 "><span onclick="" style="margin-left:25px " class="inIcon inIconAni iconhide glyphicon glyphicon-edit" aria-hidden="true"></span></a><br/>
                                                                <a onclick="deleteEvent(${eventList.event_id})"><span style="margin-left:25px " class="inIcon inIconAni iconhide  glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>

                        <c:if test="${fn:length(upcomingEvents) gt 2 }">
                            <a style="margin: 10px" href="${url}/admin/events/list"><span class="viewMore"><spring:message code="commonLable.showMore" /></span></a>
                        </c:if>

                    </div>

                </div>
                <!--/END RIGHT GRID BOTTOM CONTAINER  -->

                <%--RIGHT GRID BOTTOM CONTAINER START --%>
                <div class="card rightGridBottomContainer">
                    <div id="bootstrapModalFullCalendar"></div>
                </div>

            </div>
            <!--/END OF RIGHT GRID CONTAINER -->


        </div>
        <!--/END OF RESPONSIVE GRID MAIN CONTAINER  -->

    </div>
    <!--Content Ends  -->


    <%--modal--%>
    <div id="fullCalModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span> <span class="sr-only">#close</span></button>
                    <h4 style="text-align: center;" id="modalTitle" class="modal-title"></h4>
                </div>
                <div style="text-align: center;" id="modalBody" class="modal-body"></div>
                <div class="modal-footer">
                    <%--<button type="button" value="${row.user_id}" class="btn btn-default" id="deleteEvent">Delete</button>--%>
                    <%--<a class="btn btn-primary" id="eventUrl" target="_blank">Edit</a>--%>
                </div>
            </div>
        </div>
    </div>
    <%--modal--%>


    <script src="${url}/resources/main/moment/moment.js"></script>
    <script src="${url}/resources/main/fullcalendar/dist/fullcalendar.min.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/zh-cn.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/en-gb.js "></script>
    <%--<script src="${url}/resources/main/bootstrap/js/bootstrap.min.js"></script>--%>
    <script>

        $(document).ready(function() {
            <%--$('#requestsTable').DataTable({--%>
                <%--"lengthChange": false,--%>
                <%--"order": [[ 5, "desc" ]],--%>
                <%--"ordering": false,--%>
                <%--<c:if test="${pageContext.response.locale eq 'cn'}">--%>
                <%--"language": translation--%>
                <%--</c:if>--%>
            <%--});--%>

            $('#bootstrapModalFullCalendar').fullCalendar({
                header: {
                    left: '',
                    center: 'prev title next',
                    right: ''
                },
                <c:if test="${pageContext.response.locale eq 'cn'}">
                locale: 'zh-cn',
                </c:if>
                eventColor:'#777777',
                //eventColor: '#4285F4',
                eventClick:  function(event, jsEvent, view , id) {
                    $('#modalTitle').html(event.title);
                    $('#modalBody').html(event.description);
                    $('#eventUrl').attr('href',event.url);
                    $('#deleteEvent');
                    $('#fullCalModal').modal();
                    return false;
                },
                eventMouseover :function( event, jsEvent, view ) {

                },
//                dayClick:function () {
//                    alert("day clicked");
//                },
                events:${calendarEvents},
                height: 400,
                contentHeight: 400
//                width:200
            });

            $(".eventClick").hover(function(){
                $(".iconhide").css("visibility","visible");
            },function(){
                $(".iconhide").css("visibility","hidden");
            });
        });
        $("#addEvent").click(function(){
            //add press css
            //alert("Call Add Event model");
            window.location.href = "${url}/admin/event/add";
        });

        $('#deleteEvent').click(function () {
            var r = confirm('<spring:message code="dashboard.msg1"/>');
            if (r === true) {
                alert("Event Deleted"+$(this).val());
                $('#fullCalModal').modal('toggle');
                location.reload(true);

            } else{
                //do nothing
            }
        });

        function editEvent(eventId) {
//        alert(eventId);
            $.ajax({
                url:'${url}/admin/manage-course/event/'+eventId,
                type:'post',
                success:function(data){
                    alert(data);
                    console.log(data);
                    $('#editBtn').val(data.event_id);
                    $('#eName').val(data.title);
                    $('#eDate').val(data.eventDateTime);
                    $("textarea#eDes").val(data.description);
                    $('#editModel').modal('toggle');
                    // location.reload(true);
                },
                error : function(){
                    alert('<spring:message code="dashboard.msgError"/>');
                }
            });
            // $('#modalTitle').html(event.title);
//        $('#modalBody').html(event.description);
//        $('#fullCalModal').modal();
//        $('#fullCalModal').modal('toggle');
        }

        function deleteEvent(eventId) {
            var r = confirm('<spring:message code="dashboard.msg2"/>');
            if (r === true) {

                $.ajax({
                    url:'${url}/admin/manage-course/delete-course-event/'+eventId,
                    type:'post',
                    success:function(data){
                        //console.log(data);
                        alert('<spring:message code="dashboard.msg3"/>');
                        location.reload(true);
                    },
                    error : function(){
                        alert('<spring:message code="dashboard.msgError"/>');
                    }
                });

            }else{
                //nothing done
            }

        }

        function showEvent(title,description) {
            $('#modalTitle').html(title);
            $('#modalBody').html(description);
            $('#fullCalModal').modal();
        }

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
        };

        <%--console.log(${calendarEvents});--%>

        function acceptRequest(id,studentId,degreeType) {

                $.ajax({
                    url:'${url}/admin/degree-change/accept',
                    type:'post',
                    data:{'requestId':id,"studentId":studentId,"degreeType":degreeType},
                    success:function(data){
                        if(data === '200'){
                            alert('<spring:message code="dashboard.msg8"/>');
                            location.reload(true);
                        }else{
                            alert('<spring:message code="dashboard.msg9"/>');
                        }

                    },
                    error : function(){
                        alert('<spring:message code="dashboard.msgError"/>');
                    }
                });

        }

        function declineRequest(id) {
            var r = confirm('Are you sure you want to decline this request');
            if (r === true) {

                $.ajax({
                    url:'${url}/admin/manage-users/decline/degree-type-change',
                    type:'post',
                    data:{'id':id},
                    success:function(data){
                        if(data === '200'){
                            alert('<spring:message code="dashboard.msg10"/>');
                            location.reload(true);
                        }else{
                            alert('<spring:message code="dashboard.msg11"/>');
                        }

                    },
                    error : function(){
                        alert('<spring:message code="dashboard.msgError"/>');
                    }
                });

            }else{
                //nothing done
            }

        }


    </script>



</body>

</html>
