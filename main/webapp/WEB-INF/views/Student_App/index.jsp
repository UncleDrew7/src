<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<html lang="en">

<head>
    <title><spring:message code="studentLable.home" /></title>
    <%--<link href="https://github.com/jhonis/e-calendar/blob/master/js/jquery-1.11.0.min.js" rel="stylesheet">--%>
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link rel='stylesheet' href="${url}/resources/main/fullcalendar/dist/fullcalendar.min.css" />

    <script src="${url}/resources/main/list.min.js"></script>
</head>

<body>

    <div>

    <%--navs--%>

        <!-- Tab panes -->
        <div class="content">
            <%--alerts --%>
                <div style="display: none" class="alert alert-success alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="studentIndex.success"/></strong> <span id="suc"></span>
                </div>
                <div style="display: none" class="alert alert-danger alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="studentIndex.success"/></strong>
                </div>
            <%--alerts--%>
            <div role="tabpanel" class="tab-pane active" id="home">

                <div class="row mainContainer">

                    <!--Small cards-->
                    <div class="col-sm-3">

                        <%--<div class="card ">--%>
                            <%--<h4 class="titleTextBoxHeaders"><spring:message code="studentHome.enrolProgress" /></h4>--%>
                            <%--<div style="margin-top: -11px" class="list-group">--%>
                                <%--<a href="${url}/student/home/1" class="list-group-item enrollItem <c:if test='${filter eq 1}'>active</c:if>">Home<span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>--%>
                                <%--<a href="${url}/student/home/2" class="list-group-item enrollItem <c:if test='${filter eq 2}'>active</c:if>">Pending</a>--%>
                                <%--<a href="${url}/student/home/3" class="list-group-item enrollItem <c:if test='${filter eq 3}'>active</c:if>">Enrolled</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="card upcomingEventsContainer ">
                            <div>
                                <h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.upcomingEvent" /></h4>
                            </div>
                            <!--TimeLine container-->
                                <div class="timelineWrapperContainer">
                                    <%--onclick="enrollmentRequest(${course.courseId},'${course.courseName}','${course.teacherName}','${course.courseDescriptionEn}','${course.startDate}','${course.endDate}')"--%>
                                    <ul class="eventList">
                                        <c:forEach varStatus="index"  var="eventList" items="${upcomingEvents}">
                                            <li>
                                                <div onclick="showNotification('${eventList.title}','${eventList.description}')" class="notificationObject">
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
                                                        <div class="textCase">
                                                            <h5 class="media-heading"><c:out value="${eventList.title}"/></h5>
                                                            <p style="text-align: left"  class="courseOthersText"><c:out value="${fn:substring(eventList.description,0,30)}" />...</p>
                                                        </div>
                                                        <a href="#" class="eventViewLink">view</a>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                        <c:if test="${upcomingEvents.size() >= 1}">
                                            <a style="float: right" href="${url}/student/all-events/1/${userId}" class="showMore"><spring:message code="commonLable.showMore" /></a>
                                        </c:if>

                        </div>
                            <!--TimeLine Container-->

                        </div>

                       <!-- <div class="card smallCardContainer ">
                                <h4 class="titleTextBoxHeaders">Calender</h4>
                        </div>-->

                        <div class="card smallCardContainer ">
                            <h4 class="titleTextBoxHeaders"><spring:message code="studentIndex.calender"/></h4>
                            <div id='calendar'></div>
                            <%--<div id="bootstrapModalFullCalendar"></div>--%>
                        </div>
                           <%-- <div class="card rightGridBottomContainer">
                                <div id="bootstrapModalFullCalendar"></div>
                            </div>--%>

                    </div>
                    <!--Small cards-->

                    <div class="col-sm-9 ">
                        <h4><spring:message code="studentLable.home" /></h4>
                        <div class="card bigCardContainer">

                            <!--Search form-->
                            <div class="formWrapperContainer">
                                <div class="searchFormContainer">
                                     <%--<form class="searchForm" role="search" >--%>
                                            <%--<form class="searchForm" role="search" action="${url}/student/search/courses?search" method="GET">--%>
                                        <div class="focus-container" tabindex="-1">
                                            <div class="input-group add-on">
                                                <input type="text" class="form-control" placeholder="<spring:message code="homePageLable.search" />" name="search" id="srch-term" onchange="searching()">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" ><i class="glyphicon glyphicon-search"></i></button>
                                                    <%--<button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>--%>
                                                </div>
                                            </div>
                                        </div>
                                    <%--</form>--%>
                                </div>
                            </div>

                            <!--Search form Ends-->
                            <div class="displayPill">
                                <ul class="nav nav-pills display">
                                    <li><a id="coursePill" <c:if test="${filter ne 'exams'&& filter ne 'retakes'}"> style="background:rgb(51, 122, 183); color: #fff8f8"</c:if> data-toggle="pill" href="#courses" class="actv"><spring:message code="studentHome.course" />
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${filter eq 2}">--%>
                                                <%--&lt;%&ndash;<span style="background: #aa1111" class="badge">${courseCount}</span>&ndash;%&gt;--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${filter eq 3}">--%>
                                                <%--&lt;%&ndash;<span style="background: #0b690d" class="badge">${courseCount}</span>&ndash;%&gt;--%>
                                            <%--</c:when>--%>
                                        <%--</c:choose>--%>
                                    </a></li>

                                    <li><a id="examPill" <c:if test="${filter eq 'exams'}"> style="background:rgb(51, 122, 183); color: #fff8f8"</c:if> data-toggle="pill" href="#exams">&nbsp;<spring:message code="studentHome.exam" />&nbsp;
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${filter eq 'course'}">--%>
                                                <%--<span style="background: #aa1111" class="badge">${'8'}</span>--%>
                                            <%--</c:when>--%>
                                            <%--&lt;%&ndash;<c:when test="${filter eq 3}">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;&lt;%&ndash;<span style="background: #0b690d" class="badge">${availableExamsCount}</span>&ndash;%&gt;&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
                                            <%--<c:otherwise>--%>
                                                <%--&lt;%&ndash;<span class="badge">${enrolledCoursesAvailableExamsList.size()}</span>&ndash;%&gt;--%>
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                    </a></li>

                                    <li><a id="retakePill" <c:if test="${filter eq 'retakes'}"> style="background:rgb(51, 122, 183); color: #fff8f8"</c:if> data-toggle="pill" href="#courses" class="actv"><spring:message code="studentHome.retake" />
                                    </a></li>

                                </ul>
                            </div>

                            <%-----------------------------%>
                            <div class="mainBox tab-content">
                                <div id="courses" class="tab-pane <c:if test="${filter ne'exams' && filter ne 'retakes'}">fade in active</c:if>">
                                    <!--Coures list Begins-->
                                    <ul class="courseSearchList list-group">

                                        <c:if test="${empty currentCourseList }">
                                            <i style="margin-left: 45%">No data available... </i>
                                        </c:if>

                                        <c:forEach var = "course" items="${currentCourseList}">
                                            <!--List Item-->
                                            <li class="list-group-item listCard">
                                                <!-- course box content-->
                                                <div class="courseCard">
                                                    <div class="row">
                                                        <!--intro names -->
                                                        <div class="col-sm-8 ">
                                                            <div class="media">
                                                                <div class="media-left">
                                                                    <a href="#">
                                                                        <img src="${url}/resources/student_app_static/images/tempimg.jpg" class="img-rounded" alt="Cinque Terre" width="100" height="70">
                                                                    </a>
                                                                </div>
                                                                <div class="media-body">
                                                                    <h5 class="media-heading"><c:out value="${course.courseName}/${course.courseShortName}"/></h5>
                                                                    <div class="courseTextStyle" >
                                                                        <span class="courseOthersText"><spring:message code="studentPageCourseLables.teacher" />:</span>
                                                                        <a class="courseOthersText" href="${url}/student/view/${course.teacherId}"/>
                                                                        <c:out value="${course.teacherName}"/>
                                                                        </a>

                                                                        <span class="courseOthersText">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="studentPageCourseLables.semester" />:</span><c:out value="${course.semesterCode}"/>
                                                                    </div>
                                                                    <div class="courseTextStyle boxSpace" >
                                                        <span class="courseOthersText">
                                                            <c:out value="${fn:substring(course.courseDescriptionEn,0,118)}"/> . . .
                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-4 courseCardBox3" >
                                                            <div class="courseTextStyle" >
                                                                <div>
                                                    <span class="courseDateTextBox courseOthersText"><spring:message code="studentIndex.enrollmentDeadline"/> <c:out value="${course.endDate}"/></span>
                                                                </div>
                                                                <!--course buttons-->
                                                                <div class="courseBtnBox">
                                                                    <%--<c:choose>--%>
                                                                     <%--   <c:when test="${filter eq 2}">
                                                                            <span style="background: #aa1111;margin-top: 15px;" class="badge badge-pill badge-danger"><spring:message code="system.pending" /></span>
                                                                        </c:when>
                                                                        <c:when test="${filter eq 3}">
                                                                            <span  style="background: #0b690d;margin-top: 15px;" class="badge badge-pill badge-success"><spring:message code="system.enrolled" /></span>
                                                                        </c:when>--%>
                                                                        <%--<c:otherwise>--%>
                                                                            <c:choose>
                                                                                <c:when test="${course.displayType eq 'new'}">
                                                                                    <a class="courseButtonEnroll" onclick="enrollmentRequest(${course.courseId},'${course.courseName}','${course.teacherName}','${course.courseDescriptionEn}','${course.startDate}','${course.endDate}')"><spring:message code="studentPageCourseLables.enroll" /></a>
                                                                                </c:when>
                                                                                <c:when test="${course.displayType eq 'old'}">
                                                                                    <a class="courseButton" href="${url}/student/view/course/${course.courseId}"><spring:message code="studentPageCourseLables.goToCourseBtn" /></a>
                                                                                </c:when>
                                                                            </c:choose>
                                                                        <%--</c:otherwise>--%>
                                                                    <%--</c:choose>--%>

                                                                </div>
                                                                <!--course buttons-->
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <!--List Item-->
                                        </c:forEach>

                                    </ul>
                                    <%--<!--Coures list Ends-->--%>
                                   <%--<c:if test="${indicateShowMoreCourses}"> <div style="text-align: center ; cursor: pointer"><a onclick="showMore()" class="courseOthersText">View More</a></div></c:if>--%>

                                </div>




                                <%--- - - - - - - - - - - - -- - - - -- - - -- - - --%>


                                <div id="exams" class="tab-pane fade <c:if test="${filter eq'exams'}">fade in active</c:if>">

                                <%--    <div class="majorFilter">
                                        &lt;%&ndash;<a href="${url}/admin/major/null" class="majorLable">Options:</a>&nbsp;&ndash;%&gt;
                                        <select autocomplete="off" class="selectMajor" id="examSelect">
                                            <c:if test="${exam eq 'latest'}"><option value="1"><spring:message code="studentIndex.latestAvailableExams"/></option></c:if>
                                            <option value="0"><spring:message code="studentIndex.myAvailableExams"/></option>
                                            <c:if test="${exam ne 'latest'}"><option value="1"><spring:message code="studentIndex.latestAvailableExams"/></option></c:if>

                                        </select>
                                    </div>--%>
                                    <br/>


                                    <!--Coures list Begins-->
                                    <ul class="courseSearchList list-group">
                                        <c:if test="${empty enrolledCoursesAvailableExamsList }">
                                            <i style="margin-left: 45%"><spring:message code="studentIndex.noDataAvailable"/> </i>
                                        </c:if>

                                        <c:forEach var = "availableExams" items="${enrolledCoursesAvailableExamsList}">
                                            <!--List Item-->
                                            <!--List Item-->
                                            <li class="list-group-item listCard">
                                                <!-- course box content-->
                                                <div class="courseCard">
                                                    <div class="row">
                                                        <!--intro names -->
                                                        <div class="col-sm-8 ">
                                                            <div class="media">
                                                                <div class="media-left">
                                                                    <%--<h4><a href="${url}/student/search?${requestScope['javax.servlet.forward.query_string']}&filter=exam"><span class="label label-default"><spring:message code="studentHome.exam" /></span></a> </h4>--%>
                                                                    <h4><span class="label label-default"><spring:message code="studentHome.exam" /></span></h4>
                                                                </div>
                                                                <div class="media-body">
                                                                    <h5 class="media-heading"><c:out value="${availableExams.parentCourseName}/${availableExams.parentCourseShortName}"/></h5>
                                                                    <div class="courseTextStyle" >
                                                                        <span class="courseOthersText"><spring:message code="studentHome.examCode" /> : </span>
                                                                        <a class="courseOthersText" href="${url}/admin/view/user-profile?userId=${availableExams.examName}/>">
                                                                            <c:out value="${availableExams.examName}"/>
                                                                        </a>
                                                                    </div>
                                                                    <div class="courseTextStyle" >
                                                                        <span class="courseOthersText"><spring:message code="studentHome.examDate" /> :</span>
                                                                        <c:out value="${availableExams.dateOfExam}"/>
                                                                    </div>

                                                                </div>
                                                            </div>

                                                        </div>

                                                        <div class="col-sm-4 courseCardBox3" >
                                                            <div class="courseTextStyle" >
                                                                <div>
                                                                    <span class="courseDateTextBox courseOthersText"><span class="label label-danger"><spring:message code="studentIndex.deadline"/></span>&nbsp;:&nbsp;<c:out value="${availableExams.enrolmentCloseDate}"/></span>
                                                                </div>
                                                                <!--course buttons-->
                                                                <div class="courseBtnBox">
                                                                    <a class="courseButton" href="#" onclick="examEnrollmentRequest(${availableExams.id},${availableExams.courseId},'${availableExams.examName}','${availableExams.parentCourseName}','${availableExams.parentCourseShortName}','${availableExams.dateOfExam}','${availableExams.enrolmentStartDate}','${availableExams.enrolmentCloseDate}')"><spring:message code="studentPageCourseLables.enroll" /></a>
                                                                </div>
                                                                <!--course buttons-->
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <!--List Item-->
                                        </c:forEach>


                                    </ul>
                                    <!--Coures list Ends-->

                                </div>

                                <div id="retakes" class="tab-pane <c:if test="${filter eq'retakes'}">fade in active</c:if>">
                                    <!--Coures list Begins-->
                                    <ul class="courseSearchList list-group">

                                        <c:if test="${empty retakeCourseList }">
                                            <i style="margin-left: 45%">No data available... </i>
                                        </c:if>

                                        <c:forEach var = "recourses" items="${retakeCourseList}">
                                            <!--List Item-->
                                            <li class="list-group-item listCard">
                                                <!-- course box content-->
                                                <div class="courseCard">
                                                    <div class="row">
                                                        <!--intro names -->
                                                        <div class="col-sm-8 ">
                                                            <div class="media">
                                                                <div class="media-left">
                                                                    <a href="#">
                                                                        <img src="${url}/resources/student_app_static/images/tempimg.jpg" class="img-rounded" alt="Cinque Terre" width="100" height="70">
                                                                    </a>
                                                                </div>
                                                                <div class="media-body">
                                                                    <h5 class="media-heading"><c:out value="${recourses.courseName}/${recourses.courseShortName}"/></h5>
                                                                    <div class="courseTextStyle" >
                                                                        <span class="courseOthersText"><spring:message code="studentPageCourseLables.teacher" />:</span>
                                                                        <a class="courseOthersText" href="${url}/student/view/${recourses.teacherId}"/>
                                                                        <c:out value="${recourses.teacherName}"/>
                                                                        </a>

                                                                        <span class="courseOthersText">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="studentPageCourseLables.semester" />:</span><c:out value="${recourses.semesterCode}"/>
                                                                    </div>
                                                                    <div class="courseTextStyle boxSpace" >
                                                        <span class="courseOthersText">
                                                            <c:out value="${fn:substring(recourses.courseDescriptionEn,0,118)}"/> . . .
                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-4 courseCardBox3" >
                                                            <div class="courseTextStyle" >
                                                                <div>
                                                                    <span class="courseDateTextBox courseOthersText"><spring:message code="studentIndex.enrollmentDeadline"/> <c:out value="${recourses.enrollmentDeadline}"/></span>
                                                                </div>
                                                                <!--course buttons-->
                                                                <div class="courseBtnBox">

                                                                    <a class="courseButtonEnroll"
                                                                       onclick="enrollmentRequest(${recourses.courseId},'${recourses.courseName}','${recourses.teacherName}','${recourses.courseDescriptionEn}','${recourses.coursEnrollmentDate}','${recourses.enrollmentDeadline}')"><spring:message
                                                                            code="studentPageCourseLables.enroll"/></a>

                                                                </div>
                                                                <!--course buttons-->
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <!--List Item-->
                                        </c:forEach>

                                    </ul>
                                    <%--<!--Coures list Ends-->--%>
                                    <%--<c:if test="${indicateShowMoreCourses}"> <div style="text-align: center ; cursor: pointer"><a onclick="showMore()" class="courseOthersText">View More</a></div></c:if>--%>

                                </div>

                            </div>
                            <%--------------------------------%>

                        </div>
                    </div>

                </div>

       
            </div>


        </div>

    </div>

<%--footer--%>
    <%--course enrollment model --%>
    <div class="modal fade" id="enrollModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 id="courseN" class="modal-title"> </h4>
                </div>
                <h5 class="modelHead"><spring:message code="studentIndex.courseEnrollment"/> </h5>
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
                        <spring:message code="manageCourse2.enrolmentDeadline"/> : <span id="to"></span> </span>
                </div>
                <div class="modal-footer">
                    <div class="btn-group btn-group-justified">
                        <a href="#" id="makeRequest" onclick="makeEnrollmentRequest(this.value)" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#confirmationModal" class="btn btn-primary"><spring:message code="studentIndex.requestEnroll"/> </a>
                        <a href="#" class="btn btn-primary" data-dismiss="modal" ><spring:message code="main.cancel"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--&lt;%&ndash;CONFRIMATION MODAL &ndash;%&gt;--%>
    <%--<div class="confirmModal">--%>
    <%--<div class="modal fade" id="confirmationModal" role="dialog">--%>
        <%--<div class="modal-dialog modal-sm">--%>
            <%--<div class="modal-content">--%>
                <%--<div class="modal-header">--%>
                    <%--<button type="button" class="close" data-dismiss="modal">&times;</button>--%>
                    <%--<h4 class="modal-title confirmTitle">Confirm Enrollment Request </h4>--%>
                <%--</div>--%>
                <%--<div class="modal-body">--%>
                    <%--<div class="btn-group btn-group-lg btn-group-justified">--%>
                        <%--<button style="width: 50%" type="button"  id="makeRequest" onclick="makeEnrollmentRequest(this.value)" class="btn btn-success">Enroll</button>--%>
                        <%--<button style="width: 50%" type="button" data-dismiss="modal" class="btn btn-danger">Cancel</button>--%>
                    <%--</div>--%>
                <%--</div>--%>

            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

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

    <%--exam enrollment modal--%>
    <div class="modal fade" id="examEnrollModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">

                <div class="modal-header">
                    <h4  class="modal-title courseOthersText"><span id="examNameE"></span><button type="button" class="close" data-dismiss="modal">&times;</button></h4>
                </div>
                <h5 class="modelHead"><spring:message code="studentIndex.examEnrollment"/> </h5>
                <div class="modal-body">
                    <div class="courseTextStyle boxSpace" >
                        <span id="courseE" class="courseOthersText"></span><br/><br/>
                        <span class="courseOthersText" id="descripE">

                        </span>
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

    <%--<div class="confirmModal">--%>
        <%--<div class="modal fade" id="confirmationModalExams" role="dialog">--%>
            <%--<div class="modal-dialog modal-sm">--%>
                <%--<div class="modal-content">--%>
                    <%--<div class="modal-header">--%>
                        <%--<button type="button" class="close" data-dismiss="modal">&times;</button>--%>
                        <%--<h4 class="modal-title confirmTitle">Confirm Enrollment Request </h4>--%>
                    <%--</div>--%>
                    <%--<div class="modal-body">--%>
                        <%--<div class="btn-group btn-group-lg btn-group-justified">--%>
                            <%--<button style="width: 50%" type="button"   class="btn btn-success">Enroll</button>--%>
                            <%--<button style="width: 50%" type="button" data-dismiss="modal" class="btn btn-danger">Cancel</button>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

    <script src="${url}/resources/main/moment/moment.js"></script>
    <script src="${url}/resources/main/fullcalendar/dist/fullcalendar.min.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/zh-cn.js "></script>
    <script src="${url}/resources/main/fullcalendar/dist/locale/en-gb.js "></script>
<script>

    <%--,${course.courseName},${course.teacherName},${course.courseDescriptionEn},${course.startDate},${course.endDate}--%>
//    ,

    function showNotification(title,notification) {
        $('#eventModal').modal('toggle');
        $('#modalTitle').text(title);
        $('#modalBody').text(notification);
    }
    function enrollmentRequest(courseId ,courseName,teacherName,courseDescr,from ,dateTo){
        $('#enrollModal').modal('toggle');
        $('#courseN').text(courseName);
        $('#descrip').text(courseDescr);
        $('#teacherN').text(teacherName);
        $('#from').text(from );
        $('#to').text(dateTo);
        $('#makeRequest').val(courseId);
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

    function makeEnrollmentRequest(courseId){

        var r = confirm('<spring:message code="studentIndex.msg11"/>');
        if(r == true){
            $.ajax({
                url:'${url}/student/make-course-enrollment/'+courseId,
                type:'post',
                success:function(data){
                    if(data === '200'){
                        alert('<spring:message code="studentIndex.msg1"/>')
                        $('#enrollModal').modal('toggle');
                        $('#confirmationModal').modal('toggle');
                        $(".alert-success").css("display", "block");
                        document.location.reload(true);
                    }else{
                        alert('<spring:message code="studentIndex.msg2"/>')
                    }

                },
                error : function(data){
                    alert('<spring:message code="main.msgError" />');
                }
            });

        }else{
            //do nothing
        }
    }

    function makeExamEnrollmentRequest(examId) {
        var r = confirm('<spring:message code="studentIndex.msg3"/>');
        if(r == true){
            $.ajax({
                <%--url:'${url}/student/exam/request-enrollment/'+$('#reqE').val()+'/'+examId,--%>
                url:'${url}/student/exam/request-enrollment/' + examId,
                type:'post',
                success:function(data){
                     switch(data) {
                        case "410":
                            alert('<spring:message code="studentIndex.msg4"/>');
                            break;
                        case "420":
                            alert('<spring:message code="studentIndex.msg5"/>');
                            break;
                        case "430":
                            alert('<spring:message code="studentIndex.msg6"/>');
                            break;
                        case "440":
                            alert('<spring:message code="studentIndex.msg7"/>');
                            break;
                        case "210":
                            alert('<spring:message code="studentIndex.msg8"/>');
                            break;
                        case "200":
                        {
                            alert('<spring:message code="studentIndex.msg9"/>');
                            document.location.reload(true);
                            break;
                        }
                        default:
                            alert('<spring:message code="studentIndex.msg10"/>');
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


    $(document).ready(function() {

        // page is now ready, initialize the calendar...
        <%--console.log(${calendarEvents});--%>

        $('#calendar').fullCalendar({
            editable: true,
            eventLimit: true,
            events:${calendarEvents},
            eventClick:  function(event, jsEvent, view , id) {
                $('#modalTitle').html(event.title);
                $('#modalBody').html(event.description);
                $('#eventUrl').attr('href',event.url);
                $('#eventModal').modal('toggle');
                return false;
            }
        })
    });


    function showMore() {
        $.ajax({
            url:'${url}/getMoreCourse',
            type:'get',
            success:function(data){
                alert('success');
                $('#cName').text(''+data.courseName);
                console.log(data);
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    }

    function searching() {
//        alert("test!");
        var srchText = $("#srch-term").val();

        <c:if test="${filter ne 'exams'}">
        window.location.href = '${url}/student/home2?search='+ srchText;
        </c:if>
        <c:if test="${filter eq 'exams'}">
        window.location.href = '${url}/student/home2?filter=exams&search='+ srchText;
        </c:if>
    }



    $("#examPill").click(function(){
        <c:if test="${filter ne 'exams'}">
        window.location.href = '${url}/student/home?filter=exams';
        </c:if>
    });


    $("#coursePill").click(function(){
        localStorage.setItem('selectVal', '0' );
        <c:if test="${filter ne 'courses'}">
        window.location.href = '${url}/student/home';
        </c:if>
    });

    $("#retakePill").click(function(){
        localStorage.setItem('selectVal', '0' );
        <c:if test="${filter ne 'retakes'}">
        window.location.href = '${url}/student/home?filter=retakes';
        </c:if>
    });


    $("#examSelect").change(function(){
        var thisvalue = $(this).find("option:selected").val();
        if(thisvalue === '0'){
            window.location.href = '${url}/student/home?filter=exams';
        }else if(thisvalue === '1'){
            window.location.href = '${url}/student/home?filter=exams&exam=latest';
        }

    });

</script>


</body>

</html>