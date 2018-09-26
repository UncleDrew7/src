<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 07/08/2017
  Time: 23:02
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
    <title><spring:message code="studentLable.home" /></title>
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link rel='stylesheet' href="${url}/resources/main/fullcalendar/dist/fullcalendar.min.css" />
    <script src="${url}/resources/main/moment/moment.js"></script>
    <script src="${url}/resources/main/fullcalendar/dist/fullcalendar.min.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/zh-cn.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/en-gb.js "></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>

</head>

<body>

<div>

    <%--navs--%>

    <!-- Tab panes -->
    <div class="content">

        <div class="wrapper row">
            <ul class="breadcrumbs">
                <li class="first"><a href="${url}/student/home" class="icon-home"><spring:message code="studentEventDetails.home"/></a></li>
                <li class="last active"><a ><spring:message code="events.allEvents"/></a></li>
            </ul>
        </div>

        <div role="tabpanel" class="tab-pane active" id="home">

            <div id="users" class="row mainContainer">
                <div class="col-sm-9 ">
                    <div class="card bigCardContainer ">



                        <!--Search form-->
                        <div class="formWrapperContainer">
                            <div class="searchFormContainer">
                                    <div class="focus-container" tabindex="-1">
                                        <div class="input-group add-on">
                                            <input type="text" class="form-control search"  placeholder="<spring:message code="homePageLable.search" />" name="search" id="srch-term">
                                            <div class="input-group-btn">
                                                <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                                            </div>
                                        </div>
                                    </div>

                            </div>
                        </div>
                        <!--Search form-->




                            <!-- class="sort" automagically makes an element a sort buttons. The date-sort value decides what to sort by. -->
                            <%--<button class="sort" data-sort="name">--%>
                                <%--Sort--%>
                            <%--</button>--%>



                        <div class="eventMainList">
                            <ul class="courseSearchList  list-group list">


                                <c:forEach var = "eList" items="${eventsList}">
                                    <!--List Item-->
                                    <!--List Item-->
                                    <%--<br/>--%>
                                    <li class="list-group-item listCard">
                                        <!-- course box content-->
                                        <div class="courseCard">
                                            <div class="row">
                                                <!--intro names -->
                                                <div class="col-sm-12 ">
                                                    <div class="media">

                                                        <div class="media-body">
                                                            <div>
                                                                <span style="float: right" class="courseDateTextBox courseOthersText"><span class="label label-primary"><c:out value="${eList.eventDateTime}"/></span></span>
                                                            </div>
                                                            <h4 style="font-weight: 500; " class="media-heading name "><c:out value="${eList.title}"/></h4>
                                                            <c:if test="${eList.courseId ne 0}">
                                                                <h5 style="font-weight: 600; color: #726f6f" class="media-heading born">(<c:out value="${eList.courseName}"/>)</h5>
                                                            </c:if>
                                                            <div class="courseTextStyle" >
                                                            <span class="courseOthersText" >
                                                                <c:out value="${fn:substring(eList.description,0,158)}"/>
                                                                <c:if test="${fn:length(eList.description) gt 158}">
                                                                    ....
                                                                </c:if>

                                                            </span>
                                                            </div>
                                                            <div style="color: #1f5fa5" class="courseTextStyle" >
                                                                <i><span class="courseOthersText"><spring:message code="events2.createdBy"/> :</span>
                                                                <c:out value="${eList.createdByUserName}"/></i>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>

                                                    <%--<div class="col-sm-3 courseCardBox3" >--%>
                                                    <%--<div class="courseTextStyle" >--%>
                                                    <%--<!--course buttons-->--%>
                                                    <%--<div class="courseBtnBox">--%>
                                                    <%--<a class="courseButton" href="#${availableExams.courseId}">View</a>--%>
                                                    <%--</div>--%>
                                                    <%--<!--course buttons-->--%>
                                                    <%--</div>--%>

                                                    <%--</div>--%>

                                            </div>
                                        </div>
                                    </li>
                                    <!--List Item-->
                                </c:forEach>


                            </ul>
                            <!--Coures list Ends-->
                        </div>
                    </div>
                </div>

                <!--Small cards-->
                <div class="col-sm-3">

                    <div class="card smallCardContainer ">
                        <h4 class="titleTextBoxHeaders"><spring:message code="studentEventDetails.filterEvents"/></h4>
                        <%------------------------------------%>
                        <div style="margin-top: -11px" class="list-group">
                            <a href="${url}/student/all-events/1/800" class="list-group-item <c:if test='${filter eq 1}'>active</c:if>"><spring:message code="studentEventDetails.upcomingEvents"/>  <span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>
                            <a href="${url}/student/all-events/3/800" class="list-group-item <c:if test='${filter eq 3}'>active</c:if>"><spring:message code="studentEventDetails.allCourseEvents"/> </a>
                            <a href="${url}/student/all-events/2/800" class="list-group-item <c:if test='${filter eq 2}'>active</c:if>"><spring:message code="studentEventDetails.allSystemEvents"/> </a>
                            <a href="${url}/student/all-events/4/800" class="list-group-item <c:if test='${filter eq 4}'>active</c:if>"><spring:message code="studentEventDetails.allEvents"/></a>
                        </div>
                        <%---------------------------------------------%>
                    </div>


                    <div class="card smallCardContainer ">
                        <h4 class="titleTextBoxHeaders"><spring:message code="studentIndex.calender"/></h4>
                        <div id='calendar'></div>
                    </div>


                </div>
                <!--Small cards-->
            </div>


        </div>


    </div>

</div>

<%--footer--%>
<!-- Modal -->
<div class="modal fade" id="enrollModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 id="courseN" class="modal-title"> </h4>
            </div>
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
                    <a href="#" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModal" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                    <a href="#" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.cancel"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

<%--CONFRIMATION MODAL --%>
<div class="confirmModal">
    <div class="modal fade" id="confirmationModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title confirmTitle"><spring:message code="studentEventDetails.confirmEnrollmentRequest"/> </h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group btn-group-lg btn-group-justified">
                        <button style="width: 50%" type="button"  id="makeRequest" onclick="makeEnrollmentRequest(this.value)" class="btn btn-success"><spring:message code="courseExams.enroll"/></button>
                        <button style="width: 50%" type="button" data-dismiss="modal" class="btn btn-danger"><spring:message code="main.cancel"/></button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<%--Event Modal--%>
<div id="eventModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span> <span class="sr-only"><spring:message code="events.close"/></span></button>
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
<%--Event Modal--%>

<script>

    $(document).ready(function() {

        // page is now ready, initialize the calendar...

        $('#calendar').fullCalendar({
//            header: {
//                left: 'prev,next today',
//                center: 'title',
//                right: 'month,agendaWeek,agendaDay'
//            },
            editable: true,
            eventLimit: true
        })

    });
    function showNotification(title,notification) {
        $('#eventModal').modal('toggle');
        $('#modalTitle').text(title);
        $('#modalBody').text(notification);
    }
    function enrollmentRequest(courseId ,courseName,teacherName,courseDescr,from ,dateTo){
//        alert();
        $('#enrollModal').modal('toggle');
        $('#courseN').text(courseName);
        $('#descrip').text(courseDescr);
        $('#teacherN').text(teacherName);
        $('#from').text(from );
        $('#to').text(dateTo);
        $('#makeRequest').val(courseId);
    }

    function makeEnrollmentRequest(courseId){
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


    }

//    list
    var options = {
        valueNames: [ 'name', 'born' ]
    };

    var userList = new List('users', options);






</script>


</body>

</html>