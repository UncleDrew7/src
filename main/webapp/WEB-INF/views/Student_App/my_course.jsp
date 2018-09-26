<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="dec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<html lang="en">

<head>
    <title><spring:message code="studentMyCourses.myCourses"/></title>
    <link href="${url}/resources/student_app_static/css/my_course.css" rel="stylesheet">
    <%--<script src="//cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>--%>
</head>

<body>

<div>

        <!-- Tab panes -->
        <div class="content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <div  id="users" class="row mainContainer">

                    <!--Small cards-->
                    <div class="col-sm-3">
                        <br/><br/>
                        <div class="card  filterBox ">
                            <div>
                                <h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.filterCoures" /></h4>
                            </div>
                            <div>
                                <div style="margin-top: -11px" class="list-group">
                                    <a href="${url}/student/my-courses/Courses" class="list-group-item <c:if test='${object eq "Courses"}'>active</c:if>"><spring:message code="studentMyCourses.courses"/><span class="badge"><c:if test="${countOfUpcomingEvents ne 0}">${countOfUpcomingEvents}</c:if></span></a>
                                    <a href="${url}/student/my-courses/Exam" class="list-group-item <c:if test='${object eq "Exam"}'>active</c:if>"><spring:message code="studentMyCourses.exams"/></a>
                                </div>
                                <%--<ul class="courseFilterList">--%>
                                <%--<li></li>--%>
                                <%--<li></li>--%>
                                <%--<li><spring:message code="studentPageCourseLables.unEnrolledCourses" /></li>--%>
                                <%--<li></li>--%>
                                <%--</ul>--%>
                            </div>
                        </div>

                        <div class="card">
                            <div>
                                <h4 class="titleTextBoxHeaders"><spring:message code="sharedPageLable.semesterCategory" /></h4>
                            </div>
                            <div style="margin-top: -11px" class="list-group">
                                <a href="${url}/student/my-courses/${object}" class="list-group-item <c:if test='${ null eq  semesterId }'>active</c:if>">
                                    <spring:message code="extra.all"/>
                                </a>
                                <c:forEach var = "sList" items="${semesterList}">
                                    <a href="${url}/student/my-courses/${object}?semester=${sList.semesterId}" class="list-group-item <c:if test='${sList.semesterId eq  semesterId }'>active</c:if>">
                                        <c:out value="${sList.semesterCode}"/>
                                    </a>
                                </c:forEach>

                            </div>
                        </div>


                    </div>
                    <!--Small cards-->
                    <div class="col-sm-8 ">



                        <!--Coures list Begins-->
                        <ul class="courseList list-group">
                            <h4><spring:message code="studentPageTitle.myCourses" /></h4>

                            <div class="searchBox" align="center">
                            <%--    <div class="input-group">
                                    <input style="width:70%; float: right" type="text" class="form-control search" aria-label="...">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><spring:message code="enrollStudent.filters" /> <span class="caret"></span></button>
                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li><a class="sort" data-sort="name" href="#"><spring:message code="studentMyCourses.sortByName"/></a></li>
                                            <li><a class="sort" data-sort="date" href="#"><spring:message code="studentMyCourses.sortBydate"/></a></li>
                                            <li><a class="sort" data-sort="teacher" href="#"><spring:message code="studentMyCourses.sortByTeacher"/></a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                </div><!-- /input-group -->
                            <%--</div>&ndash;%&gt;--%>
                            <%--<div class="formWrapperContainer">--%>
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

                            <c:choose>
                                <c:when test="${object eq 'Courses'}">
                                    <c:forEach var = "myCourses" items="${studentMyCourseList}">
                                        <!--List Item-->
                                        <li class="list-group-item listCard">
                                            <!-- course box content-->
                                            <div class="courseCard">
                                                <div class="row">
                                                    <!--intro names -->
                                                    <div class="col-sm-4 ">
                                                        <div>
                                                            <div class="courseTextStyle" >
                                                                <spring:message code="courseForm.courseId"/>:  <c:out value="${myCourses.courseName}"/>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="enrollmentList.courseType"/>: <c:out value="${myCourses.courseType}"/></span>
                                                            </div>
                                                            <%--<div class="courseTextStyle" >--%>
                                                                <%--<span class="courseOthersText date">Semester:  <c:out value="${myCourses.semesterCode}"/></span>--%>
                                                            <%--</div>--%>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText teacher"><spring:message code="studentPageCourseLables.teacher" />:</span> <a class="courseOthersText" href="${url}/student/view/${myCourses.teacherId}">
                                                                <c:out value="${myCourses.teacherName}"/>
                                                            </a>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="enrollmentList.deadline"/>:  <c:out value="${myCourses.enrolmentDeadline}"/></span>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="manageUsers2.semester"/>:  <c:out value="${myCourses.semesterCode}"/></span>
                                                            </div>


                                                        </div>

                                                    </div>

                                                    <!--course name -->
                                                    <div class="col-sm-5 courseCardBox3" >
                                                        <div class="courseNameTextBox name">
                                                            <span><c:out value="${myCourses.courseShortName}"/></span>
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-3 courseCardBox3" >

                                                        <!--course buttons-->
                                                        <div class="enrollmentBox">
                                                            <a class="courseButton" href="${url}/student/view/course/${myCourses.childCourseId}"><spring:message code="studentPageCourseLables.goToCourseBtn" /></a>
                                                            <br/>
                                                            <a href="#" class="courseOthersText aText"  onclick="unEnrollFromCourse('${myCourses.childCourseId}')">Un-enroll</a><br/>



                                                        </div>
                                                        <br/>


                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <!--List Item-->
                                    </c:forEach>
                                    <c:if test="${empty studentMyCourseList }">
                                        <li class="list-group-item listCard">
                                            <!-- course box content-->
                                            <div class="courseCard">
                                                <div class="row">
                                                    <!--intro names -->

                                                    <!--course name -->
                                                    <div class="col-sm-4 courseCardBox3" >
                                                        <div class="courseNameTextBox">
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-4 courseCardBox3" >

                                                        <!--course buttons-->
                                                        <div class="noData">
                                                            <span><spring:message code="studentMyCourses.noDataToDisplay"/></span>
                                                        </div>
                                                        <br/>


                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                    </c:if>

                                </c:when>

                                <c:when test="${object eq 'Exam'}">
                                    <c:forEach var = "myExams" items="${studentExamsList}">
                                        <li class="list-group-item listCard">
                                            <!-- course box content-->
                                            <div class="courseCard">
                                                <div class="row">
                                                    <!--intro names -->
                                                    <div class="col-sm-4 ">
                                                        <div class="examBox">
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="courseForm.courseId"/> :  <c:out value="${myExams.parentCourseName}"/></span>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <a class="courseOthersText" href="#Category">
                                                                    <spring:message code="courseForm.courseName"/>: <c:out value="${myExams.examName}"/>
                                                                </a>
                                                            </div>

                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="enrollmentList.deadline"/>:  <c:out value="${myExams.enrolmentCloseDate}"/></span>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="studentHome.examDate"/>:  <c:out value="${myExams.dateOfExam}"/></span>
                                                            </div>
                                                            <div class="courseTextStyle" >
                                                                <span class="courseOthersText date"><spring:message code="manageUsers2.semester"/>:  <c:out value="${myExams.semesterCode}"/></span>
                                                            </div>


                                                        </div>

                                                    </div>

                                                    <!--course name -->
                                                    <div class="col-sm-5 courseCardBox3" >
                                                        <div class="courseNameTextBox name">
                                                            <span><c:out value="${myExams.examName}"/></span>
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-3 courseCardBox3" >

                                                        <!--course buttons-->
                                                        <div class="enrollmentBox">
                                                            <a class="courseButton" href="${url}/student/course-exam/${myExams.id}"><spring:message code="studentMyCourses.goToExam"/></a>
                                                            <br/>
                                                            <a href="#" class="courseOthersText aText"  onclick="unEnrollFromExam('${myExams.id}')">Un-enroll</a><br/>

                                                        </div>
                                                        <br/>


                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${empty studentExamsList}">
                                        <li class="list-group-item listCard">
                                            <!-- course box content-->
                                            <div class="courseCard">
                                                <div class="row">
                                                    <!--intro names -->

                                                    <!--course name -->
                                                    <div class="col-sm-4 courseCardBox3" >
                                                        <div class="courseNameTextBox">
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-4 courseCardBox3" >

                                                        <!--course buttons-->
                                                        <div class="noData">
                                                            <span><spring:message code="studentMyCourses.noDataToDisplay"/></span>
                                                        </div>
                                                        <br/>


                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                    </c:if>
                                </c:when>
                            </c:choose>



                        </ul>
                        <!--Coures list Ends-->
                    </div>


                </div>


            </div>


        </div>

    </div>

<script>

//    var options = {
//        valueNames: [ 'name','date','teacher' ]
//    };

//    var userList = new List('users', options);

<%--function searching() {--%>
<%--//        alert("test!");--%>
    <%--var srchText = $("#srch-term").val();--%>

    <%--<c:if test="${filter ne 'exams'}">--%>
    <%--window.location.href = '${url}/student/home2?search='+ srchText;--%>
    <%--</c:if>--%>
    <%--<c:if test="${filter eq 'exams'}">--%>
    <%--window.location.href = '${url}/student/home2?filter=exams&search='+ srchText;--%>
    <%--</c:if>--%>
<%--}--%>

    function searching() {
    //        alert("test!");
        var srchText = $("#srch-term").val();

        <c:if test="${object ne 'Exam'}">
        window.location.href = '${url}/student/my-courses-search/Courses?search=' + srchText;
        </c:if>
        <c:if test="${object eq 'Exam'}">
        window.location.href = '${url}/student/my-courses-search/Exam?search=' + srchText
        </c:if>
    }

    function unEnrollFromCourse(id) {
        var r = confirm('Are You Sure you want to uneroll from this course ');
        if(r == true){
            $.ajax({
                url:'${url}/student/course/un-enroll/',
                type:'post',
                data: {'childCourseId':id},
                success:function(data){
                    if(data === '200'){
                        alert('You have sucessfully un- enrolled from course')
                        document.location.reload(true);
                    }else{
                        alert('Error Course has began can not un-enroll Course ')
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

    function unEnrollFromExam(id) {

        var r = confirm('Are You Sure you want to uneroll from this exam ');
        if(r == true){
            $.ajax({
                url:'${url}/student/exam/un-enroll/',
                type:'post',
                data: {'examId':id},
                success:function(data){
                    if(data === '200'){
                        alert('You have sucessfully un- enrolled from exam');
                        document.location.reload(true);
                    }else{
                        alert('Request Failed Exam un-enrollment period ended ');
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
</script>



</body>

</html>