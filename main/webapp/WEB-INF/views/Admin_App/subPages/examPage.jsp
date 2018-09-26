<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 20/08/2017
  Time: 13:53
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
    <title><spring:message code="examPage.examPage"/></title>
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/examPage.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">
    <h1>Admin Search </h1>

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <!--Big cards-->
        <div class="col-sm-8 mainBox">
            <h4><spring:message code="studentPageTitle.home" /></h4>
            <div class="card bigCardContainer">

                <h2>Exam Code</h2>
                <h2>Total Enrolled Students: 11</h2>
                <h2>Course Name</h2>
                <h2>Enrolment DeadLine</h2>
                <h2>start date</h2>

                <button>Cancel Exam</button>
                <button>Save Changes</button>

            </div>

            <%---------------------------------%>

                <div class="card bigCardContainer">
                    <h4>Manage Exams</h4>

                    <!--SEARCH CONTAINER -->
                    <div class="searchingBox">
                        <div class="form-group pull-right">
                            <input type="text" class="search form-control" placeholder="Search Users">
                        </div>
                    </div>
                    <%--/SEARCH CONTAINER--%>

                    <!--NAV TABS -->
                    <ul class="nav nav-tabs tabSelectList">

                        <li class="active"><a data-toggle="tab" href="#home">Exam Enrolment List</a></li>
                        <li><a data-toggle="tab" href="#menu1">Exam Grades</a></li>

                    </ul>
                    <%--/NAV TABS--%>

                    <!--TAB CONTENT-->
                    <div class="tab-content">

                        <%--MANAGE USER TABLE --%>
                        <div id="home" class="tab-pane fade in active">

                            <div class="table-responsive">

                                <table class="table table-bordered table-hover">

                                    <div class="select-style">
                                        <select>
                                            <option value="volvo">Student</option>
                                            <option value="saab">Teacher</option>
                                            <option value="mercedes">All</option>
                                        </select>
                                    </div>

                                    <caption class="">
                                        <span>Showing 1 to 8 of 8 entries</span>
                                        <!--course buttons-->
                                        <a class="courseButton" href="#">Prev</a>
                                        <a class="courseButton" href="#">Next</a>
                                        <!--course buttons-->
                                    </caption>
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Student Name</th>
                                        <th>Student Id</th>
                                        <th>Degree-Type</th>
                                        <th>Enrolment Date</th>
                                        <th>Exam Attempts</th>
                                    </tr>
                                    </thead>

                                    <tbody>

                                    <c:forEach var = "row" items="${result.rows}">

                                        <tr>
                                            <td>1</td>
                                            <td>Schuller Tezrolani</td>
                                            <td>9130212018</td>
                                            <td>Double-degree</td>
                                            <td>2017-09-11</td>
                                            <td>1</td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>

                                </table>

                            </div><!--end of .table-responsive-->

                        </div>
                        <%--/TAB CONTENT--%>


                            <%----  ---   ---  ----%>


                        <!--MANAGE CLASSES TAB-->
                        <div id="menu1" class="tab-pane fade">

                           <%-------%>
                            <%--MANAGE USER TABLE --%>
                            <div id="home" class="tab-pane fade in active">

                                <div class="table-responsive">

                                    <table class="table table-bordered table-hover">

                                        <div class="select-style">
                                            <select>
                                                <option value="volvo">Student</option>
                                                <option value="saab">Teacher</option>
                                                <option value="mercedes">All</option>
                                            </select>
                                        </div>

                                        <caption class="">
                                            <span>Showing 1 to 8 of 8 entries</span>
                                            <!--course buttons-->
                                            <a class="courseButton" href="#">Prev</a>
                                            <a class="courseButton" href="#">Next</a>
                                            <!--course buttons-->
                                        </caption>
                                        <thead>
                                        <tr>
                                            <th>Exam Id</th>
                                            <th>Course Name</th>
                                            <th>Enrolment Deadline</th>
                                            <th>Start Date</th>
                                            <th>Participants</th>

                                        </tr>
                                        </thead>

                                        <tbody>

                                        <c:forEach var = "row" items="${result.rows}">

                                            <tr>
                                                <td><a href="${url}/admin/view/exam-page"><c:out value="${row.exam_name}"/></a></td>
                                                <td><a href="${url}/admin/manage-courses/course-exam"><c:out value="${row.course_name}"/></a></td>
                                                <td><c:out value="${row.enrolment_close_date}"/></td>
                                                <td><c:out value="${row.date_of_exam}"/></td>
                                                <td><a href="${url}/admin/manage-exams/enrolmentList">0</a></td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>

                                    </table>

                                </div><!--end of .table-responsive-->
                            <%----------%>
                        </div>
                        </div>
                        <%--/MANAGE CLASSES TAB--%>

                    </div>
                    <%--Manage Exams --%>
                </div>
            <%----------------------------------%>

        </div>
        <!--Big cards-->


        <%---- --  -- --- -- ----%>

        <!--Small cards-->
        <div class="col-sm-4">
            <div class="card bigCardContainer">


            </div>

        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->



</div>
<!--Content Ends  -->

</body>

</html>