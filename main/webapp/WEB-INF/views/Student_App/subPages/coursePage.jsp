<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 12/09/2017
  Time: 07:45
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>


<head>
    <title><spring:message code="studentCoursePage.viewCourse"/> </title>
    <link href="${url}/resources/app_admin_static/css/subPages/coursePage.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>

<body >


<!--Content Begins  -->
<div class="content">
    <div class="wrapper row">
        <ul class="breadcrumbs">
            <li class=""><a href="${url}/student/my-courses/Courses" class="icon-home"><spring:message code="studentMyCourses.myCourses"/></a></li>
            <%--<li><a href="#"></a></li>--%>
            <%--<li><a href="#">Second Level Interior Page</a></li>--%>
            <%--<li><a href="#">Third Level Interior Page</a></li>--%>
            <li class="last active"><a > ${courseDetails.courseShortName}</a></li>
        </ul>
    </div>



    <!--MAIN CARD CONTAINER -->
    <div class="">



        <!--Small cards-->
        <div class="col-sm-3 smallParent">

            <%--$$$$$$$--%>
            <div class="notifcationContainer bigCardContainer">
                <div class="sectionContainerHeaderBox">
                    <h4><spring:message code="studentEventDetails.upcomingEvents"/> &nbsp;<a onClick=" addCourseEvent()"></a></h4>
                </div>
                <c:if test="${empty courseEventList}">
                    <div style="text-align: center"><i><spring:message code="coursePage.noUpcomingEvents"/></i></div>
                </c:if>
                <ul class="eventList elBolck">
                    <c:forEach varStatus="index"  var="eventList" items="${courseEventList}">
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

                <c:if test="${fn:length(courseEventList) gt 2 }">
                    <span class="viewMore"><spring:message code="studentCoursePage.viewMore"/></span>
                </c:if>
            </div>










        <%--$$$$$$$--%>
            <div class="sectionContainer bigCardContainer">
                <div class="sectionContainerHeaderBox">
                    <h4><spring:message code="system.currentCourses"/></h4>
                </div>

                <div>
                    <c:if test="${empty lessonContentList}">
                        <div style="text-align: center"><i><spring:message code="coursePage.noLessonsUploaded"/></i></div>
                    </c:if>
                    <ul>
                        <c:forEach var = "lessonContentIndex" items="${lessonContentList}">
                            <li>
                                <a href="#${lessonContentIndex.lessonId}"><button class="button topicBox"><c:out value="${lessonContentIndex.lessonName}"/></button></a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>


            </div>

        </div>
        <!--Small cards-->


        <div class="col-sm-9 courseContainer">
            <h3 class="title">${courseDetails.courseName}</h3>
            <%--COURSE DESCRIPTION CONTAINER --%>
            <%--<a class="courseButton"  data-toggle="modal" data-target="#importCourseExcel" href="#">Edit Course</a>--%>


            <%-------------------------------------------------------------%>
            <div class=" courseHeaderBox">


                <%--<div class="courseSettingsBox">--%>
                    <%--<a href="${url}/admin/manage-courses/add?courseId=${aboutCourse.rowsByIndex[0][0]}">Edit Course</a>--%>
                <%--</div>--%>
                <div>
                    <span class="introText"><spring:message code="studentCoursePage.aboutThisCourse"/></span>
                    <div id="aboutTextBox">
                        <p>
                            ${courseDetails.courseDescriptionEn}
                        </p>
                    </div>
                    <a id="more" class="hideText" href="#"><spring:message code="coursePage.readMore"/> </a>
                    <a id="less"  class="hideText" href="#"><spring:message code="extra.showLess"/> </a>
                    <br/><br/>
                    <span class="introText"><spring:message code="courseForm.teacher"/> : </span><span><a href="${url}/student/view/${courseDetails.teacherId}">${courseDetails.teacherName}</a></span><br/>
                    <%--<span>Credits:4</span><br/>--%>
                    <%--<span>Course semester: Spring 2017</span><br/>--%>
                    <%--<span>Course Type: All</span><br/>--%>
                    <span class="introText" ><spring:message code="courseForm.contentLanguage"/> : </span><span><spring:message code="coursePage.english"/> </span><br/>

                </div>
                    <span class="introText dates"><spring:message code="courseForm.startDate"/>: &nbsp;${courseDetails.startDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br/>


                    <div style="width: 80%; margin: 0px auto">
                        <div class="btn-group btn-group-justified" role="group" aria-label="...">
                            <div class="btn-group" role="group">
                                <button id="toGrades" type="button" class="btn btn-default"><a href="#"><spring:message code="coursePage.gradeReport"/> </a></button>
                            </div>
                            <div class="btn-group" role="group">
                                <button onclick="toExams()" type="button" class="btn btn-default"><a href="#"><spring:message code="coursePage.exams"/> </a></button>
                            </div>
                            <%--<div class="btn-group" role="group">--%>
                                <%--<button type="button" class="btn btn-default"><a href="${url}/admin/grades/grades-details">Un-Enroll </a></button>--%>
                            <%--</div>--%>
                        </div>
                    </div>


            </div>
            <%-----------------------------------------------------%>
            <%--COURSE DESCRIPTION CONTAINER --%>



            <%--COURSE DETAILS CONTAINER --%>
            <div class=" bigCardContainer courseSections">
                <c:if test="${empty lessonContentList}">
                    <div style="text-align: center"><i><spring:message code="coursePage.noLessonsUploaded"/></i></div>
                </c:if>
                <ul>
                    <c:forEach var = "lessonContent" items="${lessonContentList}">
                        <li id="${lessonContent.lessonId}">
                            <div class="sectionContent">

                                <span  class="sectionTitle"><c:out value="${lessonContent.lessonName}"/></span><br/>
                                <div class="contentInfo">
                                    <p>
                                        <c:out value="${lessonContent.description}" escapeXml="false"/>
                                    </p>
                                    <ul>

                                        <c:forEach var = "lessonUploads" items="${lessonUploadsList}">
                                            <c:choose>
                                                <c:when test="${lessonContent.lessonId eq lessonUploads.lessonId}">
                                                    <li>
                                                        <a href="${url}/download/docs/${lessonUploads.uploadUrl}">
                                                            <div class="icon orange">
                                                                <span class="doc-icon ptt">⧉</span>
                                                                <span class="doc-type">PTT</span>
                                                            </div>
                                                            <div class="downloadText"><c:out value="${lessonUploads.uploadName}"/></div>
                                                        </a>
                                                    </li>
                                                </c:when>
                                                <%--<c:otherwise>--%>
                                                <%--dont show--%>
                                                <%--</c:otherwise>--%>
                                            </c:choose>


                                        </c:forEach>

                                            <%--<li>--%>
                                            <%--<div class="icon red">--%>
                                            <%--<span class="doc-icon pdf">☷</span>--%>
                                            <%--<span class="doc-type">PDF</span>--%>
                                            <%--</div>--%>
                                            <%--<div class="downloadText"> <span >Chapter 1</span></div>--%>

                                            <%--</li>--%>

                                    </ul>
                                </div>

                            </div>
                        </li>
                    </c:forEach>

                </ul>
                <%--<div class="sectionAddBox">--%>
                    <%--<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>--%>
                    <%--<div>Add Topic</div>--%>

                <%--</div>--%>
            </div>
            <%--COURSE DETAILS CONTAINER--%>
        </div>

        <!--Big cards-->

    </div>
    <!--MAIN CARD CONTAINER -->


</div>
<!--Content Ends  -->


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

    // Script to open and close sidebar
    function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
        document.getElementById("myOverlay").style.display = "block";
    }

    function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
        document.getElementById("myOverlay").style.display = "none";
    }

    // Modal Image Gallery
    function onClick(element) {
        document.getElementById("img01").src = element.src;
        document.getElementById("modal01").style.display = "block";
        var captionText = document.getElementById("caption");
        captionText.innerHTML = element.alt;
    }

</script>


<script type="text/javascript">

    function showNotification(title,notification) {
        $('#eventModal').modal('toggle');
        $('#modalTitle').text(title);
        $('#modalBody').text(notification);
    }

    $(document).ready(function(){
        $('#less').hide();

        //HIDE SETTINGS ON PAGE LOAD
        $('#settingsList').hide();

        //SCROLL BAR
        //http://rocha.la/jQuery-slimScroll

        $('.notifcationContainer').slimScroll({
            position: 'right',
            height: '300px',
            alwaysVisible: false,
            railVisible: false,
        });





    });

    //HIDE AND SHOW SETTINGS LIST
    $("#settings").click(function(){
        $("#settingsList").toggle()
    });
    //HIDE AND SHOW SETTINGS LIST

    $('#more').click(function (e) {
        e.stopPropagation();
        $('#aboutTextBox').css({
            'height':'auto'
        });
        $('#more').hide();
        $("#less").show();

    });

    $('#less').click(function (e) {
        e.stopPropagation();
        $('#aboutTextBox').css({
            'height':'50px'
        });
        $('#less').hide();
        $("#more").show();

    });


    $(document).click(function () {
        $('#aboutTextBox').css({
            'height':'50px'
        });

    });


    function toExams() {
        window.location.href='${url}/student/course-exam/${courseId}';
    }

    $('#toGrades').click(function(){
        window.location.href='${url}/student/grades/full-report/${courseId}';
    });


</script>

</body>


</html>


