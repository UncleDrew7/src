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
    <link href="${url}/resources/student_app_static/css/subPages/teacherProfile.css" rel="stylesheet">
</head>

<body>

<!--Content Begins  -->
<div class="content">

    <!--MAIN CARD CONTAINER -->
    <div class="row mainContainer">
        <h3><spring:message code="application.myProfile" /></h3>


        <!--Big cards-->
        <div class="col-sm-7 ">
            <%--<h4>Infor</h4>--%>
            <%--PROFILE CONTAINER --%>
                <div class="profileContainer">

                    <%--IMAGE BOX--%>
                    <c:if test="${userProfile.role eq 'Student'}">
                        <h4 class="roleTitle"><span class="degreeTypeText"><spring:message code="user.DegreeType" />  : ${userProfile.degreeType}</span></h4>
                    </c:if>
                    <a href="${url}/admin/profile/edit/${userId}/1" class="link"><spring:message code="user.Edit" /></a>
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
                            <h2 class="media-heading">${userId}
                                (<c:out value="${userProfile.role}"/>)
                            </h2>
                            <h4><spring:message code="user.Name" />   : ${userProfile.firstName}  ${userProfile.lastName} </h4>

                            <c:if test="${userProfile.role eq 'Student'}">
                                <h4><spring:message code="user.Class" />  : ${userProfile.className}</h4>
                                <h4><spring:message code="user.Intake" /> : ${userProfile.intake}</h4>
                            </c:if>

                            <h4><spring:message code="user.Gender" /> : ${userProfile.gender}</h4>
                            <%--<h4>City   : London</h4>--%>
                            <h4><spring:message code="user.Country" />: ${userProfile.country}</h4>
                        </div>
                    </div>

                    <%--IMAGE BOX--%>


                </div>
                <%--PROFILE CONTAINER --%>

            <div class="card requestCardContainer">

                <div class="formTitleBox">
                    <h4><spring:message code="studentPageCourseLables.currentCourses" /> <span class="badge badge-secondary">${currentCourseCounts}</span></h4>
                </div>
                <div class="table-responsive">
                    <ul class="list">
                        <c:forEach var = "allCourses" items="${currentCourses}">
                            <li class="item"><h4><a style="text-decoration: none; color: inherit" href="${url}/admin/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                        </c:forEach>
                    </ul>
                </div>
                <%--<--END OF TABLE-->--%>
            </div>


        </div>
        <!--Big cards-->

        <!--Small cards-->
        <div class="col-sm-5 smallCardBox">
            <%--<h4>Courses</h4>--%>
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#menu1"><h4><spring:message code="studentPageCourseLables.allCourses" />  <span class="badge badge-secondary">${allCoursesCount}</span></h4></a></li>
                <li><a data-toggle="tab" href="#allExams"><h4><spring:message code="system.allExam" />  <span class="badge badge-secondary">${allExamsCount}</span></h4></a></li>
            </ul>


            <div class="card bigCardContainer tab-content">
                <div id="menu1" class="tab-pane fade in active ">

                    <ul class="list">
                        <c:forEach var = "allCourses" items="${allCourses}">
                            <li class="item"><h4><a style="text-decoration: none" href="${url}/admin/view/course/${allCourses.courseId}"><c:out value="${allCourses.courseShortName}"/></a></h4></li>
                        </c:forEach>
                    </ul>

                </div>
                <div id="allExams" class="tab-pane fade ">

                    <ul class="list">
                        <c:forEach var = "allStudentExams" items="${allExams}">
                            <li class="item"><h4><a href="${url}/admin/manage-courses/course-exam/${allStudentExams.courseId}" data-toggle="tooltip" data-placement="right" title="${allStudentExams.courseName}"><c:out value="${allStudentExams.gradeItemName}"/></a></h4></li>
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

    $("#editProPicBtn").click(function(){
        $("#editProPicItems").css("display","inherit");
        $("#editProPicBtn").hide();
        $(".dfbtn").css("display","inherit");
    });

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