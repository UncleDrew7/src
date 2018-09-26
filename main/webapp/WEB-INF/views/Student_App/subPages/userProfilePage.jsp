<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 12/09/2017
  Time: 07:46
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
    <title><spring:message code="application.myProfile" /></title>
    <link href="${url}/resources/app_admin_static/css/subPages/editUserProfile.css" rel="stylesheet">
    <link href="${url}/resources/app_admin_static/css/subPages/viewUserProfile.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <h3><spring:message code="application.myProfile" /></h3>

        <%--PROFILE CONTAINER --%>
        <div class="profileContainer">

            <%--IMAGE BOX--%>
            <h4 class="roleTitle"><span class="degreeTypeText"><spring:message code="user.DegreeType" />  : ${userProfile.degreeType}</span></h4>
            <a  href="${url}/student/profile/edit" class="link"><spring:message code="user.Edit" /></a>
            <div class="media">
                <div class="media-left">
                    <form method="POST" action="${url}/upload/image" enctype="multipart/form-data">
                        <img class="media-object" id="blah" src="${url}/download/images/${userProfile.profileImageUrl}">
                        <button id="editProPicBtn" style="width: 200px"  class="btn btn-xs btn-default"><spring:message code="studentUserProfilePage.editImage"/></button>
                        <div style="display: none" id="editProPicItems">
                            <input onchange="readURL(this);" autocomplete="off"type="file" name="file"  id="files" required>
                            <button style="width: 200px" type="submit" class="btn btn-xs btn-default"><spring:message code="extra.saveChanges"/></button>
                        </div>
                        <%--<img  src="${url}/resources/app_admin_static/images/admin-default.jpg" alt="...">--%>
                    </form>
                    <button style="width: 200px; display: none;"  onclick="restoreDefaultImage()" class=" dfbtn btn btn-xs btn-default"><spring:message code="studentUserProfilePage.restoreDefault"/></button>
                </div>
                <div class="media-body">
                    <h2 class="media-heading">${userId}(${userProfile.role})</h2>
                    <h4><spring:message code="user.Name" />   : ${userProfile.firstName}  ${userProfile.lastName} </h4>
                    <h4><spring:message code="manageCourse2.major"/> : ${userProfile.majorName}</h4>
                    <h4><spring:message code="user.Class" />  : ${userProfile.className}</h4>
                    <h4><spring:message code="user.Intake" /> : ${userProfile.intake}</h4>
                    <h4><spring:message code="user.Gender" /> : ${userProfile.gender}</h4>
                    <%--<h4>City   : London</h4>--%>
                    <h4><spring:message code="user.Country" /> : ${userProfile.country}</h4>
                </div>
            </div>
            <%--IMAGE BOX--%>


        </div>
        <%--PROFILE CONTAINER --%>


        <!--Big cards-->
        <div class="col-sm-7 ">
            <%--<h4>Infor</h4>--%>
            <div style="padding: 7px;" class="card aboutCardContainer ">

                <div class="formTitleBox">
                    <h4><spring:message code="user.About" /> <span class="inIcon glyphicon glyphicon-menu-down" aria-hidden="true"></span></h4>
                </div>

                <p>${userProfile.selfDescription}</p>

            </div>

            <div class="card requestCardContainer">

                <div class="formTitleBox">
                    <h4><spring:message code="student.examCourseE" /></h4>
                </div>
                <div class="table-responsive">
                    <table id="userReportTable" class="table  table-hover">

                        <%--<thead>--%>
                        <%--<tr>--%>

                            <%--<th>Enrollment Item</th>--%>
                            <%--<th>Item ID</th>--%>
                            <%--<th>Name</th>--%>
                            <%--<th>Enrollment Status</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>

                        <tbody>

                        <%--<c:forEach var="row" items='${userGradeItemReport.rows}' >--%>

                        <c:forEach var = "peList" items="${pendingExams}" varStatus="index">
                            <tr onclick="onPendingClicked()" class="g-warning">
                                <td><spring:message code="enrollStudent.examEnrollment" /></td>
                                <td><c:out value="${peList.gradeItemName}"/></td>
                                <td><spring:message code="extra.enrolmentStatus" /> &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-pill badge-danger"><spring:message code="system.pending" /></span></td>
                            </tr>
                        </c:forEach>

                        <c:forEach var = "pcList" items="${pendingCourses}" varStatus="index">
                            <tr onclick="onPendingClicked()" class="g-info">
                                <td><spring:message code="extra.courseEnrolment" /></td>
                                <td><c:out value="${pcList.courseShortName}"/></td>
                                <td><spring:message code="extra.enrolmentStatus" />  &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-pill badge-danger"><spring:message code="system.pending" /></span></td>
                            </tr>
                        </c:forEach>


                        <c:forEach var = "eeList" items="${enrolledExams}" varStatus="index">
                            <tr onclick="onEnrolledExamClicked(${eeList.courseId})" class="enrolled1">
                                <td><spring:message code="extra.examEnrolment" /></td>
                                <td><c:out value="${eeList.gradeItemName}"/></td>
                                <td><spring:message code="extra.enrolmentStatus" />  &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-pill badge-success"><spring:message code="system.enrolled" /></span></td>
                            </tr>
                        </c:forEach>
                        <c:forEach var = "ecList" items="${enrolledCourses}" varStatus="index">
                            <tr onclick="onEnrolledCourseClicked(${ecList.courseId})" class="enrolled1">
                                <td><spring:message code="extra.courseEnrolment" /></td>
                                <td><c:out value="${ecList.courseName}"/></td>
                                <td><spring:message code="extra.enrolmentStatus" />  &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-pill badge-success"><spring:message code="system.enrolled" /></span></td>
                            </tr>
                        </c:forEach>

                        <%--</c:forEach>--%>

                        </tbody>

                        <tfoot>
                        <tr>
                            <%--<td colspan="5" class="text-center"><a style="cursor: pointer" id="show"><spring:message code="system.showAllE" /></a><a style="cursor: pointer" id="hide"><spring:message code="extra.showLess" /></a></td>--%>
                        </tr>
                        </tfoot>
                    </table>

                </div>
                <%--<--END OF TABLE-->--%>
            </div>

            <div class="showMro" style="text-align: center">
                <i id="smT"><spring:message code="studentUserProfilePage.showMore"/> </i><span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
            </div>


        </div>
        <!--Big cards-->

        <!--Small cards-->
        <div class="col-sm-5 smallCardBox">
            <%--<h4>Courses</h4>--%>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#home"><h4><spring:message code="studentPageCourseLables.allCourses" />  <span class="badge badge-secondary">${currentCoursesList.size()}</span></h4></a></li>
                <li><a data-toggle="tab" href="#allExams"><h4><spring:message code="system.allExam" />  <span class="badge badge-secondary">${allStudentExamsList.size()}</span></h4></a></li>
            </ul>


            <div class="card bigCardContainer tab-content">
                <div id="home" class="tab-pane fade in active">

                    <ul class="list">
                        <c:forEach var = "currentCourses" items="${currentCoursesList}">
                            <li  class="item"><h4><a style="text-decoration: none" href="${url}/student/view/course/${currentCourses.childCourseId}"><c:out value="${currentCourses.courseShortName}"/></a></h4></li>
                        </c:forEach>
                    </ul>

                </div>

                <div id="allExams" class="tab-pane fade ">

                    <ul class="list">
                        <c:forEach var = "allStudentExams" items="${allStudentExamsList}">
                            <li class="item"><h4><c:out value="${allStudentExams.parentCourseShortName} / ${allStudentExams.examName}"/></h4></li>
                            <%--<li class="item"><h4><a href="${url}/student/course-exam/${allStudentExams.id}" data-toggle="tooltip" data-placement="right" title="${allStudentExams.courseName}"><c:out value="${allStudentExams.examName}"/></a></h4></li>--%>
                        </c:forEach>
                    </ul>

                </div>
            </div>


        </div>
        <!--Small cards-->

    </div>
    <!--MAIN CARD CONTAINER -->

</div>
<!--Content Ends  -->

<%--pending modal--%>

<div class="confirmModal">
    <div class="modal fade" id="pendingModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title confirmTitle"><spring:message code="studentUserProfilePage.enrolmentRequestIsPending"/> </h4>
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

<script>
    $("#show").click(function(){
        $(".enrolled").toggle(function(){
            $('#show').toggle();
            $('#hide').toggle();
        });
    });
    $('#hide').click(function () {
        $('.enrolled').toggle(function(){
            $('#show').toggle();
            $('#hide').toggle();
        });
    });

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                    .attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $(document).ready(function(){
        $(".aboutCardContainer").click(function(){
            $(".aboutCardContainer").toggleClass("aboutCardContainerClicked");
        });
    });

    $(".showMro").click(function(){
        $(".requestCardContainer").toggleClass("requestCardContainerClicked");
//        alert($("#smT").text());
        if($("#smT").text() === "Show More"){
            $('.showMro').empty();
            $(".showMro").prepend('<i id="smT"><spring:message code="extra.showLess"/> </i><span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span>');
        }else{
            $('.showMro').empty();
            $(".showMro").prepend('<i id="smT"><spring:message code="studentUserProfilePage.showMore"/> </i><span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>');
        }


    });

    $("#editProPicBtn").click(function(){
        $("#editProPicItems").css("display","inherit");
        $("#editProPicBtn").hide();
        $(".dfbtn").css("display","inherit");
    });

    function onPendingClicked() {
        $('#pendingModal').modal('toggle');
    }

    function onEnrolledCourseClicked(courseId) {
        window.location.href = '${url}/student/view/course/'+courseId+'?nav=myProfile';
    }

    function onEnrolledExamClicked(courseId){
        window.location.href = '${url}/student/course-exam/'+courseId+'?nav=myProfile';
    }

    function restoreDefaultImage() {
        $.ajax({
            url:'${url}/profile/image/restore_default',
            type:'post',
            success:function(data){
                location.reload(true);
            },
            error : function(data){
                alert('<spring:message code="main.msgError" />');
            }
        });
    }




</script>

</body>

</html>