<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 05/10/2017
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 20/08/2017
  Time: 13:49
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
    <title><spring:message code="semesterCourseList.text1"/></title>
    <link href="${url}/resources/app_admin_static/css/manage_courses.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/manage_users.css" rel="stylesheet">
    <link href="${url}/resources/student_app_static/css/home.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/courseEnrolmentList.css" rel="stylesheet">
    <link href="${url}/resources/main/dataTables/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="${url}/resources/main/dataTables/datatables.min.js"></script>
</head>

<body>

<%--navs--%>

<!-- Tab panes -->
<div class="content">
    <c:choose >
        <c:when test="${param.ng eq 2}">
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/view/course/${courseId}" class="icon-home"><c:out value="${courseDetails.courseShortName}"/></a></li>
                    <li class="last active"><a > <spring:message code="enrollmentList.enrolmentList"/></a></li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div class="wrapper row">
                <ul class="breadcrumbs">
                    <li class="first"><a href="${url}/admin/manage-courses/courses" class="icon-home"><spring:message code="semesterCourseList.manageSemesters"/></a></li>
                    <li class="last active"><a > <spring:message code="semesterCourseList.text1"/></a></li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>



    <div class="row mainContainer">



        <!--Small cards-->
        <div class="col-sm-3">
            <div class="upcomingEventsContainer ">
                <div>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageSemesters.semesterId"/>: <b>${semesterId}</b></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="semesterForm.semesterCode"/>: <b><c:out value="${singleSemester.semesterCode}"/></b></h4>
                    <h4 class="titleTextBoxHeaders"><spring:message code="manageSemesters.numberOfCourse"/> : <b><c:out value="${numberOfCourse}"/></b></h4>
                </div>
                <h4 class="titleTextBoxHeaders"><spring:message code="semesterForm.startDate"/> : <b><c:out value="${singleSemester.startDate}"/></b></h4>
                <h4 class="titleTextBoxHeaders"><spring:message code="courseForm.endDate"/> : <b><c:out value="${singleSemester.endDate}"/></b></h4>
                <!--TimeLine container-->
                <div class="timelineWrapperContainer">


                </div>
                <!--TimeLine Container-->

            </div>


        </div>
        <!--Small cards-->
        <%----------------%>
        <div class="col-sm-9 ">



            <!--MAIN CARD CONTAINER -->
            <div class=" card mainCardContainer">


                <div class="majorFilter">
                    <a href="${url}/admin/major/null" class="majorLable"><spring:message code="manageCourse2.major"/>:</a>&nbsp;
                    <select  autocomplete="off" id="majorSelect" class="selectMajor" >
                        <c:if test="${currentMajor ne null}"><option value="">${currentMajor.majorName}</option></c:if>
                        <option value=""><spring:message code="manageUsers2.default"/></option>
                        <c:forEach var = "major" items="${majorSelectList}" varStatus="index">
                            <c:if test="${major.majorId ne currentMajor.majorId}">
                                <option value="${major.majorId}"><c:out value="${major.majorName}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>


            <%--<!--SEARCH CONTAINER -->--%>
                <%--<div class="searchingBox">--%>
                    <%--<div class="form-group pull-right">--%>
                        <%--<a class="courseButton" href="${url}/admin/manage-courses/enroll-student/${courseId}?ng=2">Enroll Student</a>&nbsp;&nbsp;&nbsp;<a class="courseButton" href="${url}/admin/manage-courses/add">Export List</a>--%>
                        <%--&lt;%&ndash;<input type="text" class="search form-control" placeholder="Search Course">&ndash;%&gt;--%>
                    <%--</div>--%>

                    <%--&lt;%&ndash;TEACHER FILE IMPOORT MODEL FORM &ndash;%&gt;--%>
                    <%--&lt;%&ndash;<button type="button" class="btn btn-success" data-toggle="modal" data-target="#popUpWindow">Open Log In Window</button>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="modal fade" id="importCourseExcel">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<div class="modal-dialog">&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<div class="modal-content">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<!--Header-->&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div class="modal-header">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<button type="button" class="close" data-dismiss="modal">&times</button>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<h3 class="modal-title">Log in</h3>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<!--Body (form)-->&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div class="modal-body">&ndash;%&gt;--%>

                                    <%--&lt;%&ndash;&lt;%&ndash;FILE FORM &ndash;%&gt;&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<form:form action="${url}/manage-courses/add-course-excel" method="post"&ndash;%&gt;--%>
                                               <%--&lt;%&ndash;enctype="multipart/form-data">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<div> Course Excel File 2007:</div>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<input name="courseExcelListFile" type="file">&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;<input type="submit" value="Import Excel File">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;</form:form>&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;&lt;%&ndash;FILE FORM &ndash;%&gt;&ndash;%&gt;--%>

                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<!--Button-->&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div class="modal-footer">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;<button class="btn btn-primary btn-block">Log </button>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;&lt;%&ndash;TEACHER FILE IMPOORT MODEL FORM&ndash;%&gt;&ndash;%&gt;--%>

                <%--</div>--%>
                <%--/SEARCH CONTAINER--%>


                <!--MAIN TAB CONTAINER -->
                <div class="mainTabContainer">

                    <br/>
                    <h3><spring:message code="semesterCourseList.text1"/></h3>

                    <%--!TABLE--%>
                    <div class="table-responsive">
                        <table id="courseTable" class="table table-bordered table-hover">


                            <thead>
                            <tr>
                                <th>#</th>
                                <th><spring:message code="manageCourse2.major"/></th>
                                <th><spring:message code="courseForm.courseId"/></th>
                                <th><spring:message code="courseForm.courseName"/></th>
                                <th><spring:message code="enrollmentList.courseType"/></th>
                                <th><spring:message code="manageUsers2.students"/></th>

                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var = "cList" items="${semesterCourseList}" varStatus="index">
                                <tr>
                                    <td>${index.count}</td>
                                    <td><c:out value="${cList.majorShortName}"/></td>
                                    <td><a href="${url}/admin/parent-course/view/${cList.courseId}"><c:out value="${cList.courseName}"/></a></td>
                                    <td><a href="${url}/admin/view/course/${cList.childCourseId}"><c:out value="${cList.courseShortName}"/></a></td>
                                    <td><c:out value="${cList.courseType}"/></td>
                                    <td><a href="${url}/admin/manage-courses/enrolment-list/${cList.childCourseId}/2"><c:out value="${cList.totalEnrollments}"/></a></td>

                                </tr>
                            </c:forEach>



                            </tbody>

                        </table>


                    </div>
                    <%--<--END OF TABLE-->--%>

                </div>




            </div>
            <%--/END OF MAIN CARD CONTAINER --%>


        </div>



    </div>


</div>

<script>

    $(document).ready(function() {
        $('#courseTable').DataTable();
    } );

    function  unEnrollStudent(userId){
        var r = confirm('<spring:message code="semesterCourseList.msg1"/>');
        if (r == true) {
            alert(userId);
        } else{
            //do nothing
            <%--window.location.href = '${url}/admin/dashboard';--%>
        }
    }

    function enrollStudent(userId){
        var r = confirm('<spring:message code="semesterCourseList.msg2"/>');
        if(r == true){
            alert(userId);
        }else{
            //do nothing
        }
    }

    function declineStudent(userId){
        var r = confirm('<spring:message code="semesterCourseList.msg3"/>');
        if(r == true){
            alert(userId);
        }else{
            //do nothing
        }
    }

    $("#majorSelect").change(function(){

        var thisvalue = $(this).find("option:selected").val();

        if(thisvalue === ""){
            window.location.href = '${url}/admin/semester/list/${semesterId}';
        }else {
            window.location.href = '${url}/admin/semester/list/${semesterId}?major='+thisvalue;
        }


    });
</script>




<%--footer--%>

</body>

</html>